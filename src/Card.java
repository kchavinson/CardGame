public class Card {
    private String rank;
    private int value;
    private String suit;

    //constructor for cards
    public Card(String rank, int value, String suit)
    {
        this.rank = rank;
        this.value = value;
        this.suit = suit;
    }
    //setter for rank
    public void setRank(String rank)
    {
        this.rank = rank;
    }
    //setter for value
    public void setValue(int value)
    {
        this.value = value;
    }
    //setter for suit
    public void setSuit(String suit)
    {
        this.suit = suit;
    }
    //getter for rank
    public String getRank()
    {
        return this.rank;
    }
    //getter for value
    public int getValue()
    {
        return this.value;
    }
    //getter for suit
    public String getSuit()
    {
        return this.suit;
    }
    public String toString()
    {
        return this.rank + " of " + this.suit;
    }

}
