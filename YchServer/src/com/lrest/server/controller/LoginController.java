package com.lrest.server.controller;

/**
 * Created by acans on 16/6/21.
 */

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lrest.server.jooqmodel.tables.records.V3UserDeviceRecord;
import com.lrest.server.models.Userinfo_4s;
import com.lrest.server.services.Config;
import com.lrest.server.services.DB;
import com.lrest.server.services.session.SessionManager;
import com.lrest.server.utils.UtilBase;
import com.tencent.common.MD5;
import eu.bitwalker.useragentutils.UserAgent;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.UUID;

import static com.lrest.server.jooqmodel.Tables.V3_USER_DEVICE;
import static com.lrest.server.utils.UtilBase.getJsonAsString;

@Path("/login")
public class LoginController extends BaseController{
    private   final org.slf4j.Logger log = LoggerFactory.getLogger(getClass());

//    @Inject
//    private SessionManager sessionManager;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String get() {

        String  _token= UUID.randomUUID().toString().replace("-", "").toUpperCase();
        String _uid="test";
        String sid;

        sid= SessionManager.getInstance().createSID(_token,_uid);



        res.setHeader("sessionid",sid);


        return success(sid);

    }

    @POST
    @Path("auth/4s/{platform}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String auth2(String query,@PathParam("platform") String platform) {
        //{username,password,user_agent,role}
        //{username,token,user_agent,role}


        String ua=req.getHeader("User-Agent");




        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {

            log.debug(String.format("ua  %s ",ua));

            JsonObject json = new JsonParser().parse(query).getAsJsonObject();
            String passwd=getJsonAsString(json,"password");
            String username=getJsonAsString(json,"username");
            String token=getJsonAsString(json,"token");

            //int role=getJsonAsInt(json,"role");
            String sql="";
            if (passwd!=null){

                String md5passwd=MD5.MD5Encode(passwd);

                sql = "select a.id id,a.user_name user_name,a.role role,a.head_url head_url,a.tel tel"
                      +",a.email email,a.nick nick,a.name_4s name_4s,a.brand_4s brand_4s,a.total_jifen total_jifen "
                      +"from gch_user_4s a where a.user_name ='"+username+"' and a.password='"+md5passwd+"' and isdelete=0";


                Userinfo_4s userinfo=null;
                Record r=create.fetchOne(sql);




                if (r!=null){
                    userinfo=r.into(Userinfo_4s.class);

                    if (userinfo == null || userinfo.head_url == "" || userinfo.head_url == null) {
                        userinfo.head_url = "";
                    } else {
                        userinfo.head_url = Config.OSS + userinfo.head_url.replace("type", "small");
                    }
                    log.debug(String.format("ua  %s ",2));
                    userinfo.session=getSession(username,ua,platform);
                }else{
                    return error(-1,"4S 用户不存在或者密码错误。");
                }


                return success(userinfo);

            }else if (token!=null){
                sql = "select user_name,password,nick,name_4s,brand_4s,addr,total_jifen,wx_open_id from gch_user_4s  where user_name='A01MG' and password='e10adc3949ba59abbe56e057f20f883e' and isdelete=0;";

            }else{
                //return  error(-2,"require password or token");
            }



            //log.info(String.format("query = {} {}"), query, UUID.randomUUID());



            return success();

        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }



    }



    private Userinfo_4s.Session getSession(String _userid,String _ua,String _platform){


        String sql="";
        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {
            UserAgent userAgent = UserAgent.parseUserAgentString(_ua);
           // String sql="select * from v3_"

            //Record3<String,String,String> ret;

            log.debug(String.format("get session %s",_userid));
            Result<V3UserDeviceRecord> result=create.fetch(V3_USER_DEVICE,V3_USER_DEVICE.USERID.eq(_userid));//.into(V3_USER_DEVICE);

            V3UserDeviceRecord record;
            String token=null;
            if (result.size()==0){


            }else if (result.size()==1){//// 这个地方有问题 如果这个逻辑总是记录一个设备id ，不过目前4s店 1个账号多人使用 就用1个device 也可以。
                token=result.get(0).getToken();
                result.get(0).setTokenLastLoginAt(new Timestamp(System.currentTimeMillis()));
                result.get(0).store();
            }else {
                for (V3UserDeviceRecord item:result){

                    if (item.getDeviceDesc()==_ua){
                        token=item.getToken();
                        item.setTokenLastLoginAt(new Timestamp(System.currentTimeMillis()));
                        item.store();
                        break;
                    }
                }


            }


            if (token==null){
                token= UtilBase.genUUID();
                record = create.newRecord(V3_USER_DEVICE);
                record.setToken(token);
                record.setUserid(_userid);
                record.setDeviceDesc(_ua);
                record.setDeviceBrowser(userAgent.getBrowser().getName());
                record.setDeviceOs(userAgent.getOperatingSystem().getName());
                record.setTokenLastLoginAt(new Timestamp(System.currentTimeMillis()));
                record.setDevicePlatform(_platform);
                record.store();


            }


            log.debug(String.format("SessionManager.getInstance().createSID"));
            String sessionid=SessionManager.getInstance().createSID(token,_userid);


            Userinfo_4s.Session se=new Userinfo_4s.Session();
            se.sessionid=sessionid;
            se.token=token;

            return se;



        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }
    }


    /**
    * @Description:
    * @Return: 登录注销接口
    * @Author: 韩武洽 @Wyshown
    * @Date: 2016-09-20 10:03
    * @Version 2.0
    */
    @POST
    @Path("auth/4s/logOut")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String logOut() {

        try{

            //SessionManager.delInstance().delSID(req.getHeader("sessionid"));
            return success();

        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }
    }


}
