package com.nguyentu.myapplication;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private String TAG = MainActivity.class.getSimpleName();
    TextView tView;
    List<Item> list;
    Controller c;
    EditText editText;
    Item current;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tView = findViewById(R.id.tView);
        list = new ArrayList<>();
        c = new Controller();
        editText = findViewById(R.id.editText);
        new GetData().execute();

    }
    public void startGame(View view){

        current = c.randomAnwser(list);
        Toast.makeText(MainActivity.this, current.getQuestion().toString(), Toast.LENGTH_SHORT).show();

    }
    public void onClick(View view){

try {


   int number = c.checkAnwser(current.getAnswer(),editText.getText().toString());
   if(number > 0){
       tView.setText("Có" + number + "chữ "+editText.getText().toString());
   }else {
       tView.setText("Không có chữ " +editText.getText().toString() + " nào");
   }
}catch (Exception e){
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
                        String anwser = c.getString("answer");
                       Item item = new Item(question,anwser);
                       list.add(item);
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Data success",
                                    Toast.LENGTH_LONG).show();
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
