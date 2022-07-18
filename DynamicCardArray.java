/*
    Initializes a dynamic array of cards that
    will be used later by the Deck class. It 
    serves to provide the Deck class with methods to call upon
    too. Will remove cards when needed from the deck and restore
    itself too when empty.
*/

import java.util.Random;
public class DynamicCardArray {
    private Card[] cards;
    private Random rand = new Random();
    private int count = 0;
    int random;
    private Card top;

    // constructor
    public DynamicCardArray() {
        // Creates a dynamic null array of cards, the big size allows for moore decks
       this.cards = new Card[200];
    }

    // Getters
    public int getCount() {
        return this.count;
    }
     // Returns the card value at a given index
     public int getCardValue(int index) {
        return cards[index].getValue();
    }

    // This is used to get the toString of the card for the hand
    public String toStringCard(int index) {
        return cards[index].toString();
    }
/*
        Take a card from the top of the deck and move 
        all the other cards down whilst setting the last
        to null.
*/  
    public Card takeFromTop() {
        top = cards[0];
        // move the cards down
        for (int i = 0; i < this.count - 1; i++) {
            cards[i] = cards[i + 1];
        }
        // set the last card to null
        cards[count - 1] = null;
        this.count--;
        return top;
    }
    // Adds card to top of the deck using the count of the deck or hand (called in both)
    public void addToTop(Card card) {
        cards[count] = card;
        this.count++;
    }
    // Shuffle the deck
    public void shuffleCards() {
        // for each card
        for (int i = 0; i < count; i++) {
            int random = rand.nextInt(count);
            // swap the cards
            Card temp = cards[i];
            cards[i] = cards[random];
            cards[random] = temp;
        }
    }
    // removes a card by making it null
    public void removeCard(int index) {
        cards[index] = null;
        count = 0;
    }
    // Used by the updateAce() method and will update to 1 when needed
    public void setCardValue(int i, int j) {
        cards[i].setValue(j);
    }

}
