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

    public int getCardValue(Card card) {
        switch (card.getValue()) {
            case "J":
            case "Q":
            case "K":
                return 10;
            case "A":
                return 11; // adjust in score calculation
            default:
                return Integer.parseInt(value);
        }
    }
}
