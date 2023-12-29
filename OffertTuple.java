import lights.*;
import lights.interfaces.*;
public class OffertTuple {
    public String oggettoAsta;
    public String venditore;
    public String acquirente;
    public Double offerta;

    public OffertTuple(String oggettoAsta, String venditore, String acquirente, Double offerta) {
        this.oggettoAsta = oggettoAsta;
        this.venditore = venditore;
        this.acquirente = acquirente;
        this.offerta = offerta;
    }

    public ITuple getTuple() {
        return new Tuple()
            .add(new Field().setValue(oggettoAsta))
            .add(new Field().setValue(venditore))
            .add(new Field().setValue(acquirente))
            .add(new Field().setValue(offerta));
    }

    public static OffertTuple fromTuple(ITuple tuple) {
        return new OffertTuple(
            (String)( (Field) tuple.get(0)).getValue(),
            (String) ( (Field) tuple.get(1)).getValue(),
            (String) ( (Field) tuple.get(2)).getValue(),
            (Double) ( (Field) tuple.get(3)).getValue()
        );
    }

    public void print() {
        System.out.println("New offert for the item on sale: " + oggettoAsta + " sold by" + venditore + " to the buyer " + acquirente + " at the price of " + offerta);
    }
}