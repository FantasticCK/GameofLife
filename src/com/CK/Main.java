package com.CK;

import java.util.Arrays;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
        int[] a = {0, 1, 0};
        int[] b = {0, 0, 1};
        int[] c = {1, 1, 1};
        int[] d = {0, 0, 0};
        int[][] board = {a, b, c, d};

        Solution solution = new Solution();
        solution.gameOfLife(board);
        System.out.println(Arrays.deepToString(board));
    }
}

class Solution {
    int[][] originalBoard;
    int[] directX = {0, 1, 0, -1, 1, -1, 1, -1};
    int[] directY = {1, 0, -1, 0, 1, 1, -1, -1};

    public void gameOfLife(int[][] board) {
        if (board.length == 0 || board[0].length == 0) return;
        originalBoard = new int[board.length][board[0].length];
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                originalBoard[r][c] = board[r][c];
            }
        }

        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                int curr = board[r][c];
                int live = liveNeighbors(originalBoard, r, c);
                if (curr == 0 && live == 3) board[r][c] = 1;
                else if (curr == 1) {
                    if (live < 2) board[r][c] = 0;
                    else if (live == 2 || live == 3) board[r][c] = 1;
                    else board[r][c] = 0;
                }
            }
        }
    }

    private int liveNeighbors(int[][] board, int r, int c) {
        int count = 0;
        for (int i = 0; i < 8; i++) {
            int[] origin = {r, c};
            int nextR = r + directX[i];
            int nextC = c + directY[i];
            int[] next = {nextR, nextC};
            if (nextR >= 0 && nextR < board.length && nextC >= 0 && nextC < board[0].length) {
                if (originalBoard[nextR][nextC] == 1) count++;
            }
        }
        return count;
    }
}

//Using 2 bits
class Solution2 {
    public void gameOfLife(int[][] board) {
        if (board == null || board.length == 0) return;
        int m = board.length, n = board[0].length;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int lives = liveNeighbors(board, m, n, i, j);

                // In the beginning, every 2nd bit is 0;
                // So we only need to care about when will the 2nd bit become 1.
                if (board[i][j] == 1 && lives >= 2 && lives <= 3) {
                    board[i][j] = 3; // Make the 2nd bit 1: 01 ---> 11
                }
                if (board[i][j] == 0 && lives == 3) {
                    board[i][j] = 2; // Make the 2nd bit 1: 00 ---> 10
                }
            }
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] >>= 1;  // Get the 2nd state.
            }
        }
    }

    public int liveNeighbors(int[][] board, int m, int n, int i, int j) {
        int lives = 0;
        for (int x = Math.max(i - 1, 0); x <= Math.min(i + 1, m - 1); x++) {
            for (int y = Math.max(j - 1, 0); y <= Math.min(j + 1, n - 1); y++) {
                lives += board[x][y] & 1;
            }
        }
        lives -= board[i][j] & 1;
        return lives;
    }
}