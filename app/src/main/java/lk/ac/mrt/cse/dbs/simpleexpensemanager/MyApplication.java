package lk.ac.mrt.cse.dbs.simpleexpensemanager;

import android.app.Application;
import android.content.Context;

/**
 * Created by arunan on 11/16/16.
 */

public class MyApplication extends Application{
    private static Application sApplication;

    public static Application getApplication(){
        return sApplication;
    }

    public static Context getContext(){
        return getApplication().getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;
    }
}
