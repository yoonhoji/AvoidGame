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

public class ShopActivity extends AppCompatActivity {

    UserVO user;
    int point;

    TextView pointView2;
    TextView haveGreen, haveBlue, haveYellow, havePurple, haveCyan;
    Button buyGreen, buyBlue, buyYellow, buyPurple, buyCyan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        point = (int) getIntent().getSerializableExtra("point");
        user = (UserVO) getIntent().getSerializableExtra("user");

        Button backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShopActivity.this, HomeActivity.class);
                intent.putExtra("user", user);
                intent.putExtra("rp", 0);
                intent.putExtra("point", 0);
                startActivity(intent);
                finish();
            }
        });

        pointView2 = findViewById(R.id.pointView2);
        pointView2.setText(String.valueOf(user.getPoint() + point));

        haveGreen = findViewById(R.id.haveGreen);
        haveBlue = findViewById(R.id.haveBlue);
        haveYellow = findViewById(R.id.haveYellow);
        havePurple = findViewById(R.id.havePurple);
        haveCyan = findViewById(R.id.haveCyan);

        buyGreen = findViewById(R.id.buyGreen);
        buyBlue = findViewById(R.id.buyBlue);
        buyYellow = findViewById(R.id.buyYellow);
        buyPurple = findViewById(R.id.buyPurple);
        buyCyan = findViewById(R.id.buyCyan);

        try {
            String result = new ShopActivity.CustomTask().execute(String.valueOf(user.getUseq()), "inventory").get();

            if (!result.equals("noSkin")) {
                String[] color = result.split(",");
                int sCnt = color.length;

                for (int i = 0; i < sCnt; i++){
                    if (color[i].equals("green")){
                        buyGreen.setVisibility(View.INVISIBLE);
                        haveGreen.setVisibility(View.VISIBLE);
                    }
                    if (color[i].equals("blue")){
                        buyBlue.setVisibility(View.INVISIBLE);
                        haveBlue.setVisibility(View.VISIBLE);
                    }
                    if (color[i].equals("yellow")){
                        buyYellow.setVisibility(View.INVISIBLE);
                        haveYellow.setVisibility(View.VISIBLE);
                    }
                    if (color[i].equals("purple")){
                        buyPurple.setVisibility(View.INVISIBLE);
                        havePurple.setVisibility(View.VISIBLE);
                    }
                    if (color[i].equals("cyan")){
                        buyCyan.setVisibility(View.INVISIBLE);
                        haveCyan.setVisibility(View.VISIBLE);
                    }
                }
            }

        } catch(Exception e){
            e.printStackTrace();
        }

        buyGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    new ShopActivity.CustomTask2().execute(
                            String.valueOf(user.getUseq()), "1", "green", "b1f179", "buySkin");
                    Intent intent = new Intent(ShopActivity.this, HomeActivity.class);
                    intent.putExtra("user", user);
                    intent.putExtra("rp", 0);
                    intent.putExtra("point", -200);
                    startActivity(intent);
                    finish();
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
        buyBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    new ShopActivity.CustomTask2().execute(
                            String.valueOf(user.getUseq()), "2", "blue", "92c3fe", "buySkin");
                    Intent intent = new Intent(ShopActivity.this, HomeActivity.class);
                    intent.putExtra("user", user);
                    intent.putExtra("rp", 0);
                    intent.putExtra("point", -200);
                    startActivity(intent);
                    finish();
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
        buyYellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    new ShopActivity.CustomTask2().execute(
                            String.valueOf(user.getUseq()), "3", "yellow", "f8ee67", "buySkin");
                    Intent intent = new Intent(ShopActivity.this, HomeActivity.class);
                    intent.putExtra("user", user);
                    intent.putExtra("rp", 0);
                    intent.putExtra("point", -200);
                    startActivity(intent);
                    finish();
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
        buyPurple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    new ShopActivity.CustomTask2().execute(
                            String.valueOf(user.getUseq()), "4", "purple", "d084e7", "buySkin");
                    Intent intent = new Intent(ShopActivity.this, HomeActivity.class);
                    intent.putExtra("user", user);
                    intent.putExtra("rp", 0);
                    intent.putExtra("point", -500);
                    startActivity(intent);
                    finish();
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
        buyCyan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    new ShopActivity.CustomTask2().execute(
                            String.valueOf(user.getUseq()), "5", "cyan", "76e2d4", "buySkin");
                    Intent intent = new Intent(ShopActivity.this, HomeActivity.class);
                    intent.putExtra("user", user);
                    intent.putExtra("rp", 0);
                    intent.putExtra("point", -1000);
                    startActivity(intent);
                    finish();
                } catch(Exception e){
                    e.printStackTrace();
                }
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
                sendMsg = "useq="+strings[0]+"&type="+strings[1];
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

    class CustomTask2 extends AsyncTask<String, Void, String> {
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
                sendMsg = "useq="+strings[0]+"&sseq="+strings[1]+"&scolor="+strings[2]+"&scode="+strings[3]+"&type="+strings[4];
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