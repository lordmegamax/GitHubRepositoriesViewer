package com.githubreposviewer;

import android.app.Application;
import com.githubreposviewer.data.DataRepository;
import com.githubreposviewer.data.DataRepositoryImpl;

public class App extends Application {


    private static DataRepositoryImpl dataRepository;

    public static DataRepository getDataRepository() {
        if (dataRepository == null) {
            dataRepository = new DataRepositoryImpl();
        }

        return dataRepository;
    }
}
