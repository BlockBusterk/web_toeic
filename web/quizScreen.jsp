<%-- 
    Document   : quizScreen
    Created on : Jun 15, 2023, 2:03:40 PM
    Author     : ACER
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <label> </label><br>
        <form>
            <input type="radio"  name="age" value="A">
            <label>A</label><br>
            <input type="radio"  name="age" value="B">
             <label>B</label><br>
            <input type="radio"  name="age" value="C">
             <label>C</label><br>
            <input type="radio"  name="age" value="D">
            <label>D</label><br><br>
            <button type="button">Next Question</button> <br><br>
            <input type="button" id="submitQuiz" value="Submit">
        </form>  
    </body>
</html>
