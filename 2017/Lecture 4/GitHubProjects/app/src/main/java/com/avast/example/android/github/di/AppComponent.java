package com.avast.example.android.github.di;

import javax.inject.Singleton;

import dagger.Component;

import com.avast.example.android.github.BaseApplication;
import com.avast.example.android.github.service.DownloadIntentService;
import com.avast.example.android.github.service.DownloadService;

/**
 * @author Lukas Prokop (prokop@avast.com)
 */
@Singleton
@Component(modules = {ApplicationModule.class, ApiModule.class})
public interface AppComponent {

    void inejct(DownloadService downloadService);

    void inject(DownloadIntentService downloadIntentService);

    void inject(BaseApplication baseApplication);
}
