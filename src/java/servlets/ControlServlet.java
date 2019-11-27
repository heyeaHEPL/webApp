package servlets;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import eboop.reponseEBOOP;
import eboop.requeteEBOOP;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author heyea
 */
@WebServlet(urlPatterns = {"/ControlServlet"})
public class ControlServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private static int NUMERO_CLIENT = 0;
    private static  int NOM_CLIENT = 1;
    private static  int PRENOM_CLIENT = 2;
    private static  int ADRESSE_CLIENT = 3;
    private static  int MAIL_CLIENT = 4;
    private static  int NATIONALITE_CLIENT = 5;
    private static  int NAISSANCE_CLIENT = 6;
    private static  int REDUCTION = 7;
    
    
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private Socket cliSock;
    
    
    
    @Override
    public void init(ServletConfig config) throws ServletException
    {
        try
        {
            super.init(config);
            requeteEBOOP req = new requeteEBOOP(requeteEBOOP.REQSTART,"start");
            ConnectServer();
            oos= new ObjectOutputStream(cliSock.getOutputStream());
            ois = new ObjectInputStream(cliSock.getInputStream());
            send(req);
            
            reponseEBOOP rep = receive();
        }
        catch(IOException ex)
        {
            System.err.println("ServletConfig : init : " + ex);
        }
    }
    
    @Override
    public void destroy()
    {
        try
        {
            oos.close();
            ois.close();
            cliSock.close();
        }
        catch(IOException e)
        {
            System.out.println("ControlServlet : destroy : "+e);
        }
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(true);
        session.setMaxInactiveInterval(30);
        requeteEBOOP req = null;
        reponseEBOOP rep = null;
        
        String action = request.getParameter("action");
        ArrayList<String> infoCli = new ArrayList<>();
        
        for(int i =0;i<=6;i++)
            infoCli.add(null);
        
        
        if(action.equals("login"))
        {
            boolean bool = false;

                if(request.getParameter("inscription") == null)
                {  
                    infoCli.set(NUMERO_CLIENT,request.getParameter("numero_client"));
                    req = new requeteEBOOP(requeteEBOOP.REQLOG,infoCli.get(NUMERO_CLIENT));
                    send(req);
                    rep = receive();

                    if(rep.GetCode() == reponseEBOOP.ACK)
                    {
                        StringTokenizer st = new StringTokenizer(rep.GetData(),",");
                        bool = true;
                        infoCli.set(NUMERO_CLIENT,st.nextToken());
                        infoCli.set(NOM_CLIENT,st.nextToken());
                        infoCli.set(PRENOM_CLIENT,st.nextToken());
                        infoCli.set(ADRESSE_CLIENT,st.nextToken());
                        infoCli.set(MAIL_CLIENT,st.nextToken());
                        infoCli.set(NATIONALITE_CLIENT,st.nextToken());
                        infoCli.set(NAISSANCE_CLIENT,st.nextToken());
                        infoCli.add(REDUCTION,"OUI");

                        session.setAttribute("numero_client",infoCli.get(NUMERO_CLIENT));
                        session.setAttribute("nom_client",infoCli.get(NOM_CLIENT));
                        session.setAttribute("prenom_client",infoCli.get(PRENOM_CLIENT));
                        session.setAttribute("adresse_client",infoCli.get(ADRESSE_CLIENT));
                        session.setAttribute("mail_client",infoCli.get(MAIL_CLIENT));
                        session.setAttribute("nationalite_client",infoCli.get(NATIONALITE_CLIENT));
                        session.setAttribute("naissance_client",infoCli.get(NAISSANCE_CLIENT));
                        session.setAttribute("reduction","OUI");
                        session.setAttribute("oos",oos);
                        session.setAttribute("ois",ois);


                        req = new requeteEBOOP(requeteEBOOP.REQ_GET_TRAV,"");
                        send(req);
                        rep = receive();

                        request.setAttribute("data_traversees",rep.GetData());
                        request.getRequestDispatcher("/jsp_principale.jsp").forward(request, response);
                        //break;
                    }
                    if(!bool)
                        request.getRequestDispatcher("/Inscription.jsp").forward(request,response);
                }
                else
                    request.getRequestDispatcher("/Inscription.jsp").forward(request,response);

        }
        if(action.equals("inscription"))
        {
            request.getRequestDispatcher("/NewClient.jsp").forward(request, response);
        }
        
        if(action.equals("newClient"))
        {
            infoCli.set(0,request.getParameter("nom"));
            infoCli.set(1,request.getParameter("prenom"));
            infoCli.set(2,request.getParameter("adresse"));
            infoCli.set(3,request.getParameter("mail"));
            infoCli.set(4,request.getParameter("pays"));
            infoCli.set(5,request.getParameter("naissance"));
            
            req = new requeteEBOOP(requeteEBOOP.REQ_BOOK,infoCli.get(NOM_CLIENT) + "," + infoCli.get(PRENOM_CLIENT) + "," + infoCli.get(ADRESSE_CLIENT) + "," + infoCli.get(MAIL_CLIENT)+ "," + infoCli.get(NATIONALITE_CLIENT)+ "," + infoCli.get(NAISSANCE_CLIENT));
            
            send(req);
            rep=receive();
            
            infoCli.set(NUMERO_CLIENT,rep.GetData());
            infoCli.set(REDUCTION,"non");
            
            session.setAttribute("numero_client",infoCli.get(NUMERO_CLIENT));
            session.setAttribute("nom_client",infoCli.get(NOM_CLIENT));
            session.setAttribute("prenom_client",infoCli.get(PRENOM_CLIENT));
            session.setAttribute("adresse_client",infoCli.get(ADRESSE_CLIENT));
            session.setAttribute("mail_client",infoCli.get(MAIL_CLIENT));
            session.setAttribute("nationalite_client",infoCli.get(NATIONALITE_CLIENT));
            session.setAttribute("naissance_client",infoCli.get(NAISSANCE_CLIENT));
            session.setAttribute("reduction","NON");
            session.setAttribute("oos",oos);
            session.setAttribute("ois",ois);
            
            req = new requeteEBOOP(requeteEBOOP.REQ_GET_TRAV,"");
            send(req);
            rep = receive();
            
            request.getRequestDispatcher("/HomePage.jsp").forward(request,response);
        }
        
        if(action.equals("deconnexion"))
        {
            if(session.getAttribute("num_client") != null)
            {
                session.invalidate();
            }
            
            request.getRequestDispatcher("/Login.jsp").forward(request, response);
        }
        
        if(action.equals("principale"))
        {
            if(session.getAttribute("num_client") != null)
            {
                if(request.getParameter("date_trav") == null)
                {
                    req = new requeteEBOOP(requeteEBOOP.REQ_GET_TRAV,"");
                }
                else
                {
                    req = new requeteEBOOP(requeteEBOOP.REQ_GET_TRAV,request.getParameter("date_trav"));
                }
                
                send(req);
                rep = receive();
                
                if(rep.GetData().equals(""))
                    request.setAttribute("data_traversees", null);
                else
                    request.setAttribute("data_traversees",rep.GetData());
                
                request.getRequestDispatcher("/HomePage.jsp").forward(request, response);
            }
            else
                request.getRequestDispatcher("/Login.jsp").forward(request, response);
        }
        
        if(action.equals("caddie"))
        {
            if(session.getAttribute("numero_client")!=null)
            {
                req = new requeteEBOOP(requeteEBOOP.REQ_SET_PAN,session.getAttribute("numero°client") + "," + request.getParameter("id_traversee"));
                
                send(req);
                rep = receive();
                
                request.setAttribute("data_panier", rep.GetData());
                request.getRequestDispatcher("/Panier.jsp").forward(request,response);
            }
            else
                request.getRequestDispatcher("/Login.jsp").forward(request, response);
                
        }
        if(action.equals("panier"))
        {
            if(session.getAttribute("num_client") != null)
            {
                req = new requeteEBOOP(requeteEBOOP.REQ_GET_PAN, (String) session.getAttribute("num_client"));
                
                send(req);
                rep = receive();
                
                request.setAttribute("data_panier", rep.GetData());
                request.getRequestDispatcher("/Panier.jsp").forward(request,response);
            }
            else
                request.getRequestDispatcher("/Login.jsp").forward(request, response);
        }
        
        if(action.equals("annuler_panier"))
        {
            if(session.getAttribute("num_client") != null)
            {
                req = new requeteEBOOP(requeteEBOOP.REQ_DEL_PAN,(String)session.getAttribute("num_client"));
                
                send(req);
                rep = receive();
                
                request.setAttribute("data_panier","");
                request.getRequestDispatcher("/Panier.jsp").forward(request, response);
            }
            else
                request.getRequestDispatcher("/Login.jsp").forward(request, response);
        }
        
        if(action.equals("paiement"))
        {
            if(session.getAttribute("numero_client") != null)
            {
                ArrayList<String> tmp = (ArrayList) request.getSession().getAttribute("list_traversees");
                String slist="";
                for(int i = 0 ; i < tmp.size() ; i++)
                    slist += tmp.get(i) + ",";
                
                String tstring = "";
                
                tstring+=String.valueOf(session.getAttribute("total")) +",";
                tstring+= (String) session.getAttribute("num_client") + ",";
                tstring+= (String) session.getAttribute("numero_carte") + ",";
                tstring+= (String) session.getAttribute("date_carte") + ",";
                tstring+= (String) session.getAttribute("nombre_passagers") + ",";
                tstring+= (String) session.getAttribute("last_minute") + ",";
                
                tstring+=slist;
                tstring = tstring.substring(0,tstring.lastIndexOf(","));
                
                req = new requeteEBOOP(requeteEBOOP.REQ_BUY,tstring);
                send(req);
                rep=receive();
                
                if(rep.GetCode() == reponseEBOOP.FAIL)
                    request.setAttribute("code","err");
                else
                    request.setAttribute("code","inf");
                
                request.setAttribute("informatoin", rep.GetData());
                request.getRequestDispatcher("/Information.jsp").forward(request, response);
            }
            else
                request.getRequestDispatcher("/Login.jsp").forward(request, response);
        }
        
        if(action.equals("pay_caddie"))
        {
            if(session.getAttribute("numero_client") != null)
            {
                request.setAttribute("list_traversees",request.getSession().getAttribute("list_traversees"));
                request.setAttribute("last_minute","NON");
                request.getRequestDispatcher("/Paiement.jsp").forward(request, response);
            }
            else
                request.getRequestDispatcher("/Login.jsp").forward(request, response);
        }
        
        if(action.equals("pay_last_minute"))
        {
            if(session.getAttribute("numero_client") != null)
            {
                ArrayList<String> tmp = new ArrayList<>();
                tmp.add(request.getParameter("id_traversee"));
                request.setAttribute("list_traversees",tmp); 
                request.setAttribute("last_minute","OUI");
                
                request.getRequestDispatcher("/Paiement.jsp").forward(request, response);
            }
            else
                request.getRequestDispatcher("/Login.jsp").forward(request, response);

        }
    }
    
    private void ConnectServer()
    {
        ois=null;
        oos=null;
        cliSock=null;
        String adresse = "localhost";
        int port = 50050;
        
        try
        {
            cliSock = new Socket(adresse,port);
        }
        catch(Exception e)
        {
            System.err.println("ControlServlet : ConnectServer : " + e);
        }
    }
    
    private void send(requeteEBOOP req)
    {
        try
        {
            oos.writeObject(req);
        }
        catch(IOException e)
        {
            System.err.println("ControlServlet : send : " + e);
        }
    }
    
    private reponseEBOOP receive()
    {
        reponseEBOOP rep = null;
        try
        {
            rep = (reponseEBOOP)ois.readObject();
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("--- erreur sur la classe = " + e.getMessage());
        }
        catch (IOException e)
        { 
            System.out.println("--- erreur IO = " + e.getMessage());
        }
        return rep;
    }
    
    private boolean ValideClient()
    {
        return true;
    }
    private boolean AddClient(ArrayList infoCli,HttpServletRequest request)
    {
        infoCli.set(0,request.getParameter("numClient"));
        return true;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
