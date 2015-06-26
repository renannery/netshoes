package br.com.netshoes.neryandroidexam.network;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import br.com.netshoes.neryandroidexam.helper.Config;
import io.realm.RealmObject;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by nery on 6/26/2015.
 */

public class APIEndpoints {

    private static RestAdapter REST_ADAPTER;

    private static void createAdapterIfNeeded() {
        if (REST_ADAPTER == null) {
            Gson gson = new GsonBuilder()
                    .setExclusionStrategies(new ExclusionStrategy() {
                        @Override
                        public boolean shouldSkipField(FieldAttributes f) {
                            return f.getDeclaringClass().equals(RealmObject.class);
                        }

                        @Override
                        public boolean shouldSkipClass(Class<?> clazz) {
                            return false;
                        }
                    })
                    .setDateFormat("yyyy-MM-dd HH:mm:ss")
                    .create();

            OkHttpClient client = new OkHttpClient();
            client.setConnectTimeout(0, TimeUnit.MILLISECONDS);
            REST_ADAPTER = new RestAdapter.Builder()
                    .setEndpoint(Config.API_PREFIX)
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .setClient(new OkClient(client))
                    .setConverter(new GsonConverter(gson))
                    .build();
        }
    }

    public APIEndpoints() {
        createAdapterIfNeeded();
    }

    public APIEndpointInterface service() {
        return REST_ADAPTER.create(APIEndpointInterface.class);
    }

    public interface APIEndpointInterface {

        @GET(Config.API_SHOTS + Config.API_SHOTS_POPULAR)
        JsonObject returnPopularShots(
                @Query("page") int page);

        @GET(Config.API_SHOTS + "/{id}")
        JsonObject returnShotDetails(
                @Path("id") long id);
    }
}