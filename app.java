import javax.swing.*;
public class app {

    public static void main(String[] args) {
        int boardWidth = 600;
           int boardHeight=boardWidth;
           JFrame frame = new JFrame("snake game");
           frame.setVisible(true);
           frame.setSize(boardWidth, boardHeight);
           frame.setLocationRelativeTo(null);
           frame.setResizable(false);
           frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
           snakeGame sg = new snakeGame(boardHeight, boardWidth);
           frame.add(sg);
           frame.pack();
           sg.requestFocus();;
    }
}