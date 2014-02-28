/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JOptionPane;
/**
 *
 * @author niXon
 */
public class aChatServer {
    
    //Globals
    public static ArrayList<Socket> ConnectionArray = new ArrayList<Socket>();
    public static ArrayList<String> CurrentUsers = new ArrayList<String>();
    //-----------------------------------------------------------------------
    
    public static void main(String[] args) throws IOException
    {
        try
        {
            final int PORT = 1333;
            ServerSocket SERVER = new ServerSocket(PORT);
            System.out.println("Waiting for clients....");
            
            while(true)
            {
                Socket SOCK = SERVER.accept();
                ConnectionArray.add(SOCK);
                
                System.out.println("Client Connected from: "+ SOCK.getLocalAddress().getHostName());
                
                AddUserName(SOCK);
                
                aChatServerReturn CHAT = new aChatServerReturn(SOCK);
                Thread X = new Thread(CHAT);
                X.start();
            }
        }
        catch(Exception X)
        {
            System.out.print(X);
        }
    }
    //-------------------------------------------------------------------------------
    
    public static void AddUserName(Socket X) throws IOException
    {
        Scanner INPUT = new Scanner(X.getInputStream());
        String UserName = INPUT.nextLine();
        CurrentUsers.add(UserName);
        
        for(int i = 1; i <= aChatServer.ConnectionArray.size(); i++)
        {
            Socket TEMP_SOCK = (Socket) aChatServer.ConnectionArray.get(i-1);
            PrintWriter OUT = new PrintWriter(TEMP_SOCK.getOutputStream());
            OUT.println("#?!" + CurrentUsers);
            OUT.flush();
        }
    }
}
