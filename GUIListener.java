// Brandon Piper
// cmps109 W05
// Homework #5

import java.io.*;
import java.awt.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;

/*
the GUIListener listens to the three possible events
that take place in the GUI frame.  the events are:
a message typed, send button clicked, or quit button.
*/
class GUIListener implements ActionListener{

    private ListenerThread listener;
    private JTextArea view;
    private JTextField input; 
    private String source;
    private StringBuffer board;
    private PrintWriter output;
  
    /*
    constructor takes in the message textarea, the input 
    textfield, the event source, the board, and the 
    output stream.
    */
    public GUIListener(JTextArea view, JTextField input,
                       String source, StringBuffer board,
                       PrintWriter output){
        this.view = view;
        this.input = input;
        this.board = board;
        this.source = source;
        this.output = output;
    }
    
    /*
    actionPerformed() determines which event occured and
    takes appropriate action.  if it is a message entered 
    then is creates a ClientThread to send the message to
    the server.
    */
    public void actionPerformed( ActionEvent e ){
        if( source.equals("input") || source.equals("send") ){
            ClientThread clientT = new ClientThread( output, input);
            clientT.start();
        }
        else if( source.equals("quit") ){
            System.exit(0);
        }
    }
}
