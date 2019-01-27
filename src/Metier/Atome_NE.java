package Metier;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Stack;
import java.util.function.Function;

public interface Atome_NE extends Refutable{
    boolean evaluer();
    boolean isFeuille();

    boolean isVariable();
    boolean negationner();
    String getIdentifiant();

}
