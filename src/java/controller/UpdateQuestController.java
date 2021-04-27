/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import daos.UserDAO;
import daos.questDAO;
import dtos.QuestionDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
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
public class UpdateQuestController extends HttpServlet {

    private static final String SUCCESS = "ManageController";
    private static final String ERROR = "manage.jsp";
    private static final String RETURN = "login.jsp";

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
        UserDAO ud = new UserDAO();
        try {
            String userID = (String) session.getAttribute("USERID");
            String activity = (String) session.getAttribute("ACTIVITY");
            String role = ud.checkRole(userID);
            if (role.equals("Ad")) {
                if (!activity.equalsIgnoreCase("NON")) {
                    int questionID = Integer.parseInt(request.getParameter("txtQuestionID"));
                    String questionContent = request.getParameter("txtQuestionName");
                    String answerA = request.getParameter("txtAnswerA");
                    String answerB = request.getParameter("txtAnswerB");
                    String answerC = request.getParameter("txtAnswerC");
                    String answerD = request.getParameter("txtAnswerD");
                    String answerCorrect = request.getParameter("txtAnswerCorrect");
                    String subjectID = request.getParameter("txtSubjectID");
                    String status = request.getParameter("txtStatus");
                    boolean stt = true;
                    if (status == null) {
                        stt = false;
                    }

                    questDAO dao = new questDAO();
                    QuestionDTO dtos = new QuestionDTO();
                    List<QuestionDTO> list = dao.getListQuest();

                    for (QuestionDTO dto : list) {
                        if (dto.getQuestionID() == questionID) {
                            dao.updateQuest(new QuestionDTO(questionID, questionContent, answerA, answerB, answerC, answerD, answerCorrect, dto.getCreateDate(), subjectID, stt));
                            String content = userID + " update question " + questionID;

                            if (stt = false) {
                                content = userID + " delete question " + questionID;
                            }

                            dao.createHistory(userID, content, questionID);
                            break;
                        }
                    }

                    url = SUCCESS;
                } else{
                    request.setAttribute("NOTI", "Candidates are doing the exam!");
                    url = SUCCESS;
                }
            } else {
                url = RETURN;
            }

        } catch (Exception e) {
            log("Error at UpdateQuestController: " + e.toString());
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
