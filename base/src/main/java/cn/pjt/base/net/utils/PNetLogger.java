package cn.pjt.base.net.utils;

import com.orhanobut.logger.Logger;

import cn.pjt.base.net.config.Configurator;

/**
 * create by pjt on 19-6-10
 * description:
 **/
public class PNetLogger {

    private static final int VERBOSE = 1;
    private static final int DEBUG = 2;
    private static final int INFO = 3;
    private static final int WARN = 4;
    private static final int ERROR = 5;

    //控制log等级
    private static int LEVEL = VERBOSE;

    public static void v(String tag, String message) {
        if (LEVEL <= VERBOSE) {

            if (Configurator.isDebugMode()) {
                Logger.t(tag).v(message);
            }
        }
    }

    public static void d(String tag, Object message) {
        if (LEVEL <= DEBUG) {
            if (Configurator.isDebugMode()) {
                Logger.t(tag).d(message);
            }
        }
    }

    public static void d(Object message) {
        if (LEVEL <= DEBUG) {
            if (Configurator.isDebugMode()) {
                Logger.d(message);
            }
        }
    }

    public static void i(String tag, String message) {
        if (LEVEL <= INFO) {
            if (Configurator.isDebugMode()) {
                Logger.t(tag).i(message);
            }
        }
    }

    public static void w(String tag, String message) {
        if (LEVEL <= WARN) {
            if (Configurator.isDebugMode()) {
                Logger.t(tag).w(message);
            }
        }
    }

    public static void json(String tag, String message) {
        if (LEVEL <= WARN) {
            if (Configurator.isDebugMode()) {
                Logger.t(tag).json(message);
            }
        }
    }

    public static void e(String tag, String message) {
        if (LEVEL <= ERROR) {
            if (Configurator.isDebugMode()) {
                Logger.t(tag).e(message);
            }
        }
    }


}
