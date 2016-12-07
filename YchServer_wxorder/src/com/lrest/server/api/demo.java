package com.lrest.server.api;

import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 * Created by acans on 16/6/23.
 */
@Path("/demo")
public class demo {

    private   final org.slf4j.Logger log = LoggerFactory.getLogger(getClass());
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String get(@Context HttpServletResponse res) {

        log.debug("demo");
        try{
            res.sendRedirect("http://192.168.3.102:3000");

        }catch (Exception e){
            e.printStackTrace();
        }

       return "";


    }
}
