package me.chipdeals.mytivitestsdk;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import me.chipdeals.mytivi.MyTivi;
import me.chipdeals.mytivi.bouquet.Bouquet;
import me.chipdeals.mytivi.channel.Channel;
import me.chipdeals.mytivi.interfaces.OnGetBouquet;
import me.chipdeals.mytivi.interfaces.OnGetBouquetChannels;
import me.chipdeals.mytivi.interfaces.OnGetSubscriptionStatus;
import me.chipdeals.mytivi.interfaces.OnSubscriptionFinished;
import me.chipdeals.mytivi.subscription.SubscriptionInfo;
import me.chipdeals.mytivi.subscription.SubscriptionStatus;

public class MainActivity extends AppCompatActivity implements OnGetBouquet, OnGetBouquetChannels
    , OnGetSubscriptionStatus, OnSubscriptionFinished {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    myTivi = MyTivi.init("haz6Ac5jX0Pp436Bkq5Sl7nMDEyasxrB", this.getBaseContext());
    myTivi.getBouquets(this);


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////

    SubscriptionInfo subscriptionInfo = new SubscriptionInfo();
    subscriptionInfo.setBouquetName("access")
        .setDurationInMonth(1)
        .setPaymentInfo("khaled", "ahounou", 90630401, "BJ")
        .setSubscriberInfo("12345678901234", "12345678901234", "khaled", "ahounou", 90630401);
    myTivi.subscribe(subscriptionInfo, this);
  }

  @Override
  public void onFailure(int Error, String message) {
    Log.e("TAG", message);
  }

  @Override
  public void onGetBouquet(ArrayList<Bouquet> bouquets) {
    for (Bouquet bouquet : bouquets) {
      Log.i("TAG", bouquet.getName().toUpperCase());
      Log.i("TAG", bouquet.getCountry().getCountryName());
      Log.i("TAG",
          "" + bouquet.getPrice().getAmount() + " " + bouquet.getPrice().getCurrency().getSymbol());
      Log.i("TAG", bouquet.getDescription());
      Log.i("TAG", bouquet.getTagLine());
      for (Channel channel : bouquet.getDefaultChannels()) {
        Log.i("TAG", channel.getName());
      }

      Log.i("TAG", bouquet.getBouquetIllustrationUrl());
      myTivi.getChannels(bouquet.getName(), this);
    }
  }

  @Override
  public void onGetBouquetChannels(ArrayList<Channel> channels) {
    for (Channel channel : channels) {
      Log.i("TAG", channel.getName());
      Log.i("TAG", channel.getThematic());
      Log.i("TAG", "zap :" + channel.getZapNumber());
    }

  }

  private MyTivi myTivi = null;

  @Override
  public void onGetStatus(SubscriptionStatus subscriptionStatus) {
    Log.i("TAG", subscriptionStatus.getSubscriptionReference());
    Log.i("TAG", subscriptionStatus.getBouquetName());
    Log.i("TAG", subscriptionStatus.getPrice().getAmount() + " " + subscriptionStatus.getPrice().getCurrency());
    Log.i("TAG", "subscription " + subscriptionStatus.getSubscriptionStatus());
    Log.i("TAG", "payment " + subscriptionStatus.getUserPaymentStatus());

  }

  @Override
  public void onSubscribeFinished(SubscriptionStatus subscriptionStatus) {
    Log.i("TAG", subscriptionStatus.getSubscriptionReference());
//    myTivi.getSubscriptionStatus(subscriptionStatus.getSubscriptionReference(), this);
  }
}