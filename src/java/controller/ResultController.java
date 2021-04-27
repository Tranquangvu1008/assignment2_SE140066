/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import daos.questDAO;
import dtos.MultichoiceDTO;
import dtos.QuestAnsDTO;
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
public class ResultController extends HttpServlet {

    private static final String SUCCESS = "result.jsp";
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
        HttpSession session = request.getSession();
        questDAO dao = new questDAO();
        try {
            String subjectQuiz = (String) session.getAttribute("SUBJECTID");
            String userID = (String) session.getAttribute("USERID");
            String questionContent = request.getParameter("txtQuestionContent");
            int numQuest = dao.getNumberQuest(subjectQuiz);
            
            session.setAttribute("ACTIVITY", null);
            
            String question;
            String answerQuiz;
            String answerQuest;
            List<Integer> list = new ArrayList<>();
            List<MultichoiceDTO> listMul = new ArrayList<>();
            List<QuizDTO> listQuiz = new ArrayList<>();
            List<QuestAnsDTO> listQA = new ArrayList<>();
            
            if(questionContent == null){
                questionContent = "";
            }
            
            list = dao.getQuestionID(subjectQuiz);
            listMul = dao.getQuestionAnswer(subjectQuiz, questionContent);
            
            for (Integer ls : list) {
                question = dao.getQuestion(ls, subjectQuiz);
                answerQuiz = dao.getAnswerQuiz(ls, subjectQuiz);
                answerQuest = dao.getAnswerQuest(ls, subjectQuiz);
                
                if (answerQuiz.equalsIgnoreCase(answerQuest)) {
                    dao.getScore(subjectQuiz, ls, numQuest);
                }
                listQuiz.add(new QuizDTO(question, answerQuest));
                listQA.add(new QuestAnsDTO(question, answerQuiz));
            }
            
            int scoreID = dao.getScoreID(userID);
            float totalScore = dao.setTotalScore(subjectQuiz);
            dao.getTotalScore(scoreID, totalScore);

            String[] splits = subjectQuiz.split("_");
            for (String item : splits) {
                subjectQuiz = item;
                break;
            }

            request.setAttribute("TOTALSCORE", totalScore);
            request.setAttribute("SUBJECTID", subjectQuiz);
            session.setAttribute("LISTMUL", listMul);
            session.removeAttribute("ACTIVE");
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
