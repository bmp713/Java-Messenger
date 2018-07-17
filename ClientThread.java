
 
// Brandon Piper
// cmps109 W05
// Homework #5

import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import tio.*;

/*
This thread responds to messages from a client 
and sends those messages to the server.
*/
 
class ClientThread extends Thread{
    private PrintWriter output;
    private JTextField text;

    /*
    the constructor reads in the output stream and the
    textfield that the message was read from.
    */
    public ClientThread( PrintWriter output, JTextField text ){
        this.output = output;
        this.text = text;
    }

    /*
    the run sets the message to the message board text area
    */
    public void run(){
        String message = text.getText();
        output.println( message );
        output.flush();
        text.setText("");
        message = text.getText();
    }
}
