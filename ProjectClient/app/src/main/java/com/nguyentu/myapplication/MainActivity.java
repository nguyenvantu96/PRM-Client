package com.nguyentu.myapplication;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private String TAG = MainActivity.class.getSimpleName();
    TextView tView;
    TextView question;
    List<Item> list;
    Controller c;
    EditText editText;
    Item current;
    LinearLayout linearLayout;
    Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tView = findViewById(R.id.tView);
        question = findViewById(R.id.question);
        list = new ArrayList<>();
        c = new Controller();
        editText = findViewById(R.id.editText);
        linearLayout = findViewById(R.id.blockQues);
        btnStart = findViewById(R.id.btnStart);
        btnStart.setEnabled(false);
        new GetData().execute();
    }

    public void startGame(View view) {
        int idx = 0;
        current = c.randomAnwser(list);
        linearLayout.removeAllViews();

        for (char character : current.getGuessedWord()) {
            LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(100, 115);

            if (idx > 0 && (idx - 1) == current.getWhiteSpaceIdx()) {
                params.setMargins(82, 0, 0, 0);
            }
            if (idx == current.getWhiteSpaceIdx()) {
                idx++;
                continue;
            }

            Button button = new Button(this);
            button.setLayoutParams(params);
            button.setText(character + "");
            linearLayout.addView(button);
            idx++;
        }

        question.setText(current.getQuestion());
    }

    public void onClick(View view) {
        int count, idx = 0;
        try {
            String currentAnswer = current.getAnswer();
            char inputAnswer = editText.getText().toString().toUpperCase().charAt(0);
            char[] guessedWord = c.checkAnwser(
                currentAnswer,
                current.getGuessedWord(),
                inputAnswer
            );
            editText.setText("");
            current.setGuessedWord(guessedWord);
            count = c.countCorrectWord(currentAnswer, inputAnswer);
            if (count > 0){
                tView.setText("Có " + count + " chữ "+ inputAnswer);
            } else {
                tView.setText("Không có chữ " + inputAnswer + " nào");
            }

            linearLayout.removeAllViews();
            for (char character : current.getGuessedWord()) {
                LinearLayout.LayoutParams params =
                        new LinearLayout.LayoutParams(100, 115);

                if (idx > 0 && (idx - 1) == current.getWhiteSpaceIdx()) {
                    params.setMargins(82, 0, 0, 0);
                }
                if (idx == current.getWhiteSpaceIdx()) {
                    idx++;
                    continue;
                }

                Button button = new Button(this);
                button.setLayoutParams(params);
                button.setText(character + "");
                linearLayout.addView(button);
                idx++;
            }
        } catch (Exception e){
            e.getMessage();
        }
    }

    private class GetData extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MainActivity.this, "Json Data is downloading", Toast.LENGTH_SHORT).show();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            HttpHandler sh = new HttpHandler();
            String url = "http://150.95.105.112";
            String jsonStr =  sh.makeServiceCall(url) ;
            if (jsonStr != null ) {
                try {
                    JSONArray jsonarray = new JSONArray(jsonStr);
                    for (int i = 0; i < jsonarray.length(); i++) {
                        JSONObject c = jsonarray.getJSONObject(i);
                        String question = c.getString("question");
                        String answer = c.getString("answer").trim().toUpperCase();
                        char[] guessedWord = new char[answer.length()];
                        for (int j = 0; j < guessedWord.length; j++) {
                            guessedWord[j] = ' ';
                        }
                        Item item = new Item(question, answer, guessedWord);
                        item.setWhiteSpaceIdx(answer.indexOf(' '));
                        list.add(item);
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Data success",
                                    Toast.LENGTH_LONG).show();
                            btnStart.setEnabled(true);
                        }
                    });
                } catch (final Exception e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
            return null;
        }
    }
}
