<%-- 
    Document   : HomePage
    Created on : 17-nov.-2019, 17:30:31
    Author     : heyea
--%>

<%@page import="java.util.StringTokenizer"%>
<%@page import="java.sql.ResultSetMetaData"%>
<%@page import="java.sql.ResultSet"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Acceuil</title>
        
        <style>
        table, th, td 
        {
          border: 1px solid black;
          color: black;
          border-collapse: collapse;
        }
        </style>
    </head>
    <header>
        <form method="POST" action="ControlServlet">
            Bienvenue <%out.println(request.getSession().getAttribute("nom_client")+" "+request.getSession().getAttribute("prenom_client")); %>
             <button type="submit" name="action"  value="panier"><b>Panier<b></button>
             <button type="submit" name="action"  value="deconnexion"><b>Déconnexion<b></button>
        </form>
    </header>
             
    <br><br>
             
    <body>
        <form method="POST" action="ControlServlet">
            <b style="color: blue">Liste des traversees disponnible</b><br>
            <b style="color: red">Payer tout de suite pour la réduction</b>
            
                    <table>
            <%
                if(request.getAttribute("data_traversees") != null)
                {
                    String data_traversee = (String)request.getAttribute("data_traversees");
            %>
          <thead>
              <tr>
            <%
                    out.println("<th>"+ "ID_TRAVERSEE" +"</th>");
                    out.println("<th>"+ "DATE DE DEPART" +"</th>");
                    out.println("<th>"+ "PORT DEPART" +"</th>");
                    out.println("<th>"+ "PORT SOURCE" +"</th>");
                    out.println("<th>"+ "NAVIRE" +"</th>");
                    out.println("<th>"+ "OPTION D'ACHAT" +"</th>");
            %>
            </tr>
          </thead >
            <%
                    StringTokenizer st = new StringTokenizer(data_traversee,",");
                    while(st.hasMoreTokens())
                    { %>
                        <tr>
                        <%
                                String tmp = st.nextToken();
                                out.println("<td align=\"center\">" + tmp + "</td>");
                                out.println("<td align=\"center\">" + st.nextToken() + "</td>");
                                out.println("<td align=\"center\">" + st.nextToken() + "</td>");
                                out.println("<td align=\"center\">" + st.nextToken() + "</td>");
                                out.println("<td align=\"center\">" + st.nextToken() + "</td>");
                         %>
                            <td >
                                <p>

                                    <label><input id="cad" type="radio" name="action" value="caddie" required><span style="color: green">Ajouter au panier !</span></label>
                                    <label><input id="las" type="radio" name="action" value="pay_last_minute" required><span style="color: red">Acheter et payer !</span></label>
  
                                <button style="color: black" type="submit" name="id_traversee" value="<%=tmp%>"><b>Acheter !</b></button>  
                                <p>
                            </td>
                        </tr>
            <%   
                    }
                }
            %>
            </table>
            <br><br> 
        </form>
            <form method="POST" action="servletControl">
                <b style="color: gold">Filtrer sur date(dd-mm-yyy) <b> <pre> <input name="date_trav" type="text"/></pre><p>
                            <button style="color: gold" type="submit" name="action" value="principale"><b>Use.</b></button>
            </form>
    </body>
</html>
