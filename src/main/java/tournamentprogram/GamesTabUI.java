/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tournamentprogram;

import com.sun.javafx.font.freetype.HBGlyphLayout;
import com.sun.javafx.scene.control.skin.ComboBoxPopupControl;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 *
 * @author pitkapa7
 */
public class GamesTabUI {
    private ApplicationLogic logic;

    public GamesTabUI(ApplicationLogic logic) {
        this.logic = logic;
    }
    
    /** Create games tab */
    public Tab createGamesTab() {
        Tab tab = createBasicTab("Games");
        VBox mainBox = new VBox();
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setContent(mainBox);
        tab.setContent(scrollPane);
        tab.setClosable(false);
        Accordion acc = new Accordion();
        GridPane addGamePane = new GridPane();
        mainBox.getChildren().addAll(addGamePane, acc);
        
        tab.setOnSelectionChanged(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                mainBox.getChildren().clear();
                addGameGrid(mainBox);
                updateAccordion(mainBox);
            }
        });
        return tab;
    }
    
    private void updateAccordion(VBox mainBox) {
//        System.out.println("mainBox.getChildren().size()" + mainBox.getChildren().size());
//        System.out.println("mainBox.getChildren() content");
//        for (Node node : mainBox.getChildren()) {
//            System.out.println("type: " + node.getClass());
//        }
        Accordion acc = new Accordion();

        for (Game game : logic.getGames()) {
            acc.getPanes().add(getGameTitledPane(game));
        }
        mainBox.getChildren().add(acc);
    }
    
    private TitledPane getGameTitledPane(Game game) {
            TitledPane gameGoalsPanel = new TitledPane();
            VBox gameVbox = getGameContentLayout(game, gameGoalsPanel);
            gameGoalsPanel.setContent(gameVbox);
            gameGoalsPanel.setText(game.getHomeTeam().getName()+" - " + game.getVisitorTeam().getName() +
                    " " + logic.getHomeTeamGoals(game).size() + " - " + logic.getVisitorTeamGoals(game).size());
            return gameGoalsPanel;
    }
    
    private void updateGameStatus(Game game, TitledPane gameGoalsPanel) {
        gameGoalsPanel.setText(game.getHomeTeam().getName()+" - " + game.getVisitorTeam().getName() +
                    " " + logic.getHomeTeamGoals(game).size() + " - " + logic.getVisitorTeamGoals(game).size());
        VBox gameContentVbox = (VBox) gameGoalsPanel.getContent();
        GridPane gameGoalsGrid = (GridPane) gameContentVbox.getChildren().get(0);
        updateGameGoalsInGridPane(game, gameGoalsGrid);
        gameGoalsPanel.setContent(gameContentVbox);
    }
    
    private void updateGameGoalsInGridPane(Game game, GridPane gameGoalsGrid) {
        if (gameGoalsGrid.getChildren().size() >2) {
            gameGoalsGrid.getChildren().remove(2, gameGoalsGrid.getChildren().size());
        }
        for (Goal homeGoal : logic.getHomeTeamGoals(game)) {
            gameGoalsGrid.add(getGameGoalDetailsVbox(homeGoal),0,homeGoal.getGoalOrderNumber());
        }
        for (Goal visitorGoal : logic.getVisitorTeamGoals(game)) {
            gameGoalsGrid.add(getGameGoalDetailsVbox(visitorGoal),1,visitorGoal.getGoalOrderNumber());
        }
    }
    
    private VBox getGameGoalDetailsVbox(Goal goal) {
        VBox goalBox = new VBox();
        goalBox.getChildren().addAll(new Label("Scorer:"), new Label(goal.getGoalOrderNumber() + " " + 
                goal.getScorer().getFullName()), new Label("Assist:"));
        goal.getAssistsInArrayList().stream()
                .forEach(player -> goalBox.getChildren().add(new Label(player.getFullName())));
        return goalBox;
    }
    
    /**
     * 
     * @param game
     * @return 
     */
    private VBox getGameContentLayout(Game game, TitledPane gameTitledPanel){
        VBox gameContentLayoutVbox = new VBox();

        Button addHomeGoalBtn = new Button("Add Home Goal");
        Button addVisitorGoalBtn = new Button("Add Visitor Goal");
        HBox addGoalsButtons = new HBox();
        addGoalsButtons.getChildren().addAll(addHomeGoalBtn, addVisitorGoalBtn);
        GridPane gameGoalsGrid = new GridPane();
        gameGoalsGrid.add(setHeadlineTextFormat(new Text("Home Goals")), 0, 0);
        gameGoalsGrid.add(setHeadlineTextFormat(new Text("Visitor Goals")), 1, 0);
        updateGameGoalsInGridPane(game, gameGoalsGrid);
        gameContentLayoutVbox.getChildren().add(gameGoalsGrid);
        gameContentLayoutVbox.getChildren().addAll(addGoalsButtons);
        
        addHomeGoalBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showAddGoalDialog(game,gameTitledPanel, "home");
            }
        });
        
        addVisitorGoalBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showAddGoalDialog(game,gameTitledPanel, "visitor");
            }
        });
        return gameContentLayoutVbox;
    }
   
    private void showAddGoalDialog(Game game, TitledPane gameTitledPanel, String side) {
        System.out.println("addGoalToGame");
        Dialog<ButtonType> addHomeGoalTogamePopup = new Dialog();
        ButtonType addButton = new ButtonType("Add");

        addHomeGoalTogamePopup.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);
        GridPane addGoalDetailsGrid = getAddGoalDialogDetailsGrid(side, game);
        addHomeGoalTogamePopup.getDialogPane().setContent(addGoalDetailsGrid);
        Optional<ButtonType> result = addHomeGoalTogamePopup.showAndWait();
        
        if (result.isPresent() && result.get().getText().equalsIgnoreCase("Add")) {
//            System.out.println("Add button pressed");
//            System.out.println("addGoalDetailsGrid.getChildren().get(4).getClass(): " + addGoalDetailsGrid.getChildren().get(4).getClass());;
//            System.out.println("addGoalDetailsGrid.getChildren().get(6).getClass(): " + addGoalDetailsGrid.getChildren().get(6).getClass());;
//            System.out.println("addGoalDetailsGrid.getChildren().get(7).getClass(): " + addGoalDetailsGrid.getChildren().get(7).getClass());;
            ComboBox<String> scorer = (ComboBox<String>) addGoalDetailsGrid.getChildren().get(5);
            ComboBox<String> assist1 = (ComboBox<String>) addGoalDetailsGrid.getChildren().get(6);
            ComboBox<String> assist2 = (ComboBox<String>) addGoalDetailsGrid.getChildren().get(7);
            TextField time = (TextField) addGoalDetailsGrid.getChildren().get(4);

            if (side.equalsIgnoreCase("home")) {
                logic.addHomeGoalToGame(scorer.getValue(), assist1.getValue(), assist2.getValue(), time.getText(), game);
            } else {
                logic.addVisitorGoalToGame(scorer.getValue(), assist1.getValue(), assist2.getValue(), time.getText(), game);
            }
        } else {
            System.out.println("Cancel pressed");
        }
        updateGameStatus(game, gameTitledPanel);
    }
    
    private GridPane getAddGoalDialogDetailsGrid(String side, Game game) {
        System.out.println("Adding home goal");
        GridPane goalDetailsInputGrid = new GridPane();
        Label scorer = new Label("Scorer: ");
        Label assist1 = new Label("Assist 1: ");
        Label assist2 = new Label("Assist 2: ");
        Label timeLabel = new Label("Time: ");
        scorer.setStyle("-fx-font-weight: bold");
        TextField timeField = new TextField();
        goalDetailsInputGrid.add(scorer, 0, 0);
        goalDetailsInputGrid.add(assist1, 0, 1);
        goalDetailsInputGrid.add(assist2, 0, 2);
        goalDetailsInputGrid.add(timeLabel, 0, 3);
        goalDetailsInputGrid.add(timeField, 1, 3);

        ComboBox<String> scorerComboBox = new ComboBox<>();
        ComboBox<String> assist1ComboBox = new ComboBox<>();
        ComboBox<String> assist2ComboBox = new ComboBox<>();
        
        if (side.equalsIgnoreCase("home")) {
            scorerComboBox = getTeamPlayersInComboBox(game.getHomeTeam());
            assist1ComboBox = getTeamPlayersInComboBox(game.getHomeTeam());
            assist2ComboBox = getTeamPlayersInComboBox(game.getHomeTeam());
        } else {
            scorerComboBox = getTeamPlayersInComboBox(game.getVisitorTeam());
            assist1ComboBox = getTeamPlayersInComboBox(game.getVisitorTeam());
            assist2ComboBox = getTeamPlayersInComboBox(game.getVisitorTeam());
        }

        goalDetailsInputGrid.add(scorerComboBox, 1, 0);
        goalDetailsInputGrid.add(assist1ComboBox, 1, 1);
        goalDetailsInputGrid.add(assist2ComboBox, 1, 2);

//        String scorerDetails = scorerComboBox.getValue();
//        String assist1Details = assist1ComboBox.getValue();
//        String assis2Details = assist2ComboBox.getValue();
//        String time = timeField.getText();
//        System.out.println("scorerDet: " + scorerDetails);
//        System.out.println("assist1Det: " + assist1Details);
//        System.out.println("assist2Det: " + assis2Details);
//        logic.addHomeGoalToGame(scorerDetails, assist1Details, assis2Details, game, time);
//        ArrayList<Goal> homeGoals = logic.getHomeTeamGoals(game);
//        for (Goal goal : homeGoals) {
//            System.out.println("Scorer: " + goal.getScorer());
//        }
        return goalDetailsInputGrid;    
    }
    
    private Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
}
   
    
//    private void updateGameGoalDetailsLayout(Game game, VBox gameGoalDetailsVbox) {
//        for (Goal goal : game.getHomeGoals()) {
//            Label goalLabel = new Label(goal.getScorer().getFullName());
//            gameGoalDetailsVbox.getChildren().add(goalLabel);
//        }
//        for (Goal goal : game.getVisitorGoals()) {
//            Label goalLabel = new Label(goal.getScorer().getFullName());
//            gameGoalDetailsVbox.getChildren().add(goalLabel);
//        }
//    }
    
    /**
     * getTeamPlayersInComboBox() returns selected teams players in combobox
     * @param t is the team which players will be returned
     * @return 
     */
    private ComboBox<String> getTeamPlayersInComboBox(Team t){
        ObservableList<String> playersInObsList = FXCollections.observableArrayList();
        for (Player player : t.getAllPlayersInArrayList()) {
            playersInObsList.add(player.getFirstName() + " " + player.getLastName() + ", " + player.getNumber());
        }
        ComboBox<String> comboBox = new ComboBox<>(playersInObsList);
        comboBox.getSelectionModel().selectFirst();
        return new ComboBox<>(playersInObsList);
    }
    
    /**
     * addGameGrid() creates layout for adding a game to tournament. Parameter (VBox) is where the
     * Gridpane is placed.
     * @param mainBox 
     */
    private void addGameGrid(VBox mainBox) {
        GridPane addGameGrid = new GridPane();
        Button addGameBtn = new Button("Add Game");
        Label homeTeamLabel = new Label("Home Team:");
        Label visitorTeamLabel = new Label("Visitor Team:");
        ComboBox<String> comboBoxHome = getTeamsInComboBox(logic.getTeams());
        ComboBox<String> comboBoxVisitor = getTeamsInComboBox(logic.getTeams());
        addGameGrid.add(homeTeamLabel, 1, 0);
        addGameGrid.add(visitorTeamLabel, 2, 0);
        addGameGrid.add(addGameBtn, 0, 1);
        addGameGrid.add(comboBoxHome, 1, 1);
        addGameGrid.add(comboBoxVisitor, 2, 1);
        
        mainBox.getChildren().add(addGameGrid);
        
        addGameBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(logic.createGame(logic.getTeam(comboBoxHome.getValue()), logic.getTeam(comboBoxVisitor.getValue()))) {
//                    System.out.println("Added Game: " + logic.getGame(comboBoxHome.getValue(), comboBoxVisitor.getValue()));
                } else {
//                    System.out.println("Game added else stmt");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Team cannot play against itself");
                    alert.showAndWait();
                }
                mainBox.getChildren().remove(1);
                updateAccordion(mainBox);
            }
        });
    }
    
    /**
     * getTeamComboBox returns a combobox where Teams are options
     */
    public ComboBox<String> getTeamsInComboBox(ArrayList<Team> teams){
        ObservableList<String> teamsInObsList = FXCollections.observableArrayList();
        for (Team team : logic.getTeams()) {
            teamsInObsList.add(team.getName());
//            System.out.println("team: " + team.getName());
        }
        ComboBox<String> comboBox = new ComboBox<>(teamsInObsList);
        comboBox.getSelectionModel().selectFirst();
        return comboBox;
    }

    /** Basic layout content */
    /** This will create basic tab layout which is used in this program */
    private Tab createBasicTab(String header) {
        Tab tab = new Tab(header);
        tab.setClosable(false);
        BorderPane paneeli = new BorderPane();
        tab.setContent(paneeli);
        return tab;
    }
    
    private Text setHeadlineTextFormat(Text t) {
        t.setFont(Font.font("Verdana", FontWeight.BOLD, 14.0));
        return t;
    }
    

}
