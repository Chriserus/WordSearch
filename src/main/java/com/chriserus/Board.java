package main.java.com.chriserus;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Board {
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    private int verticalSize;
    private int horizontalSize;
    private char[][] panel;

    public Board(int verticalSize, int horizontalSize) {
        this.verticalSize = verticalSize;
        this.horizontalSize = horizontalSize;
        panel = new char[verticalSize][horizontalSize];
        fillWithBlankSpaces();
    }

    public void fillWithRandomLetters() {
        Random r = new Random();
        char randomChar;
        for (int i = 0; i < verticalSize; i++) {
            for (int j = 0; j < horizontalSize; j++) {
                randomChar = ALPHABET.charAt(r.nextInt(ALPHABET.length()));
                if (isPositionAvailable(randomChar, i, j))
                    panel[i][j] = randomChar;
            }
        }
    }


    public Board getBoardRotatedCounterclockwise() {
        Board rotatedBoard = new Board(horizontalSize, verticalSize);
        char[][] columns = new char[horizontalSize][verticalSize];
        for (int i = 0; i < horizontalSize; i++) {
            for (int j = verticalSize - 1; j >= 0; j--) {
                columns[i][j] = panel[j][i];
            }
        }
        rotatedBoard.setPanel(columns);
        return rotatedBoard;
    }

    public void print() {
        for (int i = 0; i < verticalSize; i++) {
            for (int j = 0; j < horizontalSize; j++) {
                System.out.print(panel[i][j]);
            }
            System.out.println();
        }
    }

    public void placeCharacterInPosition(char character, int verticalPosition, int horizontalPosition) {
        panel[verticalPosition][horizontalPosition] = character;
    }

    public void placeWordVertically(char[] word) {
        int verticalPosition;
        int horizontalPosition;
        do {
            verticalPosition = randomPosition(word, verticalSize);
            horizontalPosition = randomPosition(null, horizontalSize);
        } while (!isWordPositionForVerticalOk(word, verticalPosition, horizontalPosition));

        for (int i = 0; i < word.length; i++) {
            placeCharacterInPosition(word[i], verticalPosition + i, horizontalPosition);
        }
    }

    public void placeWordHorizontally(char[] word) {
        int verticalPosition;
        int horizontalPosition;
        do {
            verticalPosition = randomPosition(null, verticalSize);
            horizontalPosition = randomPosition(word, horizontalSize);
        } while (!isWordPositionForHorizontalOk(word, verticalPosition, horizontalPosition));

        for (int i = 0; i < word.length; i++) {
            placeCharacterInPosition(word[i], verticalPosition, horizontalPosition + i);
        }
    }

    private int randomPosition(char[] word, int size) {
        return ThreadLocalRandom.current().nextInt(0, getUpperBound(word, size));
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

    private boolean isWordPositionForVerticalOk(char[] word, int verticalPosition, int horizontalPosition) {
        for (int i = 0; i < word.length; i++) {
            if (!isPositionAvailable(word[i], verticalPosition + i, horizontalPosition))
                return false;
        }
        return true;
    }

    private boolean isWordPositionForHorizontalOk(char[] word, int verticalPosition, int horizontalPosition) {
        for (int i = 0; i < word.length; i++) {
            if (!isPositionAvailable(word[i], verticalPosition, horizontalPosition + i)) {
                return false;
            }
        }
        return true;
    }

    private boolean isPositionAvailable(char potentialChar, int verticalPosition, int horizontalPosition) {
        return panel[verticalPosition][horizontalPosition] == ' ' || panel[verticalPosition][horizontalPosition] == potentialChar;
    }

    private void fillWithBlankSpaces() {
        for (int i = 0; i < verticalSize; i++) {
            for (int j = 0; j < horizontalSize; j++) {
                panel[i][j] = ' ';
            }
        }
    }

    public int getVerticalSize() {
        return verticalSize;
    }

    public int getHorizontalSize() {
        return horizontalSize;
    }

    public char[][] getPanel() {
        return panel;
    }

    public void setPanel(char[][] panel) {
        this.panel = panel;
    }
}
