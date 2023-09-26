package cpsc2150.extendedTicTacToe.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class will contain our game board for the memory implementation
 *
 * @author Mauricio Fortune
 * @invariant
 *           GameBoard should be the size of the player's choosing AND
 *           If a spot in GameBoard gets played on it should by the current player's marker AND
 *           GameBoard should be of size numRows x numColumns AND
 *           MIN_ROWS < numRows < MAX_ROWS AND
 *           MIN_COLS < numCols < MAX_COLS
 *
 * @correspondence
 *              self = Map<Character, List<BoardPosition>>
 *              number_of_rows = numRows
 *              number_of_columns = numCols
 *              self = curGameBoard[0...numRows-1][0...numCols-1]
 */
public class GameBoardMem extends AbsGameBoard implements IGameBoard{

    private int numRows;
    private int numColumns;
    private int numToWin;
    public Map<Character, List<BoardPosition>> myMap = new HashMap<Character, List<BoardPosition>>();


    /**
     * This constructor sets the dimensions of the current gameBoard
     * @param nR: the number of rows chosen by the user
     * @param nC: the number of columns chosen by the user
     * @param nTW: the number to win chosen by the user
     * @pre MIN_ROWS < nR < MAX_ROWS AND
     *      MIN_Cols < nC < MIN_COLS AND
     *      minNTW < numTW < maxNTW
     *      num_to_win < num_rows OR numToWin < num_cols [whichever is greater]
     * @post
     *      [Map is initially empty]
     *      numRows = #nR AND numColumns = #nC AND numToWin = #nTW
     */
    public GameBoardMem(int nR, int nC, int nTW){
        numRows = nR;
        numColumns = nC;
        numToWin = nTW;
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

        // If the player is in the map
        if(myMap.containsKey(player)){

            // Get the map of the particular player
            List<BoardPosition> myList = myMap.get(player);

            // Add the position to the list of boardPositions
            myList.add(pos);

            // Add the new list to the
            myMap.put(player,myList);
            return;
        }
        else{
            List<BoardPosition> myList = new ArrayList <BoardPosition>();
            myList.add(pos);
            myMap.put(player,myList);
            return;
        }

    }
    public char whatsAtPos(BoardPosition pos){

        // For each entry in the map check the
        for(Map.Entry<Character, List<BoardPosition>> m: myMap.entrySet()){
            if(m.getValue().contains(pos)){
                return m.getKey();
            }
            else{
                continue;
            }
        }
        return ' ';
    }

    @Override
    public boolean isPlayerAtPos(BoardPosition pos, char player) {
        for(Map.Entry<Character, List<BoardPosition>> m: myMap.entrySet()){
            if(m.getValue().contains(pos)){
                if(m.getKey().equals(player)){
                    return true;
                }
            }
            else{
                continue;
            }
        }
        return false;
    }
}
