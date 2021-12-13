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
package hfyaa10.player;

import java.awt.*;

import hfyaa10.ball.Ball;


public class Paddle {


    public static final Color BORDER_COLOR = Color.GREEN.darker().darker();
    public static final Color INNER_COLOR = Color.GREEN;

    private static final int DEF_MOVE_AMOUNT = 5;

    private Rectangle paddleFace;
    private Point ballPoint;
    private int moveAmount;
    private int min;
    private int max;


    public Paddle(Point ballPoint, int width, int height, Rectangle container) {
        this.ballPoint = ballPoint;

        moveAmount = 0;

        initialisePaddle(width, height, container);


    }

    private void initialisePaddle(int width, int height, Rectangle container){
        paddleFace = makeRectangle(width, height);
        min = container.x + (width / 2);
        max = min + container.width - width;
    }

    private Rectangle makeRectangle(int width, int height) {
        Point p = new Point((int) (ballPoint.getX() - (width / 2)), (int) ballPoint.getY());
        return new Rectangle(p, new Dimension(width, height));
    }

    public boolean impact(Ball b) {
        return paddleFace.contains(b.getPosition()) && paddleFace.contains(b.getDown());
    }

    public void move() {
        double x = ballPoint.getX() + moveAmount;
        if (x < min || x > max)
            return;
        ballPoint.setLocation(x, ballPoint.getY());
        paddleFace.setLocation(ballPoint.x - (int) paddleFace.getWidth() / 2, ballPoint.y);
    }

    public void moveLeft() {
        moveAmount = -DEF_MOVE_AMOUNT;
    }

    public void movRight() {
        moveAmount = DEF_MOVE_AMOUNT;
    }

    public void stop() {
        moveAmount = 0;
    }

    public Shape getPaddleFace() {
        return paddleFace;
    }

    public void moveTo(Point p) {
        ballPoint.setLocation(p);
        paddleFace.setLocation(ballPoint.x - (int) paddleFace.getWidth() / 2, ballPoint.y);
    }
}
