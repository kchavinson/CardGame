import java.util.ArrayList;
import java.util.Scanner;
public class Game {
    private final int[] values = {1,2,3,4,5,6};
    private final String[] ranks = {"9", "10", "J", "Q", "K", "A"};
    private final String[] suits = {"Hearts", "Clubs", "Spades", "Diamonds"};
    private final int NUM_PLAYERS = 4;
    private final int CARDS_PER_HAND = 5;
    private static int numRounds = 0;
    private static Player dealer;
    private Deck cardDeck;
    private Card topCard;
    private ArrayList<Card> pot;
    private Player winnerOfTrick;
    private int winningValue;
    private Card winningCard;
    private String trump;
    private int trumpValue;
    private int indexOfTurn;
    private int numTricks;
    private int indexOfWinner;
    private static Player[] players = new Player[4];
    public Team teams;
    Scanner input = new Scanner(System.in);

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
                "(9, 10, Jack," + " Queen, King, Ace). \n The goal is to score 10 points by winning tricks and " +
                "strategically calling trumps.\n" + "\n" +
                "Key Rules\n" +
                "Dealing: Five cards are dealt to each player. The top card of the deck is turned face-up as a " +
                "potential " + "trump.\n" +
                "Naming Trump: Players take turns deciding if the face-up card’s suit will be trump by saying " +
                "“Order it up” or passing. \n If all pass, a second round allows naming any suit except the one passed, " +
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
                "The first team to reach 10 points wins! " + "\n");
    }

    // Constructor that makes a dice game object
    public Game()
    {
        numRounds = 0;
        winnerOfTrick = players[0];
        pot = new ArrayList<Card>();
        cardDeck = new Deck(ranks, suits, values);

        for (int i = 0; i < NUM_PLAYERS; i++)
        {
            System.out.println("Player " + (i+1) + ": What is your name?");
            String name = input.nextLine();
            players[i] = new Player(name);
        }

    }


    // Holds the game
    public void play() {
        //create teams
        teams = new Team(players);

        //while the game isn't over keep playing
        while (!this.hasWon())
        {
            numTricks = 0;
            //runs 5 hands
            for (int i = 0; i < CARDS_PER_HAND; i++) {


                //makes dealer
                this.makeDealer();

                //shuffle card deck
                cardDeck.shuffle();

                //deals each player their hand
                this.dealHands();

                //reveals top card
                topCard = cardDeck.deal();
                System.out.println("\n\nTop Card: " + topCard + "\n");

                //take top card?
                this.setTopCardCard();

                //prints trump suit
                System.out.println("\n" + trump + " is now the Trump Suit" + "\n");

                //Run 4 turns
                for (int j = 0; j < CARDS_PER_HAND; j++) {
                    this.turn();

                    //shows winning of trick and point count
                    System.out.println(winnerOfTrick.getName() + " has won this trick with a " + winningCard);
                    winnerOfTrick.addPoints(1);
                    System.out.println(winnerOfTrick.getName() + " now has " + winnerOfTrick.getPoints() + " trick!");

                    //makes winner of last trick first for next turn
                    indexOfWinner = findIndex();

                    //resets the values of winning cards
                    winnerOfTrick = null;
                    winningCard = null;
                    winningValue = 0;

                    //resets the pot
                    while (!pot.isEmpty()) {
                        pot.remove(0);
                    }
                }
                numTricks++;
            }

            //checks which team won the round
            this.roundWinner();
            //updates number of rounds
            numRounds++;
        }
    }
    public int findIndex() {
        for (int i = 0; i < players.length; i++)
        {
            // Return the index if found
            if (players[i].equals(winnerOfTrick))
            {
                System.out.println(i);
                return i;
            }
        }
        return 0;
    }
    public void roundWinner()
    {
        //if team 1 scored more points this round, add 1 to their team total
        if ((teams.getTeam1()[0].getPoints() + teams.getTeam1()[1].getPoints()) >
                (+ teams.getTeam1()[0].getPoints() + teams.getTeam1()[1].getPoints()))
        {
            System.out.print("Team 1 has won the most tricks this round!");
            Team.addPointsToTeam1(1);
            teams.getTeam1()[0].setPoints(0);
            teams.getTeam1()[1].setPoints(0);
        }

        //if team two has won, add points to their total
        else
        {
            System.out.print("Team 2 has won the most tricks this round!");
            Team.addPointsToTeam1(1);
            teams.getTeam2()[0].setPoints(0);
            teams.getTeam2()[1].setPoints(0);
        }
    }
    private boolean hasWon()
    {
        if (Team.getTeam1Points() >= 1)
        {
            System.out.println("The Game is Over! Team 1 Wins!");
            return true;
        }
        else if (Team.getTeam2Points() >= 1)
        {
            System.out.println("The Game is Over! Team 2 Wins!");
            return true;
        }
        System.out.println("Score\n" + "Team 1: " + Team.getTeam1Points() +  "\nTeam 2: " + Team.getTeam2Points());
        return false;
    }
    public void dealHands()
    {
        for (int i = 0; i < NUM_PLAYERS; i++)
        {
            for (int j = 0; j < CARDS_PER_HAND; j++)
                players[i].addCard(cardDeck.deal());
        }
    }

    public void makeDealer()
    {
        if(numRounds % 4 == 0)
        {
            dealer = players[0];
        }
        else if(numRounds % 4 == 1)
        {
            dealer = players[2];
        }
        else if (numRounds % 4 == 2)
        {
            dealer = players[1];
        }
        else
        {
            dealer = players[3];
        }
    }
    public void findWinner(int i, int index, Card card)
    {

        if (card.getSuit().equals(trump))
        {
            trumpValue = card.getValue() * 10;
        }
        if (pot.isEmpty() || trumpValue > winningValue || (card.getSuit().equals(pot.get(0).getSuit())
                && card.getValue() > winningValue))
        {
            if (trumpValue > winningValue)
            {
                winningValue = trumpValue;
            }
            else
            {
                winningValue = card.getValue();
            }
            winnerOfTrick = players[i];
            winningCard = card;
            trumpValue = 0;
        }
    }
    public void turn()
    {
        for (int i = 0; i < NUM_PLAYERS; i++)
        {
            //problem with starting turn
            boolean validPlay = false;
            if (numTricks == 0)
            {
                indexOfTurn = ((i + numRounds + 1) % 4);
            }
            else
            {
                indexOfTurn = ((i + indexOfWinner) % 4);
            }


            while (!validPlay) {

                //prompts user for an index and gives them their hand
                System.out.println(players[indexOfTurn].getName() + "'s Hand: " + players[indexOfTurn].getHand());
                System.out.println("Please give the index of the Card you want to Play 0 - "
                        + (players[indexOfTurn].getHand().size() - 1));
                int index = input.nextInt();
                input.nextLine();
                System.out.println("\n\n\n\n\n\n\n");

                //ensures the index is valid:
                //If valid it will add the card to the pot, update the winner of the hand if needed
                if (this.isValidPlay(index, indexOfTurn))
                {
                    this.findWinner(indexOfTurn, index, players[indexOfTurn].getHand().get(index));
                    pot.add(players[indexOfTurn].getHand().get(index));
                    players[indexOfTurn].getHand().remove(index);
                    validPlay = true;

                } else {
                    System.out.println("Invalid Index Please Try Again");
                }
                System.out.println("Current pot: " + pot);
            }
        }
    }
    //checks to see if player has the suit left in their hand
    public boolean hasNoSuit(int i)
    {
        for (Card card: players[i].getHand())
        {
            if (card.getSuit().equals(pot.get(0).getSuit()))
            {
                return false;
            }
        }
        return true;
    }
    //checks to see if the user turn request is valid
    public boolean isValidIndex(int index, int i)
    {
        return players[i].getHand().size() > index && index >= 0;
    }
    public boolean isValidPlay(int index, int i)
    {
        return (pot.isEmpty() || players[i].getHand().get(index).getSuit().equals(pot.get(0).getSuit())
                || hasNoSuit(i)) && isValidIndex(index, i);
    }
    //checks to see if the trump was created correctly
    public boolean isValidTrump()
    {
        for (String suit: suits)
        {
            if(suit.equals(trump))
            {
                return true;
            }
        }
        return false;
    }

    public void setTopCardCard()
    {
        System.out.println(dealer.getName() + "'s Hand: " + dealer.getHand() + "\n");
        System.out.println("Dealer, would you like to pick up the top card? \n"  +
                "Type 'y' for Yes and 'n' for No");
        String answer = input.nextLine();
        if(answer.equals("y"))
        {
            System.out.println("Please give the index of the card you would like to remove: ");
            int index = input.nextInt();

            if (isValidIndex(index, numRounds % 4))
            {
                input.nextLine();
                dealer.getHand().remove(index);
                dealer.addCard(topCard);
                trump = topCard.getSuit();
            }
            else
            {
                System.out.println("Invalid Index Please try again");
                this.setTopCardCard();
            }
        }
        else if(answer.equals("n"))
        {
            System.out.println("What Suit would you like to be Trump instead");
            trump = input.nextLine();
            if(!isValidTrump())
            {
                System.out.println("Invalid Trump Please try again");
                this.setTopCardCard();
            }
        }
        else
        {
            System.out.println("Please Give a Valid Answer");
            this.setTopCardCard();
        }
    }
}
