/*
    Initializes the card object with the suit and value of the card enums.
    This serves to create the deck later when it is an array in 
    the dynamic card array for the Player class hand and the deck class.
*/

public class Card {
    private Value value;
    private Suit suit;

    // Constructor
    public Card(Value value, Suit suit) {
        this.value = value;
        this.suit = suit;
    }

    // Getters and setters
    public int getValue() {
        return value.getValueConstraint();
    }
    public String getSuit() {
        return suit.getSuitConstraint();
    }

    // Example: [Jack][H]10, [Ace][S]11 ...
    // Only shows if card is face up
    public String toString() {
        return "[" + value.getNameConstraint() + "]" + "[" + suit.getSuitConstraint() + "]" + value.getValueConstraint();
    }
    // Used by the updateAce() method to set the value of the Ace to 1 when needed
    public void setValue(int j) {
        this.value = Value.values()[j];
    }
}
