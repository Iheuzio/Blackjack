/*
    Initializes a deck of cards using a dynamic array of cards 
    that could be scaled later to have more decks. It also
    has a shuffle and a 'deal' method that would call
    on the dynamic card array class. Constantly checks 
    every time it is drawn if the deck is empty to avoid]
    null pointer exceptions.
*/

public class Deck {
    private DynamicCardArray deck = new DynamicCardArray();
    private Card card;
    private int count;

    // constructor
    public Deck() {
        createDeck();
        this.count = deck.getCount();
    }
    // getter for the count
    public int getCount() {
        return count;
    }

    // create a deck
    private void createDeck() {
        // 4 suits
        for (int i = 0; i < 4; i++) {
            // 13 different values
            for (int j = 0; j < 13; j++) {
                card = new Card(Value.values()[j], Suit.values()[i]);
                deck.addToTop(card);
            }
        }
    }
    // Calls dynamic array shuffle method
    public void shuffleDeck() {
        deck.shuffleCards();
    }
    // Calls the dynamic array takeFromTop method, but needs to veerfiy that it is not empty
    public Card getTopCard() {
        count--;
        isEmpty();
        return deck.takeFromTop();
    }
    // If count is 0, recreate a new deck
    public void isEmpty() {
        if (count == 0) {
            createDeck();
            count = deck.getCount();
            shuffleDeck();
        }
    }
}