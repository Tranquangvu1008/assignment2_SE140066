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
public class QuestTime {
    private Date startTime;
    private Date endTime;

    public QuestTime() {
    }

    public QuestTime(Date startTime, Date endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
    
    
}
