# Snake Game

A classic Snake game implementation in Java using Swing and the Model-View-Controller (MVC) architectural pattern.

## Description

This Snake game is a modern implementation of the classic arcade game where you control a snake to eat food and grow longer. The game features a clean, modular design following the MVC pattern and includes customizations like wall-wrapping, high score tracking, and smooth gameplay.

## Features

- Smooth, responsive gameplay with adjustable speed
- Snake passes through walls (wrap-around feature)
- High score system with player name entry
- Menu system with play and high score options
- Pause/resume functionality
- Snake speed increases as it grows longer
- Visual indicators for game over and pause states
- Clean code structure following MVC design pattern

## Requirements

- Java 8 or higher
- Java Runtime Environment (JRE)

## Installation and Running

1. Clone this repository: https://github.com/iam1010/SnakeGameMVC_V2

## How to Play

### Controls

- **Arrow Keys** or **WASD**: Control the snake's direction
- **P**: Pause/Resume the game
- **R**: Restart after game over
- **M** or **ESC**: Return to main menu

### Gameplay

1. Start the game from the main menu by clicking "Play Game"
2. Control the snake to eat the red food items
3. The snake grows longer each time it eats
4. The game ends if the snake collides with itself
5. The snake can pass through walls and emerge from the opposite side
6. Enter your name when prompted after achieving a high score

## Project Structure

The game follows the Model-View-Controller (MVC) architectural pattern:

### Model (Game Logic)
- **GameElement.java**: Abstract base class for game elements
- **SnakeHead.java**: Represents the head of the snake
- **SnakeBody.java**: Represents body segments of the snake
- **Food.java**: Represents food items
- **SnakeGame.java**: Core game logic
- **HighScore.java**: High score data structure

### View (User Interface)
- **GameFrame.java**: Main application window
- **MenuPanel.java**: Main menu interface
- **GamePanel.java**: Game rendering
- **HighScorePanel.java**: High score display
- **PlayerNameDialog.java**: Dialog for entering player names

### Controller
- **GameController.java**: Connects model and view, handles user input

## Class Inheritance

The snake is implemented using two component classes with inheritance:
- **GameElement**: Abstract base class with common properties (x, y coordinates)
  - **SnakeHead**: Extends GameElement to represent the snake's head
  - **SnakeBody**: Extends GameElement to represent snake body segments
  - **Food**: Extends GameElement to represent food items


## Customization

You can modify the following constants in the source code to customize gameplay:

- **CELL_SIZE** (in GameController.java): Size of each cell in the grid
- **BOARD_WIDTH** and **BOARD_HEIGHT** (in GameController.java): Dimensions of the game board
- **INITIAL_SPEED**, **SPEED_INCREASE**, and **MIN_SPEED** (in SnakeGame.java): Control game speed
- **MAX_HIGH_SCORES** (in SnakeGame.java): Number of high scores to save

## Future Improvements

Known issues:
- Can't choose not to enter player name when entering highscore although there is an X on the window

Potential enhancements for future versions:
- Multiple difficulty levels
- Power-ups and obstacles
- Different game modes (timed, maze, etc.)
- Customizable snake appearance
- Sound effects and background music
- Networked multiplayer mode

