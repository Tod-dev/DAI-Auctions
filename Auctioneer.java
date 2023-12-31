//BANDITORE 
import java.rmi.Naming;
import lights.*;
import lights.interfaces.*;
import java.lang.reflect.Method;
public class Auctioneer {
    private static int SECONDS_TO_WAIT_ENGLISH = 5; 
    private static IRemoteTupleSpace getSpace(String[] args) throws Exception {
      String name;
      if (args.length >= 1)
        name = args[0];
      else
        name = "TupleSpace";
      return (IRemoteTupleSpace) Naming.lookup("//localhost/" + name);
    }
    public static AuctionTuple getRandomAuctionTuple(String type){
      //new AuctionTuple("LAVATRICE", "SELLER01", 100.00);
      String[] oggetti = {"LAVATRICE", "FRIGORIFERO", "LAVASTOVIGLIE", "TELEVISORE", "LAMPADA", "TAVOLO", "SEDIA", "DIVANO", "LETTO", "COMODINO", "ARMADIO", "LIBRERIA", "SCRIVANIA", "COMPUTER", "SMARTPHONE", "TABLET", "LAVATRICE", "FRIGORIFERO", "LAVASTOVIGLIE", "TELEVISORE", "LAMPADA", "TAVOLO", "SEDIA", "DIVANO", "LETTO", "COMODINO", "ARMADIO", "LIBRERIA", "SCRIVANIA", "COMPUTER", "SMARTPHONE", "TABLET"};
      String[] venditori = {"SELLER01", "SELLER02", "SELLER03", "SELLER04", "SELLER05", "SELLER06", "SELLER07", "SELLER08", "SELLER09", "SELLER10", "SELLER11", "SELLER12", "SELLER13", "SELLER14", "SELLER15", "SELLER16", "SELLER17", "SELLER18", "SELLER19", "SELLER20", "SELLER21", "SELLER22", "SELLER23", "SELLER24", "SELLER25", "SELLER26", "SELLER27", "SELLER28", "SELLER29", "SELLER30", "SELLER31", "SELLER32"};
      Double[] prezzi = {100.00, 200.00, 300.00, 400.00, 500.00, 600.00, 700.00, 800.00, 900.00, 1000.00, 1100.00, 1200.00, 1300.00, 1400.00, 1500.00, 1600.00, 1700.00, 1800.00, 1900.00, 2000.00, 2100.00, 2200.00, 2300.00, 2400.00, 2500.00, 2600.00, 2700.00, 2800.00, 2900.00, 3000.00, 3100.00, 3200.00};
      int random = (int) (Math.random() * oggetti.length);
      if (type.equals("Dutch")){
        Double offertaMinima = prezzi[random];
        Double offertaIniziale = offertaMinima*2;
        Double decrementoPerc = 0.2;
        int secondiDecremento = 4;
        return new DutchAuctionTuple(oggetti[random], venditori[random], offertaMinima, offertaIniziale, decrementoPerc, secondiDecremento,offertaIniziale);
      }
      //type="English"
      return new AuctionTuple(oggetti[random], venditori[random], prezzi[random]);
    }
    public static void main(String[] args) throws Exception {
      System.out.println("Auctioneer STARTED");
      IRemoteTupleSpace space = getSpace(args);
      System.out.println("space -> "+ space.getName());
      String acutionType = "EnglishAuction";
      if (args.length == 2 && args[1].equals("DutchAuction")){
        System.out.println("DutchAuction");
        dutchAuction(space);
      }else{
        System.out.println("EnglishAuction");
        englishAuction(space);
      }
    }
    public static void dutchAuction(IRemoteTupleSpace space) throws Exception {
      while (true){
        System.out.println("Dutch Auction STARTED");
        DutchAuctionTuple asta = (DutchAuctionTuple) getRandomAuctionTuple("Dutch");
        asta.print();
        ITuple auctiontuple = asta.getTuple();
        System.out.println(auctiontuple);
        space.out(auctiontuple);
        ITuple MyTemplate = asta.getTemplateOffers();
        Boolean sold =false;
        OffertTuple ot = null;
        while(asta.offertaAttuale >= asta.offertaMinima){
          //wait for one offert
          System.out.println("waiting "+asta.secondiDecremento+"s for offers... ");
          int seconds = asta.secondiDecremento*1000;
          Thread.sleep(seconds);
          ITuple offert = space.inp(MyTemplate);
          if (offert != null){
            //end auction
            System.out.println("Dutch Auction offert found: " + offert);
            ot = OffertTuple.fromTuple(offert);
            sold = true;
            break;
          }
          space.ing(DutchAuctionTuple.getTemplateTypes());
          //decrement
          asta.decrementa();
          //update tuple
          space.out(asta.getTuple());
          System.out.println("Decrement!!! -> new price: " + asta.offertaAttuale);
        }
        if(!sold){
          System.out.println("Dutch Auction END with no success! no one has made an offert!");
        }else{
          System.out.println("Dutch Auction terminated WITH SUCCESS!\n" + ot.acquirente + " has won the auction with an offert of " + ot.offerta);
        }
        //remove auction tuple from tuple space of type dutch
        space.ing(DutchAuctionTuple.getTemplateTypes());
        //remove all the other possible offerts
        space.ing(MyTemplate);
        System.out.println("waiting for next auction...");
        Thread.sleep(10000);
      }
    }

    public static void englishAuction(IRemoteTupleSpace space) throws Exception {
      while(true){
        System.out.println("English Auction STARTED");
        AuctionTuple asta = getRandomAuctionTuple("English");
        asta.print();
        ITuple auctiontuple = asta.getTuple();
        System.out.println(auctiontuple);
        space.out(auctiontuple);
        ITuple MyTemplate = asta.getTemplateOffers();
        // System.out.println("offerts template: " + MyTemplate);
        // System.out.println("waiting for offers...");
        for (int i = 0; i < SECONDS_TO_WAIT_ENGLISH; i++) {
          System.out.println("waiting "+(SECONDS_TO_WAIT_ENGLISH-i)+"s for offers...");
          Thread.sleep(1000);
          System.out.println("offers found until now: " + space.count(MyTemplate));
        }
        space.in(auctiontuple); //fine asta -> rimuovo la tupla AuctionTuple dal tuple space
        ITuple[] offerts = space.ing(MyTemplate);
        Double max = -1.0;
        String vincitore = null;
        System.out.println("Analizing the offerts..." );
        if (offerts != null){
          for (ITuple tuple : offerts) {
            OffertTuple ot = OffertTuple.fromTuple(tuple);
            if(ot.offerta > max){
              System.out.println("Maximum offert until now: " + ot.offerta);
              max = ot.offerta;
              vincitore = ot.acquirente;
            }
          }
        }
        Double offertaMinima = (Double) ((Field) auctiontuple.get(2)).getValue();
        System.out.println("offerta minima: " + offertaMinima);
        String msg = asta.oggettoAsta +" (sold by "+asta.venditore+ ") -> Auction terminated ";
        if(max > offertaMinima){
          System.out.println(msg +"WITH SUCCESS!\n" + vincitore + " has won the auction with an offert of " + max);
        }else if (max > 0){
          System.out.println(msg+ "WITHOUT ANY SUCCESSFUL BIDDER.\n" + vincitore + " has made the maximum offert ("+max+" ), but still under the minimum offer( " + offertaMinima+" )");
        } else {
          System.out.println(msg +"WITHOUR ANY OFFERT!");
        }
        System.out.println("Auction END!");
        System.out.println("waiting for next auction...");
        Thread.sleep(10000);
      }
    }
}