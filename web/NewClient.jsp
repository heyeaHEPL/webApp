<%-- 
    Document   : NewClient
    Created on : 17-nov.-2019, 17:36:49
    Author     : heyea
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Inscription</title>
    </head>
    <body>
        <h1>Completez les informations d'inscription</h1>
        
        <form method='POST' action="ControlServlet">
            nom :<input name='nom' type='text' required/><p>
            prenom :<input name='prenom' type='text' required/><p>
            adresse : <input name='adresse' type='text' required/><p>
            email:<input name='mail' type='text' required/><p>
            pays:<input name='pays' type='text' required/><p>
            date de naissance:<input name='naissance' type='text' required/><p>
                <button type='submit' name='action' value="newClient">inscription</button>
        </form>
    </body>
</html>
