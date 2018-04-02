package com.githubreposviewer.publicreposlist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.githubreposviewer.R;
import com.githubreposviewer.data.pojo.Repo;

import java.util.ArrayList;
import java.util.List;

class ReposListAdapter extends RecyclerView.Adapter<ReposListAdapter.ReposViewHolder> {

    private final List<Repo> repos;
    private final LayoutInflater layoutInflater;

    public ReposListAdapter(Context context) {
        this.layoutInflater = LayoutInflater.from(context);
        this.repos = new ArrayList<>();
    }

    @NonNull
    @Override
    public ReposViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = layoutInflater.inflate(R.layout.repos_list_item, parent, false);
        return new ReposViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReposViewHolder holder, int position) {
        final Repo repo = repos.get(position);
        holder.tvRepoName.setText(repo.getFullName());
        holder.tvRepoDescription.setText(repo.getDescription());
    }

    @Override
    public int getItemCount() {
        return repos.size();
    }

    public void replace(List<Repo> repos) {
        this.repos.clear();
        this.repos.addAll(repos);
        notifyDataSetChanged();
    }

    static class ReposViewHolder extends RecyclerView.ViewHolder {
        TextView tvRepoName;
        TextView tvRepoDescription;

        ReposViewHolder(View itemView) {
            super(itemView);

            tvRepoName = itemView.findViewById(R.id.repos_list_item_repo_name);
            tvRepoDescription = itemView.findViewById(R.id.repos_list_item_repo_description);
        }
    }
}
