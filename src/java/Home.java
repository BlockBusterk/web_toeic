/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author VTN
 */
@WebServlet(urlPatterns = {"/Home"})
public class Home extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */ public static Connection getConnection(String dbURL, String userName, 
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
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        String severNameCty = "VTNTHUCTAP";
       String DB_URL = "jdbc:sqlserver://"+severNameCty+":1433;"
            + "databaseName=web_toeic"
            +   ";encrypt=true;trustServerCertificate=true;";
     String USER_NAME = "sa";
     String PASSWORD = "Huyho@ng432002";
     String email = request.getParameter("email");
      if(email == null)
     {
         ServletContext servletContext = getServletContext();
         email = servletContext.getAttribute("email").toString();
     }
      Connection conn = getConnection(DB_URL, USER_NAME, PASSWORD);
            // crate statement
      
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Home</title>");
            out.println("<link rel=\"stylesheet\" href=\"styles/home.css\">");
            out.println("<script>");
            out.println(" function question(x)");
            out.println("{");
            out.println("location.replace(\"question?part=\"+x.parentElement.rowIndex)");
            out.println("}");
            out.println("</script>");
            out.println("</head>");
            out.println("<body>");
            out.println("<table style=\"width:100%\">");
            out.println("<tr>");
            out.println("<th>Part</th>");
            out.println("<th>Today</th>");
            out.println("<th>Sum</th>");
            out.println("<th>Fight</th>");
            out.println("<th>Help</th>");
            out.println("</tr>");
            out.println("<tr>");
             Statement stmtDiem = conn.createStatement();
             ResultSet rsDiem = stmtDiem.executeQuery("Exec temp " + "'" + email + "'");
             
             while(rsDiem.next()){
             {
            out.println("<td  style=\"text-align: center; font-size:12pt;\">Part "+rsDiem.getInt(1)+"</td>");
            out.println("<td id=\"today_part1\"; style=\"text-align: center; font-size:12pt;\">"+rsDiem.getInt(2)+"</td>");
            out.println("<td style=\"text-align: center; font-size:12pt;\">"+rsDiem.getInt(3)+"</td>");
            out.println("<td onclick=\"question(this)\" style=\"text-align: center;color:brown; font-size:11pt;\"><em><u>Test Part "+rsDiem.getInt(1)+"</u><em/></td>");
            out.println("<td  style=\"text-align: center;color:gray; font-size:12pt;\"><em><u>Hint for part "+rsDiem.getInt(1)+"</u></em></td>");
            out.println("</tr>");
            }
             }
            stmtDiem.close();
            rsDiem.close();
            out.println("</table>");
            out.println("  <script type=\"text/javascript\">");
            out.println("function diem_today(x)");
            out.println("{");
            
            out.println("}");
            out.println(" </script>");
            out.println("</body>");
            out.println("</html>");
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
         try {
             processRequest(request, response);
         } catch (SQLException ex) {
             Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
         }
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
         try {
             processRequest(request, response);
         } catch (SQLException ex) {
             Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
         }
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
