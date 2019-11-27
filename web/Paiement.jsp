<%-- 
    Document   : Paiement
    Created on : 27-nov.-2019, 11:22:09
    Author     : heyea
--%>

<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Achat last minute</title>
        
        <style>
        table, th, td 
        {
          border: 1px solid black;
          color: black;
          border-collapse: collapse;
        }
        </style>
    </head>
    <body>
        <form method="POST" action="servlet_controleur">
        <% int nbl = 0,prix= 0,total = 0;double promo = 0;ArrayList<String>list_traversees = (ArrayList)request.getAttribute("list_traversees");%>
        Information sur la commande !<br>
            <table>
            <thead>
            <tr>
                <th>client</th>
                <th>traversées</th>
            </tr>
            </thead>         
            <%  
                for(int i =0;i < list_traversees.size();i++)
                {
                    %><tr><%                  
                    out.println("<td>" + request.getSession().getAttribute("numero_client") + "</td>");
                    out.println("<td>" + list_traversees.get(i)+ "</td>"); 
                    %><tr><% 
                    nbl++;
                }
            %>
            </tr>
            </table>
            <br>
            Information sur vous !<br>
                
            Numéro  : <%out.println(request.getSession().getAttribute("numero_client"));%><br>
            Nom : <%out.println(request.getSession().getAttribute("nom_client"));%><br>
            Prénom : <%out.println(request.getSession().getAttribute("prenom_client"));%><br>
            Adresse postal : <%out.println(request.getSession().getAttribute("adresse_client"));%><br>
            Adresse mail : <%out.println(request.getSession().getAttribute("mail_client"));%><br>
            Nationalite : <%out.println(request.getSession().getAttribute("nationalite_client"));%><br>
            Date de naissance : <%out.println(request.getSession().getAttribute("naissance_client"));%><br><br>
             
            Vous achetez <%out.println(nbl);%> article !<br><br>
            <%prix = ((0 + (int)(Math.random() * ((6000 - 0) + 1))) * nbl);%>
            
            Prix de base : <div style="color: green"><%out.println(prix + " euros (prix unitaire = " + prix/nbl +" euros)");%></div>
           
            <%if(request.getAttribute("last_minute").equals("OUI"))
            {
                prix = prix/2;%>
                C'est un last minute ! (50% de réduction) : <div style="color: green"><%out.println(prix + " euros");%></div>
          <%}%>  
          
          
          
                <% if(request.getSession().getAttribute("reduction").equals("OUI")){ promo = prix*0.05; total = prix - (int)promo;%>
                Vous avez droit à 5 % de rédcution (ancien client)<div style="color: green"><%out.println(promo +" euros");%></div>
                <%}else total = prix;%>
                
                Voici le prix à payer<div style="color: green"><%out.println(total +" euros");%></div></br>
                
                Nous avons besoin d'autres informations<br>
                
               Numéro de carte (XXX-XXX-XXX) : <div><input name="numero_carte" type="text"  required/> </div><p>
               Date de validité de la carte : <div><input name="date_carte" type="text"  required/> </div><p>
               Nombre de passagers (en plus) : <div><input name="nombre_passagers" type="number"  required/> </div><p>
               <%request.getSession().setAttribute("list_traversees",list_traversees);%>
               <%request.getSession().setAttribute("total",total);%>
               <%request.getSession().setAttribute("last_minute",request.getAttribute("last_minute"));%>

            <button type="submit" name="action"  value="paiement"><b>Payer<b></button>
        </form>
    </body>
</html>
