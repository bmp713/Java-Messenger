// Brandon Piper
// cmps109 W05
// Homework #5

import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;
import java.awt.Font;
import javax.swing.*;


/*
ChatClient established a connection with the server
and creates the GUI frame for the user to enter and
receive messages.
*/
class ChatClient{
    public static void main ( String args[] )
        throws java.io.IOException
    {
	if( args.length != 3 ){
	    System.out.println("Usage: java ChatClient hostname port user");
	    System.exit( 0 );
	}
        int portnum = Integer.valueOf(args[1]).intValue();
        String username = args[2];
        
        Socket sock = new Socket(args[0], portnum);
        
        BufferedReader input = new BufferedReader(
            new InputStreamReader(sock.getInputStream()));
        
        //output socket to server.
        PrintWriter output = 
            new PrintWriter(sock.getOutputStream());

        StringBuffer board = new StringBuffer();

        // set up the GUI Frame.
        JFrame frame = new JFrame( username );
        Container pane = frame.getContentPane();
        JTextField text  = new JTextField(30);
        JTextArea  users = new JTextArea();
        JTextArea  view  = new JTextArea();
	users.setFont( new Font( "", Font.ITALIC, 13 ) );
	view.setFont ( new Font( "", Font.BOLD, 13 ) );

        JButton quit = new JButton("Quit");
        JButton send = new JButton("Send");
        GUIListener quitL  = new GUIListener(view, text, "quit", board, output);
        GUIListener sendL  = new GUIListener(view, text, "send", board, output);
        GUIListener textL  = new GUIListener(view, text, "input",board, output);
        quit.addActionListener( quitL );
        send.addActionListener( sendL );
        text.addActionListener( textL );
        JPanel bottom = new JPanel();
        JPanel top    = new JPanel();
        bottom.add( text );
        bottom.add( send );
        bottom.add( quit );
        view.setText( "Message Area" );
        view.setColumns(30);
        view.setRows(11);
        users.setText( "Users Area" );
        users.setColumns(10);
        users.setRows(8);
        top.add( new JScrollPane(view), BorderLayout.CENTER );
        top.add( new JScrollPane(users), BorderLayout.CENTER );

	pane.add( top, BorderLayout.NORTH );
        pane.add( bottom, BorderLayout.SOUTH ); 
        
        frame.pack();
        frame.setVisible( true );
        
        //show that connection was established.
        output.print("cmps109\n"+ username +"\n");
        output.flush();
        System.out.println("Connection established.");
        
        //create thread to listen for messages from server.
        ListenerThread listener = new ListenerThread( 
                                  input, view, board, users );
        listener.start();
    }
}
