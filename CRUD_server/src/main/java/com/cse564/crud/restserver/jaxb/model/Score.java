/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cse564.crud.restserver.jaxb.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author fcalliss
 */
@XmlRootElement
@XmlType(propOrder={
    "gradeItemId",
    "studentId",
    "points"})
public class Score {
    
    private static final Logger LOG = LoggerFactory.getLogger(Score.class);
    
    private int gradeItemId;
    private int studentId;
    private int points;
    

    public Score() {
        LOG.info("Creating a Score object");
    }

    public int getPoints() {
        return points;
    }

    @XmlAttribute
    public void setPoints(int points) {
        LOG.info("Setting the points to:", points);
        
        this.points = points;
        
        LOG.debug("The updated Score = {}", this);
    }

    public int getStudentId() {
        return studentId;
    }

    @XmlElement
    public void setStudentId(int studentId) {
        LOG.info("Setting the StudentId to:", studentId);
        
        this.studentId = studentId;
        
        LOG.debug("The updated Score = {}", this);
    }

    public int getGradeItemId() {
        return gradeItemId;
    }

    @XmlElement
    public void setGradeItemId(int gradeItemId) {
        LOG.info("Setting the gradeItemId to:", gradeItemId);
        
        this.gradeItemId = gradeItemId;
        
        LOG.debug("The updated Score = {}", this);
    }
    
    
    @Override
    public String toString() {
        return new StringBuffer("GradeItemId:").append(this.gradeItemId).append("StudentID:").append(this.studentId).append("Points:").append(this.points).toString();
    }
    
}