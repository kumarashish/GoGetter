package techmatics.gogetmydrink;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Credentials;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by Ashish.Kumar on 23-01-2018.
 */

public class WebApiCall {
    OkHttpClient client;
    private  OkHttpClient.Builder client1;
    public static final MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final MediaType mediaTypePlain = MediaType.parse("application/json");
    Context context;
    static OkHttpClient clientForMP;

    public WebApiCall(Context context) {
        client = new OkHttpClient();
        this.context = context;
    }

    private int responseCount(Response response) {
        int result = 1;
        while ((response = response.priorResponse()) != null) {
            result++;
        }
        return result;
    }
    public void getData(String url,final WebApiResponseCallback callback) {
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS).build();
        final Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onError(e.fillInStackTrace().toString());
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    if (response != null) {
                        callback.onSucess(response.body().string());
                    } else {
                        if (response.message() != null) {
                            callback.onError(response.message());
                        } else {
                            callback.onError("No data found!");
                        }

                    }
                }else{
                    callback.onError(response.body().string());
                }
            }
        });
    }



    public String getErrorData() {
        JSONObject object = new JSONObject();
        try {
            object.put("Status", false);
            object.put("Message", "Error occured");
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
        return object.toString();
    }

public void postImage(String url,MediaType mediaTyp, String json, final WebApiResponseCallback callback)
{
    OkHttpClient client = new OkHttpClient();
    RequestBody body = RequestBody.create(mediaTyp, "\""+json+"\"\n");
    Request request = new Request.Builder()
            .url(url)
            .post(body)
            .addHeader("Content-Type", "application/json")
            .addHeader("cache-control", "no-cache")
            .addHeader("Postman-Token", "eb773d89-4cfd-4a41-970f-aeadc8428e81")
            .build();


    client.newCall(request).enqueue(new Callback() {
    @Override
    public void onFailure(Call call, IOException e) {

        callback.onError(e.fillInStackTrace().toString());
    }
    @Override
    public void onResponse(Call call, Response response) throws IOException {
        // cancelProgressDialog(pd);
        if (response.code() == 200 || response.code() == 201) {
            if (response != null) {
                callback.onSucess(response.body().string());
            } else {
                callback.onError(response.message());
            }
        } else {
            callback.onError(response.message());
        }
    }
});
}
    public void postData(String url,MediaType mediaType, String json, final WebApiResponseCallback callback) {
        client.newBuilder().connectTimeout(60000, TimeUnit.MILLISECONDS).readTimeout(60000, TimeUnit.MILLISECONDS).build();
        RequestBody reqBody = RequestBody.create(mediaType, json);
        Request request = new Request.Builder().url(url).addHeader("Content-Type", "application/json").post(reqBody).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                callback.onError(e.fillInStackTrace().toString());
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
               // cancelProgressDialog(pd);
                if (response.code() == 200 || response.code() == 201) {
                    if (response != null) {
                        callback.onSucess(response.body().string());
                    } else {
                        callback.onError(response.message());
                    }
                } else {
                    callback.onError(response.message());
                }
            }
        });
    }
    }

