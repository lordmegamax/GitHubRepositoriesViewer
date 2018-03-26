package com.githubreposviewer.data;

import com.githubreposviewer.data.pojo.Repo;
import io.reactivex.Flowable;

import java.util.List;

public class DataRepositoryImpl implements DataRepository {

    private final GitHubApi gitHubApi;

    public DataRepositoryImpl() {
        gitHubApi = new GitHubApi();
    }

    @Override
    public Flowable<List<Repo>> publicRepos() {
        return gitHubApi.service.listPublicRepos();
    }
}
