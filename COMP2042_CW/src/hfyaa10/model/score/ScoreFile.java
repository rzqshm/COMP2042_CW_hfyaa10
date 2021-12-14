package hfyaa10.model.score;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ScoreFile {
    public static final String FILEPATH = "scores.txt";
    private static final int COL_NAME = 0;
    private static final int COL_BALLS_USED = 1;
    private static final int COL_BRICKS_DESTROYED = 2;
    private static final int COL_SCORE = 3;
    private static final int NUM_COLS = 4;
    private static final String DELIMITER = ", ";
    private static final int SCORE_NOT_FOUND = -1;

    private final ArrayList<Score> scores = new ArrayList<>();
    private File file;

    public ScoreFile() {
        initFile();
        loadHighScoresFromFile();
    }

    public void initFile() {
        try {
            file = new File(FILEPATH);
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getPath());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void loadHighScoresFromFile() {
        try {
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                loadScoreFromString(reader.nextLine());
            }
            reader.close();
            sortByScores();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void loadScoreFromString(String scoreString) {
        Score score = new Score();
        String[] scoreStrings = scoreString.split(DELIMITER, NUM_COLS);
        score.setName(scoreStrings[COL_NAME]);
        score.setBallsUsed(Integer.parseInt(scoreStrings[COL_BALLS_USED]));
        score.setBricksDestroyed(Integer.parseInt(scoreStrings[COL_BRICKS_DESTROYED]));
        score.setScore(Float.parseFloat(scoreStrings[COL_SCORE]));
        // might be duplicated
        scores.add(score);
        sortByScores();
    }

    public void saveHighScoresToFile() {
        try {
            FileWriter writer = new FileWriter(FILEPATH);
            sortByScores();
            for (Score score: scores) {
                writer.write(score.scoreToString());
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void addOrUpdateScore(Score score) {

        if (scoreExists(score)) {
            Score existing = scores.get(getIdxByName(score.getName()));
            if (score.isBetterThan(existing)) {
                scores.remove(existing);
                scores.add(score);
                sortByScores();
                saveHighScoresToFile();
            } else if (existing.equals(score)) {
                // if the current score is the best score
                sortByScores();
                saveHighScoresToFile();
            }
        } else {
            scores.add(score);
            sortByScores();
            saveHighScoresToFile();
        }
    }

    private boolean scoreExists(Score score) {
        return getIdxByName(score.getName()) != SCORE_NOT_FOUND;
    }

    private void sortByScores() {
        int max;
        Score temp;
        for (int i = 0; i < scores.size(); i++) {
            max = i;
            for (int j = i + 1; j < scores.size(); j++) {
                if (scores.get(j).getScore() > scores.get(max).getScore()) {
                    max = j;
                }
            }
            temp = scores.get(i);
            scores.set(i, scores.get(max));
            scores.set(max, temp);
        }
    }

    private int getIdxByName(String name) {
        for (int i = 0; i < scores.size(); i++) {
            Score score = scores.get(i);
            if (score.getName().equals(name)) {
                return i;
            }
        }
        return SCORE_NOT_FOUND;
    }

    public ArrayList<Score> getScores() {
        sortByScores();
        return scores;
    }
}
