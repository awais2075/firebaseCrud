package com.android.firebasecrud.activity;

import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.firebasecrud.R;
import com.android.firebasecrud._interface.FirebaseResponse;
import com.android.firebasecrud._interface.ItemClickListener;
import com.android.firebasecrud.adapter.RecyclerViewAdapter;
import com.android.firebasecrud.decoration.MyDividerItemDecoration;
import com.android.firebasecrud.firebase.FireBaseDb;
import com.android.firebasecrud.model.Vendor;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class VendorActivity extends AppCompatActivity implements ItemClickListener<Vendor>, FirebaseResponse<Vendor>, View.OnClickListener {

    private DatabaseReference databaseReference;
    private FireBaseDb firebaseDb;
    private RecyclerViewAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        findViewById(R.id.fab).setOnClickListener(this);
        databaseReference = FirebaseDatabase.getInstance().getReference("vendor");
        firebaseDb = new FireBaseDb(this, databaseReference, Vendor.class);


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));


        //get data from fire-base
        firebaseDb.view();
    }

    @Override
    public void onItemClicked(Vendor vendor) {
        firebaseDb.delete(vendor.getVendorId(), vendor);
    }

    @Override
    public void onSuccess(List<Vendor> vendorList) {
        adapter = new RecyclerViewAdapter(this, R.layout.item_vendor, vendorList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onFailure(String message) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab: {
                addVendor();

            }
            break;
        }
    }

    private void addVendor() {
        String vendorId = databaseReference.push().getKey();
        Vendor vendor = new Vendor(vendorId, "Luqman Anjum", "Lahore, Punjab, Pakistan");
        firebaseDb.insert(vendorId, vendor);
    }

    private void updateVendor(Vendor vendor) {
        vendor.setVendorName("Usman Anjum");
        vendor.setVendorAddress("Karachi, Sindh, Pakistan");
        firebaseDb.update(vendor.getVendorId(), vendor);
    }

    private void deleteVendor(Vendor vendor) {
        firebaseDb.delete(vendor.getVendorId(), vendor);
    }
}
