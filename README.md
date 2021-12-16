# Brick Destroy
#### Software Maintenance Coursework

This coursework is about implementing Software Maintenance principles to an original
Brick Destroy game created by FilippoRanza. (https://github.com/FilippoRanza/Brick_Destroy)

Brick Destroy is a simple arcade video game with the objective of destroying
walls of bricks using a ball and paddle.
## Game Controls

`SPACE` to start or pause the game.     
`ESC` to access the pause menu.     
`D` to move right.      
`A` to move left.       
`ALT`+`SHIFT`+`F1` to open the console.
## More about the game
The Brick Destroyer Game created by FilippoRanza requires players to break walls using a 
paddle and a ball. These walls consists of bricks. The player would have to hit all of
the bricks with the ball to destroy the wall, which can also be known as a Level.

The game has multiple levels. These levels are differentiated by the wall composotion (clay, 
cement or steel). 

A few additional features have been added to the game as per instructed by 
the Coursework.Here are the Additional Features:
#### Score and Reward System

- Scoring System -> the game now counts your score according to the amount 
of bricks destroyed and displays it on the screen. These scores will be saved
and ranked in the High Score Leaderboard which can now also be viewed.
- Reward System -> a Bonus Multiplier for the score has been added. It multiplies 
the points a player gets when destroying bricks and adds it to the score. The more 
bricks the player hits in a row without letting the ball fall, the bigger the point
multiplication.
- Log In feature -> the game will now ask for the player's username before starting 
the game so that they can be saved to the High Score Leaderboard.
#### Information Screen
- Info/Help Screen -> A `help` button which enables the help/information screen
has been added to the Main Menu of the game. The Information Screen will display 
the basic game operations such as the game controls, brief game Objective and scoring
system. This is so that players can understand the game better. It can be closed by 
an `exit` button that will on the Information Screen.
#### Game Modifications

- Added New Levels -> 2 new Levels have been added to the game. Levels increase along 
with it's Difficulty. This makes the game longer as it has more playable levels hence 
making it more interesting and challenging. The levels from 1st to 6th are now as 
follows:

   - Clay
   - Clay + Cement
   - Clay + Steel
   - Cement
   - Steel
   - Cement + Steel
  
- Modified Menu Screen -> The menu screen's appearance has been modified. The button
layout, Colours and Texts on the Menu Screen has been changed. There are now 3 buttons 
instead of 2.
---
## Refactoring Project

- ### Package and Class
  
  - Organisation and renaming-> New Packages are made and named according to what classes they contain. Classes that are 
  related get moved and organized into Packages. Some classes have been renamed to better
  represent itself in the program. Multiple packages have been made to organize the classes
  but all are under 3 main packages which are `control`,`model` and `viewGui`.

  
  - Encapsulation -> Has been done on big classes and methods so that the code is more 
  understandable to programmers. It has been done to classes and methods that disclose the 
  actual logic and flow of that method or class.
- ### Design Pattern

  - The MVC design pattern has been implemented to the project. The model, view and 
  controller aspect of the design pattern can be seen by the package structure. Here are the
  packages:
  
    - `model` - ball, brick, player, score, levels
    - `viewGui` - GameWindow, HelpScreen, MenuScreen
    - `control` - GameBoard, debug

---

by Razeeq Suhaimee (hfyaa10) 20203994




