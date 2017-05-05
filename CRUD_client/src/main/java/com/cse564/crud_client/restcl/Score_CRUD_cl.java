/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cse564.crud_client.restcl;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import javax.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 *
 * @author Omkar Totade
 */
public class Score_CRUD_cl {
    
    private static final Logger LOG = LoggerFactory.getLogger(GradeItem_CRUD_cl.class);
    private WebResource webResource;
    private Client client;
    private static final String BASE_URI="http://localhost:8080/CRUD_server";
    
    
    public Score_CRUD_cl()
    {
        LOG.info("Creating a GradeItem_CRUD_client");
        ClientConfig config=new DefaultClientConfig();
        client=Client.create(config);
    }
    
    public ClientResponse createScore (Object requestEntity,String gradeItemId,String studentId) throws UniformInterfaceException
    {
       System.out.println("Inside createScore in Score_CRUD_cl");
       webResource=client.resource(BASE_URI);
       LOG.debug("webresource={}",webResource.getURI());
       LOG.info("Initiating a Create request");
       LOG.debug("XML String = {}", (String) requestEntity);
       ClientResponse clientResponse=webResource.path("gradebook").path("gradeitems").path(gradeItemId).path("students").path(studentId).type(MediaType.APPLICATION_XML).entity(requestEntity).post(ClientResponse.class);
       return clientResponse;
    }
     
    public ClientResponse deleteScore (String studentId, String gradeItemId) throws UniformInterfaceException
    {
       System.out.println("Inside deleteScore in Score_CRUD_cl");
       webResource=client.resource(BASE_URI);
       LOG.debug("webresource={}",webResource.getURI());
       LOG.info("Initiating a Delete request");
       LOG.debug("ID = {}", studentId);
       ClientResponse clientResponse=webResource.path("gradebook").path("gradeitems").path(gradeItemId).path("students").path(studentId).delete(ClientResponse.class);
       return clientResponse;
    }
    
    public ClientResponse updateScore (Object requestEntity, String studentId, String gradeItemId) throws UniformInterfaceException
    {
       System.out.println("Inside updateScore in Score_CRUD_cl");
       webResource=client.resource(BASE_URI);
       LOG.debug("webresource={}",webResource.getURI());
       LOG.info("Initiating an Update request");
       LOG.debug("XML String = {}", (String) requestEntity);
       LOG.debug("Id = {}", studentId);
       ClientResponse clientResponse=webResource.path("gradebook").path("gradeitems").path(gradeItemId).path("students").path(studentId).entity(requestEntity).put(ClientResponse.class);
       return clientResponse;
    }
    
    public <T> T getScore(Class<T> responseType, String studentId, String gradeItemId) throws UniformInterfaceException 
    {
        System.out.println("Inside getScore in Score_CRUD_cl");
        webResource=client.resource(BASE_URI);
        LOG.debug("webresource={}",webResource.getURI());
        LOG.info("Initiating a Retrieve request");
        LOG.debug("responseType = {}", responseType.getClass());
        LOG.debug("Id = {}", studentId);
        
        //WebResource resource = webResource;
        //resource = resource.path(id);
        
        return webResource.path("gradebook").path("gradeitems").path(gradeItemId).path("students").path(studentId).accept(MediaType.APPLICATION_XML).get(responseType);
    }
    
    public <T> T getEntireGradeBook(Class<T> responseType) throws UniformInterfaceException
    {
        System.out.println ("Inside getEntireGradeBook in Score_CRUD_cl");
        webResource=client.resource(BASE_URI);
        
        return webResource.path("gradebook").path("gradeitems").accept(MediaType.APPLICATION_XML).get(responseType);
    }
    
    public <T> T getStudentGradeBook(Class<T> responseType, String studentId) throws UniformInterfaceException
    {
        System.out.println("Inside getStudentGradeBook in Score_CRUD_cl");
        webResource=client.resource(BASE_URI);
        
        return webResource.path("gradebook").path("gradeitems").path("students").path(studentId).accept(MediaType.APPLICATION_XML).get(responseType);
    }
}
