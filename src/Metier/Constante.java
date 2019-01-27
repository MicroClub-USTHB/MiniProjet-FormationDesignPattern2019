package Metier;

import java.util.function.Function;

public class Constante extends Formule_Atomique {

    public Constante(String idf, boolean valeur) {
        super(idf);
        this.valeur=valeur;
    }


    @Override
    public boolean evaluer() {
        return false;
    }

    @Override
    public void setValeur(boolean b) {

    }

    @Override
    public boolean isVariable() {
        return false;
    }

    @Override
    public Function<Boolean[], Boolean> getCalculator() {
        return new Function<Boolean[], Boolean>() {
            @Override
            public Boolean apply(Boolean[] booleans) {
                return false;
            }
        };
    }
}
