package com.githubreposviewer.data;

import com.githubreposviewer.data.pojo.Repo;
import io.reactivex.Flowable;
import retrofit2.http.GET;

import java.util.List;

public interface GitHubService {
    @GET("/repositories")
    Flowable<List<Repo>> listPublicRepos();
}
