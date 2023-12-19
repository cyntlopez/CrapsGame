package model;

import java.util.Random;

/**
 * This class represents the logic for the game of craps.
 * It includes methods for rolling dice, updating bets, and managing
 * game states.
 *
 * @author cynlopez
 * @version Fall 2023
 */
public class GameLogic {
    private int myTotal;
    private int myPoint;
    private int myDieOne;
    private int myDieTwo;
    private int myPlayerScore;
    private int myHouseScore;
    private boolean myScoreGained;
    private final Random myRoller;
    private boolean myHasRolled;
    private int myBankBalance;
    private int myBetAmount;

    /**
     * Constructs a new instance of the GameLogic class.
     * Initializes game state variables and the random number generator.
     */
    public GameLogic() {
        myPoint = 0;
        myTotal = 0;
        myPlayerScore = 0;
        myHouseScore = 0;
        myDieOne = 0;
        myDieTwo = 0;
        myRoller = new Random();
        myHasRolled = false;
        myScoreGained = false;
        myBankBalance = 0;
        myBetAmount = 0;
    }

    /**
     * Rolls two six-sided dice and calculates the total.
     */
    public void rollDice() {
        myDieOne = myRoller.nextInt(6) + 1;
        myDieTwo = myRoller.nextInt(6) + 1;
        myTotal = myDieOne + myDieTwo;
    }

    /**
     * Handles the logic for the first roll of the dice, determining the game outcome.
     * Updates player and house scores accordingly.
     */
    public void firstRoll() {
        rollDice();

        if (myTotal == 7 || myTotal == 11) {
            myPlayerScore++;
            myScoreGained = true;
            winBet();

        } else if (myTotal == 2 || myTotal == 3 || myTotal == 12) {
            myHouseScore++;
            myScoreGained = true;
            loseBet();

        } else {
            myHasRolled = true;
            myPoint = myTotal;
        }
    }

    /**
     * Handles additional rolls after the first one, determining the game outcome.
     * Updates player and house scores accordingly.
     */
    public void additionalRolls() {
        rollDice();

        if (myPoint == myTotal) {
            myPlayerScore++;
            myScoreGained = true;
            winBet();

        } else if (myTotal == 7) {
            myHouseScore++;
            myScoreGained = true;
            loseBet();
        }
    }

    /**
     * Handles the logic for which roll logic will be called
     * depending on the if the player rolled.
     */
    private void genericRoll() {
        if (myHasRolled) {
            additionalRolls();

        } else {
            firstRoll();
        }
    }

    /**
     * Updates bank account with bet winnings.
     */
    public void winBet() {
        int winningBet = getBet() * 2;
        setBankBalance(getBankBalance() + winningBet);
        setBet(0);
    }

    /**
     * Updates bank account with bet losings.
     */
    public void loseBet() {
        setBankBalance(getBankBalance());
        setBet(0);
    }

    /**
     * Updates the current bet amount and deducts it from the player's bank balance.
     *
     * @param theBet The amount to be added to the current bet.
     * @throws IllegalArgumentException If the bet amount is non-positive.
     */
    public void updateBet(int theBet) {
        if (theBet > 0 && myBetAmount <= myBankBalance) {
            setBet(getBet() + theBet);
            setBankBalance(getBankBalance() - theBet);
        }
    }

    /**
     * Resets the game state, including scores, dice values, and bet amount.
     */
    public void resetGame() {
        myPlayerScore = 0;
        myHouseScore = 0;
        myTotal = 0;
        myPoint = 0;
        myDieOne = 0;
        myDieTwo = 0;
        myBetAmount = 0;
        myBankBalance = 0;
        myScoreGained = false;
        myHasRolled = false;
    }

    /**
     * Starts a new game by resetting specific game state variables.
     * Retains the current bank balance for the new game.
     */
    public void startNewGame() {
        myTotal = 0;
        myPoint = 0;
        myDieOne = 0;
        myDieTwo = 0;
        myHasRolled = false;
        myScoreGained = false;
        myBankBalance = getBankBalance();
        myBetAmount = 0;
    }

    /**
     * Sets the player's bank balance.
     *
     * @param theAmount The amount to set as the bank balance.
     * @throws NumberFormatException If the specified amount is negative.
     */
    public void setBankBalance(int theAmount){
        if (theAmount >= 0) {
            myBankBalance = theAmount;
        } else {
            throw new NumberFormatException("ONLY POSITIVE NUMBERS");
        }
    }

    /**
     * Sets the number of wins by the player.
     *
     * @param theWins The number of wins to set.
     */
    public void setPlayerWins(int theWins) {
        myPlayerScore = theWins;
    }

    /**
     * Sets the current bet amount.
     *
     * @param theBet The amount to set as the current bet.
     */
    public void setBet(int theBet) {
        this.myBetAmount = theBet;
    }

    /**
     * Checks if the player has gained a point in the current game round.
     *
     * @return true if the player gained a point, false otherwise.
     */
    public boolean getPointGained() {
        return myScoreGained;
    }

    /**
     * Gets the number of wins by the player.
     *
     * @return The number of wins by the player.
     */
    public int getPlayerWins() {
        return myPlayerScore;
    }

    /**
     * Gets the number of wins by the house.
     *
     * @return The number of wins by the house.
     */
    public int getHouseWins() {
        return myHouseScore;
    }

    /**
     * Gets the point value in the current game round.
     *
     * @return The point value.
     */
    public int getPoint() {
        return myPoint;
    }

    /**
     * Gets the value of the first die in the current roll.
     *
     * @return The value of the first die.
     */
    public int getDieOne() {
        return myDieOne;
    }

    /**
     * Gets the value of the second die in the current roll.
     *
     * @return The value of the second die.
     */

    public int getDieTwo() {
        return myDieTwo;
    }

    /**
     * Gets the total value of the dice in the current roll.
     *
     * @return The total value of the dice.
     */
    public int getTotal() {
        return myTotal;
    }

    /**
     * Gets the current bet amount.
     *
     * @return The current bet amount.
     */
    public int getBet() {
       return myBetAmount;
    }

    /**
     * Gets the player's current bank balance.
     *
     * @return The player's bank balance.
     */
    public int getBankBalance() {
        return myBankBalance;
    }

    /**
     * Initiates a generic roll based on the current game state.
     */
    public void getGenericRoll() {
       genericRoll();
    }
}
