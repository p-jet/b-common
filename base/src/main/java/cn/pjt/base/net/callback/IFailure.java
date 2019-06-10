package cn.pjt.base.net.callback;

/**
 * create by pjt on 19-6-10
 * description:
 **/
public interface IFailure {

    /**
     * @param code request error code
     * @param desc request error description
     */
    void onFailure(int code, String desc);

}
