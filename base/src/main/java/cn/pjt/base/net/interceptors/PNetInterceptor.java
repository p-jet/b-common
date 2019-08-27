package cn.pjt.base.net.interceptors;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.LinkedHashMap;

import cn.pjt.base.net.config.Configurator;
import cn.pjt.base.net.utils.PNetLogger;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;


/**
 * create by pjt on 19-6-10
 * description:
 **/
public class PNetInterceptor extends BaseInterceptor {

    private final static String TAG =  PNetInterceptor.class.getSimpleName();

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {

        Request request = chain.request();
        long startTime = System.currentTimeMillis();
        Response response = chain.proceed(request);
        long endTime = System.currentTimeMillis();
        ResponseBody body = response.body();

        if (!Configurator.isDebugMode()) {
            return response;
        }
        if (body == null)
            return response;

        if (!bodyEncoded(response.headers())) {
            BufferedSource source = body.source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.buffer();

            Charset charset = UTF8;
            MediaType contentType = body.contentType();
            if (contentType != null) {
                try {
                    charset = contentType.charset(UTF8);
                } catch (UnsupportedCharsetException e) {
                    return response;
                }
            }
            if (!isPlaintext(buffer)) {
                return response;
            }
            String result = buffer.clone().readString(charset);
            //得到所需的string，开始判断是否异常
            //***********************do something*****************************
            LinkedHashMap<String, String> urlParameters = getUrlParameters(chain);
            LinkedHashMap<String, String> bodyParameters = getBodyParameters(chain);

            String timeResuming = cacuTimeResuming(startTime, endTime);

            PNetLogger.i(TAG, request.toString() + "\n" +
                    "UrlParameter:" + urlParameters + "\n" +
                    "BodyParameter:" + bodyParameters + "\n" +
                    response + "\n" +
                    "Result:" + result + "\n" +
                    "TimeConsuming:" + timeResuming);
        }
        return response;
    }


    private String cacuTimeResuming(long startTime, long endTime) {
        long timeConsuming = endTime - startTime;
        return timeConsuming + "ms";
    }


}
