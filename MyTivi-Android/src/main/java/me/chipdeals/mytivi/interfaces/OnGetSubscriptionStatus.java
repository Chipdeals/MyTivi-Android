package me.chipdeals.mytivi.interfaces;

import me.chipdeals.mytivi.subscription.SubscriptionStatus;

public interface OnGetSubscriptionStatus extends IEvent {
  public void onGetStatus(SubscriptionStatus subscriptionStatus);
}
