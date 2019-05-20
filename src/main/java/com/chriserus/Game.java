package main.java.com.chriserus;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private List<String> words;
    private ArrayList<char[]> arrayOfWordsAsCharArrays;
    private Board board;
    private Board blockedBoard;

    public Game(Board board, List<String> words) {
        this.words = words;
        this.board = board;
        this.blockedBoard = new Board(board.getVerticalSize(), board.getHorizontalSize());
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
        placeAllWordsOnTheBoard();
        board.fillWithRandomLetters();
        board.print();
        System.out.println("---------WORDS ON BOARD---------");
        blockedBoard.print();
    }

    private void placeAllWordsOnTheBoard() {
        for (char[] word : arrayOfWordsAsCharArrays) {
            int roll = (int) Math.round(Math.random());
            switch (roll) {
                case 0:
                    board.placeWordVertically(arrayOfWordsAsCharArrays.get(arrayOfWordsAsCharArrays.indexOf(word)));
                    blockedBoard.placeWordVertically(arrayOfWordsAsCharArrays.get(arrayOfWordsAsCharArrays.indexOf(word)));
                    break;
                case 1:
                    board.placeWordHorizontally(arrayOfWordsAsCharArrays.get(arrayOfWordsAsCharArrays.indexOf(word)));
                    blockedBoard.placeWordHorizontally(arrayOfWordsAsCharArrays.get(arrayOfWordsAsCharArrays.indexOf(word)));
                    break;
            }
        }
    }

    public void solvePuzzle() {
        Solver solver = new Solver(board, arrayOfWordsAsCharArrays);
        solver.solve();
    }
}
