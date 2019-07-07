package wabel.rakan.bakingApp.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import wabel.rakan.bakingApp.R;
import wabel.rakan.bakingApp.adapters.RecipesAdapter;
import wabel.rakan.bakingApp.RecipesService;
import wabel.rakan.bakingApp.RetrofitClient;
import wabel.rakan.bakingApp.models.Recipe;
import wabel.rakan.bakingApp.ui.activities.StepsListActivity;
import wabel.rakan.bakingApp.SpacingItemDecoration;

public class RecipesFragment extends Fragment implements RecipesAdapter.RecipeClickListener {

    private Context mContext;
    private RecipesAdapter adapter;
    private List<Recipe> mRecipeList;

    public static final String TAG = RecipesFragment.class.getSimpleName();
    private static final String RECIPES_KEY = "recipes";

    @BindView(R.id.recipeRecycler)
    RecyclerView mRecipeRecycler;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;

    public RecipesFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recipes, container, false);
        ButterKnife.bind(this, view);

        mContext = getActivity();
        initViews();

        if (savedInstanceState != null && savedInstanceState.containsKey(RECIPES_KEY)) {
            mSwipeRefresh.setRefreshing(true);
            loadRecipes();
        } else {

        }


        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                refresh();
            }
        });

        loadRecipes();

        return view;

    }

    private void refresh() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                loadRecipes();

                mSwipeRefresh.setRefreshing(false);
            }
        },3000);
    }

    private void initViews() {
        adapter = new RecipesAdapter(mContext, this);
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mRecipeRecycler.setLayoutManager(layoutManager);
        mRecipeRecycler.setHasFixedSize(true);
        mRecipeRecycler.addItemDecoration(new SpacingItemDecoration((int)
                getResources().getDimension(R.dimen.margin_medium)));
        mRecipeRecycler.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener());
        mRecipeRecycler.setAdapter(adapter);
    }


    private void loadRecipes() {

        RecipesService service = RetrofitClient.getClient().create(RecipesService.class);
        Call<List<Recipe>> call = service.getRecipes();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                if (response.isSuccessful()) {
                    mRecipeList = response.body();

                    adapter.setData(mRecipeList);
                    adapter.notifyDataSetChanged();
                    mSwipeRefresh.setRefreshing(false);
                } else {
                    getActivity().finish();
                }
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {

                Log.e(TAG, t.getMessage());
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onRecipeClick(Recipe recipe) {
        Intent intent = new Intent(mContext, StepsListActivity.class);
        intent.putExtra(StepsListActivity.INTENT_EXTRA, recipe);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }
}
