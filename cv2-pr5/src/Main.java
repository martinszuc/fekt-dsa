import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Define the start state of the puzzle (2D array)
        int[][] startState = {
                {1, 0, 2},
                {3, 4, 5},
                {6, 7, 8}

//                {7, 2, 4},
//                {5, 0, 6},
//                {8, 3, 1}
        };

        GameBoard gameBoard = new GameBoard(startState);

        System.out.println("Initial Board:");
        gameBoard.printBoard();

        Scanner scanner = new Scanner(System.in);
        int moveCounter = 0;

        while (true) {
            System.out.println("Enter a move (5 = up, 2 = down, 1 = left, 3 = right, 0 to quit): ");
            int move = scanner.nextInt();
            if (move == 0) {
                break; // Quit the game
            }

            boolean moved = gameBoard.move(move);
            if (moved) {
                moveCounter++;
                System.out.println("Board after move:");
                gameBoard.printBoard();
                // Check if the game is won
                if (gameBoard.isSolved()) {
                    System.out.println("Congratulations! You've solved the puzzle!");
                    break; // Exit the game after winning
                }
            } else {
                System.out.println("Invalid move! Try again.");
            }
        }

        System.out.println("Game over. You made " + moveCounter + " moves.");
    }
}