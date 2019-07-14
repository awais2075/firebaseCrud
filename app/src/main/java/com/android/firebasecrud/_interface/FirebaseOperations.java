package com.android.firebasecrud._interface;

import com.android.firebasecrud.model.Restaurant;

import java.util.List;

public interface FirebaseOperations<Model> {

    boolean insert(String key, Model value);

    boolean delete(String key, Model value);

    boolean update(String key, Model value);

    List<Model> view();

}
