/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tournamentprogram;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Observable;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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

    public Tab createTeamsTab() {
        Tab tab = new Tab("Teams");
        VBox mainBox = new VBox();
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setContent(mainBox);
        tab.setContent(scrollPane);
        VBox teamsInVbox = new VBox();
        HBox addTeamBox = new HBox();
        Button addTeamBtn = new Button("Add Team");
        TextField teamName = new TextField();
        addTeamBox.getChildren().addAll(addTeamBtn, teamName);
        Accordion acc = new Accordion();
        for (Team team : logic.getTeams()) {
            addNewTeamPanel(team.getName(),acc);
        }
        mainBox.getChildren().addAll(addTeamBox, acc);
//        mainBox.getChildren().addAll(addTeamBox, teamsInVbox);

        tab.setClosable(false);

        addTeamBtn.setOnAction(event -> {
//                System.out.println("teamName.getText(): " + teamName.getText());
                logic.addTeam(teamName.getText());
                teamsInVbox.getChildren().add(new Label(teamName.getText()));
                addNewTeamPanel(teamName.getText(), acc);
        });

        return tab;
    }
    
    private void addNewTeamPanel(String teamName, Accordion acc){
        TitledPane teamPanel = new TitledPane();
        teamPanel.setText(teamName);
        teamPanel.setContent(getTeamVboxLayout(teamName));
        acc.getPanes().add(teamPanel);
    }
    
    private VBox getTeamVboxLayout(String teamName){
        VBox teamsVbox = new VBox();
        
        HBox offencePlayersBox = new HBox();
        HBox defencePlayersBox = new HBox();
        HBox goalkeepersPlayersBox = new HBox();
        offencePlayersBox.getChildren().addAll(new Label("Offence"), getTeamPlayersVBox(teamName, "Offence"));
        defencePlayersBox.getChildren().addAll(new Label("Defence"), getTeamPlayersVBox(teamName, "defence"));
        goalkeepersPlayersBox.getChildren().addAll(new Label("Goalkeepers"), getTeamPlayersVBox(teamName, "goalkeepers"));
        teamsVbox.getChildren().addAll(offencePlayersBox,defencePlayersBox,goalkeepersPlayersBox);
        
        GridPane addPlayerToTeam = (GridPane)addPlayerToTeamBoxLayout(teamsVbox, teamName);
        teamsVbox.getChildren().add(addPlayerToTeam);
        
        return teamsVbox;
    }
    
    private VBox getTeamPlayersVBox(String name, String position){
        VBox playerBox = new VBox();
        if (position.equalsIgnoreCase("offence") && logic.getTeam(name).getOffencePlayers().size()>0) {
            for (Player player : logic.getTeam(name).getOffencePlayers()) {
                Label playerLabel = new Label(player.getFirstName() + " " + player.getLastName());
                playerBox.getChildren().add(playerLabel);
            }
        } else if (position.equalsIgnoreCase("defence") && logic.getTeam(name).getDefencePlayers().size()>0) {
            for (Player player : logic.getTeam(name).getDefencePlayers()) {
                Label playerLabel = new Label(player.getFirstName() + " " + player.getLastName());
                playerBox.getChildren().add(playerLabel);
            }
        } else if (position.equalsIgnoreCase("goalkeepers") && logic.getTeam(name).getGoalkeepers().size()>0){
            for (Player player : logic.getTeam(name).getGoalkeepers()) {
                Label playerLabel = new Label(player.getFirstName() + " " + player.getLastName());
                playerBox.getChildren().add(playerLabel);
            }
        }
        return playerBox;
    }
    
    private Node addPlayerToTeamBoxLayout(VBox teamsVbox, String team){
        GridPane addPlayerGrid = new GridPane();
        Label player = new Label("Player firstname,lastname,number");
        Label position = new Label("Position");
        Button addPlayerBtn = new Button("Add player");
        TextField playerDetails = new TextField("pekka,pelaaja,33");
        ComboBox<String> positionComboBox = createPositionComboBox();
        addPlayerGrid.add(player, 1, 0);
        addPlayerGrid.add(position, 2, 0);
        addPlayerGrid.add(addPlayerBtn, 0, 1);
        addPlayerGrid.add(playerDetails, 1, 1);
        addPlayerGrid.add(positionComboBox, 2, 1);
        
        addPlayerBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                addPlayerToTeamFunction(teamsVbox, team, positionComboBox.getValue(),playerDetails.getText());
            }
        });
        
        return addPlayerGrid;
    }
    
    private ComboBox<String> createPositionComboBox() {
        ObservableList<String> playersInObsList = FXCollections.observableArrayList();
        playersInObsList.add("Offence");
        playersInObsList.add("Defence");
        playersInObsList.add("Goalkeeper");
        ComboBox<String> comboBox = new ComboBox<>(playersInObsList);
        comboBox.getSelectionModel().selectFirst();
        return comboBox;
    }
    
    private void addPlayerToTeamFunction(VBox teamsVbox, String teamName, String position, String details){
        String[] pieces = details.split(",");
        Player plr = new Player();
        int number = 0;
        VBox playersPerPositionVbox = new VBox();
        try {
            number = Integer.parseInt(pieces[2]);
        } catch (Exception e) {
            teamsVbox.getChildren().add(new Label("Converting String to player number failed"));
            e.printStackTrace();
        }
        plr = new Player(pieces[0],pieces[1],number,logic.getTeam(teamName));
        
        logic.addPlayerToTeam(plr, position.toLowerCase(),logic.getTeam(teamName));
        if (position.toLowerCase().equalsIgnoreCase("offence")) {
            HBox positionHbox = (HBox)teamsVbox.getChildren().get(0);
            playersPerPositionVbox = (VBox)positionHbox.getChildren().get(1);
        } else if (position.toLowerCase().equalsIgnoreCase("defence")) {
            HBox positionHbox = (HBox) teamsVbox.getChildren().get(1);
            playersPerPositionVbox = (VBox)positionHbox.getChildren().get(1);
        } else if (position.toLowerCase().equalsIgnoreCase("goalkeeper")) {
            HBox positionHbox = (HBox) teamsVbox.getChildren().get(2);
            playersPerPositionVbox = (VBox)positionHbox.getChildren().get(1);
        }
        playersPerPositionVbox.getChildren().add(new Label(plr.getFirstName()+" "+plr.getLastName()));
        
//        Team t = logic.getTeam(teamName);
//        Player p = logic.getPlayer(t.getName(), pieces[0], pieces[1]);
//        System.out.println("t.tostring" + t.toString());
//        System.out.println("t.getOffplrs.size" + t.getOffencePlayers().size());
//        System.out.println("t.getDefplrs.size" + t.getDefencePlayers().size());
//        System.out.println("t.getGoalplrs.size" + t.getGoalkeepers().size());

    }


    

}
