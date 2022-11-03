package com.example.gkandroid;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Food implements Parcelable {

    private String tenFood;
    private String imgFood;
    private String gia;
    private String idFood;

    public Food() {
    }

    public Food(String tenFood, String imgFood, String gia, String idFood) {
        this.tenFood = tenFood;
        this.imgFood = imgFood;
        this.gia = gia;
        this.idFood = idFood;
    }

    public String getTenFood() {
        return tenFood;
    }

    public void setTenFood(String tenFood) {
        this.tenFood = tenFood;
    }

    public String getImgFood() {
        return imgFood;
    }

    public void setImgFood(String imgFood) {
        this.imgFood = imgFood;
    }

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }

    public String getIdFood() {
        return idFood;
    }

    public void setIdFood(String idFood) {
        this.idFood = idFood;
    }

    protected Food(Parcel in) {
        tenFood = in.readString();
        idFood = in.readString();
        imgFood = in.readString();
        gia = in.readString();
    }

    public static final Parcelable.Creator<Food> CREATOR = new Parcelable.Creator<Food>() {
        @Override
        public Food createFromParcel(Parcel in) {
            return new Food(in);
        }

        @Override
        public Food[] newArray(int size) {
            return new Food[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(tenFood);
        parcel.writeString(idFood);
        parcel.writeString(imgFood);
        parcel.writeString(gia);
    }
}
