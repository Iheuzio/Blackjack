/*
    Can be a bit messy here with the main method,
    but I tried to condense it as much as possible.
    If I changed something it broke the main method 
    due to scope issues.

    This Class is the main application class for the
    Blackjack game. It will initilize the objects we 
    made all together and call on the other classes 
    verifying as it progresses. I tried to get rid 
    of all the redundant code, I believe it should be gone.

    Only the int value validation really needed a try catch 
    exception, the other ones will work fine if a user
    enters values.

*/

import java.util.Scanner;
public class Blackjack {
    // main method
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String name;
        String answer;
        int total_assets = 0;
        boolean game_over = false;
        boolean end_turn = false;
        boolean player_hidden = false;
        boolean dealer_hidden = true;
        int end_case = 0;
        int end_clause = 1;
        int player_bet;

        System.out.println("Welcome to Blackjack!");
        System.out.println("Enter your name:");
        System.out.print("> ");
        name = sc.nextLine();
        do {
            try {
                do{
                    System.out.println("Please enter a Integer value [1-2000] for your starting money.");
                    System.out.print("> ");
                    total_assets = Integer.parseInt(sc.nextLine());
                    end_clause = 0;
                } while (total_assets <= 0);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a Integer value [1-2000] for your starting money.");
                System.out.print("> ");
                // This is fine since we are setting it back to 1 which is the default
                end_clause = 1;
            }
        } while (end_clause == 1);
        // player cannot have more than 2000 assets
        if (total_assets > 2000) {
            System.out.println("You cannot have more than 2000$, Setting your total assets to 2000$");
            total_assets = 2000;
        }
        // initializes the player objects (dealer + You)
        Player player = new Player(name, total_assets);
        Player dealer = new Player("Dealer",0);
        // initializes the deck object
        Deck deck = new Deck();
        deck.shuffleDeck();
        do {
            System.out.println(name + " your total assets is " + player.getTotalAssets());
            System.out.println("Enter your bet:");
            // Gets the bet from the method and sets it to the player
            do {
                try {
                    do {
                        player_bet = player.getBet(Integer.parseInt(sc.nextLine()));
                        end_clause = 0;
                    } while (player_bet >= player.getTotalAssets());
                } catch (NumberFormatException e) {
                    System.out.println("Enter a value of 0 to " + player.getTotalAssets() + " (integer)");
                    System.out.print("> ");
                    end_clause = 1;
                }
            } while (end_clause == 1);
            // player draws a card
            end_case = startGame(player, dealer, deck, player_hidden, dealer_hidden);
            do {
                System.out.println("Do you want to Hit? (yes/no)");
                System.out.print("> ");
                answer = sc.next();
                if (answer.equals("yes")) {
                    end_turn = playATurn(player, deck, player_hidden);
                    if (dealer.getHandValue() <= 16 && end_turn == false) {
                        end_turn = playATurn(dealer, deck, dealer_hidden);
                    }
                }
                else if (answer.equals("no")) {
                    if (dealer.getHandValue() <= 16) {
                        drawCard(dealer, deck);
                    }
                    System.out.println("Dealer's hand was valued at " + dealer.getHandValue());
                    // Swap to player_hidden as the unknown is revealed
                    dealer.printHand(player_hidden);
                    end_turn = false;
                    end_case = checkCurrentHand(player, dealer, end_clause);
                }
                // don't need to specify "yes" or "no" since we have end_turn and end_case here
            } while (end_turn == false && end_case == 0);

            end_case = checkCurrentHand(player, dealer, end_clause);
            player.isDealerWin(end_case);
            // Will check if player wants to keep playing
            do {
                System.out.println("Keep playing? (yes/no)");
                System.out.print("> ");
                answer = sc.next();
                if (answer.equals("no")) {
                    game_over = true;
                    break;
                }
            } while (!answer.equals("yes"));
            // Player can't have 0 to continue playing
            if (game_over == false) {
                game_over = checkTotalAssets(player);
            }
            // Resets the hands and allows a new round to be played
            player.resetHand();
            dealer.resetHand();
            end_case = 0;
            end_turn = false;
        } while (!game_over);
        System.out.println("Thanks for playing!");
        // show player's money left after quitting
        System.out.println(name + " you left with " + player.getTotalAssets() + "$");
    }

    // Iniates a turn for each the dealer and player whilst checking for a win
    public static boolean playATurn(Player player, Deck deck, boolean hidden) {
        drawCard(player, deck);
        System.out.println(player.getName() + " hand is currently valued at : " + player.getHandValue());
        player.printHand(hidden);
        if(player.isBust() == true) {
            return true;
        }
        else if(player.isBlackjack() == true) {
            return true;
        }
        return false;
    }
    // Checks multiple conditions for a win or loss and returning this code to the player class
    // so we can use it for a custom return message for the player.
    public static int checkCurrentHand(Player player, Player dealer, int clause) {
            // Both the dealer and player have blackjack
            if(dealer.isBlackjack() == true && player.isBlackjack() == true) {
                return 5;
            }
            // Player busts
            if(player.isBust() == true) {
                 return 1;
            }
            // Player has blackjack
            if(player.isBlackjack() == true) {
                return 2;
            }
            // Dealer busts
            if(dealer.isBust() == true) {
                return 3;
            }
            // Dealer has blackjack
            if(dealer.isBlackjack() == true) {
                return 4;
            }
            // Only runs this code after game has ended, not during the start
            if (clause >= 0) {
                // If player has a greater hand value than dealer
                if (player.getHandValue() > dealer.getHandValue()) {
                    return 6;
                }
                // If player has a lesser hand value than dealer
                if (player.getHandValue() < dealer.getHandValue()) {
                    return 7;
                }
                // If player has the same hand value as dealer
                if (player.getHandValue() == dealer.getHandValue()) {
                    return 8;
                }
            }
        return 0;
    }
    // Starts the game up with the first turn dealing and checking some conditions
    public static int startGame(Player player, Player dealer, Deck deck, boolean player_hidden, boolean dealer_hidden) {
        int end_case = 0;
        int start_clause = 0;
        // player draws a card
        drawCard(player, deck);
        drawCard(player, deck);
        // dealer draws a card
        drawCard(dealer, deck);;
        // prints out player hand
        System.out.println(player.getName() + "'s hand is currently valued at : " + player.getHandValue());
        player.printHand(player_hidden);
        // prints out dealer hand
        System.out.println("Dealer's hand is currently valued at : " + dealer.getHandValue());
        dealer.printHand(dealer_hidden);
        end_case = checkCurrentHand(player, dealer, start_clause);
        return end_case;
    }
    // Everytime a card is drawn, this will check if the deck is empty then draw a card if not
    public static void drawCard(Player player, Deck deck) {
        if (deck.getCount() == 0) {
            deck.shuffleDeck();
        }
        player.drawCard(deck);
    }
    // Checks if the player has 0 to continue playing
    public static boolean checkTotalAssets(Player player) {
        if (player.getTotalAssets() == 0) {
            return true;
        }
        return false;
    }
}

