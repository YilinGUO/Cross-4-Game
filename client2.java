/**
 * This class is the yellow player client extending the client base class.
 * The yellow player plays AFTER the red player.
 */
package eecs285.proj5.guoyilin;

/**
 * @author Yilin
 *
 */
public class client2
{
  public static void main(String args[])
  {
    client clientYellow = new client(client.IP, client.PORT2, client.YELLOW);
    // red plays first, so disable the yellow drop buttons at first
    clientYellow.disableDrop();
    while (true)
    {
      clientYellow.otherPlay();
      clientYellow.enableDrop();
    }
  }
}
