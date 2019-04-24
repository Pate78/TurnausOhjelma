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
            Team team = new Team(name, 0 ,0);
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
        return new Team("Team Not Found",0,0);
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
    public boolean createGame(Team home, Team visitor) {
        if (!home.equals(visitor)) {
            home.setTeamId(home.getName());
            visitor.setTeamId(visitor.getName());
            Game game = new Game(home, visitor);
            
            if (home.getTeamId() == null) {
                System.out.println("AppLogic: createGame: homeTeamId: " + home.getTeamId());
            }
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
//    public Team getVisitorTeamOfGame(Game game) {
//        for (Game game1 : games) {
//            if (game1.getVisitorTeam().getName().equalsIgnoreCase(game.getVisitorTeam().getName())) {
//                return game1.getVisitorTeam();
//            }
//        }
//        System.out.println("App Logic getVisitorTeamOfGame returned null");
//        return null;
//    }
    
    /**
     *
     * @return
     */
    public ArrayList<Game> getGames() {
        ArrayList<Game> games = new ArrayList<>();
        try {
            games = dataManagerGame.getGames();
        } catch (SQLException e) {
            System.out.println("Error getting games:\n" + e.getMessage());
        }
        if (games.size() == 0) {
            System.out.println("AppLogic: getGames(): ArrayList games == 0");
            for (Game game : games) {
                try {
//                    Team homeTeam = dataManagerTeam.getTeam(game.getHomeTeam().getTeamId());
//                    Team visitorTeam = dataManagerTeam.getTeam(game.getVisitorTeam().getTeamId());
                    int homeTeamGoalsDone = dataManagerTeam.getTeamGoalsDone(game.getHomeTeam());
                    int visitorTeamGoalsDone = dataManagerTeam.getTeamGoalsDone(game.getVisitorTeam());
                    int homeGoalsDoneAgainst = dataManagerTeam.getTeamGoalsDoneAgainst(game.getHomeTeam());
                    int visitorGoalsDoneAgainst = dataManagerTeam.getTeamGoalsDoneAgainst(game.getVisitorTeam());
                    game.getHomeTeam().setGoalsDone(homeTeamGoalsDone);
                    game.getVisitorTeam().setGoalsDone(visitorTeamGoalsDone);
                    game.getHomeTeam().setGoalsDoneAgainst(homeGoalsDoneAgainst);
                    game.getVisitorTeam().setGoalsDoneAgainst(visitorGoalsDoneAgainst);
                    ArrayList<Goal> goals = dataManagerGame.getGameGoals(game.getGameId());
                    for (Goal goal : goals) {
                        if (goal.getTeamId().equalsIgnoreCase(game.getHomeTeam().getTeamId())) {
                            game.addHomeGoal(goal);
                        } else {
                            game.addVisitorGoal(goal);
                        }
                    }
                } catch (SQLException e) {
                    System.out.println("Error setting home or visitor team in Applogic: getGames()");
                }

            }
        } else {
            System.out.println("AppLogic: getGames(): games ArrayList greater than 0");
        }
        
        
        return games;
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
     * Method is used to search a player from combobox string selection.
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
        int goalsDone = 0;
        try {
            goalsDone = dataManagerGame.getGameGoals(game.getGameId()).size();
        } catch (Exception e) {
        }
//        System.out.println("Applogic: addVisitorGoal: game: goalsDone " +
//                goalsDone);
        Goal goal = new Goal((goalsDone+1),game.getGameId(),
                game.getHomeTeam().getTeamId(),
                scorer, assist1, assist2, time);
        game.getHomeTeam().addOnetoGoalsDone();
        game.getVisitorTeam().addOneToGoalsDoneAgainst();
//        System.out.println("AppLogic: addGoal: getHomeTeam: teamName " + game.getHomeTeam().getName() +", goalsDone: " + game.getHomeTeam().getGoalsDone());
//        System.out.println("AppLogic: addGoal: getHomeTeam: goalsDoneAgainst: " + game.getHomeTeam().getGoalsDoneAgainst());

        try {
            game.addHomeGoal(players, (goalsDone+1), game.getHomeTeam().getTeamId(), time);
            dataManagerGame.addOrUpdateGoalToGame(goal, game);

            dataManagerTeam.addGoalToTeam(game.getHomeTeam());
            dataManagerTeam.insertOrUpdateTeam(game.getVisitorTeam());
        } catch (Exception e) {
            System.out.println("Error adding goal to game:\n" + e.getMessage());
        }
//        Player scorer = getOnePlayerFromComboBoxOfTeam(nameAndNumberStringFromComboBoxScorer, game.getHomeTeam());
//        Player assist1 = getOnePlayerFromComboBoxOfTeam(nameAndNumberStringFromComboBoxAssist1, game.getHomeTeam());
//        Player assist2 = getOnePlayerFromComboBoxOfTeam(nameAndNumberStringFromComboBoxAssist2, game.getHomeTeam());
//
//        game.addHomeGoal(scorer, assist1, assist2, time);
//        System.out.println("homegoals.size: " + game.getHomeGoals().size());
        System.out.println("AppLogic: addHomeGoal");
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
        System.out.println("AppLogic: addVisitorGoal");
//        for (Player player : players) {
//            System.out.println("Adding players to Goal: " + player.getFullName());
//        }
        int goalsDone = 0;
        try {
            goalsDone = dataManagerGame.getGameGoals(game.getGameId()).size();
        } catch (SQLException e) {
            System.out.println("Error getting game goals:\n" + e.getMessage());
        }

        try {
            Goal goal = new Goal((goalsDone+1), game.getGameId(),
                    game.getVisitorTeam().getTeamId(),
                    scorer, assist1, assist2, time);
//            System.out.println("AppLogic: addVisitorGoalToGame: goal teamId" + goal.getTeamId());
            game.addVisitorGoal(players, (goalsDone+1), game.getVisitorTeam().getTeamId(), time);
            dataManagerGame.addOrUpdateGoalToGame(goal, game);

//            System.out.println("AppLogic: addVisitorGoal: game.getVisitorTeam().getGoalsDone(): visitorTeamName: " +
//                    game.getVisitorTeam().getName() + " goalsDone:" +game.getVisitorTeam().getGoalsDone());
            game.getVisitorTeam().addOnetoGoalsDone();
            game.getHomeTeam().addOneToGoalsDoneAgainst();

            dataManagerTeam.insertOrUpdateTeam(game.getHomeTeam());
            dataManagerTeam.insertOrUpdateTeam(game.getVisitorTeam());
        } catch (Exception e) {
            System.out.println("Error adding goal to game:\n" + e.getMessage());
        }
    }
        
    public boolean removeGoalFromGame(String goalId) {
        try {
            Goal goalToRemove = dataManagerGame.getGoal(goalId);
            dataManagerGame.removeGoalFromGame(goalToRemove);
            return true;
        } catch (SQLException e) {
            System.out.println("Error removing a goal from game:\n" + e.getMessage());
            return false;
        }
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
    
//    public boolean removeGoalFromGame(String goalId) {
//        
//    }
    
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
//        Collections.sort(teams);
        for (Team team : teams) {
//            System.out.println("AppLogic: getTeamsSorted(): teamName: "+team.getName() +
//                    ", team.getGoalsdone: " + team.getGoalsDone() + 
//                    ", team.getGoalsDoneAgainst: " + team.getGoalsDoneAgainst());
        }
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
