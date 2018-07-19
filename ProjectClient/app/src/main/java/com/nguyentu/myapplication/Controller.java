package com.nguyentu.myapplication;

import java.util.List;
import java.util.Random;

public class Controller {
    public char[] checkAnwser(String answer, char[] guessedWord, char input) {
        int idx = 0;

        for (char character : answer.toCharArray()) {
            if (character == input) {
                guessedWord[idx] = character;
            }
            idx++;
        }
        return guessedWord;
    }

    public int countCorrectWord(String answer, char input) {
        int count = 0;
        for (char character : answer.toCharArray()) {
            if (character == input) {
                count++;
            }
        }
        return count;
    }

    public Item randomAnwser(List<Item> list){
       int index = generateRandom(0, list.size() - 1);
       Item currentQuestion = list.get(index);
        return currentQuestion;
    }

    public  int generateRandom(int min, int max) {
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
}
