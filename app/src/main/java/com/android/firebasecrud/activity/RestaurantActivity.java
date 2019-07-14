package com.android.firebasecrud.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import com.android.firebasecrud.R;
import com.android.firebasecrud._interface.FirebaseResponse;
import com.android.firebasecrud._interface.ItemClickListener;
import com.android.firebasecrud.adapter.RecyclerViewAdapter;
import com.android.firebasecrud.decoration.MyDividerItemDecoration;
import com.android.firebasecrud.firebase.FireBaseDb;
import com.android.firebasecrud.model.Restaurant;
import com.android.firebasecrud.util.Util;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class RestaurantActivity extends AppCompatActivity implements ItemClickListener<Restaurant>, FirebaseResponse<Restaurant>, View.OnClickListener {


    private DatabaseReference databaseReference;
    private FireBaseDb firebaseDb;
    private RecyclerViewAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        findViewById(R.id.fab).setOnClickListener(this);
        databaseReference = FirebaseDatabase.getInstance().getReference("restaurant");
        firebaseDb = new FireBaseDb(this, databaseReference, Restaurant.class);


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));


        //get data from fire-base
        firebaseDb.view();
    }

    @Override
    public void onItemClicked(Restaurant restaurant) {
        Util.showToast(this, restaurant.getRestaurantName());

        //this line will delete specific restaurant
        deleteRestaurant(restaurant);

    }

    @Override
    public void onSuccess(List<Restaurant> restaurantList) {
        adapter = new RecyclerViewAdapter(this, R.layout.item_restaurant, restaurantList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onFailure(String message) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab: {
                addRestaurant();

            }
            break;
        }
    }

    private void addRestaurant() {
        String restaurantId = databaseReference.push().getKey();
        Restaurant restaurant = new Restaurant(restaurantId, "China Town", "Islamabad, Pakistan");
        firebaseDb.insert(restaurantId, restaurant);
    }

    private void updateRestaurant(Restaurant restaurant) {
        restaurant.setRestaurantName("Pakistan Town");
        restaurant.setRestaurantAddress("Karachi, Pakistan");
        firebaseDb.update(restaurant.getRestaurantId(), restaurant);
    }

    private void deleteRestaurant(Restaurant restaurant) {
        firebaseDb.delete(restaurant.getRestaurantId(), restaurant);
    }
}
