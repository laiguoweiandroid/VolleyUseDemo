package com.example.guowei.volleyusedemo.volleyApi;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.guowei.volleyusedemo.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Guowei on 2017/6/8.
 */

public class VolleyUtil {

    private static VolleyUtil mInstance;
    private Context mContext;
    private static RequestQueue mRequestQueue;

    private VolleyUtil(Context context) {
        mContext=context.getApplicationContext();
        mRequestQueue = Volley.newRequestQueue(mContext);

    }
    public static VolleyUtil getmInstance(Context mContext){
        if(mInstance==null){
            synchronized (VolleyUtil.class){
                if(mInstance==null){
                    mInstance=new VolleyUtil(mContext);
                }
            }
        }
       return mInstance;
    }
    public static RequestQueue getQuestQueue(){
        return mRequestQueue;
    }

    /**
     *volley的get请求测试
     * @param url
     * @param listener
     * @param errorListener
     * @return
     */
    public boolean VolleyGetTest(
            String url,
            String tag,
            Response.Listener<String> listener,
            Response.ErrorListener errorListener){
        if(isNetworkAvaliable()){
            //可换成JsonObjectRequest对象，jsonArrayRequest对象，格式一样，返回的数据类型不一致
            StringRequest stringRequest = new StringRequest(
                    Request.Method.GET,
                    url,
                    listener,
                    errorListener
            );
            if(!tag.isEmpty()){
                stringRequest.setTag(tag);
            }
            mRequestQueue.add(stringRequest);

            return true;
        }
      return false;
    }

    /**
     * volley的post请求测试
     * @param url
     * @param phone
     * @param key
     * @param tag
     * @param listener
     * @param errorListener
     * @return
     */
    public boolean volleayPostTest(
            String url,
            final String phone,
            final String key,
            String tag,
            Response.Listener<String> listener,
            Response.ErrorListener errorListener){
        if(isNetworkAvaliable()){
            //可换成JsonObjectRequest对象，jsonArrayRequest对象，格式一样，返回的数据类型不一致
            StringRequest stringRequest= new StringRequest(
                    Request.Method.POST,
                    url,
                    listener,
                    errorListener){
                /**
                 * post请求的参数合成
                 * @return
                 * @throws AuthFailureError
                 */
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> mparams= new HashMap<>();
                    mparams.put("phone",phone);
                    mparams.put("key",key);
                    return mparams;
                }
            };


            if(!tag.isEmpty()){
                stringRequest.setTag(tag);
            }
            mRequestQueue.add(stringRequest);
            return true;
        }
        return false;
    }

    /**
     * volley图片网络请求加载借口测试
     * @param url
     * @param listener
     * @param maxWidth
     * @param maxHeight
     * @param config
     * @param errorListener
     * @return
     */
    public boolean volleyImageRequestTest(
            String url,
            String tag,
            Response.Listener<Bitmap> listener,
            int maxWidth,
            int maxHeight,
            Bitmap.Config config,
            Response.ErrorListener errorListener){
        if(isNetworkAvaliable()){
            ImageRequest imageRequest=new ImageRequest(
                    url,
                    listener,
                    maxWidth,
                    maxHeight,
                    config,
                    errorListener);
            if (!tag.isEmpty()){
                imageRequest.setTag(tag);
            }
              mRequestQueue.add(imageRequest);
            return true;
        }
        return false;
    }

    /**
     *
     * @param url
     * @param v
     * @return
     */
    public boolean volleyimageLoaderTest(
            String url,
            ImageView v){
        if(isNetworkAvaliable()){
            ImageLoader imageLoader =new ImageLoader(mRequestQueue,new BitmapCache());
            ImageLoader.ImageListener listener = imageLoader.getImageListener(v,
                    R.mipmap.ic_launcher,R.mipmap.ic_launcher);
            imageLoader.get(url,listener);
            return true;
        }
     return false;
    }

    /**
     *
     * @param url
     * @param v
     * @return
     */
    public boolean volleyimageLoaderTest(
            String url,
            NetworkImageView v){
        if(isNetworkAvaliable()){
            ImageLoader imageLoader =new ImageLoader(mRequestQueue,new BitmapCache());
            v.setDefaultImageResId(R.mipmap.ic_launcher);
            v.setErrorImageResId(R.mipmap.ic_launcher);
            v.setImageUrl(url,imageLoader);
            return true;
        }
        return false;
    }
    /**
     * 取消请求
     * @param tag
     */
    public void cancelRequestQueue(Object tag){
        if(tag!=null){
            mRequestQueue.cancelAll(tag);
        }
    }
    /**
     * 判断网络是否可用
     * @return
     */
    private boolean isNetworkAvaliable() {
        if(isNetworkAvaliable(mContext)){
            return true;
        }
        return false;
    }

    private boolean isNetworkAvaliable(Context mContext) {
        ConnectivityManager connectivityManager = (android.net.ConnectivityManager)
                mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if(mNetworkInfo!=null&&mNetworkInfo.isAvailable()
                &&mNetworkInfo.isConnected()){
            return true;
        }
        return false;
    }
}
