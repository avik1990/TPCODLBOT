package com.app.tpcodlbot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.tpcodlbot.adapter.ChatAdapter;
import com.app.tpcodlbot.model.AnswerResponse;
import com.app.tpcodlbot.model.DiscoveryInstantSearchModel;
import com.app.tpcodlbot.model.QuestionResponse;
import com.app.tpcodlbot.retrofit.ApiInterface;
import com.app.tpcodlbot.retrofit.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity implements ChatAdapter.GetQuestionId {

    RecyclerView rvRecycler;
    EditText etChatView;
    Context context;
    List<DiscoveryInstantSearchModel> list_discoveryInstantSearchModel = new ArrayList<>();
    List<QuestionResponse> questionResponse;
    AnswerResponse answerResponse;
    ChatAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        initViews();
        fetchQuestions();
    }

    private void initViews() {
        rvRecycler = findViewById(R.id.rvRecycler);
        etChatView = findViewById(R.id.etChatView);

        etChatView.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    if (!etChatView.getText().toString().trim().isEmpty()) {
                        list_discoveryInstantSearchModel.add(new DiscoveryInstantSearchModel(ChatAdapter.ITEM_TYPE_USERQUESTION, etChatView.getText().toString().trim()));
                        adapter.updateChatView(list_discoveryInstantSearchModel);
                        rvRecycler.smoothScrollToPosition(adapter.getItemCount() - 1);
                        etChatView.setText("");
                        fetchAnswers();
                    }
                }
                return false;
            }
        });
    }


    private void fetchQuestions() {
        ApiInterface service = RetrofitClientInstance.getRetrofitInstance().create(ApiInterface.class);
        Call<List<QuestionResponse>> call = service.getAllQuestions();
        call.enqueue(new Callback<List<QuestionResponse>>() {

            @Override
            public void onResponse(Call<List<QuestionResponse>> call, Response<List<QuestionResponse>> response) {
                questionResponse = response.body();

                try {
                    if (questionResponse != null) {
                        mergerData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<QuestionResponse>> call, Throwable t) {
                t.printStackTrace();
                Log.i("TAG", t.getMessage());
            }
        });
    }

    private void mergerData() {
        list_discoveryInstantSearchModel.clear();
        list_discoveryInstantSearchModel.add(new DiscoveryInstantSearchModel(0, questionResponse));

        adapter = new ChatAdapter(context, list_discoveryInstantSearchModel, this);
        rvRecycler.setAdapter(adapter);
    }

    @Override
    public void getQuestionID(String id) {
         Toast.makeText(context, id, Toast.LENGTH_SHORT).show();
        if (id.equalsIgnoreCase("4")) {
            fetchIMageAnswers();
        } else {
            fetchAnswers();
        }

    }


    private void fetchAnswers() {
        ApiInterface service = RetrofitClientInstance.getRetrofitInstance().create(ApiInterface.class);
        Call<AnswerResponse> call = service.getAllAnswers();
        call.enqueue(new Callback<AnswerResponse>() {

            @Override
            public void onResponse(Call<AnswerResponse> call, Response<AnswerResponse> response) {
                answerResponse = response.body();

                try {
                    if (answerResponse != null) {
                        prepareData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<AnswerResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


    private void fetchIMageAnswers() {
        ApiInterface service = RetrofitClientInstance.getRetrofitInstance().create(ApiInterface.class);
        Call<AnswerResponse> call = service.getImageAnswers();
        call.enqueue(new Callback<AnswerResponse>() {

            @Override
            public void onResponse(Call<AnswerResponse> call, Response<AnswerResponse> response) {
                answerResponse = response.body();

                try {
                    if (answerResponse != null) {
                        prepareImageData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<AnswerResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void prepareData() {
        //list_discoveryInstantSearchModel.clear();
        list_discoveryInstantSearchModel.add(new DiscoveryInstantSearchModel(answerResponse.getAnswerid(), answerResponse.getAnswer(), ChatAdapter.ITEM_TYPE_ANSWERS));
        adapter.updateChatView(list_discoveryInstantSearchModel);
        rvRecycler.smoothScrollToPosition(adapter.getItemCount() - 1);
    }

    private void prepareImageData() {
        //list_discoveryInstantSearchModel.clear();
        list_discoveryInstantSearchModel.add(new DiscoveryInstantSearchModel(answerResponse.getAnswerid(), answerResponse.getAnswer(), ChatAdapter.ITEM_TYPE_IMAGES));
        adapter.updateChatView(list_discoveryInstantSearchModel);
        rvRecycler.smoothScrollToPosition(adapter.getItemCount() - 1);
    }
}