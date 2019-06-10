package cn.pjt.base.net.filter.data;

import java.io.Serializable;

/**
 * create by pjt on 19-6-10
 * description:
 **/
public class RespData<EntityType> implements Serializable {

    public IRespEntity<EntityType> respEntity;
    public IRespError<EntityType> respError;

}
