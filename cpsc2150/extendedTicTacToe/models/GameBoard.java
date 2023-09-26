package cpsc2150.extendedTicTacToe.models;

/**
 * This class will contain our game board for the fast implementation
 *
 * @author Mauricio Fortune
 * @invariant Every spot in GameBoard should be ' ' unless played on
 *          AND
 *          if a spot in GameBoard gets played on it should by the current player's marker AND
 *          GameBoard should be of size numRows x numColumns AND
 *          MIN_ROWS < numRows < MAX_ROWS AND
 *          MIN_COLS < numCols < MAX_COLS
 * @correspondence
 *              number_of_rows = numRows
 *              number_of_columns = numCols
 *              self = curGameBoard[0...numRows-1][0...numCols-1]
 */
public class GameBoard extends AbsGameBoard implements IGameBoard {
    private int numRows;
    private int numColumns;
    private int numToWin;
    private static char curGameBoard [][];

    /**
     * This constructor creates an empty gameBoard
     * @param nR: is the number of rows chosen by the player
     * @param nC: is the number of columns chosen by the player
     * @param nTW: is the num to win which is chosen by the player
     *
     * @pre MIN_ROWS < nR < MAX_ROWS AND
     *      MIN_Cols < nC < MIN_COLS AND
     *      minNTW < numTW < maxNTW
     *      num_to_win < num_rows OR numToWin < num_cols [whichever is greater]
     *
     * @post [curGameBoard initialized to ' '] AND numRows = nR AND numColumns = nC AND numToWin = nTW
     */
    public GameBoard(int nR, int nC, int nTW){
        numRows = nR;
        numColumns = nC;
        numToWin = nTW;
        curGameBoard = new char [numRows][numColumns];

        // Initialize all spots in deck to ' '
        for(int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                curGameBoard[i][j] = ' ';
            }
        }

    }

    public int getNumRows(){

        return numRows;
    }
    public int getNumColumns(){

        return numColumns;
    }
    public int getNumToWin(){

        return numToWin;
    }

    public void placeMarker(BoardPosition pos, char player){

        curGameBoard[pos.getRow()][pos.getColumn()] = player;
    }

    public char whatsAtPos(BoardPosition pos){

        return curGameBoard[pos.getRow()][pos.getColumn()];
    }

}
