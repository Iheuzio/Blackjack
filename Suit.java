/*
    Initializes the suit of the card using each of the classes.
    It will then store that enum in the toString later
*/

public enum Suit {
    HEARTS ("H"),
    DIAMONDS ("D"),
    SPADES ("S"),
    CLUBS ("C");

    private final String suit_constraint;

    // Constructor
    Suit(String suit_constraint) {
        this.suit_constraint = suit_constraint;
    }

    // Getter for each of the suits
    public String getSuitConstraint() {
        return suit_constraint;
    }
}