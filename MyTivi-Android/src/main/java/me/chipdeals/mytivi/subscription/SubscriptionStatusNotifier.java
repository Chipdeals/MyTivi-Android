package me.chipdeals.mytivi.subscription;

import android.content.Context;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.ANResponse;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import me.chipdeals.mytivi.MyTivi;
import me.chipdeals.mytivi.interfaces.OnGetSubscriptionStatus;

public class SubscriptionStatusNotifier extends Worker {
  public SubscriptionStatusNotifier(@NonNull Context context, @NonNull WorkerParameters params) {
    super(context, params);
  }

  @Override
  public Result doWork() {
    String subscriptionRef = getInputData().getString("subscriptionRef");
    Log.i("TAG", subscriptionRef);

    ANRequest request = AndroidNetworking.get("https://apis.chipdeals.me/mytiviplus/subscription/{pathParameter}")
        .addPathParameter("pathParameter", subscriptionRef)
        .addQueryParameter("apikey", MyTivi.getApiKey())
        .build();
    ANResponse<SubscriptionStatus> response = request.executeForObject(SubscriptionStatus.class);
    if (response.isSuccess()) {
      SubscriptionStatus subscriptionStatus = response.getResult();
      if (subscriptionStatus.getSubscriptionStatus().compareToIgnoreCase("pending") != 0) {
        OnGetSubscriptionStatus subscriptionEvent =
            MyTivi.getPendingSubscriptions().get(subscriptionStatus.getSubscriptionReference());
        Log.i("TAG", "pending subscriptions : " + MyTivi.getPendingSubscriptions().size());
        if (subscriptionEvent != null) {
          subscriptionEvent.onGetStatus(subscriptionStatus);
          MyTivi.getPendingSubscriptions().remove(subscriptionStatus.getSubscriptionReference());
        }
        Log.i("TAG", subscriptionStatus.getSubscriptionStatus());
        return Result.success();
      } else {
        return Result.retry();
      }
    }
    return Result.failure();
  }


}

