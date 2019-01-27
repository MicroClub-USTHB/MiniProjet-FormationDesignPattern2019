package Client;

import Metier.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class Gestion_Table_Verite {
    HashMap<LinkedList<Boolean>,Boolean> table_de_verite = new HashMap<>();
    LinkedList<String> liste_literraux = new LinkedList<>();

    public static void varier(Expression_Logique E, Formule_Atomique V, LinkedList<Formule_Atomique> litteraux,HashMap<LinkedList<Boolean>,Boolean> D){
        V.setValeur(true);
        E.getValeursVerite().add(true);
        if(litteraux.indexOf(V)!=litteraux.size()-1)
        {
            varier(E,litteraux.get(litteraux.indexOf(V)+1),litteraux,D);
        }
        else{
            D.put(E.getValeursVerite(),E.evaluer());
        }
        V.setValeur(false);
        E.getValeursVerite().add(false);
        if(litteraux.indexOf(V)!=litteraux.size()-1)
        {
            varier(E,litteraux.get(litteraux.indexOf(V)+1),litteraux,D);
        }
        else{
            D.put(E.getValeursVerite(),E.evaluer());
        }
    }

    public void Creer_table(Expression_Logique E){
        LinkedList<Formule_Atomique> LF = new LinkedList<>();
        HashSet<Formule_Atomique> HF = new HashSet<>();
        E.getLitteraux(HF);
        LF.addAll(HF);
        for(Formule_Atomique F:LF)
            liste_literraux.add(F.toString());
        System.out.println(liste_literraux);
        HashMap<LinkedList<Boolean>,Boolean> Rez = new HashMap<>();
        varier(E,LF.get(0),LF,Rez);
        table_de_verite = Rez;
    }

    public HashMap<LinkedList<Boolean>, Boolean> getTable_de_verite() {
        return table_de_verite;
    }

    public LinkedList<String> getListe_literraux() {
        return liste_literraux;
    }

    public static void main(String[] st){
        Variable V1 = new Variable("A");
        V1.setValeur(true);
        Variable V2 = new Variable("B");
        V2.setValeur(false);
        Variable V3 = new Variable("C");
        V3.setValeur(true);
        Variable V4 = new Variable("D");
        V4.setValeur(false);
        Terme_NE T1 = new Terme_NE(V1,V2,Connecteurs_Base.AND);
        Terme_NE T2 = new Terme_NE(V3,V4,Connecteurs_Base.OR);
        Terme_NE T3 = new Terme_NE(T1,T2,Connecteurs_Base.OR);
        new Gestion_Table_Verite().Creer_table(T3);
    }

}
