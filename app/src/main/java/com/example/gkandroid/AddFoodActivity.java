package com.example.gkandroid;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

public class AddFoodActivity extends AppCompatActivity {

    //creating variables for our button, edit text,firebase database, database refrence, progress bar.
    private Button addCourseBtn;
    private EditText FoodTen, FoodImg,FoodGia;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private ProgressBar loadingPB;
    private String FoodID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);
        //initializing all our variables.
        addCourseBtn = findViewById(R.id.idBtnAddFood);
        FoodTen = findViewById(R.id.edtName);
        FoodImg = findViewById(R.id.edtLinkImg);
        FoodGia = findViewById(R.id.edtGia);
        loadingPB = findViewById(R.id.idPBLoading);
        firebaseDatabase = FirebaseDatabase.getInstance();
        //on below line creating our database reference.
        databaseReference = firebaseDatabase.getReference("Food");
        //adding click listener for our add course button.
        addCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingPB.setVisibility(View.VISIBLE);
                //getting data from our edit text.
                String Name = FoodTen.getText().toString();
                String Foodanh = FoodImg.getText().toString();
                String FoodPrice = FoodGia.getText().toString();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddGHHmmssz");
                FoodID = sdf.format(new Date()).trim();
                //on below line we are passing all data to our modal class.
                Food food = new Food(Name,Foodanh,FoodPrice,FoodID);
                //on below line we are calling a add value event to pass data to firebase database.
                databaseReference.child(FoodID).setValue(food);
                        //displaying a toast message.
                        Toast.makeText(getApplicationContext(), "Food Added..", Toast.LENGTH_SHORT).show();
                        //starting a main activity.
                        startActivity(new Intent(AddFoodActivity.this, HomeActivity.class));
            }
        });

    }
}