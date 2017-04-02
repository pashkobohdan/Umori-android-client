package com.pashkobohdan.umori_jokesandstories;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.pashkobohdan.umori_jokesandstories.adapters.SourceAdapter;
import com.pashkobohdan.umori_jokesandstories.data.model.SourceModel;
import com.pashkobohdan.umori_jokesandstories.data.ormLite.HelperFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Sources extends AppCompatActivity {

    RecyclerView recyclerView;
    List<SourceModel> sources;

    SwipeRefreshLayout refreshLayout;

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

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.sources_refresh_swipe_layout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
                refreshLayout.setRefreshing(false);
            }
        });
        refreshLayout.setRefreshing(true);

        refreshData();

    }

    @Override
    protected void onResume() {
        super.onResume();

        refreshData();
    }

    private void refreshData() {
        sources.clear();
        try {
            sources.addAll(HelperFactory.getHelper().getSourceDAO().getAllSources());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Application.getApi().getSources().enqueue(new Callback<SourceModel[][]>() {
            @Override
            public void onResponse(Call<SourceModel[][]> call, Response<SourceModel[][]> response) {
                //sources.clear();

                for (SourceModel[] s : response.body()) {
                    for (SourceModel sourceModel : s) {
                        if (!sources.contains(sourceModel)) {
                            sources.add(sourceModel);
                            try {
                                HelperFactory.getHelper().getSourceDAO().create(sourceModel);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            //sources.addAll(Arrays.asList(s));
                        }
                    }
                }

                recyclerView.getAdapter().notifyDataSetChanged();
                refreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<SourceModel[][]> call, Throwable t) {
                Toast.makeText(Sources.this, R.string.internet_error, Toast.LENGTH_SHORT).show();
                refreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.source_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.shuffle:
                Intent intent = new Intent(Sources.this, Posts.class);

                intent.putExtra("site", "random");
                intent.putExtra("name", "random");
                intent.putExtra("desc", "random");

                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
