/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tournamentprogram;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Game class which object is a floorball game where you an add goals
 * @author pate
 */
public class Game {
    private LocalDateTime date;
    private Team homeTeam;
    private Team visitorTeam;
    private ArrayList<Goal> homeGoals;
    private ArrayList<Goal> visitorGoals;
    private static int nextGoalOrderNumber;
    private String gameId;
    
    /**
     *
     * @param homeTeam
     * @param vistitorTeam
     */
    public Game(Team homeTeam, Team vistitorTeam, int nextGoalOrderNumber) {
        this.homeTeam = homeTeam;
        this.visitorTeam = vistitorTeam;
        this.homeGoals = new ArrayList<>();
        this.visitorGoals = new ArrayList<>();
        this.nextGoalOrderNumber = nextGoalOrderNumber+1;
        this.date = LocalDateTime.now();
        this.gameId = Integer.toString(date.getDayOfYear()) + "." + 
                Integer.toString(date.getMonthValue()) + "." +
                Integer.toString(date.getYear()) + "." +
                homeTeam.getTeamId() + "." +
                visitorTeam.getTeamId();
    }
    
    /**
     *
     * @return
     */
    public LocalDateTime getDate(){
        return this.date;
    }
    
    /**
     *
     * @param id
     */
    public void setId(String id) {
        this.gameId = id;
    }

    /**
     *
     * @return
     */
    public String getGameId(){
        return this.gameId;
    }
    
    public int getNextGoalOrderNumber() {
        return this.nextGoalOrderNumber;
    }
    
    /** Add home goal to the game functions. Own functions for both teams to clarify
     * the class
     * @param players
     * @param time The time when the goal was done. This field can be left empty if it is not needed
     * with empty string"".
     */
    
    public void addHomeGoal(ArrayList<Player> players, int nextGoalOrderNumber,  String teamId, String time) {
        Goal newGoal = new Goal(nextGoalOrderNumber, teamId);
        newGoal.setScorer(players.get(0));
        if (players.size()>1) {
            newGoal.setAssist1(players.get(1));
        }
        if (players.size() > 2) {
            newGoal.setAssist2(players.get(2));
        }
        newGoal.setTime(time);
        this.homeGoals.add(newGoal);
//        System.out.println("Goal index: " + this.goalIndex);
        this.nextGoalOrderNumber++;
    }

    /**
     *
     * @param scorer
     * @param assist1
     * @param assist2
     * @param time
     */
    public void addHomeGoal(int nextGoalOrderNumber, String teamId, Player scorer, Player assist1, Player assist2, String time) {
                System.out.println("Game: addHomeGoal: goalOrderNumber: " + nextGoalOrderNumber );

        Goal newGoal = new Goal(gameId, nextGoalOrderNumber, teamId, scorer, assist1, assist2, time); //goalORderNumber, scorer, assist1 assist2, time, this.gameId
        setGoalId(newGoal);
        this.homeGoals.add(newGoal);
        this.nextGoalOrderNumber++;
    }
    
    /**
     *
     * @param scorer
     * @param assist1
     * @param time
     */
    public void addHomeGoal(int nextGoalOrderNumber, String teamId ,Player scorer, Player assist1, String time) {
                System.out.println("Game: addHomeGoal: goalOrderNumber: " + nextGoalOrderNumber );

        Goal newGoal = new Goal(nextGoalOrderNumber, teamId, scorer, assist1, time);
        setGoalId(newGoal);
        this.homeGoals.add(newGoal);
        this.nextGoalOrderNumber++;
    }
    
    /**
     *
     * @param time
     */
    public void addHomeGoal(int nextGoalOrderNumber, String teamId, String time){
                System.out.println("Game: addHomeGoal: goalOrderNumber: " + nextGoalOrderNumber );

        Goal newGoal = new Goal(nextGoalOrderNumber, teamId, time);
        setGoalId(newGoal);
        this.homeGoals.add(newGoal);
        this.nextGoalOrderNumber++;
    }
    
    /**
     *
     * @param scorer
     * @param time
     */
    public void addHomeGoal(int nextGoalOrderNumber, String teamId, Player scorer, String time) {
                System.out.println("Game: addHomeGoal: goalOrderNumber: " + nextGoalOrderNumber );

        Goal newGoal = new Goal(nextGoalOrderNumber, teamId, scorer, time);
        setGoalId(newGoal);
        this.homeGoals.add(newGoal);
        this.nextGoalOrderNumber++;
    }
    
    /**
     * Adding visitor goals to game.
     * @param players
     * @param time Time when the goal was done
     * This field can be left empty if it is not needed
     * with empty string "".
     */
    public void addVisitorGoal(ArrayList<Player> players, int nextGoalOrderNumber, String teamId, String time) {
        System.out.println("Game: addHomeGoal: goalOrderNumber: " + nextGoalOrderNumber );

        Goal newGoal = new Goal(nextGoalOrderNumber, teamId, time);
        newGoal.setScorer(players.get(0));
        if (players.size()>1) {
            newGoal.setAssist1(players.get(1));
        }
        if (players.size() > 2) {
            newGoal.setAssist2(players.get(2));
        }
        this.visitorGoals.add(newGoal);
//        System.out.println("Goal index: " + this.goalIndex);
        this.nextGoalOrderNumber++;
    }
    
    /**
     *
     * @param scorer
     * @param assist1
     * @param assist2
     * @param time
     */
    public void addVisitorGoal(String teamId, int nextGoalOrderNumber, Player scorer, Player assist1, Player assist2, String time) {
        System.out.println("Game: addVisitorGoal: goalOrderNumber: " + nextGoalOrderNumber );
        Goal newGoal = new Goal(this.gameId, nextGoalOrderNumber, teamId, scorer, assist1, assist2, time);
        setGoalId(newGoal);
        this.visitorGoals.add(newGoal);
        this.nextGoalOrderNumber++;
    }
    
    /**
     *
     * @param scorer
     * @param assist1
     * @param time
     */
    public void addVisitorGoal(int nextGoalOrderNumber, String teamId, Player scorer, Player assist1, String time) {
        Goal newGoal = new Goal(nextGoalOrderNumber, teamId, scorer, assist1, time);
        System.out.println("Game: addVisitorGoal: goalOrderNumber: " + nextGoalOrderNumber );

        setGoalId(newGoal);
        this.visitorGoals.add(newGoal);
        this.nextGoalOrderNumber++;;
    }
    
    /**
     *
     * @param scorer
     * @param time
     */
    public void addVisitorGoal(int nextGoalOrderNumber, String teamId, Player scorer, String time) {
        System.out.println("Game: addVisitorGoal: goalOrderNumber: " + nextGoalOrderNumber );

        Goal newGoal = new Goal(nextGoalOrderNumber, teamId, scorer, time);
        setGoalId(newGoal);
        this.visitorGoals.add(newGoal);
        this.nextGoalOrderNumber++;   
    }
    
    /**
     *
     * @param time
     */
    public void addVisitorGoal(int nextGoalOrderNumber, String teamId, String time){
        System.out.println("Game: addVisitorGoal: goalOrderNumber: " + nextGoalOrderNumber );

        Goal newGoal = new Goal(nextGoalOrderNumber, teamId, time);
        setGoalId(newGoal);
        this.visitorGoals.add(newGoal);
        this.nextGoalOrderNumber++;
    }
    
    private void setGoalId(Goal goal) {
        goal.setGoalId(nextGoalOrderNumber, this.gameId);
    }
    
    /**Remove specific goal from game
     * THIS NEEDS TO BE IMPLEMENTED
     * @param name
     * @return 
     */

    
    public boolean removeTeamFromGame(String name) {
        for (int i=0;i<homeGoals.size();i++) {

//            THIS NEEDS TO BE IMPLEMENTED

//                if(goals.get(i).getTeamName().equalsIgnoreCase(name)) {
//                    goals.remove(i);
//                }
                
        }
        if (this.visitorTeam.getName().equalsIgnoreCase(name)) {
            this.visitorTeam = new Team("");
            return true;
        } else if (this.homeTeam.getName().equalsIgnoreCase(name)) {
            this.homeTeam = new Team("");
            return true;
        } else{
            return false;
        }
    }
    
    /**
     *
     * @param t
     */
    public void setHomeTeam(Team t) {
        this.homeTeam = t;
    }

    /**
     *
     * @param t
     */
    public void setVisitorTeam(Team t) {
        this.visitorTeam = t;
    }
    
    /**
     *
     * @return
     */
    public ArrayList<Goal> getHomeGoals() {
        return homeGoals;
    }
    
    /**
     *
     * @return
     */
    public ArrayList<Goal> getVisitorGoals() {
        return visitorGoals;
    }

    /**
     *
     * @return
     */
    public Team getHomeTeam() {
        return homeTeam;
    }

    /**
     *
     * @return
     */
    public Team getVisitorTeam() {
        return visitorTeam;
    }
    
    @Override
    public String toString() {
        return homeTeam.getName() +"-"+ visitorTeam.getName();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.homeTeam);
        hash = 29 * hash + Objects.hashCode(this.visitorTeam);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Game other = (Game) obj;
        if (!Objects.equals(this.homeTeam, other.homeTeam)) {
            return false;
        }
        if (!Objects.equals(this.visitorTeam, other.visitorTeam)) {
            return false;
        }
        return true;
    }
    
    
    
}
