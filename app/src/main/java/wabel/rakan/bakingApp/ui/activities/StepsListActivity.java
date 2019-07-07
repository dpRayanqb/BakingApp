package wabel.rakan.bakingApp.ui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import wabel.rakan.bakingApp.R;
import wabel.rakan.bakingApp.adapters.RecipesStepsAdapter;
import wabel.rakan.bakingApp.models.Baking_app_Ingredients;
import wabel.rakan.bakingApp.models.Recipe;
import wabel.rakan.bakingApp.models.Steps;
import wabel.rakan.bakingApp.ui.fragments.StepDetailsFragment;
import wabel.rakan.bakingApp.widgets.RecipeWidgetProvider;

public class StepsListActivity extends AppCompatActivity
        implements RecipesStepsAdapter.StepsClickListener, StepDetailsFragment.OnStepClickListener {

    public static final String INTENT_EXTRA = "recipe";
    public static final String WIDGET_PREF = "widget_prefs";
    public static final String ID_PREF = "id";
    public static final String NAME_PREF = "name";

    private boolean isTwoPane;
    private int mRecipeId;
    private List<Steps> stepsList;
    private String mRecipeName;

    @BindView(R.id.step_list_rv)
    RecyclerView mRecyclerView;
    private ArrayList<Object> objects;

    private RecipesStepsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps_list);
        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        objects = new ArrayList<>();


        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        assert intent != null;
        if (intent.hasExtra(INTENT_EXTRA)) {
            Recipe recipe = getIntent().getParcelableExtra(INTENT_EXTRA);
            mRecipeId = recipe.getId();
            mRecipeName = recipe.getName();
            List<Baking_app_Ingredients> ingredients = recipe.getIngredients();
            stepsList = recipe.getSteps();
            String mRecipeName = recipe.getName();
            objects.addAll(ingredients);
            objects.addAll(stepsList);
            setTitle(mRecipeName);
        }


        if (findViewById(R.id.step_detail_container) != null) {

            isTwoPane = true;
        }

        initViews();

    }

    private void initViews() {
        mAdapter = new RecipesStepsAdapter(this, objects, isTwoPane, this);
        assert mRecyclerView != null;
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void closeOnError() {
        finish();
    }


    @Override
    public void onStepClick(Steps steps) {
        if (steps != null) {
            if (isTwoPane) {
                StepDetailsFragment fragment = new StepDetailsFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.step_detail_container, fragment)
                        .commit();
            } else {
                Intent intent = new Intent(this, StepsDetailsActivity.class);
                intent.putExtra(StepsDetailsActivity.EXTRA, steps);
                intent.putExtra(StepsDetailsActivity.EXTRA_NAME, mRecipeName);
                intent.putParcelableArrayListExtra(StepsDetailsActivity.EXTRA_LIST,
                        (ArrayList<? extends Parcelable >) stepsList);
                startActivity(intent);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_widget_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add_widget:
                addToPrefsForWidget();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addToPrefsForWidget() {
        SharedPreferences preferences = getApplicationContext().getSharedPreferences(WIDGET_PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(ID_PREF, mRecipeId);
        editor.putString(NAME_PREF, mRecipeName);
        editor.apply();


        RecipeWidgetProvider.updateWidget(this);
    }

    @Override
    public void onPreviousStepClick(Steps steps) {

    }

    @Override
    public void onNextStepClick(Steps steps) {

    }
}
