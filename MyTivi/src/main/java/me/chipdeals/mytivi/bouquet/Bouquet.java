package me.chipdeals.mytivi.bouquet;

import me.chipdeals.mytivi.MyTivi;
import me.chipdeals.mytivi.channel.Channel;

public final class Bouquet {

  public class Country {

    public String getCountryCode() {
      return countryCode;
    }

    public String getCountryName() {
      return countryName;
    }

    private String countryCode;
    private String countryName;
  }

  public class Price {

    public int getAmount() {
      return amount;
    }


    public String getUnit() {
      return unit;
    }

    public Currency getCurrency() {
      return this.currency;
    }

    public class Currency {
      public String getCode() {
        return code;
      }

      public String getName() {
        return name;
      }

      public String getSymbol() {
        return symbol;
      }


      private String code;
      private String name;
      private String symbol;
    }

    private String unit;
    private int amount;
    private Currency currency;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getTagLine() {
    return this.tagline;
  }

  public void setTagLine(String tagLine) {
    this.tagline = tagLine;
  }

  public long getTimezonesInSecond() {
    return timezonesInSecond;
  }

  public void setTimezonesInSecond(long timezonesInSecond) {
    this.timezonesInSecond = timezonesInSecond;
  }

  public boolean HaveAdultOption() {
    return haveAdultOption;
  }

  public void setHaveAdultOption(boolean haveAdultOption) {
    this.haveAdultOption = haveAdultOption;
  }

  public boolean haveCanalPlus() {
    return haveCanalPlus;
  }

  public void setHaveCanalPlus(boolean haveCanalPlus) {
    this.haveCanalPlus = haveCanalPlus;
  }

  public boolean haveMyCanal() {
    return haveMyCanal;
  }

  public void setHaveMyCanal(boolean haveMyCanal) {
    this.haveMyCanal = haveMyCanal;
  }

  public String getBouquetIllustrationUrl() {
    return bouquetIllustrationUrl + "?apikey=" + MyTivi.getApiKey();
  }

  public void setBouquetIllustrationUrl(String bouquetIllustrationUrl) {
    this.bouquetIllustrationUrl = bouquetIllustrationUrl;
  }

  public Country getCountry() {
    return country;
  }

  /*public void setCountry(String countryCode, String countryName) {
    Country country = new Country();
    country.setCountryCode(countryCode);
    country.setCountryName(countryName);
    this.country = country;
  }*/

  public Price getPrice() {
    return price;
  }

  public Channel[] getDefaultChannels() {
    return defaultChannels;
  }

  private String name;
  private String description;
  private String tagline;
  private boolean haveAdultOption;
  private boolean haveCanalPlus;
  private boolean haveMyCanal;
  private long timezonesInSecond;
  private String bouquetIllustrationUrl;
  private Price price;
  private Country country;
  private Channel[] defaultChannels;
}

