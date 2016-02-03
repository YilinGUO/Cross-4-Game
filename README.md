# Cross-4-Game
Enables two players to play cross-4 while chatting concurrently

To play chess, you have to first start the server (server.java), then start
the red player (client1.java), then the yellow player (client2.java)
Note that in this game, the red player plays before yellow.

All default values can be found in client.java:
* The dimension of the chess board is 6 rows by 7 columns. You can 
change the dimension by changing the ROW_NUM and the COL_NUM.
* The user input text field is currently set as 20. You can change it by 
changing MAX_TEXT_SIZE.
* The dimension of the message history window is set as 10 by 10. You can
change MSG_WIDTH and MSG_HEIGHT.
* The ip address and the port of the two sockets are 127.0.0.1, 45000; and 
127.0.1, 45001. You can change them by changing IP, PORT1 and PORT2.

The two bonus functions have not been implemented yet.
