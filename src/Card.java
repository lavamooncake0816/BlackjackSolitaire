/*
define the properties for suit and values
implement a constructor and methods for getting card details
 */
public class Card {
    private String suit;
    private String value;

    public Card(String suit, String value) {
        this.suit = suit;
        this.value = value;
    }

    public String getSuit() {
        return suit;
    }

    public String getValue() {
        return value;
    }

    public String toString() {
        return value + suit;
    }
}
