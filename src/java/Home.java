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
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Home</title>");
            out.println("<style>");
            out.println("table, th, td {\n" +"  border:1px solid black;\n" +"}");
            out.println("</style>");
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
            out.println("<th bgcolor=\"green\" style=\"color:white; font-size:12pt;\">Part</th>");
            out.println("<th bgcolor=\"green\" style=\"color:white; font-size:12pt;\">Today</th>");
            out.println("<th bgcolor=\"green\" style=\"color:white; font-size:12pt;\">Sum</th>");
            out.println("<th bgcolor=\"green\" style=\"color:white; font-size:12pt;\">Fight</th>");
            out.println("<th bgcolor=\"green\" style=\"color:white; font-size:12pt;\">Help</th>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td  style=\"text-align: center; font-size:12pt;\">Part 1</td>");
            out.println("<td  style=\"text-align: center; font-size:12pt;\">0</td>");
            out.println("<td style=\"text-align: center; font-size:12pt;\">0</td>");
            out.println("<td onclick=\"question(this)\" style=\"text-align: center;color:brown; font-size:11pt;\"><em><u>Test Part 1</u><em/></td>");
            out.println("<td  style=\"text-align: center;color:gray; font-size:11pt;\"><em><u>Hint for part 1</u></em></td>");
            out.println("</tr>");
             out.println("<tr>");
            out.println("<td  style=\"text-align: center; font-size:12pt;\">Part 2</td>");
            out.println("<td  style=\"text-align: center; font-size:12pt;\">0</td>");
            out.println("<td style=\"text-align: center; font-size:12pt;\">0</td>");
            out.println("<td  onclick=\"question(this)\" style=\"text-align: center;color:brown; font-size:11pt;\"><em><u>Test Part 2</u><em/></td>");
            out.println("<td  style=\"text-align: center;color:gray; font-size:11pt;\"><em><u>Hint for part 2</u></em></td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td  style=\"text-align: center; font-size:12pt;\">Part 3</td>");
            out.println("<td  style=\"text-align: center; font-size:12pt;\">0</td>");
            out.println("<td style=\"text-align: center; font-size:12pt;\">0</td>");
            out.println("<td onclick=\"question(this)\" style=\"text-align: center;color:brown; font-size:11pt;\"><em><u>Test Part 3</u><em/></td>");
            out.println("<td  style=\"text-align: center;color:gray; font-size:11pt;\"><em><u>Hint for part 3</u></em></td>");
            out.println("</tr>");
             out.println("<tr>");
            out.println("<td  style=\"text-align: center; font-size:12pt;\">Part 4</td>");
            out.println("<td  style=\"text-align: center; font-size:12pt;\">0</td>");
            out.println("<td style=\"text-align: center; font-size:12pt;\">0</td>");
            out.println("<td onclick=\"question(this)\" style=\"text-align: center;color:brown; font-size:11pt;\"><em><u>Test Part 4</u><em/></td>");
            out.println("<td  style=\"text-align: center;color:gray; font-size:11pt;\"><em><u>Hint for part 4</u></em></td>");
            out.println("</tr>");
             out.println("<tr>");
            out.println("<td  style=\"text-align: center; font-size:12pt;\">Part 5</td>");
            out.println("<td  style=\"text-align: center; font-size:12pt;\">0</td>");
            out.println("<td style=\"text-align: center; font-size:12pt;\">0</td>");
            out.println("<td onclick=\"question(this)\" style=\"text-align: center;color:brown; font-size:11pt;\"><em><u>Test Part 5</u><em/></td>");
            out.println("<td  style=\"text-align: center;color:gray; font-size:11pt;\"><em><u>Hint for part 5</u></em></td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td  style=\"text-align: center; font-size:12pt;\">Part 6</td>");
            out.println("<td  style=\"text-align: center; font-size:12pt;\">0</td>");
            out.println("<td style=\"text-align: center; font-size:12pt;\">0</td>");
            out.println("<td onclick=\"question(this)\" style=\"text-align: center;color:brown; font-size:11pt;\"><em><u>Test Part 6</u><em/></td>");
            out.println("<td  style=\"text-align: center;color:gray; font-size:11pt;\"><em><u>Hint for part 6</u></em></td>");
            out.println("</tr>");
           out.println("<tr>");
            out.println("<td  style=\"text-align: center; font-size:12pt;\">Part 7A</td>");
            out.println("<td  style=\"text-align: center; font-size:12pt;\">0</td>");
            out.println("<td style=\"text-align: center; font-size:12pt;\">0</td>");
            out.println("<td onclick=\"question(this)\" style=\"text-align: center;color:brown; font-size:11pt;\"><em><u>Test Part 7A</u><em/></td>");
            out.println("<td  style=\"text-align: center;color:gray; font-size:11pt;\"><em><u>Hint for part 7A</u></em></td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td  style=\"text-align: center; font-size:12pt;\">Part 7B</td>");
            out.println("<td  style=\"text-align: center; font-size:12pt;\">0</td>");
            out.println("<td style=\"text-align: center; font-size:12pt;\">0</td>");
            out.println("<td onclick=\"question(this)\" style=\"text-align: center;color:brown; font-size:11pt;\"><em><u>Test Part 7B</u><em/></td>");
            out.println("<td  style=\"text-align: center;color:gray; font-size:11pt;\"><em><u>Hint for part 7B</u></em></td>");
            out.println("</tr>");
            out.println("</table>");
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
