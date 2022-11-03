package com.example.gkandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class EditFoodActivity extends AppCompatActivity {

    //creating variables for our edit text, firebase database, database reference, course rv modal,progress bar.
    private EditText editNameEdt, editImgLink, editPrice;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Food food;
    private ProgressBar loadingPB;
    //creating a string for our course id.
    private String foodID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_food);
        //initializing all our variables on below line.
        Button updateFood = findViewById(R.id.idBtnUpdate);
        editNameEdt = findViewById(R.id.edtEditName);
        editImgLink = findViewById(R.id.edtEditLinkImg);
        editPrice = findViewById(R.id.edtEditGia);

        loadingPB = findViewById(R.id.idPBLoading);
        firebaseDatabase = FirebaseDatabase.getInstance();
        //on below line we are getting our modal class on which we have passed.
        Intent intent = getIntent();
        food = intent.getParcelableExtra("food");
        Button deleteCourseBtn = findViewById(R.id.idBtndeleteFood);

        if (food != null) {
            //on below line we are setting data to our edit text from our modal class.
            editNameEdt.setText(food.getTenFood());
            editImgLink.setText(food.getImgFood());
            editPrice.setText("" + food.getGia());
            foodID = food.getIdFood();
        }

        //on below line we are initialing our database reference and we are adding a child as our course id.
        databaseReference = firebaseDatabase.getReference("Food").child(foodID);
        //on below line we are adding click listener for our add course button.
        updateFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //on below line we are making our progress bar as visible.
                loadingPB.setVisibility(View.VISIBLE);
                //on below line we are getting data from our edit text.
                String eName = editNameEdt.getText().toString();
                String Img = editImgLink.getText().toString();
                String Price = editPrice.getText().toString();

                //on below line we are creating a map for passing a data using key and value pair.
                Map<String, Object> map = new HashMap<>();
                map.put("tenFood", eName);
                map.put("imgFood", Img);
                map.put("gia", Price);
                map.put("idFood", foodID);

                //on below line we are calling a database reference on add value event listener and on data change method
                databaseReference.updateChildren(map);
                //on below line we are displaying a toast message.
                Toast.makeText(EditFoodActivity.this, "Food Updated Success", Toast.LENGTH_SHORT).show();
                //opening a new activity after updating our coarse.
                startActivity(new Intent(EditFoodActivity.this, HomeActivity.class));
                return;
            }

        });

        //adding a click listener for our delete course button.
        deleteCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //calling a method to delete a course.
                deleteCourse();
            }
        });

    }

    private void deleteCourse() {
        //on below line calling a method to delete the course.
        databaseReference.removeValue();
        //displaying a toast message on below line.
        Toast.makeText(this, "Course Deleted..", Toast.LENGTH_SHORT).show();
        //opening a main activity on below line.
        startActivity(new Intent(EditFoodActivity.this, HomeActivity.class));
    }
}