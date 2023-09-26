package cpsc2150.extendedTicTacToe.models;

/**
 * This class is used to keep track of an individual cell on the board
 * @author Mauricio Fortune
 *
 * @invariant row >= 0 AND row <= 4 AND column >= 0 AND column <= 7
 *
 */
public class BoardPosition {

    private final int row;
    private final int column;

    /**
     * This constructor creates a position with a specified row and column
     *
     * @param r is the chosen row.
     * @param c is the chosen column.
     *
     * @pre r >= 0 AND r <= 4 AND c >= 0 AND c <= 7
     * @post row = r AND column = c
     *
     */
    public BoardPosition(int r, int c){
        row = r;
        column = c;

    }

    /**
     * This method returns the row value
     *
     * @return the value of the row variable.
     *
     * @pre [row must be initialized]
     * @post row = #row AND column = #column
     */
    public int getRow(){

        return row;
    }

    /**
     * This method returns the column value
     *
     * @return the value of the column variable
     *
     * @pre [column must be initialized]
     * @post column = #column AND row = #row
     */
    public int getColumn(){

        return column;
    }

    /**
     * This method returns true if two position are equal meaning they have the same
     * row and column. It returns false if the positions are not equal.
     *
     * @param o Object holding a position that we will be comparing.
     *
     * @return A bool that is true if they are the same position or false if they are
     *         different positions.
     *
     * @pre 0 <= o.getRow() <= 4 AND 0 <= o.getColumn() <= 7 AND 0 <= this.getRow() <= 4
     *      AND 0 <= this.getColumn() <= 7
     * @post row = #row AND column = #column
     */
    @Override
    public boolean equals(Object o){

        if (!(o instanceof BoardPosition)) {
            return false;
        }
        BoardPosition other = (BoardPosition) o;
        if(this.getRow() == other.getRow() && this.getColumn() == other.getColumn()) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * This method returns a string made up of the row and column of a particular
     * position.
     *
     * @return a string made up "<row>,<column>".
     *
     * @pre [this.getRow() and this.getColumn() must be initialized] AND
     *      0 <= this.getRow() <= 4 AND 0 <= this.getColumn() <= 7
     * @post row = #row AND column = #column
     */
    @Override
    public String toString(){

        return this.getRow() + "," + this.getColumn();
    }
}

