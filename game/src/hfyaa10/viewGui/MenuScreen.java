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
package hfyaa10.viewGui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;


public class MenuScreen extends JComponent implements MouseListener, MouseMotionListener {

    private static final String GREETINGS = "Welcome to:";
    private static final String GAME_TITLE = "Brick Destroy";
    private static final String CREDITS = "Version Razeeq 20203994 - COMP2042_CW_hfyaa10";
    private static final String START_TEXT = "Start";
    private static final String MENU_TEXT = "Exit";
    private static final String HELP_TEXT = "Help";

    private static final Color BG_COLOR = Color.LIGHT_GRAY.darker();
    private static final Color BORDER_COLOR = new Color(0,0,0); //Black
    private static final Color DASH_BORDER_COLOR = new  Color(160, 160, 160);//gray
    private static final Color TEXT_COLOR = new Color(0, 0, 0);//egyptian blue
    private static final Color CLICKED_BUTTON_COLOR = Color.WHITE.brighter();
    private static final Color CLICKED_TEXT = Color.WHITE;
    private static final int BORDER_SIZE = 5;
    private static final float[] DASHES = {12,6};

    private Rectangle menuScreen;
    private Rectangle startButton;
    private Rectangle menuButton;
    private Rectangle helpButton;


    private BasicStroke borderStoke;
    private BasicStroke borderStoke_noDashes;

    private Font greetingsFont;
    private Font gameTitleFont;
    private Font creditsFont;
    private Font buttonFont;

    private GameWindow gameWindow;

    private boolean startClicked;
    private boolean menuClicked;
    private boolean helpClicked;

    public MenuScreen(GameWindow owner, Dimension size){

        this.gameWindow = owner;

        setWindowAndInput();

        setScreen(size);
        setButtons(size);

        setBorders();
        setFonts();

    }

    //new method
    // makes window focused and able to receive input
    private void setWindowAndInput(){
        this.setFocusable(true);
        this.requestFocusInWindow();    // window focus

        this.addMouseListener(this);
        this.addMouseMotionListener(this);  // mouse input listener
    }

    //new method
    private void setScreen(Dimension size){
        menuScreen = new Rectangle(new Point(0,0),size);
        this.setPreferredSize(size);
    }

    //new method
    private void setButtons(Dimension size){
        Dimension btnDim = new Dimension(size.width / 3, size.height / 12);
        startButton = new Rectangle(btnDim);
        menuButton = new Rectangle(btnDim);
        helpButton = new Rectangle(btnDim);
    }

    //new method
    private void setFonts(){
        greetingsFont = new Font("Noto Mono",Font.PLAIN,25);
        gameTitleFont = new Font("Noto Mono",Font.BOLD,40);
        creditsFont = new Font("Monospaced",Font.PLAIN,12);
        buttonFont = new Font("Monospaced",Font.PLAIN,startButton.height-2);
    }

    //new method
    private void setBorders(){
        borderStoke = new BasicStroke(BORDER_SIZE,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND,0,DASHES,0);
        borderStoke_noDashes = new BasicStroke(BORDER_SIZE,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND);
    }

    public void paint(Graphics g){
        drawMenu((Graphics2D)g);
    }


    public void drawMenu(Graphics2D g2d){

        drawContainer(g2d);

        /*
        all the following method calls need a relative
        painting directly into the MenuScreen rectangle,
        so the translation is made here so the other methods do not do that.
         */
        Color prevColor = g2d.getColor();
        Font prevFont = g2d.getFont();

        double x = menuScreen.getX();
        double y = menuScreen.getY();

        g2d.translate(x,y);

        //methods calls
        drawText(g2d);
        drawButton(g2d);
        //end of methods calls

        g2d.translate(-x,-y);
        g2d.setFont(prevFont);
        g2d.setColor(prevColor);
    }

    private void drawContainer(Graphics2D g2d){
        Color prev = g2d.getColor();

        g2d.setColor(BG_COLOR);
        g2d.fill(menuScreen);

        Stroke tmp = g2d.getStroke();

        g2d.setStroke(borderStoke_noDashes);
        g2d.setColor(DASH_BORDER_COLOR);
        g2d.draw(menuScreen);

        g2d.setStroke(borderStoke);
        g2d.setColor(BORDER_COLOR);
        g2d.draw(menuScreen);

        g2d.setStroke(tmp);

        g2d.setColor(prev);
    }

    private void drawText(Graphics2D g2d){

        g2d.setColor(TEXT_COLOR);

        FontRenderContext frc = g2d.getFontRenderContext();

        Rectangle2D greetingsRect = greetingsFont.getStringBounds(GREETINGS,frc);
        Rectangle2D gameTitleRect = gameTitleFont.getStringBounds(GAME_TITLE,frc);
        Rectangle2D creditsRect = creditsFont.getStringBounds(CREDITS,frc);

        int sX,sY;

        sX = (int)(menuScreen.getWidth() - greetingsRect.getWidth()) / 2;
        sY = (int)(menuScreen.getHeight() / 4);

        g2d.setFont(greetingsFont);
        g2d.drawString(GREETINGS,sX,sY);

        sX = (int)(menuScreen.getWidth() - gameTitleRect.getWidth()) / 2;
        sY += (int) gameTitleRect.getHeight();//add 10% of String height between the two strings

        g2d.setFont(gameTitleFont);
        g2d.drawString(GAME_TITLE,sX,sY);

        sX = (int)(menuScreen.getWidth() - creditsRect.getWidth()) / 2;
        sY += (int) creditsRect.getHeight() * 1.2; //add 20% of String height between the two strings

        g2d.setFont(creditsFont);
        g2d.drawString(CREDITS,sX,sY);


    }

    private void drawButton(Graphics2D g2d){

        FontRenderContext frc = g2d.getFontRenderContext();

        Rectangle2D txtRect = buttonFont.getStringBounds(START_TEXT,frc);
        Rectangle2D mTxtRect = buttonFont.getStringBounds(MENU_TEXT,frc);
        Rectangle2D hTxtRect = buttonFont.getStringBounds(HELP_TEXT, frc);

        g2d.setFont(buttonFont);

        int x = (menuScreen.width - startButton.width) / 2;
        int y =(int) ((menuScreen.height - startButton.height) * 0.6);

        startButton.setLocation(x,y);

        x = (int)(startButton.getWidth() - txtRect.getWidth()) / 2;
        y = (int)(startButton.getHeight() - txtRect.getHeight()) / 2;

        x += startButton.x;
        y += startButton.y + (startButton.height * 0.9);




        if(startClicked){
            Color tmp = g2d.getColor();
            g2d.setColor(CLICKED_BUTTON_COLOR);
            g2d.draw(startButton);
            g2d.setColor(CLICKED_TEXT);
            g2d.drawString(START_TEXT,x,y);
            g2d.setColor(tmp);
        }
        else{
            g2d.draw(startButton);
            g2d.drawString(START_TEXT,x,y);
        }

        x = startButton.x;
        y = startButton.y;

        y *= 1.2;

        helpButton.setLocation(x,y);


        x = (int)(helpButton.getWidth() - mTxtRect.getWidth()) / 2;
        y = (int)(helpButton.getHeight() - mTxtRect.getHeight()) / 2;

        x += helpButton.x;
        y += helpButton.y + (startButton.height * 0.9);

        if(helpClicked){
            Color tmp = g2d.getColor();

            g2d.setColor(CLICKED_BUTTON_COLOR);
            g2d.draw(helpButton);
            g2d.setColor(CLICKED_TEXT);
            g2d.drawString(HELP_TEXT,x,y);
            g2d.setColor(tmp);
        }
        else{
            g2d.draw(helpButton);
            g2d.drawString(HELP_TEXT,x,y);
        }
        x = helpButton.x;
        y = helpButton.y;

        y *= 1.2;

        menuButton.setLocation(x, y);

        x = (int) (menuButton.getWidth() - mTxtRect.getWidth()) / 2;
        y = (int) (menuButton.getHeight() - mTxtRect.getHeight()) / 2;

        x += menuButton.x;
        y += menuButton.y + (startButton.height * 0.9);

        if (menuClicked) {
            Color tmp = g2d.getColor();

            g2d.setColor(CLICKED_BUTTON_COLOR);
            g2d.draw(menuButton);
            g2d.setColor(CLICKED_TEXT);
            g2d.drawString(MENU_TEXT, x, y);
            g2d.setColor(tmp);
        } else {
            g2d.draw(menuButton);
            g2d.drawString(MENU_TEXT, x, y);
        }

    }


    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(startButton.contains(p)){
           gameWindow.showGameWindow();

        }
        else if(menuButton.contains(p)){
            System.out.println("Goodbye " + System.getProperty("user.name"));
            System.exit(0);
        } else if (helpButton.contains(p)){
            gameWindow.showHelpFrame();
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(startButton.contains(p)){
            startClicked = true;
            repaint(startButton.x,startButton.y,startButton.width+1,startButton.height+1);

        }
        else if(menuButton.contains(p)){
            menuClicked = true;
            repaint(menuButton.x,menuButton.y,menuButton.width+1,menuButton.height+1);
        }
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        if(startClicked ){
            startClicked = false;
            repaint(startButton.x,startButton.y,startButton.width+1,startButton.height+1);
        }
        else if(menuClicked){
            menuClicked = false;
            repaint(menuButton.x,menuButton.y,menuButton.width+1,menuButton.height+1);
        } else if (helpClicked){
            helpClicked = true;
            repaint(helpButton.x, helpButton.y, helpButton.width + 1, helpButton.height + 1);
        }
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
        if(startButton.contains(p) || menuButton.contains(p) || helpButton.contains(p))
            this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        else
            this.setCursor(Cursor.getDefaultCursor());

    }
}
