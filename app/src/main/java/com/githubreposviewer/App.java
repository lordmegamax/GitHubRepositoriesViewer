package com.githubreposviewer;

import android.app.Application;
import com.githubreposviewer.data.DataSource;
import com.githubreposviewer.data.DataSourceImpl;

public class App extends Application {


    private static DataSource dataSource;

    public static DataSource getDataSource() {
        if (dataSource == null) {
            dataSource = new DataSourceImpl();
        }

        return dataSource;
    }
}
