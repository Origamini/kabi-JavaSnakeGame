import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class snakeGame extends JPanel implements ActionListener, KeyListener {
   private class Tile {

      int x, y;

      Tile(int x, int y) {
         this.x = x;
         this.y = y;
      }
   }

   int boardHeight;
   int boardWidth;
   int tileSize = 25;
   Tile snakehead;
   ArrayList<Tile> snakeBody;
   Tile food;
   Random random;

   // game logic

   Timer gameLoop;
   int velocityX, velocityY;
   boolean gameOver = false;

   snakeGame(int boardHeight, int boardWidth) {
      this.boardHeight = boardHeight;
      this.boardWidth = boardWidth;
      setPreferredSize(new Dimension(this.boardHeight, this.boardWidth));
      setBackground(Color.black);
      addKeyListener(this);
      setFocusable(true);
      snakehead = new Tile(5, 5);
      snakeBody = new ArrayList<Tile>();
      food = new Tile(10, 10);
      random = new Random();
      placeFood();
      velocityX = 0;
      velocityY = 0;

      gameLoop = new Timer(100, this);
      gameLoop.start();

   }

   public void paintComponent(Graphics g) {
      super.paintComponent(g);
      draw(g);
   }

   public void draw(Graphics g) {
      // for (int i = 0; i < boardWidth / tileSize; i++) {
      //    g.drawLine(i * tileSize, 0, i * tileSize, boardHeight);
      //    g.drawLine(0, i * tileSize, boardWidth, i * tileSize);
      // }

      // food color
      g.setColor(Color.RED);
      //g.fillRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize);
            g.fill3DRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize, true);

      // snake
      g.setColor(Color.green);
      //g.fillRect(snakehead.x * tileSize, snakehead.y * tileSize, tileSize, tileSize);
            g.fill3DRect(snakehead.x * tileSize, snakehead.y * tileSize, tileSize, tileSize,true);

      // snake body

      for (int i = 0; i < snakeBody.size(); i++) {
         Tile snakePart = snakeBody.get(i);
         //g.fillRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize);
         g.fill3DRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize,true);
      }

      // score
      g.setFont(new Font("Arial", Font.PLAIN, 16));
      if (gameOver) {
         g.setColor(Color.red);
         g.drawString("Game Over :" + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
      } else {
         g.drawString("Score: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
      }
   }

   public void placeFood() {
      food.x = random.nextInt(boardWidth / tileSize);
      food.y = random.nextInt(boardHeight / tileSize);

   }

   public boolean collision(Tile tile1, Tile tile2) {
      return tile1.x == tile2.x && tile1.y == tile2.y;
   }

   public void move() {

      // eating food
      if (collision(snakehead, food)) {
         snakeBody.add(new Tile(food.x, food.y));
         placeFood();
      }
      // snake body

      for (int i = snakeBody.size() - 1; i >= 0; i--) {
         Tile snakePart = snakeBody.get(i);
         if (i == 0) {
            snakePart.x = snakehead.x;
            snakePart.y = snakehead.y;
         } else {
            Tile prevsnakePart = snakeBody.get(i - 1);
            snakePart.x = prevsnakePart.x;
            snakePart.y = prevsnakePart.y;
         }
      }
      // snake head
      snakehead.x += velocityX;
      snakehead.y += velocityY;

      // game over conditions
      for (int i = 0; i < snakeBody.size(); i++) {
         Tile snakePart = snakeBody.get(i);
         // collide with the snake head

         if (collision(snakehead, snakePart)) {
            gameOver = true;
         }
      }

      if (snakehead.x * tileSize < 0 || snakehead.x * tileSize > boardWidth || snakehead.y * tileSize < 0
            || snakehead.y * tileSize > boardHeight) {
         gameOver = true;
      }

   }

   @Override
   public void actionPerformed(ActionEvent e) {
      move();
      repaint();
      if (gameOver) {
         gameLoop.stop();
      }
   }

   @Override
   public void keyPressed(KeyEvent e) {
      if (e.getKeyCode() == KeyEvent.VK_UP && velocityY != 1) {
         velocityX = 0;
         velocityY = -1;
      } else if (e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -1) {
         velocityX = 0;
         velocityY = 1;
      } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != -1) {
         velocityX = 1;
         velocityY = 0;
      } else if (e.getKeyCode() == KeyEvent.VK_LEFT &&
            velocityX != 1) {
         velocityX = -1;
         velocityY = 0;
      }
   }

   // do not need this
   @Override
   public void keyTyped(KeyEvent e) {
   }

   @Override
   public void keyReleased(KeyEvent e) {
   }
}
