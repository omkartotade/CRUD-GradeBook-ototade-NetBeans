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
    "name",
    "id",
    "percentPointAllocation"})
public class GradeItem {
    
    private static final Logger LOG = LoggerFactory.getLogger(GradeItem.class);
    
    private String name;
    private int id;
    private int percentPointAllocation;

    public GradeItem() {
        LOG.info("Creating an GradeItem object");
    }

    public String getName() {
        return name;
    }

    @XmlElement
    public void setName(String name) {
        LOG.info("Setting the name to:", name);
        
        this.name = name;
        
        LOG.debug("The updated GradeItem = {}", this);
    }

    public int getId() {
        return id;
    }

    @XmlElement
    public void setId(int id) {
        LOG.info("Setting the ID to:", id);
        
        this.id = id;
        
        LOG.debug("The updated GradeItem = {}", this);
    }

    public int getPercentPointAllocation() {
        return percentPointAllocation;
    }

    @XmlAttribute
    public void setPercentPointAllocation(int percentPointAllocation) {
        LOG.info("Setting the percentPointAllocation to:", percentPointAllocation);
        
        this.percentPointAllocation = percentPointAllocation;
        
        LOG.debug("The updated GradeItem = {}", this);
    }

    @Override
    public String toString() {
        return new StringBuffer("Name:").append(this.name).append("ID:").append(this.id).append("Percent Point Allocation:").append(this.percentPointAllocation).toString();
    }
    
}
