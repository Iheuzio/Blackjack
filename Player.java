/*
    Will be used by the player and the dealer to play the game,
    the dealer will act based on some rules.

    - The dealer will draw a card if the hand value is less than or equal to 16
    - The player takes priority for busting before the dealer
*/
public class Player {
    private int totalAssets;
    private String name;
    private DynamicCardArray hand;
    private int handSize;
    private boolean isBust;
    private boolean isBlackjack;
    private int bet;
    private int handValue;

    // constructor
    public Player(String name, int totalAssets) {
        this.name = name;
        this.handSize = 0;
        this.handValue = 0;
        this.totalAssets = totalAssets;
        this.hand = new DynamicCardArray();
    }

    // Getters
    public String getName() {
        return name;
    }
    public int getTotalAssets() {
        return this.totalAssets;
    }
    // Gets the value of the hand and returns it
    public int getHandValue() {
        handValue = 0;
        updateAce();
        for (int i = 0; i < handSize; i++) {
            handValue += hand.getCardValue(i);
        }
        return handValue;
    }
      // gets the bet and checks if it is valid, setting it, if not it is 0
      public int getBet(int bet) {
        // check if player has enough money
        if (totalAssets - bet < 0) {
            System.out.println("You don't have enough money to bet that much -> " + totalAssets);
            System.out.print("> ");
            return 0;
        } else {
            totalAssets -= bet;
            this.bet = bet;
        }
        return bet;
    }
    // -------------------------------------------------
    // Playing a Turn

    // draw card from deck
    public void drawCard(Deck deck) {
        hand.addToTop(deck.getTopCard());
        handSize++;
    }
    // print out hand
    public void printHand(boolean hidden) {
        System.out.println(this.name + "'s hand is: ");
        for (int i = 0; i < handSize; i++) {
            System.out.println(hand.toStringCard(i));
        }
        if (hidden == true && getHandValue() <= 16) {
            System.out.println("[Unknown]");
        }
        // Don't have time to fix the UI :(
        System.out.println("---------------------------------");
    }
    // update ace to 1 if hand value is over 21
    public void updateAce() {
        for (int i = 0; i < handSize; i++) {
            if (hand.getCardValue(i) == 11) {
                hand.setCardValue(i, 1);
            }
        }
    }
    // -------------------------------------------------
    // Ending a Turn

    // check if player won returns false if player wins
    public void isDealerWin(int end_case) {
        switch (end_case) {
            case 0:
                return;
            // If player busted
            case 1:
                System.out.println("You busted! Dealer wins!");
                return;
            // If the player got Blackjack
            case 2:
                System.out.println("You got blackjack! You win!");
                playerUpdateWin();
                return;
            // If the Dealer busted
            case 3:
                System.out.println("Dealer busted! You win!");
                playerUpdateWin();
                return;
            // If the Dealer got Blackjack
            case 4:
                System.out.println("Dealer got blackjack! You lose!");
                return;
            // If both players have Blackjack
            case 5:
                System.out.println("Both you and the dealer got blackjack! It's a tie!");
                gameTied();
                return;
            // Player hand greator than dealer hand
            case 6:
                System.out.println("You win! Your hand is higher than the dealer's hand");
                playerUpdateWin();
                return;
            // If the Dealer's hand is graetor than the player
            case 7:
                System.out.println("Dealer's hand is valued higher than yours! You lose!");
                return;
        // If the Dealer's hand is tied than the player (case 8 is the defeault here)
            default:
                System.out.println("Dealer's hand is valued equal to yours! Its a tie!");
                gameTied();
                return;
        }
    }
    // Player lost
    private void playerUpdateWin() {
        totalAssets += 2 * this.bet;
    }
    // Checks if the game is tied
    public void  gameTied() {
        this.totalAssets += this.bet;
    }
    // -------------------------------------------------
    // Reset the current hand

    // Resets the hand for a new round
    public void resetHand() {
        for (int i = 0; i < handSize; i++) {
            hand.removeCard(i);
        }
        this.handSize = 0;
        resetHandAttributes();
    }
    // reset hand attributes
    private void resetHandAttributes() {
        this.isBust = false;
        this.isBlackjack = false;
    }
    // -------------------------------------------------
    // Checking player's hand
    
    // check if hand is blackjack
    public boolean isBlackjack() {
        if (getHandValue() == 21) {
            isBlackjack = true;
        }
        return isBlackjack;
    }
    // Checks if players hand is over 21 after updating the ace
    public boolean isBust() {
        if (getHandValue() > 21) {
            updateAce();
            if (getHandValue() <= 21) {
                return false;
            }
            // Have to recheck since we updated the ace value to 1 here
            else if (getHandValue() > 21) {
                isBust = true;
            }
        }
        return isBust;
    }
}
