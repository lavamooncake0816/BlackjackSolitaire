/*
create a grid to hold the cards
initialise a deck and keep track of discards
implement the main game loop
 */
import java.util.Scanner;

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
    }

    public void play() {
        while (discardCount < 4) {
            displayGrid();
            System.out.println("Discards remaining: " + (4 - discardCount));
            Card card = deck.dealCard();
            System.out.println("Your card: " + card);

            boolean played = false;
            // the loop will repeat if the card wasn't played successfully
            while (!played) {
                int position = getPosition();
                played = placeCard(card, position);
            }
        }

        int finalScore = calculateScore();

        System.out.println("Game over! You scored " + finalScore + " points.");
    }

    private void displayGrid() {
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                if (grid[row][col] == null) {
                    System.out.print((row * 5 + col + 1) + " ");
                } else {
                    System.out.print(grid[row][col] + " ");
                }
            }
            System.out.println();
        }
    }

    private int getPosition() {
        System.out.println("Please enter the position of the card you would like to play: ");
        return scanner.nextInt() - 1; // convert to 0-based idx
    }

    private boolean placeCard(Card card, int position) {
        int row = position / 5;
        int col = position % 5;

        if (grid[row][col] == null) {
            grid[row][col] = card;
            return true;
        } else {
            System.out.println("Position " + position + " already occupied, please choose another position.");
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
