package com.lrest.server.services.services.session;

import com.lrest.server.services.services.Config;

import javax.inject.Singleton;

/**
 * Created by acans on 16/6/23.
 */
@Singleton
public  class SessionManager {


    private   static SessionManagerInteface sessionManager;


    public static SessionManagerInteface getInstance(){

        if (sessionManager==null){
            if (Config.use_redis==1){
                sessionManager=new RedisSessionManager();
            }else{
                sessionManager=new MemorySessionManager();
            }
        }
        return  sessionManager;
    };


    public static SessionManagerInteface delInstance(){

//        if (sessionManager==null){
            if (Config.use_redis==1){
                sessionManager=new RedisSessionManager();
            }else{
                sessionManager=new MemorySessionManager();
            }
//        }
        return  sessionManager;
    };



}
