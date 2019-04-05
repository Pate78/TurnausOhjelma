/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tournamentprogram;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Team-luokan ilmentymiin tallennetaan joukkueen nimi sekä pelaajat. Pelaajat tallennetaan pelipaikan
 * mukaan ArrayList-tyyppisiin listoihin.
 * @author pitkapa7
 */
public class Team {
    private String teamName;
    private ArrayList<Player> playersOffence;
    private ArrayList<Player> playersDefence;
    private ArrayList<Player> playersGoalkeepers;

    public Team(String teamName) {
        this.teamName = teamName;
        this.playersOffence = new ArrayList<>();
        this.playersDefence = new ArrayList<>();
        this.playersGoalkeepers = new ArrayList<>();
    }
    
    public void addPlayer(Player player, String place) {
        switch(place) {
            case "offence":
               this.playersOffence.add(player);
                System.out.println("Team:addPlayer:offence");
               break;
            case "defence":
                this.playersDefence.add(player);
                System.out.println("Team:addPlayer:defence");
                break;
            case "goalkeeper":
                this.playersGoalkeepers.add(player);
                System.out.println("Team:addPlayer:goalkeeper");
                break;
            default:
                System.out.println("position not found");
                break;
        }
    }
    
    public List<Player> getOffencePlayers(){
        return new ArrayList<>(playersOffence);
    }
    public List<Player> getDefencePlayers(){
        return new ArrayList<>(playersDefence);
    }
    public ArrayList<Player> getGoalkeepers(){
        return new ArrayList<>(playersGoalkeepers);
    }
    
    /**
     * Palauttaa HashMap-tyyppisen listan pelaajista, jossa on avaimena pelipaikka
     * @return
     */
    public Map<String, ArrayList<Player>> getAllPlayersWithPosition() {
        Map<String, ArrayList<Player>> allPlayers = new HashMap<>();
        allPlayers.put("offence", playersOffence);
        allPlayers.put("defence", playersDefence);
        allPlayers.put("goalkeepers", playersGoalkeepers);
        return allPlayers;
    }
    
    /**
     * Palauttaa ArrayList tyyppisen listan pelaajista ilman pelipaikkatietoa.
     * @return
     */
    public ArrayList<Player> getAllPlayersInArrayList() {
        ArrayList<Player> allPlayers = new ArrayList<>();
        for (Player player : this.playersOffence) {
            allPlayers.add(player);
        }
        for (Player player : this.playersDefence) {
            allPlayers.add(player);
        }
        for (Player playersGoalkeeper : this.playersGoalkeepers) {
            allPlayers.add(playersGoalkeeper);
        }
        return allPlayers;
    }
    
    public String getName() {
        return this.teamName;
    }
    
    /**
     * @param fname Parametri fname on pelaajan etunimi.
     * @param lname Parametri lname on pelaajan sukunimi.
     * @return Metodilla etsitään pelaaja joukkueesta. Jos pelaajaa ei löydy, palautetaan null.
     */
    public Player getPlayer(String fname, String lname) {
        for (Player player : getAllPlayersInArrayList()) {
            if (player.getFirstName().equalsIgnoreCase(fname) && player.getLastName().equalsIgnoreCase(lname)) {
                return player;
            }
        }
        return null;//new Player("Player", "Not Found", 0, new Team("Player not found"));
    }
    
    public void removePlayer(Player player, String place) {
        switch(place) {
            case "offence":
                this.playersOffence.remove(player);
                break;
            case "defence":
                this.playersDefence.remove(player);
                break;
            case "goalkeeper":
                this.playersGoalkeepers.remove(player);
                break;
            default:
                break;
        }
    }
    
    @Override
    public String toString() {
        String team = this.teamName + "\n";
        team += "Offence\n";
        for (Player player : playersOffence) {
            team += player.toString()+"\n";
        }
        team += "Defence\n";
        for (Player player : playersDefence) {
            team += player.toString()+"\n";
        }
        team += "Goalkeepers\n";
        for (Player player : playersGoalkeepers) {
            team += player.toString()+"\n";
        }
        return team;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.teamName);
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
        final Team other = (Team) obj;
        if (!Objects.equals(this.teamName, other.teamName)) {
            return false;
        }
        return true;
    }
    
    
}
