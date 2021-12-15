package hfyaa10.viewGui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;



public class HelpScreen extends JComponent implements MouseListener, MouseMotionListener {

    JLabel info;

    private static final String EXIT_TEXT = "Exit";
    private static final String infoTitle = "BRICK DESTROY 2";
    private static final String infoDescription = "An updated and improved Brick Destroy Game originally by FilippoRanza";
    private static final String infoGame = "Game Information";
    private static final String info1 = "Goal is to DESTROY Walls with a Ball and Paddle.";
    private static final String infoKeys = "A - move left      SPACE - start or pause game";
    private static final String infoKeys1 = "D - move right      ESC - open pause menu";
    private static final String infoKeys2 = "h - open highscore      ALT+SHITF+F1 - open console";
    private static final String infoScore = "Your High score will be recorded with your username.";
    private static final String infoScore1 = "Pro Tip: Hit bricks in a streak to get score multiplier ;)";



    private static final Color BG_COLOR = Color.LIGHT_GRAY.darker();
    private static final Color BORDER_COLOR = new Color(0, 0, 0); //Venetian Red
    private static final Color DASH_BORDER_COLOR = new Color(255, 128, 0);//school bus yellow
    private static final Color TEXT_COLOR = new Color(0, 0, 0);//egyptian blue
    private static final Color CLICKED_BUTTON_COLOR = BG_COLOR.brighter();
    private static final Color CLICKED_TEXT = Color.WHITE;
    private static final int BORDER_SIZE = 5;
    private static final float[] DASHES = {12, 6};
    public static final int FONT_SIZE1 = 40;
    public static final int FONT_SIZE2 = 12;
    public static final int FONT_SIZE3 = 20;


    private Rectangle exitButton;
    private Rectangle helpScreen;

    private BasicStroke borderStoke;
    private BasicStroke borderStoke_noDashes;

    private Font buttonFont;
    private Font infoTitleFont;
    private Font infoDescriptionFont;
    private Font infoGameFont;
    private Font info1Font;
    private Font infoKeysFont;
    private Font infoScoreFont;

    private final GameWindow gameWindow;

    private boolean exitClicked;


    public HelpScreen(GameWindow gameWindow, Dimension area) {

        this.gameWindow = gameWindow;

        focusWindowAndInput();
        setScreen(area);
        setButtons(area);
        setBorders();
        setFonts();

    }


    private void focusWindowAndInput() {
        // window focus
        this.setFocusable(true);
        this.requestFocusInWindow();
        // mouse listener
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }


    private void setScreen(Dimension area) {
        helpScreen = new Rectangle(new Point(0, 0), area);
        this.setPreferredSize(area);
    }


    private void setButtons(Dimension area) {
        Dimension btnDim = new Dimension(area.width / 3, area.height / 12);
        exitButton = new Rectangle(btnDim);

    }

    private void setFonts() {
        buttonFont = new Font("Monospaced", Font.PLAIN, exitButton.height - 2);
        infoTitleFont = new Font("Noto Mono", Font.BOLD, FONT_SIZE1);
        infoDescriptionFont = new Font("Noto Mono", Font.PLAIN, FONT_SIZE2);
        infoGameFont = new Font("Not Mono", Font.BOLD, FONT_SIZE3);
        info1Font = new Font("Not Mono", Font.PLAIN, FONT_SIZE2);
        infoKeysFont = new Font("Not Mono", Font.BOLD, FONT_SIZE2);
        infoScoreFont = new Font("Not Mono", Font.PLAIN, FONT_SIZE2);





    }

    private void setBorders() {
        borderStoke = new BasicStroke(BORDER_SIZE, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0, DASHES, 0);
        borderStoke_noDashes = new BasicStroke(BORDER_SIZE, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
    }

    public void paint(Graphics g) {
        drawMenu((Graphics2D) g);
    }


    public void drawMenu(Graphics2D g2d) {

        drawContainer(g2d);

        /*
        all the following method calls need a relative
        painting directly into the MenuScreen rectangle,
        so the translation is made here so the other methods do not do that.
         */
        Color prevColor = g2d.getColor();
        Font prevFont = g2d.getFont();

        double x = helpScreen.getX();
        double y = helpScreen.getY();

        g2d.translate(x, y);

        //methods calls
        drawText(g2d);
        drawButton(g2d);
        //end of methods calls

        g2d.translate(-x, -y);
        g2d.setFont(prevFont);
        g2d.setColor(prevColor);
    }

    private void drawContainer(Graphics2D g2d) {
        Color prev = g2d.getColor();

        g2d.setColor(BG_COLOR);
        g2d.fill(helpScreen);

        Stroke tmp = g2d.getStroke();

        g2d.setStroke(borderStoke_noDashes);
        g2d.setColor(DASH_BORDER_COLOR);
        g2d.draw(helpScreen);

        g2d.setStroke(borderStoke);
        g2d.setColor(BORDER_COLOR);
        g2d.draw(helpScreen);

        g2d.setStroke(tmp);

        g2d.setColor(prev);
    }


    private void drawButton(Graphics2D g2d) {

        FontRenderContext frc = g2d.getFontRenderContext();

        Rectangle2D txtRect = buttonFont.getStringBounds(EXIT_TEXT, frc);


        g2d.setFont(buttonFont);

        int x = (helpScreen.width - exitButton.width) / 2;
        int y = (int) ((helpScreen.height - exitButton.height) * 0.9);

        exitButton.setLocation(x, y);

        x = (int) (exitButton.getWidth() - txtRect.getWidth()) / 2;
        y = (int) (exitButton.getHeight() - txtRect.getHeight()) / 2;

        x += exitButton.x;
        y += exitButton.y + (exitButton.height * 0.9);


        if (exitClicked) {
            Color tmp = g2d.getColor();
            g2d.setColor(CLICKED_BUTTON_COLOR);
            g2d.draw(exitButton);
            g2d.setColor(CLICKED_TEXT);
            g2d.drawString(EXIT_TEXT, x, y);
            g2d.setColor(tmp);
        } else {
            g2d.draw(exitButton);
            g2d.drawString(EXIT_TEXT, x, y);
        }


    }

    private void drawText(Graphics2D g2d) {

        g2d.setColor(TEXT_COLOR);

        FontRenderContext frc = g2d.getFontRenderContext();

        Rectangle2D infoTitleRect = infoTitleFont.getStringBounds(infoTitle, frc);
        Rectangle2D infoDescriptionRect = infoDescriptionFont.getStringBounds(infoDescription, frc);
        Rectangle2D infoGameRect = infoGameFont.getStringBounds(infoGame, frc);
        Rectangle2D info1Rect = info1Font.getStringBounds(info1, frc);
        Rectangle2D infoKeyRect = infoKeysFont.getStringBounds(infoKeys, frc);
        Rectangle2D infoKeyRect1 = infoKeysFont.getStringBounds(infoKeys1, frc);
        Rectangle2D infoKeyRect2 = infoKeysFont.getStringBounds(infoKeys2, frc);
        Rectangle2D infoScoreRect = infoScoreFont.getStringBounds(infoScore, frc);
        Rectangle2D infoScoreRect1 = infoScoreFont.getStringBounds(infoScore1, frc);

        int sX, sY;

        //title
        sX = (int) (helpScreen.getWidth() - infoTitleRect.getWidth()) / 2;
        sY = (int) (helpScreen.getHeight() / 8);
        g2d.setFont(infoTitleFont);
        g2d.drawString(infoTitle, sX, sY);

        //description
        sX = (int) (helpScreen.getWidth() - infoDescriptionRect.getWidth()) / 2;
        sY += (int) infoDescriptionRect.getHeight() * 0.8;
        g2d.setFont(infoDescriptionFont);
        g2d.drawString(infoDescription, sX, sY);

        //Game information
        sX = (int) (helpScreen.getWidth() - (infoGameRect.getWidth()*2.6)) ;
        sY += (int) infoGameRect.getHeight() * 1.5;
        g2d.setFont(infoGameFont);
        g2d.drawString(infoGame, sX, sY);


        sX = (int) (helpScreen.getWidth() - info1Rect.getWidth())/15 ;
        sY += (int) info1Rect.getHeight() * 1;
        g2d.setFont(info1Font);
        g2d.drawString(info1, sX, sY);

        //Key binds
        sX = (int) (helpScreen.getWidth() - infoKeyRect.getWidth()) / 2;
        sY += (int) infoKeyRect.getHeight() * 2;
        g2d.setFont(infoKeysFont);
        g2d.drawString(infoKeys, sX, sY);

        sX = (int) (helpScreen.getWidth() - infoKeyRect1.getWidth()) / 2;
        sY += (int) infoKeyRect1.getHeight() * 1.5;
        g2d.setFont(infoKeysFont);
        g2d.drawString(infoKeys1, sX, sY);

        sX = (int) (helpScreen.getWidth() - infoKeyRect2.getWidth()) / 2;
        sY += (int) infoKeyRect2.getHeight() * 1.5;
        g2d.setFont(infoKeysFont);
        g2d.drawString(infoKeys2, sX, sY);

        //score info
        sX = (int) (helpScreen.getWidth() - infoScoreRect.getWidth()) / 2;
        sY += (int) infoScoreRect.getHeight() * 2.5;
        g2d.setFont(infoScoreFont);
        g2d.drawString(infoScore, sX, sY);

        sX = (int) (helpScreen.getWidth() - infoScoreRect1.getWidth()) / 2;
        sY += (int) infoScoreRect1.getHeight() * 1;
        g2d.setFont(infoScoreFont);
        g2d.drawString(infoScore1, sX, sY);



    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if (exitButton.contains(p)) {
            gameWindow.showMenu();
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if (exitButton.contains(p)) {
            exitClicked = true;
            repaint(exitButton.x, exitButton.y, exitButton.width + 1, exitButton.height + 1);
        }
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        if (exitClicked) {
            exitClicked = false;
            repaint(exitButton.x, exitButton.y, exitButton.width + 1, exitButton.height + 1);
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
        if (exitButton.contains(p))
            this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        else
            this.setCursor(Cursor.getDefaultCursor());

    }
}

