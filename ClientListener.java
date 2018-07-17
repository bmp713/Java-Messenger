// Brandon M. Piper
// cmps109 W05
// Homework #5

import java.io.*;
import java.net.*;
import java.util.*;

// This thread listens for incoming messages from an
// associated client and sends those messages to the 
// ServerSender object which then sends the message
// out to all the clients.

class ClientListener extends Thread{
    private ArrayList<String> users;
    private BufferedReader input;
    private ServerSender sender;
    private String username;

    /*
    constructor reads in the input stream, the ServerSender object
    the username, and the list of users.
    */
    public ClientListener( BufferedReader input, ServerSender sender, 
                           String username, ArrayList<String> users ){
        this.username = username;
        this.input = input;
        this.sender = sender;
        this.users = users;
    }

    /*
    run initially updates the list of users and sends that
    list out to the clients.  then it goes into a loop
    reading messages from the client until they terminate
    the connection.  the list is then updated again.
    */
    public void run(){
        StringBuffer list = new StringBuffer();

        // send the new userlist string to all clients.
        list.append("%");
        for( int i = 0; i < users.size(); i++ ){
            list.append( users.get( i ) +" ");
        }
        list.append("%");
        sender.sendMessage( list.toString() );
        sender.sendMessage( "["+ username +" ENTERED THE ROOM]" );
        
        try{
            String message = input.readLine();
            while ( message != null){
                sender.sendMessage( "["+ username +"] "+ message );
                message = input.readLine();
            }
        }
        catch( IOException e ){
            System.out.println( e );
        }

        // update the list of users when user logs off.
        int i;
        for( i = 0; i < users.size(); i++ ){
            if( users.get( i ).equals( username ))break;
        }
        users.remove(i);

        list = new StringBuffer();
        list.append("%");
        for( i = 0; i < users.size(); i++ ){
            list.append( users.get( i ) +" ");
        }
        list.append("%");
        sender.sendMessage( list.toString() );

        System.out.println( username +" exiting" );
        sender.sendMessage( "["+ username +" LEFT THE ROOM]" );
    }
}
