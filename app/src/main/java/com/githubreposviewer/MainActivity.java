package com.githubreposviewer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.githubreposviewer.data.DataRepository;
import com.githubreposviewer.data.pojo.Repo;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    Disposable publicReposDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DataRepository repo = App.getDataRepository();
        publicReposDisposable = repo
                .publicRepos()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Repo>>() {
                               @Override
                               public void accept(List<Repo> gitHubRepositories) throws Exception {
                                   // FIXME: 26-Mar-18 implement
                               }
                           }
                        , new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                // FIXME: 26-Mar-18 implement
                            }
                        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (publicReposDisposable != null) {
            publicReposDisposable.dispose();
        }
    }
}
