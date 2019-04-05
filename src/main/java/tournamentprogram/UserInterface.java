/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tournamentprogram;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Tämä luokka käynnistää käyttöliittymän sekä sovelluslogiikan. Käyttöliittymään
 * käynnistetään kaksi välilehteä: Teams ja Games. Teams-välilehdellä tallennetaan
 * turnauksen joukkueet pelaajinee ja Games-välilhedellä tallennetaan turnauksen ottelut
 * maaleineen.
 * @author pitkapa7
 */
public class UserInterface extends Application {
    
    @Override
    public void start(Stage primaryStage) {
//        btn.setText("Say 'Hello World'");
//        btn.setOnAction(new EventHandler<ActionEvent>() {
//            
//            @Override
//            public void handle(ActionEvent event) {
//                System.out.println("Hello World!");
//            }
//        });
        
        ApplicationLogic logic = new ApplicationLogic();
        TeamsTabUI teamsTabUI = new TeamsTabUI(logic);
        Tab teamsTab = teamsTabUI.createTeamsTab();
        GamesTabUI gamesTabUI= new GamesTabUI(logic);
        ScrollPane gamesTabScrollPane = new ScrollPane();
        Tab gamesTab = gamesTabUI.createGamesTab();
        TabPane root = new TabPane(teamsTab, gamesTab);
        

        Scene scene = new Scene(root, 1000, 800);
        primaryStage.setTitle("Floorball tournament");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    



    
}
