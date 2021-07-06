package me.chipdeals.mytivi.subscription;

public class SubscriptionStatus {
  public class PriceInfo {
    public int getDurationInMonth() {
      return durationInMonth;
    }

    public long getAmount() {
      return amount;
    }

    public String getCurrency() {
      return currency;
    }

    private int durationInMonth;
    private long amount;
    private String currency;
  }

  public String getBouquetName() {
    return bouquetName;
  }

  public String getNoCard() {
    return noCard;
  }

  public long getDate() {
    return date;
  }

  public PriceInfo getPrice() {
    return price;
  }

  public String getSubscriptionReference() {
    return subscriptionReference;
  }

  public String getSubscriptionStatus() {
    return subscriptionStatus;
  }

  public String getUserPaymentStatus() {
    return userPaymentStatus;
  }

  public String getStatusCodeMessage() {
    return statusCodeMessage;
  }

  public int getStatusCode() {
    return statusCode;
  }

  private String bouquetName;
  private String noCard;
  private String userPaymentStatus;
  private String subscriptionStatus;
  private String subscriptionReference;
  private long date;
  private int statusCode;
  private String statusCodeMessage;
  private PriceInfo price;
}
