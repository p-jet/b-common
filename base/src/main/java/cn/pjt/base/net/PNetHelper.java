package cn.pjt.base.net;

import android.content.Context;
import android.os.Handler;

import java.io.File;
import java.util.Map;
import java.util.WeakHashMap;

import cn.pjt.base.net.callback.IFailure;
import cn.pjt.base.net.callback.ISuccess;
import cn.pjt.base.net.config.ConfigKeys;
import cn.pjt.base.net.config.Configurator;
import cn.pjt.base.net.http.HttpCreator;
import cn.pjt.base.net.http.IMethod;
import cn.pjt.base.net.loader.PNetLoader;
import cn.pjt.base.net.loader.LoaderStyle;
import cn.pjt.base.net.rx.PNetLifeCycle;
import cn.pjt.base.net.rx.PNetObserver;
import cn.pjt.base.net.rx.SwitchSchedulers;
import cn.pjt.base.net.utils.NetBuilder;
import io.reactivex.Observable;
import io.reactivex.Observer;
import okhttp3.RequestBody;

/**
 * create by pjt on 19-6-10
 * description:
 **/
public class PNetHelper {


    private final String URL;
    private final WeakHashMap<String, Object> PARAMS = new WeakHashMap<>();
    private final RequestBody BODY;
    private final LoaderStyle LOADER_STYLE;
    private final Context CONTEXT;
    private final File FILE;
    private final ISuccess<Object> SUCCESS;
    private final IFailure FAILURE;
    private IMethod FOYONETMETHOD;
    private Class<?> SERVICE;
    private final PNetLifeCycle LIFE_CYCLE;
    private Class<Observer> observerClass;

    public PNetHelper(String url,
                      Class<?> service,
                      IMethod method,
                      Map<String, Object> params,
                      RequestBody body,
                      Context context,
                      LoaderStyle loaderStyle,
                      File file,
                      ISuccess<Object> success,
                      IFailure failure,
                      PNetLifeCycle lifecycle
    ) {
        this.URL = url;
        this.SERVICE = service;
        this.FOYONETMETHOD = method;
        if (!PARAMS.isEmpty()) {
            PARAMS.clear();
        }
        PARAMS.putAll(params);
        this.BODY = body;
        this.CONTEXT = context;
        this.LOADER_STYLE = loaderStyle;
        this.FILE = file;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.LIFE_CYCLE = lifecycle;
    }

    public static NetBuilder builder() {
        return new NetBuilder();
    }

    public static Configurator init(Context context) {
        Configurator.getInstance().getFoYoConfigs().put(ConfigKeys.APPLICATION_CONTEXT, context.getApplicationContext());
        return getConfigurator();
    }

    public static Configurator getConfigurator() {
        return Configurator.getInstance();
    }

    /**
     * 可用来设置 全局变量
     *
     * @param key
     * @param <T>
     * @return
     */
    public static <T> T getConfiguration(Object key) {
        return Configurator.getInstance().getConfiguration(key);
    }

    public static Context getApp() {
        return getConfiguration(ConfigKeys.APPLICATION_CONTEXT);
    }

    public static Handler getHandler() {
        return getConfiguration(ConfigKeys.HANDLER);
    }

    /**
     * 构造请求Observable 可在项目中RxJava 串行使用
     *
     * @param <T>
     * @return
     */
    public <T> Observable<T> request() throws Exception {

        Observable observable = null;
        if (null == FOYONETMETHOD) {
            throw new Exception("net method can not be null");
        }
        Object service;
        if (null != SERVICE) {
            service = HttpCreator.getService(SERVICE);
        } else {
            throw new Exception("Attention : service method parameters can not be null");
        }
        if (PARAMS.isEmpty()) {
            throw new Exception("params can not be null");
        }
        observable = FOYONETMETHOD.ob(service, PARAMS);
        if (null == observable) {
            throw new Exception("observable can not be null");
        }
        return observable;
    }

    private interface CallListener<T> {

        void onSucc(T data);

        void onFail(int code, String desc);
    }

    /**
     * 调用次方法 执行具体的请求操作
     */
    public void excute() {
        try {
            showLoading();
            Observable o = request();
            if (null != o) {
                execute(o, new CallListener() {
                    @Override
                    public void onSucc(Object data) {
                        stopLoding();
                        if (null != SUCCESS) {
                            SUCCESS.onSuccess(data);
                        }
                    }

                    @Override
                    public void onFail(int code, String desc) {
                        stopLoding();
                        if (null != FAILURE) {
                            FAILURE.onFailure(code, desc);
                        }
                    }
                });
            } else {
                throw new Exception("Observable get failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            stopLoding();
        }
    }


    /**
     * 设置请求的具体回调监听
     *
     * @param observable
     * @param <T>
     */
    private <T> void execute(Observable<T> observable, final CallListener callBack) {
        Observable<T> tObservable = observable.compose(new SwitchSchedulers<T>().applySchedulers());
        if (null != LIFE_CYCLE) {
            tObservable = tObservable.compose(LIFE_CYCLE.<T>bindToLife());
        }
        tObservable.subscribe(new PNetObserver<T>() {
            @Override
            public void onSuccess(T data) {
                callBack.onSucc(data);
            }

            @Override
            public void onFailure(int code, String desc) {
                callBack.onFail(code, desc);
            }
        });
    }

    private void showLoading() {
        if (null != CONTEXT && null != LOADER_STYLE) {
            PNetLoader.showLoading(CONTEXT, LOADER_STYLE);
        }
    }

    private void stopLoding() {
        if (null != LOADER_STYLE) {
            PNetLoader.stopLoading();
        }
    }

}
