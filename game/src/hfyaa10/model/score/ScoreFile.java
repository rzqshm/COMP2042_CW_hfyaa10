package hfyaa10.model.score;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ScoreFile {
    public static final String filepath = "scores.txt";
    private static final int nameColumn = 0;
    private static final int scoreColumn = 1;
    private static final int usedBallsColumn = 2;
    private static final int destroyedBricksColumn = 3;
    private static final int ColumnAmount = 4;
    private static final String delimiter = ", ";
    private static final int noScore = -1;

    private final ArrayList<Score> scores = new ArrayList<>();
    private File file;

    public ScoreFile() {
        initializeFile();
        loadScoresFromFile();
    }

    public void initializeFile() {
        try {
            file = new File(filepath);
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

    public void loadScoresFromFile() {
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
        String[] scoreStrings = scoreString.split(delimiter, ColumnAmount);
        score.setName(scoreStrings[nameColumn]);
        score.setScore(Float.parseFloat(scoreStrings[scoreColumn]));
        score.setUsedBalls(Integer.parseInt(scoreStrings[usedBallsColumn]));
        score.setDestroyedBricks(Integer.parseInt(scoreStrings[destroyedBricksColumn]));
        scores.add(score);
        sortByScores();
    }

    public void saveScoresToFile() {
        try {
            FileWriter writer = new FileWriter(filepath);
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

    public void UpdateScoreFile(Score score) {

        if (hasScore(score)) {
            Score existing = scores.get(getIdName(score.getName()));
            if (score.isBetterThan(existing)) {
                scores.remove(existing);
                scores.add(score);
                sortByScores();
                saveScoresToFile();
            } else if (existing.equals(score)) {
                // save new high score
                sortByScores();
                saveScoresToFile();
            }
        } else {
            scores.add(score);
            sortByScores();
            saveScoresToFile();
        }
    }

    private boolean hasScore(Score score) {
        return getIdName(score.getName()) != noScore;
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

    private int getIdName(String name) {
        for (int i = 0; i < scores.size(); i++) {
            Score score = scores.get(i);
            if (score.getName().equals(name)) {
                return i;
            }
        }
        return noScore;
    }

    public ArrayList<Score> getScores() {
        sortByScores();
        return scores;
    }
}
