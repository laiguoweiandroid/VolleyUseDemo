package com.example.guowei.volleyusedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.guowei.volleyusedemo.volleyApi.VolleyUtil;

public class MainActivity extends AppCompatActivity {
    private TextView tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvContent = (TextView) findViewById(R.id.tv_content);
    }

    /**
     * get请求
     * @param v
     */
    public void doGet(View v){
        String url="http://apis.juhe.cn/mobile/get?phone=13429667914&key=20a426be714235de1ed08f44c5351b82";
        String tag ="get";
        VolleyUtil.getmInstance(this).cancelRequestQueue(tag);
        VolleyUtil.getmInstance(this).VolleyGetTest(url, tag, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.isEmpty()){
                    tvContent.setText(response);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.v("volleyError",volleyError.toString());

            }
        });

    }

    /**
     * post请求
     * @param v
     */
    public void doPost(View v){

        String url="http://apis.juhe.cn/mobile/get?";
        String tag ="post";
        String phone="13429667914";
        String key="20a426be714235de1ed08f44c5351b82";
        VolleyUtil.getmInstance(this).cancelRequestQueue(tag);
        VolleyUtil.getmInstance(this).volleayPostTest(url, phone,key,tag, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.isEmpty()){
                    tvContent.setText(response);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.v("volleyError",volleyError.toString());

            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        VolleyUtil.getmInstance(this).cancelRequestQueue("get");
        VolleyUtil.getmInstance(this).cancelRequestQueue("post");
    }
}
