package cn.pjt.base.net.callback;


/**
 * create by pjt on 19-6-10
 * description:
 **/
public interface ISuccess<T> {

    /**
     * request success callback method
     *
     * @param response request success data
     */
    void onSuccess(T response);

}
