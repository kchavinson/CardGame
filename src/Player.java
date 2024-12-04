import java.util.ArrayList;

public class Player {
    private ArrayList<Card> hand;
    private int points;
    private String name;
    private Card lastPlayed;


    public Player(String name)
    {
        this.name = name;
        this.points = 0;
        this.hand = new ArrayList<Card>();
    }
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
    public void setName(String name)
    {
        this.name = name;
    }
    public String getName()
    {
        return this.name;
    }
    public void setPoints(int points)
    {
        this.points = points;
    }
    public int getPoints()
    {
        return points;
    }
    public void addPoints(int addPoints)
    {
        points += addPoints;
    }
    public void addCard(Card card)
    {
        this.hand.add(card);
    }
    public ArrayList<Card> getHand()
    {
        return this.hand;
    }
    public Card getLastPlayed()
    {
        return this.lastPlayed;
    }
    public void setLastPlayed(Card card)
    {
        this.lastPlayed = card;
    }
}
