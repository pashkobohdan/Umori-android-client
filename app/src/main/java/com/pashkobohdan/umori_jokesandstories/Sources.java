package com.pashkobohdan.umori_jokesandstories;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.pashkobohdan.umori_jokesandstories.adapters.SourceAdapter;
import com.pashkobohdan.umori_jokesandstories.data.model.SourceModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Sources extends AppCompatActivity {

    RecyclerView recyclerView;
    List<SourceModel> sources;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sources);

        sources = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.sources_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        SourceAdapter adapter = new SourceAdapter(sources);
        recyclerView.setAdapter(adapter);

        final SwipeRefreshLayout refreshLayout = (SwipeRefreshLayout)findViewById(R.id.sources_refresh_swipe_layout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
                refreshLayout.setRefreshing(false);
            }
        });

        refreshData();

        progressBar = (ProgressBar)findViewById(R.id.source_loading_progress_bar);

    }

    private void refreshData(){
        Application.getApi().getSources().enqueue(new Callback<SourceModel[][]>() {
            @Override
            public void onResponse(Call<SourceModel[][]> call, Response<SourceModel[][]> response) {
                sources.clear();

                for(SourceModel[] s : response.body()) {
                    sources.addAll(Arrays.asList(s));
                }

                recyclerView.getAdapter().notifyDataSetChanged();


                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<SourceModel[][]> call, Throwable t) {
                Toast.makeText(Sources.this, t.toString(), Toast.LENGTH_SHORT).show();

                progressBar.setVisibility(View.GONE);
            }
        });
    }
}
