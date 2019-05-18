package main.java.com.chriserus;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Game {
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    private List<String> words;
    private ArrayList<char[]> arrayOfWordsAsCharArrays;
    private int verticalSize;
    private int horizontalSize;
    private char[][] board;
    private char[][] blockedBoard;

    public Game(List<String> words, int horizontalSize, int verticalSize) {
        this.words = words;
        this.verticalSize = verticalSize;
        this.horizontalSize = horizontalSize;
    }

    public void preparePuzzle() {
        System.out.println(words);
        placeWordsInArrays();
    }

    private void placeWordsInArrays() {
        arrayOfWordsAsCharArrays = new ArrayList<>();
        words.forEach(word -> arrayOfWordsAsCharArrays.add(word.toCharArray()));
        prepareBoard();
    }

    private void prepareBoard() {
        board = new char[verticalSize][horizontalSize];
        blockedBoard = new char[verticalSize][horizontalSize];
        fillBoardWithRandomChars();
        placeAllWordsOnTheBoard();
        printBoard(board);
        System.out.println("---------WORDS ON BOARD---------");
        printBoard(blockedBoard);
    }

    private void placeAllWordsOnTheBoard() {
        for (char[] word : arrayOfWordsAsCharArrays) {
            int roll = (int) Math.round(Math.random());
            switch (roll) {
                case 0:
                    placeWordVertically(arrayOfWordsAsCharArrays.get(arrayOfWordsAsCharArrays.indexOf(word)));
                    break;
                case 1:
                    placeWordHorizontally(arrayOfWordsAsCharArrays.get(arrayOfWordsAsCharArrays.indexOf(word)));
                    break;
            }
        }
    }

    private void fillBoardWithRandomChars() {
        Random r = new Random();
        for (int i = 0; i < verticalSize; i++) {
            for (int j = 0; j < horizontalSize; j++) {
                board[i][j] = ALPHABET.charAt(r.nextInt(ALPHABET.length()));
                blockedBoard[i][j] = ' ';
            }
        }
    }

    private void printBoard(char[][] board) {
        for (int i = 0; i < verticalSize; i++) {
            for (int j = 0; j < horizontalSize; j++) {
                System.out.print(board[i][j]);
            }
            System.out.println();
        }
    }

    private void placeWordHorizontally(char[] word) {
        int verticalPosition;
        int horizontalPosition;
        do {
            verticalPosition = randomPosition(null, verticalSize);
            horizontalPosition = randomPosition(word, horizontalSize);
        } while (!isWordPositionForHorizontalOk(word, verticalPosition, horizontalPosition));

        for (int i = 0; i < word.length; i++) {
            board[verticalPosition][horizontalPosition + i] = word[i];
            blockedBoard[verticalPosition][horizontalPosition + i] = word[i];
        }
    }

    private int randomPosition(char[] word, int size) {
        return ThreadLocalRandom.current().nextInt(0, getUpperBound(word, size));
    }

    private boolean isWordPositionForHorizontalOk(char[] word, int verticalPosition, int horizontalPosition) {
        for (int i = 0; i < word.length; i++) {
            if (positionOccupied(blockedBoard[verticalPosition][horizontalPosition + i], word[i])) {
                return false;
            }
        }
        return true;
    }

    private void placeWordVertically(char[] word) {
        int verticalPosition;
        int horizontalPosition;
        do {
            verticalPosition = randomPosition(word, verticalSize);
            horizontalPosition = randomPosition(null, horizontalSize);
        } while (!isWordPositionForVerticalOk(word, verticalPosition, horizontalPosition));

        for (int i = 0; i < word.length; i++) {
            board[verticalPosition + i][horizontalPosition] = word[i];
            blockedBoard[verticalPosition + i][horizontalPosition] = word[i];
        }
    }

    private boolean isWordPositionForVerticalOk(char[] word, int verticalPosition, int horizontalPosition) {
        for (int i = 0; i < word.length; i++) {
            if (positionOccupied(blockedBoard[verticalPosition + i][horizontalPosition], word[i])) {
                return false;
            }
        }
        return true;
    }

    private boolean positionOccupied(char blockedChar, char potentialChar) {
        return !(blockedChar == ' ' || blockedChar == potentialChar);
    }

    private int getUpperBound(char[] word, int size) {
        int wordLength = 0;
        if (word != null) {
            wordLength = word.length;
        }
        if (wordLength == size)
            wordLength--;
        return size - wordLength;
    }

    public void solvePuzzle() {
        Solver solver = new Solver(board, arrayOfWordsAsCharArrays);
        solver.solve();
    }
}
