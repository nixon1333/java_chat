/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.net.*;
import java.io.*;
import java.util.Scanner;
import javax.swing.JOptionPane;
/**
 *
 * @author niXon
 */
public class aChatClient implements Runnable
{
    //globals
    Socket SOCK;
    Scanner INPUT;
    Scanner SEND = new Scanner(System.in);
    PrintWriter OUT;
//---------------------------------------------------------------
    public aChatClient(Socket X)
    {
        this.SOCK = X;
    }
//---------------------------------------------------------------
    public void run()
    {
        try
        {
            try
            {
                INPUT = new Scanner(SOCK.getInputStream());
                OUT = new PrintWriter(SOCK.getOutputStream());
                OUT.flush();
                CheckStrem();
            }
            finally
            {
                SOCK.close();
            }
        }
        catch(Exception X)
        {
            System.out.print(X);
        }
    }
//----------------------------------------------------------------------
    public void DISCONNECT() throws IOException
    {
        OUT.println(aChatClientGUI.UserName + " has disconnected.");
        OUT.flush();
        SOCK.close();
        JOptionPane.showMessageDialog(null, "You disconnected");
        System.exit(0);
    }
//----------------------------------------------------------------------
    public void CheckStrem()
    {
        while(true)
        {
            RECEIVE();
        }
    }
//-----------------------------------------------------------------------
    public void RECEIVE()
    {
        if(INPUT.hasNext())
        {
            String MESSAGE = INPUT.nextLine();
            
            if(MESSAGE.contains("#?!"))
            {
                String temp1 = MESSAGE.substring(3);
                       temp1 = temp1.replace("[", "");
                       temp1 = temp1.replace("]", "");
                       
                String[] CurrentUsers = temp1.split(", ");
                aChatClientGUI.JL_ONLINE.setListData(CurrentUsers);
            }
            else
            {
                aChatClientGUI.TA_CONVERSATION.append(MESSAGE + "\n");
            }
        }
    }
//--------------------------------------------------------------------------
    public void SEND(String X)
    {
        OUT.println(aChatClientGUI.UserName + ": "+ X);
        OUT.flush();
        aChatClientGUI.TF_Message.setText("");
    }
    //-------------------------------------------------------------------
}
