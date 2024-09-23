public class GameBoard {
    private int[][] board;
    private int emptyRow, emptyCol;
    private final int[][] goalState = {
            {0, 1, 2},
            {3, 4, 5},
            {6, 7, 8}
    };

    public GameBoard(int[][] initialBoard) {
        this.board = initialBoard;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == 0) {
                    emptyRow = i;
                    emptyCol = j;
                }
            }
        }
    }

    public boolean move(int direction) {
        int newRow = emptyRow, newCol = emptyCol;

        switch (direction) {
            case 5: // Move up
                newRow--;
                break;
            case 2: // Move down
                newRow++;
                break;
            case 1: // Move left
                newCol--;
                break;
            case 3: // Move right
                newCol++;
                break;
            default:
                return false; // Invalid direction
        }

        if (newRow >= 0 && newRow < board.length && newCol >= 0 && newCol < board[0].length) {
            board[emptyRow][emptyCol] = board[newRow][newCol];
            board[newRow][newCol] = 0;
            emptyRow = newRow;
            emptyCol = newCol;
            return true;
        }
        return false; // Move was out of bounds
    }

    // Check if the current board matches the goal state
    public boolean isSolved() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] != goalState[i][j]) {
                    return false; // If any tile is not in the correct position
                }
            }
        }
        return true; // All tiles are in the correct position
    }

    // Method to print the current board state
    public void printBoard() {
        for (int[] row : board) {
            for (int tile : row) {
                System.out.print(tile + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}