package com.example.kp.entities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kp.R;

import java.util.ArrayList;
import java.util.List;

public class IssueAdapter extends RecyclerView.Adapter<IssueAdapter.ViewHolder> {
    private static List<Issue> tasks = new ArrayList<>();
    private final LayoutInflater inflater;
    private final SelectListener listener;


    public IssueAdapter(Context context, List<Issue> tasks, SelectListener listener) {
        IssueAdapter.tasks = tasks;
        this.inflater = LayoutInflater.from(context);
        this.listener = listener;
    }

    @NonNull
    @Override
    public IssueAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.issue_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IssueAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final Issue issue = tasks.get(position);
        holder.issueIdView.setText(issue.getId());
        holder.issueNameView.setText(issue.getName());
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClicked(tasks.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView issueIdView, issueNameView;
        public ConstraintLayout constraintLayout;

        //        View mView;
        ViewHolder(View view) {
            super(view);
            issueNameView = view.findViewById(R.id.issueId);
            issueIdView = view.findViewById(R.id.issueName);
            constraintLayout = view.findViewById(R.id.issueItem);
        }
    }
}
