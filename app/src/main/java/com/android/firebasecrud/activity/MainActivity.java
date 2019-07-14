package com.android.firebasecrud.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import com.android.firebasecrud.R;
import com.android.firebasecrud._interface.FirebaseResponse;
import com.android.firebasecrud.firebase.FireBaseDb;
import com.android.firebasecrud.model.Restaurant;
import com.android.firebasecrud.util.Util;
import com.google.firebase.database.*;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.button_viewRestaurants).setOnClickListener(this);
        findViewById(R.id.button_viewVendors).setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_viewRestaurants:
                startActivity(new Intent(this, RestaurantActivity.class));
                break;
            case R.id.button_viewVendors:
                startActivity(new Intent(this, VendorActivity.class));
                break;
        }
    }
}
