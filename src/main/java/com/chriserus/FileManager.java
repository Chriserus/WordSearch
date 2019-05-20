package main.java.com.chriserus;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class FileManager {
    private static final String WORDS_FILE_LOCATION = "src\\resources\\words.txt";
    private static final String CONFIG_FILE_LOCATION = "src\\resources\\config.properties";
    private int numberOfWords;
    private List<String> words;
    private Board board;

    public FileManager() {
        readProperties();
        evaluateWords();
    }

    private void evaluateWords() {
        words = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(WORDS_FILE_LOCATION));
            String line;
            while ((line = reader.readLine()) != null) { //loop will run from 2nd line
                if (words.size() >= numberOfWords) {
                    break;
                }
                words.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readProperties() {
        try (InputStream input = new FileInputStream(CONFIG_FILE_LOCATION)) {
            Properties properties = new Properties();
            properties.load(input);
            numberOfWords = Integer.parseInt(properties.getProperty("words.number"));
            int boardVerticalSize = Integer.parseInt(properties.getProperty("board.size.vertical"));
            int boardHorizontalSize = Integer.parseInt(properties.getProperty("board.size.horizontal"));
            board = new Board(boardVerticalSize, boardHorizontalSize);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public Board getBoard() {
        return board;
    }

    public List<String> getWords() {
        return words;
    }

}
