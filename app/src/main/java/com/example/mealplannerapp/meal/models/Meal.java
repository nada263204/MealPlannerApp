package com.example.mealplannerapp.meal.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.SerializedName;
import java.lang.reflect.Field;

@Entity(tableName = "favorite_meals")
public class Meal implements Parcelable {

    @PrimaryKey
    @NonNull
    private String idMeal;

    public Meal() {

    }

    private String strMeal;
    private String strCategory;
    private String strArea;
    private String strInstructions;
    private String strMealThumb;
    private String strYoutube;

    @SerializedName("strIngredient1") private String strIngredient1;
    @SerializedName("strIngredient2") private String strIngredient2;
    @SerializedName("strIngredient3") private String strIngredient3;
    @SerializedName("strIngredient4") private String strIngredient4;
    @SerializedName("strIngredient5") private String strIngredient5;
    @SerializedName("strIngredient6") private String strIngredient6;
    @SerializedName("strIngredient7") private String strIngredient7;
    @SerializedName("strIngredient8") private String strIngredient8;
    @SerializedName("strIngredient9") private String strIngredient9;
    @SerializedName("strIngredient10") private String strIngredient10;
    @SerializedName("strIngredient11") private String strIngredient11;
    @SerializedName("strIngredient12") private String strIngredient12;
    @SerializedName("strIngredient13") private String strIngredient13;
    @SerializedName("strIngredient14") private String strIngredient14;
    @SerializedName("strIngredient15") private String strIngredient15;
    @SerializedName("strIngredient16") private String strIngredient16;
    @SerializedName("strIngredient17") private String strIngredient17;
    @SerializedName("strIngredient18") private String strIngredient18;
    @SerializedName("strIngredient19") private String strIngredient19;
    @SerializedName("strIngredient20") private String strIngredient20;

    @SerializedName("strMeasure1") private String strMeasure1;
    @SerializedName("strMeasure2") private String strMeasure2;
    @SerializedName("strMeasure3") private String strMeasure3;
    @SerializedName("strMeasure4") private String strMeasure4;
    @SerializedName("strMeasure5") private String strMeasure5;
    @SerializedName("strMeasure6") private String strMeasure6;
    @SerializedName("strMeasure7") private String strMeasure7;
    @SerializedName("strMeasure8") private String strMeasure8;
    @SerializedName("strMeasure9") private String strMeasure9;
    @SerializedName("strMeasure10") private String strMeasure10;
    @SerializedName("strMeasure11") private String strMeasure11;
    @SerializedName("strMeasure12") private String strMeasure12;
    @SerializedName("strMeasure13") private String strMeasure13;
    @SerializedName("strMeasure14") private String strMeasure14;
    @SerializedName("strMeasure15") private String strMeasure15;
    @SerializedName("strMeasure16") private String strMeasure16;
    @SerializedName("strMeasure17") private String strMeasure17;
    @SerializedName("strMeasure18") private String strMeasure18;
    @SerializedName("strMeasure19") private String strMeasure19;
    @SerializedName("strMeasure20") private String strMeasure20;

    protected Meal(Parcel in) {
        idMeal = in.readString();
        strMeal = in.readString();
        strCategory = in.readString();
        strArea = in.readString();
        strInstructions = in.readString();
        strMealThumb = in.readString();
        strYoutube = in.readString();
        strIngredient1 = in.readString();
        strIngredient2 = in.readString();
        strIngredient3 = in.readString();
        strIngredient4 = in.readString();
        strIngredient5 = in.readString();
        strIngredient6 = in.readString();
        strIngredient7 = in.readString();
        strIngredient8 = in.readString();
        strIngredient9 = in.readString();
        strIngredient10 = in.readString();
        strIngredient11 = in.readString();
        strIngredient12 = in.readString();
        strIngredient13 = in.readString();
        strIngredient14 = in.readString();
        strIngredient15 = in.readString();
        strIngredient16 = in.readString();
        strIngredient17 = in.readString();
        strIngredient18 = in.readString();
        strIngredient19 = in.readString();
        strIngredient20 = in.readString();
        strMeasure1 = in.readString();
        strMeasure2 = in.readString();
        strMeasure3 = in.readString();
        strMeasure4 = in.readString();
        strMeasure5 = in.readString();
        strMeasure6 = in.readString();
        strMeasure7 = in.readString();
        strMeasure8 = in.readString();
        strMeasure9 = in.readString();
        strMeasure10 = in.readString();
        strMeasure11 = in.readString();
        strMeasure12 = in.readString();
        strMeasure13 = in.readString();
        strMeasure14 = in.readString();
        strMeasure15 = in.readString();
        strMeasure16 = in.readString();
        strMeasure17 = in.readString();
        strMeasure18 = in.readString();
        strMeasure19 = in.readString();
        strMeasure20 = in.readString();
    }

    public static final Creator<Meal> CREATOR = new Creator<Meal>() {
        @Override
        public Meal createFromParcel(Parcel in) {
            return new Meal(in);
        }

        @Override
        public Meal[] newArray(int size) {
            return new Meal[size];
        }
    };

    public String getIdMeal() { return idMeal; }
    public String getStrMeal() { return strMeal; }
    public String getStrCategory() { return strCategory; }
    public String getStrArea() { return strArea; }
    public String getStrInstructions() { return strInstructions; }
    public String getStrMealThumb() { return strMealThumb; }
    public String getStrYoutube() { return strYoutube; }

    public String getStrIngredient1() { return strIngredient1; }
    public String getStrIngredient2() { return strIngredient2; }
    public String getStrIngredient3() { return strIngredient3; }
    public String getStrIngredient4() { return strIngredient4; }
    public String getStrIngredient5() { return strIngredient5; }
    public String getStrIngredient6() { return strIngredient6; }
    public String getStrIngredient7() { return strIngredient7; }
    public String getStrIngredient8() { return strIngredient8; }
    public String getStrIngredient9() { return strIngredient9; }
    public String getStrIngredient10() { return strIngredient10; }
    public String getStrIngredient11() { return strIngredient11; }
    public String getStrIngredient12() { return strIngredient12; }
    public String getStrIngredient13() { return strIngredient13; }
    public String getStrIngredient14() { return strIngredient14; }
    public String getStrIngredient15() { return strIngredient15; }
    public String getStrIngredient16() { return strIngredient16; }
    public String getStrIngredient17() { return strIngredient17; }
    public String getStrIngredient18() { return strIngredient18; }
    public String getStrIngredient19() { return strIngredient19; }
    public String getStrIngredient20() { return strIngredient20; }

    public String getStrMeasure1() { return strMeasure1; }
    public String getStrMeasure2() { return strMeasure2; }
    public String getStrMeasure3() { return strMeasure3; }
    public String getStrMeasure4() { return strMeasure4; }
    public String getStrMeasure5() { return strMeasure5; }
    public String getStrMeasure6() { return strMeasure6; }
    public String getStrMeasure7() { return strMeasure7; }
    public String getStrMeasure8() { return strMeasure8; }
    public String getStrMeasure9() { return strMeasure9; }
    public String getStrMeasure10() { return strMeasure10; }
    public String getStrMeasure11() { return strMeasure11; }
    public String getStrMeasure12() { return strMeasure12; }
    public String getStrMeasure13() { return strMeasure13; }
    public String getStrMeasure14() { return strMeasure14; }
    public String getStrMeasure15() { return strMeasure15; }
    public String getStrMeasure16() { return strMeasure16; }
    public String getStrMeasure17() { return strMeasure17; }
    public String getStrMeasure18() { return strMeasure18; }
    public String getStrMeasure19() { return strMeasure19; }
    public String getStrMeasure20() { return strMeasure20; }

    // Setters (needed for Room)
    public void setIdMeal(String idMeal) { this.idMeal = idMeal; }
    public void setStrMeal(String strMeal) { this.strMeal = strMeal; }
    public void setStrCategory(String strCategory) { this.strCategory = strCategory; }
    public void setStrArea(String strArea) { this.strArea = strArea; }
    public void setStrInstructions(String strInstructions) { this.strInstructions = strInstructions; }
    public void setStrMealThumb(String strMealThumb) { this.strMealThumb = strMealThumb; }
    public void setStrYoutube(String strYoutube) { this.strYoutube = strYoutube; }

    // Dynamic ingredient getter
    public String getIngredient(int index) {
        try {
            Field field = this.getClass().getDeclaredField("strIngredient" + index);
            return (String) field.get(this);
        } catch (Exception e) {
            return null;
        }
    }

    // Dynamic measure getter
    public String getMeasure(int index) {
        try {
            Field field = this.getClass().getDeclaredField("strMeasure" + index);
            return (String) field.get(this);
        } catch (Exception e) {
            return null;
        }
    }

    public void setStrIngredient1(String strIngredient1) { this.strIngredient1 = strIngredient1; }
    public void setStrIngredient2(String strIngredient2) { this.strIngredient2 = strIngredient2; }
    public void setStrIngredient3(String strIngredient3) { this.strIngredient3 = strIngredient3; }
    public void setStrIngredient4(String strIngredient4) { this.strIngredient4 = strIngredient4; }
    public void setStrIngredient5(String strIngredient5) { this.strIngredient5 = strIngredient5; }
    public void setStrIngredient6(String strIngredient6) { this.strIngredient6 = strIngredient6; }
    public void setStrIngredient7(String strIngredient7) { this.strIngredient7 = strIngredient7; }
    public void setStrIngredient8(String strIngredient8) { this.strIngredient8 = strIngredient8; }
    public void setStrIngredient9(String strIngredient9) { this.strIngredient9 = strIngredient9; }
    public void setStrIngredient10(String strIngredient10) { this.strIngredient10 = strIngredient10; }
    public void setStrIngredient11(String strIngredient11) { this.strIngredient11 = strIngredient11; }
    public void setStrIngredient12(String strIngredient12) { this.strIngredient12 = strIngredient12; }
    public void setStrIngredient13(String strIngredient13) { this.strIngredient13 = strIngredient13; }
    public void setStrIngredient14(String strIngredient14) { this.strIngredient14 = strIngredient14; }
    public void setStrIngredient15(String strIngredient15) { this.strIngredient15 = strIngredient15; }
    public void setStrIngredient16(String strIngredient16) { this.strIngredient16 = strIngredient16; }
    public void setStrIngredient17(String strIngredient17) { this.strIngredient17 = strIngredient17; }
    public void setStrIngredient18(String strIngredient18) { this.strIngredient18 = strIngredient18; }
    public void setStrIngredient19(String strIngredient19) { this.strIngredient19 = strIngredient19; }
    public void setStrIngredient20(String strIngredient20) { this.strIngredient20 = strIngredient20; }

    // Setters for Measures
    public void setStrMeasure1(String strMeasure1) { this.strMeasure1 = strMeasure1; }
    public void setStrMeasure2(String strMeasure2) { this.strMeasure2 = strMeasure2; }
    public void setStrMeasure3(String strMeasure3) { this.strMeasure3 = strMeasure3; }
    public void setStrMeasure4(String strMeasure4) { this.strMeasure4 = strMeasure4; }
    public void setStrMeasure5(String strMeasure5) { this.strMeasure5 = strMeasure5; }
    public void setStrMeasure6(String strMeasure6) { this.strMeasure6 = strMeasure6; }
    public void setStrMeasure7(String strMeasure7) { this.strMeasure7 = strMeasure7; }
    public void setStrMeasure8(String strMeasure8) { this.strMeasure8 = strMeasure8; }
    public void setStrMeasure9(String strMeasure9) { this.strMeasure9 = strMeasure9; }
    public void setStrMeasure10(String strMeasure10) { this.strMeasure10 = strMeasure10; }
    public void setStrMeasure11(String strMeasure11) { this.strMeasure11 = strMeasure11; }
    public void setStrMeasure12(String strMeasure12) { this.strMeasure12 = strMeasure12; }
    public void setStrMeasure13(String strMeasure13) { this.strMeasure13 = strMeasure13; }
    public void setStrMeasure14(String strMeasure14) { this.strMeasure14 = strMeasure14; }
    public void setStrMeasure15(String strMeasure15) { this.strMeasure15 = strMeasure15; }
    public void setStrMeasure16(String strMeasure16) { this.strMeasure16 = strMeasure16; }
    public void setStrMeasure17(String strMeasure17) { this.strMeasure17 = strMeasure17; }
    public void setStrMeasure18(String strMeasure18) { this.strMeasure18 = strMeasure18; }
    public void setStrMeasure19(String strMeasure19) { this.strMeasure19 = strMeasure19; }
    public void setStrMeasure20(String strMeasure20) { this.strMeasure20 = strMeasure20; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(idMeal);
        parcel.writeString(strMeal);
        parcel.writeString(strCategory);
        parcel.writeString(strArea);
        parcel.writeString(strInstructions);
        parcel.writeString(strMealThumb);
        parcel.writeString(strYoutube);
        parcel.writeString(strIngredient1);
        parcel.writeString(strIngredient2);
        parcel.writeString(strIngredient3);
        parcel.writeString(strIngredient4);
        parcel.writeString(strIngredient5);
        parcel.writeString(strIngredient6);
        parcel.writeString(strIngredient7);
        parcel.writeString(strIngredient8);
        parcel.writeString(strIngredient9);
        parcel.writeString(strIngredient10);
        parcel.writeString(strIngredient11);
        parcel.writeString(strIngredient12);
        parcel.writeString(strIngredient13);
        parcel.writeString(strIngredient14);
        parcel.writeString(strIngredient15);
        parcel.writeString(strIngredient16);
        parcel.writeString(strIngredient17);
        parcel.writeString(strIngredient18);
        parcel.writeString(strIngredient19);
        parcel.writeString(strIngredient20);
        parcel.writeString(strMeasure1);
        parcel.writeString(strMeasure2);
        parcel.writeString(strMeasure3);
        parcel.writeString(strMeasure4);
        parcel.writeString(strMeasure5);
        parcel.writeString(strMeasure6);
        parcel.writeString(strMeasure7);
        parcel.writeString(strMeasure8);
        parcel.writeString(strMeasure9);
        parcel.writeString(strMeasure10);
        parcel.writeString(strMeasure11);
        parcel.writeString(strMeasure12);
        parcel.writeString(strMeasure13);
        parcel.writeString(strMeasure14);
        parcel.writeString(strMeasure15);
        parcel.writeString(strMeasure16);
        parcel.writeString(strMeasure17);
        parcel.writeString(strMeasure18);
        parcel.writeString(strMeasure19);
        parcel.writeString(strMeasure20);
    }
}
