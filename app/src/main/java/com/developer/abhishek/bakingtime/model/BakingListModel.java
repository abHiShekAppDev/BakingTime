package com.developer.abhishek.bakingtime.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BakingListModel implements Parcelable{

    @SerializedName(ApiEndpoint.ID)
    private String id;
    @SerializedName(ApiEndpoint.NAME)
    private String name;
    @SerializedName(ApiEndpoint.INGREDIENTS)
    private List<Ingredients> ingredients = null;
    @SerializedName(ApiEndpoint.STEPS)
    private List<Steps> steps = null;
    @SerializedName(ApiEndpoint.SERVINGS)
    private Integer servings;
    @SerializedName(ApiEndpoint.IMAGE)
    private String image;

    public BakingListModel(String id, String name, List<Ingredients> ingredients, List<Steps> steps, Integer servings, String image) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.servings = servings;
        this.image = image;
    }

    protected BakingListModel(Parcel in) {
        id = in.readString();
        name = in.readString();
        ingredients = in.createTypedArrayList(Ingredients.CREATOR);
        steps = in.createTypedArrayList(Steps.CREATOR);
        if (in.readByte() == 0) {
            servings = null;
        } else {
            servings = in.readInt();
        }
        image = in.readString();
    }

    public static final Creator<BakingListModel> CREATOR = new Creator<BakingListModel>() {
        @Override
        public BakingListModel createFromParcel(Parcel in) {
            return new BakingListModel(in);
        }

        @Override
        public BakingListModel[] newArray(int size) {
            return new BakingListModel[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Ingredients> getIngredients() {
        return ingredients;
    }

    public List<Steps> getSteps() {
        return steps;
    }

    public Integer getServings() {
        return servings;
    }

    public String getImage() {
        return image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeTypedList(ingredients);
        parcel.writeTypedList(steps);
        if (servings == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(servings);
        }
        parcel.writeString(image);
    }
}
