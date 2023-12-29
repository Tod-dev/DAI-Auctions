import lights.*;
import lights.interfaces.*;
public class DutchAuctionTuple extends AuctionTuple {
    // private String oggettoAsta;
    // private String venditore;
    // private Double offertaMinima;
    public static final int SECONDS_TO_WAIT_DUTCH = 10; // secondi da aspettare fino ad abbassare il prezzo
    public Double decrementoPerc;
    public Double offertaIniziale;
    public int secondiDecremento;
    public Double offertaAttuale;

    public DutchAuctionTuple(String oggettoAsta, String venditore, Double offertaMinima, Double offertaIniziale,Double decrementoPerc,  int secondiDecremento, Double offertaAttuale) {
      super(oggettoAsta, venditore, offertaMinima);
      this.decrementoPerc = decrementoPerc;
      this.offertaIniziale = offertaIniziale;
      this.secondiDecremento = secondiDecremento;
      this.offertaAttuale = offertaAttuale;
      if (offertaAttuale == null){
        this.offertaAttuale = offertaIniziale;
      }
    }

    public void decrementa(){
      this.offertaAttuale = Math.round((this.offertaAttuale - this.offertaAttuale*this.decrementoPerc)*100.0)/100.0;
    }

    public static ITuple getTemplateTypes() {
        return new Tuple()
            .add(new Field().setType(String.class))
            .add(new Field().setType(String.class))
            .add(new Field().setType(Double.class))
            .add(new Field().setType(Double.class))
            .add(new Field().setType(Double.class))
            .add(new Field().setType(Integer.class))
            .add(new Field().setType(Double.class));
    }
 

    public ITuple getTuple() {
        return new Tuple()
            .add(new Field().setValue(this.oggettoAsta))
            .add(new Field().setValue(this.venditore))
            .add(new Field().setValue(this.offertaMinima))
            .add(new Field().setValue(this.offertaIniziale))
            .add(new Field().setValue(this.decrementoPerc))
            .add(new Field().setValue(this.secondiDecremento))
            .add(new Field().setValue(this.offertaAttuale));
    }

    //SAME AS AUCTIONTUPLE
    // //template to get all the offers for this auction
    // public ITuple getTemplateOffers() {
    //     return new Tuple()
    //         .add(new Field().setValue(this.oggettoAsta))
    //         .add(new Field().setValue(this.venditore))
    //         .add(new Field().setType(String.class)) //acquirente
    //         .add(new Field().setType(Double.class)); //offerta
    // }

    public static DutchAuctionTuple fromTuple(ITuple tuple) {
        return new DutchAuctionTuple(
            (String)( (Field) tuple.get(0)).getValue(),
            (String) ( (Field) tuple.get(1)).getValue(),
            (Double) ( (Field) tuple.get(2)).getValue(),
            (Double) ( (Field) tuple.get(3)).getValue(),
            (Double) ( (Field) tuple.get(4)).getValue(),
            (int) ( (Field) tuple.get(5)).getValue(),
            (Double) ( (Field) tuple.get(6)).getValue()
        );
    }

    public void print() {
        System.out.println("Item for sale : " + this.oggettoAsta + " sold by " + this.venditore + " at the minimum price of  " + this.offertaMinima + " with a decrement of " + this.decrementoPerc + "%  every " + this.secondiDecremento + " seconds" + " and an initial price of " + this.offertaIniziale + " and an actual price of " + this.offertaAttuale);
    }
}