package com.safealert;

public class SafeAlertLogic {

    private char[][] board;
    private boolean xTurn;
    private int movesCount;
    private boolean alertaTrimisa;

    public SafeAlertLogic() {
        board = new char[3][3];
        reset();
    }

    public void reset() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                board[i][j] = ' ';
        xTurn = true;
        movesCount = 0;
        alertaTrimisa = false;
    }

    public void makeMove(int row, int col, String player) {
        if (row < 0 || row > 2 || col < 0 || col > 2 || board[row][col] != ' ')
            return;

        board[row][col] = player.charAt(0);
        xTurn = !xTurn;
        movesCount++;
    }

    public boolean isGameOver() {
        return checkWinner() != ' ';
    }

    public boolean hasWinner() {
        char winner = checkWinner();
        return winner == 'X' || winner == 'O';
    }

    public boolean isDraw() {
        return checkWinner() == 'D';
    }

    public boolean alertaDeTrimis() {
        return !alertaTrimisa && movesCount == 3;
    }

    public void marcheazaAlertaTrimisa() {
        alertaTrimisa = true;
    }

    public char checkWinner() {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != ' ' &&
                board[i][0] == board[i][1] &&
                board[i][1] == board[i][2])
                return board[i][0];

            if (board[0][i] != ' ' &&
                board[0][i] == board[1][i] &&
                board[1][i] == board[2][i])
                return board[0][i];
        }

        if (board[0][0] != ' ' &&
            board[0][0] == board[1][1] &&
            board[1][1] == board[2][2])
            return board[0][0];

        if (board[0][2] != ' ' &&
            board[0][2] == board[1][1] &&
            board[1][1] == board[2][0])
            return board[0][2];

        if (movesCount == 9)
            return 'D';

        return ' ';
    }
}
