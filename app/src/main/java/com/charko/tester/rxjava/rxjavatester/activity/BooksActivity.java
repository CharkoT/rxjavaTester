package com.charko.tester.rxjava.rxjavatester.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.charko.tester.rxjava.rxjavatester.R;
import com.charko.tester.rxjava.rxjavatester.adapter.SimpleStringAdapter;
import com.charko.tester.rxjava.rxjavatester.client.RestClient;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class BooksActivity extends AppCompatActivity {

    private Disposable disposable;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private SimpleStringAdapter simpleStringAdapter;
    private RestClient restClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);
        restClient = new RestClient(this);
        configureLayout();
        createObservable();

    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if (disposable != null && !disposable.isDisposed()) {
//            disposable.dispose();
//        }
//    }

    private void createObservable() {
        Observable<List<String>> booksObservable =
                Observable.fromCallable(() -> restClient.getFavoriteBooks());
        disposable = booksObservable.
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(strings -> displayBooks(strings));
    }


    private void displayBooks(List<String> books) {
        simpleStringAdapter.setStrings(books);
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    private void configureLayout() {
        setContentView(R.layout.activity_books);
        progressBar = findViewById(R.id.loader);
        recyclerView = findViewById(R.id.books_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        simpleStringAdapter = new SimpleStringAdapter(this);
        recyclerView.setAdapter(simpleStringAdapter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
