package com.avast.example.android.github;

import android.app.Application;
import android.os.StrictMode;

import com.avast.example.android.github.dagger.AppComponent;
import com.avast.example.android.github.dagger.DaggerAppComponent;
import com.avast.example.android.github.dagger.module.AppModule;

/**
 * Application holds app wide data.
 *
 * @author Tomáš Kypta (kypta)
 */
public class GitHubApplication extends Application {

    // TODO task 8
    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        initStrictMode();

        mAppComponent = initAppComponent();
    }

    // way to override in other flavors
    protected AppComponent initAppComponent() {
        // TODO task 8
        return DaggerAppComponent.builder()
            .appModule(new AppModule(this))
            .build();
    }

    // TODO task 8
    public AppComponent getAppComponent() {
        return mAppComponent;
    }

    @Override
    public Object getSystemService(String name) {
        // TODO task 7

        // TODO task 8

        return super.getSystemService(name);
    }

    /**
     * Init StrictMode
     * This setup works since API level 11 (some parts work since API level 9)
     */
    private void initStrictMode() {
        if (BuildConfig.DEBUG) {
            StrictMode.ThreadPolicy.Builder tpb = new StrictMode.ThreadPolicy.Builder();
            tpb.detectAll();
            tpb.penaltyLog();
            tpb.penaltyFlashScreen();
            StrictMode.setThreadPolicy(tpb.build());

            StrictMode.VmPolicy.Builder vmpb = new StrictMode.VmPolicy.Builder();
            vmpb.detectLeakedSqlLiteObjects();
            vmpb.detectLeakedClosableObjects();
            vmpb.penaltyLog();
            StrictMode.setVmPolicy(vmpb.build());
        }
    }
}
