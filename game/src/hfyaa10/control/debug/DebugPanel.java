/*
 *  Brick Destroy - A simple Arcade video game
 *   Copyright (C) 2017  Filippo Ranza
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package hfyaa10.control.debug;

import hfyaa10.model.levels.Wall;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionListener;



public class DebugPanel extends JPanel {

    private static final Color DEF_BKG = Color.WHITE;

    public static final int gridRow = 2;
    public static final int gridColumn = 2;

    private JSlider ballXSpeed;
    private JSlider ballYSpeed;


    public DebugPanel(Wall wall){

        initialize();

        debugButtons(wall);
        debugSliders(wall);

    }

    private void debugButtons(Wall wall) {
        JButton skipLevel = makeButton("Skip Level", e -> wall.nextLevel());
        this.add(skipLevel);

        JButton resetBalls = makeButton("Reset Balls", e -> wall.resetBallCount());
        this.add(resetBalls);
    }

    private void debugSliders(Wall wall) {
        ballXSpeed = makeSlider(Wall.minSpeedX,Wall.maxSpeedX, e -> wall.setBallXSpeed(ballXSpeed.getValue()));
        ballYSpeed = makeSlider(Wall.minSpeedY,Wall.maxSpeedY, e -> wall.setBallYSpeed(ballYSpeed.getValue()));
        this.add(ballXSpeed);
        this.add(ballYSpeed);
    }


    private void initialize(){
        this.setBackground(DEF_BKG);
        this.setLayout(new GridLayout(gridRow,gridColumn));
    }

    private JButton makeButton(String title, ActionListener e){
        JButton out = new JButton(title);
        out.addActionListener(e);
        return  out;
    }

    private JSlider makeSlider(int min, int max, ChangeListener e){
        JSlider out = new JSlider(min,max);
        out.setMajorTickSpacing(1);
        out.setSnapToTicks(true);
        out.setPaintTicks(true);
        out.addChangeListener(e);
        return out;
    }

    public void setSpeed(int x, int y){
        ballXSpeed.setValue(x);
        ballYSpeed.setValue(y);
    }

}
