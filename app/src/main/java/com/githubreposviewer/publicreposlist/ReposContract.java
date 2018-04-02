package com.githubreposviewer.publicreposlist;

import com.githubreposviewer.data.pojo.Repo;

import java.util.List;

public interface ReposContract {
    interface ReposView {
        void showRepositories(List<Repo> repos);

        void toggleProgressIndicator(boolean visible);

        void showRepositoriesUnavailableError();
    }

    interface ReposPresenter {
        void loadRepositories();

        void detachView();
    }
}
