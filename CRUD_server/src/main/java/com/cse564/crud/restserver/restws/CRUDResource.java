/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cse564.crud.restserver.restws;

import com.cse564.crud.restserver.controller.Controller;
import com.cse564.crud.restserver.jaxb.model.GradeItem;
import com.cse564.crud.restserver.jaxb.model.Score;
import com.cse564.crud.restserver.jaxb.utils.Converter;
import java.net.URI;
import java.util.Random;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;

import javax.ws.rs.POST;
import javax.ws.rs.PUT;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Omkar Totade
 */
@Path("gradeitems")
public class CRUDResource {
    
    private static final Logger LOG = LoggerFactory.getLogger(CRUDResource.class);
    
    
    @Context
    private UriInfo context;
    private static int id=1;
    //creates a new instance of CRUDResource
    public CRUDResource()
    {
        LOG.info("Creating a CRUD Resource");
    }
    
    
    //Get Student's GradeBook
    @GET
    @Path("students/{studentId}")
    @Produces(MediaType.APPLICATION_XML)
    public Response getStduentGradeBook (@PathParam("studentId")String studentId)
    {
        System.out.println("CRUDResource:In GET Method for StudentGradeBook");
        Response response=null;
        String xmlString;
        String finalXmlString="";
        boolean found=false;
        if (Controller.scoreItemArrayList.size()>0)
        {
            for (int i=0;i<Controller.scoreItemArrayList.size();i++)
            {
                if (Controller.scoreItemArrayList.get(i).getStudentId()==Integer.parseInt(studentId))
                {
                    System.out.println("Inside if");
                    xmlString=Converter.convertFromObjectToXML(Controller.scoreItemArrayList.get(i), Score.class);
                    finalXmlString=finalXmlString+"\n"+"\n"+xmlString;
                    found=true;
                }                
            }
            response=Response.status(Response.Status.OK).entity(finalXmlString).build();
        }
        if (found==false)
        {
            response=Response.status(Response.Status.NO_CONTENT).entity("No Student Found").build();
        }
        return response;
    }
    
    
    //Get Entire GradeBook
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Response getEntireGradeBook ()
    {
        System.out.println("CRUDResource:In GET method for Entire GradeBook");
        Response response;
        String finalXmlString="";
        String xmlString;
        if (Controller.scoreItemArrayList.size()>0)
        {
            for (int i=0;i<Controller.scoreItemArrayList.size();i++)
            {
                xmlString=Converter.convertFromObjectToXML(Controller.scoreItemArrayList.get(i), Score.class);
                finalXmlString=finalXmlString+"\n"+"\n"+xmlString;
            }
            response=Response.status(Response.Status.OK).entity(finalXmlString).build();
        }
        else
        {
            response=Response.status(Response.Status.NO_CONTENT).entity("No GradeBook Data to Show").build();
        }
        return response;
    }
    
    
    //GradeItem HTTP Verb Methods
    @POST
    //@Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Response createGradeItem (String content)
    {
        System.out.println("CRUDResource:In POST method for GradeItem");
        //LOG.info("Creating the instance GradeItem Resource {}", gradeItem);
        LOG.debug("POST request");
        LOG.debug("Request Content = {}", content);
        GradeItem gradeItem;
        System.out.println("GradeItemArrayList size:"+Controller.gradeItemArrayList.size());
        Response response;
        boolean conflict=false;
             
        try
        {
            gradeItem=(GradeItem)Converter.convertFromXMLToObject(content, GradeItem.class);
            
            for (int i=0;i<Controller.gradeItemArrayList.size();i++)
            {
                if (Controller.gradeItemArrayList.get(i).getName().equals(gradeItem.getName()))
                {
                    conflict=true;
                }
            }
            if (conflict==false)
            {
            int gradeItemId=CRUDResource.id++;
            gradeItem.setId(gradeItemId);
            System.out.println("Generated GradeItemId:"+gradeItemId);
            String xmlString=Converter.convertFromObjectToXML(gradeItem, GradeItem.class);
            URI locationURI=URI.create(context.getAbsolutePath()+"/"+Integer.toString(gradeItemId));
            response=Response.status(Response.Status.CREATED).location(locationURI).entity(xmlString).build();
            Controller.gradeItemArrayList.add(gradeItem);
            System.out.println("GradeItemArrayList size:"+Controller.gradeItemArrayList.size());
            }
            else
            {
                response=Response.status(Response.Status.CONFLICT).entity("Conflict").build();
            }
        }
        
        catch (JAXBException e)
        {
            LOG.info("Creating a {} {} Status Response", Response.Status.BAD_REQUEST.getStatusCode(), Response.Status.BAD_REQUEST.getReasonPhrase());
            LOG.debug("XML is {} is incompatible with GradeItem Resource", content);                
            response = Response.status(Response.Status.BAD_REQUEST).entity(content).build();
        }
        
        catch (RuntimeException e)
        {
            LOG.debug("Catch All exception");                
            LOG.info("Creating a {} {} Status Response", Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), Response.Status.INTERNAL_SERVER_ERROR.getReasonPhrase());                
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(content).build();
        }
              
        LOG.debug("Generated response {}", response);
        return response;
    }
    
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_XML)
    public Response getGradeItem(@PathParam("id")String id)
    {
        //LOG.info("Retrieving the GradeItem Resource {}", gradeItem);
        LOG.debug("GET request");
        LOG.debug("PathParam id = {}", id);
        System.out.println("CRUDResource:Inside GET Method for GradeItem");
        boolean found=false;
        boolean present=true;
        boolean noContent=true;
        Response response=null;
        
        System.out.println("GradeItemArrayList size:"+Controller.gradeItemArrayList.size());
        for (int i=0;i<Controller.gradeItemArrayList.size();i++)
        {
            if (Controller.gradeItemArrayList.get(i).getId()==Integer.parseInt(id))
            {
                LOG.info("Creating a {} {} Status Response", Response.Status.OK.getStatusCode(), Response.Status.OK.getReasonPhrase());
               
                String xmlString=Converter.convertFromObjectToXML(Controller.gradeItemArrayList.get(i), GradeItem.class);
                response = Response.status(Response.Status.OK).entity(xmlString).build();
                System.out.println("Found in List");
                present=true;
                noContent=false;
                break;
            }   
        }
        if (noContent==true)
        {
            LOG.info("Creating a {} {} Status Response", Response.Status.NO_CONTENT.getStatusCode(), Response.Status.NO_CONTENT.getReasonPhrase());
            System.out.println("noContent==true");
            response = Response.status(Response.Status.NO_CONTENT).entity("No GradeItem Resource to return").build(); 
        }
        if (present==false)
            {
                LOG.info("Creating a {} {} Status Response", Response.Status.NOT_FOUND.getStatusCode(), Response.Status.NOT_FOUND.getReasonPhrase());
                System.out.println("present==false");
                response = Response.status(Response.Status.NOT_FOUND).entity("No GradeItem Resource to return").build();
            }
        
        LOG.debug("Returning the value {}",response);
        return response;
    }
    
    @PUT
    @Path("{id}")
    //@Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Response updateGradeItem(@PathParam("id")String id, String content)
    {
        System.out.println("CRUDResource:Inside PUT Method for GradeItem");
        //LOG.info("Updating the GradeItem Resource {} with {}", gradeItem, content);
        LOG.debug("PUT request");
        LOG.debug("PathParam id = {}", id);
        LOG.debug("Request Content = {}", content);
        GradeItem gradeItem=new GradeItem();
        Response response;
        boolean present=false;
        
        for (int i=0;i<Controller.gradeItemArrayList.size();i++)
        {
            if (Controller.gradeItemArrayList.get(i).getId()==Integer.parseInt(id))
            {
                present=true;
                Controller.gradeItemArrayList.remove(i);
                break;
            }
        }
        if (present==true)
        {
            LOG.debug("Attempting to update the gradeItem Resource {}", gradeItem);            
            try
            {
                gradeItem=(GradeItem)Converter.convertFromXMLToObject(content, GradeItem.class);
                LOG.debug("The XML {} was converted to the object {}", content, gradeItem);         
                LOG.debug("Updated GradeItem Resource to {}", gradeItem);                
                LOG.info("Creating a {} {} Status Response", Response.Status.OK.getStatusCode(), Response.Status.OK.getReasonPhrase());
                String xmlString=Converter.convertFromObjectToXML(gradeItem, GradeItem.class);
                response = Response.status(Response.Status.OK).entity(content).build();
                Controller.gradeItemArrayList.add(gradeItem);
            }
            
            catch (JAXBException e)
            {
                LOG.info("Creating a {} {} Status Response", Response.Status.BAD_REQUEST.getStatusCode(), Response.Status.BAD_REQUEST.getReasonPhrase());
                LOG.debug("XML is {} is incompatible with GradeItem Resource", content);                
                response = Response.status(Response.Status.BAD_REQUEST).entity(content).build();
            }
            catch (RuntimeException e)
            {
                LOG.debug("Catch All exception");                
                LOG.info("Creating a {} {} Status Response", Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), Response.Status.INTERNAL_SERVER_ERROR.getReasonPhrase());                
                response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(content).build();
            
            }
        }
        else
        {
            LOG.info("Creating a {} {} Status Response", Response.Status.NOT_FOUND.getStatusCode(), Response.Status.NOT_FOUND.getReasonPhrase());
           
            response = Response.status(Response.Status.NOT_FOUND).entity(content).build();
        }
       
        
        LOG.debug("Generated response {}", response);        
        return response;
    }
    
    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_XML)
    public Response deleteGradeItem(@PathParam("id")String id)
    {
        System.out.println("CRUDResource:Inside DELETE Method for GradeItem");
        
        LOG.debug("DELETE request");
        LOG.debug("PathParam id = {}", id);
        boolean present=false;
        GradeItem gradeItem=new GradeItem();
        Response response=null;
        for (int i=0;i<Controller.gradeItemArrayList.size();i++)
        {
            if (Controller.gradeItemArrayList.get(i).getId()==Integer.parseInt(id))
            {
                present=true;
                gradeItem=Controller.gradeItemArrayList.get(i);
                break;
            }
        }
        
        if (present==false)
        {
            LOG.info("Creating a {} {} Status Response", Response.Status.NOT_FOUND.getStatusCode(), Response.Status.NOT_FOUND.getReasonPhrase());
            LOG.debug("No GradeItem Resource to delete");
            response=Response.status(Response.Status.NOT_FOUND).build();
        }
        else
        {
            LOG.info("Creating a {} {} Status Response", Response.Status.OK.getStatusCode(), Response.Status.OK.getReasonPhrase());
            LOG.debug("Deleting the GradeItem Resource {}", gradeItem);                
            Controller.gradeItemArrayList.remove(gradeItem);
            response = Response.status(Response.Status.OK).build();
        }
                   
        LOG.debug("Generated response {}", response);
        return response;
    }  
    
    //ScoreItem HTTP Verb Methods
    @POST
    @Path("{gradeItemId}/students/{studentId}")
    @Produces(MediaType.APPLICATION_XML)
    //@Consumes(MediaType.APPLICATION_XML)
    public Response createScoreItem(@PathParam("gradeItemId")String gradeItemId,@PathParam("studentId")String studentId, String content)
    {
        System.out.println("CRUDResource:Inside POST Method for ScoreItem");
        LOG.debug("POST request");
        LOG.debug("Request Content = {}", content);
        Score scoreItem;
        Response response;
        boolean gradeItemPresent=false;
        boolean alreadyPresent=false;
        
        for (int i=0;i<Controller.gradeItemArrayList.size();i++)
        {
            if (Controller.gradeItemArrayList.get(i).getId()==Integer.parseInt(gradeItemId))
            {
                gradeItemPresent=true;
            }
        }

        if (gradeItemPresent==true)
        {           
            try
            {
                scoreItem=(Score)Converter.convertFromXMLToObject(content, Score.class);
                LOG.debug("The XML {} was converted to the object {}", content, scoreItem);
       
                LOG.info("Creating a {} {} Status Response", Response.Status.CREATED.getStatusCode(), Response.Status.CREATED.getReasonPhrase());
        
                scoreItem.setStudentId(Integer.parseInt(studentId));
                scoreItem.setGradeItemId(Integer.parseInt(gradeItemId));
                String xmlString=Converter.convertFromObjectToXML(scoreItem, Score.class);
                //URI locationURI=URI.create(context.getAbsolutePath()+"/"+studentId);
                URI locationURI=URI.create(context.getAbsolutePath()+"");               
                response=Response.status(Response.Status.CREATED).location(locationURI).entity(xmlString).build();
                Controller.scoreItemArrayList.add(scoreItem);
            }
            catch(JAXBException e)
            {
                LOG.info("Creating a {} {} Status Response", Response.Status.BAD_REQUEST.getStatusCode(), Response.Status.BAD_REQUEST.getReasonPhrase());
                LOG.debug("XML is {} is incompatible with ScoreItem Resource", content);                
                response = Response.status(Response.Status.BAD_REQUEST).entity(content).build();   
            }
            catch(RuntimeException e)
            {
                LOG.debug("Catch All exception");                
                LOG.info("Creating a {} {} Status Response", Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), Response.Status.INTERNAL_SERVER_ERROR.getReasonPhrase());                
                response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(content).build();
            }
        }
        else
        {
            response = Response.status(Response.Status.NOT_FOUND).entity("GradeItemId not found").build();
        }
        LOG.debug("Generated response {}", response);
        return response;
    }
    
    @GET
    @Path("{gradeItemId}/students/{studentId}")
    @Produces(MediaType.APPLICATION_XML)
    public Response getScoreItem(@PathParam("gradeItemId")String gradeItemId, @PathParam("studentId")String studentId)
    {
        System.out.println("CRUDResource:Inside GET Method for ScoreItem");
        
        LOG.debug("GET request");
        LOG.debug("PathParam gradeItemId = {}", gradeItemId);
        LOG.debug("PathParam studentId = {}", studentId);
        
        boolean present=false;
        boolean noContent=true;
        Response response=null;
        
        for (int i=0;i<Controller.scoreItemArrayList.size();i++)
        {      
            if ((Controller.scoreItemArrayList.get(i).getGradeItemId()==Integer.parseInt(gradeItemId))&&(Controller.scoreItemArrayList.get(i).getStudentId()==Integer.parseInt(studentId)))
            {
                LOG.info("Creating a {} {} Status Response", Response.Status.OK.getStatusCode(), Response.Status.OK.getReasonPhrase());
                
                String xmlString=Converter.convertFromObjectToXML(Controller.scoreItemArrayList.get(i), Score.class);
                response = Response.status(Response.Status.OK).entity(xmlString).build();
                present=true;
                noContent=false;
            }
        }
        if(present==false)
        {
            LOG.info("Creating a {} {} Status Response", Response.Status.NOT_FOUND.getStatusCode(), Response.Status.NOT_FOUND.getReasonPhrase());
            
            response = Response.status(Response.Status.NOT_FOUND).entity("No Score Resource to return").build();
        }
        if (noContent==true)
        {
        LOG.info("Creating a {} {} Status Response", Response.Status.NO_CONTENT.getStatusCode(), Response.Status.NO_CONTENT.getReasonPhrase());
        System.out.println("noContent==true");
        response = Response.status(Response.Status.NO_CONTENT).entity("No ScoreItem Resource to return").build();
            
        }
        LOG.debug("Returning the value {}",response);
        return response;            
    }
    
    @PUT
    @Path("{gradeItemId}/students/{studentId}")
    //@Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Response upgradeScoreItem(@PathParam("gradeItemId")String gradeItemId, @PathParam("studentId")String studentId, String content)
    {
        System.out.println("CRUDResource:Inside PUT Method for ScoreItem");
       
        LOG.debug("PUT request");
        LOG.debug("PathParam gradeItemId = {}", gradeItemId);
        LOG.debug("PathParam studentId = {}", studentId);
        LOG.debug("Request Content = {}", content);
        
        boolean present=false;
        Response response;
        Score scoreItem=new Score(); 
        for (int i=0;i<Controller.scoreItemArrayList.size();i++)
        {
            if (Controller.scoreItemArrayList.get(i).getGradeItemId()==Integer.parseInt(gradeItemId)&&(Controller.scoreItemArrayList.get(i).getStudentId()==Integer.parseInt(studentId)))
            {
                present=true;
                Controller.scoreItemArrayList.remove(i);
                break;
            }
        }
        
        if (present==true)
        {           
            try
            {
                scoreItem=(Score)Converter.convertFromXMLToObject(content, Score.class);
                LOG.debug("The XML {} was converted to the object {}", content, scoreItem);         
                LOG.debug("Updated ScoreItem Resource to {}", scoreItem);                
                LOG.info("Creating a {} {} Status Response", Response.Status.OK.getStatusCode(), Response.Status.OK.getReasonPhrase());
                String xmlString=Converter.convertFromObjectToXML(scoreItem, Score.class);
                response = Response.status(Response.Status.OK).entity(content).build();
                Controller.scoreItemArrayList.add(scoreItem);
            }
            catch (JAXBException e)
            {
                LOG.info("Creating a {} {} Status Response", Response.Status.BAD_REQUEST.getStatusCode(), Response.Status.BAD_REQUEST.getReasonPhrase());
                LOG.debug("XML is {} is incompatible with ScoreItem Resource", content);                
                response = Response.status(Response.Status.BAD_REQUEST).entity(content).build();
            }
            catch (RuntimeException e)
            {
                LOG.debug("Catch All exception");                
                LOG.info("Creating a {} {} Status Response", Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), Response.Status.INTERNAL_SERVER_ERROR.getReasonPhrase());                
                response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(content).build();
            
            }
        }
        else
        {
            LOG.info("Creating a {} {} Status Response", Response.Status.NOT_FOUND.getStatusCode(), Response.Status.NOT_FOUND.getReasonPhrase());
            LOG.debug("Cannot update ScoreItem Resource {} as it has not yet been created", scoreItem);                      
            response = Response.status(Response.Status.NOT_FOUND).entity(content).build();
        }
        LOG.debug("Generated response {}", response);        
        return response;        
    }
    
    @DELETE
    @Path("{gradeItemId}/students/{studentId}")
    @Produces(MediaType.APPLICATION_XML)
    public Response deleteScoreItem(@PathParam("gradeItemId")String gradeItemId, @PathParam("studentId")String studentId)
    {
        System.out.println("CRUDResource:Inside DELETE Method for ScoreItem");
        
        Response response;
        boolean present=false;
        Score scoreItem=new Score();
        for (int i=0;i<Controller.scoreItemArrayList.size();i++)
        {
            if (Controller.scoreItemArrayList.get(i).getGradeItemId()==Integer.parseInt(gradeItemId)&&(Controller.scoreItemArrayList.get(i).getStudentId()==Integer.parseInt(studentId)))
            {
                present=true;
                scoreItem=Controller.scoreItemArrayList.get(i);
                break;
            }
        }
         if (present==false)
        {
            LOG.info("Creating a {} {} Status Response", Response.Status.NOT_FOUND.getStatusCode(), Response.Status.NOT_FOUND.getReasonPhrase());
            LOG.debug("Retrieving the ScoreItem Resource {}", scoreItem);                
            response = Response.status(Response.Status.NOT_FOUND).build();
        }
        else
        {            
               LOG.info("Creating a {} {} Status Response", Response.Status.OK.getStatusCode(), Response.Status.OK.getReasonPhrase());
               LOG.debug("Deleting the ScoreeItem Resource {}", scoreItem);                
               Controller.scoreItemArrayList.remove(scoreItem);
               response = Response.status(Response.Status.OK).build();          
        }
        LOG.debug("Generated response {}", response);
        return response;
    }    
    
    public int getRandomId()
    {
        int id=1;
        //Random r=new Random();
        //id=r.nextInt(99)+1;
        return id++;
    }
}