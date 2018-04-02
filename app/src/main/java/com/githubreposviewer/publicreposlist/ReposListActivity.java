package com.githubreposviewer.publicreposlist;

import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.githubreposviewer.App;
import com.githubreposviewer.R;
import com.githubreposviewer.data.pojo.Repo;

import java.util.List;

public class ReposListActivity extends AppCompatActivity implements ReposContract.ReposView {

    private static final String TAG = "ReposListActivity";
    private static final String KEY_LAYOUT_MANAGER_STATE = "key_layout_manager_state";

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView errorTextView;
    private ReposListAdapter reposListAdapter;

    private ReposPresenter presenter;

    private Bundle lastSavedInstanceState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repos_list);
        lastSavedInstanceState = savedInstanceState;

        initViews();
        initPresenter();
    }

    private void initViews() {
        progressBar = findViewById(R.id.activity_repos_list_progress_bar);
        errorTextView = findViewById(R.id.activity_repos_list_loading_error_text_view);
        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.activity_repos_list_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        reposListAdapter = new ReposListAdapter(this);
        recyclerView.setAdapter(reposListAdapter);
    }

    private void initPresenter() {
        presenter = new ReposPresenter(this, App.getDataSource());
        presenter.loadRepositories();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
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

    @Override
    public void showRepositories(List<Repo> repos) {
        reposListAdapter.replace(repos);
        toggleProgressIndicator(false);
        errorTextView.setVisibility(View.GONE);

        restoreRecyclerViewState(lastSavedInstanceState);
    }

    @Override
    public void toggleProgressIndicator(boolean visible) {
        progressBar.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showRepositoriesUnavailableError() {
        toggleProgressIndicator(false);
        errorTextView.setVisibility(View.VISIBLE);
    }
}
