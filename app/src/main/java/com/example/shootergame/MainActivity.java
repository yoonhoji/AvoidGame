package com.example.shootergame;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.io.*;
import java.net.*;

public class MainActivity extends Activity {

    public static UserVO user = new UserVO();

    EditText userId, userPw;
    Button loginBtn, toJoinBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userId = (EditText) findViewById(R.id.userId);
        userPw = (EditText) findViewById(R.id.userPw);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        toJoinBtn = (Button) findViewById(R.id.toJoinBtn);
        loginBtn.setOnClickListener(btnListener);
        toJoinBtn.setOnClickListener(btnListener);
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
                sendMsg = "id="+strings[0]+"&pw="+strings[1]+"&type="+strings[2];
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

                    if (receiveMsg.equals("false")){
                        return "false";
                    } else if (receiveMsg.equals("noId")){
                        return "noId";
                    } else {
                        String[] userInfo = receiveMsg.split(",");

                        user.setUseq(Integer.parseInt(userInfo[0]))
                                .setNickname(userInfo[1])
                                .setId(userInfo[2])
                                .setPw(userInfo[3])
                                .setRp(Integer.parseInt(userInfo[4]))
                                .setPoint(Integer.parseInt(userInfo[5]))
                                .setStatus(userInfo[6]);

                        return user.getStatus();
                    }

                } else {
                    Log.i("통신 결과", conn.getResponseCode()+"에러");
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "";
        }
    }

    View.OnClickListener btnListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.loginBtn : // 로그인 버튼 눌렀을 경우
                    String loginId = userId.getText().toString();
                    String loginPw = userPw.getText().toString();
                    try {
                        String result = new CustomTask().execute(loginId,loginPw,"login").get();
                        if(result.equals("Valid")) {
                            Toast.makeText(MainActivity.this,"로그인 성공",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                            intent.putExtra("user", user);
                            intent.putExtra("rp", 0);
                            intent.putExtra("point", 0);
                            startActivity(intent);
                            finish();
                        } else if(result.equals("Invalid")) {
                            Toast.makeText(MainActivity.this,"이미 탈퇴한 회원입니다.",Toast.LENGTH_SHORT).show();
                            userId.setText("");
                            userPw.setText("");
                        } else if(result.equals("false")) {
                            Toast.makeText(MainActivity.this,"비밀번호가 일치하지 않습니다.",Toast.LENGTH_SHORT).show();
                            userId.setText("");
                            userPw.setText("");
                        } else if(result.equals("noId")) {
                            Toast.makeText(MainActivity.this,"존재하지 않는 아이디입니다.",Toast.LENGTH_SHORT).show();
                            userId.setText("");
                            userPw.setText("");
                        }
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.toJoinBtn: // 회원가입 이동
                    Intent intent = new Intent(MainActivity.this, JoinActivity.class);
                    startActivity(intent);
                    finish();
                    break;
            }
        }
    };
}

