package minmaxtic;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class Game implements ActionListener {
  JFrame frame = new JFrame();
  JPanel titlePanel = new JPanel();
  JPanel button = new JPanel();
  JLabel textField = new JLabel();
  JButton[] buttons = new JButton[9];

  Game() {
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(800, 800);
    frame.setResizable(false);
    frame.getContentPane().setBackground(new Color(50, 50, 50));
    frame.setLayout(new BorderLayout());
    frame.setVisible(true);
    frame.setLocationRelativeTo(null);

    textField.setBackground(new Color(25, 25, 25));
    textField.setForeground(new Color(25, 255, 0));
    textField.setFont(new Font("Ink Free", Font.BOLD, 75));
    textField.setHorizontalAlignment(JLabel.CENTER);
    textField.setText("Tic-Tac-Toe");
    textField.setOpaque(true);

    titlePanel.setLayout(new BorderLayout());
    titlePanel.setBounds(0, 0, 800, 100);

    button.setLayout(new GridLayout(3, 3));
    button.setBackground(new Color(150, 150, 150));

    for (int i = 0; i < 9; i++) {
      buttons[i] = new JButton();
      button.add(buttons[i]);
      buttons[i].setFont(new Font("Ink Free", Font.BOLD, 120));
      buttons[i].setFocusable(false);
      buttons[i].setBackground(new Color(125, 125, 125));
      buttons[i].setBorder(new LineBorder(Color.black, 2));
      buttons[i].addActionListener(this);
    }

    titlePanel.add(textField);
    frame.add(titlePanel, BorderLayout.NORTH);
    frame.add(button);
  }
  public String checkWinner() {
    for (int i = 0; i < 8; i++) {
      String line = switch (i) {
        case 0 -> buttons[0].getText() + buttons[1].getText() + buttons[2].getText();
        case 1 -> buttons[3].getText() + buttons[4].getText() + buttons[5].getText();
        case 2 -> buttons[6].getText() + buttons[7].getText() + buttons[8].getText();
        case 3 -> buttons[0].getText() + buttons[3].getText() + buttons[6].getText();
        case 4 -> buttons[1].getText() + buttons[4].getText() + buttons[7].getText();
        case 5 -> buttons[2].getText() + buttons[5].getText() + buttons[8].getText();
        case 6 -> buttons[0].getText() + buttons[4].getText() + buttons[8].getText();
        case 7 -> buttons[2].getText() + buttons[4].getText() + buttons[6].getText();
        default -> null;
      };
      if (line.equals("XXX")) {
        return "X";
      } else if (line.equals("OOO")) {
        return "O";
      }
    }
    for (int i = 0; i < 9; i++) {
      if (buttons[i].getText().equals("")) {
        return null;
      }
    }
    return "Tie";
  }
  public void check() {
    String winner = checkWinner();
    if (winner != null) {
      for (int i = 0; i < 9; i++) {
        buttons[i].setEnabled(false);
      }
      if (winner.equals("X")) {
        textField.setText(winner + " wins!");
      } else if (winner.equals("O")) {
        textField.setText(winner + " wins!");
      } else {
        textField.setText("It's a Tie!");
      }
      Font font = buttons[4].getFont();
      buttons[4].setEnabled(true);
      buttons[4].setFont(new Font(font.getName(), font.getStyle(), 50));
      buttons[4].setForeground(Color.black);
      buttons[4].setText("Restart");
    }
  }
  public boolean isBoardFull() {
    int count = 0;
    for (int i = 0; i < 9; i++) {
      if (buttons[i].getText().equals("")) {
        count++;
      }
    }
    return count == 9;
  }
  public int minmax(boolean player1, int depth) {
    String winner = checkWinner();
    if (winner != null) {
      if (winner.equals("O")) return 10 - depth;
      else if (winner.equals("X")) return depth - 10;
      else return 0;
    }
    if (isBoardFull()) return 0;
    int bestScore;
    if (player1) {
      bestScore = Integer.MIN_VALUE;
      for (int i = 0; i < 9; i++) {
        if (buttons[i].getText().equals("")) {
          buttons[i].setText("O");
          int currentScore = minmax(false, depth + 1);
          buttons[i].setText("");
          bestScore = Math.max(bestScore, currentScore);
        }
      }
    } else {
      bestScore = Integer.MAX_VALUE;
      for (int i = 0; i < 9; i++) {
        if (buttons[i].getText().equals("")) {
          buttons[i].setText("X");
          int currentScore = minmax(true, depth + 1);
          buttons[i].setText("");
          bestScore = Math.min(bestScore, currentScore);
        }
      }
    }
    return bestScore;
  }
  public void aiTurn() {
    int bestScore = Integer.MIN_VALUE;
    int bestMove = -1;
    for (int i = 0; i < 9; i++) {
      if (buttons[i].getText().equals("")) {
        buttons[i].setText("O");
        int currentScore = minmax(false, 0);
        buttons[i].setText("");
        if (currentScore > bestScore) {
          bestScore = currentScore;
          bestMove = i;
        }
      }
    }
    if (bestMove != -1) {
      buttons[bestMove].setForeground(Color.blue);
      buttons[bestMove].setText("O");
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    for (int i = 0; i < 9; i++) {
      if (e.getSource() == buttons[i] && buttons[i].getText().equals("")) {
        buttons[i].setForeground(Color.red);
        buttons[i].setText("X");
        check();
        aiTurn();
        check();
      }
      if (e.getSource() == buttons[4] && buttons[4].getText().equals("Restart")) {
        frame.setVisible(false);
        new Game();
      }
    }
  }
}