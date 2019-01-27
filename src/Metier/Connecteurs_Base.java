package Metier;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Stack;
import java.util.function.Function;

public enum Connecteurs_Base implements Connecteur{
    OR,
    AND,
    FLECHE;

    private Function<Stack<Boolean>,Boolean> evaluateur;
    private Function<Stack<Boolean>,Stack<Boolean>> traitement_recursif;


    public Function<Stack<Boolean>, Stack<Boolean>> getTraitement_recursif() {
        if(traitement_recursif!=null)
            return traitement_recursif;
        setEvaluateur();
        this.traitement_recursif = new Function<Stack<Boolean>, Stack<Boolean>>() {
            @Override
            public Stack<Boolean> apply(Stack<Boolean> booleans) {
                evaluateur.apply(booleans);
                return booleans;
            }
        };
        return traitement_recursif;
    }

    public Function<Stack<Boolean>,Boolean>  setEvaluateur() {
        if(evaluateur!=null)
            return evaluateur;
        if(this.compareTo(Connecteurs_Base.AND)==0)
        {
             evaluateur = new Function<Stack<Boolean>, Boolean>() {
                @Override
                public Boolean apply(Stack<Boolean> booleans) {
                    boolean b = booleans.pop();
                    boolean b1 = booleans.pop();
                    booleans.push(b&&b1);
                    return b&&b1;
                }
            };
        }else {
            if(this.compareTo(Connecteurs_Base.OR)==0){
                 evaluateur = new Function<Stack<Boolean>, Boolean>() {
                    @Override
                    public Boolean apply(Stack<Boolean> booleans) {
                        boolean b = booleans.pop();
                        boolean b1 = booleans.pop();
                        booleans.push(b||b1);
                        return b||b1;
                    }
                };
            }else
            {
                 evaluateur = (new Function<Stack<Boolean>, Boolean>() {
                    @Override
                    public Boolean apply(Stack<Boolean> booleans) {
                        boolean b = booleans.pop();
                        boolean b1 = booleans.pop();
                        booleans.push(!b1||b);
                        return !b1||b;
                    }
                });
            }
        }
        return evaluateur;
    }

    public Function<Stack<Boolean>,Boolean>  getEvaluateur() {
        setEvaluateur();
        return evaluateur;
    }

    @Override
    public boolean calculer(Stack<Boolean> S) {
        getEvaluateur();
        return evaluateur.apply(S);
    }

    @Override
    public boolean calculer(Boolean... booleans) {
        Stack<Boolean> SB = new Stack<>();
        for(int i=0;i<booleans.length;i++)
            SB.push(booleans[i]);
        return calculer(SB);
    }


    @Override
    public boolean evaluer() {
        return false;
    }

    @Override
    public Function<Stack<Boolean>, Stack<Boolean>> getStackHandler() {
        getTraitement_recursif();
        return traitement_recursif;
    }

}
