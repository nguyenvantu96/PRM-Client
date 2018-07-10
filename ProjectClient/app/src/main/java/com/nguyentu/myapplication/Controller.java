package com.nguyentu.myapplication;

import java.util.List;
import java.util.Random;

public class Controller {
    public int checkAnwser(String anwser,String input){
        int count = 0;
        String[] data = anwser.split("");
        for (int i=0;i < data.length;i++){
            if(input.equals(data[i])){
                count++;
            }
        }
        return count;
    }
    public Item randomAnwser(List<Item> list){
       int index = generateRandom(0,list.size()-1);
       Item currentQuestion = list.get(index);
        return currentQuestion;
    }
    public  int generateRandom(int min, int max) {
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
}
