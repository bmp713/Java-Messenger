// Brandon Piper

import java.io.*;                                                       
import java.net.*; 
import java.util.*; 

/*
The ServerSender object keeps track of a list
of sockets to each connected client. 
*/
class ServerSender{
    private ArrayList<Socket> sockets;
    StringBuffer users;

    public ServerSender( ArrayList<Socket> sockets ){
	this.sockets = sockets;
    }
    
/*
sendLine takes in a message to be sent to all 
connected clients.
*/
    void sendMessage( String message ){
	try{
	    Socket client = null;
	    for(int i = 0; i < sockets.size(); i++){
		client = sockets.get( i );
		PrintWriter output = new PrintWriter(
		    client.getOutputStream());
		output.println( message );
		output.flush();
	    }
	}
	catch( IOException e ){
	    System.out.println( e );
	}
	
	
    }
}
