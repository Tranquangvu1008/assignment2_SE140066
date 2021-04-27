package controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author SE140066
 */
public class MainController extends HttpServlet {

    private final static String ERROR = "error.jsp";
    private final static String LOGIN = "LoginController";
    private final static String LOGIN_PAGE = "login.jsp";
    private final static String REGISTER = "RegisterController";
    private final static String MANAGE = "ManageController";
    private final static String SEARCH = "SearchController";
    private final static String UPDATE = "UpdateQuestController";
    private final static String DELETE = "DeleteQuestController";
    private final static String LOGOUT = "LogoutController";
    private final static String CREATE = "CreateController";
    private final static String ADD = "AddController";
    private final static String ACCEPT = "AcceptController";
    private final static String QUIZ = "QuizController";
    private final static String HISTORY = "HistoryAdminController";
    private final static String RESULT = "ResultController";
    private final static String WELCOME = "WelcomeController";
    
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
        try {
            String action = request.getParameter("btnAction");
            if ("Login".equals(action)) {
                url = LOGIN;
            } else if ("LoginPage".equals(action)) {
                url = LOGIN_PAGE;
            } else if ("Manage".equals(action)) {
                url = MANAGE;
            } else if ("Search".equals(action)) {
                url = SEARCH;
            } else if ("Update".equals(action)) {
                url = UPDATE;
            } else if ("Delete".equals(action)) {
                url = DELETE;
            } else if ("Register".equals(action)) {
                url = REGISTER;
            } else if ("Logout".equals(action)) {
                url = LOGOUT;
            } else if ("Create".equals(action)) {
                url = CREATE;
            } else if ("Add".equals(action)) {
                url = ADD;
            } else if ("Accept".equals(action)) {
                url = ACCEPT;
            } else if ("Next".equals(action)) {
                url = QUIZ;
            } else if ("HistoryAdmin".equals(action)) {
                url = HISTORY;
            } else if ("Finish".equals(action)) {
                url = RESULT;
            } else if ("Welcome".equals(action)) {
                url = WELCOME;
            }
        } catch (Exception e) {
            log("Error at MainController: " + e.toString());
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
