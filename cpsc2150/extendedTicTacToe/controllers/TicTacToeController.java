package cpsc2150.extendedTicTacToe.controllers;

import cpsc2150.extendedTicTacToe.models.*;
import cpsc2150.extendedTicTacToe.views.*;

import java.awt.event.ActionEvent;

/**
 * <p>
 * The {@link TicTacToeController} class will handle communication between our {@link TicTacToeView}
 * and our model ({@link IGameBoard} and {@link BoardPosition})
 * </p>
 *
 * <p>
 * This is where you will write code
 * <p>
 *
 * You will need to include your {@link BoardPosition} class, the {@link IGameBoard} interface
 * and both of the {@link IGameBoard} implementations from Project 4.
 * If your code was correct you will not need to make any changes to your {@link IGameBoard} implementation class.
 *
 * @version 2.0
 */
public class TicTacToeController {

    /**
     * <p>
     * The current game that is being played
     * </p>
     */
    private IGameBoard curGame;

    /**
     * <p>
     * The screen that provides our view
     * </p>
     */
    private TicTacToeView screen;

    /**
     * <p>
     * Constant for the maximum number of players.
     * </p>
     */
    public static final int MAX_PLAYERS = 10;

    /**
     * <p>
     * The number of players for this game. Note that our player tokens are hard coded.
     * </p>
     */
    private int numPlayers;

    /**
     * <p>
     * The array for the players markers
     * </p>
     */
    private char [] players = new char[10];

    private boolean inGame = true;
    private char curPlayer;
    private int turnCounter;


    /**
     * <p>
     * This creates a controller for running the Extended TicTacToe game
     * </p>
     *
     * @param model
     *      The board implementation
     * @param view
     *      The screen that is shown
     * @param np
     *      The number of players for this game.
     *
     * @post [ the controller will respond to actions on the view using the model. ]
     */
    public TicTacToeController(IGameBoard model, TicTacToeView view, int np) {
        this.curGame = model;
        this.screen = view;
        this.numPlayers = np;


        // Initiates each player in the player array.
        for(int i = 0; i < np; i++){
            if(i > 2){
                this.players[i] = (char) ('A' + (i - 3));
            }
            else{
                this.players[i] = (char) ('X' + i);
            }
        }

        // Make the first player's symbol X
        curPlayer = 'X';

        // Initialize the turn counter
        turnCounter = 0;

    }

    /**
     * <p>
     * This processes a button click from the view.
     * </p>
     *
     * @param row
     *      The row of the activated button
     * @param col
     *      The column of the activated button
     *
     * @post [ will allow the player to place a marker in the position if it is a valid space, otherwise it will display an error
     * and allow them to pick again. Will check for a win as well. If a player wins it will allow for them to play another
     * game hitting any button ]
     */
    public void processButtonClick(int row, int col) {

        //Create the position
        BoardPosition pos = new BoardPosition(row, col);

        // If the game is over, reset all the values
        if(!inGame) {
            inGame = true;
            turnCounter = 0;
            this.newGame();
        }

        // Since they cannot select a row or column out of bounds, check if the space is full
        // If the space is already populated print error message and repopulate
        else if(!this.curGame.checkSpace(pos)){
            screen.setMessage("Invalid space, please select an open space");
        }
        else{

            // Get the current player's marker
            curPlayer = this.players[turnCounter % this.numPlayers];

            // Set the marker on the gameBoard(model) and the screen(view)
            curGame.placeMarker(pos, curPlayer);
            screen.setMarker(row, col, curPlayer);

            // If there is a winner, print the winning statement
            if (curGame.checkForWinner(pos)) {
                screen.setMessage("Player " + curPlayer + " has won the game, press any button to play again");
                inGame = false;
            } else if (curGame.checkForDraw()) {
                screen.setMessage("This game has ended in a draw, press any button to play again");
                inGame = false;
            } else {
                turnCounter += 1;
                curPlayer = this.players[turnCounter % this.numPlayers];
                screen.setMessage("It is " + curPlayer + "'s turn");
            }
        }
    }



    /**
     * <p>
     * This method will start a new game by returning to the setup screen and controller
     * </p>
     *
     * @post [ a new game gets started ]
     */
    private void newGame() {
        //close the current screen
        screen.dispose();

        //start back at the set up menu
        GameSetupScreen screen = new GameSetupScreen();
        GameSetupController controller = new GameSetupController(screen);
        screen.registerObserver(controller);
    }
}