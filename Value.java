/*
    Initializes the value of the card using the predefined values. 
    Ace can be 1 or 11, depending on when u call the updateAce() if 
    needed. This will be called later in the toString() method, but
    also is very important for a lot of other methods with the 
    value_constraint
*/

public enum Value {
    // Ace will auto update later to 1 if needed
    ACE (11, "Ace"),
    TWO (2, "Two"),
    THREE (3, "Three"),
    FOUR (4, "Four"),
    FIVE (5, "Five"),
    SIX (6, "Six"),
    SEVEN (7, "Seven"),
    EIGHT (8, "Eight"),
    NINE (9, "Nine"),
    TEN (10, "Ten"),
    JACK (10, "Jack"),
    QUEEN (10, "Queen"),
    KING (10, "King");

    private final int value_constraint;
    private final String name_constraint;

    // constructor
    Value(int value_constraint, String name_constraint) {
        this.value_constraint = value_constraint;
        this.name_constraint = name_constraint;
    }

    // Getters
    public int getValueConstraint() {
        return value_constraint;
    }
    public String getNameConstraint() {
        return name_constraint;
    }

}
