package com.charko.tester.rxjava.rxjavatester.client;

import android.content.Context;
import android.os.SystemClock;

import java.util.ArrayList;
import java.util.List;

public class RestClient {

    private Context context;

    public RestClient(Context context) {
        this.context = context;
    }

    public List<String> getFavoriteBooks() {
        SystemClock.sleep(8000);
        return createBooks();
    }

    public List<String> getFavoriteBooksWithException() {
        throw new RuntimeException("Fail load Books");
    }

    private List<String> createBooks() {
        List<String> books = new ArrayList<>();
        books.add("Lord of the Rings");
        books.add("The dark elf");
        books.add("Eclipse Introduction");
        books.add("History book");
        books.add("Der kleine Prinz");
        books.add("7 habits of highly effective people");
        books.add("Other book 1");
        books.add("Other book 2");
        books.add("Other book 3");
        books.add("Other book 4");
        books.add("Other book 5");
        books.add("Other book 6");
        return books;
    }
}
