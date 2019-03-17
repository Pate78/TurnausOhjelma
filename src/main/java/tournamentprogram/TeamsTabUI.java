/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tournamentprogram;

import java.util.ArrayList;
import java.util.Observable;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author pitkapa7
 */
public class TeamsTabUI {
    private ApplicationLogic logic;

    public TeamsTabUI(ApplicationLogic logic) {
        this.logic = logic;
    }
    
    
    public Tab createTeamsTab(){

        Tab teams = createBasicTab("Teams");
        Button addButton = new Button("Add team");
//        Button modifyButton = new Button("Modify team");
        Button saveTeamButton = new Button("Save team");
        Button cancelAddTeamButton = new Button("Cancel");

        TextField teamName = new TextField("Team name");
        VBox leftBox = new VBox();
//        VBox centerBox = new VBox();
       
        leftBox.getChildren().addAll(addButton);

        BorderPane tabPanel = (BorderPane)teams.getContent();
        
        
        
        /** Action listeners for the Teams tab.*/
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                VBox addBox = new VBox(teamName, saveTeamButton, cancelAddTeamButton);
                tabPanel.setCenter(addBox);
                addButton.setDisable(true);
            }
        });
        
        saveTeamButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                logic.addTeam(teamName.getText());
//                centerBox.getChildren().clear();
                ArrayList<Team> savedTeams = logic.getTeams();

                tabPanel.setCenter(teamsInAccordion());
                addButton.setDisable(false);
            }
        });
        
        cancelAddTeamButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
//                centerBox.getChildren().clear();
//                System.out.println("cancelbutton clicked");
                tabPanel.setCenter(teamsInAccordion());
                addButton.setDisable(false);
            }
        });
        
        tabPanel.setLeft(leftBox);
        
        tabPanel.setCenter(teamsInAccordion());
        return teams;
    }

    
    /** This will create accordion where all teams and their players are listed */
    private Accordion teamsInAccordion() {
        Accordion acc = new Accordion();

        for (Team t: logic.getTeams()) {
            TitledPane titledPane = new TitledPane();
            titledPane.setText(t.getName());
            GridPane grid = new GridPane();
            
            grid.add(new Label("Offence"), 0, 1);
            VBox offPlayers = new VBox();
            for (Player op : t.getOffencePlayers()) {
                offPlayers.getChildren().add(new Label(op.getFirstName() + " " + op.getLastName() + " " + op.getNumber()));
            }
            grid.add(offPlayers, 1, 1);
            
            grid.add(new Label("Defence"), 0, 2);
            VBox defPlayers = new VBox();
            for (Player dp : t.getDefencePlayers()) {
                defPlayers.getChildren().add(new Label(dp.getFirstName() + " " + dp.getLastName() + " " + dp.getNumber()));
            }
            grid.add(defPlayers, 1, 2);
            
            grid.add(new Label("Goalkeepers"), 0, 3);
            VBox goalkeepers = new VBox();
            for (Player goalkeeper : t.getGoalkeepers()) {
                goalkeepers.getChildren().add(new Label(goalkeeper.getFirstName() + " " + goalkeeper.getLastName() + " " + goalkeeper.getNumber()));
            }
            grid.add(goalkeepers, 1, 3);
            
            Button addPlayerButton = new Button("Add Player");
            grid.add(addPlayerButton, 0, 0);
            Team testTeam = new Team("");
            try {
                TitledPane expandedPane = acc.getExpandedPane();
                System.out.println("expandedPane.getText(): " +expandedPane.getText());
                testTeam = logic.getTeam(expandedPane.getText());
                System.out.println("testTeam: " + testTeam.getName()); 
            } catch (Exception e) {
    //            e.printStackTrace();
            }
        
            grid.add(getPlayerDetails(addPlayerButton, testTeam, acc.getExpandedPane()),1,0);
            
            titledPane.setText(t.getName());
            titledPane.setContent(grid);
            acc.getPanes().add(titledPane);
            
            
        }
        
        return acc;
        
    }
    
    private Tab createBasicTab(String header) {
        Tab tab = new Tab(header);
        tab.setClosable(false);
        BorderPane paneeli = new BorderPane();
        tab.setContent(paneeli);
        return tab;
    }
    

    
    private VBox getPlayerDetails(Button addPlayerButton, Team t, TitledPane panel) {
        VBox playerDetails = new VBox();
        Label headline = new Label("Firstname,Lastname,number,place:");
        TextField textField = new TextField();
        playerDetails.getChildren().addAll(headline, textField);
        
        addPlayerButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int number = 0;
                String[] pieces = textField.getText().split(",");
                try {
                    number = Integer.parseInt(pieces[2]);
                } catch (Exception e) {
                    System.out.println("Nullpointerexc");
                    System.out.println(e.getStackTrace());
                    
                }
                t.addPlayer(new Player(pieces[0], pieces[1], number), pieces[3]);
                Player plr = t.getPlayer(pieces[0], pieces[1]);
                System.out.println("Tallennettiin pelaaja: " + plr.getFirstName() + " " + plr.getLastName());
                System.out.println("tiimin pelaajat: " + t.toString());
            }
        });
        
        return playerDetails;
    }
    

}
