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
package hfyaa10.gui;

import hfyaa10.model.level.GameBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

// the window of the game (view)
public class GameWindow extends JFrame implements WindowFocusListener {

    private static final String DEF_TITLE = "Brick Destroyer!";
    private static final int MENU_HEIGHT = 300;
    private static final int MENU_WIDTH = 450;

    private GameBoard gameBoard;
    private MenuScreen menuScreen;
    private HelpScreen helpScreen;

    private boolean focusWindow;

    public GameWindow(){
        super();

        focusWindow = false;

        this.setLayout(new BorderLayout());

        gameBoard = new GameBoard(this);
        menuScreen = new MenuScreen(this,new Dimension(MENU_WIDTH,MENU_HEIGHT));
        helpScreen = new HelpScreen(this,new Dimension(MENU_WIDTH,MENU_HEIGHT)); // same size as menu screen

        showHomeMenu();
    }

    public void showHomeMenu(){
        this.add(menuScreen,BorderLayout.CENTER);
        this.setUndecorated(true);
    }

    public void initialize(){
        this.setTitle(DEF_TITLE);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.makeWindowCentred();
        this.setVisible(true);
    }

    public void showGameWindow(){
        this.dispose();
        this.remove(menuScreen);
        this.add(gameBoard,BorderLayout.CENTER);
        this.setUndecorated(false);
        initialize();
        /*to avoid problems with graphics focus controller is added here*/
        this.addWindowFocusListener(this);

    }

    public void showHelpFrame() {
        this.dispose();
        this.remove(menuScreen);
        this.add(helpScreen, BorderLayout.CENTER);
        this.setUndecorated(false);
        initialize();
        /*to avoid problems with graphics, focus controller is added here*/
        this.addWindowFocusListener(this);


    }



    public void showMenu() {
        this.dispose();
        this.remove(helpScreen);
        this.add(menuScreen, BorderLayout.CENTER);
        this.setUndecorated(false);
        initialize();
        /*to avoid problems with graphics, focus controller is added here*/
        this.addWindowFocusListener(this);

    }

    // makes window centered
    private void makeWindowCentred(){
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (size.width - this.getWidth()) / 2;
        int y = (size.height - this.getHeight()) / 2;
        this.setLocation(x,y);
    }


    @Override
    public void windowGainedFocus(WindowEvent windowEvent) {
        /*
            the first time the frame loses focus is because
            it has been disposed to install the GameBoard,
            so went it regains the focus it's ready to play.
            of course calling a method such as 'onLostFocus'
            is useful only if the GameBoard as been displayed
            at least once
         */
        focusWindow = true;
    }

    @Override
    public void windowLostFocus(WindowEvent windowEvent) {
        if(focusWindow)
            gameBoard.onLostFocus();

    }
}
