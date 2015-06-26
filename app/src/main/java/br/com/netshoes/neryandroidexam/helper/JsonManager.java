package br.com.netshoes.neryandroidexam.helper;

/**
 * Created by nery on 6/26/2015.
 */

import android.util.Log;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import br.com.netshoes.neryandroidexam.model.GenericBus;
import br.com.netshoes.neryandroidexam.model.Shot;
import br.com.netshoes.neryandroidexam.network.APIEndpoints;
import de.greenrobot.event.EventBus;
import io.realm.RealmObject;
import retrofit.mime.TypedFile;

public class JsonManager {

    private static JsonManager instance;
    private Gson gson;

    private JsonManager() {
        gson = new GsonBuilder()
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
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

    }

    public static JsonManager getInstance() {
        if(instance == null) {
            instance = new JsonManager();
        }
        return instance;
    }

    public void popularShots (final int page) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JsonObject jo = new APIEndpoints().service().returnPopularShots(page);
                    Type typeShots = new TypeToken<ArrayList<Shot>>() {}.getType();
                    ArrayList<Shot> shots = gson.fromJson(jo.getAsJsonArray("shots"), typeShots);

                    EventBus.getDefault().post(new GenericBus(GenericBus.SHOTS_LIST, shots));
                } catch (Exception e) {
                    Log.e("ERROR!", "MESSAGE --> PARSE popularShots: ".concat(e.toString()));
                    EventBus.getDefault().post(new GenericBus(GenericBus.SHOTS_LIST, null));
                }
            }
        }).start();
    }

    public void shotDetails (final long id) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JsonObject jo = new APIEndpoints().service().returnShotDetails(id);
                    Shot shot = gson.fromJson(jo, Shot.class);

                    EventBus.getDefault().post(new GenericBus(GenericBus.SHOTS_DETAILS, shot));
                } catch (Exception e) {
                    Log.e("ERROR!", "MESSAGE --> PARSE shotDetails: ".concat(e.toString()));
                    EventBus.getDefault().post(new GenericBus(GenericBus.SHOTS_DETAILS, null));
                }
            }
        }).start();
    }

}
