package com.pashkobohdan.umori_jokesandstories.adapters;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pashkobohdan.umori_jokesandstories.R;
import com.pashkobohdan.umori_jokesandstories.data.model.PostModel;

import java.util.List;

/**
 * Created by bohdan on 25.03.17.
 */

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private List<PostModel> posts;

    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public PostAdapter(List<PostModel> posts) {
        this.posts = posts;
    }

    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item, parent, false);
        return new PostAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PostAdapter.ViewHolder holder,final int position) {
        PostModel post = posts.get(position);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.post.setText(Html.fromHtml(post.getElementPureHtml(), Html.FROM_HTML_MODE_LEGACY));
        } else {
            holder.post.setText(Html.fromHtml(post.getElementPureHtml()));
        }

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setPosition(position);
                return false;
            }
        });

        if (post.isInDB()){
            holder.postStatus.setText("Saved");
            holder.postStatus.setTextColor(Color.BLACK);
        }else{
            holder.postStatus.setText("New");
            holder.postStatus.setTextColor(Color.RED);
        }

        holder.postSource.setText(post.getSource().getName());

    }

    @Override
    public void onViewRecycled(ViewHolder holder) {
        holder.itemView.setOnLongClickListener(null);
        super.onViewRecycled(holder);
    }

    @Override
    public int getItemCount() {
        if (posts == null)
            return 0;
        return posts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView post;
        TextView postSource;
        TextView postStatus;

        public ViewHolder(View itemView) {
            super(itemView);
            post = (TextView) itemView.findViewById(R.id.post_text);
            postSource = (TextView) itemView.findViewById(R.id.post_source);
            postStatus = (TextView) itemView.findViewById(R.id.post_status);
        }


    }
}
