package com.example.myapplication;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface Entity_Keyword {
    /* GET 을 사용*/
    @GET("v2/local/search/keyword.json")
    Call<Meta.KeyWord> searchKeyword(
            @Header("Authorization") String token,   //Authorization 대부분 Header 로 전해줍니다.
            @Query("query") String query,            //우리가 찾고자 하는 키워드
            @Query("x") double y,                    //y좌표(왜 반대인지는 모르겠음..)
            @Query("y") double x,                    //x좌표
            @Query("radius")int radius              //범위(중심좌표로 부터)
    );
}
