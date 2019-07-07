package wabel.rakan.bakingApp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import wabel.rakan.bakingApp.models.Recipe;

public interface RecipesService {
    @GET("baking.json")
    Call<List<Recipe>> getRecipes();
}