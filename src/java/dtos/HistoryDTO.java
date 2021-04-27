/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.util.Date;

/**
 *
 * @author SE140066
 */
public class HistoryDTO {
    private int historyID;
    private String userID;
    private String content;
    private Date createDate;
    private int questionID;

    public HistoryDTO(int historyID) {
        this.historyID = historyID;
    }

    public HistoryDTO(int historyID, String userID, String content, Date createDate, int questionID) {
        this.historyID = historyID;
        this.userID = userID;
        this.content = content;
        this.createDate = createDate;
        this.questionID = questionID;
    }

    public int getHistoryID() {
        return historyID;
    }

    public void setHistoryID(int historyID) {
        this.historyID = historyID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }
    
    
}
