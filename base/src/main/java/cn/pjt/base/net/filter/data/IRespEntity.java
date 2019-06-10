package cn.pjt.base.net.filter.data;

/**
 * create by pjt on 19-6-10
 * description:
 **/
public interface IRespEntity<T> {

    T entity();

    void setEntity(T t);

}
