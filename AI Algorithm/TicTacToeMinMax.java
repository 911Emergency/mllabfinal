import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TicTacToeMinMax {
    private static final int COMPUTER = 1;
    private static final int HUMAN = -1;
    private static final int EMPTY = 0;

    private int[][] board;

    public TicTacToeMinMax() {
        board = new int[][] { { EMPTY, EMPTY, EMPTY }, { EMPTY, EMPTY, EMPTY }, { EMPTY, EMPTY, EMPTY } };
    }

    public void printBoard() {
        for (int[] row : board) {
            for (int cell : row) {
                System.out.print((cell == COMPUTER) ? "O " : (cell == HUMAN) ? "X " : "- ");
            }
            System.out.println();
        }
    }

    public Integer evaluate() {
        Integer winner = checkWinner();
        if (winner != null) {
            return winner;
        } else if (isBoardFull()) {
            return 0;
        } else {
            return null;
        }
    }

    public boolean isBoardFull() {
        for (int[] row : board) {
            for (int cell : row) {
                if (cell == EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    public Integer checkWinner() {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0] != EMPTY) {
                return board[i][0];
            }
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[0][i] != EMPTY) {
                return board[0][i];
            }
        }

        if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != EMPTY) {
            return board[0][0];
        }
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] != EMPTY) {
            return board[0][2];
        }

        return null;
    }

    public List<int[]> getAvailableMoves() {
        List<int[]> availableMoves = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == EMPTY) {
                    availableMoves.add(new int[] { i, j });
                }
            }
        }
        return availableMoves;
    }

    public int minimaxAlphaBeta(int depth, int alpha, int beta, boolean maximizingPlayer) {
        Integer result = evaluate();
        if (result != null) {
            return result;
        }

        if (maximizingPlayer) {
            int maxEval = Integer.MIN_VALUE;
            for (int[] move : getAvailableMoves()) {
                int i = move[0];
                int j = move[1];
                board[i][j] = COMPUTER;

                int eval = minimaxAlphaBeta(depth - 1, alpha, beta, false);

                board[i][j] = EMPTY;
                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(alpha, eval);
                if (beta <= alpha) {
                    break;
                }
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (int[] move : getAvailableMoves()) {
                int i = move[0];
                int j = move[1];
                board[i][j] = HUMAN;

                int eval = minimaxAlphaBeta(depth - 1, alpha, beta, true);

                board[i][j] = EMPTY;
                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, eval);
                if (beta <= alpha) {
                    break;
                }
            }
            return minEval;
        }
    }

    public int[] findBestMove() {
        int bestVal = Integer.MIN_VALUE;
        int[] bestMove = null;

        for (int[] move : getAvailableMoves()) {
            int i = move[0];
            int j = move[1];
            board[i][j] = COMPUTER;

            int moveVal = minimaxAlphaBeta(0, Integer.MIN_VALUE, Integer.MAX_VALUE, false);

            board[i][j] = EMPTY;

            if (moveVal > bestVal) {
                bestMove = move;
                bestVal = moveVal;
            }
        }

        return bestMove;
    }

    public static void main(String[] args) {
        TicTacToeMinMax game = new TicTacToeMinMax();
        game.printBoard();

        Scanner scanner = new Scanner(System.in);

        while (!game.isBoardFull() && game.checkWinner() == null) {
            System.out.print("Enter your move (row and column, separated by space): ");
            int x = scanner.nextInt();
            int y = scanner.nextInt();

            if (x < 0 || x >= 3 || y < 0 || y >= 3) {
                System.out.println("Invalid move! Row and column must be between 0 and 2. Try again.");
                continue;
            }

            if (game.board[x][y] == game.EMPTY) {
                game.board[x][y] = game.HUMAN;
            } else {
                System.out.println("Invalid move! The cell is already occupied. Try again.");
                continue;
            }

            game.printBoard();

            if (game.isBoardFull() || game.checkWinner() != null) {
                break;
            }

            System.out.println("Thinking...");
            int[] bestMove = game.findBestMove();
            game.board[bestMove[0]][bestMove[1]] = game.COMPUTER;

            game.printBoard();
        }

        Integer winner = game.checkWinner();
        if (winner != null) {
            if (winner == game.COMPUTER) {
                System.out.println("You lose! The computer wins.");
            } else if (winner == game.HUMAN) {
                System.out.println("Congratulations! You win.");
            }
        } else {
            System.out.println("It's a tie!");
        }
    }

}
