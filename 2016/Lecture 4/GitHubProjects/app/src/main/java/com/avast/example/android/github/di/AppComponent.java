package com.avast.example.android.github.di;

import javax.inject.Singleton;

import dagger.Component;

import com.avast.example.android.github.service.DownloadService;

/**
 * @author Lukas Prokop (prokop@avast.com)
 */
@Singleton
@Component(modules = {ApiModule.class})
public interface AppComponent {

    void inejct(DownloadService downloadService);
}
