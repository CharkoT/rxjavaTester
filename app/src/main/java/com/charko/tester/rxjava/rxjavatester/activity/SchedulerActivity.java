package com.charko.tester.rxjava.rxjavatester.activity;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.charko.tester.rxjava.rxjavatester.R;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class SchedulerActivity extends AppCompatActivity {

    private Disposable disposable;
    private ProgressBar progressBar;
    private TextView textView;
    private Button button;

    Callable<String> callable = new Callable<String>() {
        @Override
        public String call() throws Exception {
            return doSomethingLong();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduler);
        configureLayout();
    }


    public String doSomethingLong() {
        SystemClock.sleep(1000);
        return "Hello";
    }

    private void configureLayout() {
        setContentView(R.layout.activity_scheduler);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        textView = (TextView) findViewById(R.id.messagearea);
        button = findViewById(R.id.scheduleLongRunningOperation);

        button.setOnClickListener(view -> {
            Observable.fromCallable(callable)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe(disposable1 -> {
                        progressBar.setVisibility(View.VISIBLE);
                        button.setEnabled(false);
                        textView.setText(textView.getText().toString() + "\n" + "Progressbar set visible");
                    }).subscribe(getDisposableObserver());

        });
    }

    /**
     * Observer
     * Handles the stream of data:
     */
    private DisposableObserver<String> getDisposableObserver() {
        return new DisposableObserver<String>() {

            @Override
            public void onComplete() {
                textView.setText(textView.getText().toString() + "\n" + "OnComplete");
                progressBar.setVisibility(View.INVISIBLE);
                button.setEnabled(true);
                textView.setText(textView.getText().toString() + "\n" + "Hidding Progressbar");
            }

            @Override
            public void onError(Throwable e) {
                textView.setText(textView.getText().toString() + "\n" + "OnError");
                progressBar.setVisibility(View.INVISIBLE);
                button.setEnabled(true);
                textView.setText(textView.getText().toString() + "\n" + "Hidding Progressbar");
            }

            @Override
            public void onNext(String message) {
                textView.setText(textView.getText().toString() + "\n" + "onNext " + message);
            }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
