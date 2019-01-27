package Metier;

import java.util.HashSet;
import java.util.Stack;
import java.util.function.Function;

public interface Formule_NE extends Refutable{
    void enlever_fleche();
    void enlever_ou();
    Connecteur getConnecteur();
    Refutable getMembreDroit();
    Refutable getMembreGauche();
    Function<Stack<Boolean>, Stack<Boolean>> getStackHandler();
}
