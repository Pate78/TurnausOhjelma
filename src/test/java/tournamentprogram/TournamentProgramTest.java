/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tournamentprogram;

import tournamentprogram.Player;
import tournamentprogram.Team;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author pitkapa7
 */
public class TournamentProgramTest {
    
    private Player p,p2,p3;
    private Team t,t2;
    private ApplicationLogic logic;

    @Before
    public void createPlayerAndTeam(){
        t = new Team("Kuukka Gunners");
        t2 = new Team("O2");
        p = new Player("Matti", "Hyökkääjä", 99, t);
        p2 = new Player("Milla", "Puolustaja", 69, t);
        p3 = new Player("Mika", "Maalivahti", 1, t);
        t.addPlayer(p, "offence");
        t.addPlayer(p2, "defence");
        t.addPlayer(p3, "goalkeeper");
        logic = new ApplicationLogic();
        logic.createGame(t, t2);
    }
    
    /** Team class tests */
    @Test
    public void offencePlayersInTeam(){
        assertEquals(1, t.getOffencePlayers().size());
    }
    
    @Test
    public void defencePlayersInTeam(){
        assertEquals(1, t.getDefencePlayers().size());
    }
    
    @Test
    public void goalkeepersInTeam(){
        assertEquals(1, t.getGoalkeepers().size());
    }
    
    @Test
    public void getTeamName(){
        assertEquals(t.getName(), "Kuukka Gunners");
    }
    
    @Test
    public void removePlayerFromTeam(){
        t.removePlayer(p2, "Not The Place Where I'm Playing");
        assertEquals(3, t.getAllPlayersInArrayList().size());
        t.removePlayer(p2, "defence");
        assertEquals(2, t.getAllPlayersInArrayList().size());
        t.removePlayer(p, "offence");
        assertEquals(1, t.getAllPlayersInArrayList().size());
        t.removePlayer(p3, "goalkeeper");
        assertEquals(0, t.getAllPlayersInArrayList().size());
    }
    
    @Test
    public void findPlayer() {
        assertEquals(p, t.getPlayer("Matti", "Hyökkääjä"));
        assertEquals(p2, t.getPlayer("Milla", "Puolustaja"));
        assertEquals(p3, t.getPlayer("Mika", "Maalivahti"));
        assertEquals(null, t.getPlayer("Keke", "Rosberg"));
        assertEquals(null, t.getPlayer("Mika", "Hyökkääjä"));
    }
    
    @Test
    public void getPlayers() {
        assertEquals(3, t.getAllPlayersInArrayList().size());
    }
    
    @Test
    public void addPlayerToNonExistingPlace(){
        t.addPlayer(p, "Not The Place I Want To Play In");
        assertEquals(3, t.getAllPlayersInArrayList().size());
    }
    
    @Test
    public void getHashCode() {
        assertEquals(-1406378517, t.hashCode());
    }
    
    @Test
    public void teamEquals() {
        Team t2 = t;
        assertTrue(t.equals(t2));
        assertFalse(t.equals(new Team("Pielisen Pelaajat")));
        assertFalse(t.equals(null));
        assertFalse(t.equals(new Player("Jaakko", "Saariluoma", 1, new Team("Tampereen Pässit"))));
        assertTrue(t.equals(new Team("Kuukka Gunners")));
    }
    
    /** Player-class tests */
    @Test
    public void getPlayerNumber(){
        assertEquals(p.getNumber(), 99);
        assertTrue(p.setNumber(69));
        assertFalse(p.setNumber(-2));
        assertEquals(p.getNumber(), 69);
    }
    
    @Test
    public void getPlayerFirstName() {
        assertEquals(p.getFirstName(), "Matti");
    }
    
    @Test
    public void getPlayerLastName() {
        assertEquals(p.getLastName(), "Hyökkääjä");
    }
    
    @Test
    public void goalsMadeByPlayer() {
        p.addGoal();
        assertEquals(1, p.getGoals());
    }
    
    @Test
    public void removeGoalFromPlayer(){
        p.addGoal();
        p.removeGoal();
        p.removeGoal();
        assertEquals(0, p.getGoals());
    }
    
    @Test
    public void getAssists() {
        p.addAssist();
        p.addAssist();
        p.removeAssist();
        p.removeAssist();
        p.removeAssist();
        assertEquals(p.getAssists(), 0);
    }
    
    @Test
    public void playerGoalsNotNegative(){
        p.removeGoal();
        assertEquals(0, p.getGoals());
    }
    
    @Test
    public void getPlayerToString(){
        p.addAssist();
        p.addGoal();
        p.addGoal();
        assertEquals(p.toString(), "Matti Hyökkääjä, 99, goals: 2, assists: 1");
    }
    
    @Test
    public void playerComparisionIsOk(){
        assertEquals(p2.compareTo(p), 0);
        p.addGoal();
        assertEquals(p2.compareTo(p), -1);
        assertEquals(p.compareTo(p2), 1);
        p2.addGoal();
        p.addAssist();
        assertEquals(p2.compareTo(p), -1);
        assertEquals(p.compareTo(p2), 1);
    }
    
    /** Application Logic class tests */
    @Test
    public void addGame(){
        logic.createGame(t, t2);
        assertEquals(2, logic.getGames().size());
        assertFalse(logic.createGame(t, t));
    }
    
    @Test
    public void addHomeGoal() {
        assertEquals(0, logic.getHomeTeamGoals(logic.getGame(t.getName(), t2.getName())).size());
    }
    
    @Test
    public void homeGoalsArrayListIsOk() {
        logic.addHomeGoalToGame("", "", "", "", logic.getGame(t.getName(), t2.getName()));
        assertEquals(1, logic.getHomeTeamGoals(logic.getGame(t.getName(), t2.getName())).size());
    }
    
    @Test
    public void addVisitorGoals(){
        assertEquals(0, logic.getVisitorTeamGoals(logic.getGame(t.getName(), t2.getName())).size());
//        logic.addVisitorGoalToGame("", "", "", "", logic.getGame(t.getName(), t2.getName()));
    }
    
    @Test
    public void getVisitorGoalsArrayListIsOk() {
       logic.addVisitorGoalToGame("", "", "", "", logic.getGame(t.getName(), t2.getName()));
       assertEquals(1, logic.getVisitorTeamGoals(logic.getGame(t.getName(), t2.getName())).size()); 
    }
    
//    @Test
//    public void getIndexOfTeam(){
//        assertEquals(1, logic.get);
//    }
    
    
    
 
    
}
