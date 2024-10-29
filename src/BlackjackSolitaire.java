/*
create a grid to hold the cards
initialise a deck and keep track of discards
implement the main game loop
 */
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;

public class BlackjackSolitaire {
    private Card[][] grid;
    private Deck deck;
    private Scanner scanner;
    private int discardCount;

    public BlackjackSolitaire() {
        grid = new Card[4][5];
        deck = new Deck();
        scanner = new Scanner(System.in);
        discardCount = 0;

        // Initialise the grid and randomly assign discard spots
        initialiseGridWithDiscardSpots(); // No need to display the grid here
    }

    private Set<Integer> initialiseGridWithDiscardSpots() {
        Random rand = new Random();
        Set<Integer> discardSpots = new HashSet<>();

        // Randomly select 4 unique pos for discards from 1 to 20
        while (discardSpots.size() < 4) {
            int spot = rand.nextInt(20) + 1; // Generate a number between 1 and 20
            discardSpots.add(spot); // Add the position to the set
        }

        // Fill the grid with null to indicate all positions are initially empty
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                grid[row][col] = null;
            }
        }
        return discardSpots;
    }

    private void displayGridWithDiscardSpots(Set<Integer> discardSpots) {
        System.out.println("Grid positions (D marks discard spots):");
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                int spotIdx = row * 5 + col + 1;
                if (discardSpots.contains(spotIdx)) {
                    System.out.println("D "); // Mark discard positions
                } else {
                    if (grid[row][col] == null) {
                        System.out.print((row * 5 + col + 1) + " "); // Show position numbers for empty scoring spots
                    } else {
                        System.out.print(grid[row][col].toString() + " "); // Show the card representation
                    }
                }
            }
            System.out.println(); // New line after each row
        }
    }

    public void play() {
        Set<Integer> discardSpots = initialiseGridWithDiscardSpots();
        displayGridWithDiscardSpots(discardSpots); // Show the current state of the grid

        while (discardCount < 4) {
            System.out.println("Discards remaining: " + (4 - discardCount));

            Card card = deck.dealCard();
            System.out.println("Your card: " + card);

            boolean played = false;
            // the loop will repeat if the card wasn't played successfully
            while (!played) {
                int position = getPosition(); // Get the position from user input
                if (discardSpots.contains(position)) {
                    // If the position is a discard spot, just increment the discard count
                    discardCount++;
                    System.out.println("Card discarded at position: " + position + ".");
                    played = true;
                } else {
                    played = placeCard(card, position); // Try to place the card

                    // Display the grid again after placing the card
                    if (played) {
                        displayGridWithDiscardSpots(discardSpots); // Show updated grid with discard indicators
                    } else {
                        System.out.println("Please choose another position.");
                    }
                }
            }

            // Check if all scored positions are filled after placing the card
            if (areAllScoredPositionsFilled()) {
                System.out.println("All scored positions filled. Scoring the hands...");
                int finalScore = calculateScore();
                System.out.println("Game over! You scored " + finalScore + " points.");
                break; // Exit the loop
            }
        }
    }

    //
    private boolean areAllScoredPositionsFilled() {
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                if (grid[row][col] == null) {
                    return false;
                }
            }
        }
        return true;
    }

    private int getPosition() {
        System.out.println("Please enter the position of the card you would like to play: ");
        return scanner.nextInt();
    }

    private boolean placeCard(Card card, int position) {
        int row = (position - 1)/ 5;
        int col = (position - 1) % 5;

        if (row < 4 && col < 5 && grid[row][col] == null) {
            grid[row][col] = card;
            return true; // Placement successfully
        } else {
            System.out.println("Position " + position + " invalid.");
            return false;
        }
    }

    private int calculateScore() {
        int totalScore = 0;

        // score each row
        for (int row = 0; row < grid.length; row++) {
            totalScore += calculateHandScore(row, true); // indicates it's a row
        }

        for (int col = 0; col < grid[0].length; col++) {
            totalScore += calculateHandScore(col, false); // indicates it's a col
        }

        return totalScore;
    }

    private int calculateHandScore(int idx, boolean isRow) {
        int handScore = 0;
        int acesCount = 0;
        int cardCount = 0;

        // calculate the sum based on whether we are processing a row or a col
        for (int i = 0; i < (isRow ? grid[idx].length : grid.length); i++) {
            Card card = isRow ? grid[idx][i] : grid[i][idx];

            if (card != null) {
                int cardValue = card.getCardValue(card);
                handScore += cardValue;
                cardCount++;

                // count Aces (temporary)
                if (card.getValue().equals("A")) {
                    acesCount++;
                }
            }
        }

        // determine the hand type and calculate points (don't forget adjusting Aces)
        while (handScore > 21 && acesCount > 0) {
            handScore -= 10;
            acesCount--;
        }

        if (handScore == 21 && cardCount == 2) {
            return 10; // Blackjack
        } else if (handScore == 21 && (acesCount >= 3 && acesCount <= 5)) {
            return 7;
        } else if (handScore == 20) {
            return 5;
        } else if (handScore == 19){
            return 4;
        } else if (handScore == 18){
            return 3;
        } else if (handScore == 17){
            return 2;
        } else if (handScore <= 16){
            return 1;
        } else {
            return 0; // hand of any size that sums to 22 or more (bust)
        }
    }
}
