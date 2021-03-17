package com.example.shootergame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ResultActivity extends AppCompatActivity {

    UserVO user;
    int score, point;

    TextView result_rp, result_point;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Log.d("로그", "리절트액티비티");

        user = (UserVO) getIntent().getSerializableExtra("user");
        score = (int) getIntent().getSerializableExtra("score");
        point = (int) getIntent().getSerializableExtra("point");

        result_rp = findViewById(R.id.result_rp);
        result_rp.setText(""+score);

        result_point = findViewById(R.id.result_point);
        result_point.setText(""+point);

        new ResultActivity.CustomTask().execute(
                String.valueOf(user.getUseq()), String.valueOf(score), String.valueOf(point), "result");

        Button okBtn = findViewById(R.id.okBtn);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, HomeActivity.class);
                intent.putExtra("user", user);
                intent.putExtra("rp", score);
                intent.putExtra("point", point);
                startActivity(intent);
                finish();
            }
        });
    }

    class CustomTask extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;
        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://172.30.1.38:8080/sendDataToAndroid/data.jsp");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg = "useq="+strings[0]+"&rp="+strings[1]+"&point="+strings[2]+"&type="+strings[3];
                osw.write(sendMsg);
                osw.flush();
                if(conn.getResponseCode() == conn.HTTP_OK) {
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuffer buffer = new StringBuffer();
                    while ((str = reader.readLine()) != null) {
                        buffer.append(str);
                    }
                    receiveMsg = buffer.toString();

                } else {
                    Log.i("통신 결과", conn.getResponseCode()+"에러");
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return receiveMsg;
        }
    }
}