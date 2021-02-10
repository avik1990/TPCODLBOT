package com.app.tpcodlbot.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.app.tpcodlbot.ChatActivity;
import com.app.tpcodlbot.R;
import com.app.tpcodlbot.model.DiscoveryInstantSearchModel;
import com.google.android.flexbox.FlexboxLayout;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;


public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    public static final int ITEM_TYPE_QUESTIONS = 0;
    public static final int ITEM_TYPE_ANSWERS = 1;
    public static final int ITEM_TYPE_IMAGES = 2;
    public static final int ITEM_TYPE_URL = 3;
    public static final int ITEM_TYPE_DIVIDER = 4;
    public static final int ITEM_TYPE_USERQUESTION = 5;
    GetQuestionId getQuestionId;

    List<DiscoveryInstantSearchModel> list_discoveryInstantSearchModel;
    boolean QuestionSet = false;

    public interface GetQuestionId {
        void getQuestionID(String id);
    }

    public void updateChatView(List<DiscoveryInstantSearchModel> list_discoveryInstantSearchModel) {
        this.list_discoveryInstantSearchModel = list_discoveryInstantSearchModel;
        Log.e("ARRSIZE", "" + list_discoveryInstantSearchModel.size());
        Gson g = new Gson();
        Log.e("JSON", g.toJson(list_discoveryInstantSearchModel));
        DiscoveryInstantSearchModel organisation = g.fromJson(g.toJson(list_discoveryInstantSearchModel), DiscoveryInstantSearchModel.class);
        this.list_discoveryInstantSearchModel = (List<DiscoveryInstantSearchModel>) organisation;
        notifyDataSetChanged();
    }

    public ChatAdapter(Context context, List<DiscoveryInstantSearchModel> list_discoveryInstantSearchModel, GetQuestionId getQuestionId) {
        this.context = context;
        this.list_discoveryInstantSearchModel = list_discoveryInstantSearchModel;
        this.getQuestionId = getQuestionId;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        RecyclerView.ViewHolder holder = null;

        switch (viewType) {
            case ITEM_TYPE_QUESTIONS:
                view = inflater.inflate(R.layout.row_questions, parent, false);
                holder = new QuestionsViewHolder(view);
                break;

            case ITEM_TYPE_ANSWERS:
                view = inflater.inflate(R.layout.row_answers, parent, false);
                holder = new AnswerViewHolder(view);
                break;

            case ITEM_TYPE_USERQUESTION:
                view = inflater.inflate(R.layout.row_question_user, parent, false);
                holder = new QuestionUserViewHolder(view);
                break;

            case ITEM_TYPE_IMAGES:
                view = inflater.inflate(R.layout.row_images, parent, false);
                holder = new ImageViewHolder(view);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        if (holder.getItemViewType() == ITEM_TYPE_QUESTIONS) {
            QuestionsViewHolder userviewViewHolder = (QuestionsViewHolder) holder;
            QuestionViewSetter(userviewViewHolder, i);
        } else if (holder.getItemViewType() == ITEM_TYPE_ANSWERS) {
            AnswerViewHolder tripViewHolder = (AnswerViewHolder) holder;
            AnswerViewSetter(tripViewHolder, i);
        } else if (holder.getItemViewType() == ITEM_TYPE_USERQUESTION) {
            QuestionUserViewHolder tripViewHolder = (QuestionUserViewHolder) holder;
            UserTypedViewSetter(tripViewHolder, i);
        } else if (holder.getItemViewType() == ITEM_TYPE_IMAGES) {
            ImageViewHolder tripViewHolder = (ImageViewHolder) holder;
            ImageViewSetter(tripViewHolder, i);
        }
    }

    private void QuestionViewSetter(QuestionsViewHolder userviewViewHolder, int l) {
        // if (!QuestionSet) {
        LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        buttonLayoutParams.setMargins(5, 5, 5, 5);
        userviewViewHolder.flexContainer.removeAllViews();
        for (int i = 0; i < list_discoveryInstantSearchModel.get(l).getListQuestions().size(); i++) {
            final TextView tv = new TextView(context);
            tv.setText(list_discoveryInstantSearchModel.get(l).getListQuestions().get(i).getQuestion());
            tv.setHeight(70);
            tv.setTextSize(10.0f);
            tv.setGravity(Gravity.CENTER);
            tv.setTextColor(Color.parseColor("#ffffff"));
            tv.setBackground(context.getResources().getDrawable(R.drawable.rounded_corner_yellow));
            tv.setId(i + 1);
            tv.setLayoutParams(buttonLayoutParams);
            tv.setTag(list_discoveryInstantSearchModel.get(l).getListQuestions().get(i).getId());
            tv.setPadding(20, 10, 20, 10);

            tv.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    String ID = tv.getTag().toString();
                    getQuestionId.getQuestionID(ID);
                }
            });

            userviewViewHolder.flexContainer.addView(tv);
            QuestionSet = true;
        }
        ///}

    }

    private void AnswerViewSetter(AnswerViewHolder userviewViewHolder, int l) {
        userviewViewHolder.tvUserChat.setText(list_discoveryInstantSearchModel.get(l).getAnswers());
    }

    private void UserTypedViewSetter(QuestionUserViewHolder userviewViewHolder, int l) {
        userviewViewHolder.tvUserChat.setText(list_discoveryInstantSearchModel.get(l).getUserTypedquestions());
    }

    private void ImageViewSetter(ImageViewHolder userviewViewHolder, int l) {
        try {
            Log.e("IMAGES", list_discoveryInstantSearchModel.get(l).getAnswers());
            Picasso.with(context).load(list_discoveryInstantSearchModel.get(l).getAnswers()).into(userviewViewHolder.ivImages);
        } catch (Exception e) {
        }
    }


    @Override
    public int getItemCount() {
        return list_discoveryInstantSearchModel.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (list_discoveryInstantSearchModel.get(position).getItemtype() == ITEM_TYPE_QUESTIONS) {
            Log.e("ROW", "ROW1");
            return ITEM_TYPE_QUESTIONS;
        } else if (list_discoveryInstantSearchModel.get(position).getItemtype() == ITEM_TYPE_ANSWERS) {
            Log.e("ROW", "ROW2");
            return ITEM_TYPE_ANSWERS;
        } else if (list_discoveryInstantSearchModel.get(position).getItemtype() == ITEM_TYPE_USERQUESTION) {
            Log.e("ROW", "ROW3");
            return ITEM_TYPE_USERQUESTION;
        } else if (list_discoveryInstantSearchModel.get(position).getItemtype() == ITEM_TYPE_IMAGES) {
            Log.e("ROW", "ROW4");
            return ITEM_TYPE_IMAGES;
        } else {
            Log.e("ROW", "ROW5");
            return ITEM_TYPE_DIVIDER;
        }
    }

    private class QuestionsViewHolder extends RecyclerView.ViewHolder {

        FlexboxLayout flexContainer;

        public QuestionsViewHolder(View itemView) {
            super(itemView);
            flexContainer = itemView.findViewById(R.id.flexContainer);
        }
    }

    private class AnswerViewHolder extends RecyclerView.ViewHolder {

        TextView tvUserChat;

        public AnswerViewHolder(View itemView) {
            super(itemView);
            tvUserChat = itemView.findViewById(R.id.tvUserChat);
        }
    }

    private class QuestionUserViewHolder extends RecyclerView.ViewHolder {

        TextView tvUserChat;

        public QuestionUserViewHolder(View itemView) {
            super(itemView);
            tvUserChat = itemView.findViewById(R.id.tvUserChat);
        }
    }

    private class ImageViewHolder extends RecyclerView.ViewHolder {

        ImageView ivImages;

        public ImageViewHolder(View itemView) {
            super(itemView);
            ivImages = itemView.findViewById(R.id.ivImages);
        }
    }
}
