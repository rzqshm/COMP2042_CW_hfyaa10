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
package hfyaa10.control;

import hfyaa10.control.levels.Wall;
import hfyaa10.model.player.Paddle;

import hfyaa10.model.brick.Brick;
import hfyaa10.control.debug.DebugConsole;
import hfyaa10.model.ball.Ball;
import hfyaa10.model.score.ScoreTab;
import hfyaa10.model.score.ScoreFile;
import hfyaa10.model.score.UserScore;



import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.FontRenderContext;



public class GameBoard extends JComponent implements KeyListener,MouseListener,MouseMotionListener {

    private static final String CONTINUE = "Continue";
    private static final String RESTART = "Restart";
    private static final String EXIT = "Exit";
    private static final String PAUSE = "Pause Menu";
    private static final int TEXT_SIZE = 30;
    private static final Color MENU_COLOR = new Color(0,255,0);

    public static final int BRICK_NUM = 30;
    public static final int LINE_NUM = 3;
    public static final int INITIAL_X_BALLPOSITION = 300;
    public static final int INITIAL_Y_BALLPOSITION = 420;
    public static final int BRICK_DIMENSIONRATIO = 6 / 2;


    private static final int DEF_WIDTH = 600;
    private static final int DEF_HEIGHT = 450;

    private static final Color BG_COLOR = Color.BLACK;

    private Timer gameTimer;

    private Wall wall;

    private String inGameScoreMonitor;
    private String inGameMultiplierMonitor;

    private boolean showPauseMenu;

    private Font menuFont;

    private Rectangle continueButtonRect;
    private Rectangle exitButtonRect;
    private Rectangle restartButtonRect;
    private int strLen;

    private DebugConsole debugConsole;

    public ScoreFile scoreFile = new ScoreFile(); //
    public ScoreTab scoreView = new ScoreTab(scoreFile); //


    public GameBoard(JFrame owner){
        super();

        strLen = 0;
        showPauseMenu = false;



        menuFont = new Font("Monospaced",Font.PLAIN,TEXT_SIZE);


        this.initialize();
        inGameScoreMonitor = "";
        inGameMultiplierMonitor = "";
        wall = new Wall(new Rectangle(0,0,DEF_WIDTH,DEF_HEIGHT),BRICK_NUM,LINE_NUM,BRICK_DIMENSIONRATIO,new Point(INITIAL_X_BALLPOSITION,INITIAL_Y_BALLPOSITION));

        // initialise score file
        UserScore userInput = new UserScore(wall.User);
        System.out.println("Generated new userInput object.");
        System.out.println("Updating username.");
        userInput.inputUsername();
        System.out.println(wall.User.getName());
        scoreFile.UpdateScoreFile(wall.User);
        System.out.println("Added scores.");

        debugConsole = new DebugConsole(owner,wall,this);

        wall.nextLevel();

        gameTimer = new Timer(10,e ->{
            wall.move();
            wall.findImpacts();

            inGameScoreMonitor = String.format(
                    "Bricks: %d Balls %d Score: %.0f",
                    wall.getBrickCount(),
                    wall.getBallCount(),
                    wall.User.getScore()
                    );

            inGameMultiplierMonitor = String.format(
                    "Current Streak Bonus Multiplier: %.2f",
                    wall.User.getBonusMultiplier()
            );

            if(wall.isBallLost()){
                scoreFile.UpdateScoreFile(wall.User);
                if(wall.ballEnd()){
                    wall.wallReset();
                    inGameScoreMonitor = "Game over";
                    scoreView.showScoreTable();
                }
                wall.ballReset();
                gameTimer.stop();
            }
            else if(wall.isDone()){
                if(wall.hasLevel()){
                    inGameScoreMonitor = "Go to Next Level";

                    //show high score screen
                    scoreFile.UpdateScoreFile(wall.User);
                    scoreView.showScoreTable();

                    gameTimer.stop();
                    wall.ballReset();
                    wall.wallReset();
                    wall.nextLevel();
                }
                else{
                    inGameScoreMonitor = "ALL WALLS DESTROYED";

                    scoreFile.UpdateScoreFile(wall.User);
                    scoreView.showScoreTable();

                    gameTimer.stop();
                }
            }

            repaint();
        });

    }



    private void initialize(){
        this.setPreferredSize(new Dimension(DEF_WIDTH,DEF_HEIGHT));
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addKeyListener(this);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }


    public void paint(Graphics g){

        Graphics2D g2d = (Graphics2D) g;

        clear(g2d);

        g2d.setColor(Color.WHITE);
        g2d.drawString(inGameScoreMonitor,225,225); // display score  on screen
        g2d.drawString(inGameMultiplierMonitor,395,445); // display bonus multiplier on screen

        drawBall(wall.ball,g2d);

        for(Brick b : wall.bricks)
            if(!b.isBroken())
                drawBrick(b,g2d);

        drawPaddle(wall.paddle,g2d);

        if(showPauseMenu)
            drawMenu(g2d);

        Toolkit.getDefaultToolkit().sync();
    }

    private void clear(Graphics2D g2d){
        Color tmp = g2d.getColor();
        g2d.setColor(BG_COLOR);
        g2d.fillRect(0,0,getWidth(),getHeight());
        g2d.setColor(tmp);
    }

    private void drawBrick(Brick brick,Graphics2D g2d){
        Color tmp = g2d.getColor();

        g2d.setColor(brick.getInnerColor());
        g2d.fill(brick.getBrick());

        g2d.setColor(brick.getBorderColor());
        g2d.draw(brick.getBrick());


        g2d.setColor(tmp);
    }

    private void drawBall(Ball ball,Graphics2D g2d){
        Color tmp = g2d.getColor();

        Shape s = ball.getBallFace();

        g2d.setColor(ball.getInnerColor());
        g2d.fill(s);

        g2d.setColor(ball.getBorderColor());
        g2d.draw(s);

        g2d.setColor(tmp);
    }

    private void drawPaddle(Paddle p, Graphics2D g2d){
        Color tmp = g2d.getColor();

        Shape s = p.getPaddleFace();
        g2d.setColor(Paddle.INNER_COLOR);
        g2d.fill(s);

        g2d.setColor(Paddle.BORDER_COLOR);
        g2d.draw(s);

        g2d.setColor(tmp);
    }

    private void drawMenu(Graphics2D g2d){
        obscureGameBoard(g2d);
        drawPauseMenu(g2d);
    }

    private void obscureGameBoard(Graphics2D g2d){

        Composite tmp = g2d.getComposite();
        Color tmpColor = g2d.getColor();

        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.55f);
        g2d.setComposite(ac);

        g2d.setColor(Color.BLACK);
        g2d.fillRect(0,0,DEF_WIDTH,DEF_HEIGHT);

        g2d.setComposite(tmp);
        g2d.setColor(tmpColor);
    }

    private void drawPauseMenu(Graphics2D g2d){
        Font tmpFont = g2d.getFont();
        Color tmpColor = g2d.getColor();


        g2d.setFont(menuFont);
        g2d.setColor(MENU_COLOR);

        if(strLen == 0){
            FontRenderContext frc = g2d.getFontRenderContext();
            strLen = menuFont.getStringBounds(PAUSE,frc).getBounds().width;
        }

        int x = (this.getWidth() - strLen) / 2;
        int y = this.getHeight() / 10;

        g2d.drawString(PAUSE,x,y);

        x = this.getWidth() / 8;
        y = this.getHeight() / 4;


        if(continueButtonRect == null){
            FontRenderContext frc = g2d.getFontRenderContext();
            continueButtonRect = menuFont.getStringBounds(CONTINUE,frc).getBounds();
            continueButtonRect.setLocation(x,y-continueButtonRect.height);
        }

        g2d.drawString(CONTINUE,x,y);

        y *= 2;

        if(restartButtonRect == null){
            restartButtonRect = (Rectangle) continueButtonRect.clone();
            restartButtonRect.setLocation(x,y-restartButtonRect.height);
        }

        g2d.drawString(RESTART,x,y);

        y *= 3.0/2;

        if(exitButtonRect == null){
            exitButtonRect = (Rectangle) continueButtonRect.clone();
            exitButtonRect.setLocation(x,y-exitButtonRect.height);
        }

        g2d.drawString(EXIT,x,y);



        g2d.setFont(tmpFont);
        g2d.setColor(tmpColor);
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        switch(keyEvent.getKeyCode()){
            case KeyEvent.VK_A:
                wall.paddle.moveLeft();
                break;
            case KeyEvent.VK_D:
                wall.paddle.movRight();
                break;
            case KeyEvent.VK_ESCAPE:
                showPauseMenu = !showPauseMenu;
                repaint();
                gameTimer.stop();
                break;
            case KeyEvent.VK_H:
                // show high score screen
                scoreFile.UpdateScoreFile(wall.User);
                scoreView.showScoreTable();
                break;
            case KeyEvent.VK_SPACE:
                if(!showPauseMenu)
                    if(gameTimer.isRunning())
                        gameTimer.stop();
                    else
                        gameTimer.start();
                break;
            case KeyEvent.VK_F1:
                if(keyEvent.isAltDown() && keyEvent.isShiftDown())
                    debugConsole.setVisible(true);
            default:
                wall.paddle.stop();
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        wall.paddle.stop();
    }
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(!showPauseMenu)
            return;
        if(continueButtonRect.contains(p)){
            showPauseMenu = false;
            repaint();
        }
        else if(restartButtonRect.contains(p)){
            inGameScoreMonitor = "Restarting Game...";
            wall.ballReset();
            wall.wallReset();
            showPauseMenu = false;
            repaint();
        }
        else if(exitButtonRect.contains(p)){
            System.exit(0);
        }

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(exitButtonRect != null && showPauseMenu) {
            if (exitButtonRect.contains(p) || continueButtonRect.contains(p) || restartButtonRect.contains(p))
                this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            else
                this.setCursor(Cursor.getDefaultCursor());
        }
        else{
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    public void onLostFocus(){
        gameTimer.stop();
        inGameScoreMonitor = "Focus Lost";
        repaint();
    }

}
