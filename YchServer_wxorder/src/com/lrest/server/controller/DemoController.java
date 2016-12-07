package com.lrest.server.controller;

/**
 * Created by acans on 16/6/21.
 */

import com.lrest.server.models.Base_Idname;
import org.slf4j.LoggerFactory;
import rx.Observable;
import rx.Observer;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/demo2")
public class DemoController extends BaseController{
    private   final org.slf4j.Logger log = LoggerFactory.getLogger(getClass());


    @GET

    public String get() {

        Observable<Base_Idname> user =Observable.create((subscriber)->{
            try {

                //Do DB call
                Base_Idname u = null;
                subscriber.onNext(u);
                subscriber.onCompleted();

            }catch (Exception e) {
                subscriber.onError(e);
            }

        });



        return "demo";
    }
}
