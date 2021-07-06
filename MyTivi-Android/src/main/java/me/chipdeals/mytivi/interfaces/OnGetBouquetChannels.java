package me.chipdeals.mytivi.interfaces;

import java.util.ArrayList;

import me.chipdeals.mytivi.channel.Channel;

public interface OnGetBouquetChannels extends IEvent {
  void onGetBouquetChannels(ArrayList<Channel> channels);
}
