import lights.*;
import lights.interfaces.*;
public class AuctionTuple {
    public String oggettoAsta;
    public String venditore;
    public Double offertaMinima;

    public AuctionTuple(String oggettoAsta, String venditore, Double offertaMinima) {
        this.oggettoAsta = oggettoAsta;
        this.venditore = venditore;
        this.offertaMinima = offertaMinima;
    }

    public ITuple getTuple() {
        return new Tuple()
            .add(new Field().setValue(this.oggettoAsta))
            .add(new Field().setValue(this.venditore))
            .add(new Field().setValue(this.offertaMinima));
    }

    //template to get all the offers for this auction
    public ITuple getTemplateOffers() {
        return new Tuple()
            .add(new Field().setValue(this.oggettoAsta))
            .add(new Field().setValue(this.venditore))
            .add(new Field().setType(String.class)) //acquirente
            .add(new Field().setType(Double.class)); //offerta
    }

    public static AuctionTuple fromTuple(ITuple tuple) {
        return new AuctionTuple(
            (String)( (Field) tuple.get(0)).getValue(),
            (String) ( (Field) tuple.get(1)).getValue(),
            (Double) ( (Field) tuple.get(2)).getValue()
        );
    }

    public void print() {
        System.out.println("Oggetto in vendita: " + this.oggettoAsta + " venduto da " + this.venditore + " al prezzo minimo di  " + this.offertaMinima);
    }
}