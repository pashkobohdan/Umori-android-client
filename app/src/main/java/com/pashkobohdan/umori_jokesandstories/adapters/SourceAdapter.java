package com.pashkobohdan.umori_jokesandstories.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pashkobohdan.umori_jokesandstories.Posts;
import com.pashkobohdan.umori_jokesandstories.R;
import com.pashkobohdan.umori_jokesandstories.data.model.SourceModel;

import java.util.List;

/**
 * Created by bohdan on 25.03.17.
 */

public class SourceAdapter extends RecyclerView.Adapter<SourceAdapter.ViewHolder> {

    private List<SourceModel> posts;

    public SourceAdapter(List<SourceModel> posts) {
        this.posts = posts;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final SourceModel post = posts.get(position);
        holder.site.setText(post.getDesc());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Posts.class);

                intent.putExtra("site", post.getSite());
                intent.putExtra("name", post.getName());
                intent.putExtra("desc", post.getDesc());

                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (posts == null)
            return 0;
        return posts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView site;

        public ViewHolder(View itemView) {
            super(itemView);
            site = (TextView) itemView.findViewById(R.id.post_text);
        }
    }
}