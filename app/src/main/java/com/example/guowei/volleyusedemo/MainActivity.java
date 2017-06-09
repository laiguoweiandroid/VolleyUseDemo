package com.example.guowei.volleyusedemo;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.guowei.volleyusedemo.volleyApi.VolleyUtil;

public class MainActivity extends AppCompatActivity {
    /**
     * ImageCache
     * LruCache
     * ImageLoader
     * ImageRequest
     * NetworkImageView
     * ImageListener
     */
    private TextView tvContent;
    private ImageView imageView,imageView1;
    private NetworkImageView networkImageview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvContent = (TextView) findViewById(R.id.tv_content);
        imageView = (ImageView) findViewById(R.id.ivImage);
        imageView1 = (ImageView) findViewById(R.id.ivImage1);
        networkImageview = (NetworkImageView) findViewById(R.id.netImageView);
        loadImageView();
        loadImageViewByImageLoader();
        loadNetWorkImageView();

    }

    private void loadNetWorkImageView() {
        String url ="https://www.baidu.com/img/bdlogo.png";
        VolleyUtil.getmInstance(this).volleyimageLoaderTest(url,networkImageview);
    }

    private void loadImageViewByImageLoader() {
        String url ="https://www.baidu.com/img/bdlogo.png";
        VolleyUtil.getmInstance(this).volleyimageLoaderTest(url,imageView1);

    }

    private void loadImageView() {
        String url ="https://www.baidu.com/img/bdlogo.png";
        String tag ="image";
        VolleyUtil.getmInstance(this).cancelRequestQueue("image");
        VolleyUtil.getmInstance(this).volleyImageRequestTest(url, tag, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                if(response!=null){
                    imageView.setImageBitmap(response);
                }

            }
        }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("",error.toString());

            }
        });
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
