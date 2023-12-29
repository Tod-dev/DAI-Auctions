import java.rmi.Naming;
import lights.*;
import lights.interfaces.*;
public class AuctionParticipant {
  private static IRemoteTupleSpace getSpace(String[] args) throws Exception {
    String name;
    if (args.length >= 1)
      name = args[0];
    else
      name = "TupleSpace";
    return (IRemoteTupleSpace) Naming.lookup("//localhost/" + name);
  }
  public static void main(String[] args) throws Exception {
    IRemoteTupleSpace space = getSpace(args);
    System.out.println("space -> "+ space.getName());
    String name = (args.length >= 2) ? args[1] :  "Participant"+Math.random();
    System.out.println("AuctionParticipant STARTED: " + name);
    if (args.length >= 3 && args[2].equals("DutchAuction")){
      System.out.println("DutchAuction");
      dutchAuction(space, name);
    }else{
      System.out.println("EnglishAuction");
      englishAuction(space, name);
    }
  }
  public static void dutchAuction(IRemoteTupleSpace space, String name) throws Exception {
    //read an DutchAuctionTuple from the tuple space
    ITuple MyTemplateDutch = DutchAuctionTuple.getTemplateTypes();

    while(true){
      ITuple auction = space.rd(MyTemplateDutch); // blocking
      DutchAuctionTuple asta = DutchAuctionTuple.fromTuple(auction);
      System.out.println("Auction Found: " + auction);
      //Create an OffertTuple with offert = offertaAttuale and put it in the tuple space with certain probability
      //hp probability = 0.2 of making an offert
      Double prob = Math.random();
      if (prob < 0.1){
        Double offert = asta.offertaAttuale;
        OffertTuple offerta = new OffertTuple(asta.oggettoAsta, asta.venditore, name, offert);
        ITuple offerttuple = offerta.getTuple();
        // System.out.println("offerttuple: " + offerttuple);
        space.out(offerttuple);
        System.out.println("new offert created: " +offerttuple);
      }else{
        System.out.println("no offert created");
      }
      Thread.sleep(2000);
    }
  }
  public static void englishAuction(IRemoteTupleSpace space, String name)throws Exception {

    //read an AuctionTuple from the tuple space
    ITuple MyTemplateEnglish = AuctionTuple.getTemplateTypes();
    
    while(true){
      ITuple auction = space.rd(MyTemplateEnglish); // blocking
      AuctionTuple asta = AuctionTuple.fromTuple(auction);
      System.out.print("Auction Found: ");
      asta.print();
      //Create an OffertTuple and put it in the tuple space (hp: offerta è +10% -80% offerta minima)
      Double offert = asta.offertaMinima + Math.random()*( asta.offertaMinima*0.1) - Math.random()*(asta.offertaMinima*0.8) ; 
      offert = Math.round(offert*100.0)/100.0; // arrotondo a 2 cifre decimali
      OffertTuple offerta = new OffertTuple(asta.oggettoAsta, asta.venditore, name, offert);
      ITuple offerttuple = offerta.getTuple();
      // System.out.println("offerttuple: " + offerttuple);
      //make the offert only wih certain probability
      Double prob = Math.random();
      if (prob < 0.8){
        space.out(offerttuple);
        System.out.println("new offert created: " +offerttuple);
      }
      Thread.sleep(600);
    }
  }
}
