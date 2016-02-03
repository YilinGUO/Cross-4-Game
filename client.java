/**
 * This class serves as the base class for implementing two players in the 
 * cross 4 game. The class mainly consists of the game GUI and the client
 * socket.
 */
package eecs285.proj5.guoyilin;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @author Yilin
 *
 */
public class client extends JFrame
{
  public static final int ROW_NUM = 6;
  public static final int COL_NUM = 7;
  public static final int WHITE = 0;
  public static final int RED = 1;
  public static final int YELLOW = 2;
  public static final int MAX_TEXT_SIZE = 20;
  public static final int MSG_WIDTH = 10;
  public static final int MSG_HEIGHT = 10;

  public static final String IP = "127.0.0.1";
  public static final int PORT1 = 45000;
  public static final int PORT2 = 45001;

  // the current player and the other player; if red, stored as 1; if yellow,
  // stored as 2
  private int player, other;
  private ClientServerSocket theClient;

  private int[][] grid = new int[ROW_NUM][COL_NUM];
  // components of the GUI
  private JLabel[][] boardLabel = new JLabel[ROW_NUM][COL_NUM];
  private JButton[] dropButton = new JButton[COL_NUM];
  private JButton sendButton = new JButton("send");
  private JTextField messageField = new JTextField(MAX_TEXT_SIZE);
  private JLabel msgHistoryLabel = new JLabel("");
  private JButton saveButton = new JButton("save");
  private JButton loadButton = new JButton("load");

  private URL whiteURL = getClass().getResource("/images/white.jpg");
  private URL playerURL, otherURL;
  private gameGUIListener mylistener = new gameGUIListener();

  // the constructor, initializes the GUI and start the client
  public client(String ip, int port, int inPlayer)
  {
    super("Connect 4");
    // the GUI is a border layout, consisting of a center panel (the chess
    // board and the chatting window), a bottom panel (the load and save
    // button)
    setLayout(new BorderLayout());

    theClient = new ClientServerSocket(ip, port);
    theClient.startClient();
    player = inPlayer;
    // initialize URL of the button (the playerURL and the otherURL)
    if (player == RED)
    {
      other = YELLOW;
      playerURL = getClass().getResource("/images/red.jpg");
      otherURL = getClass().getResource("/images/yellow.jpg");
    }
    if (player == YELLOW)
    {
      other = RED;
      playerURL = getClass().getResource("/images/yellow.jpg");
      otherURL = getClass().getResource("/images/red.jpg");
    }

    // initializes the center panel
    JPanel centerPan = new JPanel(new FlowLayout());
    JPanel centerLeftPan = new JPanel(new GridLayout(ROW_NUM + 1, COL_NUM));
    Box centerRightPan = Box.createVerticalBox();

    for (int cnt = 0; cnt < COL_NUM; cnt++)
    {
      dropButton[cnt] = new JButton("drop");
      centerLeftPan.add(dropButton[cnt]);
    }
    // initialize the whole chess board with white buttons
    for (int rowcnt = 0; rowcnt < ROW_NUM; rowcnt++)
    {
      for (int colcnt = 0; colcnt < COL_NUM; colcnt++)
      {
        boardLabel[rowcnt][colcnt] = new JLabel();
        boardLabel[rowcnt][colcnt].setIcon(new ImageIcon(whiteURL, "white"));
        centerLeftPan.add(boardLabel[rowcnt][colcnt]);
      }
    }

    msgHistoryLabel.setPreferredSize(new Dimension(10, 10));
    centerRightPan.add(msgHistoryLabel);
    centerRightPan.add(messageField);
    centerRightPan.add(sendButton);
    centerPan.add(centerLeftPan);
    centerPan.add(centerRightPan);

    // initializes the bottom panal
    JPanel bottomPan = new JPanel(new FlowLayout(FlowLayout.TRAILING));
    bottomPan.add(loadButton);
    bottomPan.add(saveButton);

    add(centerPan, BorderLayout.CENTER);
    add(bottomPan, BorderLayout.SOUTH);
    
    // add action listener 
    for (int cnt = 0; cnt < COL_NUM; cnt++)
    {
      dropButton[cnt].addActionListener(mylistener);
    }
    sendButton.addActionListener(mylistener);
    loadButton.addActionListener(mylistener);
    saveButton.addActionListener(mylistener);
    pack();
    setVisible(true);
  }

  public class gameGUIListener implements ActionListener
  {
    public void actionPerformed(ActionEvent event)
    {
      // if the source of the action is one of the drop button
      int row, col;
      for (col = 0; col < COL_NUM; col++)
      {
        if (event.getSource() == dropButton[col])
        {
          theClient.sendInt(col);
          for (row = ROW_NUM - 1; row >= 0; row--)
          {
            if (grid[row][col] == 0)
              break;
          }
          // set the color of the piece to be the color of the player
          grid[row][col] = player;
          boardLabel[row][col].setIcon(new ImageIcon(playerURL));
          // check whether the current move makes the player win
          if (checkWin(row, col, player))
          {
            JOptionPane.showMessageDialog(getRootPane(), "You win!");
            System.exit(0);
          }
          // if not win, disable all the drop buttons and let the other player
          // to play
          disableDrop();
          break;
        }
      }
      // if the source of the action is the loadButton
      if (event.getSource() == loadButton)
      {
        JOptionPane.showMessageDialog(getRootPane(),
            "This function isn't implemented at this time!");
        // TODO
      }
      // if the source of the action is the saveButton
      if (event.getSource() == saveButton)
      {
        JOptionPane.showMessageDialog(getRootPane(),
            "This function isn't implemented at this time!");
        // TODO
      }
      // if the source of the action is the sendButton
      if (event.getSource() == sendButton)
      {
        JOptionPane.showMessageDialog(getRootPane(),
            "This function isn't implemented at this time!");
        // TODO
      }
    }
  }

  // enables all the drop buttons with still empty spaces
  public void enableDrop()
  {
    int numOfDisable = 0;
    for (int col = 0; col < COL_NUM; col++)
    {
      if (grid[0][col] != 0)
      {
        dropButton[col].setEnabled(false);
        numOfDisable++;
      } else
        dropButton[col].setEnabled(true);
    }
    // if all the drop buttons can not be enabled, then the chess board
    // has become full, and still no one wins; therefore, the game ties
    if (numOfDisable == COL_NUM)
    {
      JOptionPane.showMessageDialog(this, "Game ties!");
      System.exit(0);
    }
  }

  // disables all the drop buttons; useful when the current round is not
  // the player
  public void disableDrop()
  {
    for (int col = 0; col < COL_NUM; col++)
    {
      dropButton[col].setEnabled(false);
    }
  }

  // when the other player plays, receive the col from the server and update
  // the chess board
  public void otherPlay()
  {
    int col = theClient.recvInt();
    // check which row is empty for the received column
    int row;
    for (row = ROW_NUM - 1; row >= 0; row--)
    {
      if (grid[row][col] == 0)
      {
        break;
      }
    }
    // change the piece to the other player
    grid[row][col] = other;
    boardLabel[row][col].setIcon(new ImageIcon(otherURL));
    // check whether the other player wins
    if (checkWin(row, col, other))
    {
      JOptionPane.showMessageDialog(this, "You lose!");
      System.exit(0);
    }
  }

  // checks whether the player wins with the current move (row, col)
  // first horizontal, next vertical, next diagonal
  private boolean checkWin(int row, int col, int player)
  {
    int cur;
    // horizontal
    for (int cnt = Math.max(col - 3, 0); cnt <= Math.min(col,
        COL_NUM - 4); cnt++)
    {
      for (cur = 0; cur < 4; cur++)
      {
        if (grid[row][cnt + cur] != player)
          break;
      }
      if (cur == 4)
        return true;
    }

    // vertical line
    if (row + 3 < ROW_NUM)
    {
      for (cur = 1; cur < 4; cur++)
      {
        if (grid[row + cur][col] != player)
          break;
      }
      if (cur == 4)
        return true;
    }

    // diagonal lines, of two directions
    for (int cnt = Math.max(Math.max(col - 3, 0), col - row); cnt <= Math
        .min(Math.min(col, COL_NUM - 4), col + ROW_NUM - row - 4); cnt++)
    {
      for (cur = 0; cur < 4; cur++)
      {
        if (grid[row - col + cnt + cur][cnt + cur] != player)
          break;
      }
      if (cur == 4)
        return true;
    }
    for (int cnt =
        Math.max(Math.max(col - 3, 0), col - ROW_NUM + row + 1); cnt <= Math
            .min(Math.min(col, COL_NUM - 4), col + row - 3); cnt++)
    {
      for (cur = 0; cur < 4; cur++)
      {
        if (grid[row + col - cur - cnt][cur + cnt] != player)
          break;
      }
      if (cur == 4)
        return true;
    }

    return false;
  }
}
