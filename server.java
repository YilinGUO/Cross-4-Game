/**
 * This class is the server for the cross 4 chess game, consisting of the 
 * server socket. The class let the two player to play consecutively, with 
 * the red player playing first.
 */
package eecs285.proj5.guoyilin;

/**
 * @author Yilin
 *
 */
public class server
{
  public static void main(String args[])
  {
    // initializes two server sockets to communicate with two clients,
    // respectively
    ClientServerSocket redSock =
        new ClientServerSocket(client.IP, client.PORT1);
    redSock.startServer();
    ClientServerSocket yellowSock =
        new ClientServerSocket(client.IP, client.PORT2);
    yellowSock.startServer();
    // let the two players to play consecutively, until the one player wins
    // the game, or the game ties
    while (true)
    {
      int lhs = redSock.recvInt();
      yellowSock.sendInt(lhs);

      int rhs = yellowSock.recvInt();
      redSock.sendInt(rhs);
    }
  }
}
