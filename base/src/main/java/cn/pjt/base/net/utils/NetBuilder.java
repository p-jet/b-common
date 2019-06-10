package cn.pjt.base.net.utils;

import android.content.Context;
import android.text.TextUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

import cn.pjt.base.net.PNetHelper;
import cn.pjt.base.net.base.BaseView;
import cn.pjt.base.net.callback.IFailure;
import cn.pjt.base.net.callback.ISuccess;
import cn.pjt.base.net.config.ConfigKeys;
import cn.pjt.base.net.config.Configurator;
import cn.pjt.base.net.http.IMethod;
import cn.pjt.base.net.loader.LoaderStyle;
import cn.pjt.base.net.rx.PNetLifeCycle;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * create by pjt on 19-6-10
 * description:
 **/
public class NetBuilder {

    private String mUrl = null;
    private Map<String, Object> PARAMS = new WeakHashMap<>();
    private RequestBody mRequestBody = null;
    private Context mContext = null;
    private LoaderStyle mLoaderStyle = null;
    private File mFile = null;
    private ISuccess mSuccess = null;
    private IFailure mFailure = null;
    private IMethod mMethod;
    private Class<?> mService;
    private PNetLifeCycle mFoYoRxLifecycle;
    private BaseView mView;

    public NetBuilder() {
    }

    public final NetBuilder service(Class<?> service) {
        this.mService = service;
        return this;
    }

    public final NetBuilder method(IMethod method) {
        this.mMethod = method;
        return this;
    }

    public final NetBuilder url(String url) {
        this.mUrl = url;
        return this;
    }

    public final NetBuilder params(Map<String, String> params) {
        PARAMS.putAll(params);
        return this;
    }

    public final NetBuilder params(String key, String value) {
        if (null != value)
            PARAMS.put(key, value);
        return this;
    }

    public final NetBuilder params(String key, Object value) {
        if (null != value)
            PARAMS.put(key, value);
        return this;
    }

    public final NetBuilder paramsValueObj(Map<String, Object> params) {
        PARAMS.putAll(params);
        return this;
    }

    public final NetBuilder file(File file) {
        this.mFile = file;
        return this;
    }

    public final NetBuilder file(String filePath) {
        this.mFile = new File(filePath);
        return this;
    }

    public final NetBuilder raw(String raw) {
        this.mRequestBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), raw);
        return this;
    }

    public final NetBuilder context(Context context) {
        this.mContext = context;
        return this;
    }

    public final NetBuilder bindLifeCycle(PNetLifeCycle lifeCycle) {
        this.mFoYoRxLifecycle = lifeCycle;
        return this;
    }

    public final NetBuilder view(BaseView view) {
        this.mView = view;
        this.mContext = view.getViewContext();
        this.mFoYoRxLifecycle = view;
        return this;
    }

    public final NetBuilder loader(Context context, LoaderStyle loaderStyle) {
        this.mContext = context;
        this.mLoaderStyle = loaderStyle;
        return this;
    }

    public final NetBuilder loader(Context context) {
        this.mContext = context;
        this.mLoaderStyle = LoaderStyle.BallSpinFadeLoaderIndicator;
        return this;
    }

    public final NetBuilder success(ISuccess success) {
        this.mSuccess = success;
        return this;
    }

    public final NetBuilder failure(IFailure failure) {
        this.mFailure = failure;
        return this;
    }

    public final PNetHelper build() {
        HashMap<String, Object> globalParams = Configurator.getInstance().getConfiguration(ConfigKeys.NET_GLOBLE_PARAMS);
        if (null != globalParams) {
            PARAMS.putAll(globalParams);
        }
        String token = Configurator.getInstance().getConfiguration(ConfigKeys.TOKEN);
        if (!TextUtils.isEmpty(token)) {
            PARAMS.put("token", token);
        }
        return new PNetHelper(mUrl, mService,
                mMethod, PARAMS, mRequestBody,
                mContext, mLoaderStyle, mFile, mSuccess,
                mFailure, mFoYoRxLifecycle);
    }

}
