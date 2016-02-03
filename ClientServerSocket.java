/**
 * This is the base class for the client server socket. It can send 
 * and receive int.
 */
package eecs285.proj5.guoyilin;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Yilin
 *
 */
public class ClientServerSocket
{  
  private String ipAddr;
  private int portNum;
  private Socket socket;
  private DataOutputStream outData;
  private DataInputStream inData;
  
  // constructor
  public ClientServerSocket(String inIPAddr, int inPortNum)
  {
    ipAddr = inIPAddr;
    portNum = inPortNum;
    inData = null;
    outData = null;
    socket = null;
  }
  
  // starts the client
  public void startClient()
  {
    try
    {
      socket = new Socket(ipAddr, portNum);
      outData= new DataOutputStream(socket.getOutputStream());
      inData= new DataInputStream(socket.getInputStream());
    }
    catch (IOException ioe)
    {
      System.out.println("ERROR: Unable to connect -" +
      "is the server running?");
      System.exit(10);
    }
  }
  
  // starts the server
  public void startServer()
  {
    ServerSocket serverSock;
    try
    {
      serverSock= new ServerSocket(portNum);
      System.out.println("Waiting for client to connect...");
      socket = serverSock.accept();
      outData= new DataOutputStream(socket.getOutputStream());
      inData= new DataInputStream(socket.getInputStream());
      System.out.println("Client connection accepted");
    }
    catch (IOException ioe)
    {
      System.out.println("ERROR: Caught exception starting server");
      System.exit(7);
    }
  }

  // send an integer from one side to the other
  public void sendInt(int inSendInt)
  {
    try
    {
      outData.writeInt(inSendInt);
    }
    catch (IOException ioe)
    {
      System.out.println("ERROR: sending int");
      System.exit(11);
    }
  }
  
  // receives the integer sent from the other side
  public int recvInt()
  {
    int recvInt = 0;
    try
    {
      recvInt = inData.readInt();
    }
    catch (IOException ioe)
    {
      System.out.println("ERROR: receiving int from socket");
      System.exit(10);
    }
    return (recvInt);
  }
}