<%-- 
    Document   : Login
    Created on : 17-nov.-2019, 17:04:16
    Author     : heyea
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Connexion ou inscription</title>
    </head>
    <body>
        <h1>Entrez vos informations :</h1>
        
        <form method="POST" action="ControlServlet">
            numero de client : <input name="numero_client" type="text" required/>
            <br><br>
            mot de passe : <input name="password" type="text" required/>
            <button type="submit" name="action" value="login"><b>Effectuer<b></button>
        </form>
        <br>
        <form method="POST" action="ControlServlet">
            <button type="submit" name="action" value="inscription"><b>Pas encore inscrit ?<b></button>
        </form>    
    </body>
</html>
