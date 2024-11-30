package com.siddhantbhardwaj.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.siddhantbhardwaj.quizapp.databinding.ActivityMainBinding;
import com.siddhantbhardwaj.quizapp.model.Question;
import com.siddhantbhardwaj.quizapp.model.QuestionList;
import com.siddhantbhardwaj.quizapp.viewmodel.QuizViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    QuizViewModel viewModel;
    List<Question> questionList;

    static int result = 0;
    static int totalQuestions = 0;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding = DataBindingUtil.setContentView(
                this,
                R.layout.activity_main
        );
        result = 0;
        totalQuestions = 0;

        viewModel = new ViewModelProvider(this).get(QuizViewModel.class);

        displayFirstQuestion();
        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayNextQuestion();
            }
        });

    }

    public void displayFirstQuestion(){
        viewModel.getQuestionListLiveData().observe(this, new Observer<QuestionList>() {
            @Override
            public void onChanged(QuestionList questions) {
                questionList = questions;
                binding.txtQuestion.setText("Question 1:" + questions.get(0).getQuestion());
                binding.radio1.setText(questions.get(0).getOptionOne());
                binding.radio2.setText(questions.get(0).getOptionTwo());
                binding.radio3.setText(questions.get(0).getOptionThree());
                binding.radio4.setText(questions.get(0).getOptionFour());
            }
        });
    }

    public void displayNextQuestion(){
         // direct to results
        if(binding.btnNext.getText().equals("Finish")){
            Intent intent = new Intent(MainActivity.this,ResultsActivity.class);
            startActivity(intent);
            finish();
        }
        // direct to next question
        int selectedOption = binding.radioGroup.getCheckedRadioButtonId();
        if(selectedOption != -1){
            RadioButton radioButton = findViewById(selectedOption);
            if((questionList.size() - i) > 0){
                totalQuestions = questionList.size();

                if(radioButton.getText().toString().equals(
                        questionList.get(i).getCorrectOption()
                )){
                    result++;
                    binding.txtResult.setText(
                            "Correct Answer is " + result
                    );
                }
                if(i==0){
                    i++;
                }
                binding.txtQuestion.setText("Question " + (i + 1) + " : " +
                        questionList.get(i).getQuestion()
                        );
                binding.radio1.setText(questionList.get(i).getOptionOne());
                binding.radio2.setText(questionList.get(i).getOptionTwo());
                binding.radio3.setText(questionList.get(i).getOptionThree());
                binding.radio4.setText(questionList.get(i).getOptionFour());

                if(i == (questionList.size() - 1)){
                    binding.btnNext.setText("Finish");
                }
                binding.radioGroup.clearCheck();
                i++;
            }else{
                if(radioButton.getText().toString().equals(
                        questionList.get(i - 1).getCorrectOption()
                )){
                    result++;
                    binding.txtResult.setText(" Correct Answer : " + result);
                }
            }
        }else{
            Toast.makeText(
                    this,
                    "You need to make a selection",
                    Toast.LENGTH_LONG
            ).show();
        }

    }

}