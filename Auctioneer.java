//BANDITORE 
import java.rmi.Naming;
import lights.*;
import lights.interfaces.*;
import java.lang.reflect.Method;
public class Auctioneer {
    private static IRemoteTupleSpace getSpace(String[] args) throws Exception {
      String name;
      if (args.length == 1)
        name = args[0];
      else
        name = "TupleSpace";
      return (IRemoteTupleSpace) Naming.lookup("//localhost/" + name);
    }
    public static AuctionTuple getRandomAuctionTuple(){
      //new AuctionTuple("LAVATRICE", "SELLER01", 100.00);
      String[] oggetti = {"LAVATRICE", "FRIGORIFERO", "LAVASTOVIGLIE", "TELEVISORE", "LAMPADA", "TAVOLO", "SEDIA", "DIVANO", "LETTO", "COMODINO", "ARMADIO", "LIBRERIA", "SCRIVANIA", "COMPUTER", "SMARTPHONE", "TABLET", "LAVATRICE", "FRIGORIFERO", "LAVASTOVIGLIE", "TELEVISORE", "LAMPADA", "TAVOLO", "SEDIA", "DIVANO", "LETTO", "COMODINO", "ARMADIO", "LIBRERIA", "SCRIVANIA", "COMPUTER", "SMARTPHONE", "TABLET"};
      String[] venditori = {"SELLER01", "SELLER02", "SELLER03", "SELLER04", "SELLER05", "SELLER06", "SELLER07", "SELLER08", "SELLER09", "SELLER10", "SELLER11", "SELLER12", "SELLER13", "SELLER14", "SELLER15", "SELLER16", "SELLER17", "SELLER18", "SELLER19", "SELLER20", "SELLER21", "SELLER22", "SELLER23", "SELLER24", "SELLER25", "SELLER26", "SELLER27", "SELLER28", "SELLER29", "SELLER30", "SELLER31", "SELLER32"};
      Double[] prezzi = {100.00, 200.00, 300.00, 400.00, 500.00, 600.00, 700.00, 800.00, 900.00, 1000.00, 1100.00, 1200.00, 1300.00, 1400.00, 1500.00, 1600.00, 1700.00, 1800.00, 1900.00, 2000.00, 2100.00, 2200.00, 2300.00, 2400.00, 2500.00, 2600.00, 2700.00, 2800.00, 2900.00, 3000.00, 3100.00, 3200.00};
      int random = (int) (Math.random() * oggetti.length);
      return new AuctionTuple(oggetti[random], venditori[random], prezzi[random]);
    }
    public static void main(String[] args) throws Exception {
      System.out.println("Auctioneer STARTED");
      IRemoteTupleSpace space = getSpace(args);
      System.out.println(space.getName());
      while(true){
        AuctionTuple asta = getRandomAuctionTuple();
        asta.print();
        ITuple auctiontuple = asta.getTuple();
        System.out.println(auctiontuple);
        space.out(auctiontuple);
        ITuple MyTemplate = asta.getTemplateOffers();
        System.out.println("offerts template: " + MyTemplate);
        System.out.println("waiting for offers...");
        for (int i = 0; i < 5; i++) {
          System.out.println("waiting for offers...");
          Thread.sleep(1000);
          System.out.println("offers found until now: " + space.count(MyTemplate));
        }
        space.in(auctiontuple); //fine asta -> rimuovo la tupla AuctionTuple dal tuple space
        ITuple[] offerts = space.ing(MyTemplate);
        Double max = -1.0;
        String vincitore = null;
        if (offerts != null){
          for (ITuple tuple : offerts) {
            OffertTuple ot = OffertTuple.fromTuple(tuple);
            System.out.println("offerta: " + ot.offerta);
            if(ot.offerta > max){
              max = ot.offerta;
              vincitore = ot.acquirente;
            }
          }
        }
        Double offertaMinima = (Double) ((Field) auctiontuple.get(2)).getValue();
        System.out.println("offerta minima: " + offertaMinima);
        String msg = asta.oggettoAsta +" (venduto da "+asta.venditore+ ") -> Asta conclusa ";
        if(max > offertaMinima){
          System.out.println(msg +"CON SUCCESSO!\n" + vincitore + " ha vinto l'asta con un'offerta di " + max);
        }else if (max > 0){
          System.out.println(msg+ "SENZA AGGIUDICATARIO.\n" + vincitore + " ha fatto l'offerta più alta( "+max+" ), ma comunque sotto l'offerta minima( " + offertaMinima+" )");
        } else {
          System.out.println(msg +"senza nessuna offerta!");
        }
        System.out.println("Auction terminata!");
        System.out.println("waiting for next auction...");
        Thread.sleep(10000);
      }
    }
}