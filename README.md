
# Mytivi+ Android Library

## À propos Mytivi+ Android Library
Bienvenue dans la documentation pour les développeurs Mytivi+ Android. Récupérez les bouquets, la liste des chaînes Canal+ par bouquet et (re)abonnez vos clients.

## Utiliser Mytivi+ dans votre application 

Ajouter ceci dans votre fichier  *build.gradle*
```groovy
implementation 'me.chipdeals.mytiti:mytivi-android:0.0.1'
```
N'oublier pas d'ajouter la permission  internet dans le manifest si ce n'est fait
```xml
<uses-permission android:name="android.permission.INTERNET" />
```
Dans l'activité qui utilise MyTivi, initialiser le SDK dans la méthode onCreate() :
```java
myTivi = MyTivi.init("<API_KEY>", this.getBaseContext());
```
**Obtenez une <API_KEY> en écrivant à l'adresse Email mytivi@chipdeals.me**

### Récupérer la liste des bouquets Canal+
```java
public class MainActivity extends AppCompatActivity implements OnGetBouquet {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    myTivi = MyTivi.init("<API_KEY>", this.getBaseContext());
    myTivi.getBouquets(this);
  }

 @Override
  public void onGetBouquet(ArrayList<Bouquet> bouquets) {
    for (Bouquet bouquet : bouquets) {
      Log.i("TAG", bouquet.getName());
      //... pour plus de méthodes de l'objet Bouquet, voir plus bas
    }
  }

  private MyTivi myTivi;

}  
```
---
<br/>

### Récupérer la liste des chaînes d'un bouquet
```java
public class MainActivity extends AppCompatActivity implements OnGetBouquetChannels {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    myTivi = MyTivi.init("<API_KEY>", this.getBaseContext());
    String bouquetName = "tout canal+"; //ou le récupérer dynamiquement avec la méthode MyTivi.getBouquets();
    myTivi.getChannels(bouquetName, this);
  }

 @Override
  public void onGetBouquetChannels(ArrayList<Channel> channels) {
    for (Channel channel : channels) {
      Log.i("TAG", channel.getName());
      //... pour plus de méthodes de l'objet Channel, voir plus bas
    }
  }

  private MyTivi myTivi;

}  
```
---
<br/>

### Récupérer la liste des chaînes de tous les bouquets
```java
public class MainActivity extends AppCompatActivity implements OnGetBouquet, OnGetBouquetChannels {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    myTivi = MyTivi.init("<API_KEY>", this.getBaseContext());
    myTivi.getBouquets(this);
  }

 @Override
    public void onGetBouquet(ArrayList<Bouquet> bouquets) {
    for (Bouquet bouquet : bouquets) {
      String bouquetName = bouquet.getName();
      myTivi.getChannels(bouquet.getName(), this);
    }
  }

  @Override
  public void onGetBouquetChannels(ArrayList<Channel> channels) {
    for (Channel channel : channels) {
      Log.i("TAG", channel.getName());
      //... pour plus de méthodes de l'objet Channel, voir plus bas
    }

  }


  private MyTivi myTivi;

}  
```
---
<br/>

### Abonner un client à un bouquet Canal +
```java
public class MainActivity extends AppCompatActivity implements OnSubscriptionFinished {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    myTivi = MyTivi.init("<API_KEY>", this.getBaseContext());
    
    SubscriptionInfo subscriptionInfo = new SubscriptionInfo();
    String bouquetName = "access";
    int durationInMonth = 1; //ou 2, 3, 6, 12
    String paymentInfoFirstName = "Paul";
    String paymentInfoLastName = "Emerik";
    long paymentInfoPhoneNumber = 51020306;
    String paymentInfoCountryCode = "BJ";
    String noSubscriber = "23900119420012"; 
    String noCard = "99663554422112";
    String subscriberFirstName = "Pierre";
    String subscriberLastName = "Emerik";
    long subscriberPhoneNumber = 90630405;

    subscriptionInfo.setBouquetName(bouquetName)
        .setDurationInMonth(durationInMonth)
        .setPaymentInfo(paymentInfoFirstName, paymentInfoLastName, paymentInfoPhoneNumber, paymentInfoCountryCode)
        .setSubscriberInfo(noSubscriber, noCard, subscriberFirstName, subscriberLastName, subscriberPhoneNumber);

    myTivi.subscribe(subscriptionInfo, this);
  }

  @Override
  public void onSubscribeFinished(SubscriptionStatus subscriptionStatus) {
    Log.i("TAG", subscriptionStatus.getSubscriptionReference());
    //... pour plus de méthodes de l'objet SubscriptionStatus, voir plus bas
  }

  private MyTivi myTivi;

}  
```
---
<br/>

### Récupérer le status d'une subscription à tout instant
```java
public class MainActivity extends AppCompatActivity implements OnGetSubscriptionStatus {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    myTivi = MyTivi.init("<API_KEY>", this.getBaseContext());

  }

  @Override
  public void onGetStatus(SubscriptionStatus subscriptionStatus) {
    Log.i("TAG", subscriptionStatus.getSubscriptionReference());
    //... pour plus de méthodes de l'objet SubscriptionStatus, voir plus bas
  }
public class MainActivity extends AppCompatActivity implements OnGetSubscriptionStatus {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    myTivi = MyTivi.init("<API_KEY>", this.getBaseContext());

  }

  @Override
  public void onGetStatus(SubscriptionStatus subscriptionStatus) {
    Log.i("TAG", subscriptionStatus.getSubscriptionReference());
    //... pour plus de méthodes de l'objet SubscriptionStatus, voir plus bas
  }

  private MyTivi myTivi;

}  
```
<br/>
<br/>
<br/>

### Les méthodes de l'objet bouquet

```java
public class MainActivity extends AppCompatActivity implements OnSubscriptionFinished {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    myTivi = MyTivi.init("<API_KEY>", this.getBaseContext());
    
    SubscriptionInfo subscriptionInfo = new SubscriptionInfo();
    String bouquetName = "access";
    int durationInMonth = 1; //ou 2, 3, 6, 12
    String paymentInfoFirstName = "Paul";
    String paymentInfoLastName = "Emerik";
    long paymentInfoPhoneNumber = 51020306;
    String paymentInfoCountryCode = "BJ";
    String noSubscriber = "23900119420012"; 
    String noCard = "99663554422112";
    String subscriberFirstName = "Pierre";
    String subscriberLastName = "Emerik";
    long subscriberPhoneNumber = 90630405;

    subscriptionInfo.setBouquetName(bouquetName)
        .setDurationInMonth(durationInMonth)
        .setPaymentInfo(paymentInfoFirstName, paymentInfoLastName, paymentInfoPhoneNumber, paymentInfoCountryCode)
        .setSubscriberInfo(noSubscriber, noCard, subscriberFirstName, subscriberLastName, subscriberPhoneNumber);

    myTivi.subscribe(subscriptionInfo, this);
  }

  @Override
  public void onSubscribeFinished(SubscriptionStatus subscriptionStatus) {
    Log.i("TAG", subscriptionStatus.getSubscriptionReference());
    //... pour plus de méthodes de l'objet SubscriptionStatus, voir plus bas
  }

  private MyTivi myTivi;

}  
```
<br/>
<br/>
<br/>

### Les méthodes de l'objet bouquet

<u>MyTivi</u>: Il s'agit de l'objet pricipale qui permet d'effectuer les differentes opérations
```java
myTivi.init(String apiKey, Context context); // Initialise l'objet MyTiviplus avec l'api key
myTivi.getInstance(); // Retourne l'instance MyTivi qui a été initialisée
myTivi.getApiKey(); // Retourne l'api key utilisé pour l'initialisation
myTivi.getBouquets(IEvent onBouquetEvent); // Lance la récupération de la liste des bouquets
myTivi.getChannels(String bouquetName, IEvent channelEvent); // Lancce la récupération de la liste des chaines d'un bouquet
myTivi.subscribe(SubscriptionInfo subscriptionInfo, IEvent subscriptionEvent); // Lance l'abonenemnt d'un client 
myTivi.getSubscriptionStatus(String subscriptionReference, IEvent subscriptionEvent); // Lance la récupération du status d'un abonnement
myTivi.getPendingSubscriptions(); // Retourne la liste d'abonnement en attente de réponse
```

<u>Bouquet</u>: Contient les informations d'un bouquet Canal+
```java
bouquet.getName(); // Retourne le nom du bouquet
bouquet.getDescription(); // Retourne la description du bouquet
bouquet.getTagLine(); // Retourne le slogan du bouquet
bouquet.getTimezonesInSecond(); // Retourne l'écart d'heure en seconde du pays du bouquet par rapport au GMT
bouquet.HaveAdultOption(); // Retourne l'inclusion des chaines réservées aux adultes
bouquet.haveCanalPlus(); // Retourne l'inclusion des chaines CANAL+
bouquet.haveMyCanal(); // Retourne S'il est possible d'avoir les chaînes de ce bouquet dans l'application myCanal
bouquet.getBouquetIllustrationUrl(); // Retourne l'url de l'image d'illustration du bouquet
bouquet.getCountry(); // Retourne un objet Country qui décris le pays auquel est lié le bouquet.
bouquet.getPrice(); // Retourne un Objet Price qui contient le prix du bouquet.
bouquet.getSampleChannels(); // Retourne un Tableau d'objets Channel qui donne quelques examples de chaînes mises en avant par ce bouquet.
```

<u>Country:</u> Décris le pays auquel est lié un bouquet
```java
country.getCountryCode(); // Retourne le code à 2 caractères du pays auquel est lié le bouquet
country.getCountryName(); // Retourne le nom du pays auquel est lié le bouquet
```

<u>Price:</u> Contiens les informations liées aux prix du bouquet
```java
price.getAmount(); // Retourne le montant unitaire d'un abonnement à ce bouquet
price.getUnit(); // Retourne la durée unitaire de l'abonnement
price.getCurrency(); // Retourne les informations liées à la devise du pays de l'abonnement
```

<u>Currency:</u> Contiens les informations relactives à la du pays de l'abonnement
```java
currency.getCode(); // Retourne le code ISO de la devise
currency.getName(); // Retourne le nom de la devise
currency.getSymbol(); // Retourne le symbole de la devise
```

<u>Channel:</u> contenant toutes les informations d'une chaîne
```java
channel.getName(); // Retourne le nom de la chaîne
channel.getThematic(); // Retourne le sujet autour duquel se concentre la chaîne
channel.getZapNumber(); // Retourne le numéro de la chaîne sur le décodeur
channel.getLogoUrl(); // Retourne l'url du logo pour thême normal de la chaîne
channel.getLogoBlackUrl(); // Retourne l'url du logo pour thême sombre de la chaîne
```

<u>SubscribtionStatus:</u> Contiens les informations relatives au status d'un abonnement
```java
subscriptionStatus.getBouquetName(); // Retourne le nom du bouquet auquel a souscri le client
subscriptionStatus.getNoCard(); // Retourne le numéro de la carte du décodeur sur lequel est fait l'abonnement
subscriptionStatus.getDate(); // Retourne retourne la date à laquelle l'abonnement est fait
subscriptionStatus.getPrice(); // Retourne l'objet PriceInfo qui contient les information relatives au prix de l'abonnement lancé
subscriptionStatus.getSubscriptionReference(); // Retourne le code qui permet d'itentifier avec unicité un abonnement
subscriptionStatus.getUserPaymentStatus(); // Retourne du paiement de l'utilisateur. Contient les valeurs: "success" ou "pending" ou "error".
subscriptionStatus.getSubscriptionStatus(); // Retourne l'état de l'abonnement de l'utilisateur. Contient les valeurs: "success" ou "pending" ou "error".
subscriptionStatus.getStatusCodeMessage(); // Retourne un message qui informe sur l'état précis de l'abonnement.
subscriptionStatus.getStatusCode(); // Retourne un code qui informe sur l'état précis de l'abonnement. Voir plus bas pour plus de description
```

<u>PriceInfo:</u> Contiens les information relatives au prix de l'abonnement lancé
```java
channel.getDurationInMonth(); // Retourne la durée mensuelle de l'abonnement lancé
channel.getAmount(); // Retourne le montant de l'abonnement.
channel.getCurrency(); // Retourne la devise du pays de l'abonnement.
```




<br/>


### Copyright
```
   Copyright (C) 2021 Chipdeals Inc

```

