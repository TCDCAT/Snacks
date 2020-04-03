package com.example.snacks.http;





import com.example.snacks.api.Api;
import com.example.snacks.util.Base64Utils;

import java.io.IOException;
import java.net.Proxy;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitUtils {
        public static final String BASE_URL = "http://115.28.185.227:4001";
    /**
     * 超时时间
     */
    public static final int TIMEOUT = 60;
    private static volatile RetrofitUtils mInstance;
    private Retrofit mRetrofit;

    public static RetrofitUtils getInstance() {
        if (mInstance == null) {
            synchronized (RetrofitUtils.class) {
                if (mInstance == null) {
                    mInstance = new RetrofitUtils();
                }
            }
        }
        return mInstance;
    }

    private RetrofitUtils() {
        initRetrofit();
    }

    /**
     * 初始化Retrofit
     */
    private void initRetrofit() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        // 设置超时
        builder.connectTimeout(TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(TIMEOUT, TimeUnit.SECONDS);
        builder.writeTimeout(TIMEOUT, TimeUnit.SECONDS);
        builder.proxy(Proxy.NO_PROXY);
//
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                Request request = original.newBuilder()
                        .header("User-Agent", "YouDP")
                        .header("Accept", "application/vnd.youdianpi.v1.full+json")
                        .header("cookie", getRandomString(20))
                        .header("Referer", "www.sxystushop.com")
                        .header("Version", "ic_msg_fans.0.0")
                        .header("h", Base64Utils.encode("我叼你老母，你干嘛爬我的APP"))
                        .header("a", getRandomString(20)+"==")
                        .header("k", getRandomString(10)+ Base64Utils.encode(String.valueOf(System.currentTimeMillis())).replace("==","")+getRandomString(10)+"==")
                        .header("m", getRandomString(15)+"==")
                        .header("s", getRandomString(20)+"==")
                        .method(original.method(), original.body())
                        .build();
                return chain.proceed(request);
            }
        });

        OkHttpClient client = builder.build();
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    private static Api apiService;

    public static String getRandomString(int length) { //length表示生成字符串的长度
        String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    private static final String TAG = "RetrofitUtils";

    public Api create() {
        apiService = mRetrofit.create(Api.class);
        return apiService;
    }
}