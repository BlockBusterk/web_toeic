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
            throws ServletException, IOException { response.setContentType("text/html;charset=UTF-8");
        String severNameCty = "VTNTHUCTAP";
       String DB_URL = "jdbc:sqlserver://"+severNameCty+":1433;"
            + "databaseName=web_toeic"
            +   ";encrypt=true;trustServerCertificate=true;";
     String USER_NAME = "sa";
     String PASSWORD = "Huyho@ng432002";
     String email = request.getParameter("email");
     String part = request.getParameter("part");
                 
     if(email == null)
     {
         ServletContext servletContext = getServletContext();
         email = servletContext.getAttribute("email").toString();
     }
     if(part == null)
     {
         ServletContext servletContext = getServletContext();
         part = servletContext.getAttribute("part").toString();
     }
     int idCauHoi=0;
  try (PrintWriter out = response.getWriter())
  {
            // connnect to database 'testdb'
       out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet NewServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println(" <form action=\"Result\" method=\"post\" id=\"form1\">  ");
            Connection conn = getConnection(DB_URL, USER_NAME, PASSWORD);
            // crate statement
            Statement stmtQuestion = conn.createStatement();
            Statement stmtChoice = conn.createStatement();
            Statement stmtCount = conn.createStatement();
          
            ResultSet rsQuestion = stmtQuestion.executeQuery("Exec get_question "+ part +",'"+email+"'");
            
            // show data
            if (rsQuestion.next()) {
                if(rsQuestion.getInt(4)<5)
                {
                  idCauHoi = rsQuestion.getInt(1);
                  getServletContext().setAttribute( "idCauHoi", idCauHoi );
                  getServletContext().setAttribute( "email", email );
                   getServletContext().setAttribute( "part", part );
                 out.print(idCauHoi);
                    try (ResultSet rsChoice = stmtChoice.executeQuery("Exec get_choice "+idCauHoi)) {
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
                        out.println("<img src=\"image_sound\\41.png\" alt=\"Trulli\" width=\"500\" height=\"333\"><br>");
                        out.println("<audio controls>  <source src=\"image_sound\\41.MP3\" type=\"audio/mpeg\">   Your browser does not support the audio element </audio>");
                        out.print("<div id=\"noidung\" name=\"noidung\"   ><b>" + rsQuestion.getString(2) + "</b></div><br>");
                        while(rsChoice.next())
                        {
                            switch (rsChoice.getString(2).toLowerCase()) {
                                case "a":
                                    out.print("<input type=\"radio\"  name=\"dap_an\" value=\""+rsChoice.getString(4)+"\">" + "A" +rsChoice.getString(3)+ "</div><br><br>");
                                    break;
                                case "b":
                                    out.print("<input type=\"radio\"  name=\"dap_an\" value=\""+rsChoice.getString(4)+"\">" + "B" +rsChoice.getString(3)+ "</div><br><br>");
                                    break;
                                case "c":
                                    out.print("<input type=\"radio\"  name=\"dap_an\" value=\""+rsChoice.getString(4)+"\">" + "C" +rsChoice.getString(3)+ "</div><br><br>");
                                    break;
                                case "d":
                                    out.print("<input type=\"radio\"  name=\"dap_an\" value=\""+rsChoice.getString(4)+"\">" + "D" +rsChoice.getString(3)+ "</div><br><br>");
                                    break;
                                default:
                                    break;
                            }
                        }//
                        
                        out.print(" <input type=\"submit\" id=\"submitQuiz\" value=\"Submit\">");
                    }
            }else {
                     out.println("<h1>Da vuot qua so cau hoi trong ngay</h1>");
                }
            }
            
            else{
                response.sendRedirect("index.jsp");
            }
                stmtQuestion.close();
                stmtChoice.close();
                rsQuestion.close();
                conn.close();
                out.println("</form>");
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
