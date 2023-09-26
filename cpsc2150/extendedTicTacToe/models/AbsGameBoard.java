package cpsc2150.extendedTicTacToe.models;

public abstract class AbsGameBoard implements IGameBoard {

    /**
     * This method returns a string made up of the row and column of a particular
     * position.
     *
     * @return a string made up of the entire gameBoard
     *
     * @post [GameBoard content returned in string format] AND curGameBoard = #curGameBoard
     */
    @Override
    public String toString(){

        int i, j, k;
        char curChar;
        String gridString;
        gridString = new String("   ");
        BoardPosition pos;


        for(i = 0; i < getNumColumns(); i++){
            if(i < 10) {
                gridString = gridString.concat(" " + i + "|");
            }
            else{
                gridString = gridString.concat(i + "|");
            }
        }

        gridString = gridString.concat("\n");

        for(j=0; j < getNumRows(); j++){
            if(j < 10) {
                gridString = gridString.concat(" " + j + "|");
            }
            else{
                gridString = gridString.concat(j + "|");
            }
            for (k=0; k < getNumColumns(); k++){
                pos = new BoardPosition(j, k);
                curChar = whatsAtPos(pos);
                gridString = gridString.concat(curChar + " |");
            }
            gridString = gridString.concat("\n");
        }

        return gridString;
    }

}
