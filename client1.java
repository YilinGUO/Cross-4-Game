/**
 * This class is the red player client extending the client base class. The 
 * red player plays BEFORE the yellow player.
 */
package eecs285.proj5.guoyilin;

/**
 * @author Yilin
 *
 */
public class client1
{
  public static void main(String args[])
  {
    client clientRed = new client(client.IP, client.PORT1, client.RED);
    while (true)
    {
      clientRed.otherPlay();
      clientRed.enableDrop();
    }
  }
}
