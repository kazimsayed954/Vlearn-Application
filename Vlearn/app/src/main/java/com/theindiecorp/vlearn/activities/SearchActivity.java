package com.theindiecorp.vlearn.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.theindiecorp.vlearn.R;
import com.theindiecorp.vlearn.adapters.SearchResourceAdapter;
import com.theindiecorp.vlearn.data.Course;

import java.util.ArrayList;
import java.util.Map;

public class SearchActivity extends AppCompatActivity {

    private FirebaseFirestore database = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        SearchView searchView = findViewById(R.id.search_view);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL));

        final SearchResourceAdapter adapter = new SearchResourceAdapter(this, new ArrayList<Course>());
        recyclerView.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                database.collection("courses")
                        .whereEqualTo("nameOfCourse", query + "\uf8ff")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(SearchActivity.this, "Found it", Toast.LENGTH_SHORT).show();
                                    ArrayList<Course> courses = new ArrayList<>();
                                    for(DocumentSnapshot doc : task.getResult()){
                                        Course e = doc.toObject(Course.class);
                                        courses.add(e);
                                    }
                                    adapter.setCourses(courses);
                                    adapter.notifyDataSetChanged();
                                }
                                else{
                                    Toast.makeText(SearchActivity.this, "Something got wrong", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                database.collection("courses")
                        .whereEqualTo("nameOfCourse", newText)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(SearchActivity.this, "Found it", Toast.LENGTH_SHORT).show();
                                    ArrayList<Course> courses = new ArrayList<>();
                                    for(DocumentSnapshot doc : task.getResult()){
                                        Course e = doc.toObject(Course.class);
                                        courses.add(e);
                                    }
                                    adapter.setCourses(courses);
                                    adapter.notifyDataSetChanged();
                                }
                                else{
                                    Toast.makeText(SearchActivity.this, "Something got wrong", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                return false;
            }
        });

    }
}