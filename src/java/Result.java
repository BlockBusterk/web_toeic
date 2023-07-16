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
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
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
@WebServlet(urlPatterns = {"/Result"})
public class Result extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @param password
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
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        ServletContext servletContext = getServletContext();

        String email = servletContext.getAttribute("email").toString();
        getServletContext().setAttribute("email", email);
        String part = servletContext.getAttribute("part").toString();
        getServletContext().setAttribute("part", part);

        String severNameCty = "LAPTOP-AR34IMPG\\SQLEXPRESS";
        String DB_URL = "jdbc:sqlserver://" + severNameCty + ":1433;"
                + "databaseName=web_toeic"
                + ";encrypt=true;trustServerCertificate=true;";
        String USER_NAME = "sa";
        String PASSWORD = "Huyho@ng432002";
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Result</title>");
            out.println("<script>");
            out.println(" function question()");
            out.println("{");
            out.println("location.replace(\"question\")");
            out.println("}");
            out.println("</script>");
            out.println("<script>");
            out.println(" function Home()");
            out.println("{");
            out.println("location.replace(\"Home\")");
            out.println("}");
            out.println("</script>");
            out.println("</head>");
            out.println("<body>");
            if (part.equals("1") || part.equals("2") || part.equals("5")) {
                Integer idCauHoi = (Integer) servletContext.getAttribute("idCauHoi");
                String dapAn = request.getParameter("dap_an");
                try (Connection conn = getConnection(DB_URL, USER_NAME, PASSWORD)) {
                    Statement stmtResult = conn.createStatement();
                    if (dapAn == null) {
                        dapAn = "FALSE";
                    }
                    int rsResult = stmtResult.executeUpdate("Exec insert_ketqua " + idCauHoi + ",'" + email + "','" + dapAn + "'");
                    stmtResult.close();
                    conn.close();
                }

                out.println("<h1>" + dapAn + "</h1>");
                out.println("<button onclick=\"question()\">");
                out.println("Next question");
                out.println("</button>");

            } else if (part.equals("3") || part.equals("4") || part.equals("6") || part.equals("7")) {

                try (Connection conn = getConnection(DB_URL, USER_NAME, PASSWORD)) {
                    int i = 1;
                    Statement stmtResult = conn.createStatement();
                    while (i <= 3) {

                        String dapAn = request.getParameter("dap_an" + i);
                        if (dapAn == null) {
                            dapAn = "FALSE";
                        }
                        int idCauHoi = (Integer) servletContext.getAttribute("idCauHoi" + i);
                        int rsResult = stmtResult.executeUpdate("Exec insert_ketqua " + idCauHoi + ",'" + email + "','" + dapAn + "'");
                        out.println("<h3>" +idCauHoi+" - "+ dapAn + "</h3><br>");
                        i++;
                    }
                    stmtResult.close();
                    conn.close();
                }
            }
            out.println("<button onclick=\"Home()\">");
            out.println("Return Home");
            out.println("</button>");
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
            Logger.getLogger(Result.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Result.class.getName()).log(Level.SEVERE, null, ex);
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
