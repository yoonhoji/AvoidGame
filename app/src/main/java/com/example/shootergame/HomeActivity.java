package com.example.shootergame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    UserVO user;

    TextView nicknameView, rpView, pointView;
    ImageView whiteCheck, greenCheck, blueCheck, yellowCheck, purpleCheck, cyanCheck;
    Button logoutBtn, playBtn;
    ImageButton shopBtn, white, green, blue, yellow, purple, cyan;
    String skin = "white";
    static int rp = 0;
    static int point = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        user = (UserVO) getIntent().getSerializableExtra("user");
        rp += (int) getIntent().getSerializableExtra("rp");
        point += (int) getIntent().getSerializableExtra("point");

        nicknameView = findViewById(R.id.nicknameView);
        nicknameView.setText(user.getNickname());

        rpView = findViewById(R.id.rpView);
        rpView.setText(String.valueOf(user.getRp() + rp));

        pointView = findViewById(R.id.pointView);
        pointView.setText(String.valueOf(user.getPoint() + point));

        logoutBtn = findViewById(R.id.logoutBtn);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        playBtn = findViewById(R.id.playBtn);
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, PlayActivity.class);
                intent.putExtra("user", user);
                intent.putExtra("skin", skin);
                startActivity(intent);
                finish();
            }
        });
        shopBtn = findViewById(R.id.shopBtn);
        shopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ShopActivity.class);
                intent.putExtra("point", point);
                intent.putExtra("user", user);
                startActivity(intent);
                finish();
            }
        });

        whiteCheck = findViewById(R.id.whiteCheck);
        greenCheck = findViewById(R.id.greenCheck);
        blueCheck = findViewById(R.id.blueCheck);
        yellowCheck = findViewById(R.id.yellowCheck);
        purpleCheck = findViewById(R.id.purpleCheck);
        cyanCheck = findViewById(R.id.cyanCheck);
        whiteCheck.setVisibility(View.VISIBLE);
        greenCheck.setVisibility(View.INVISIBLE);
        blueCheck.setVisibility(View.INVISIBLE);
        yellowCheck.setVisibility(View.INVISIBLE);
        purpleCheck.setVisibility(View.INVISIBLE);
        cyanCheck.setVisibility(View.INVISIBLE);

        white = findViewById(R.id.white);
        white.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whiteCheck.setVisibility(View.VISIBLE);
                greenCheck.setVisibility(View.INVISIBLE);
                blueCheck.setVisibility(View.INVISIBLE);
                yellowCheck.setVisibility(View.INVISIBLE);
                purpleCheck.setVisibility(View.INVISIBLE);
                cyanCheck.setVisibility(View.INVISIBLE);
                skin = "white";
            }
        });

        green = findViewById(R.id.green);
        green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whiteCheck.setVisibility(View.INVISIBLE);
                greenCheck.setVisibility(View.VISIBLE);
                blueCheck.setVisibility(View.INVISIBLE);
                yellowCheck.setVisibility(View.INVISIBLE);
                purpleCheck.setVisibility(View.INVISIBLE);
                cyanCheck.setVisibility(View.INVISIBLE);
                skin = "green";
            }
        });

        blue = findViewById(R.id.blue);
        blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whiteCheck.setVisibility(View.INVISIBLE);
                greenCheck.setVisibility(View.INVISIBLE);
                blueCheck.setVisibility(View.VISIBLE);
                yellowCheck.setVisibility(View.INVISIBLE);
                purpleCheck.setVisibility(View.INVISIBLE);
                cyanCheck.setVisibility(View.INVISIBLE);
                skin = "blue";
            }
        });

        yellow = findViewById(R.id.yellow);
        yellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whiteCheck.setVisibility(View.INVISIBLE);
                greenCheck.setVisibility(View.INVISIBLE);
                blueCheck.setVisibility(View.INVISIBLE);
                yellowCheck.setVisibility(View.VISIBLE);
                purpleCheck.setVisibility(View.INVISIBLE);
                cyanCheck.setVisibility(View.INVISIBLE);
                skin = "blue";
            }
        });

        purple = findViewById(R.id.purple);
        purple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whiteCheck.setVisibility(View.INVISIBLE);
                greenCheck.setVisibility(View.INVISIBLE);
                blueCheck.setVisibility(View.INVISIBLE);
                yellowCheck.setVisibility(View.INVISIBLE);
                purpleCheck.setVisibility(View.VISIBLE);
                cyanCheck.setVisibility(View.INVISIBLE);
                skin = "purple";
            }
        });

        cyan = findViewById(R.id.cyan);
        cyan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whiteCheck.setVisibility(View.INVISIBLE);
                greenCheck.setVisibility(View.INVISIBLE);
                blueCheck.setVisibility(View.INVISIBLE);
                yellowCheck.setVisibility(View.INVISIBLE);
                purpleCheck.setVisibility(View.INVISIBLE);
                cyanCheck.setVisibility(View.VISIBLE);
                skin = "cyan";
            }
        });

        green.setVisibility(View.INVISIBLE);
        blue.setVisibility(View.INVISIBLE);
        yellow.setVisibility(View.INVISIBLE);
        purple.setVisibility(View.INVISIBLE);
        cyan.setVisibility(View.INVISIBLE);

        try {
            String result = new CustomTask().execute(String.valueOf(user.getUseq()), "inventory").get();

            if (!result.equals("noSkin")) {
                String[] color = result.split(",");
                int sCnt = color.length;

                for (int i = 0; i < sCnt; i++){
                    if (color[i].equals("green")){
                        green.setVisibility(View.VISIBLE);
                    }
                    if (color[i].equals("blue")){
                        blue.setVisibility(View.VISIBLE);
                    }
                    if (color[i].equals("yellow")){
                        yellow.setVisibility(View.VISIBLE);
                    }
                    if (color[i].equals("purple")){
                        purple.setVisibility(View.VISIBLE);
                    }
                    if (color[i].equals("cyan")){
                        cyan.setVisibility(View.VISIBLE);
                    }
                }
            }

        } catch(Exception e){
            e.printStackTrace();
        }

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        RankingAdapter adapter = new RankingAdapter();

        try {
            String result2 = new CustomTask2().execute().get();
            String[] users = result2.split("-");
            Log.d("랭킹", "users[0] : " + users[0]);
            String[] infos;
            for (int i = 0; i < users.length; i++){
                infos = users[i].split(",");
                Log.d("랭킹", "infos : " + infos[0] +" & "+ infos[1]);
                adapter.addItem(new UserRanking(i+1, infos[0], infos[1]));
            }
            Log.d("pr1", String.valueOf(adapter.getItemCount()));
            recyclerView.setAdapter(adapter);

        } catch(Exception e){
            e.printStackTrace();
        }
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
                Log.d("랭킹", "CustomTask2 실행");
                String str;
                URL url = new URL("http://172.30.1.38:8080/sendDataToAndroid/data.jsp");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg = "type=ranking";
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
                    Log.d("랭킹", "receiveMsg : " + receiveMsg);

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