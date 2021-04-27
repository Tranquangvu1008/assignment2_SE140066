/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

/**
 *
 * @author SE140066
 */
public class QuizDTO {
    private String question;
    private String answer;

    public QuizDTO() {
    }

    public QuizDTO(String questionID, String answer) {
        this.question = questionID;
        this.answer = answer;
    }

    public String getQuestionID() {
        return question;
    }

    public void setQuestionID(String questionID) {
        this.question = questionID;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
    
}
