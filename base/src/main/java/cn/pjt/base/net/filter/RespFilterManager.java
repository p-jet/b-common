package cn.pjt.base.net.filter;

import java.util.ArrayList;
import java.util.List;

import cn.pjt.base.net.config.ConfigKeys;
import cn.pjt.base.net.config.Configurator;
import cn.pjt.base.net.filter.chain.RealRespChain;
import cn.pjt.base.net.filter.data.RespData;

/**
 * create by pjt on 19-6-10
 * description:
 **/
public class RespFilterManager {

    /**
     * 执行 网络请求 返回数据 的分类处理 解耦框架和业务层
     * @param data
     * @return
     * @throws Exception
     */
    public static RespData execute(RespData data) throws Exception {

        List<RespFilter> filters = new ArrayList<>();
        List<RespFilter> clientFilters = Configurator.getInstance().getConfiguration(ConfigKeys.NET_FILTER);

        filters.add(new RespBaseFilter());
        if (null != clientFilters && clientFilters.size() > 0) {
            filters.addAll(clientFilters);
        }
        RespFilter.RespChain realRespChain = new RealRespChain(filters, data, 0);
        return realRespChain.proceed(data);
    }


}
