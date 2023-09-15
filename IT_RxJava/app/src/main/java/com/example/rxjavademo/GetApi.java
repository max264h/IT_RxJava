package com.example.rxjavademo;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetApi {
    @GET("comments")//一樣使用Get要取Api資料
    Observable<List<DataResponse>> getCommentsData(
      //使用Observable告知有這個數據流可以觀測，後續才可以透過subscribe去訂閱這個數據流
      @Query("id") int id
      //這邊設定這個數據流有一個參數(id)，屆時就要輸入id才可以搜尋
    );
}
