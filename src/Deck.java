import java.util.ArrayList;

public class Deck {

    ArrayList<Card> deck = new ArrayList<Card>();
    private static int cardsLeft;

    public Deck(String[] ranks, String[] suits, int[] values)
    {
        for (int i = 0; i < ranks.length; i++)
        {
            for (String suit : suits) {
                this.deck.add(new Card(ranks[i], values[i], suit));
            }
        }
        cardsLeft = this.deck.size();
    }
    public boolean isEmpty()
    {
        return (cardsLeft == 0);
    }
    public Card deal()
    {
        if (cardsLeft == 0)
        {
            return null;
        }
        cardsLeft--;
        return this.deck.get(cardsLeft);
    }
    public void shuffle()
    {
        int randomIndex;
        for (int i = this.deck.size() - 1; i > 0; i--) {
            randomIndex = (int) (Math.random() * (i + 1));
            Card holder = this.deck.get(i);
            this.deck.set(i, deck.get(randomIndex));
            this.deck.set(randomIndex, holder);
        }
        cardsLeft = this.deck.size();
    }
}
