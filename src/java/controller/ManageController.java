/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import daos.questDAO;
import dtos.QuestionDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author SE140066
 */
public class ManageController extends HttpServlet {

    private static final String SUCCESS = "manage.jsp";
    private static final String ERROR = "manage.jsp";

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
        String url = ERROR;
        HttpSession session = request.getSession();
        questDAO dao = new questDAO();

        try {
            List<String> listSubject = dao.getListSubject();
            request.setAttribute("LIST_SUBJECT", listSubject);

            String search = request.getParameter("txtSearch");
            String subject = request.getParameter("txtSubject");
            String index = request.getParameter("index");
            request.setAttribute("STATUS", true);

            int indexPage = 0;
            int numberPage = 0;
            List<QuestionDTO> list = new ArrayList<>();

            if (search == null) {
                search = "";
            }

            if (subject == null || subject.equalsIgnoreCase("All")) {
                subject = "";
            }
            
            if (index != null) {
                indexPage = Integer.parseInt(index);
                numberPage = dao.getNumberPage();
                list = dao.getPaging(indexPage, search, subject);
            } else if (indexPage == 0) {
                indexPage = 1;
                numberPage = dao.getNumberPage();
                list = dao.getPaging(indexPage, search, subject);
            }

            if (list != null) {
                session.setAttribute("LISTQUEST", list);
                session.setAttribute("SEARCH", search);
                request.setAttribute("NUMBER_PAGE", numberPage);
                request.setAttribute("CURRENT_PAGE", indexPage);
                session.setAttribute("MESSAGE", null);
                url = SUCCESS;
            } else{
                session.setAttribute("LISTQUEST", list);
                session.setAttribute("SEARCH", search);
            }

        } catch (Exception e) {
            log("Error at ManageController : " + e.toString());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
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
