package wabel.rakan.bakingApp.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import wabel.rakan.bakingApp.R;
import wabel.rakan.bakingApp.databinding.IngredientsListItemBinding;
import wabel.rakan.bakingApp.databinding.RecipeStepItemBinding;
import wabel.rakan.bakingApp.models.Baking_app_Ingredients;
import wabel.rakan.bakingApp.models.Recipe;
import wabel.rakan.bakingApp.models.Steps;

public class RecipesStepsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Object> dataSet;
    private Recipe mRecipe;
    private boolean isTWoPane;
    private Context mContext;
    private StepsClickListener mListener;

    public RecipesStepsAdapter( Context mContext, List<Object> dataSet, boolean isTWoPane, StepsClickListener mListener) {
        this.dataSet = dataSet;
        this.isTWoPane = isTWoPane;
        this.mContext = mContext;
        this.mListener = mListener;
    }

    @Override
    public int getItemViewType(int position) {
        if (dataSet.get(position) instanceof Baking_app_Ingredients) {
            return 0;
        } else if (dataSet.get(position) instanceof Steps){
            return 1;
        }
        return -1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == 0) {
            return new IngredientsViewHolder(LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.ingredients_list_item, viewGroup, false));
        } else {
            return new StepsViewHolder(LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.recipe_step_item, viewGroup, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof IngredientsViewHolder) {
            IngredientsViewHolder viewHolder = (IngredientsViewHolder) holder;
            Baking_app_Ingredients ingredients = (Baking_app_Ingredients) dataSet.get(position);
            if (ingredients != null) {
                viewHolder.bind(ingredients);
            }
        } else {
            StepsViewHolder viewHolder = (StepsViewHolder) holder;
            Steps steps = (Steps) dataSet.get(position);

            if(steps != null) {

                viewHolder.bind(steps);
            }

        }
    }

    @Override
    public int getItemCount() {
        return dataSet == null ? 0 : dataSet.size();
    }

    public interface StepsClickListener {
        void onStepClick(Steps steps);
    }

    class IngredientsViewHolder extends RecyclerView.ViewHolder {

        private IngredientsListItemBinding ingredientsBinding;

        IngredientsViewHolder(View itemView) {
            super(itemView);
            ingredientsBinding = DataBindingUtil.bind(itemView);
        }

        void bind(Baking_app_Ingredients ingredients) {
            ingredientsBinding.setIngredient(ingredients);
            ingredientsBinding.executePendingBindings();
        }
    }

    class StepsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private RecipeStepItemBinding stepItemBinding;

        StepsViewHolder(View itemView) {
            super(itemView);
            stepItemBinding = DataBindingUtil.bind(itemView);
            itemView.setOnClickListener(this);
        }

        void bind(Steps steps) {
            stepItemBinding.setStep(steps);
            stepItemBinding.executePendingBindings();
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Steps steps = (Steps) dataSet.get(position);
            mListener.onStepClick(steps);
        }
    }
}