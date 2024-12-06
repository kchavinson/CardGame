import java.util.ArrayList;

public class Player {
    private ArrayList<Card> hand;
    private int points;
    private String name;

    //creates player object with name
    public Player(String name)
    {
        this.name = name;
        this.points = 0;
        this.hand = new ArrayList<Card>();
    }
    //creates player object by taking in a name and hand
    public Player(String name, ArrayList<Card> hand)
    {
        this.name = name;
        this.points = 0;
        this.hand = new ArrayList<Card>();
        for (Card card: hand)
        {
            this.hand.add(card);
        }
    }
    //setter for name
    public void setName(String name)
    {
        this.name = name;
    }
    //getter for name
    public String getName()
    {
        return this.name;
    }
    //setter for points
    public void setPoints(int points)
    {
        this.points = points;
    }
    //getter for points
    public int getPoints()
    {
        return points;
    }
    //method to add points for players
    public void addPoints(int addPoints)
    {
        points += addPoints;
    }
    //method to add card to a hand
    public void addCard(Card card)
    {
        this.hand.add(card);
    }
    //getter for hand
    public ArrayList<Card> getHand()
    {
        return this.hand;
    }
}
