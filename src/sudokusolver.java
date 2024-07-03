import java.util.Random;

public class SudokuSolver {
    private static final int SIZE = 9; // Size of the Sudoku grid
    private static final int EMPTY = 0; // Empty cells are denoted by 0
    private static final int DIFFICULTY = 40; // Number of cells to remove for the puzzle

    public static void main(String[] args) {
        int[][] board = generateSudokuPuzzle();
        System.out.println("Generated Sudoku Puzzle:");
        printBoard(board);

        if (solveSudoku(board)) {
            System.out.println("\nSolved Sudoku:");
            printBoard(board);
        } else {
            System.out.println("No solution exists");
        }
    }

    private static boolean solveSudoku(int[][] board) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board[row][col] == EMPTY) {
                    for (int num = 1; num <= SIZE; num++) {
                        if (isSafe(board, row, col, num)) {
                            board[row][col] = num;
                            if (solveSudoku(board)) {
                                return true;
                            } else {
                                board[row][col] = EMPTY;
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean isSafe(int[][] board, int row, int col, int num) {
        for (int x = 0; x < SIZE; x++) {
            if (board[row][x] == num || board[x][col] == num) {
                return false;
            }
        }

        int startRow = row - row % 3, startCol = col - col % 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i + startRow][j + startCol] == num) {
                    return false;
                }
            }
        }

        return true;
    }

    private static int[][] generateSudokuPuzzle() {
        int[][] board = new int[SIZE][SIZE];
        fillDiagonalBlocks(board);
        fillRemaining(board, 0, 3);
        removeNumbers(board);
        return board;
    }

    private static void fillDiagonalBlocks(int[][] board) {
        for (int i = 0; i < SIZE; i += 3) {
            fillBlock(board, i, i);
        }
    }

    private static void fillBlock(int[][] board, int row, int col) {
        Random random = new Random();
        boolean[] used = new boolean[SIZE + 1];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int num;
                do {
                    num = random.nextInt(SIZE) + 1;
                } while (used[num]);
                used[num] = true;
                board[row + i][col + j] = num;
            }
        }
    }

    private static boolean fillRemaining(int[][] board, int row, int col) {
        if (row >= SIZE && col >= SIZE) {
            return true;
        }

        if (col >= SIZE) {
            row++;
            col = 0;
        }

        if (board[row][col] != EMPTY) {
            return fillRemaining(board, row, col + 1);
        }

        for (int num = 1; num <= SIZE; num++) {
            if (isSafe(board, row, col, num)) {
                board[row][col] = num;
                if (fillRemaining(board, row, col + 1)) {
                    return true;
                }
                board[row][col] = EMPTY;
            }
        }

        return false;
    }

    private static void removeNumbers(int[][] board) {
        Random random = new Random();
        int count = DIFFICULTY;
        while (count != 0) {
            int cellId = random.nextInt(SIZE * SIZE);
            int row = cellId / SIZE;
            int col = cellId % SIZE;

            if (board[row][col] != EMPTY) {
                board[row][col] = EMPTY;
                count--;
            }
        }
    }

    private static void printBoard(int[][] board) {
        for (int r = 0; r < SIZE; r++) {
            for (int d = 0; d < SIZE; d++) {
                System.out.print(board[r][d] + " ");
            }
            System.out.println();
        }
    }
}
