package cpsc2150.extendedTicTacToe.models;

/**
 * This is an interface that holds the functions that are necessary in order for a GameBoard
 * to be functional.
 *
 * Initialization Ensures:
 *       GameBoard contains only blank characters and is MAX_ROWS x MAX_COLS or
 *       smaller
 *
 *
 * Defines:
 *      num_rows : z
 *      num_cols : z
 *      num_to_win : z
 *
 * Constraints:
 *      minRows < num_rows < maxRows
 *      minCols < num_cols < maxCols
 *      minNTW < num_to_win < maxNTW
 *      num_to_win < num_rows OR numToWin < num_cols [whichever is greater]
 *
 */

public interface IGameBoard {

    public static final int MAX_ROWS = 100;
    public static final int MIN_ROWS = 3;

    public static final int MAX_COLUMNS = 100;
    public static final int MIN_COLUMNS = 3;

    public static final int MAX_NUM_TO_WIN = 25;
    public static final int MIN_NUM_TO_WIN = 3;



    //primary:

    /**
     * Returns the number of rows in the GameBoard
     * @return The number of rows that the grid will have
     *
     * @post getNumRows = nR AND nR = #nR AND nC = #nC AND nTW = #nTW
     */
    public int getNumRows();

    /**
     * Returns the number of rows in the GameBoard
     * @return The number of columns that the grid will have
     *
     * @post getNumColumns = nC AND nR = #nR AND nC = #nC AND nTW = #nTW
     */
    public int getNumColumns();

    /**
     * Returns the number of tokens in a row needed to win the game
     * @return The number of markers in a row needed to win
     *
     * @post getNumToWin = nTW AND nR = #nR AND nC = #nC AND nTW = #nTW
     */
    public int getNumToWin();

    /**
     * Places the character in marker on the position specified by marker
     * @param pos the precise row and column of a position
     * @param player the variable representing the character of the current player
     *
     * @pre checkSpace(pos) = True
     * @post curGameBoard[pos.getRow()][pos.getColumn()] = player AND [rest of board does not change
     * just the one position in the gameBoard]
     */
    public void placeMarker(BoardPosition pos, char player);

    /**
     * returns what is in the GameBoard at position pos
     * @param pos the precise row and column of a position
     * @return the character in the precise position in the curGameBoard
     *
     * @pre 0 <= pos.getRow() < MAX_ROWS AND 0 <= pos.getColumn() < MAX_COLUMNS
     * @post curGameBoard = #curGameBoard AND curGameBoard[pos.getRow()][pos.getColumn()] = marker [If the position is filled]
     * AND curGameBoard[pos.getRow()][pos.getColumn()] == ' ' [If the position is empty]
     */
    public char whatsAtPos(BoardPosition pos);

    //Secondary
    /**
     * Checks to see if a space has been populated by a player or not
     * @param pos contains the precise row and column of the position
     *
     * @return True or False: True meaning the space is available and False if it has been used or out of bounds
     *
     * @pre 0 <= pos.getRow() < MAX_ROWS AND 0 <= pos.getColumn() < MAX_COLUMNS
     * @post curGameBoard[pos.getRow()][pos.getColumn()] = ' ' AND curGameBoard = #curGameBoard
     */

    default boolean checkSpace(BoardPosition pos){
        if((pos.getRow() > getNumRows() - 1 || pos.getRow() < 0)||(pos.getColumn() > getNumColumns() - 1 || pos.getColumn() < 0)){
            return false;
        }
        else {
            return (whatsAtPos(pos) == ' ');
        }
    }


    /**
     * This function will check to see if the lastPos placed resulted in a winner
     * @param lastPos the precise row and column of a position of the previous move
     * @return True or False: True if that placement resulted in a winner, False if that placement did not result in a winner
     *
     * @pre 0 <= pos.getRow() < MAX_ROWS AND 0 <= pos.getColumn() < MAX_COLUMNS
     * @post curGameBoard = #curGameBoard AND [Check if last position is equal to NUM_TO_WIN Horizontally, Vertically, and Diagonally]
     */
    default boolean checkForWinner(BoardPosition lastPos){
        char marker = whatsAtPos(lastPos);
        //Check to see if X or O got a horizontal win
        if(checkHorizontalWin(lastPos, marker) == true){
            return true;
        }

        //Check to see if X or O got a vertical win
        else if(checkVerticalWin(lastPos, marker) == true){
            return true;
        }

        //Check to see if X or O got a diagonal win
        if(checkDiagonalWin(lastPos, marker) == true){
            return true;
        }

        //Nobody got a win, return false
        return false;

    }

    /**
     * This function will check to see if the game has resulted in a tie
     * @return True or False: True if the match resulted in a draw, False if match has not resulted in a draw
     *
     * @pre [Check for winner called after every marker was placed and did not return true]
     * @post curGameBoard = #curGameBoard AND checkForDraw = true [no free board positions remaining and no win]
     */
    default boolean checkForDraw(){

        // Variables used to track the current position and which player is in there
        char curMarker;
        BoardPosition curPos;

        // For each row check all the columns
        for(int i = 0; i < getNumRows(); i++){
            for(int j = 0; j < getNumColumns(); j++){

                // At this position, create a position and check what marker is there
                curPos = new BoardPosition(i, j);
                curMarker = whatsAtPos(curPos);

                // If it is a space, then the board is not filled, so it is not a tie, return false
                if(curMarker == ' '){
                    return false;
                }

                // If the space is filled, continue checking
                else{
                    continue;
                }
            }
        }

        // Went through all the spaces and checked, they are all full
        return true;


    }

    /**
     * Checks to see if the last marker placed resulted in 5 in a row horizontally
     * @param lastPos the precise row and column of a position of the previous move
     * @param player the variable representing the character of the current player
     * @return true if there is a horizontal win and false if it is not a horizontal win
     *
     * @pre 0 <= pos.getRow() < MAX_ROWS AND 0 <= pos.getColumn() < MAX_COLUMNS
     * @post curGameBoard = #curGameBoard AND checkHorizontalWin = true [if amount of same markers horizontally = NUM_TO_WIN]
     */
    default boolean checkHorizontalWin(BoardPosition lastPos, char player){

        // Counter variable used to track how many we have in a row
        int counter = 0;

        // Find out what is at the current position
        char curMarker;

        // Shifts us through the column to see how many we have in a row
        int columnChecker = lastPos.getColumn();
        BoardPosition pos;

        // First check to the right, make sure it does not exceed the size of the gameBoard
        while( columnChecker < getNumColumns()){

            // create a new position. Stays in the same row but increases column each time
            // Get what is at this position
            pos = new BoardPosition(lastPos.getRow(), columnChecker);
            curMarker = whatsAtPos(pos);

            // If the marker in this position is the marker we are seeking
            if(curMarker == player){

                // Increase the checker to check the next column
                columnChecker += 1;

                // Add one more to the amount we have in a row
                counter += 1;

                // If it isn't the player we are looking for, check in the other direction
            }else{

                break;
            }

            // If they have enough in a row, they won!
            if(counter >= getNumToWin()){
                return true;
            }else{
                continue;
            }

        }

        // Make the new checker 1 column to the left of the position they started at
        columnChecker = lastPos.getColumn() - 1;

        // Check to the left
        while(columnChecker >= 0){

            // create a new position. Stays in the same row but decreases column each time
            // Get what is at this position
            pos = new BoardPosition(lastPos.getRow(), columnChecker);
            curMarker = whatsAtPos(pos);

            // If the marker in this position is the marker we are seeking
            if(curMarker == player){

                // Decrease the checker to check the next column
                columnChecker -= 1;

                // Add one more to the amount we have in a row
                counter += 1;

            }
            else{

                break;
            }

            // If they have enough in a row, they won!
            if(counter >= getNumToWin()){
                return true;
            }
            else{
                continue;
            }

        }

        // Went through all possible positions and they did not have enough in a row to win.
        return false;

    }

    /**
     * Checks to see if the last marker placed resulted in 5 in a row vertically
     * @param lastPos the precise row and column of a position of the previous move
     * @param player the variable representing the character of the current player
     * @return true if there is a vertical win and false if it is not a vertical win
     *
     * @pre 0 <= pos.getRow() < MAX_ROWS AND 0 <= pos.getColumn() < MAX_COLUMNS
     * @post curGameBoard = #curGameBoard AND checkVerticalWin = true [if amount of same markers Vertically = NUM_TO_WIN]
     */
    default boolean checkVerticalWin(BoardPosition lastPos, char player){


        // Counter variable used to track how many we have in a column
        int counter = 0;

        // Find out what is at the current position
        char curMarker;

        // Shifts us through the rows to see how many we have in a row
        int rowChecker = lastPos.getRow();
        BoardPosition pos;

        // First check to below, make sure it does not exceed the size of the gameBoard
        while( rowChecker < getNumRows()){

            // create a new position. Stays in the same column but row column each time
            // Get what is at this position
            pos = new BoardPosition(rowChecker, lastPos.getColumn());
            curMarker = whatsAtPos(pos);

            // If the marker in this position is the marker we are seeking
            if(curMarker == player){

                // Increase the checker to check the next row
                rowChecker += 1;

                // Add one more to the amount we have in a column
                counter += 1;

                // If it isn't the player we are looking for, check in the other direction
            }else{
                break;
            }

            // If they have enough in a column, they won!
            if(counter >= getNumToWin()){
                return true;
            }else{
                continue;
            }

        }

        // Make the new checker 1 row to the above of the position they started at
        rowChecker = lastPos.getRow() - 1;

        // Check to the left
        while(rowChecker >= 0){

            // create a new position. Stays in the same column but decreases row each time
            // Get what is at this position
            pos = new BoardPosition(rowChecker, lastPos.getColumn());
            curMarker = whatsAtPos(pos);

            // If the marker in this position is the marker we are seeking
            if(curMarker == player){

                // Decrease the checker to check the next row
                rowChecker -= 1;

                // Add one more to the amount we have in a column
                counter += 1;
            }
            else{
                break;
            }

            // If they have enough in a column, they won!
            if(counter >= getNumToWin()){
                return true;
            }
            else{
                continue;
            }

        }

        // Went through all possible positions, and they did not have enough in a column to win.
        return false;

    }

    /**
     * Checks to see if the last marker placed resulted in 5 in a row diagonally
     * @param lastPos the precise row and column of a position of the previous move
     * @param player the variable representing the character of the current player
     * @return true if there is a vertical win and false if it is not a vertical win
     *
     * @pre 0 <= pos.getRow() < MAX_ROWS AND 0 <= pos.getColumn() < MAX_COLUMNS (lastpos.get row in bounds and same for column
     * @post curGameBoard = #curGameBoard AND checkDiagonalWin = true [if amount of same markers diagonally = NUM_TO_WIN]
     */
    default boolean checkDiagonalWin(BoardPosition lastPos, char player){

        // Counter variable used to track how many we have in a row
        int counter = 0;

        // Find out what is at the current position
        char curMarker;

        // Shifts us through the column to see how many we have in a row
        int columnChecker = lastPos.getColumn();

        // Shifts us through the rows to see how many we have in a row
        int rowChecker = lastPos.getRow();

        BoardPosition pos;

        // First check to the SE, make sure it does not exceed the size of the gameBoard
        while(columnChecker < getNumColumns() && rowChecker < getNumRows()){

            // create a new position. increases row and column each time
            // Get what is at this position
            pos = new BoardPosition(rowChecker, columnChecker);
            curMarker = whatsAtPos(pos);

            // If the marker in this position is the marker we are seeking
            if(curMarker == player){

                // Increase the checkers to check the next position SE
                columnChecker += 1;
                rowChecker += 1;

                // Add one more to the amount we have in a row
                counter += 1;

                // If it isn't the player we are looking for, check in the other direction
            }else{
                break;
            }

            // If they have enough in a diagonal, they won!
            if(counter >= getNumToWin()){
                return true;
            }else{
                continue;
            }
        }

        // Make the new checkers start at the NW of the original pos
        columnChecker = lastPos.getColumn() - 1;
        rowChecker = lastPos.getRow() - 1;

        // Check to the NW
        while(columnChecker >= 0 && rowChecker >= 0){

            // create a new position. Decreases row and column each time
            // Get what is at this position
            pos = new BoardPosition(rowChecker, columnChecker);
            curMarker = whatsAtPos(pos);

            // If the marker in this position is the marker we are seeking
            if(curMarker == player){

                // Decrease the checkers to check the next position NW
                columnChecker -= 1;
                rowChecker -= 1;

                // Add one more to the amount we have in a row
                counter += 1;
            }
            else{
                break;
            }

            // If they have enough in a diagonal, they won!
            if(counter >= getNumToWin()){
                return true;
            }
            else{
                continue;
            }

        }


        // Reset all the variables to begin checking the diagonal from SW to NE
        counter = 0;
        columnChecker = lastPos.getColumn();
        rowChecker = lastPos.getRow();

        // Make sure it isn't off the grid, CHECKING NE
        while(rowChecker >= 0 && columnChecker < getNumColumns()){

            // create a new position. increases row and column each time
            // Get what is at this position
            pos = new BoardPosition(rowChecker, columnChecker);
            curMarker = whatsAtPos(pos);

            // If the marker in this position is the marker we are seeking
            if(curMarker == player){

                // Increase the checkers to check the next position NE
                columnChecker += 1;
                rowChecker -= 1;

                // Add one more to the amount we have in a row
                counter += 1;


            }
            // If it isn't the player we are looking for, check in the other direction
            else{
                break;
            }

            // If they have enough in a diagonal, they won!
            if(counter >= getNumToWin()){
                return true;
            }else{
                continue;
            }
        }

        // Make the new checkers start at the SW of the original pos
        columnChecker = lastPos.getColumn() - 1;
        rowChecker = lastPos.getRow() + 1;

        // Check to the SW
        while(rowChecker < getNumRows() && columnChecker >= 0){

            // create a new position. Decreases row and column each time
            // Get what is at this position
            pos = new BoardPosition(rowChecker, columnChecker);
            curMarker = whatsAtPos(pos);

            // If the marker in this position is the marker we are seeking
            if(curMarker == player){

                // Decrease the checkers to check the next position SW
                columnChecker -= 1;
                rowChecker += 1;

                // Add one more to the amount we have in a row
                counter += 1;
            }
            else{
                break;
            }

            // If they have enough in a diagonal, they won!
            if(counter >= getNumToWin()){
                return true;
            }
            else{
                continue;
            }

        }

        // Went through all possible positions, and they did not have enough in a diagonal to win
        return false;

    }

    /**
     * returns what is in the GameBoard at position pos
     * @param pos the precise row and column of a position
     * @param player the variable representing the character of the current player
     * @return returns true if the player is at pos; otherwise, it returns false
     *
     * @pre 0 <= pos.getRow() < MAX_ROWS AND 0 <= pos.getColumn() < MAX_COLUMNS
     * @post curGameBoard = #curGameBoard AND curGameBoard[pos.getRow()][pos.getColumn()] = player
     */
    default boolean isPlayerAtPos(BoardPosition pos, char player){

        char curMarker;
        curMarker = whatsAtPos(pos);
        if(curMarker == player){
            return true;
        }
        else{
            return false;
        }
    }

}
