/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tournamentprogram;

import java.util.Objects;

/**
 * Player luokan ilmentymät ovat pelaajia joukkueissa. Pelaaja on yhtä kolmesta tyypistä: Offence eli 
 * hyökkäys, Defence eli puolustus tai Goalkeeper eli maalivahti. Player luokan ilmentymissä pidetään kirjaa 
 * myös pelaajan tekemistä maaleista ja syötöistä. Pelaajia pystyy myös laittamaan paremmuusjärjestykseen
 * tehtyjen maalien ja syöttöjen mukaan.
 * @author pitkapa7
 */
public class Player implements Comparable<Player>{
    private String firstname;
    private String lastname;
    private int number;
    private int goals;
    private int assists;
    private Team t;

    public Player() {
    }

    
    public Player(String firstname, String lastname, int number, Team t) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.number = number;
        this.goals = 0;
        this.assists = 0;
        this.t = t;
        
    }
    
    public void addGoal() {
        this.goals++;
    }
    
    public void addAssist() {
        this.assists++;
    }
    
    public void removeAssist() {
        if (assists>0) {
            assists--;
        }
    }
    
    public int getAssists() {
        return this.assists;
    }
    
    public boolean removeGoal(){
        if (this.goals > 0) {
            this.goals--;
            return true;
        }
        return false;
    }
    
    public int getGoals() {
        return this.goals;
    }

    public String getFirstName() {
        return firstname;
    }

    public String getLastName() {
        return lastname;
    }
    
    public String getFullName() {
        return this.firstname + " " + this.lastname;
    }

    public int getNumber() {
        return number;
    }

    public Team getTeam() {
        return t;
    }
    

    public boolean setNumber(int number) {
        if (number>0) {
            this.number = number;
            return true;
        }
        return false;
    }
     
    
    @Override
    public int compareTo(Player o) {
        if(this.goals>o.goals || (this.goals==o.goals && this.assists>o.assists)) {
            return 1;
        } else if (this.goals<o.goals || (this.goals==o.goals && this.assists<o.assists)) {
            return -1;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        return firstname + " " + lastname + ", " + number + ", goals: " + goals + ", assists: " + assists;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.firstname);
        hash = 29 * hash + Objects.hashCode(this.lastname);
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
        final Player other = (Player) obj;
        if (!Objects.equals(this.firstname, other.firstname)) {
            return false;
        }
        if (!Objects.equals(this.lastname, other.lastname)) {
            return false;
        }
        if (!Objects.equals(this.number, other.number)) {
            return false;
        }
        return true;
    }
    
    
    
    
    
    
    
    
}
