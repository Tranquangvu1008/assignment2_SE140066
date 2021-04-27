/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import daos.questDAO;
import dtos.QuestTime;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
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
public class AcceptController extends HttpServlet {

    private static final String SUCCESS = "QuizController";
    private static final String ERROR = "WelcomeController";

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
        String url = SUCCESS;
        questDAO dao = new questDAO();
        HttpSession session = request.getSession();
        try {
            String subjectID = request.getParameter("txtSubject");
            int numQuest = dao.getNumOfQues(subjectID);//LAY SO CAU TRONG DB DECODE
            String userID = (String) session.getAttribute("USERID");
            String activity = (String) session.getAttribute("ACTIVITY");

            if (subjectID != null) {
                if (activity == null) {
                    dao.createScore(userID);
                    int scoreID = dao.getScoreID(userID);
                    String subjectQuiz = dao.createQuiz(scoreID, subjectID, numQuest);
                    session.setAttribute("SUBJECTID", subjectQuiz);

                    int time = dao.getTime(subjectID);
                    QuestTime qt = new QuestTime();
                    Calendar now = Calendar.getInstance();
                    qt.setStartTime(now.getTime());
                    now.add(Calendar.MINUTE, time);
                    qt.setEndTime(now.getTime());
                    Date time1 = now.getTime();

                    session.setAttribute("TIME", qt);
                    session.setAttribute("ACTIVITY", "NON");
                } else {
                    request.setAttribute("MESSAGE", "Candidates are doing the exam!");
                    url = ERROR;
                }
            }
            session.setAttribute("NUMQUEST", numQuest);

        } catch (Exception e) {
            log("Error at LoginServlet : " + e.toString());
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
