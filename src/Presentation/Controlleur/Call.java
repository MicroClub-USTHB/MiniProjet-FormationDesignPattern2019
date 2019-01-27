package Presentation.Controlleur;

import Client.Gestion_Table_Verite;
import Metier.Connecteurs_Base;
import Metier.Formule_Atomique;
import Metier.Terme_NE;
import Metier.Variable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public abstract class Call {

    protected HashMap<LinkedList<Boolean>,Boolean> dictionnaire = new HashMap<>();
    protected LinkedList<String> listNomColonnes = new LinkedList<>();
    protected ObservableList<LinkedList<Boolean>> o = FXCollections.observableArrayList();

    protected boolean verifSyntaxique(String expression){
        return true;
    }

    protected boolean verifSatisf(){
        return true;
    }

    protected void compiler(String expression){

    }

    protected void remplirTable(TableView<LinkedList<Boolean>> Table){
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
        Gestion_Table_Verite GTV = new Gestion_Table_Verite();
        GTV.Creer_table(T3);
        dictionnaire = GTV.getTable_de_verite();
        System.out.println(dictionnaire);
        listNomColonnes = GTV.getListe_literraux();
        System.out.println(listNomColonnes);
        o.addAll(dictionnaire.keySet());
        Table.setItems(o);
        for (int i = 0; i <listNomColonnes.size(); i++) {
            String str = listNomColonnes.get(i);
            TableColumn<LinkedList<Boolean>,String> T = new TableColumn<>(str);
            int finalI = i;
            T.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().get(finalI)+""));
            Table.getColumns().add(T);
        }
        TableColumn<LinkedList<Boolean>,String> valeur = new TableColumn<>("Valeur");
        valeur.setCellValueFactory(cell -> (new SimpleStringProperty(String.valueOf(dictionnaire.get(cell.getValue())))));
        Table.getColumns().add(valeur);
    }

}
