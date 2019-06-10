package cn.pjt.base.net.rx;


import com.trello.rxlifecycle2.LifecycleTransformer;

/**
 * create by pjt on 19-6-10
 * description:
 **/
@FunctionalInterface
public interface PNetLifeCycle {
    /**
     * 绑定生命周期
     */
    <T> LifecycleTransformer<T> bindToLife();
}
