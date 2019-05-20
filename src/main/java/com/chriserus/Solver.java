package main.java.com.chriserus;

import java.util.ArrayList;

public class Solver {
    private Board board;
    private ArrayList<char[]> wordsToBeFound;

    public Solver(Board board, ArrayList<char[]> wordsToBeFound) {
        this.board = board;
        this.wordsToBeFound = wordsToBeFound;
    }

    public void solve() {
        int horizontalResult = 0;
        int verticalResult = 0;
        Board rotatedBoard = board.getBoardRotatedCounterclockwise();
        while (!wordsToBeFound.isEmpty()) {
            horizontalResult += countFoundWords(board.getVerticalSize(), board.getPanel());
            verticalResult += countFoundWords(board.getHorizontalSize(), rotatedBoard.getPanel());
        }
        System.out.println("----------SOLUTION----------");
        System.out.println("Horizontal words: " + horizontalResult);
        System.out.println("Vertical words: " + verticalResult);
    }

    private int countFoundWords(int size, char[][] array) {
        int result = 0;
        for (int i = 0; i < size; i++) {
            if (findWordInRow(array[i]))
                result++;
        }
        return result;
    }

    private boolean findWordInRow(char[] text) {
        for (char[] word : wordsToBeFound) {
            BoyerMoore boyerMoore = new BoyerMoore(word, 500);
            if (boyerMoore.search(text) != text.length) {
                wordsToBeFound.remove(word);
                return true;
            }
        }
        return false;
    }
}
