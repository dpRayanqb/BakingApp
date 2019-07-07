package wabel.rakan.bakingApp.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Baking_app_Ingredients implements Parcelable {


    public static final Creator<Baking_app_Ingredients> CREATOR = new Creator<Baking_app_Ingredients>() {
        @Override
        public Baking_app_Ingredients createFromParcel(Parcel in) {
            return new Baking_app_Ingredients(in);
        }

        @Override
        public Baking_app_Ingredients[] newArray(int size) {
            return new Baking_app_Ingredients[size];
        }
    };
    @SerializedName("quantity")
    private float quantity;
    @SerializedName("measure")
    private String measure;
    @SerializedName("ingredient")
    private String ingredient;

    public Baking_app_Ingredients() {
        this.quantity = 0;
        this.measure = "";
        this.ingredient = "";
    }

    protected Baking_app_Ingredients(Parcel in) {
        quantity = in.readFloat();
        measure = in.readString();
        ingredient = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(quantity);
        dest.writeString(measure);
        dest.writeString(ingredient);
    }


    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    @NonNull
    @Override
    public String toString() {
        return "Baking_app_Ingredients{" +
                "quantity=" + quantity +
                ", measure='" + measure + '\'' +
                ", ingredient='" + ingredient + '\'' +
                '}';
    }
}