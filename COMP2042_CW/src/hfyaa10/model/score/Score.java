package hfyaa10.model.score;

import java.lang.Math;
import java.util.ArrayList;

public class Score {
    // scoreMultiplier: score is determined by most recent scoreMultiplier only

    public static final float CLAY_BRICK_REWARD = 1.0F;
    public static final float STEEL_BRICK_REWARD = 1.5F;
    public static final float CEMENT_BRICK_REWARD = 2.0F;

    public static final float BASE_SCORE_MULTIPLIER = 1.0F;
    private float scoreMultiplier;
    private int currentBallBrickCount;

    private String name;
    private int ballsUsed;
    private int bricksDestroyed;
    private float score;

    public Score() {
        this.ballsUsed = 0;
        this.bricksDestroyed = 0;
        this.currentBallBrickCount = 0;
        resetScore();
        resetScoreMultiplier();
    }

    public Score(String name) {
        this.name = name;
        this.ballsUsed = 0;
        this.bricksDestroyed = 0;
        this.currentBallBrickCount = 0;
        resetScore();
        resetScoreMultiplier();
    }

    public Score(String name, int ballsUsed, int bricksDestroyed, float score) {
        this.name = name;
        this.ballsUsed = ballsUsed;
        this.bricksDestroyed = bricksDestroyed;
        this.score = score;
    }

    // score calculation

    // REWARD - multiplies user score as he hits more bricks
    public void updateScore() {
        score += CLAY_BRICK_REWARD * scoreMultiplier;
    }

    public void resetScore() {
        score = 0;
    }

    // setters

    public void setScore(float score) {
        this.score = score;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Bonus Multiplier resets if ball falls
    public void resetScoreMultiplier() {
        scoreMultiplier = BASE_SCORE_MULTIPLIER;
        currentBallBrickCount = 0;
    }

    // bonusMultiplier increment as more bricks break
    public void updateScoreMultiplier() {
        float calc = (float) currentBallBrickCount / 5;
        scoreMultiplier = (float) Math.log(calc + 1) + 1;
    }

    // added 1 to balls used for easier score reading
    public void incBallsUsed() {
        this.ballsUsed += 1;
    }

    public void setBallsUsed(int ballsUsed) {
        this.ballsUsed = ballsUsed;
    }

    // added 1 to bricks destroyed for easier score reading
    public void incBricksDestroyed() {
        this.bricksDestroyed += 1;
        this.currentBallBrickCount += 1;
    }

    public void setBricksDestroyed(int bricksDestroyed) {
        this.bricksDestroyed = bricksDestroyed;
    }

    // getters

    public String getName() {
        return name;
    }

    public int getBallsUsed() {
        return ballsUsed;
    }

    public int getBricksDestroyed() {
        return bricksDestroyed;
    }

    public float getScore() {
        return score;
    }

    public float getScoreMultiplier() {
        return scoreMultiplier;
    }

    public String scoreToString() {
        return String.format(
                "%s, %d, %d, %.1f\n",
                this.getName(),
                this.getBallsUsed(),
                this.getBricksDestroyed(),
                this.getScore()
        );
    }

    public String[] scoreToSeparatedStrings() {
        ArrayList<String> res = new ArrayList<>();
        res.add(this.name);
        res.add(String.format("%d", this.getBallsUsed()));
        res.add(String.format("%d", this.getBricksDestroyed()));
        res.add(String.format("%.1f", this.getScore()));
        return res.toArray(new String[0]);
    }

    public boolean isBetterThan(Score score) {
        int res = Float.compare(this.getScore(), score.getScore());
        return res > 0;
    }
}

