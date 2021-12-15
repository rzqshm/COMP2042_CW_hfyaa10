package hfyaa10.model.score;

import javax.swing.*;
import java.awt.*;

public class UserScore {
    private static final int windowWidth = 300;
    private static final int windowHeight = 150;

    private final JFrame frame;

    private final Score player;

    public UserScore(Score player) {

        this.player = player;
        frame = new JFrame();
        frame.setPreferredSize(new Dimension(windowWidth, windowHeight));

    }

    public void inputUsername() {
        player.setName(
                (String) JOptionPane.showInputDialog(
                        frame,
                        "Please enter your username:",
                        "Brick Destoyer Login",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        null,
                        System.getProperty("user.name")
                )
        );
    }
}
