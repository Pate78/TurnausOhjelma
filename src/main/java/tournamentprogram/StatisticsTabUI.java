/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tournamentprogram;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 *
 * @author pitkapa7
 */
public class StatisticsTabUI {

    private ApplicationLogic logic;

    public StatisticsTabUI(ApplicationLogic logic) {
        this.logic = logic;
    }
    
    public Tab createStatisticsTab() {
        Tab tab = new Tab();
        tab.setClosable(false);
        tab.setText("Statistics");
        GridPane teamStatisticsGrid = new GridPane();
        GridPane playerStatisticsGrid = new GridPane();
        VBox statisticsVbox = new VBox();
        statisticsVbox.setSpacing(5);
        statisticsVbox.getChildren().addAll(teamStatisticsGrid, playerStatisticsGrid);
        tab.setContent(statisticsVbox);
        
        tab.setOnSelectionChanged(event -> {
//            tab.setContent(new Label());
            updateAllGoalsGrid(teamStatisticsGrid);
            updatePlayerStatisticsGrid(playerStatisticsGrid);
        });
        
        return tab;
    }
            
    
    private Node updateAllGoalsGrid( GridPane grid) {
        grid.getChildren().remove(0, grid.getChildren().size());
        grid.add(setHeadlineTextFormat(new Text("Team stats:"), 12, 0, 0,true), 0, 0);
        grid.add(setHeadlineTextFormat(new Text("Team"),12,0,0,false), 0, 1);
        grid.add(setHeadlineTextFormat(new Text("Goals done"),12,0,0,false), 1, 1);
        grid.setPadding(new Insets(10));
        grid.setVgap(10);
        grid.setHgap(10);
        int i = 2;
        for (Team team : logic.getTeamsSorted()) {
            grid.add(new Text(team.getName()), 0, i);
            grid.add(new Text(Integer.toString(team.getGoalsDone())), 1, i);
            i++;
            System.out.println("StatisticTabUI: updateAllGoalsGrid: teamName: "+ team.getName() +" team.getGoalsDone(): " + team.getGoalsDone());
        }
        return grid;
    }
    
    private Node updatePlayerStatisticsGrid(GridPane grid) {
        grid.getChildren().remove(0, grid.getChildren().size());
//        grid.add(new Text("Player"), 0, 0);
//        grid.add(new Text("Goals"), 1, 0);
        grid.setPadding(new Insets(10));
        grid.setVgap(10);
        grid.setHgap(10);
        grid.add(setHeadlineTextFormat(new Text("Player stats: "),12,0,0,true), 0, 1);
        grid.add(setHeadlineTextFormat(new Text("Player"),11,0,0,true), 1, 2);
        grid.add(setHeadlineTextFormat(new Text("Goals"),11,0,0,true), 2, 2);
        int rowNum = 3;

        for (Team team: logic.getTeams()) {
            grid.add(setHeadlineTextFormat(new Text(team.getName()),11,50.0,50.0, false), 0, rowNum);
            for (Player plr: logic.getPlayersSorted(team)) {
                rowNum++;
                grid.add(new Text(plr.getFullName()), 1, rowNum);
                grid.add(new Text(Integer.toString(plr.getGoals())), 2, rowNum);
            }
            rowNum++;
        }
        return grid;
    }
    
    private Text setHeadlineTextFormat(Text t, double fontSize, double setX, double setY, boolean underline) {
        t.setFont(Font.font("Verdana", FontWeight.BOLD, fontSize));
        t.setUnderline(underline);
        t.setX(setX);
        t.setY(setY);
        return t;
    }
}

