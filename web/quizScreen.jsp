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
        <img src="image_sound\41.png" alt="Girl in a jacket" width="300" height="300"><br>
        <audio controls>
            <source src="image_sound\41.MP3" type="audio/mpeg">
            Your browser does not support the audio tag.
        </audio><br>
        <form>
            <input type="radio"  name="age" value="A">
            <label>A</label><br>
            <input type="radio"  name="age" value="B">
             <label>B</label><br>
            <input type="radio"  name="age" value="C">
             <label>C</label><br>
            <input type="radio"  name="age" value="D">
            <label>D</label><br><br>
            
            <input type="submit" value="Submit">
        </form>  
    </body>
</html>
