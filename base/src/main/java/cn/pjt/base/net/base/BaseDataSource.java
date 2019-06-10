package cn.pjt.base.net.base;

import java.util.List;

/**
 * create by pjt on 19-6-10
 * description:
 **/
public interface BaseDataSource {

    interface LoadTasksCallback<T> {

        void onTasksLoaded(List<T> tasks);

        void onDataNotAvailable(int errorType, String message);
    }

    interface GetTaskCallback<T> {

        void onTaskLoaded(T task);

        void onDataNotAvailable(int code, String desc);
    }

}
