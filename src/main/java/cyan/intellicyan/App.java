package cyan.intellicyan;

import android.app.Application;

import cyan.intellicyan.util.CrashHandler;

/**
 * Created by wx on 2017/4/4.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler ch = new CrashHandler(this);
        Thread.setDefaultUncaughtExceptionHandler(ch);
    }
}
