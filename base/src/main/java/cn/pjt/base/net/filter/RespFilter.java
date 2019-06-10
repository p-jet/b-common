package cn.pjt.base.net.filter;

import cn.pjt.base.net.filter.data.RespData;

/**
 * create by pjt on 19-6-10
 * description:
 **/
public interface RespFilter {

    RespData filter(RespChain chain) throws Exception;

    interface RespChain {

        RespData proceed(RespData data) throws Exception;

        RespData respData();
    }

}
