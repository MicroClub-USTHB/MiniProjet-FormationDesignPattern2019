package Metier;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Stack;
import java.util.function.Function;

public abstract class Expression_Logique implements Refutable {

    Function<Stack<Boolean>,Stack<Boolean>> stackHandler ;

    protected boolean negation = false;

    public Function<Stack<Boolean>, Stack<Boolean>> getStackHandler() {
        return stackHandler;
    }

    public abstract void getLitteraux(HashSet<Formule_Atomique> litteraux);

    @Override
    public abstract boolean evaluer();

    public final boolean negationner(){
        negation = !negation;
        return negation;
    }



    public  LinkedList<Boolean> getValeursVerite(){
        HashSet<Formule_Atomique> HF = new HashSet<>();
        getLitteraux(HF);
        LinkedList<Boolean> LB = new LinkedList<>();
        for(Formule_Atomique F:HF){
            LB.add(F.getValeur());
        }
        return LB;
    }

    @Override
    public Expression_Logique getExpression() {
        return this;
    }



}
