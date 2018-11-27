package com.charko.tester.rxjava.rxjavatester.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.charko.tester.rxjava.rxjavatester.R;
import com.charko.tester.rxjava.rxjavatester.adapter.SimpleStringAdapter;
import com.charko.tester.rxjava.rxjavatester.client.RestClient;

import java.util.List;

import io.reactivex.BackpressureStrategy;
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
        // TODO 식별자 생성 수행할 업무 추가
        Observable<List<String>> booksObservable =
                Observable.fromCallable(() -> restClient.getFavoriteBooks());

//        Flowable<List<String>> booksFlowable = Flowable.

        // TODO 식별자를 사용하여 rx기능 추가
        disposable = booksObservable
                .subscribeOn(Schedulers.io())
                .toFlowable(BackpressureStrategy.BUFFER)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(strings -> displayBooks(strings)); // subscribe - onNext and onComplate, onError등이 사용되어진다.

        disposable = booksObservable
                .subscribeOn(Schedulers.io())
                .toFlowable(BackpressureStrategy.BUFFER)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(strings -> { // onNext() 처리

                }, throwable -> { // onError() 처리

                }, () -> { // onCoplete() 처리
                    Log.e("tag", "test : onComplete");
                });
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
