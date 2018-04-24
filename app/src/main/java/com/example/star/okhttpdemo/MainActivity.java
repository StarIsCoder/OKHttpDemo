package com.example.star.okhttpdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private String url = "http://192.168.8.101:8080/TestForCSDN/";
    //1、拿到OkHttpClient对象 可以设置全局信息 例如timeout
    private OkHttpClient okHttpClient = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.get_textView);
    }

    public void doPost(View view) {
        //构造FormBody
        FormBody.Builder formBody = new FormBody.Builder();
        formBody.add("username", "wangshenxing");
        formBody.add("password", "star");
        Request.Builder builder = new Request.Builder();
        Request request = builder.url(url + "test").post(formBody.build()).build();
        try {
            execRequest(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void doPostString(View view) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plan;charset=utf-8"),
                "{username:wangshenxing,password:123}");
        Request.Builder builder = new Request.Builder();
        Request request = builder.url(url + "postString").post(requestBody).build();
        try {
            execRequest(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void doGet(View view) {
        //2、构造Request
        Request.Builder builder = new Request.Builder();
        Request request = builder.get().url(url + "test").build();
        try {
            execRequest(request);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void execRequest(Request request) throws IOException {
        //3、封装Call
        Call call = okHttpClient.newCall(request);
        //4、执行call
        //直接执行
        //        try {
        //            call.execute();
        //        } catch (IOException e) {
        //            e.printStackTrace();
        //        }
        //异步执行
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                Log.d("wangshenxing", "response body :" + result);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(result);
                    }
                });
            }
        });
    }


}
