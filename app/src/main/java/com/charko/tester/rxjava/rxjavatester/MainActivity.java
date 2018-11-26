package com.charko.tester.rxjava.rxjavatester;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.charko.tester.rxjava.rxjavatester.activity.BooksActivity;
import com.charko.tester.rxjava.rxjavatester.activity.ColorsActivity;
import com.charko.tester.rxjava.rxjavatester.activity.SchedulerActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnColors = findViewById(R.id.first);
        Button btnBooks = findViewById(R.id.second);
        Button btnScheduler = findViewById(R.id.third);


        btnColors.setOnClickListener(view -> {
            startActivity(new Intent(this, ColorsActivity.class));
        });

        btnBooks.setOnClickListener(view -> {
            startActivity(new Intent(this, BooksActivity.class));
        });


        btnScheduler.setOnClickListener(view -> {
            startActivity(new Intent(this, SchedulerActivity.class));
        });
    }
}
