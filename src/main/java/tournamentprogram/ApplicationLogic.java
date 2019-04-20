/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tournamentprogram;

import data.DbManagerPlayer;
import java.util.ArrayList;
import java.util.HashMap;
import data.*;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
/**
 *
 * @author pate
 */
public class ApplicationLogic {
//    private ArrayList<Team> teams;
    private ArrayList<Game> games;
    private DbManager dataManager;
    private DbManagerPlayer dataManagerPlayer;
    private DbManagerTeam dataManagerTeam;
    private DbManagerGame dataManagerGame;

    /**
     *
     */
    public ApplicationLogic() {
        this.games = new ArrayList<>();
//        this.teams = new ArrayList<>();
        dataManager = new DbManager();
        dataManager.createDBTables();
        dataManagerPlayer = new DbManagerPlayer(dataManager);
        dataManagerTeam = new DbManagerTeam(dataManager);
        dataManagerGame = new DbManagerGame(dataManager);

    }
    
    public void createNewTournament() {
        dataManager.startNewTournament();
    }
    
    /**
     *
     * @param name
     * @return
     */
    public boolean addTeam(String name) {
        if (!name.equals("")) {
            Team team = new Team(name);
//            this.teams.add(team);
            try {
                dataManagerTeam.insertOrUpdateTeam(team);
            } catch (SQLException se) {
                System.out.println("Error in adding a team:\n" + se.getMessage());
                return false;
            }
            return true;
        }
        return false;
    }
    
    /**
     *
     * @return
     */
    public ArrayList<Team> getTeams(){
        ArrayList<Team> teams = new ArrayList<>();
        try {
            teams = dataManagerTeam.getTeams();
        } catch (SQLException se) {
            System.out.println("Error in fetching teams");
        }
        return new ArrayList<>(teams);
    }
    
    /**
     *
     * @param teamName
     * @return
     */
    public Team getTeam(String teamName) {
        try {
            for (Team team : dataManagerTeam.getTeams()) {
                if (team.getName().equalsIgnoreCase(teamName)) {
    //                System.out.println("Returning team: " + team.getName());
                    return team;
                }
            }
        } catch (SQLException se) {
            System.out.println("Error getting a team:\n"+se.getMessage());
        }

        System.out.println("App logic getTeam() returned empty team");
        return new Team("Team Not Found");
    }
    
    /**
     *
     * @param playerToAdd
     * @param place
     * @param team
     * @return
     */
    public Player addPlayerToTeam(Player playerToAdd, String place, Team team) {
        for (Player player : team.getAllPlayersInArrayList()) {
            if (player.getNumber()==playerToAdd.getNumber()) {
                return null;
            }
        }
        try {
            dataManagerPlayer.insertOrUpdatePlayer(playerToAdd);
        } catch (SQLException se) {
            System.out.println("Error adding a player: \n" + se.getMessage());
        }
        getTeam(team.getName()).addPlayer(playerToAdd, place);
        return playerToAdd;
//        System.out.println("getTeam(team.getName())" + getTeam(team.getName()).toString());
    }
    
    /**
     *
     * @param playerDetails
     * @param position
     * @param team
     * @return
     */
    public Player addPlayerToTeam(String playerDetails, String position, Team team) {
        String[] pieces = playerDetails.split(",");
//        Player plr = new Player(place, place, 0, team.getId());
        int number = 0;
        try {
            number = Integer.parseInt(pieces[2]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (Player player : team.getAllPlayersInArrayList()) {
            if (player.getNumber()==number) {
                return null;
            }
        }
        Player plr = new Player(pieces[0],pieces[1],number,team.getTeamId(), position);
        
        try {
            dataManagerPlayer.insertOrUpdatePlayer(plr);
        } catch (SQLException se) {
            System.out.println("Error inserting a player:\n" + se.getMessage());
        }
        
        getTeam(team.getName()).addPlayer(plr, position);
        
//        System.out.println("getTeam(team.getName())" + getTeam(team.getName()).toString());
        return plr;
    }
    
    /**
     *
     * @param playerId
     * @return
     */
    public boolean removePlayerFromTeam (String playerId) {
        try {
            dataManagerPlayer.deleteFromPlayers(playerId);
            return true;
        } catch (SQLException e) {
            System.out.println("Error removing a player from player table:\n" + e.getMessage());
        }
        return false;
    }
    
    /**
     *
     * @param name
     * @return
     */
    public HashMap<String, ArrayList<Player>> getPlayersOfTeam(String name) {
        for (Team team : getTeams()) {
            if (team.getName().equalsIgnoreCase(name)) {
                try {
//                    System.out.println("GEtting players in application logic!! ");
                    return dataManagerTeam.getAllPlayersOfTeamWithPosition(team.getTeamId());
                } catch (SQLException se) {
                    System.out.println("Error fetching teams all players:\n" + se.getMessage());
                }
            }
        }
        return null;
    }
    
    /**
     *
     * @param teamName
     * @param fname
     * @param lname
     * @return
     */
    public Player getPlayer(String teamName, String fname, String lname){
        return getTeam(teamName).getPlayer(fname, lname);
    }
    
    /**
     * Metod creates a new game. Id field of the game will be constructed of hour
     * day, month, year, home team hashcode and visitor team hashcode
     * when the game is save
     * @param home home team of the game
     * @param visitor
     * @return 
     */
    public boolean createGame(Team home, Team visitor, int goalOrderNumber) {
        if (!home.equals(visitor)) {
            Game game = new Game(home, visitor, goalOrderNumber);
//            this.games.add(game);
            try {
                dataManagerGame.insertOrUpdateGame(game);
            } catch (SQLException e) {
                System.out.println("Error creating a game:\n"+e.getMessage());
            }
//            System.out.println("home.equals(visitor): " + home.equals(visitor));
            
            return true;
        }
        return false;
    }
    
    /**
     *
     * @param game
     * @return
     */
    public Team getHomeTeamOfGame(Game game) {
        
        
        for (Game game1 : games) {
            if (game1.getHomeTeam().getName().equalsIgnoreCase(game.getHomeTeam().getName())) {
                return game1.getHomeTeam();
            }
        }
        System.out.println("App Logic getHomeTeamOfGame returned null");
        return null;
    }
    
    /**
     *
     * @param game
     * @return
     */
    public Team getVisitorTeamOfGame(Game game) {
        for (Game game1 : games) {
            if (game1.getVisitorTeam().getName().equalsIgnoreCase(game.getVisitorTeam().getName())) {
                return game1.getVisitorTeam();
            }
        }
        System.out.println("App Logic getVisitorTeamOfGame returned null");
        return null;
    }
    
    /**
     *
     * @param home
     * @param visitor
     * @return
     */
    public Game getGame(String home, String visitor) {
        for (Game game : games) {
            System.out.println("Applogic:getGame: game.getGameId(): " + game.getGameId());
            if (game.getHomeTeam().getName().equalsIgnoreCase(home) && game.getVisitorTeam().getName().equalsIgnoreCase(visitor)) {
                return game;
            }
        }
        System.out.println("App logic getGame returned null");
        return null;
    }
    
    /**
     *
     * @return
     */
    public ArrayList<Game> getGames() {
        try {
            return dataManagerGame.getGames();
        } catch (SQLException e) {
            System.out.println("Error getting games:\n" + e.getMessage());
        }
        return this.games;
    }
    
    /**
     *
     * @param t
     * @param index
     */
//    public void removeTeamFromGame(Team t, int index) {
//        this.games.get(index).removeTeamFromGame(t.getName());
//    }
    
    public void removeTeam(String teamId) {
        try {
            dataManagerTeam.removeTeam(getTeam(teamId));
        } catch (SQLException e) {
            System.out.println("Removing a team failed:\n" + e.getMessage());
        }
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
//                System.out.println("pieces[1]: " + pieces[1]);
                String[] name = pieces[0].split(" ");
                ArrayList<Player> teamPlayers = new ArrayList<>();
                try {
                    teamPlayers = dataManagerTeam.getAllPlayersOfTeam(t.getTeamId());
                } catch (SQLException e) {
                    System.out.println("Error getting team players:\n" + e.getMessage());
                }
                for (Player player : teamPlayers) {
                    if (player.getFirstName().equalsIgnoreCase(name[0]) && player.getLastName().equalsIgnoreCase(name[1])) {
                        player.addGoal();
                        return player;
                    }
                }
            }
        }
        return new Player();
    }
    
    
    
    /**
     * Index of team in ArrayList<Team>. This will be used when team is removed.
     * @param team the team whose index is needed.
     * @return 
     */
//    private int getIndexOfTeam(Team team) {
//        for (int i = 0; i < teams.size(); i++) {
//            if (teams.get(i).getName().equalsIgnoreCase(team.getName())) {
//                return i;
//            }
//        }
//        return -1;
//    }
    
    /**
     *
     * @param nameAndNumberStringFromComboBoxScorer
     * @param nameAndNumberStringFromComboBoxAssist1
     * @param nameAndNumberStringFromComboBoxAssist2
     * @param time
     * @param game
     */
    public void addHomeGoalToGame(String nameAndNumberStringFromComboBoxScorer, String nameAndNumberStringFromComboBoxAssist1, String nameAndNumberStringFromComboBoxAssist2,  String time, Game game) {
        Player scorer = getOnePlayerFromComboBoxOfTeam(nameAndNumberStringFromComboBoxScorer, game.getHomeTeam());
        Player assist1 = getOnePlayerFromComboBoxOfTeam(nameAndNumberStringFromComboBoxAssist1, game.getHomeTeam());
        Player assist2 = getOnePlayerFromComboBoxOfTeam(nameAndNumberStringFromComboBoxAssist2, game.getHomeTeam());
        ArrayList<Player> players = new ArrayList<>();
        players.add(scorer);
        if (!assist1.getFullName().equalsIgnoreCase("Player Not Found")){
            players.add(assist1);
        }
        if (!assist2.getFullName().equalsIgnoreCase("Player Not Found")) {
            players.add(assist2);
        }
//        for (Player player : players) {
//            System.out.println("Adding players to Goal: " + player.getFullName());
//        }
        System.out.println("Applogic: addHomeGoal: game.getNextGoalOrderNumber: " +game.getNextGoalOrderNumber());
        try {
            Goal goal = new Goal(game.getGameId(), game.getNextGoalOrderNumber()+1,
                    game.getHomeTeam().getTeamId(),
                    scorer, assist1, assist2, time);
            dataManagerGame.addOrUpdateGoalToGame(goal, game);
        } catch (Exception e) {
            System.out.println("Error adding goal to game:\n" + e.getMessage());
        }
        game.addHomeGoal(players, game.getNextGoalOrderNumber(), game.getHomeTeam().getTeamId(), time);
//        Player scorer = getOnePlayerFromComboBoxOfTeam(nameAndNumberStringFromComboBoxScorer, game.getHomeTeam());
//        Player assist1 = getOnePlayerFromComboBoxOfTeam(nameAndNumberStringFromComboBoxAssist1, game.getHomeTeam());
//        Player assist2 = getOnePlayerFromComboBoxOfTeam(nameAndNumberStringFromComboBoxAssist2, game.getHomeTeam());
//
//        game.addHomeGoal(scorer, assist1, assist2, time);
//        System.out.println("homegoals.size: " + game.getHomeGoals().size());
        game.getHomeTeam().setGoalsDone(game.getHomeTeam().getGoalsDone()+1);
        game.getVisitorTeam().setGoalsDoneAgainst(game.getVisitorTeam().getGoalsDoneAgainst()+1);
    }
    
    /**
     *
     * @param nameAndNumberStringFromComboBoxScorer
     * @param nameAndNumberStringFromComboBoxAssist1
     * @param nameAndNumberStringFromComboBoxAssist2
     * @param time
     * @param game
     */
    public void addVisitorGoalToGame(String nameAndNumberStringFromComboBoxScorer, String nameAndNumberStringFromComboBoxAssist1, String nameAndNumberStringFromComboBoxAssist2, String time, Game game) {
        Player scorer = getOnePlayerFromComboBoxOfTeam(nameAndNumberStringFromComboBoxScorer, game.getVisitorTeam());
        Player assist1 = getOnePlayerFromComboBoxOfTeam(nameAndNumberStringFromComboBoxAssist1, game.getVisitorTeam());
        Player assist2 = getOnePlayerFromComboBoxOfTeam(nameAndNumberStringFromComboBoxAssist2, game.getVisitorTeam());
        ArrayList<Player> players = new ArrayList<>();
        players.add(scorer);
//        System.out.println("Applogic:addVisitorGoalToGame: scorerId:" + scorer.getId());
        if (!assist1.getFullName().equalsIgnoreCase("Player Not Found")){
            players.add(assist1);
        }
        if (!assist2.getFullName().equalsIgnoreCase("Player Not Found")) {
            players.add(assist2);
        }
//        for (Player player : players) {
//            System.out.println("Adding players to Goal: " + player.getFullName());
//        }
        System.out.println("Applogic: addVisitorGoal: game.getNextGoalOrderNumber: " +game.getNextGoalOrderNumber());

        try {
            Goal goal = new Goal(game.getGameId(), game.getNextGoalOrderNumber()+1,
                    game.getVisitorTeam().getTeamId(),
                    scorer, assist1, assist2, time);
//            System.out.println("AppLogic: addVisitorGoalToGame: goal teamId" + goal.getTeamId());
            game.addVisitorGoal(players, game.getNextGoalOrderNumber(), game.getVisitorTeam().getTeamId(), time);
            dataManagerGame.addOrUpdateGoalToGame(goal, game);
        } catch (Exception e) {
            System.out.println("Error adding goal to game:\n" + e.getMessage());
        }
        
        game.getVisitorTeam().setGoalsDone(game.getVisitorTeam().getGoalsDone()+1);
        game.getHomeTeam().setGoalsDoneAgainst(game.getHomeTeam().getGoalsDoneAgainst()+1);
    }
    
    /**
     *
     * @param game
     * @return
     */
    public ArrayList<Goal> getGoals(Game game, String teamId){
        ArrayList<Goal> teamGoals = new ArrayList<>();
        try {
            ArrayList<Goal> allGoals = new ArrayList<>();
            allGoals = dataManagerGame.getGameGoals(game.getGameId());
//            System.out.println("Applogic: getHomeGoals:");
            for (Goal goal : allGoals) {
//                System.out.println("AppLogic getGoals: goalOrderNumber: " + goal.getGoalOrderNumber());
                Player scorer = dataManagerPlayer.getPlayer(goal.getScorerId());
                if (teamId.equals(goal.getTeamId())) {
                    teamGoals.add(goal);
                }
            }
            return teamGoals;
        } catch (SQLException e) {
            System.out.println("Error getting home goals:\n" + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("returning empty list of home goals");
        return new ArrayList<>();
    }
    
    /**
     *
     * @param game
     * @return
     */
//    public ArrayList<Goal> getVisitorTeamGoals(Game game) {
//        for (Game game1 : games) {
//            if (game1.equals(game)) {
//                return game.getVisitorGoals();
//            }
//        }
//        System.out.println("returning empty list of visitor goals");
//        return new ArrayList<>();
//    }
    
    public Player getScorerOfGoal(Goal goal) {
        Player scorer = new Player();
        try {
            Player plr = dataManagerPlayer.getPlayer(goal.getScorerId());
            return plr;
        } catch (Exception e) {
            System.out.println("Error returning scorer:\n" + e.getMessage());
            return null;
        }
    }
    
    /**
     *
     * @param goal
     * @return
     */
    public List<Player> getAssistsOfGoal(Goal goal) {
        ArrayList<Player> assists = new ArrayList<>();
//        System.out.println("Getting assista of a goal in App logic");
 
        assists.add(goal.getAssist1());
        assists.add(goal.getAssist2());
        
        return assists;
    }
    
    /**
     * Statistics tab methods
     * @return 
     */
    
    public ArrayList<Team> getTeamsSorted() {
        ArrayList<Team> teams = new ArrayList<>();
        try {
            teams = dataManagerTeam.getTeams();
        } catch (SQLException e) {
            System.out.println("Error fetching teams from DB!!!\n" + e.getMessage());
        }
        Collections.sort(teams);
        return teams;
    }
    
    /**
     *
     * @param t
     * @return
     */
    public ArrayList<Player> getPlayersSorted(Team t){
        ArrayList<Player> players = new ArrayList<>();
        try {
            players = dataManagerTeam.getAllPlayersOfTeam(t.getTeamId());
            Collections.sort(players);
//            System.out.println("Getting players:\n");
            for (Player player : players) {
//                System.out.println("player name: " + player.getFullName() + "\n");
            }
        } catch (SQLException se) {
            System.out.println("Error getting all players:\n" + se);
        }
        return players;
    }
            
    
}
