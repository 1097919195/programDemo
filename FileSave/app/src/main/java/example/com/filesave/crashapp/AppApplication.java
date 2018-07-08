package example.com.filesave.crashapp;


import android.app.Application;
import android.content.Context;

import example.com.filesave.basecrashlog.AppCrashHandler;

/**
 * APPLICATION
 */
public class AppApplication extends Application {

    private static AppApplication appApplication;

    @Override
    public void onCreate() {
        super.onCreate();
//		CrashAppLog.getInstance().init(this);

        AppCrashHandler.getInstance().init(this);
    }

    public static Context getAppContext() {
        return appApplication;
    }
}
