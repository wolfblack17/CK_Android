package com.example.gkandroid.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gkandroid.AddFoodActivity;
import com.example.gkandroid.DetailFood;
import com.example.gkandroid.EditFoodActivity;
import com.example.gkandroid.Food;
import com.example.gkandroid.FoodAdapter;
import com.example.gkandroid.R;
import com.example.gkandroid.TruyenFood;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements FoodAdapter.FoodClickInterface {
    FloatingActionButton fab;
    ArrayList<Food> arrayFood = null;
    FoodAdapter adapter;
    TruyenFood truyenFood;
    RecyclerView rycFood;

    private ProgressBar loadingPB;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        truyenFood = (TruyenFood) getActivity();
        return inflater.inflate(R.layout.fragment_home, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        anhxa(view);
        firebaseDatabase = FirebaseDatabase.getInstance();
        arrayFood = new ArrayList<>();
        //on below line we are getting database reference.
        databaseReference = firebaseDatabase.getReference("Food");
        addfood();
        adapter = new FoodAdapter(arrayFood, requireContext(), this::onFoodClick);
        rycFood.setLayoutManager(new LinearLayoutManager(requireContext()));
        rycFood.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        getFood();
    }

    private void getFood() {
        //on below line clearing our list.
        arrayFood.clear();
        //on below line we are calling add child event listener method to read the data.
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                //on below line we are hiding our progress bar.
                loadingPB.setVisibility(View.GONE);
                //adding snapshot to our array list on below line.
                arrayFood.add(snapshot.getValue(Food.class));
                //notifying our adapter that data has changed.
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                //this method is called when new child is added we are notifying our adapter and making progress bar visibility as gone.
                loadingPB.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                //notifying our adapter when child is removed.
                adapter.notifyDataSetChanged();
                loadingPB.setVisibility(View.GONE);

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                //notifying our adapter when child is moved.
                adapter.notifyDataSetChanged();
                loadingPB.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void addfood() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(requireActivity(), AddFoodActivity.class));
            }
        });
    }

    private void anhxa(View view) {
        loadingPB = view.findViewById(R.id.idPBLoading);
        rycFood = view.findViewById(R.id.rycListFood);
        fab = view.findViewById(R.id.floating_action_button);
        arrayFood = new ArrayList<>();
    }

    @Override
    public void onFoodClick(int position) {
        displayBottomSheet(arrayFood.get(position));
    }

    private void displayBottomSheet(Food modal) {
        //on below line we are creating our bottom sheet dialog.
        final BottomSheetDialog bottomSheetTeachersDialog = new BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme);
        //on below line we are inflating our layout file for our bottom sheet.
        View layout = LayoutInflater.from(requireContext()).inflate(R.layout.bottom_sheet_layout, null);
        //setting content view for bottom sheet on below line.
        bottomSheetTeachersDialog.setContentView(layout);
        //on below line we are setting a cancelable
        bottomSheetTeachersDialog.setCancelable(false);
        bottomSheetTeachersDialog.setCanceledOnTouchOutside(true);
        //calling a method to display our bottom sheet.
        bottomSheetTeachersDialog.show();
        //on below line we are creating variables for our text view and image view inside bottom sheet
        //and initialing them with their ids.
        TextView courseNameTV = layout.findViewById(R.id.idTVFoodName);
        TextView priceTV = layout.findViewById(R.id.idTVCoursePrice);
        ImageView courseIV = layout.findViewById(R.id.idIVCourse);
        //on below line we are setting data to different views on below line.
        courseNameTV.setText(modal.getTenFood());
        priceTV.setText("$." + modal.getGia());
        if (URLUtil.isValidUrl(modal.getImgFood())) {

            Glide.with(requireContext()).load(modal.getImgFood()).into(courseIV);
        } else {
            courseIV.setImageResource(R.drawable.banhmi);
        }
        Button viewBtn = layout.findViewById(R.id.idBtnVIewDetails);
        Button editBtn = layout.findViewById(R.id.idBtnEditFood);

        //adding on click listener for our edit button.
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //on below line we are opening our EditCourseActivity on below line.
                Intent i = new Intent(requireActivity(), EditFoodActivity.class);
                //on below line we are passing our course modal
                i.putExtra("food", modal);
                startActivity(i);
                bottomSheetTeachersDialog.dismiss();
            }
        });
        //adding click listener for our view button on below line.
        viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //on below line we are navigating to browser for displaying course details from its url
                Intent intent = new Intent(requireActivity(), DetailFood.class);
                intent.putExtra("food", modal);
                startActivity(intent);
                bottomSheetTeachersDialog.dismiss();
            }
        });

    }
}
