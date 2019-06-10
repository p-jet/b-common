package cn.pjt.base.net.filter.chain;

import java.util.List;

import cn.pjt.base.net.filter.RespFilter;
import cn.pjt.base.net.filter.data.RespData;

/**
 * create by pjt on 19-6-10
 * description:
 **/
public class RealRespChain implements RespFilter.RespChain {


    private List<RespFilter> filters;
    private RespData respData;
    private int index;

    public RealRespChain(List<RespFilter> filters, RespData respData, int index) {
        this.filters = filters;
        this.respData = respData;
        this.index = index;
    }

    @Override
    public RespData proceed(RespData data) throws Exception {

        if (index >= filters.size()) {
            return data;
        }
        RespFilter.RespChain next = new RealRespChain(filters, respData, index + 1);
        RespFilter respFilter = filters.get(index);

        return respFilter.filter(next);
    }

    @Override
    public RespData respData() {
        return respData;
    }


}
