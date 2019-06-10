package cn.pjt.base.net.filter;

import cn.pjt.base.net.filter.data.RespData;

/**
 * create by pjt on 19-6-10
 * description:
 **/
public class RespBaseFilter implements RespFilter {

    @Override
    public RespData filter(RespChain chain) throws Exception {

        RespData respData = chain.respData();
        RespData dealRespData = chain.proceed(respData);
        if (dealRespData != null) {
            respData = dealRespData;
        }
        return respData;
    }

}
