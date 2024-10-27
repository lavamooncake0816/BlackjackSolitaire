/*
define an arraylist to hold cards
create a constructor that initialises the deck and shuffles it
implement a method to deal card
 */
import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private ArrayList<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
        String[] suits = {"C", "D", "H", "S"}; // Clubs, Diamonds, Hearts, Spades
        String[] values = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};

        for (String suit : suits) {
            for (String value : values) {
                cards.add(new Card(suit, value));
            }
        }
        Collections.shuffle(cards);
    }

    public Card dealCard() {
        return cards.removeLast();
    }
}
