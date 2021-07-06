package me.chipdeals.mytivi.interfaces;

import java.util.ArrayList;

import me.chipdeals.mytivi.bouquet.Bouquet;

public interface OnGetBouquet extends IEvent {
  public void onGetBouquet(ArrayList<Bouquet> bouquets);
}
