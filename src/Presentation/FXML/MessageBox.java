package Presentation.FXML;

import Presentation.Controlleur.Controller;
import Presentation.FXML.Gestionaire_Interface;
import com.jfoenix.controls.JFXButton;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MessageBox {

    Stage window =new Stage();
    public static boolean genererT=false;

    public void display(String title,String message,int option){

        window.initStyle(StageStyle.TRANSPARENT);

        VBox conteneur=new VBox();
        Pane p1=new Pane();
        HBox p2=new HBox();
        HBox p3=new HBox();
        p3.setAlignment(Pos.CENTER);

        p2.setSpacing(10);

        conteneur.getStylesheets().add("Presentation/CSS/Style.css");
        p1.setStyle("-fx-background-color:  #b0bbbf");
        p1.setPrefHeight(40);
        ImageView img1=new ImageView(new Image("Presentation/Assets/close'.png"));
        img1.setFitHeight(22);
        img1.setFitWidth(25);

        JFXButton b=new JFXButton("",img1);
        b.setContentDisplay(ContentDisplay.CENTER);
        b.setPrefSize(44,36);
        b.setFocusTraversable(false);
        b.setCursor(Cursor.HAND);
        b.setRipplerFill(Paint.valueOf("000000"));
        b.getStyleClass().add("closeButtons");
        b.setOnAction(e ->{
            Stage stage = (Stage) b.getScene().getWindow();
            stage.close();
        });

        Label l=new Label(title);
        l.setFont(Font.font("Impact",21));
        l.setTextFill(Paint.valueOf("ffffff"));

        ImageView img2=new ImageView(new Image("Presentation/Assets/info.png"));
        img2.setFitHeight(25);
        img2.setFitWidth(25);

        JFXButton b1=new JFXButton("OK !");
        b1.getStyleClass().add("buttons1");

        p3.getChildren().add(b1);
        b1.setOnAction(e ->{
            fermerFenetre();
        });
        if(option==2){
            JFXButton b2=new JFXButton("Générer La Table De Vérité");
            b2.getStyleClass().add("buttons1");
            p3.getChildren().add(b2);
            p3.setSpacing(10);
            b2.setOnAction(e -> {
                genererT=true;
                fermerFenetre();
            });
        }

        Label l1=new Label(message);
        l1.setFont(Font.font("Impact",21));
        l1.setTextFill(Paint.valueOf("81898b"));
        p2.setAlignment(Pos.CENTER);
        conteneur.setStyle("-fx-background-color: white; -fx-border-color: #919a9d; -fx-border-width: 3px 3px 3px 3px");

        b.setLayoutX(450);
        b.setLayoutY(0);
        l.setLayoutX(40);
        l.setLayoutY(5);
        img2.setLayoutX(5);
        img2.setLayoutY(6);

        p1.getChildren().addAll(img2,l,b);
        p2.getChildren().addAll(l1);
        VBox v=new VBox(p2,p3);
        v.setSpacing(10);
        v.setMinHeight(100);
        v.setAlignment(Pos.CENTER);
        conteneur.getChildren().addAll(p1,v);

        Scene scene=new Scene(conteneur);
        window.setScene(scene);
        Gestionaire_Interface.moveStage((Stage) window.getScene().getWindow(),window.getScene().getRoot());
        window.showAndWait();
    }

    void fermerFenetre(){
        Stage stage = (Stage) window.getScene().getWindow();
        stage.close();
    }


}
