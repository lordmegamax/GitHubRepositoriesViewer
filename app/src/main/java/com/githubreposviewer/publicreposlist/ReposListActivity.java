package com.githubreposviewer.publicreposlist;

import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.githubreposviewer.App;
import com.githubreposviewer.R;
import com.githubreposviewer.data.DataRepository;
import com.githubreposviewer.data.pojo.Repo;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import org.reactivestreams.Subscription;

import java.util.List;

public class ReposListActivity extends AppCompatActivity {

    private static final String TAG = "ReposListActivity";
    private static final String KEY_LAYOUT_MANAGER_STATE = "key_layout_manager_state";

    private Disposable publicReposDisposable;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private ReposListAdapter reposListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repos_list);

        progressBar = findViewById(R.id.activity_repos_list_progress_bar);

        initRecyclerView();
        loadReposList(savedInstanceState);
    }

    private void loadReposList(final Bundle savedInstanceState) {
        DataRepository repo = App.getDataRepository();
        publicReposDisposable = repo
                .publicRepos()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Subscription>() {
                    @Override
                    public void accept(Subscription subscription) throws Exception {
                        progressBar.setVisibility(View.VISIBLE);
                    }
                })
                .subscribe(new Consumer<List<Repo>>() {
                               @Override
                               public void accept(List<Repo> gitHubRepositories) throws Exception {
                                   reposListAdapter.replace(gitHubRepositories);
                                   progressBar.setVisibility(View.INVISIBLE);

                                   restoreRecyclerViewState(savedInstanceState);
                               }
                           }
                        , new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Toast.makeText(getApplicationContext(), R.string.error_loading_public_repos, Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.INVISIBLE);
                            }
                        });
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY_LAYOUT_MANAGER_STATE, recyclerView.getLayoutManager().onSaveInstanceState());
    }

    private void restoreRecyclerViewState(final Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            Parcelable savedRecyclerLayoutState = savedInstanceState.getParcelable(KEY_LAYOUT_MANAGER_STATE);
            recyclerView.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
        }
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.activity_repos_list_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        reposListAdapter = new ReposListAdapter(this);
        recyclerView.setAdapter(reposListAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (publicReposDisposable != null) {
            publicReposDisposable.dispose();
        }
    }
}
