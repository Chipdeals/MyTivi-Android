package me.chipdeals.mytivi;

import android.content.Context;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;
import me.chipdeals.mytivi.bouquet.Bouquet;
import me.chipdeals.mytivi.channel.Channel;
import me.chipdeals.mytivi.interfaces.IEvent;
import me.chipdeals.mytivi.interfaces.OnGetBouquet;
import me.chipdeals.mytivi.interfaces.OnGetBouquetChannels;
import me.chipdeals.mytivi.interfaces.OnGetSubscriptionStatus;
import me.chipdeals.mytivi.interfaces.OnSubscriptionFinished;
import me.chipdeals.mytivi.subscription.SubscriptionInfo;
import me.chipdeals.mytivi.subscription.SubscriptionStatus;
import me.chipdeals.mytivi.subscription.SubscriptionStatusNotifier;

public class MyTivi {
  private MyTivi(Context context) {
    this.context = context;
  }

  public static HashMap<String, OnGetSubscriptionStatus> getPendingSubscriptions() {
    return MyTivi.subscriptionEventAndRef;
  }

  public static MyTivi getInstance() {
    if (instance == null) {
      throw new IllegalStateException("call init() first with the APIKey");
    }
    return instance;
  }

  public static MyTivi init(@NonNull String apiKey, Context context) {
    if (instance == null) {
      MyTivi.apiKey = apiKey;
      AndroidNetworking.initialize(context.getApplicationContext());
      instance = new MyTivi(context);

    }
    return instance;
  }

  public static String getApiKey() {
    return MyTivi.apiKey == null ? "" : MyTivi.apiKey;
  }

  public void getBouquets(@NonNull IEvent onBouquetEvent) {
    if (MyTivi.apiKey == null) {
      throw new IllegalStateException("call init() first with the APIKey");
    }
    fireGetBouquetRequest(onBouquetEvent);
  }

  public void getChannels(@NonNull String bouquetName, IEvent channelEvent) {
    if (MyTivi.apiKey == null) {
      throw new IllegalStateException("call init() first with the APIKey");
    }
    fireGetChannelsRequest(bouquetName, channelEvent);
  }

  private ANRequest buildRequest(String url, @Nullable String pathParameter) {
    if (pathParameter == null) {
      AndroidNetworking.get(url)
          .addQueryParameter("apikey", MyTivi.getApiKey())
          .setTag("bouquets")
          .setPriority(Priority.IMMEDIATE)
          .build();
    }
    return AndroidNetworking.get(url)
        .addPathParameter("pathParameter", pathParameter)
        .addQueryParameter("apikey", MyTivi.getApiKey())
        .setTag("bouquets")
        .setPriority(Priority.IMMEDIATE)
        .build();
  }

  private void fireGetBouquetRequest(IEvent onBouquetEvent) {
    buildRequest(bouquetsUrl, null)
        .getAsJSONObject(new JSONObjectRequestListener() {
          @Override
          public void onResponse(JSONObject response) {
            try {
              JSONArray bouquetJson = response.getJSONArray("bouquets");
              Gson gson = new Gson();
              ArrayList<Bouquet> bouquets = gson.fromJson(bouquetJson.toString(),
                  new TypeToken<ArrayList<Bouquet>>() {}.getType());
              ((OnGetBouquet) onBouquetEvent).onGetBouquet(bouquets);
            } catch (JSONException e) {
              e.printStackTrace();
            }

          }

          @Override
          public void onError(ANError error) {
            //TODO document error codes ?
            JSONObject json;
            try {
              json = new JSONObject(error.getErrorBody());
              String message = json.getString("message");
              onBouquetEvent.onFailure(error.getErrorCode(), message);
            } catch (JSONException e) {
              e.printStackTrace();
            }

          }
        });


  }

  private void fireGetChannelsRequest(String bouquetName, IEvent onChannelEvent) {
    buildRequest(channelsUrl, bouquetName)
        .getAsJSONObject(new JSONObjectRequestListener() {
          @Override
          public void onResponse(JSONObject response) {
            try {
              JSONArray bouquetJson = response.getJSONArray("channels");
              Gson gson = new Gson();
              ArrayList<Channel> channels = gson.fromJson(bouquetJson.toString(),
                  new TypeToken<ArrayList<Channel>>() {}.getType());
              ((OnGetBouquetChannels) onChannelEvent).onGetBouquetChannels(channels);
            } catch (JSONException e) {
              e.printStackTrace();
            }

          }

          @Override
          public void onError(ANError error) {
            JSONObject json;
            try {
              json = new JSONObject(error.getErrorBody());
              String message = json.getString("message");
              onChannelEvent.onFailure(error.getErrorCode(), message);
            } catch (JSONException e) {
              e.printStackTrace();
            }
          }
        });
  }

  public void subscribe(SubscriptionInfo subscriptionInfo, IEvent subscriptionEvent) {
    Gson gson = new Gson();
    AndroidNetworking.post(subscribeUrl)
        .addQueryParameter("apikey", MyTivi.getApiKey())
        .addStringBody(gson.toJson(subscriptionInfo))
        .setPriority(Priority.IMMEDIATE)
        .setContentType("application/json")
        .build()
        .getAsJSONObject(new JSONObjectRequestListener() {
          @Override
          public void onResponse(JSONObject response) {
            Gson gson = new Gson();
            SubscriptionStatus status = gson.fromJson(response.toString(),
                new TypeToken<SubscriptionStatus>() {}.getType());
            ((OnSubscriptionFinished) subscriptionEvent).onSubscribeFinished(status);
            Data inputData = new Data.Builder()
                .putString("subscriptionRef", status.getSubscriptionReference())
                .build();
            subscriptionEventAndRef.put(status.getSubscriptionReference(), (OnGetSubscriptionStatus) subscriptionEvent);
            WorkRequest checkSubscriptionStatus = new OneTimeWorkRequest.Builder(SubscriptionStatusNotifier.class)
                .setInputData(inputData)
                .setInitialDelay(20, TimeUnit.SECONDS)
                .build();
            WorkManager
                .getInstance(MyTivi.this.context)
                .enqueue(checkSubscriptionStatus);
          }

          @Override
          public void onError(ANError error) {
            JSONObject json;
            try {
              json = new JSONObject(error.getErrorBody());
              String message = json.getString("message");
              subscriptionEvent.onFailure(error.getErrorCode(), message);
            } catch (JSONException e) {
              e.printStackTrace();
            }
          }
        });
  }

  public void getSubscriptionStatus(String subscriptionReference, IEvent subscriptionEvent) {
    buildRequest(subscriptionUrl, subscriptionReference).getAsJSONObject(new JSONObjectRequestListener() {
      @Override
      public void onResponse(JSONObject response) {
        Gson gson = new Gson();
        SubscriptionStatus status = gson.fromJson(response.toString(),
            new TypeToken<SubscriptionStatus>() {}.getType());
        ((OnGetSubscriptionStatus) subscriptionEvent).onGetStatus(status);

      }

      @Override
      public void onError(ANError error) {
        JSONObject json;
        try {
          json = new JSONObject(error.getErrorBody());
          String message = json.getString("message");
          subscriptionEvent.onFailure(error.getErrorCode(), message);
        } catch (JSONException e) {
          e.printStackTrace();
        }
      }
    });
  }

  private static final String bouquetsUrl = "https://apis.chipdeals.me/mytiviplus/bouquets";
  private static final String subscribeUrl = "https://apis.chipdeals.me/mytiviplus/subscribe/";
  private static final String subscriptionUrl = "https://apis.chipdeals" +
      ".me/mytiviplus/subscription/{pathParameter}";
  private static final String channelsUrl = "https://apis.chipdeals.me/mytiviplus/channels/{pathParameter}";
  private static String apiKey = null;
  private static MyTivi instance = null;
  private Context context = null;
  private static final HashMap<String, OnGetSubscriptionStatus> subscriptionEventAndRef =
      new HashMap<>();
  public final int STATUS_END_USER_SUBSCRIBED = 200;
  public final int STATUS_WAITING_PAYMENT = 202;
  public final int STATUS_PAYMENT_DONE_SUBSCRIPTION_PENDING = 230;
  public final int STATUS_PAYMENT_UNDEFINED_ERROR = 460;
  public final int STATUS_NOT_ENOUGH_MONEY = 529;
  public final int STATUS_SERVER_ERROR = 530;
  public final int STATUS_PAYMENT_DONE_SUBSCRIPTION_ERROR = 531;
}