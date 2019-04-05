/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tournamentprogram;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pitkapa7
 */
public class Goal {
    private Player scorer;
    private Player assist1;
    private Player assist2;
    private String time;
    private int index;

    public Goal(int index){
    }
    
    public Goal(Player player, Player assist1, Player assist2, String time, int index) {
        this.scorer = player;
        this.assist1 = assist1;
        this.assist2 = assist2;
        this.time = time;
        this.index = index;
    }
    
    public Goal(Player scorer, Player assist1, String time, int index) {
        this.scorer = scorer;
        this.assist1 = assist1;
        this.time = time;
        this.index = index;
    }

    public Goal(Player scorer, String time, int index) {
        this.scorer = scorer;
        this.time = time;
        this.index = index;
    }
    
    public Player getScorer() {
        return scorer;
    }

    public ArrayList<Player> getAssist() {
        ArrayList<Player> assists = new ArrayList<>();
        assists.add(assist1);
        assists.add(assist2);
        return assists;
    }

    public String getTime() {
        return time;
    }
    
    public int getIndex() {
        return this.index;
    }
    
    
    
}
