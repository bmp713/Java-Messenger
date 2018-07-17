
    
// Brandon Piper
// cmps109 W05
// Homework #5

import java.io.*;
import java.net.*;
import java.util.*;

/*
ChatServer is the server that handles all client connections
in the chat room.  It has an object that listens for any
message from a client and then relays that message to all 
connected clients.
*/
class ChatServer{
    public static void main (String args[])
        throws java.io.IOException
    {
        if (args.length != 1) {
            System.out.println("Usage: "+"java chatServer portnumber");
            System.exit(1);
        }
        
        int portnum = Integer.parseInt(args[0]);
        ServerSocket sock = null;
        
        try{
            sock = new ServerSocket(portnum);
        } 
        catch(IOException e){
            System.out.println("Could not listen on port: "+portnum+", "+e);
            System.exit(1);
        }
        System.out.println("Now listening at port " + portnum);
        
        Socket clientSocket = null;
        
        //create list of connected sockets.
        ArrayList<Socket> sockets = new ArrayList<Socket>();
       
        //create a list of user names.
        ArrayList<String> users = new ArrayList<String>();

        //create one ServerSender object that each client
        //will point at.  the sender will relay all messages
        //received by the server to all connected clients.
        ServerSender sender = new ServerSender( sockets );
        
        //listen for connection requests.
        while(true){
            try{
                clientSocket = sock.accept();
            } 
            catch(IOException e){
                System.out.println("Accept failed: "+ portnum +", "+ e);
                System.exit(1);
            }
            
            BufferedReader input = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
            
            String course = input.readLine();
            
            if( course.equals("cmps109") ){
                
                String user = input.readLine();
                System.out.println( user +" connected.");
		//System.out.println("Connection with "+ user +" established.");
                sockets.add( clientSocket );
                users.add( user );
                
                        
                //create ClientListener to listen for new messages
                ClientListener listener = 
                    new ClientListener( input, sender, user, users );
                listener.start();
            }
            else{
                System.out.println("No connection established.");
            }
        }
    }
}

    
    
