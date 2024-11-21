public class Card {
    private String rank;
    private int value;
    private String suit;
    private boolean isTrump;

    public Card(String rank, int value, String suit)
    {
        this.rank = rank;
        this.value = value;
        this.suit = suit;
        this.isTrump = false;
    }
    public void setRank(String rank)
    {
        this.rank = rank;
    }
    public void setValue(int value)
    {
        this.value = value;
    }
    public void setSuit(String suit)
    {
        this.suit = suit;
    }
    public String getRank()
    {
        return this.rank;
    }
    public int getValue()
    {
        return this.value;
    }
    public String getSuit()
    {
        return this.suit;
    }
    public boolean getIsTrump()
    {
        return this.isTrump;
    }
    public void getSuit(boolean isTrump)
    {
       this.isTrump = isTrump;
    }
    public String toString()
    {
        return this.rank + " of " + this.suit;
    }

}
