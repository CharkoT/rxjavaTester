package com.charko.tester.rxjava.rxjavatester.activity;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.TextView;

import com.charko.tester.rxjava.rxjavatester.R;
import com.charko.tester.rxjava.rxjavatester.adapter.SimpleStringAdapter;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RxJavaSimpleActivity extends AppCompatActivity {

    RecyclerView colorListView;
    SimpleStringAdapter simpleStringAdapter;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    public int value = 0;

    final Observable<Integer> serverDownloadObservable = Observable.create(emitter -> {
        SystemClock.sleep(10000);

        emitter.onNext(5);
        emitter.onComplete();
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java_simple);

        Button button = findViewById(R.id.button);

        button.setOnClickListener(view -> {
            view.setEnabled(false);
            Disposable disposable = serverDownloadObservable.observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(integer -> {
                                updateTheUserInterface(integer);
                                view.setEnabled(true);
                            }
                    );
            compositeDisposable.add(disposable);
        });

    }


    // TODO : 메모리 릭 발생 가능성이 높아, Observable을 초기화 시킨다.
    @Override
    protected void onStop() {
        super.onStop();

        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.isDisposed();
        }
    }

    private void updateTheUserInterface(int integer) {
        TextView view = (TextView) findViewById(R.id.resultView);
        view.setText(String.valueOf(integer));
    }

}
