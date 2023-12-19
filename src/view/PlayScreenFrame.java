package view;

import javax.swing.*;
import model.GameLogic;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

/**
 * The PlayScreenFrame class represents the main GUI window for the game of craps.
 * It includes components for rolling dice, managing bets, displaying current roll information,
 * handling player wins, and providing options through a menu bar.

 * The PlayScreenFrame class utilizes the GameLogic class to manage the underlying game logic.

 *
 * @author cynlopez
 * @version Fall 2023
 */

public class PlayScreenFrame extends JFrame implements ActionListener {
    private JMenuItem start, reset, exit;
    private JMenuItem about, rules, shortcuts;
    private final GameLogic myGameLogic;
    private final JFrame myMainFrame = new JFrame();
    private final JButton myPlayButton = new JButton("Play Again");
    private final JButton myRollDiceButton = new JButton("Roll Dice");
    private final JButton myBetButton = new JButton("BET");
    private final JButton mySetBankButton = new JButton("Set Bank");
    private final JTextField myPlayerWinsField = new JTextField("0");
    private final JTextField myHouseWinsField = new JTextField("0");
    private final JTextField myPointField = new JTextField();
    private final JTextField myTotalField = new JTextField();
    private final JTextField myDieOneField = new JTextField();
    private final JTextField myDieTwoField = new JTextField();
    private final JTextField myBetAmount = new JTextField();
    private final JTextField myCurrentBet = new JTextField();
    private final JTextField myBankAmount = new JTextField();

    /**
     * Constructs a new PlayScreenFrame, initializing the GameLogic instance and setting up the GUI components.
     */
    public PlayScreenFrame() {
        myGameLogic = new GameLogic();
        setupGUI();
    }

    /**
     * Sets up the GUI components, including the menu bar, buttons, text fields, and panels.
     */
    private void setupGUI() {
        frameComponents();
        menuBar();
        rollDiceComponents();
        currentRollComponents();
        bankComponents();
        playButtonComponents();
        winsTotalComponents();
        betComponents();
    }

    /**
     * Sets up the components for the play button, allowing the user to start a new game.
     */
    private void playButtonComponents() {
        final JPanel playPanel = new JPanel();

        playPanel.setLayout(new GridBagLayout());
        GridBagConstraints gridLayout = new GridBagConstraints();
        gridLayout.gridx = 0;
        gridLayout.gridy = 0;
        gridLayout.anchor = GridBagConstraints.CENTER;

        playPanel.add(myPlayButton, gridLayout);

        myPlayButton.setEnabled(false);

        myPlayButton.addActionListener(e -> {
            myGameLogic.startNewGame();
            myDieOneField.setText("0");
            myDieTwoField.setText("0");
            myTotalField.setText("0");
            myPointField.setText("0");
            myBetAmount.setText("0");
            myCurrentBet.setText("0");
            myRollDiceButton.setEnabled(true);

        });

        myMainFrame.add(playPanel);
    }

    /**
     * Sets up the components for rolling dice, displaying the point value, and updating player and house wins.
     */
    private void rollDiceComponents() {
        final JPanel rollDicePanel = new JPanel();
        rollDicePanel.setLayout(new BoxLayout(rollDicePanel, BoxLayout.X_AXIS));

        JLabel pointLabel = new JLabel("Point: ");
        myPointField.setMaximumSize(new Dimension(125, 25));

        rollDicePanel.add(pointLabel);
        rollDicePanel.add(myPointField);
        rollDicePanel.add(myRollDiceButton);

        myRollDiceButton.addActionListener(e -> {
            int pointValue = myGameLogic.getPoint();

            myPointField.setText(String.valueOf(pointValue));

            myRollDiceButton.setEnabled(!myGameLogic.getPointGained());

            SwingUtilities.invokeLater(() -> {
                int playerWins = myGameLogic.getPlayerWins();
                int houseWins = myGameLogic.getHouseWins();

                myPlayerWinsField.setText(String.valueOf(playerWins));
                myHouseWinsField.setText(String.valueOf(houseWins));

            });
        });

        myRollDiceButton.setEnabled(false);
        myPointField.setEditable(false);

        myMainFrame.add(rollDicePanel);
    }

    /**
     * Sets up the components for managing bets, including input fields and a button to place bets.
     */
    private void betComponents() {
        final JPanel betPanel = new JPanel();
        betPanel.setLayout(new BoxLayout(betPanel, BoxLayout.PAGE_AXIS));
        betPanel.setBorder(BorderFactory.createTitledBorder("Bet"));

        JLabel moneyLabel = new JLabel("How much do you want to bet?");
        JLabel moneyLabel1 = new JLabel("(In dollars)");
        JLabel betLabel = new JLabel("Bet Total:");
        myBetAmount.setMaximumSize(new Dimension(125, 25));
        myCurrentBet.setMaximumSize(new Dimension(125, 25));

        betPanel.add(betLabel);
        betPanel.add(myCurrentBet);
        betPanel.add(moneyLabel);
        betPanel.add(moneyLabel1);
        betPanel.add(myBetAmount);
        betPanel.add(myBetButton);

        myBetButton.addActionListener(e -> {
            try {
                int betAmount = Integer.parseInt(myBetAmount.getText());

                betInput(betAmount);

                myBetAmount.setText(Integer.toString(betAmount));


            } catch (NumberFormatException exception) {
                JOptionPane.showMessageDialog(this, "Input valid bet amount.",
                        "Uh oh!!", JOptionPane.ERROR_MESSAGE);
            }
        });

        myBetAmount.setEditable(true);
        myBetButton.setEnabled(false);
        myCurrentBet.setEditable(false);

        myMainFrame.add(betPanel);
    }

    /**
     * Handles the logic for computing bet input and updating the game state.
     *
     * @param theAmount The amount to be bet by the player.
     */
    private void betInput(int theAmount) {
        try {
            int currentBankAmount = Integer.parseInt(myBankAmount.getText());

            if (theAmount > currentBankAmount) {
                JOptionPane.showMessageDialog(this, "Bet amount is more than Bank amount.",
                        "Uh oh!", JOptionPane.ERROR_MESSAGE);
            } else {
                int totalBet = myGameLogic.getBet() + theAmount;
                myGameLogic.updateBet(theAmount);
                myGameLogic.setBankBalance(currentBankAmount - theAmount);
                myGameLogic.setBet(totalBet);

                myBetAmount.setText(Integer.toString(totalBet));
                myCurrentBet.setText(Integer.toString(totalBet));
                myBankAmount.setText(Integer.toString(myGameLogic.getBankBalance()));

            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Input amount in bank before betting. ",
                    "Uh oh!!!", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Sets up the components for managing the player's bank balance.
     */
    private void bankComponents() {
        final JPanel bankPanel = new JPanel();
        bankPanel.setLayout(new BoxLayout(bankPanel, BoxLayout.X_AXIS));
        bankPanel.setBorder(BorderFactory.createTitledBorder("Bank"));

        JLabel moneyLabel = new JLabel("$");
        myBankAmount.setMaximumSize(new Dimension(125, 25));

        bankPanel.add(moneyLabel);
        bankPanel.add(myBankAmount);
        bankPanel.add(mySetBankButton);

        mySetBankButton.addActionListener(e -> {
            try {
                int bankAmount = Integer.parseInt(myBankAmount.getText());
                myGameLogic.setBankBalance(bankAmount);

                myBankAmount.setText(Integer.toString(bankAmount));

            } catch (NumberFormatException exception) {
                JOptionPane.showMessageDialog(this, "Input valid bank amount.",
                        "Uh oh!!", JOptionPane.ERROR_MESSAGE);
            }
        });

        myBankAmount.setEditable(true);

        myMainFrame.add(bankPanel);
    }

    /**
     * Sets up the components for displaying information about the current roll, including dice values and totals.
     */
    private void currentRollComponents() {
        final JPanel currentRollPanel = new JPanel();
        currentRollPanel.setLayout(new BoxLayout(currentRollPanel, BoxLayout.PAGE_AXIS));
        currentRollPanel.setBorder(BorderFactory.createTitledBorder("Current Roll"));

        JLabel dieOneLabel = new JLabel("Die One: ");
        myDieOneField.setMaximumSize(new Dimension(125, 25));
        JLabel dieTwoLabel = new JLabel("Die Two: ");
        myDieTwoField.setMaximumSize(new Dimension(125, 25));
        JLabel totalLabel = new JLabel("Total: ");
        myTotalField.setMaximumSize(new Dimension(125, 25));

        myRollDiceButton.addActionListener(e -> {
            myGameLogic.getGenericRoll();

            int dieOneValue = myGameLogic.getDieOne();
            int dieTwoValue = myGameLogic.getDieTwo();
            int totalValue = myGameLogic.getTotal();

            myDieOneField.setText(String.valueOf(dieOneValue));
            myDieTwoField.setText(String.valueOf(dieTwoValue));
            myTotalField.setText(String.valueOf(totalValue));
        });

        currentRollPanel.add(dieOneLabel);
        currentRollPanel.add(myDieOneField);
        currentRollPanel.add(dieTwoLabel);
        currentRollPanel.add(myDieTwoField);
        currentRollPanel.add(totalLabel);
        currentRollPanel.add(myTotalField);

        myDieOneField.setEditable(false);
        myDieTwoField.setEditable(false);
        myTotalField.setEditable(false);

        myMainFrame.add(currentRollPanel);
    }

    /**
     * Sets up the components for displaying player and house win totals.
     */
    private void winsTotalComponents() {
        final JPanel winsTotalPanel = new JPanel();

        winsTotalPanel.setLayout(new BoxLayout(winsTotalPanel, BoxLayout.PAGE_AXIS));
        winsTotalPanel.setBorder(BorderFactory.createTitledBorder("Win Totals"));

        JLabel playerWinsLabel = new JLabel("Player Win Total: ");
        myPlayerWinsField.setMaximumSize(new Dimension(125, 25));
        JLabel houseWinsLabel = new JLabel("House Win Total: ");
        myHouseWinsField.setMaximumSize(new Dimension(125, 25));

        myRollDiceButton.addActionListener(e -> {
            int playerWins = myGameLogic.getPlayerWins();
            int houseWins = myGameLogic.getHouseWins();

            myPlayerWinsField.setText(String.valueOf(playerWins));
            myHouseWinsField.setText(String.valueOf(houseWins));

            SwingUtilities.invokeLater(() -> {
                int bankUpdate = myGameLogic.getBankBalance();

                myBankAmount.setText(Integer.toString(bankUpdate));

            });
        });

        winsTotalPanel.add(playerWinsLabel);
        winsTotalPanel.add(myPlayerWinsField);
        winsTotalPanel.add(houseWinsLabel);
        winsTotalPanel.add(myHouseWinsField);

        myPlayerWinsField.setEditable(false);
        myHouseWinsField.setEditable(false);

        myMainFrame.add(winsTotalPanel);
    }

    /**
     * Resets the frame by clearing all fields and disabling certain buttons.
     */
    private void resetFrame() {
        myPointField.setText("");
        myBetAmount.setText("");
        myBankAmount.setText("");
        myDieOneField.setText("");
        myDieTwoField.setText("");
        myTotalField.setText("");
        myPlayerWinsField.setText("0");
        myHouseWinsField.setText("0");
        myCurrentBet.setText("");
        myBetButton.setEnabled(false);
        myRollDiceButton.setEnabled(false);
        myPlayButton.setEnabled(false);
    }

    /**
     * Sets up the main frame properties, such as size, location, title, and default close operation.
     */
    private void frameComponents() {
        myMainFrame.setLayout(new GridLayout(2,3,5,5));
        myMainFrame.setSize(800,550);
        myMainFrame.setLocation(400,200);
        myMainFrame.setResizable(false);
        myMainFrame.setBackground(Color.LIGHT_GRAY);
        myMainFrame.setTitle("The Game of Craps");
        myMainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myMainFrame.setVisible(true);
    }

    /**
     * Sets up the menu bar with many options, such as game actions and help options.
     */
    private void menuBar() {
        myMainFrame.requestFocusInWindow();

        JMenuBar bar = new JMenuBar();

        JMenu gameMenu = new JMenu("Game");
        JMenu helpMenu = new JMenu("Help");

        start = new JMenuItem("Start");
        reset = new JMenuItem("Reset");
        exit = new JMenuItem("Exit");

        about = new JMenuItem("About");
        rules = new JMenuItem("Rules");
        shortcuts = new JMenuItem("Shortcuts");

        myMainFrame.setJMenuBar(bar);

        bar.add(gameMenu);
        bar.add(helpMenu);

        gameMenu.add(start);
        gameMenu.add(reset);
        gameMenu.add(exit);

        helpMenu.add(about);
        helpMenu.add(rules);
        helpMenu.add(shortcuts);

        start.addActionListener(this);
        reset.addActionListener(this);
        exit.addActionListener(this);
        about.addActionListener(this);
        rules.addActionListener(this);
        shortcuts.addActionListener(this);

        start.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
        reset.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,KeyEvent.CTRL_DOWN_MASK));
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,KeyEvent.CTRL_DOWN_MASK));
        about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,KeyEvent.CTRL_DOWN_MASK));
        rules.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U,KeyEvent.CTRL_DOWN_MASK));
        shortcuts.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,KeyEvent.CTRL_DOWN_MASK));

        myRollDiceButton.addActionListener(this);
        myBetButton.addActionListener(this);
        myPlayButton.addActionListener(this);
        mySetBankButton.addActionListener(this);

        myPlayButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_DOWN_MASK), "play");
        myPlayButton.getActionMap().put("play", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myPlayButton.doClick();
                myPlayButton.requestFocusInWindow();
            }
        });

        myRollDiceButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_D, KeyEvent.CTRL_DOWN_MASK), "roll");
        myRollDiceButton.getActionMap().put("roll", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myRollDiceButton.doClick();
                myRollDiceButton.requestFocusInWindow();
            }
        });

        myBetButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_M, KeyEvent.CTRL_DOWN_MASK), "bet");
        myBetButton.getActionMap().put("bet", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myBetButton.doClick();
                myBetButton.requestFocusInWindow();
            }
        });

        mySetBankButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_T, KeyEvent.CTRL_DOWN_MASK), "set bank");
        mySetBankButton.getActionMap().put("set bank", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mySetBankButton.doClick();
                mySetBankButton.requestFocusInWindow();
            }
        });
    }

    /**
     * Handles the actions performed when menu items are clicked.
     *
     * @param e The ActionEvent representing the user's interaction.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == exit) {
            int result = JOptionPane.showConfirmDialog(this,"Do you want to exit the game?", "Exit",
                    JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                myMainFrame.dispatchEvent(new WindowEvent(myMainFrame, WindowEvent.WINDOW_CLOSING));
            }
        }
        if (e.getSource() == start) {
            myPlayButton.setEnabled(true);
            myRollDiceButton.setEnabled(true);
            myBetButton.setEnabled(true);
            start.setEnabled(false);
        }
        if (e.getSource() == reset) {
            myGameLogic.resetGame();
            resetFrame();
            start.setEnabled(true);
        }
        if (e.getSource() == about) {
            JOptionPane.showMessageDialog(this, "Game made by Cynthia Lopez. Version" +
                    " one. Code was written in JDK 19. ", "About", JOptionPane.INFORMATION_MESSAGE);
        }
        if (e.getSource() == rules) {
            JOptionPane.showMessageDialog(this, """
                   The rules of the game:\s
                   \s
                   A player rolls two dice where each die has six faces in the usual way (values 1 through 6).
                   After the dice have come to rest the sum of the two upward faces is calculated.
                   The first roll/throw:\s
                   If the sum is 7 or 11 on the first throw the roller/player wins.
                   If the sum is 2, 3 or 12 the roller/player loses, that is the house wins.
                   If the sum is 4, 5, 6, 8, 9, or 10, that sum becomes the roller/player's 'point'.
                   Continue rolling given the player's point
                   Now the player must roll the 'point' total before rolling a 7 in order to win.
                   If they roll a 7 before rolling the point value they got on the first roll the roller/player loses (the 'house' wins).""",
                    "Rules", JOptionPane.INFORMATION_MESSAGE);
        }
        if (e.getSource() == shortcuts) {
            JOptionPane.showMessageDialog(this, """
                            SHORTCUTS FOR BUTTONS:\s
                            \s
                            Roll Dice (ctrl + D)
                            Play Again (ctrl + P)
                            Set Bank (ctrl + T)
                            Bet (ctrl + M)
                            \s
                            SHORTCUTS FOR MENU ITEMS:\s
                            \s
                            Start (ctrl + S)
                            Reset (ctrl + R)
                            Exit (ctrl + E)
                            About (ctrl + A)
                            Rules (ctrl + U)
                            Shortcuts (ctrl + L)
                            """,
                    "Shortcuts", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
