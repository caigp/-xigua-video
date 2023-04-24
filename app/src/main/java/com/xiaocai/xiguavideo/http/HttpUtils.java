package com.xiaocai.xiguavideo.http;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtils {

    private static OkHttpClient client = new OkHttpClient();

    public static void get(String requestTag, String url, Callback callback) {
        Request request = new Request.Builder()
                .get()
                .tag(requestTag)
                .url(url)
                .build();
        client.newCall(request).enqueue(callback);
    }

    public static String syncGet(String requestTag, String url) {
        Request request = new Request.Builder()
                .get()
                .tag(requestTag)
                .url(url)
                .build();
        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void postBodyJson(String requestTag, String url, String bodyJson,
                                    Headers headers, Callback callback) {

        RequestBody requestBody = RequestBody.create(bodyJson, MediaType.parse("application/json"));

        Request.Builder request = new Request.Builder();
        request.tag(requestTag)
                .url(url)
                .post(requestBody);
        if (headers != null) {
            request.headers(headers);
        }

        client.newCall(request.build()).enqueue(callback);
    }

    public static Response postSync(String requestTag, String url) throws IOException {
        FormBody.Builder builder = new FormBody.Builder();

        Request.Builder request = new Request.Builder();
        request.tag(requestTag)
                .url(url)
                .post(builder.build());

        return client.newCall(request.build()).execute();
    }

    public static void cancelRequest(String requestTag) {
        for (Call queuedCall : client.dispatcher().queuedCalls()) {
            if (requestTag.equals(queuedCall.request().tag())) {
                queuedCall.cancel();
            }
        }

        for (Call runningCall : client.dispatcher().runningCalls()) {
            if (requestTag.equals(runningCall.request().tag())) {
                runningCall.cancel();
            }
        }
    }
}
