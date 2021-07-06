package me.chipdeals.mytivi.interfaces;

import me.chipdeals.mytivi.subscription.SubscriptionStatus;

public interface OnSubscriptionFinished {
  void onSubscribeFinished(SubscriptionStatus subscriptionStatus);
}
