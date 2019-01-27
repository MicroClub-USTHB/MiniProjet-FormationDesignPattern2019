package Client;

import Metier.*;
import Presentation.FXML.Gestionaire_Interface;

import java.sql.Ref;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;

public class Gestion_Refutation {
    private Expression_Logique expression_logique;
    private Deque<Refutable>  D;
    private StringBuilder deroulement;

    public Gestion_Refutation(Expression_Logique E){
        expression_logique = E;
        D = new ArrayDeque<>();
        D.push(E);
    }

    public boolean verifier(){
        HashMap<String,Boolean> HSB = new HashMap<>();
        deroulement = new StringBuilder();
        Deque<Refutable> deque = new ArrayDeque<>(D);
        return Refutation(deroulement,deque,HSB,"E");
    }

    private boolean Refutation(StringBuilder deroulement,Deque<Refutable> deque,HashMap<String,Boolean> mi,String nom_ensemble){
        HashMap<String,Boolean> mappage_identificateurs = new HashMap<>(mi);
        deroulement = deroulement;
        deroulement.append("-->");
        deroulement.append(nom_ensemble);
        deroulement.append(" = ");
        deroulement.append(deque.toString().replace("[", "{").replace("]", "}"));
        deroulement.append("\n");
        deroulement.toString();
        if(!mi.isEmpty())
        {
            deroulement.append(" sachant : ");
            deroulement.toString();
            for(String s:mi.keySet()){
                deroulement.append(s);
                deroulement.append("=");
                deroulement.append(mi.get(s));
                deroulement.append(" et ");
                deroulement.toString();
            }
            deroulement.append("\n");
            deroulement.toString();
        }
        while (!deque.isEmpty())
        {
            Refutable EL = deque.poll();

            deroulement.append("On prend  : ");
            deroulement.append(EL);
            deroulement.append("\n");
            deroulement.toString();
            if(EL==null)
                break;
            if(EL.isFeuille())
            {
                Atome_NE F = (Formule_Atomique) EL;
                if(mappage_identificateurs.keySet().contains(F.getIdentifiant()) && mappage_identificateurs.get(F.getIdentifiant())!=F.isNegation())
                {

                    deroulement.append(" On doit avoir ").append(F).append("=").append(!F.isNegation()).append(" cependant on sait que ").append(F).append("=").append(!mappage_identificateurs.get(F.getIdentifiant())).append(" donc ").append(nom_ensemble).append(" est insatisfaisable.");
                    deroulement.append("\n");
                    return false;
                }
                else{
                    if(!mappage_identificateurs.keySet().contains(F.getIdentifiant())) {

                        deroulement.append("On fixe  : ").append(F.getIdentifiant()).append("=").append(!F.isNegation());
                        deroulement.append("\n");
                    }
                    mappage_identificateurs.put(F.getIdentifiant(),F.isNegation());
                }
            }
            else{
                Formule_NE T = (Terme_NE) EL;
                if(T.getConnecteur() == Connecteurs_Base.OR)
                    T.enlever_ou();
                if(T.getConnecteur() == Connecteurs_Base.FLECHE)
                    T.enlever_fleche();

                deroulement.append("La formule deviens  " + T);
                deroulement.append("\n");
                if(!T.isNegation())
                {
                    deque.push(T.getMembreDroit());
                    deque.push(T.getMembreGauche());
                    deroulement.append(" ");
                    deroulement.append("on ajoute : {");
                    deroulement.append(T.getMembreGauche());
                    deroulement.append("} et {");
                    deroulement.append(T.getMembreDroit());
                    deroulement.append("}\n");
                    deroulement.append(" l'ensemble ");
                    deroulement.append(nom_ensemble);
                    deroulement.append(" deviens ");
                    deroulement.append(deque);
                    deroulement.append("\n");
                }else
                {
                    deroulement.append(" donc étudie deux ensembles ");
                    deroulement.append(nom_ensemble);
                    deroulement.append("1");
                    deroulement.append(" et ");
                    deroulement.append(nom_ensemble);
                    deroulement.append("2");
                    deroulement.append("\n");
                    Deque<Refutable> D1 = new ArrayDeque<>(deque);
                    Deque<Refutable> D2 = new ArrayDeque<>(deque);
                    T.getMembreDroit().changer_negation();
                    T.getMembreGauche().changer_negation();
                    D1.push(T.getMembreDroit());
                    boolean b = Refutation(deroulement,D1,mappage_identificateurs,nom_ensemble+"1");
                    if(!b)
                    {
                        D2.push(T.getMembreGauche());
                        b = Refutation(deroulement,D2,mappage_identificateurs,nom_ensemble+"2");
                        if(!b)
                            deroulement.append(" Fin de l'étude de ").append(nom_ensemble).append(" qui est insatisfaisable car ").append(nom_ensemble).append("1").append(" et ").append(nom_ensemble).append("2 sont tout deux insatisfaisable ").append("\n");
                        return b;
                    }
                    else
                    {
                        return true;
                    }
                }
            }
        }

        return true;
    }

    public static void main(String[] str){
        Variable V1 = new Variable("A");
        V1.setValeur(true);
        V1.changer_negation();
        Variable V2 = new Variable("B");
        V2.setValeur(false);
        V2.changer_negation();
        Variable V3 = new Variable("B");
        V3.setValeur(true);
        Variable V4 = new Variable("A");
        V4.setValeur(false);
        Terme_NE T1 = new Terme_NE(V2,V1,Connecteurs_Base.FLECHE);
        Terme_NE T2 = new Terme_NE(V3,T1,Connecteurs_Base.AND);
        Terme_NE T3 = new Terme_NE(V4,T2,Connecteurs_Base.AND);
        Variable V5 = new Variable("D");
        V5.setValeur(false);
        Variable V6 = new Variable("B");
        V6.setValeur(false);
        Terme_NE T4 = new Terme_NE(V5,V6,Connecteurs_Base.AND);
        Terme_NE T5 = new Terme_NE(T4,T3,Connecteurs_Base.AND);
        Gestion_Refutation GR = new Gestion_Refutation(T5);
        Deque<Refutable> D = new ArrayDeque<>();
        D.push(T5);
        GR.verifier();
        System.out.println(GR.deroulement);

    }




}
