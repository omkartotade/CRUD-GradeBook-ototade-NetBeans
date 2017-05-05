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
public class GradeItem_CRUD_cl {
    
    private static final Logger LOG = LoggerFactory.getLogger(GradeItem_CRUD_cl.class);
    private WebResource webResource;
    private Client client;
    private static final String BASE_URI="http://localhost:8080/CRUD_server";
    
    public GradeItem_CRUD_cl()
    {
        LOG.info("Creating a GradeItem_CRUD_client");
        ClientConfig config=new DefaultClientConfig();
        client=Client.create(config);
        webResource=client.resource(BASE_URI);
        LOG.debug("webresource={}",webResource.getURI());
    }
    
    public ClientResponse createGradeItem (Object requestEntity) throws UniformInterfaceException
    {
       System.out.println("Inside createGradeItem in GradeItem_CRUD_cl");
       LOG.info("Initiating a Create request");
       LOG.debug("XML String = {}", (String) requestEntity);
       ClientResponse clientResponse=webResource.path("gradebook").path("gradeitems").type(MediaType.APPLICATION_XML).entity(requestEntity).post(ClientResponse.class);
       return clientResponse;
    }
    
    public ClientResponse deleteGradeItem (String gradeItemId) throws UniformInterfaceException
    {
       System.out.println("Inside deleteGradeItem in GradeItem_CRUD_cl");
       LOG.info("Initiating a Delete request");
       LOG.debug("ID = {}", gradeItemId);
       ClientResponse clientResponse=webResource.path("gradebook").path("gradeitems").path(gradeItemId).type(MediaType.APPLICATION_XML).delete(ClientResponse.class);
       return clientResponse;
    }
    
    public ClientResponse updateGradeItem (Object requestEntity, String gradeItemId) throws UniformInterfaceException
    {
       System.out.println("Inside updateGradeItem in GradeItem_CRUD_cl");
       LOG.info("Initiating an Update request");
       LOG.debug("XML String = {}", (String) requestEntity);
       LOG.debug("Id = {}", gradeItemId);
       ClientResponse clientResponse=webResource.path("gradebook").path("gradeitems").path(gradeItemId).type(MediaType.APPLICATION_XML).entity(requestEntity).put(ClientResponse.class);
       return clientResponse;
    }
    
    public <T> T getGradeItem(Class<T> responseType, String gradeItemId) throws UniformInterfaceException 
    {
      System.out.println("Inside getGradeItem in GradeItem_CRUD_cl");
      LOG.info("Initiating a Retrieve request");
      LOG.debug("responseType = {}", responseType.getClass());
      LOG.debug("Id = {}", gradeItemId);
      
      //WebResource resource = webResource;
      //resource = resource.path(id);
      
      return webResource.path("gradebook").path("gradeitems").path(gradeItemId).accept(MediaType.APPLICATION_XML).get(responseType);
    }   
    
    public void close()
    {
        LOG.info("Closing the REST client");
        client.destroy();
    }
      
   
}
