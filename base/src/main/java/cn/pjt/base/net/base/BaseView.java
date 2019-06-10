package cn.pjt.base.net.base;

import android.content.Context;

import cn.pjt.base.net.rx.PNetLifeCycle;

/**
 * create by pjt on 19-6-10
 * description:
 **/
public interface BaseView<T> extends PNetLifeCycle {

    /**t
     * Presenter 获取view 的 Context
     *
     * @return
     */
    Context getViewContext();

}
