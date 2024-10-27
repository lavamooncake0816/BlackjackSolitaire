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


}
