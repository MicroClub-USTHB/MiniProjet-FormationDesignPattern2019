package Metier;

import java.sql.Ref;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Stack;
import java.util.function.Function;

public class Terme_NE extends Expression_Logique implements Formule_NE{
    private boolean negation = false;
    private Refutable membreDroit;
    private Refutable membreGauche;
    private Connecteurs_Base connecteur;

    public Terme_NE(Refutable membreDroit, Refutable membreGauche, Connecteurs_Base connecteur) {
        this.membreDroit = membreDroit;
        this.membreGauche = membreGauche;
        this.connecteur = connecteur;
    }

    public boolean isNegation() {
        return negation;
    }


    public void changer_negation(){
        negation = !negation;
        if(connecteur.compareTo(Connecteurs_Base.OR)==0)
        {
            connecteur = Connecteurs_Base.AND;
            membreDroit.changer_negation();
            membreGauche.changer_negation();
        }
        else
        {if (connecteur == Connecteurs_Base.AND)
            {
                connecteur = Connecteurs_Base.OR;
                membreDroit.changer_negation();
                membreGauche.changer_negation();
            }
            else{
                connecteur = Connecteurs_Base.AND;
                membreDroit.changer_negation();
            }
        }
    }

    public void setNegation(boolean negation) {
        this.negation = negation;
    }

    public void setMembreDroit(Atome_NE membreDroit) {
        this.membreDroit = membreDroit;
    }

    public void setMembreGauche(Atome_NE membreGauche) {
        this.membreGauche = membreGauche;
    }

    public void setConnecteur(Connecteurs_Base connecteur) {
        this.connecteur = connecteur;
    }

    @Override
    public Function<Boolean[], Boolean> getCalculator() {
        return new Function<Boolean[], Boolean>() {
            @Override
            public Boolean apply(Boolean[] booleans) {
                Stack<Boolean> SB = new Stack<>();
                for(Boolean b:booleans){
                    SB.push(b);
                }
                SB = getStackHandler().apply(SB);
                return SB.peek();
            }
        };
    }

    public Refutable getMembreDroit() {
        return membreDroit;
    }

    public Refutable getMembreGauche() {
        return membreGauche;
    }

    public Connecteurs_Base getConnecteur() {
        return connecteur;
    }

    public void enlever_fleche(){
        if(connecteur == Connecteurs_Base.FLECHE){
            connecteur = Connecteurs_Base.OR;
            membreGauche.changer_negation();
        }
        if(!membreGauche.isFeuille())
        {
            Terme_NE TT = (Terme_NE) membreGauche;
            TT.enlever_fleche();
        }
        if(!membreDroit.isFeuille())
        {
            Terme_NE TT = (Terme_NE) membreDroit;
            TT.enlever_fleche();
        }
    }

    @Override
    public void enlever_ou() {
        if(connecteur == Connecteurs_Base.OR)
        {
            negation = !negation;
            connecteur = Connecteurs_Base.AND;
            membreDroit.changer_negation();
            membreGauche.changer_negation();
        }
        if(!membreGauche.isFeuille())
        {
            Terme_NE TT = (Terme_NE) membreGauche;
            TT.enlever_ou();
        }
        if(!membreDroit.isFeuille())
        {
            Terme_NE TT = (Terme_NE) membreDroit;
            TT.enlever_ou();
        }
    }

    public static void main(String[] param){
        Variable V1 = new Variable("A");
        V1.setValeur(true);
        Variable V2 = new Variable("B");
        V2.setValeur(false);
        Variable V3 = new Variable("C");
        V3.setValeur(true);
        Variable V4 = new Variable("D");
        V4.setValeur(false);
        Terme_NE T1 = new Terme_NE(V2,V1,Connecteurs_Base.FLECHE);
        Terme_NE T2 = new Terme_NE(V3,V4,Connecteurs_Base.FLECHE);
        Terme_NE T3 = new Terme_NE(T2,T1,Connecteurs_Base.FLECHE);
        System.out.println(T3);
        LinkedList<Refutable> LR = new LinkedList<>();
        LR.add(V1);
        LR.add(V2);
        LR.add(V3);
        LR.add(V4);
        LR.add(T1);
        LR.add(T2);
        LR.add(T3);
        HashSet<Formule_Atomique> LFA = new HashSet<>();
        T3.getLitteraux(LFA);
        T3.enlever_fleche();
        T3.changer_negation();
        System.out.println(T3);
    }

    @Override
    public boolean evaluer() {
        return connecteur.calculer(membreGauche.evaluer(),membreDroit.evaluer());
    }

    @Override
    public Function<Stack<Boolean>, Stack<Boolean>> getStackHandler() {
        Function<Stack<Boolean>, Stack<Boolean>> F1 = new Function<Stack<Boolean>, Stack<Boolean>>() {
            @Override
            public Stack<Boolean> apply(Stack<Boolean> booleans) {
                return booleans;
            }
        };
        if(!membreDroit.isFeuille() && !membreGauche.isFeuille())
        {
            Formule_NE MG,MD;
            MG = (Formule_NE) membreGauche;
            MD = (Formule_NE) membreDroit;

            final boolean[] rez1 = new boolean[2];
            F1 = MG.getStackHandler().andThen(new Function<Stack<Boolean>, Stack<Boolean>>() {
                @Override
                public Stack<Boolean> apply(Stack<Boolean> booleans) {
                    rez1[0] = booleans.pop();
                    return booleans ;
                }
            }).compose(MD.getStackHandler()).andThen(new Function<Stack<Boolean>, Stack<Boolean>>() {
                @Override
                public Stack<Boolean> apply(Stack<Boolean> booleans) {
                    booleans.push(rez1[0]);
                    return booleans ;
                }
            }).compose(connecteur.getTraitement_recursif()).andThen(new Function<Stack<Boolean>, Stack<Boolean>>() {
                @Override
                public Stack<Boolean> apply(Stack<Boolean> booleans) {
                    return booleans;
                }
            });
        }
        return F1;
    }

    @Override
    public void getLitteraux(HashSet<Formule_Atomique> litteraux) {
       membreGauche.getLitteraux(litteraux);
       membreDroit.getLitteraux(litteraux);
    }

    @Override
    public boolean isFeuille() {
        return false;
    }

    @Override
    public String toString() {
        if(!negation)
            return "(" + membreGauche.toString()+" "+ connecteur.toString() +" "+membreDroit.toString() + ")";
        else
            return "NOT(" +membreGauche.toString()+" "+ connecteur.toString() +" "+membreDroit.toString()+")";
    }

    public boolean calculate(Boolean ... bool){
        return getCalculator().apply(bool);
    }
}
