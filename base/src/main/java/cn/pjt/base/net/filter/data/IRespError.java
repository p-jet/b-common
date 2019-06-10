package cn.pjt.base.net.filter.data;

/**
 * create by pjt on 19-6-10
 * description:
 **/
public interface IRespError<T> {

    int code();

    String errorMsg();

    void setCode(int code);

    void setErrorMsg(String errorMsg);

    T t();

    void setT(T t);


}
