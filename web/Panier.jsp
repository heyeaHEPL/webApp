<%-- 
    Document   : Panier
    Created on : 20-nov.-2019, 21:11:20
    Author     : heyea
--%>

<%@page import="java.util.StringTokenizer"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Panier</title>
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
            <% ArrayList<String>list_traversees = new ArrayList<>();%>
            
            <table>
                <thead>
                    <tr>

                        <th>client</th>
                        <th>traversées</th>
                    </tr>
                </thead>
                <%
                        StringTokenizer st = new StringTokenizer(request.getAttribute("data_panier").toString(),",");
                        boolean tmp = false;
                        while(st.hasMoreTokens())
                        {  
                            tmp = true;
                            %>
                            <tr>
                            <%  
                                    out.println("<td>" + st.nextToken() + "</td>");
                                    String tmp_traversee = st.nextToken();
                                    list_traversees.add(tmp_traversee);
                                    out.println("<td>" + tmp_traversee + "</td>");
                            %>
                            </tr>
                            <%   
                        }

                %>
            </table>
            <%request.getSession().setAttribute("list_traversees",list_traversees);%>
            <%
            if(tmp ==false)
            {
                %>
                Il y a rien dans le panier<br>
                <%  
            }%>
            <%if(tmp == true){%>
            <button type="submit" name="action"  value="annuler_panier"><b>Vider le panier<b></button> 
            <button type="submit" name="action"  value="pay_caddie"><b>Payer<b></button><%}%>
            <button type="submit" name="action"  value="principale"><b>Retour<b></button>      
        </form>
    </body>
</html>
