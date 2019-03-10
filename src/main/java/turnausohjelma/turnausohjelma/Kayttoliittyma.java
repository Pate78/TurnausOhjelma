/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package turnausohjelma.turnausohjelma;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author pitkapa7
 */
public class Kayttoliittyma extends Application {
    
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
        Tab teamsTab = createTeamsTab(logic);
        Tab gamesTab = createGamesTab(logic);
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
    
    private Tab createTeamsTab(ApplicationLogic logic){
        Tab teams = createBasicTab("Teams");
        Button addButton = new Button("Add team");
        Button modifyButton = new Button("Modify team");
        Button saveButton = new Button("Save team");
        Button cancelAddTeamButton = new Button("Cancel");

        TextField teamName = new TextField("Team name");
        VBox leftBox = new VBox();
        VBox centerBox = new VBox();
       
        leftBox.getChildren().addAll(addButton, modifyButton);

        BorderPane tabPanel = (BorderPane)teams.getContent();
        
        
        
        /** Action listeners for the Teams tab.*/
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                VBox addBox = new VBox(teamName, saveButton, cancelAddTeamButton);
                tabPanel.setCenter(addBox);
                addButton.setDisable(true);
            }
        });
        
        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                logic.addTeam(teamName.getText());
                centerBox.getChildren().clear();
                ArrayList<Team> savedTeams = logic.getTeams();

                tabPanel.setCenter(teamsInVBox(logic));
                addButton.setDisable(false);
            }
        });
        
        
        cancelAddTeamButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                centerBox.getChildren().clear();
                centerBox.getChildren().add(teamsInVBox(logic));
            }
        });
        
        tabPanel.setLeft(leftBox);
        tabPanel.setCenter(centerBox);
        return teams;
    }
    
    private Tab createGamesTab(ApplicationLogic logic) {
        Tab games = createBasicTab("Games");
        VBox leftBox = new VBox();
        Button addButton = new Button("Add game");
        Button modifyButton = new Button("Modify game");
        leftBox.getChildren().addAll(addButton, modifyButton);
        BorderPane tabPanel = (BorderPane)games.getContent();
        tabPanel.setLeft(leftBox);
        
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                VBox box = new VBox();
                tabPanel.setCenter(box);
                TextField teamName = new TextField("Game");
                Button save = new Button("Save game");
                box.getChildren().addAll(teamName, save);
            }
        });
        
        return games;
    }
    
    /** This will create basic tab layout which is used in this program */
    private Tab createBasicTab(String header) {
        Tab tab = new Tab(header);
        tab.setClosable(false);
        BorderPane paneeli = new BorderPane();
        tab.setContent(paneeli);
        return tab;
    }
    
    private VBox teamsInVBox(ApplicationLogic logic) {
        VBox teamBox = new VBox();
        HBox teamName = new HBox();
        HBox players = new HBox();
        for (Team t: logic.getTeams()) {
            teamName.getChildren().add(new Label(t.getName()));
            for (Player op : t.getOffencePlayers()) {
                players.getChildren().add(new Label(op.getForname() + " " + op.getLastname()));
            }
            for (Player dp : t.getDefencePlayers()) {
                players.getChildren().add(new Label(dp.getForname() + " " + dp.getLastname()));
            }
            for (Player goalkeeper : t.getGoalkeepers()) {
                players.getChildren().add(new Label(goalkeeper.getForname() + " " + goalkeeper.getLastname()));
            }
            
        }
        teamBox.getChildren().addAll(teamName, players);
        return teamBox;
    }
    
}
