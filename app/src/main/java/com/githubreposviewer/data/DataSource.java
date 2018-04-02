package com.githubreposviewer.data;

import com.githubreposviewer.data.pojo.Repo;
import io.reactivex.Flowable;

import java.util.List;

public interface DataSource {
    Flowable<List<Repo>> publicRepos();
}
