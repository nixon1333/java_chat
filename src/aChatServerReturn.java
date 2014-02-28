/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.*;
import java.net.*;
import java.util.Scanner;
/**
 *
 * @author niXon
 */
public class aChatServerReturn implements Runnable
{
    //globals
    Socket SOCK;
    private Scanner INPUT;
    private PrintWriter OUT;
    String MESSAGE = "";
//------------------------------------------------------------- 
    public aChatServerReturn(Socket X)
    {
        this.SOCK = X;
    }
//-------------------------------------------------------------
    public void CheckConnection() throws IOException
    {
        if(!SOCK.isConnected())
        {
            for(int i = 1; i <= aChatServer.ConnectionArray.size(); i++)
            {
                if(aChatServer.ConnectionArray.get(i) == SOCK)
                {
                    aChatServer.ConnectionArray.remove(i);
                }
            }
            
            for (int i = 1; i <= aChatServer.ConnectionArray.size(); i++)
            {
                Socket TEMP_SOCK = (Socket) aChatServer.ConnectionArray.get(i - 1);
                PrintWriter TEMP_OUT = new PrintWriter(TEMP_SOCK.getOutputStream());
                TEMP_OUT.println(TEMP_SOCK.getLocalAddress().getHostAddress() + " disconnected");
                TEMP_OUT.flush();
                //show disconnection at server
                System.out.println(TEMP_SOCK.getLocalAddress().getHostName() + " disconnected");
            }
        }
    }
//--------------------------------------------------------
    public void run()
    {
        try
        {
            try
            {
                INPUT = new Scanner(SOCK.getInputStream());
                OUT = new PrintWriter(SOCK.getOutputStream());
                
                while(true)
                {
                    CheckConnection();
                    
                    if(!INPUT.hasNext())
                    {
                        return;
                    }
                    
                    MESSAGE = INPUT.nextLine();
                    
                    System.out.println("Client said: " +MESSAGE);
                    
                    for(int i = 1; i<= aChatServer.ConnectionArray.size(); i++)
                    {
                        //if necessay take casr below out.. this addition to make to compile
                        Socket TEMP_SOCK = (Socket) aChatServer.ConnectionArray.get(i - 1);
                        PrintWriter TEMP_OUT = new PrintWriter(TEMP_SOCK.getOutputStream());
                        TEMP_OUT.println(MESSAGE);
                        TEMP_OUT.flush();
                        System.out.println("Sent to: " + TEMP_SOCK.getLocalAddress().getHostName());
                    }//close this loop
                    
                }//close while loop
                
            }//close ineer try
            finally
            {
                SOCK.close();
            }
        }//close last try
        catch(Exception X)
        {
            System.out.println(X);
        }
    } 
    
}
