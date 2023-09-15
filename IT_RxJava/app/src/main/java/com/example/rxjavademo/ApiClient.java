package com.example.rxjavademo;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public Retrofit getCommentApi(){
        return new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory.create())
                //使用RxJava需要多添加這個
                .build();
    }
}
