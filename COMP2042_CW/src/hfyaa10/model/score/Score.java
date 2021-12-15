package hfyaa10.model.score;


import java.util.ArrayList;

public class Score {
    // scoreMultiplier: score is determined by most recent scoreMultiplier only

    public static final float BonusMultiplier = 1.0F;
    public static final float baseBonusMultiplier = 1.0F;

    private float scoreMultiplier;

    private int BricksBallDestroyed;
    private int destroyedBricks;
    private int usedBalls;

    private String name;
    private float score;

    public Score() {
        this.BricksBallDestroyed = 0;
        this.destroyedBricks = 0;
        this.usedBalls = 0;
        resetScore();
        resetBonusMultiplier();
    }


    // score calculation
    // REWARD - multiplies user score as he hits more bricks
    public void currentScore() {
        score += BonusMultiplier * scoreMultiplier;
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
    public void resetBonusMultiplier() {
        scoreMultiplier = baseBonusMultiplier;
        BricksBallDestroyed = 0;
    }

    // bonusMultiplier increment as more bricks break
    // every 5 bricks break = bonus multiplier increase by 1
    public void updateBonusMultiplier() {
        float calc = (float) BricksBallDestroyed / 5;
        scoreMultiplier = (float) calc + 1;
    }

    public void addBricksDestroyed() {
        this.destroyedBricks += 1;
        this.BricksBallDestroyed += 1;
    }

    public void setDestroyedBricks(int destroyedBricks) {
        this.destroyedBricks = destroyedBricks;
    }

    public void addUsedBalls() {
        this.usedBalls += 1;
    }

    public void setUsedBalls(int ball) {
        this.usedBalls = ball;
    }




    // getters

    public String getName() {
        return name;
    }

    public int getUsedBalls() {
        return usedBalls;
    }

    public int getDestroyedBricks() {
        return destroyedBricks;
    }

    public float getScore() {
        return score;
    }

    public float getBonusMultiplier() {
        return scoreMultiplier;
    }

    public String scoreToString() {
        return String.format(
                "%s, %.1f, %d, %d\n",
                this.getName(),
                this.getScore(),
                this.getDestroyedBricks(),
                this.getUsedBalls()

        );
    }

    public String[] scoreToMultipleStrings() {
        ArrayList<String> res = new ArrayList<>();
        res.add(this.name);
        res.add(String.format("%.1f", this.getScore()));
        res.add(String.format("%d", this.getUsedBalls()));
        res.add(String.format("%d", this.getDestroyedBricks()));
        return res.toArray(new String[0]);
    }

    public boolean isBetterThan(Score score) {
        int res = Float.compare(this.getScore(), score.getScore());
        return res > 0;
    }
}

