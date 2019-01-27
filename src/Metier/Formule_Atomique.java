package Metier;

import java.util.HashSet;
import java.util.LinkedList;

public abstract class Formule_Atomique extends Expression_Logique implements Atome_NE  {

    protected boolean valeur;

    private String identifiant;


    public Formule_Atomique(String idf){
        identifiant = idf;
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public boolean isNegation() {
        return negation;
    }



    @Override
    public void changer_negation() {
        negationner();
    }


    @Override
    public boolean evaluer() {
        if(negation)
            return !valeur;
        return valeur ;
    }

    public boolean getValeur(){
        return valeur;
    }

    public abstract void setValeur(boolean b);

    @Override
    public boolean isFeuille() {
        return true;
    }

    @Override
    public void getLitteraux(HashSet<Formule_Atomique> litteraux) {
        litteraux.add(this);
    }

    @Override
    public String toString() {
        if(!isNegation())
            return getIdentifiant();
        else
            return "NOT("+getIdentifiant()+")";
    }
}

