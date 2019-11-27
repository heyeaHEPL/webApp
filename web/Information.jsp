<%-- 
    Document   : Information
    Created on : 27-nov.-2019, 11:08:45
    Author     : heyea
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Information payement</title>
    </head>
    <body>
        <form> 
            <%if(request.getAttribute("type").equals("err")){%><h1 style=" color: red"><%out.println(request.getAttribute("information"));%></h1><%}
            else {%> <h1 style="color: green"><%out.println(request.getAttribute("information"));%></h1> <%}%>
            <button type="submit" name="action"  value="principale"><b>Retour<b></button>      
        </form>
    </body>
</html>
