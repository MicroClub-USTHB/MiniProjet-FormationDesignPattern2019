package Metier;

import java.util.HashMap;
import java.util.Stack;
import java.util.function.Function;

public interface Connecteur {
    boolean evaluer();
    Function<Stack<Boolean>, Stack<Boolean>> getStackHandler();
    boolean calculer(Stack<Boolean> S);
    boolean calculer(Boolean ... booleans);
}
