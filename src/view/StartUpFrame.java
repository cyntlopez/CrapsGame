package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

/**
 * The class represents the initial frame of the application,
 * allowing users to start the game or exit the application.

 * The StartUpFrame serves as the entry point for the user to initiate the game.
 *
 * @author cynlopez
 * @version Fall 2023
 */
public class StartUpFrame extends JPanel implements ActionListener {
    private final JButton myStartButton;
    private final JButton myExitButton;
    private final JFrame myMainFrame = new JFrame();

    /**
     * Constructs a new StartUpFrame, initializing the "Play!" and "Exit" buttons,
     * and setting up the GUI components.
     */
    public StartUpFrame() {
        myExitButton = new JButton("Exit");
        myStartButton = new JButton("Play!");

        myStartButton.addActionListener(this);
        myExitButton.addActionListener(this);

        setupGUI();
    }

    /**
     * Sets up the GUI components, including layout and frame settings.
     */

    private void setupGUI() {
        layoutComponents();
        frameComponents();
    }

    /**
     * Configures the main frame settings, including size, location, title, and image.
     */
    private void frameComponents() {
        myMainFrame.setSize(650, 500);
        myMainFrame.setLocation(450, 250);
        myMainFrame.setResizable(false);
        myMainFrame.setTitle("The Game of Craps");
        myMainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myMainFrame.setVisible(true);

        ImageIcon imageIcon = new ImageIcon("images//dice_transparent_bg.png");
        JLabel imageLabel = new JLabel(imageIcon);

        myMainFrame.add(imageLabel, BorderLayout.CENTER);
    }

    /**
     * Sets up the layout of the buttons in the frame.
     */
    private void layoutComponents() {
        final JPanel layout = new JPanel(new FlowLayout(FlowLayout.CENTER));

        layout.add(myStartButton);
        layout.add(myExitButton);

        myMainFrame.add(layout, BorderLayout.SOUTH);
    }

    /**
     * Handles the actions performed when the "Play!" or "Exit" button is clicked.
     *
     * @param e The ActionEvent representing the user's interaction.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == myStartButton) {
            new PlayScreenFrame();
            myMainFrame.dispose();

        } else if (e.getSource() == myExitButton) {
            int result = JOptionPane.showConfirmDialog(this, "Do you want to exit the game?", "Exit",
                    JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                myMainFrame.dispatchEvent(new WindowEvent(myMainFrame, WindowEvent.WINDOW_CLOSING));
            }
        }
    }
}
