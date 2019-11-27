/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

/**
 *
 * @author heyea
 */


import eboop.reponseEBOOP;
import eboop.requeteEBOOP;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.swing.JOptionPane;

public class SessionListener implements HttpSessionListener{
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    
    @Override
    public void sessionCreated(HttpSessionEvent se)
    {
        
    }
    
    @Override
    public void sessionDestroyed(HttpSessionEvent se)
    {
        ois = (ObjectInputStream) se.getSession().getAttribute("ois");
        oos = (ObjectOutputStream) se.getSession().getAttribute("oos");
        
        send(new requeteEBOOP(requeteEBOOP.REQ_DEL_PAN,(String) se.getSession().getAttribute("numero_client")));
        reponseEBOOP rep = receive();
    }
    
    private void send(requeteEBOOP req)
    {
        try
        {
            oos.writeObject(req);
        }
        catch(IOException e)
        {
            System.err.println("SessionListener : send : "+e);
        }
    }
    
    private reponseEBOOP receive()
    {
        reponseEBOOP rep = null;
        try
        {
            rep = (reponseEBOOP)ois.readObject();
        }
        catch(Exception e)
        {
            System.err.println("SessionListener : receive : "+e);
        }
        return rep;
    }
}
