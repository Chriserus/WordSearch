package main.java.com.chriserus;

public class Main {

    public static void main(String[] args) {
        FileManager fileManager = new FileManager();
        Game game = new Game(fileManager.getWords(), fileManager.getBoardVerticalSize(), fileManager.getBoardHorizontalSize());
        game.preparePuzzle();
        game.solvePuzzle();
    }
}
