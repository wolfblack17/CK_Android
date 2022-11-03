package com.example.gkandroid;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {
    private ArrayList<Food> foodList;
    private Context context;
    private FoodClickInterface foodClickInterface;
    int lastPos = -1;

    public FoodAdapter(ArrayList<Food> foodList, Context context, FoodClickInterface foodClickInterface) {
        this.foodList = foodList;
        this.context = context;
        this.foodClickInterface = foodClickInterface;
    }

    @NonNull
    @Override
    public FoodAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflating our layout file on below line.
        View view = LayoutInflater.from(context).inflate(R.layout.row_food, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Food food = foodList.get(position);
        holder.txtTenThucAn.setText(food.getTenFood());
        holder.txtGia.setText("Rs. " + food.getGia());
        Log.d("test", "onBindViewHolder: "+URLUtil.isValidUrl(food.getImgFood()));

            if (URLUtil.isValidUrl(food.getImgFood())) {

                Glide.with(context).load(food.getImgFood()).into(holder.imgFood);
            } else {
                holder.imgFood.setImageResource(R.drawable.banhmi);
            }

        //adding animation to recycler view item on below line.
        setAnimation(holder.itemView, position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foodClickInterface.onFoodClick(position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return foodList.size();
    }


    private void setAnimation(View itemView, int position) {
        if (position > lastPos) {
            //on below line we are setting animation.
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            itemView.setAnimation(animation);
            lastPos = position;
        }
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        //creating variable for our image view and text view on below line.
        private ImageView imgFood;
        private TextView txtTenThucAn, txtGia;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //initializing all our variables on below line.
            txtTenThucAn = itemView.findViewById(R.id.tvItemFood);
            imgFood = itemView.findViewById(R.id.Itemimg);
            txtGia = itemView.findViewById(R.id.tvPrice);

        }
    }

    public interface FoodClickInterface {
        void onFoodClick(int position);
    }

}
