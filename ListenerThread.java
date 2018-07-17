// Brandon Piper

import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;

/*
This thread listens to the main socket for any messages
from the server and displays them to the client.
Outputs all messages received to the JTextArea.
*/
class ListenerThread extends Thread{
    private BufferedReader input;
    private JTextArea view;
    private JTextArea list;
    private StringBuffer board;
    /*
    constructor reads in the socket, the message textarea,
    the user textarea, and the board.
    */
    public ListenerThread( BufferedReader input, JTextArea view,
                           StringBuffer board, JTextArea list ){
        this.input = input;
        this.board = board;
        this.view = view;
        this.list = list;
    }

    /*
    the run basically determines whether the message it receives
    is a userlist update or a message to post to the message area.
    */
    public void run(){
        try{
            String message = input.readLine();

            while( message != null ){
                if( message.charAt(0) == '%' ){
                    message = message.substring(1,message.length()-1);
                    StringBuffer userlist = new StringBuffer();
                    StringTokenizer users = new StringTokenizer( message );
                    
                    while( users.hasMoreTokens() ){
                        String user = users.nextToken();
                        userlist.append( user +"\n");
                    }
                    list.setText( userlist.toString() );
                }
                else{
                    board.append( message +"\n");
                    view.setText( board.toString() );
		    view.setCaretPosition( board.toString().length() );
                }
                message = input.readLine();
            }
        }
        catch( IOException e ){
            System.out.println( e );
        }
    }
}
