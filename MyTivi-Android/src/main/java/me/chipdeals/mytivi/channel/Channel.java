package me.chipdeals.mytivi.channel;

import me.chipdeals.mytivi.MyTivi;

public class Channel {

  public String getName() {
    return name;
  }


  public String getThematic() {
    return thematic;
  }

  public int getZapNumber() {
    return zapNumber;
  }

  public String getLogoUrl() {
    return logoUrl + "?apikey=" + MyTivi.getApiKey();
  }


  public String getLogoBlackUrl() {
    return logoBlackUrl + "?apikey=" + MyTivi.getApiKey();
  }

  private String name;
  private String thematic;
  private int zapNumber;
  private String logoUrl;
  private String logoBlackUrl;
}
