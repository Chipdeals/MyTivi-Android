package me.chipdeals.mytivi.subscription;

public class SubscriptionInfo {
  public SubscriptionInfo setBouquetName(String bouquetName) {
    this.bouquetName = bouquetName;
    return this;
  }

  public SubscriptionInfo setDurationInMonth(int durationInMonth) {
    this.durationInMonth = durationInMonth;
    return this;
  }

  public SubscriptionInfo setSubscriberInfo(String noSubscriber, String noCard, String firstName,
                                            String lastName, long phoneNumber) {
    subscriberInfo = new SubscriberInfo(noSubscriber, noCard, firstName, lastName, phoneNumber);

    return this;

  }

  public SubscriptionInfo setPaymentInfo(String firstName, String lastName, long phoneNumber,
                                         String countryCode) {
    paymentInfo = new PaymentInfo(firstName, lastName, phoneNumber, countryCode);
    return this;
  }

  class SubscriberInfo {
    SubscriberInfo(String noSubscriber, String noCard, String firstName,
                   String lastName, long phoneNumber) {
      this.noSubscriber = noSubscriber;
      this.noCard = noCard;
      this.firstName = firstName;
      this.lastName = lastName;
      this.phoneNumber = phoneNumber;
    }

    public String getFirstName() {
      return firstName;
    }

    public String getLastName() {
      return lastName;
    }

    public long getPhoneNumber() {
      return phoneNumber;
    }

    public String getNoCard() {
      return noCard;
    }

    public String getNoSubscriber() {
      return noSubscriber;
    }

    private String noSubscriber;
    private String noCard;
    private String firstName;
    private String lastName;
    private long phoneNumber;
  }

  class PaymentInfo {
    PaymentInfo(String firstName, String lastName, long phoneNumber, String countryCode) {
      this.firstName = firstName;
      this.lastName = firstName;
      this.phoneNumber = phoneNumber;
      this.countryCode = countryCode;
    }

    public String getCountryCode() {
      return countryCode;
    }

    public long getPhoneNumber() {
      return phoneNumber;
    }

    public String getFirstName() {
      return firstName;
    }

    public String getLastName() {
      return lastName;
    }

    private String firstName;
    private String lastName;
    private long phoneNumber;
    private String countryCode;
  }

  private String bouquetName;
  private int durationInMonth;
  private SubscriberInfo subscriberInfo;
  private PaymentInfo paymentInfo;
}
