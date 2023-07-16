/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.time.LocalDate;
import javax.servlet.ServletContext;

/**
 *
 * @author VTN
 */
@WebServlet(urlPatterns = {"/question"})
public class question extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public static Connection getConnection(String dbURL, String userName,
            String password) {
        Connection conn = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection(dbURL, userName, password);
            System.out.println("connect successfully!");
        } catch (Exception ex) {
            System.out.println("connect failure!");
            ex.printStackTrace();
        }
        return conn;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String severNameCty = "LAPTOP-AR34IMPG\\SQLEXPRESS";
        String DB_URL = "jdbc:sqlserver://" + severNameCty + ":1433;"
                + "databaseName=web_toeic"
                + ";encrypt=true;trustServerCertificate=true;";
        String USER_NAME = "sa";
        String PASSWORD = "Huyho@ng432002";
        String email = request.getParameter("email");
        String part = request.getParameter("part");

        if (email == null) {
            ServletContext servletContext = getServletContext();
            email = servletContext.getAttribute("email").toString();
        }
        if (part == null) {
            ServletContext servletContext = getServletContext();
            part = servletContext.getAttribute("part").toString();
        }
        int idCauHoi = 0;
        int socau = 0;
        if (part.equals("1") || part.equals("2") || part.equals("5")) {
            socau = 5;
        } else if (part.equals("3") || part.equals("4") || part.equals("6") || part.equals("7")) {
            socau = 3;
        } else {
            socau = 10;
        }
        try (PrintWriter out = response.getWriter()) {
            // connnect to database 'testdb'
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet NewServlet</title>");
            out.println("<script>");
            out.println(" function Back()");
            out.println("{");
            out.println("location.replace(\"Home\")");
            out.println("}");
            out.println("</script>");
            out.println("</head>");
            out.println("<body>");

            Connection conn = getConnection(DB_URL, USER_NAME, PASSWORD);
            // crate statement
            Statement stmtQuestion = conn.createStatement();
            Statement stmtChoice = conn.createStatement();
            Statement stmtCount = conn.createStatement();
            if (part.equals("1") || part.equals("2") || part.equals("5")) {
                ResultSet rsQuestion = stmtQuestion.executeQuery("Exec get_question " + part + ",'" + email + "'");

                // show data
                while (rsQuestion.next()) {
                    if (rsQuestion.getInt(4) < socau) {
                        idCauHoi = rsQuestion.getInt(1);
                        getServletContext().setAttribute("idCauHoi", idCauHoi);
                        getServletContext().setAttribute("email", email);
                        getServletContext().setAttribute("part", part);
                        out.print(idCauHoi);
                        try (ResultSet rsChoice = stmtChoice.executeQuery("Exec get_choice " + idCauHoi)) {
                            out.println(" <form action=\"Result\" method=\"post\" id=\"form1\">  ");
                            out.println(" <h1 id=\"countdown\">35</h1>");

                            out.println("  <script type=\"text/javascript\">");
                            out.println(" var seconds;");
                            out.println(" var temp;");
                            out.println(" function countdown()");
                            out.println(" {");
                            out.println(" seconds=document.getElementById('countdown').innerHTML;");
                            out.println(" seconds=parseInt(seconds,10);");
                            out.println(" if (seconds<=0)");
                            out.println("     {");
                            out.println(" temp=document.getElementById('countdown');");
                            out.println(" temp.innerHTML=\"all done\";");
                            out.println(" document.getElementById('form1').submit();");
                            out.println(" }");
                            out.println(" seconds--;");
                            out.println(" temp=document.getElementById('countdown');");
                            out.println(" temp.innerHTML=seconds;");
                            out.println(" setTimeout(countdown,1000);");
                            out.println(" }");
                            out.println(" countdown();");
                            out.println(" </script>");
                            if (part.equals("1")) {
                                out.println("<img src=\"" + rsQuestion.getString(6) + "\" alt=\"\" width=\"500\" height=\"333\"><br>");
                                out.println("<audio controls>  <source src=\"" + rsQuestion.getString(5) + "\" type=\"audio/mpeg\">   Your browser does not support the audio element </audio><br>");
                            } else if (part.equals("2")) {
                                out.println("<audio controls>  <source src=\"" + rsQuestion.getString(5) + "\" type=\"audio/mpeg\">   Your browser does not support the audio element </audio><br>");
                            } else {
                                out.print("<div id=\"noidung\" name=\"noidung\"   ><b>" + rsQuestion.getString(2) + "</b></div><br>");
                            }
                            while (rsChoice.next()) {
                                switch (rsChoice.getString(2).toLowerCase()) {
                                    case "a":
                                        out.print("<input type=\"radio\"  name=\"dap_an\" value=\"" + rsChoice.getString(4) + "\">" + "A" + rsChoice.getString(3) + "</div><br><br>");
                                        break;
                                    case "b":
                                        out.print("<input type=\"radio\"  name=\"dap_an\" value=\"" + rsChoice.getString(4) + "\">" + "B" + rsChoice.getString(3) + "</div><br><br>");
                                        break;
                                    case "c":
                                        out.print("<input type=\"radio\"  name=\"dap_an\" value=\"" + rsChoice.getString(4) + "\">" + "C" + rsChoice.getString(3) + "</div><br><br>");
                                        break;
                                    case "d":
                                        out.print("<input type=\"radio\"  name=\"dap_an\" value=\"" + rsChoice.getString(4) + "\">" + "D" + rsChoice.getString(3) + "</div><br><br>");
                                        break;
                                    default:
                                        break;
                                }
                            }//
                            stmtQuestion.close();
                            stmtChoice.close();
                            rsQuestion.close();
                            conn.close();
                            out.print(" <input type=\"submit\" id=\"submitQuiz\" value=\"Submit\">");
                            out.println("</form>");
                        }
                    } else {
                        out.println("<h1>Da vuot qua so cau hoi trong ngay</h1>");
                        out.println("<button onclick=\"Back()\">");
                        out.println("Return Home");
                        out.println("</button>");
                        stmtQuestion.close();
                        stmtChoice.close();
                        rsQuestion.close();
                        conn.close();
                    }
                }
            } else if (part.equals("3") || part.equals("4") || part.equals("6") || part.equals("7")) {

                stmtQuestion.executeQuery("Exec get_multiple_question " + part + ",'" + email + "'");
                ResultSet rsQuestion = stmtQuestion.getResultSet();
                if (rsQuestion.next()) {
                    stmtQuestion.getMoreResults();
                    rsQuestion = stmtQuestion.getResultSet();
                       stmtQuestion.getMoreResults();
                            rsQuestion = stmtQuestion.getResultSet();
                    // show data
                    if (rsQuestion.next()) {
                        if (rsQuestion.getInt(4) < socau) {

                            getServletContext().setAttribute("email", email);
                            getServletContext().setAttribute("part", part);
                             out.println(" <form action=\"Result\" method=\"post\" id=\"form1\">  ");
                            out.println(" <h1 id=\"countdown\">85</h1>");
                            out.println("  <script type=\"text/javascript\">");
                            out.println(" var seconds;");
                            out.println(" var temp;");
                            out.println(" function countdown()");
                            out.println(" {");
                            out.println(" seconds=document.getElementById('countdown').innerHTML;");
                            out.println(" seconds=parseInt(seconds,10);");
                            out.println(" if (seconds<=0)");
                            out.println("     {");
                            out.println(" temp=document.getElementById('countdown');");
                            out.println(" temp.innerHTML=\"all done\";");
                            out.println(" document.getElementById('form1').submit();");
                            out.println(" }");
                            out.println(" seconds--;");
                            out.println(" temp=document.getElementById('countdown');");
                            out.println(" temp.innerHTML=seconds;");
                            out.println(" setTimeout(countdown,1000);");
                            out.println(" }");
                            out.println(" countdown();");
                            out.println(" </script>");
                            out.println("<audio controls>  <source src=\"" + rsQuestion.getString(5) + "\" type=\"audio/mpeg\">   Your browser does not support the audio element </audio><br>");
                           
                            stmtQuestion.getMoreResults();

                            rsQuestion = stmtQuestion.getResultSet();
                            int i = 1;
                            while (rsQuestion.next()) {
                                idCauHoi = rsQuestion.getInt(1);
                                getServletContext().setAttribute("idCauHoi" + i, idCauHoi);
                                out.print("<div id=\"noidung\" name=\"noidung\"   ><b>" + idCauHoi + " " + rsQuestion.getString(2) + "</b></div><br>");
                                ResultSet rsChoice = stmtChoice.executeQuery("Exec get_choice " + idCauHoi);
                                while (rsChoice.next()) {
                                    switch (rsChoice.getString(2).toLowerCase()) {
                                        case "a":
                                            out.print("<input type=\"radio\"  name=\"dap_an"+i+"\" value=\"" + rsChoice.getString(4) + "\">" + "A) " + rsChoice.getString(3) + "</div><br><br>");
                                            break;
                                        case "b":
                                            out.print("<input type=\"radio\"  name=\"dap_an"+i+"\" value=\"" + rsChoice.getString(4) + "\">" + "B) " + rsChoice.getString(3) + "</div><br><br>");
                                            break;
                                        case "c":
                                            out.print("<input type=\"radio\"  name=\"dap_an"+i+"\" value=\"" + rsChoice.getString(4) + "\">" + "C) " + rsChoice.getString(3) + "</div><br><br>");
                                            break;
                                        case "d":
                                            out.print("<input type=\"radio\"  name=\"dap_an"+i+"\" value=\"" + rsChoice.getString(4) + "\">" + "D) " + rsChoice.getString(3) + "</div><br><br>");
                                            break;
                                        default:
                                            break;
                                    }
                                }
                                i++;
                            }//

                            stmtQuestion.close();
                            stmtChoice.close();
                            rsQuestion.close();
                            conn.close();
                            out.print(" <input type=\"submit\" id=\"submitQuiz\" value=\"Submit\">");
                            out.println("</form>");
                        }
                    }
                } else {
                    out.println("<h1>Da vuot qua so cau hoi trong ngay</h1>");
                    out.println("<button onclick=\"Back()\">");
                    out.println("Return Home");
                    out.println("</button>");
                    stmtQuestion.close();
                    stmtChoice.close();
                    rsQuestion.close();
                    conn.close();
                }
            }
            out.println("</body>");
            out.println("</html>");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
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
