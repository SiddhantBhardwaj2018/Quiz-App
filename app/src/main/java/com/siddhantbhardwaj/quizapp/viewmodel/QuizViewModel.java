package com.siddhantbhardwaj.quizapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.siddhantbhardwaj.quizapp.model.QuestionList;
import com.siddhantbhardwaj.quizapp.repository.QuizRepository;

public class QuizViewModel extends ViewModel {

    private QuizRepository quizRepository = new QuizRepository();
    private LiveData<QuestionList> questionListLiveData;

    public QuizViewModel() {
        questionListLiveData = quizRepository.getQuestionsFromApi();
    }

    public LiveData<QuestionList> getQuestionListLiveData() {
        return questionListLiveData;
    }
}
