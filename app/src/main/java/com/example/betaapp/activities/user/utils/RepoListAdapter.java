package com.example.betaapp.activities.user.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.betaapp.R;
import com.example.betaapp.db.models.DBORepo;

import java.util.ArrayList;

public class RepoListAdapter extends RecyclerView.Adapter<RepoListAdapter.ViewHolder> {

    // -------------------------------------------------------------------------------
    // Fields
    // -------------------------------------------------------------------------------

    private final ArrayList<DBORepo> repos = new ArrayList<>();

    private final OnRepoClickListener clickListener;

    // -------------------------------------------------------------------------------
    // Instance creations
    // -------------------------------------------------------------------------------

    public RepoListAdapter(OnRepoClickListener clickListener) {
        this.clickListener = clickListener;
    }

    // -------------------------------------------------------------------------------
    // Override
    // -------------------------------------------------------------------------------

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_repo, parent, false);
        return new ViewHolder(view, clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getTextView().setText(repos.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return repos.size();
    }

    // -------------------------------------------------------------------------------
    // Public
    // -------------------------------------------------------------------------------

    public void updateList(ArrayList<DBORepo> newRepos) {
        repos.clear();
        repos.addAll(newRepos);
        notifyDataSetChanged();
    }

    public DBORepo getRepo(int position) {
        return repos.get(position);
    }

    // -------------------------------------------------------------------------------
    // Other
    // -------------------------------------------------------------------------------

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView textView;
        private final OnRepoClickListener listener;

        public ViewHolder(View view, OnRepoClickListener listener) {
            super(view);
            this.textView = (TextView) view.findViewById(R.id.repo_text);
            this.listener = listener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onRepoClicked(getAdapterPosition());
        }

        public TextView getTextView() {
            return textView;
        }
    }
}
