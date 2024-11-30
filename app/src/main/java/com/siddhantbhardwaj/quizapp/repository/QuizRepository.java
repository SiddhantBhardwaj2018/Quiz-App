package com.siddhantbhardwaj.quizapp.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.siddhantbhardwaj.quizapp.model.QuestionList;
import com.siddhantbhardwaj.quizapp.retrofit.QuestionsApi;
import com.siddhantbhardwaj.quizapp.retrofit.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizRepository {

    private QuestionsApi questionsApi;

    public QuizRepository() {
        this.questionsApi =  RetrofitInstance.getInstance().create(QuestionsApi.class);
    }

    public LiveData<QuestionList> getQuestionsFromApi(){
        MutableLiveData<QuestionList> data = new MutableLiveData<>();
        Call<QuestionList> response = questionsApi.getAllQuestions();
        response.enqueue(new Callback<QuestionList>() {
            @Override
            public void onResponse(Call<QuestionList> call, Response<QuestionList> response) {
                QuestionList questionList = response.body();
                data.setValue(questionList);
            }

            @Override
            public void onFailure(Call<QuestionList> call, Throwable throwable) {

            }
        });
        return data;
    }

}
