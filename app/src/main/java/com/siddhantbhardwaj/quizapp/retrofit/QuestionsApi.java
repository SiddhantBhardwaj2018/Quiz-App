package com.siddhantbhardwaj.quizapp.retrofit;

import com.siddhantbhardwaj.quizapp.model.QuestionList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface QuestionsApi {

    @GET("/getAllQuestions")
    public Call<QuestionList> getAllQuestions();

}
