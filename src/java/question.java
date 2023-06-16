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
     String pass = request.getParameter("password");
  try (PrintWriter out = response.getWriter())
  {
            // connnect to database 'testdb'
       out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet NewServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet NewServlet at " + request.getContextPath() + "</h1>");
            out.println(" <form action=\"update_post\" method=\"post\" id=\"form1\">  ");
            Connection conn = getConnection(DB_URL, USER_NAME, PASSWORD);
            // crate statement
            Statement stmt = conn.createStatement();
            // get data from table 'student'
            
            ResultSet rs = stmt.executeQuery("Exec get_question 5");
            Statement stmtA = conn.createStatement();
           Statement stmtB = conn.createStatement();
           Statement stmtC = conn.createStatement();
           Statement stmtD = conn.createStatement();
            // show data
            if (rs.next()) {
                 int idCauhoi = rs.getInt(1);
                ResultSet rs_DapanA = stmtA.executeQuery("select * from lua_chon_dap_an where cauhoibaithithuid = "+idCauhoi+" and choice = 'a' ");
                ResultSet rs_DapanB = stmtB.executeQuery("select * from lua_chon_dap_an where cauhoibaithithuid = "+idCauhoi+" and choice = 'b' ");
                ResultSet rs_DapanC = stmtC.executeQuery("select * from lua_chon_dap_an where cauhoibaithithuid = "+idCauhoi+" and choice = 'c' ");
                ResultSet rs_DapanD = stmtD.executeQuery("select * from lua_chon_dap_an where cauhoibaithithuid = "+idCauhoi+" and choice = 'd' ");
                out.print("<div id=\"noidung\" name=\"noidung\"   ><b>" + rs.getString(2) + "</b></div><br>");
                if(rs_DapanA.next())
                {
                     out.print("<input type=\"radio\"  name=\"age\" value=\"A\">" + "A. " +rs_DapanA.getString(3)+ "</div><br><br>");
                }
               if(rs_DapanB.next())
                {
                     out.print("<input type=\"radio\"  name=\"age\" value=\"A\">" + "B. " +rs_DapanB.getString(3)+ "</div><br><br>");
                }
               if(rs_DapanC.next())
                {
                     out.print("<input type=\"radio\"  name=\"age\" value=\"A\">" + "C. " +rs_DapanC.getString(3)+ "</div><br><br>");
                }
               if(rs_DapanD.next())
                {
                     out.print("<input type=\"radio\"  name=\"age\" value=\"A\">" + "D. " +rs_DapanD.getString(3)+ "</div><br><br>");
                }
                out.print(" <input type=\"submit\" id=\"submitQuiz\" value=\"Submit\">");
            }
            else{
                response.sendRedirect("index.jsp");
            }
             stmt.close();
                rs.close();
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
