package com.example.gkandroid;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailFood extends AppCompatActivity {
    ImageView imageViewChiTiet, btnPlus, btnMinus;
    TextView tvNameFoodCT, tvNumberOrder, tvPriceCT;
    private int numberOrder = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_food);

        anhxa();
        Intent intent = getIntent();

        if(intent != null){
            Food food = (Food) intent.getParcelableExtra("food");
            imageViewChiTiet.setImageResource(R.drawable.banhmi);
            tvNameFoodCT.setText(food.getTenFood());
            tvPriceCT.setText("$" + food.getGia());

            btnPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    numberOrder = numberOrder + 1;
                    tvNumberOrder.setText(String.valueOf(numberOrder));
                    tvPriceCT.setText(String.valueOf("$" + numberOrder * Double.valueOf(food.getGia())));
                }
            });
            btnMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (numberOrder > 1){
                        numberOrder = numberOrder - 1;
                    }
                    tvNumberOrder.setText(String.valueOf(numberOrder));
                    tvPriceCT.setText(String.valueOf("$" + numberOrder * Double.valueOf(food.getGia())));
                }
            });

            imageViewChiTiet.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    return false;
                }
            });
        }
    }

    private void anhxa() {
        imageViewChiTiet = (ImageView) findViewById(R.id.imageViewDetail);
        tvNameFoodCT = (TextView) findViewById(R.id.tvNameFoodDetail);
        tvPriceCT = (TextView) findViewById(R.id.tvGia);
        tvNumberOrder = (TextView) findViewById(R.id.tvSoLuong);
        btnMinus = (ImageView) findViewById(R.id.imgMinus);
        btnPlus = (ImageView) findViewById(R.id.imgPlus);
    }
}