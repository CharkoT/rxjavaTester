package com.charko.tester.rxjava.rxjavatester;

import org.junit.Test;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.observers.TestObserver;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void anObservableStreamOfEventsAndDataShouldEmitsEachItemInOrder() {
//        TestSubscriber<Integer> ts = Flowable.range(1, 5).test();
//        TestObserver<Integer> to = Observable.range(1, 5).test();
//        TestObserver<Integer> tso = Single.just(1).test();
//        TestObserver<Integer> tmo = Maybe.just(1).test();
//        TestObserver<Integer> tco = Completable.complete().test();

//        Observable<String> pipelineOfData = Observable.just("Foo", "Bar");
//
//        pipelineOfData.subscribe(pipelineOfData);
//
//        List<Object> dataEmitted = testObserver.values();
//        assertThat(dataEmitted).hasSize(2);
//        assertThat(dataEmitted).containsOnlyOnce("Foo");
//        assertThat(dataEmitted).containsOnlyOnce("Bar");
    }

    String result = "";

    // Simple subscription to a fix value
    @Test
    public void returnAValue() {
        result = "";
        Observable<String> observer = Observable.just("Hello"); // provides data
        observer.subscribe(s -> result = s); // Callable as subscriber
        assertTrue(result.equals("Hello"));
    }

    @Test
    public void expectNPE() {
        Observable<Todo> todoObservable = Observable.create(new ObservableOnSubscribe<Todo>() {
            @Override
            public void subscribe(ObservableEmitter<Todo> emitter) throws Exception {
                try {
                    List<Todo> todos = ExampleUnitTest.this.getTodos();
                    if (todos == null) {
                        throw new NullPointerException("todos was null");
                    }
                    for (Todo todo : todos) {
                        emitter.onNext(todo);
                    }
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        });
        TestObserver<Object> testObserver = new TestObserver<>();
        todoObservable.subscribeWith(testObserver);

        // expect a NPE by using the TestObserver
        testObserver.assertError(NullPointerException.class);
    }

    private List<Todo> getTodos() {
        return null;
    }

    public class Todo {
    }
}