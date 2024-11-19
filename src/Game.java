public class Game {
    private final int[] values =  {1,2,3,4,5,6,7,8,9,10,10,10,10};
    private final String[] ranks =  {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
    private final String[] suits = {"Hearts", "Clubs", "Spades", "Diamonds"};

    Deck cardDeck = new Deck(ranks, suits, values);


}
