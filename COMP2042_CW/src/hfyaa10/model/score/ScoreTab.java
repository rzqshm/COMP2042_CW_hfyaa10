package hfyaa10.model.score;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class ScoreTab {
    private static final String WindowTitle = "High Scores LeaderBoard";

    private static final String[] ScoreTableHeaders = {
            "Player UserName",
            "Score"
    };

    JFrame frame;
    JTable table;
    JScrollPane scrollPane;

    ScoreFile scoresFile;

    public ScoreTab(ScoreFile scoresFile) {
        this.scoresFile = scoresFile;
        initializeScoreScreen();
    }

    public void initializeScoreScreen() {

        frame = new JFrame();
        frame.setTitle(WindowTitle);
        frame.setSize(500, 500);

        String[][] highScoreStrings = generateScoreStrings();
        table = new JTable(highScoreStrings, ScoreTableHeaders);

        scrollPane = new JScrollPane(table);
        frame.add(scrollPane);
    }

    public void updateScoreScreen() {
        String[][] highScoreStrings = generateScoreStrings();
        DefaultTableModel tableData = new DefaultTableModel(highScoreStrings, ScoreTableHeaders);
        table.setModel(tableData);
    }

    private String[][] generateScoreStrings() {

        ArrayList<String[]> highScoreStrings = new ArrayList<>();

        for (Score score: scoresFile.getScores()) {
            highScoreStrings.add(score.scoreToMultipleStrings());
        }

        return highScoreStrings.toArray(new String[0][]);
    }

    public void showScoreTable() {
        updateScoreScreen();
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

