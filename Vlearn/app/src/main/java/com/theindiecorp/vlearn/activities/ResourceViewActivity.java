package com.theindiecorp.vlearn.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.theindiecorp.vlearn.R;
import com.theindiecorp.vlearn.adapters.TopicsListAdapter;
import com.theindiecorp.vlearn.data.Course;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class ResourceViewActivity extends AppCompatActivity {

    FirebaseFirestore database = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource_view);

        final LinearLayout topicsLayout = findViewById(R.id.topics_layout);
        final TextView categoryTv = findViewById(R.id.category_tv);
        final TextView courseNameTv = findViewById(R.id.course_name_tv);
        final TextView enrollmentTv = findViewById(R.id.enrollment_number_tv);
        final TextView descriptionTv = findViewById(R.id.course_description_tv);
        final TextView keyPointsTv = findViewById(R.id.key_points_tv);

        final String courseId = getIntent().getStringExtra("courseId");

        RecyclerView topicsRecyclerView = findViewById(R.id.recycler_view);
        topicsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        final TopicsListAdapter topicsListAdapter = new TopicsListAdapter(this, new ArrayList<String>());
        topicsRecyclerView.setAdapter(topicsListAdapter);

        ImageButton backBtn = findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        final TextView readMoreTv = findViewById(R.id.read_more_tv);
        readMoreTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                topicsLayout.setVisibility(View.VISIBLE);
                readMoreTv.setVisibility(View.GONE);
            }
        });

        database.collection("courses").document(courseId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(!task.isSuccessful()){
                    Toast.makeText(ResourceViewActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
                }
                else{
                    Map<String, Object> map = task.getResult().getData();
                    Gson gson = new Gson();
                    JsonElement jsonElement = gson.toJsonTree(map);
                    Course course = gson.fromJson(jsonElement, Course.class);
                    categoryTv.setText(course.getCategory());
                    courseNameTv.setText(course.getNameOfCourse());
                    enrollmentTv.setText(course.getEnrollmentNo() + " students");
                    descriptionTv.setText(course.getCourseDescription());
                    keyPointsTv.setText(course.getKeyPoints());
                }
            }
        });

        database.collection("courseTopics").document(courseId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(!task.isSuccessful()){
                    Toast.makeText(ResourceViewActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
                }
                else{
                    ArrayList<String> topics = new ArrayList<>();
                    Toast.makeText(ResourceViewActivity.this, "Found", Toast.LENGTH_SHORT).show();
                    Map<String, Object> map = task.getResult().getData();
                    Set<Map.Entry<String, Object> > st = map.entrySet();
                    for (Map.Entry<String, Object> me : st){
                        topics.add(me.getValue().toString());
                    }
                    topicsListAdapter.setTopics(topics);
                    topicsListAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}