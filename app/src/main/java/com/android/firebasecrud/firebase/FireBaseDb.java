package com.android.firebasecrud.firebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.android.firebasecrud._interface.FirebaseOperations;
import com.android.firebasecrud._interface.FirebaseResponse;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FireBaseDb implements FirebaseOperations, ValueEventListener, DatabaseReference.CompletionListener {

    private DatabaseReference databaseReference;
    private FirebaseResponse firebaseResponse;
    private Class refClass;

    public FireBaseDb(FirebaseResponse firebaseResponse, DatabaseReference databaseReference, Class refClass) {
        this.firebaseResponse = firebaseResponse;
        this.databaseReference = databaseReference;
        this.refClass = refClass;
    }

    @Override
    public boolean insert(String key, Object value) {
        databaseReference.child(key).setValue(value, this);
        return true;
    }

    @Override
    public boolean delete(String key, Object value) {
        databaseReference.child(key).removeValue(this);
        return false;
    }

    @Override
    public boolean update(String key, Object value) {
        databaseReference.child(key).setValue(value, this);
        return true;
    }

    @Override
    public List view() {
        databaseReference.addValueEventListener(this);
        return null;
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        List<Object> list = new ArrayList<>();
        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
            Object object = postSnapshot.getValue(refClass);
            list.add(object);
        }
        firebaseResponse.onSuccess(list);
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {
        firebaseResponse.onFailure(databaseError.getMessage());
    }

    @Override
    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {

    }
}
