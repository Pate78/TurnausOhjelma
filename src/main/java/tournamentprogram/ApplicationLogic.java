/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tournamentprogram;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.control.TitledPane;

/**
 *
 * @author pitkapa7
 */
public class ApplicationLogic {
    private ArrayList<Team> teams;
    private ArrayList<Game> games;

    public ApplicationLogic() {
        this.games = new ArrayList<>();
        this.teams = new ArrayList<>();
        Team t = new Team("Kuukka Gunners");
        Team t2 = new Team("Kuumat");

        t.addPlayer(new Player("Tiina", "offence", 39, t), "offence");
        t.addPlayer(new Player("Tiina", "defence", 39, t), "defence");
        t.addPlayer(new Player("Tiina", "goalkeeper", 39, t), "goalkeeper");
        Game testGame = new Game(t, t2);
//        games.add(testGame);
        
        System.out.println("playertostring: " + t.getAllPlayersWithPosition().toString());
        this.teams.add(t);
//                this.teams.add(t2);
        System.out.println("T hashcode"+ t.hashCode());
        System.out.println("t.tostring: " + t.toString());

    }
    
    public void addTeam(String name) {
        this.teams.add(new Team(name));
    }
    
    public ArrayList<Team> getTeams(){
        return new ArrayList<>(teams);
    }
    
    public Team getTeam(String teamName) {
        for (Team team : teams) {
            if (team.getName().equalsIgnoreCase(teamName)) {
                System.out.println("Returning team: " + team.getName());
                return team;
            }
        }
        System.out.println("App logic getTeam() returned empty team");
        return new Team("");
    }
    
    public void addPlayerToTeam(Player playerToAdd, String place, Team team) {
        getTeam(team.getName()).addPlayer(playerToAdd, place);
//        System.out.println("getTeam(team.getName())" + getTeam(team.getName()).toString());
    }
    
    public boolean removePlayerFromTeam (Player playerToRemove, Team team, String place) {
        if (getIndexOfTeam(team) != -1) {
            this.teams.get(getIndexOfTeam(team)).removePlayer(playerToRemove, place);
            return true;
        }
        return false;
    }
    
    public Map<String, ArrayList<Player>> getPlayersOfTeam(String name) {
        Map<String, ArrayList<Player>> players = new HashMap<>();
        for (Team team : teams) {
            if (team.getName().equalsIgnoreCase(name)) {
                return team.getAllPlayersWithPosition();
            }
        }
        return null;
    }
    
    public boolean createGame(Team home, Team visitor) {
        if (!home.equals(visitor)) {
            this.games.add(new Game(home, visitor));
            System.out.println("home.equals(visitor): " + home.equals(visitor));
            return true;
        }
        return false;
    }
    
    public Team getHomeTeamOfGame(Game game) {
        for (Game game1 : games) {
            if (game1.getHomeTeam().getName().equalsIgnoreCase(game.getHomeTeam().getName())) {
                return game1.getHomeTeam();
            }
        }
        System.out.println("App Logic getHomeTeamOfGame returned null");
        return null;
    }
    
    public Team getVisitorTeamOfGame(Game game) {
        for (Game game1 : games) {
            if (game1.getVisitorTeam().getName().equalsIgnoreCase(game.getVisitorTeam().getName())) {
                return game1.getVisitorTeam();
            }
        }
        System.out.println("App Logic getVisitorTeamOfGame returned null");
        return null;
    }
    
    public Game getGame(String home, String visitor) {
        for (Game game : games) {
            if (game.getHomeTeam().getName().equalsIgnoreCase(home) && game.getVisitorTeam().getName().equalsIgnoreCase(visitor)) {
                return game;
            }
        }
        System.out.println("App logic getGame returned null");
        return null;
    }
    
    public ArrayList<Game> getGames() {
        return this.games;
    }
    
    public void removeTeamFromGame(Team t, int index) {
        this.games.get(index).removeTeamFromGame(t.getName());
    }
    
    public Player getPlayer(String teamName, String fname, String lname){
        Team t = getTeam(teamName);
        return t.getPlayer(fname, lname);
    }
    
    /**
     * Method is used to extract search a player from combobox string selection.
     * "Player Not Found" is a special player which is always lost (cannot be found anywhere in playfield)
     * and still he is scoring and assisting scores. This player is filtered out of the user interface and
     * his scores will not be counted in personal scores since he is an ass but goals will be added to game.
     * @param nameAndNumberStringFromComboBox String from combobox which is selected. 
     * @param t Team where the player belongs
     * @return 
     */
    public Player getOnePlayerFromComboBoxOfTeam(String nameAndNumberStringFromComboBox, Team t) {
        if (nameAndNumberStringFromComboBox != null) {
            String[] pieces = nameAndNumberStringFromComboBox.split(",");
            if (pieces.length == 2) {
                System.out.println("pieces[1]: " + pieces[1]);
                String[] name = pieces[0].split(" ");
                ArrayList<Player> teamPlayers = t.getAllPlayersInArrayList();
                for (Player player : teamPlayers) {
                    if (player.getFirstName().equalsIgnoreCase(name[0]) && player.getLastName().equalsIgnoreCase(name[1])) {
                        player.addGoal();
                        return player;
                    }
                }
            }
        }
        return new Player("Player", "Not found", 0, new Team("Player not found"));
    }
    
    
    
    /**
     * Index of team in ArrayList<Team>. This will be used when team is removed.
     * @param team the team whose index is needed.
     * @return 
     */
    private int getIndexOfTeam(Team team) {
        for (int i = 0; i < teams.size(); i++) {
            if (teams.get(i).getName().equalsIgnoreCase(team.getName())) {
                return i;
            }
        }
        return -1;
    }
    
    public void addHomeGoalToGame(String nameAndNumberStringFromComboBoxScorer, String nameAndNumberStringFromComboBoxAssist1, String nameAndNumberStringFromComboBoxAssist2,  String time, Game game) {
        Player scorer = getOnePlayerFromComboBoxOfTeam(nameAndNumberStringFromComboBoxScorer, game.getHomeTeam());
        Player assist1 = getOnePlayerFromComboBoxOfTeam(nameAndNumberStringFromComboBoxAssist1, game.getHomeTeam());
        Player assist2 = getOnePlayerFromComboBoxOfTeam(nameAndNumberStringFromComboBoxAssist2, game.getHomeTeam());
//        Player scorer = getOnePlayerFromComboBoxOfTeam(nameAndNumberStringFromComboBoxScorer, game.getHomeTeam());
//        Player assist1 = getOnePlayerFromComboBoxOfTeam(nameAndNumberStringFromComboBoxAssist1, game.getHomeTeam());
//        Player assist2 = getOnePlayerFromComboBoxOfTeam(nameAndNumberStringFromComboBoxAssist2, game.getHomeTeam());
//
        game.addHomeGoal(scorer, assist1, assist2, time);
        System.out.println("homegoals.size: " + game.getHomeGoals().size());
    }
    
    public void addVisitorGoalToGame(String nameAndNumberStringFromComboBoxScorer, String nameAndNumberStringFromComboBoxAssist1, String nameAndNumberStringFromComboBoxAssist2, String time, Game game) {
        Player scorer = getOnePlayerFromComboBoxOfTeam(nameAndNumberStringFromComboBoxScorer, game.getVisitorTeam());
        Player assist1 = getOnePlayerFromComboBoxOfTeam(nameAndNumberStringFromComboBoxAssist1, game.getVisitorTeam());
        Player assist2 = getOnePlayerFromComboBoxOfTeam(nameAndNumberStringFromComboBoxAssist2, game.getVisitorTeam());
        game.addVisitorGoal(scorer, assist1, assist2, time);
    }
    
    public ArrayList<Goal> getHomeTeamGoals(Game game){
        for (Game game1 : games) {
//            System.out.println("Games to string: Game1:" + game1.toString() + " game: " + game.toString());
//            System.out.println("Applogic class game1.equals(game): " + game1.equals(game));
            if (game1.equals(game)) {
                System.out.println("game.getHomeGoals.size(): " + game.getHomeGoals().size());
                return game.getHomeGoals();
            }
        }
        System.out.println("returning empty list of goals");
        return new ArrayList<>();
    }
    
    public ArrayList<Goal> getVisitorTeamGoals(Game game) {
        for (Game game1 : games) {
            if (game1.equals(game)) {
                return game.getVisitorGoals();
            }
        }
        return null;
    }
    
}
