import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    //declaring constants, instance variables, and scanners
    private final int[] values = {1, 2, 3, 4, 5, 6};
    private final String[] ranks = {"9", "10", "J", "Q", "K", "A"};
    private final String[] suits = {"Hearts", "Clubs", "Spades", "Diamonds"};
    private final int NUM_PLAYERS = 4;
    private final int CARDS_PER_HAND = 5;

    private static int numRounds = 0;
    private static Player dealer = null;
    private static Player[] players = new Player[4];
    private Team teams;
    private ArrayList<Card> pot;
    private Deck cardDeck;
    private Player winnerOfTrick;
    private int winningValue;
    private Card winningCard;
    private String trump;
    private int indexOfWinner;

    Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        // Prints rules
        Game.rules();

        // Creates dice game object
        Game game1 = new Game();

        // Runs through game
        game1.play();

    }

    //prints out rules
    private static void rules() {
        System.out.println(
                "\nEuchre Overview\n\n" +
                        "Euchre is a trick-taking card game for four players in teams of two, using a 24-card deck " +
                        "(9, 10, Jack," + " Queen, King, Ace). \nThe goal is to score 3 points by winning tricks and " +
                        "strategically calling trumps.\n" + "\n" +
                        "Key Rules\n\n" +
                        "Dealing: Five cards are dealt to each player. The top card of the deck is turned face-up as a "
                        + "potential trump.\n" +
                        "The Dealer names trump by deciding if the face-up card’s suit will be trump by " +
                        "picking up that card or passing it and then choosing a trump. \n\n" +
                        "Teams: Teams alternate selecting trump, rotating through all players. " +
                        "Players 1 and 3 and Players 2 and 4 are on the same team.\n\n" +
                        "Playing\n\n" +
                        "Tricks:\nPlayers must follow suit if possible; trump beats all other suits.\n\n" +
                        "Ranking:\n" +
                        "Remaining trump cards: Ace, King, Queen, Jack, 10, 9.\n\n" +
                        "Scoring:\n" +
                        "If you win a the most tricks in a round your team gets one point\n\n" + "Winning the Game\n" +
                        "Again, The first team to reach 3 points wins! " + "\n\n");
    }

    // Constructor that makes a game object
    public Game() {
        winnerOfTrick = players[0];
        pot = new ArrayList<Card>();
        cardDeck = new Deck(ranks, suits, values);
        trump = null;
        winningValue = 0;
        winningCard = null;
        winnerOfTrick = null;
        indexOfWinner = 0;

        //prompts all players for their name and creates players
        for (int i = 0; i < NUM_PLAYERS; i++) {
            System.out.println("Player " + (i + 1) + ": What is your name?");
            String name = input.nextLine();
            players[i] = new Player(name);
        }
        //create teams
        teams = new Team(players);
    }


    // Holds the game
    private void play() {


        //while the game isn't over keep playing
        while (!this.hasWon()) {
            //makes dealer
            this.makeDealer();

            //shuffles card deck
            cardDeck.shuffle();

            //deals each player their hand
            this.dealHands();

            //gives dealer opportunity to take top card
            this.takeTopCard();

            //prints trump suit
            System.out.println("\n" + trump + " is now the Trump Suit" + "\n");
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");

            //Run 5 turns
            for (int j = 0; j < CARDS_PER_HAND; j++) {

                //runs turn then shows who won
                this.turn(j);
                this.whoWon();
            }
            //checks which team won the round
            this.roundWinner();
            //updates number of rounds
            numRounds++;
        }
    }
    private void whoWon()
    {
        //shows winning of trick and point count
        System.out.println("\n" + winnerOfTrick.getName() + " has won this trick with a " + winningCard + "\n");
        winnerOfTrick.addPoints(1);
        System.out.println("# of tricks won by " + winnerOfTrick.getName() + ": " + winnerOfTrick.getPoints() +  "\n");

        indexOfWinner = findIndexOfWinner();
        //resets cards
        this.reset();
    }

    //finds the index of the winner of the most recent trick
    private int findIndexOfWinner() {
        for (int i = 0; i < players.length; i++) {
            // Return the index if found
            if (players[i].equals(winnerOfTrick)) {
                return i;
            }
        }
        return 0;
    }

    //after each round, update the points for each team
    private void roundWinner() {
        //point totals for each team
        int team1Points = teams.getTeam1()[0].getPoints() + teams.getTeam1()[1].getPoints();
        int team2Points = teams.getTeam2()[0].getPoints() + teams.getTeam2()[1].getPoints();

        //if team 1 scored more points this round, add 1 to their team total
        if (team1Points > team2Points) {
            System.out.println("Team 1 has won the most tricks this round!");
            Team.addPointsToTeam1(1);
        } else {
            System.out.println("Team 2 has won the most tricks this round!");
            Team.addPointsToTeam2(1);
        }

        //reset each player's points
        this.resetPoints();
    }
    private void resetPoints()
    {
        for (int i = 0; i < NUM_PLAYERS; i++)
        {
            players[i].setPoints(0);
        }
    }
    //checks to see after each round if the game has been won
    private boolean hasWon() {
        if (Team.getTeam1Points() == 3) {
            System.out.println("The Game is Over! Team 1 Wins!");
            return true;
        } else if (Team.getTeam2Points() == 3) {
            System.out.println("The Game is Over! Team 2 Wins!");
            return true;
        }
        //prints out score
        System.out.println("\n\nScore\n" + "Team 1: " + Team.getTeam1Points() + "\nTeam 2: " + Team.getTeam2Points());
        return false;
    }

    //method to deal each player their hands
    private void dealHands() {
        for (int i = 0; i < NUM_PLAYERS; i++) {
            for (int j = 0; j < CARDS_PER_HAND; j++)
                players[i].addCard(cardDeck.deal());
        }
    }

    private void makeDealer() {
        //if the game has just started player 1 is the dealer, next round is Player 2 etc.
            dealer = players[numRounds % NUM_PLAYERS];
    }

    private void findWinner(int i, int index, Card card) {
        int trumpValue = 0;
        //if the card is a trump, create a new value where it will be higher than all none trump
        if (card.getSuit().equals(trump)) {
             trumpValue = card.getValue() * 10;
        }
        //if it is the first card, the winning card, and the suit of the card is the same as the first played it can win
        if (pot.isEmpty() || trumpValue > winningValue || (card.getSuit().equals(pot.get(0).getSuit())
                && card.getValue() > winningValue)) {
            //if the trump value is greater than the winningValue set it to winningValue
            if (trumpValue > winningValue) {
                winningValue = trumpValue;
            }
            //if not trump just set the cards value to the winning value
            else {
                winningValue = card.getValue();
            }
            winnerOfTrick = players[i];
            winningCard = card;
        }
    }
    //prompts user for index of a card
    private int getIndexOfCard(int indexOfTurn)
    {
        System.out.println(players[indexOfTurn].getName() + "'s Hand: " + players[indexOfTurn].getHand());
        System.out.println("Please give the index of the Card you want to Play 0 - "
                + (players[indexOfTurn].getHand().size() - 1));
        return input.nextInt();
    }
    //runs all turns in a round
    private void turn(int numTricks) {
        for (int i = 0; i < NUM_PLAYERS; i++) {

            boolean validPlay = true;
            int indexOfTurn = 0;

            //unless it is the first turn, the winner starts
            if (numTricks == 0) {
                indexOfTurn = ((i + numRounds + 1) % NUM_PLAYERS);
            } else {
                indexOfTurn = ((i + indexOfWinner) % NUM_PLAYERS);
            }
            //while the play has not been deemed faulty, run through turn
            while (validPlay) {

                //gets index and spaces out terminal so different players hands cannot be seen
                int indexInput = getIndexOfCard(indexOfTurn);
                input.nextLine();
                System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");

                //ensures the index is valid:
                //If valid it will add the card to the pot, update the winner of the hand if needed
                if (this.isValidPlay(indexInput, indexOfTurn)) {
                    this.findWinner(indexOfTurn, indexInput, players[indexOfTurn].getHand().get(indexInput));
                    pot.add(players[indexOfTurn].getHand().get(indexInput));
                    players[indexOfTurn].getHand().remove(indexInput);
                    validPlay = false;

                } else {
                    System.out.println("Invalid Index Please Try Again");
                }
                System.out.println("Current pot: " + pot);
            }
        }
    }

    //checks to see if player has the suit left in their hand
    private boolean hasNoSuit(int i) {
        for (Card card : players[i].getHand()) {
            if (card.getSuit().equals(pot.get(0).getSuit())) {
                return false;
            }
        }
        return true;
    }
    private void reset()
    {
        //resets the values of winning cards
        winnerOfTrick = null;
        winningCard = null;
        winningValue = 0;

        //resets the pot
        while (!pot.isEmpty()) {
            pot.remove(0);
        }
    }
    //checks to see if the user turn request is valid
    private boolean isValidIndex(int index, int i) {
        return players[i].getHand().size() > index && index >= 0;
    }

    //makes sure the user can play the card
    private boolean isValidPlay(int index, int i) {
        return isValidIndex(index, i) && (pot.isEmpty()
                || players[i].getHand().get(index).getSuit().equals(pot.get(0).getSuit()) || hasNoSuit(i));
    }

    //checks to see if the trump was created correctly
    private boolean isValidTrump() {
        //for all of the suits, if the input from the user is valid, return true
        for (String suit : suits) {
            if (suit.equals(trump)) {
                return true;
            }
        }
        return false;
    }
    //prompts the user for the answer to whether or not they want the top card
    private String takeTopCardAnswer(Card topCard)
    {
        System.out.println("\n\nTop Card: " + topCard + "\n");
        System.out.println(dealer.getName() + "'s Hand: " + dealer.getHand() + "\n");
        System.out.println("Dealer, would you like to pick up the top card? \n" +
                "Type 'y' for Yes and 'n' for No");
        return input.nextLine();
    }
    //adds top card to hand, removes chosen card, and sets trump
    private void addTopCard(Card topCard, int index)
    {
        dealer.getHand().remove(index);
        dealer.addCard(topCard);
        trump = topCard.getSuit();
    }
    private void takeTopCard() {
        boolean validInput = false;
        Card topCard = cardDeck.deal();

        //while the input is invalid keep running
        while (!validInput) {

            String answer = this.takeTopCardAnswer(topCard);
            //if the answer is y, prompt the user for an index for a card they want to remove
            if (answer.equals("y")) {
                System.out.println("Please give the index of the card you would like to remove: ");
                int indexOfRemovedCard = input.nextInt();
                input.nextLine();

                //if valid index removes the card and sets trump to the same as suit of top card
                if (isValidIndex(indexOfRemovedCard, numRounds % NUM_PLAYERS)) {
                    this.addTopCard(topCard, indexOfRemovedCard);
                    validInput = true;
                } else {
                    System.out.println("Invalid Index Please try again");
                }
            }
            //if answer is n, prompt user for what they would like for trump
            else if (answer.equals("n")) {
                System.out.println("What Suit would you like to be Trump instead");
                trump = input.nextLine();

                //if trump is valid, exit while loop
                validInput = this.isValidTrump();

                //otherwise this will print and it will reprompt user
                System.out.println("Invalid Trump Please try again");


            } else {
                System.out.println("Please Give a Valid Answer");
            }
        }
    }
}