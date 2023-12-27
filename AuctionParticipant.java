import java.rmi.Naming;
import lights.*;
import lights.interfaces.*;
public class AuctionParticipant {
  private static IRemoteTupleSpace getSpace(String[] args) throws Exception {
    String name;
    if (args.length == 2)
      name = args[1];
    else
      name = "TupleSpace";
    return (IRemoteTupleSpace) Naming.lookup("//localhost/" + name);
  }
  public static void main(String[] args) throws Exception {
    IRemoteTupleSpace space = getSpace(args);
    System.out.println(space.getName());

    String name = (args.length == 2) ? args[0] :  "Participant"+Math.random();
    System.out.println("AuctionParticipant STARTED: " + name);

    //read an AuctionTuple from the tuple space
    ITuple MyTemplate = new Tuple()
      .add(new Field().setType(String.class)) //oggettoAsta
      .add(new Field().setType(String.class)) //venditore
      .add(new Field().setType(Double.class)); //offertaMinima

    while(true){
      ITuple auction = space.rd(MyTemplate); // blocking
      AuctionTuple asta = AuctionTuple.fromTuple(auction);
      System.out.println("Asta trovata: ");
      asta.print();

      //Create an OffertTuple and put it in the tuple space (hp: offerta è +- 20% offerta minima)
      Double offertamin20perc = asta.offertaMinima*0.2;
      Double offert = asta.offertaMinima + Math.random()*offertamin20perc - Math.random()*offertamin20perc ; 
      offert = Math.round(offert*100.0)/100.0; // arrotondo a 2 cifre decimali
      OffertTuple offerta = new OffertTuple(asta.oggettoAsta, asta.venditore, name, offert);
      ITuple offerttuple = offerta.getTuple();
      System.out.println("offerttuple: " + offerttuple);
      space.out(offerttuple);
      System.out.println("offerttuple inserita nel tuple space");
      Thread.sleep(400);
    }
  }
}
