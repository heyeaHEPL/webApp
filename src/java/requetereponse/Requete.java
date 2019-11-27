package requetereponse;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
/**
 *
 * @author fredm
 */
public interface Requete {
    
    //public Runnable createRunnable(Socket s, ConsoleServeur cs);
    
    public Runnable createRunnable (Socket s,ObjectOutputStream oos,ObjectInputStream ois);
}
