package cn.pjt.base.net.filter.data;

import java.io.Serializable;

/**
 * create by pjt on 19-6-10
 * description:
 **/
public class RespEntity<T> implements IRespEntity, Serializable {

    T t;

    public RespEntity(T t) {
        this.t = t;
    }

    @Override
    public T entity() {
        return t;
    }

    @Override
    public void setEntity(Object o) {
        t = (T) o;
    }

}
