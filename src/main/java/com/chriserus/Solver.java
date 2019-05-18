package main.java.com.chriserus;

import java.util.ArrayList;

public class Solver {

    private int verticalSize;
    private int horizontalSize;
    private char[][] board;
    private ArrayList<char[]> wordsToBeFound;
    private ArrayList<char[]> foundWords = new ArrayList<>();

    public Solver(char[][] board, ArrayList<char[]> arrayOfWordsAsCharArrays) {
        this.board = board;
        this.wordsToBeFound = arrayOfWordsAsCharArrays;
        verticalSize = board.length;
        horizontalSize = board[0].length;
    }

    public void solve() {
        int horizontalResult = 0;
        int verticalResult = 0;
        char[][] columns = rotateBoardCounterclockwise();
        while (!wordsToBeFound.isEmpty()) {
            horizontalResult += countFoundWords(verticalSize, board);
            verticalResult += countFoundWords(horizontalSize, columns);
        }
        System.out.println("----------SOLUTION----------");
        System.out.println("Horizontal words: " + horizontalResult);
        System.out.println("Vertical words: " + verticalResult);
    }

    private char[][] rotateBoardCounterclockwise() {
        char[][] columns = new char[horizontalSize][verticalSize];
        for (int i = 0; i < horizontalSize; i++) {
            for (int j = verticalSize - 1; j >= 0; j--) {
                columns[i][j] = board[j][i];
            }
        }
        return columns;
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
                foundWords.add(word);
                wordsToBeFound.remove(word);
                return true;
            }
        }
        return false;
    }
}
