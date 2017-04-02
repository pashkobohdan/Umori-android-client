package com.pashkobohdan.umori_jokesandstories;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.pashkobohdan.umori_jokesandstories.adapters.PostAdapter;
import com.pashkobohdan.umori_jokesandstories.data.model.PostModel;
import com.pashkobohdan.umori_jokesandstories.data.model.SourceModel;
import com.pashkobohdan.umori_jokesandstories.data.ormLite.HelperFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Posts extends AppCompatActivity {
    private static final int POST_VIEW_CONTEXT_MENU = 1;
    private static final int INCREMENT_POST_COUNT = 50;

    private String site, name, desc;

    RecyclerView recyclerView;
    List<PostModel> posts = new ArrayList<>();
    PostAdapter adapter;

    LinearLayoutManager layoutManager;


    private int currentPostCount = INCREMENT_POST_COUNT;
    //private AtomicBoolean loadingElsePosts = new AtomicBoolean(false);

    SwipeRefreshLayout refreshLayout;

    private List<SourceModel> sources = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);


        Intent intent = getIntent();
        site = intent.getStringExtra("site");
        name = intent.getStringExtra("name");
        desc = intent.getStringExtra("desc");

        if (site == null || site.equals("") || name == null || name.equals("") || desc == null || desc.equals("")) {
            Toast.makeText(this, R.string.data_error, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(desc);
            getSupportActionBar().setHomeButtonEnabled(true);
        }


        try {
            if (site.equals("random")
                    && name.equals("random")
                    && desc.equals("random")) {
                sources.addAll(HelperFactory.getHelper().getSourceDAO().getAllSources());
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle(R.string.random);
                    getSupportActionBar().setHomeButtonEnabled(true);
                }
            } else {

                for (SourceModel sourceModel : HelperFactory.getHelper().getSourceDAO().getAllSources()) {
                    if (sourceModel.getSite().equals(site)
                            && sourceModel.getName().equals(name)
                            && sourceModel.getDesc().equals(desc)) {
                        sources.add(sourceModel);
                        break;
                    }
                }
            }
            if (sources.size() == 0) {
                Toast.makeText(this, R.string.data_error, Toast.LENGTH_SHORT).show();
                finish();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            finish();
        }


        recyclerView = (RecyclerView) findViewById(R.id.posts_recycler_view);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new PostAdapter(posts);
        recyclerView.setAdapter(adapter);

        registerForContextMenu(recyclerView);

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.posts_refresh_swipe_layout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                refreshData();
            }
        });
        refreshLayout.setRefreshing(true);

//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView,
//                                   int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//
//                int totalItemCount = layoutManager.getItemCount();
//                int lastVisibleItem = layoutManager
//                        .findLastVisibleItemPosition();
//                if (!loadingElsePosts.get() && totalItemCount <= (lastVisibleItem + 5)) {
//
//                    Toast.makeText(Posts.this, "need more !", Toast.LENGTH_SHORT).show();
//                    loadMoreData();
//
//                    loadingElsePosts.set(true);
//                }
//            }
//        });

        refreshData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void refreshData() {
        posts.clear();
        try {
            for(SourceModel sourceModel : sources) {
                posts.addAll(reversePostslist(new ArrayList<>(sourceModel.getPostList())));
                for (PostModel postModel : sourceModel.getPostList()) {
                    postModel.setInDB(true);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        for(final SourceModel sourceModel : sources) {
            Application.getApi().getData(sourceModel.getSite(), sourceModel.getName(), currentPostCount).enqueue(new Callback<List<PostModel>>() {
                @Override
                public void onResponse(Call<List<PostModel>> call, Response<List<PostModel>> response) {
                    //posts.addAll(response.body());
                    for (PostModel postModel : reversePostslist(response.body())) {
                        if (!posts.contains(postModel)) {
                            posts.add(0, postModel);
                            postModel.setSource(sourceModel);
                            try {
                                sourceModel.addPost(postModel);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            //sources.addAll(Arrays.asList(s));
                        }
                    }

                    if(sources.size() > 1) {
                        Collections.shuffle(posts);
                    }
                    recyclerView.getAdapter().notifyDataSetChanged();


                    refreshLayout.setRefreshing(false);
                }

                @Override
                public void onFailure(Call<List<PostModel>> call, Throwable t) {
                    Toast.makeText(Posts.this, R.string.internet_error, Toast.LENGTH_SHORT).show();

                    if(sources.size() > 1) {
                        Collections.shuffle(posts);
                    }
                    recyclerView.getAdapter().notifyDataSetChanged();


                    refreshLayout.setRefreshing(false);
                }
            });
        }
    }

//    private void loadMoreData() {
//        Application.getApi().getData(site, name, currentPostCount + INCREMENT_POST_COUNT).enqueue(new Callback<List<PostModel>>() {
//            @Override
//            public void onResponse(Call<List<PostModel>> call, Response<List<PostModel>> response) {
//                if(response.body().size() <= posts.size()){
//                    Toast.makeText(Posts.this, "More data isn't exist ! old : " + currentPostCount + ", new : "+response.body().size(), Toast.LENGTH_SHORT).show();
//                }else{
//                    posts = response.body();
//                    recyclerView.getAdapter().notifyDataSetChanged();
//                }
//
//                loadingElsePosts.set(false);
//
//                currentPostCount = posts.size();
//            }
//
//            @Override
//            public void onFailure(Call<List<PostModel>> call, Throwable t) {
//                Toast.makeText(Posts.this, "An error occurred during networking", Toast.LENGTH_SHORT).show();
//
//                loadingElsePosts.set(false);
//            }
//        });
//    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(POST_VIEW_CONTEXT_MENU, 1,
                Menu.NONE, R.string.share);
        menu.add(POST_VIEW_CONTEXT_MENU, 2,
                Menu.NONE, R.string.open_in_browser);
        menu.add(POST_VIEW_CONTEXT_MENU, 3,
                Menu.NONE, R.string.copy);
    }

    private List<PostModel> reversePostslist(List<PostModel> list) {
        List<PostModel> result = new LinkedList<>();

        for (int i = list.size() - 1; i >= 0; i--) {
            result.add(list.get(i));
        }
        return result;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getGroupId() == POST_VIEW_CONTEXT_MENU) {
            switch (item.getItemId()) {
                case 1:

                    Intent intent2 = new Intent();
                    intent2.setAction(Intent.ACTION_SEND);
                    intent2.setType("text/plain");
                    intent2.putExtra(Intent.EXTRA_TEXT, "Прочитай : \n" + "http://www.umori.li" + posts.get(adapter.getPosition()).getLink());
                    startActivity(Intent.createChooser(intent2, getString(R.string.share_via)));
                    break;
                case 2:
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.umori.li" + posts.get(adapter.getPosition()).getLink()));
                    startActivity(browserIntent);
                    break;
                case 3:
                    if (copyToClipboard(Posts.this, posts.get(adapter.getPosition()).getElementPureHtml())) {
                        Toast.makeText(this, R.string.copied, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, R.string.error, Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
        return super.onContextItemSelected(item);
    }

    @SuppressLint("NewApi")
    @SuppressWarnings("deprecation")
    public boolean copyToClipboard(Context context, String text) {
        try {
            int sdk = android.os.Build.VERSION.SDK_INT;
            if (sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
                android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context
                        .getSystemService(context.CLIPBOARD_SERVICE);
                clipboard.setText(text);
            } else {
                android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context
                        .getSystemService(context.CLIPBOARD_SERVICE);
                android.content.ClipData clip = android.content.ClipData.newPlainText("simple text", text);
                clipboard.setPrimaryClip(clip);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
