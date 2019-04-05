/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tournamentprogram;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

/**
 * Game class which object is a floorball game where you an add goals
 * @author pitkapa7
 */
public class Game {
    private Team homeTeam;
    private Team visitorTeam;
    private ArrayList<Goal> homeGoals;
    private ArrayList<Goal> visitorGoals;
    public int goalIndex;

    public Game(Team homeTeam, Team vistitorTeam) {
        this.homeTeam = homeTeam;
        this.visitorTeam = vistitorTeam;
        this.homeGoals = new ArrayList<>();
        this.visitorGoals = new ArrayList<>();
        this.goalIndex = 1;
    }
    
    /** Add home goal to the game functions. Own functions for both teams to clarify
     * the class
     * @param scorer Player who did the goal
     * @param assist1 Player who assisted the goal.
     * @param assist2 Player who assisted the goal.
     * @param time The time when the goal was done. This field can be left empty if it is not needed
     * with empty string"".
     */
    public void addHomeGoal(Player scorer, Player assist1, Player assist2, String time) {
        this.homeGoals.add(new Goal(scorer, assist1, assist2, time, goalIndex));
        this.goalIndex++;
    }
    
    public void addHomeGoal(Player scorer, Player assist1, String time) {
        this.homeGoals.add(new Goal(scorer, assist1, time, goalIndex));
        this.goalIndex++;
    }
    
    public void addHomeGoal(String time){
        this.homeGoals.add(new Goal(goalIndex));
        this.goalIndex++;
    }
    
    public void addHomeGoal(Player scorer, String time) {
        this.homeGoals.add(new Goal(scorer, time, goalIndex));
        this.goalIndex++;
    }
    
    /**
     * Adding visitor goals to game.
     * @param scorer Player who did the goal
     * @param assist1 Player who assisted the goal
     * @param assist2 Player who assisted the goal
     * @param time Time when the goal was done
     * This field can be left empty if it is not needed
     * with empty string "".
     */
    public void addVisitorGoal(Player scorer, Player assist1, Player assist2, String time) {
        this.visitorGoals.add(new Goal(scorer, assist1, assist2, time, goalIndex));
        this.goalIndex++;
    }
    
    public void addVisitorGoal(Player scorer, Player assist1, String time) {
        this.visitorGoals.add(new Goal(scorer, assist1, time, goalIndex));
        this.goalIndex++;
    }
    
    public void addVisitorGoal(Player scorer, String time) {
        this.visitorGoals.add(new Goal(scorer ,time, goalIndex));
        this.goalIndex++;      
    }
    
    public void addVisitorGoal(String time){
        this.visitorGoals.add(new Goal(goalIndex));
        this.goalIndex++;
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
    
    public void setHomeTeam(Team t) {
        this.homeTeam = t;
    }

    public void setVisitorTeam(Team t) {
        this.visitorTeam = t;
    }
    
    public ArrayList<Goal> getHomeGoals() {
        return homeGoals;
    }
    
    public ArrayList<Goal> getVisitorGoals() {
        return visitorGoals;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

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
