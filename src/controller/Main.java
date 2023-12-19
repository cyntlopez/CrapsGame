package controller;

import view.StartUpFrame;

/**
 * The Main class serves as the entry point for the Craps game application.
 * It launches the application by invoking the StartUpFrame.
 * The application flow begins with the StartUpFrame, allowing users to initiate the game.
 *
 * @author cynlopez
 * @version Fall 2023
 */

public class Main {
    public static void main(String... args) {
        javax.swing.SwingUtilities.invokeLater(StartUpFrame::new);
    }
}
