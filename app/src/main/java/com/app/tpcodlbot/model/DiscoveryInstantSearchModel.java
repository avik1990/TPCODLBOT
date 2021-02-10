package com.app.tpcodlbot.model;

import java.util.List;

public class DiscoveryInstantSearchModel {

    String qID;
    String questions;

    String answerID;
    String answers;

    int Itemtype;

    String UserTypedquestions;

    List<QuestionResponse> listQuestions;

    public DiscoveryInstantSearchModel(String answerID, String answers, int itemtype) {
        this.answerID = answerID;
        this.answers = answers;
        Itemtype = itemtype;
    }

    public DiscoveryInstantSearchModel(int itemtype, String userTypedquestions) {
        Itemtype = itemtype;
        UserTypedquestions = userTypedquestions;
    }

    public DiscoveryInstantSearchModel(int itemtype, List<QuestionResponse> listQuestions) {
        Itemtype = itemtype;
        this.listQuestions = listQuestions;
    }

    public String getqID() {
        return qID;
    }

    public void setqID(String qID) {
        this.qID = qID;
    }

    public String getQuestions() {
        return questions;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
    }

    public String getAnswerID() {
        return answerID;
    }

    public void setAnswerID(String answerID) {
        this.answerID = answerID;
    }

    public String getAnswers() {
        return answers;
    }

    public void setAnswers(String answers) {
        this.answers = answers;
    }

    public int getItemtype() {
        return Itemtype;
    }

    public void setItemtype(int itemtype) {
        Itemtype = itemtype;
    }

    public List<QuestionResponse> getListQuestions() {
        return listQuestions;
    }

    public void setListQuestions(List<QuestionResponse> listQuestions) {
        this.listQuestions = listQuestions;
    }

    public String getUserTypedquestions() {
        return UserTypedquestions;
    }

    public void setUserTypedquestions(String userTypedquestions) {
        UserTypedquestions = userTypedquestions;
    }
}
