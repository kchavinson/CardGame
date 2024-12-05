import java.util.ArrayList;

public class Team {
    private Player[] team1 = new Player[2];
    private Player[] team2 = new Player[2];
    public static int team1Points = 0;
    public static int team2Points = 0;

    public Team(Player[] players)
    {
        // Add the 1st and 3rd players to team1
        team1[0] = players[0];
        team1[1] = players[2];

        // Add the 2nd and 4th players to team2
        team2[0] = players[1];
        team2[1] = players[3];
    }
    public Player[] getTeam1()
    {
        return this.team1;
    }
    public Player[] getTeam2()
    {
        return this.team2;
    }

    // Getter for team1Points
    public static int getTeam1Points()
    {
        return team1Points;
    }

    // Setter for team1Points
    public static void setTeam1Points(int points)
    {
        team1Points = points;
    }

    // Getter for team2Points
    public static int getTeam2Points()
    {
        return team2Points;
    }

    // Setter for team2Points
    public static void setTeam2Points(int points)
    {
        team2Points = points;
    }

    // Method to add points to team1
    public static void addPointsToTeam1(int points)
    {
        team1Points += points;
    }

    // Method to add points to team2
    public static void addPointsToTeam2(int points)
    {
        team2Points += points;
    }

}
