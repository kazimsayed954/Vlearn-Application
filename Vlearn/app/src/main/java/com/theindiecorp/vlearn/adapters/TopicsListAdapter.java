package com.theindiecorp.vlearn.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.theindiecorp.vlearn.R;

import java.util.ArrayList;

public class TopicsListAdapter extends RecyclerView.Adapter<TopicsListAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<String> dataSet;

    public void setTopics(ArrayList<String> dataSet){
        this.dataSet = dataSet;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView topicTv;
        private ProgressBar progressBar;

        public MyViewHolder(View itemView){
            super(itemView);
            topicTv = itemView.findViewById(R.id.topic_tv);
            progressBar = itemView.findViewById(R.id.progress_bar);
        }
    }

    public TopicsListAdapter(Context context, ArrayList<String> dataSet){
        this.context = context;
        this.dataSet = dataSet;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.topics_list_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String topic = dataSet.get(position);
        holder.topicTv.setText(topic);
        holder.progressBar.setProgress(100);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

}
