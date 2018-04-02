package com.githubreposviewer.data;

import com.githubreposviewer.data.pojo.Repo;
import io.reactivex.Flowable;

import java.util.List;

public class DataSourceImpl implements DataSource {

    private final GitHubApi gitHubApi;

    public DataSourceImpl() {
        gitHubApi = new GitHubApi();
    }

    @Override
    public Flowable<List<Repo>> publicRepos() {
        return gitHubApi.getService().listPublicRepos();
    }
}
