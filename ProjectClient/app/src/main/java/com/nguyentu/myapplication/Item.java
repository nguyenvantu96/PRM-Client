package com.nguyentu.myapplication;

public class Item {
    private String question;
    private String answer;

    public Item(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public Item() {
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
