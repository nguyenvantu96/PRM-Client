package com.nguyentu.myapplication;

public class Item {
    private String question;
    private String answer;
    private char[] guessedWord;
    private int whiteSpaceIdx;

    public Item(String question, String answer, char[] guessedWord) {
        this.question = question;
        this.answer = answer;
        this.guessedWord = guessedWord;
    }

    public Item() { }

    public String getQuestion() { return question; }

    public void setQuestion(String question) { this.question = question; }

    public String getAnswer() { return answer; }

    public char[] getGuessedWord() { return guessedWord; }

    public void setGuessedWord(char[] guessedWord) { this.guessedWord = guessedWord; }

    public void setAnswer(String answer) { this.answer = answer; }

    public int getWhiteSpaceIdx() { return whiteSpaceIdx; }

    public void setWhiteSpaceIdx(int whiteSpaceIdx) { this.whiteSpaceIdx = whiteSpaceIdx; }

}
