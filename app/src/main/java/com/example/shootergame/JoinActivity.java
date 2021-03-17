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

public class JoinActivity extends Activity {
    EditText joinNickname, joinId, joinPw, joinPwCheck;
    Button toLoginBtn, joinBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        joinNickname = (EditText) findViewById(R.id.joinNickname);
        joinId = (EditText) findViewById(R.id.joinId);
        joinPw = (EditText) findViewById(R.id.joinPw);
        joinPwCheck = (EditText) findViewById(R.id.joinPwCheck);
        toLoginBtn = (Button) findViewById(R.id.toLoginBtn);
        joinBtn = (Button) findViewById(R.id.joinBtn);
        toLoginBtn.setOnClickListener(btnListener);
        joinBtn.setOnClickListener(btnListener);
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
                sendMsg = "nickname="+strings[0]+"&id="+strings[1]+"&pw="+strings[2]+"&type="+strings[3];
                Log.d("asdasd", sendMsg);
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

    View.OnClickListener btnListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.toLoginBtn : // 로그인 이동
                    Intent intent = new Intent(JoinActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.joinBtn: // 회원가입
                    String nickname = joinNickname.getText().toString();
                    String id = joinId.getText().toString();
                    String pw = joinPw.getText().toString();
                    String pwc = joinPwCheck.getText().toString();

                    if (pw.equals(pwc)) {
                        try {
                            String result = new CustomTask().execute(nickname, id, pw, "join").get();
                            if (result.equals("id")) {
                                Toast.makeText(JoinActivity.this, "이미 존재하는 아이디입니다.", Toast.LENGTH_SHORT).show();
                                joinId.setText("");
                            } else if (result.equals("ok")) {
                                joinNickname.setText("");
                                joinId.setText("");
                                joinPw.setText("");
                                joinPwCheck.setText("");
                                Toast.makeText(JoinActivity.this, "성공적으로 가입되었습니다. 로그인 후 이용해주세요.", Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(JoinActivity.this, "비밀번호가 서로 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };
}

