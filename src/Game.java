import java.util.Scanner;
public class Game {
    private final int[] values = {1,2,3,4,5,6};
    private final String[] ranks = {"9", "10", "J", "Q", "K", "A"};
    private final String[] suits = {"Hearts", "Clubs", "Spades", "Diamonds"};
    private final int NUM_PLAYERS = 4;
    private final int CARDS_PER_HAND = 5;
    private static int numHands = 0;
    private static String dealer;
    private static Player[] players = Player[4];
    private Deck cardDeck;

    public static void main(String[] args)
    {
        // Prints rules
        Game.rules();

        // Creates dice game object
        Game game1 = new Game();

        // Runs through game
        game1.play();

    }

    public static void rules()
    {
        System.out.println(
                "Euchre Overview\n" +
                "Euchre is a trick-taking card game for four players in teams of two, using a 24-card deck " +
                "(9, 10, Jack," + " Queen, King, Ace). The goal is to score 10 points by winning tricks and " +
                "strategically calling trumps.\n" + "\n" +
                "Key Rules\n" +
                "Dealing: Five cards are dealt to each player. The top card of the deck is turned face-up as a " +
                "potential " + "trump.\n" +
                "Naming Trump: Players take turns deciding if the face-up card’s suit will be trump by saying " +
                "“Order it up” or passing. If all pass, a second round allows naming any suit except the one passed, " +
                "with the dealer forced to choose if all pass again (\"Stick the dealer\" rule).\n" +
                "Teams: The team choosing trump is the Makers, and the other team is the Defenders.\n" +
                "Playing\n" +
                "Tricks: Players must follow suit if possible; trump beats all other suits.\n" +
                "Trump Ranking:\n" +
                "Jack of trump (Right Bower)\n" +
                "Jack of same-color suit (Left Bower)\n" +
                "Remaining trump cards: Ace, King, Queen, 10, 9.\n" +
                "Scoring\n" +
                "Makers Win:\n" +
                "3-4 tricks: 1 point.\n" +
                "All 5 tricks: 2 points.\n" +
                "Going alone (winning all 5 tricks): 4 points.\n" +
                "Defenders Win:\n" +
                "3+ tricks: 2 points.\n" +
                "Winning the Game\n" +
                "The first team to reach 10 points wins.! " + "\n"
                + "Now the fun part, you get to choose how many sides are on the die"
                + "\n" + "Get over $200 and you win, or you could lose it all... ");
    }

    // Constructor that makes a dice game object
    public Game()
    {
        Scanner input = new Scanner(System.in);
        Deck cardDeck = new Deck(ranks, suits, values);
        for (int i = 0; i < NUM_PLAYERS; i++)
        {
            System.out.println("Player " + i+1 + ": What is your name?");
            String name = input.nextLine();
            players[i] = new Player(name);
        }

    }


    // Holds the game
    public void play()
    {
        while(players[0].getPoints() < 10 && players[1].getPoints() < 10)
        {
            numHands++;
            this.makeDealer();
            this.dealHands();



        }
    }

    public void dealHands()
    {
        for (int i = 0; i < NUM_PLAYERS; i++)
        {
            for (int j; j < CARDS_PER_HAND; j++)
                players[i].addCard(cardDeck.deal());
        }
    }

    public void makeDealer()
    {
        if(numHands % 4 == 0)
        {
            dealer = players[0].getName();
        }
        else if(numHands % 3 == 0)
        {
            dealer = players[2].getName();
        }
        else if (numHands % 2 == 0)
        {
            dealer = players[1].getName();
        }
        else
        {
            dealer = players[3].getName();
        }
    }

    public void setPlayerNames()
    {

    }
}
