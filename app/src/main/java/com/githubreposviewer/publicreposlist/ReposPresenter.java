package com.githubreposviewer.publicreposlist;

import com.githubreposviewer.data.DataSource;
import com.githubreposviewer.data.pojo.Repo;
import com.githubreposviewer.publicreposlist.ReposContract.ReposView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import org.reactivestreams.Subscription;

import java.util.List;

public class ReposPresenter implements ReposContract.ReposPresenter {

    private DataSource dataSource;
    private Disposable disposable;
    private ReposView view;

    public ReposPresenter(final ReposView view, final DataSource dataSource) {
        this.view = view;
        this.dataSource = dataSource;
    }

    @Override
    public void loadRepositories() {
        disposable = dataSource
                .publicRepos()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Subscription>() {
                    @Override
                    public void accept(Subscription subscription) throws Exception {
                        if (view != null) {
                            view.toggleProgressIndicator(true);
                        }
                    }
                })
                .subscribe(new Consumer<List<Repo>>() {
                    @Override
                    public void accept(List<Repo> gitHubRepositories) throws Exception {
                        if (view != null) {
                            view.showRepositories(gitHubRepositories);
                            view.toggleProgressIndicator(false);
                        }

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (view != null) {
                            view.showRepositoriesUnavailableError();
                        }
                    }
                });
    }

    @Override
    public void detachView() {
        view = null;
        if (disposable != null) {
            disposable.dispose();
        }
    }
}
