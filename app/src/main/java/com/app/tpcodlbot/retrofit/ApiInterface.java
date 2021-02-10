package com.app.tpcodlbot.retrofit;

import com.app.tpcodlbot.model.AnswerResponse;
import com.app.tpcodlbot.model.QuestionResponse;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiInterface {

    @GET("get/bUSHCqHvyq?indent=2")
    Call<List<QuestionResponse>> getAllQuestions();

    @GET("get/ckyRcRZBua?indent=2")
    Call<AnswerResponse> getAllAnswers();

    @GET("get/cfOVCNPSeW?indent=2")
    Call<AnswerResponse> getImageAnswers();
}

