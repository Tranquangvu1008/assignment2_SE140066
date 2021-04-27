/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import daos.questDAO;
import dtos.QuestionDTO;
import dtos.QuizDTO;
import java.io.IOException;
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
public class QuizController extends HttpServlet {

    private static final String SUCCESS = "quiz.jsp";
    private static final String ERROR = "welcome.jsp";

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
            String subject = (String) session.getAttribute("SUBJECTID");
            String index = request.getParameter("index");
            String questionID = request.getParameter("txtQuestionID");
            String answer = request.getParameter("txtAnswer");

            int indexPage = 0;
            int numberPage = 0;
            List<QuestionDTO> list = new ArrayList<>();

            if (questionID == null) {
                questionID = "";
            }

            if (answer == null) {
                answer = "";
            }
            if (!questionID.equals("") && !answer.equals("")) {
                int questNum = Integer.parseInt(questionID);
                dao.getAnswer(answer, questNum);
            }

            if (index != null) {
                indexPage = Integer.parseInt(index);
                numberPage = dao.getNumberQuestion(subject);
                list = dao.getPagingQuiz(indexPage, subject);
            } else if (indexPage == 0) {
                indexPage = 1;
                numberPage = dao.getNumberQuestion(subject);
                list = dao.getPagingQuiz(indexPage, subject);
            }

            List<QuizDTO> listQA = dao.getQA(subject);
            String answerQuiz = "";
            
            int i = indexPage -  1;
            answerQuiz = listQA.get(i).getAnswer();

            if (list != null) {
                session.setAttribute("LISTQUIZ", list);
                session.setAttribute("ANSWER", answerQuiz);
                request.setAttribute("NUMBER_PAGE", numberPage);
                request.setAttribute("CURRENT_PAGE", indexPage);
                url = SUCCESS;
            } else {
                session.setAttribute("LISTQUIZ", list);
            }

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
