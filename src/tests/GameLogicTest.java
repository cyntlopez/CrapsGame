package tests;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import model.GameLogic;

/**
 * Unit tests for Game Logic class.
 *
 * @author cynlopez
 * @version Fall 2023
 */
public class GameLogicTest {

    private GameLogic gameLogic;

    /**
     * Sets up the GameLogic instance for testing.
     */
    @Before
    public void setUp() {
        gameLogic = new GameLogic();
    }

    /**
     * Checks that all initial values are set to zero.
     */
    @Test
    public void testInitialization() {
        assertEquals(0, gameLogic.getPoint());
        assertEquals(0, gameLogic.getDieOne());
        assertEquals(0, gameLogic.getDieTwo());
        assertEquals(0, gameLogic.getTotal());
        assertEquals(0, gameLogic.getPlayerWins());
        assertEquals(0, gameLogic.getHouseWins());
        assertEquals(0, gameLogic.getBet());
        assertEquals(0, gameLogic.getBankBalance());
    }

    /**
     * Checks that die rolls are within a range of 1 to 6.
     */
    @Test
    public void testRoll() {
        for (int i = 0; i < 50; i++) {
            gameLogic.rollDice();
            int dieOneValue = gameLogic.getDieOne();
            int dieTwoValue = gameLogic.getDieTwo();

            assertTrue(dieOneValue >= 1 && dieOneValue <= 6);
            assertTrue(dieTwoValue >= 1 && dieTwoValue <= 6);
        }
    }

    /**
     * Checks that the bet is correctly updated, and the bank balance reflects the change.
     */
    @Test
    public void testUpdateBet() {
        gameLogic.setBankBalance(100);
        gameLogic.updateBet(50);
        assertEquals(50, gameLogic.getBet());
        assertEquals(50, gameLogic.getBankBalance());
    }

    /**
     * Checks that the bet is reset to zero, and the bank balance increases by twice the bet amount.
     */
    @Test
    public void testWinBet() {
        gameLogic.setBankBalance(100);
        gameLogic.updateBet(50);
        gameLogic.winBet();
        assertEquals(0, gameLogic.getBet());
        assertEquals(150, gameLogic.getBankBalance());
    }

    /**
     * Checks that the bet is reset to zero, and the bank balance remains unchanged.
     */
    @Test
    public void testLoseBet() {
        gameLogic.setBankBalance(100);
        gameLogic.updateBet(50);
        gameLogic.loseBet();
        assertEquals(0, gameLogic.getBet());
        assertEquals(50, gameLogic.getBankBalance());
    }

    /**
     * Checks that the player wins are correctly incremented.
     */
    @Test
    public void testFirstRollWin() {
        int playerWins = gameLogic.getPlayerWins();
        gameLogic.setPlayerWins(playerWins + 1);
        int abc = gameLogic.getPlayerWins();
        assertTrue(playerWins < abc);

    }

    /**
     * Checks that the player wins are correctly decremented.
     */

    @Test
    public void testFirstRollLose() {
        int playerWins = gameLogic.getPlayerWins();
        gameLogic.setPlayerWins(playerWins - 1);
        int abc = gameLogic.getPlayerWins();
        assertTrue(playerWins > abc);
    }

    /**
     * Checks that the total value after the first roll is equal to the sum of the two dice values.
     */
    @Test
    public void testFirstRollPoint() {
        gameLogic.firstRoll();
        int diceValue = gameLogic.getDieOne() + gameLogic.getDieTwo();
        int total = gameLogic.getTotal();
        assertEquals(diceValue, total);
    }
}