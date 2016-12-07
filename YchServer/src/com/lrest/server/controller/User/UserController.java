package com.lrest.server.controller.User;

/**
 * Created by acans on 16/6/21.
 */

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lrest.server.controller.BaseController;
import com.lrest.server.jooqmodel.tables.GchCarModel;
import com.lrest.server.jooqmodel.tables.GchPay;
import com.lrest.server.jooqmodel.tables.GchPayAreaLowPrice;
import com.lrest.server.jooqmodel.tables.GchUserAttentionCarModel;
import com.lrest.server.jooqmodel.tables.records.*;
import com.lrest.server.models.*;
import com.lrest.server.services.DB;
import com.lrest.server.services.weixin.WXAPIConfig;
import com.lrest.server.utils.UtilBase;
import com.tencent.common.MD5;
import okhttp3.RequestBody;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.lrest.server.jooqmodel.Tables.*;
import static com.lrest.server.utils.UtilBase.*;
import static java.util.stream.Collectors.*;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Path("/nl/user")
public class UserController extends BaseController {
    private final org.slf4j.Logger log = LoggerFactory.getLogger(getClass());

    @GET
    public String get() {
        log.debug("/nl/user");
        return "/nl/user";
    }

    @POST
    @Path("/attention/{action}/{user_id}/{car_model_id}/")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String attention(@PathParam("action") String action,@PathParam("user_id") String user_id,@PathParam("car_model_id") String car_model_id) {
        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {
            GchUserAttentionCarModelRecord result=create.fetchOne(GCH_USER_ATTENTION_CAR_MODEL,GCH_USER_ATTENTION_CAR_MODEL.USER_ID.eq(user_id).and(GCH_USER_ATTENTION_CAR_MODEL.CAR_MODEL_ID.eq(car_model_id)));//.into(V3_USER_DEVICE);
            int attention_status;
            //之前没有关注过该车型
            if(result==null){

                if( action .equals("isattention")){
                    attention_status = 0;
                    return success(0,"未关注");
                }
                result = create.newRecord(GCH_USER_ATTENTION_CAR_MODEL);
                result.setId( MD5.MD5Encode(String.valueOf(System.currentTimeMillis())));
                result.setUserId(user_id);
                result.setCarModelId(car_model_id);
                result.setIsdelete(0);

                 int status =  result.store();
                if(status == 1){

                    GchCarModelRecord carmodel = create.fetchOne(GCH_CAR_MODEL,GCH_CAR_MODEL.ID.eq(car_model_id));
                    if(carmodel!=null){

                        carmodel.setAttentionCount(carmodel.getAttentionCount()+1);


                    }else{
                        return error(-1, "您关注的车型不存在！");
                    }

                }
                return success(1,"关注成功");

            }else{
                if( action .equals("isattention")){

                    if(result.getIsdelete()==0){
                        //关注过
                         attention_status = 1;
                    }else{
                        //未关注
                         attention_status = 0;
                    }

                    return success(attention_status,attention_status==0?"未关注":"已关注");
                }else{
                    result.setIsdelete(result.getIsdelete()==0?1:0);

                    result.store();
                    //1表示关注成功，0表示关注失败
                    return success(result.getIsdelete()==0?0:1,result.getIsdelete()==0?"关注成功":"取消关注成功");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }

    }
    @POST
    @Path("/add")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String gch_user_add(String query) {

        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {


            //log.info(String.format("query = {} {}"), query, UUID.randomUUID());

            JsonObject json = new JsonParser().parse(query).getAsJsonObject().get("query").getAsJsonObject();

            String openid=getJsonAsString(json, "openid");
            String tel=getJsonAsString(json, "tel");
            String name=getJsonAsString(json, "name");



            String sql = "select count(*) from gch_user_general where wx_open_id='"
                    + openid + "'"
                    + " and isdelete=0"
                    + " and user_name='"+ tel+"'";
            int count = create.fetchOne(sql).into(int.class);
            log.debug(String.format("%s %s %s %d",openid,tel,name,count));
            if (count > 0) {
                return success(1, "该微信用户已经存在,跳过创建","");
            }else{

                GchUserGeneralRecord record=create.fetchOne(
                        GCH_USER_GENERAL,GCH_USER_GENERAL.WX_OPEN_ID.eq(openid).and(GCH_USER_GENERAL.USER_NAME.isNull()).and(GCH_USER_GENERAL.ISDELETE.eq(0)));


                if (record==null){
                    log.debug("is null");

                     record=create.fetchOne(
                            GCH_USER_GENERAL,GCH_USER_GENERAL.WX_OPEN_ID.eq(openid).and(GCH_USER_GENERAL.USER_NAME.ne(tel)).and(GCH_USER_GENERAL.ISDELETE.eq(0)));


                    if (record!=null){
                          log.debug(".eq(openid).and(GCH_USER_GENERAL.USER_NAME.ne(tel))");
                          return error(-1, "你的微信号已经绑定过其他手机号码,请使用原先绑定手机号码参与,或者联系客服。");
                    }


                    record=create.fetchOne(GCH_USER_GENERAL,GCH_USER_GENERAL.USER_NAME.eq(tel).and(GCH_USER_GENERAL.ISDELETE.eq(0)));

                    if (record==null){
                        long seconds = System.currentTimeMillis()/1000;
                        log.debug("openid isnull tel ok");
                        record = create.newRecord(GCH_USER_GENERAL);

                        record.setId(genUUID());
                        record.setUserName(tel);
                        record.setTel(tel);
                        record.setPassword("e10adc3949ba59abbe56e057f20f883e");
                        record.setRole(1);
                        record.setLoginip(getIpAddr(req));
                        record.setLogintime(String.valueOf(seconds));
                        record.setStatus((short) 1);
                        record.setName(name);
                        record.setWxOpenId(openid);
                        record.setIsdelete(0);


                        record.store();

                    }else{

                        if (!openid.equals(record.getWxOpenId())&&(record.getWxOpenId()!=null)){

                           // if (record.getWxOpenId()!=null) {
                                return error(-1, "你的手机号码已经绑定过其他微信号,请使用原先微信号码参与活动,或者联系客服。");
                            //}
                        }else{
                            record.setWxOpenId(openid);

                            record.store();

                            return success(1, "该微信用户已经存在,绑定opentid,跳过创建","");
                        }



                    }


                }else{
                    log.debug("not null");

                    record.setUserName(tel);
                    record.setTel(tel);
                    record.setName(name);
                    record.setPassword("e10adc3949ba59abbe56e057f20f883e");

                    record.store();
                }

                return success(record.getId());

                /*
                GchUserGeneralRecord record = create.newRecord(GCH_USER_GENERAL);

                record.setId(genUUID());
                record.setUserName(getJsonAsString(json, "tel"));
                record.setTel(getJsonAsString(json, "tel"));
                record.setPassword("e10adc3949ba59abbe56e057f20f883e");
                record.setRole(1);
                record.setName(getJsonAsString(json, "name"));
                record.setWxOpenId(getJsonAsString(json, "openid"));
                record.setIsdelete(0);


                // create.attach(record);

                record.store();


                return success(record.getId());*/

//                sql ="select count(*) from gch_user_general where wx_open_id='"
//                        + getJsonAsString(json, "openid") + "'"
//                        + " and isdelete=0"
//                        + " and user_name is null";
//                count = create.fetchOne(sql).into(int.class);
//                if(count >0){
//                    sql = "update gch_user_general set user_name="
//                            +getJsonAsString(json, "tel")+"'where wx_open_id='"
//                            +getJsonAsString(json, "openid") + "'";
//
//                    int ret=create.execute(sql);
//
//                    if (ret==0){
//                        return  error(-1,"用户openid已经存在,手机号码写入失败");
//                    }else{
//                        return success();
//                    }
//                }else{
//                    GchUserGeneralRecord record = create.newRecord(GCH_USER_GENERAL);
//
//                    record.setId(genUUID());
//                    record.setUserName(getJsonAsString(json, "tel"));
//                    record.setTel(getJsonAsString(json, "tel"));
//                    record.setPassword("e10adc3949ba59abbe56e057f20f883e");
//                    record.setRole(1);
//                    record.setName(getJsonAsString(json, "name"));
//                    record.setWxOpenId(getJsonAsString(json, "openid"));
//                    record.setIsdelete(0);
//
//
//                    // create.attach(record);
//
//                    record.store();
//
//
//                    return success(record.getId());
//                }
            }



        } catch (Exception e) {
            e.printStackTrace();
            return error(-2, e.getMessage());
        }


    }
    //用户微信端登录
    @POST
    @Path("/login/wx/{openid}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String login_wx(@PathParam("openid") String openid) {

        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {
            //User_general ret = new User_general();
           /* String sql = "select id,wx_open_id from gch_user_general where wx_open_id='"+openid+"'and isdelete=0";
            List<User_general.generals> usergeneral = create.fetch(sql).into(User_general.generals.class);*/
            /*String result = create.select(GCH_USER_GENERAL.ID,GCH_USER_GENERAL.WX_OPEN_ID)
                    .from(GCH_USER_GENERAL)
                    .where(GCH_USER_GENERAL.WX_OPEN_ID.eq(openid).and(GCH_USER_GENERAL.ISDELETE.eq(0)))
                    .fetchOne();*/
            JsonObject params = new JsonObject();
            GchUserGeneralRecord usergeneral =create.fetchOne(GCH_USER_GENERAL,GCH_USER_GENERAL.WX_OPEN_ID.eq(openid).and(GCH_USER_GENERAL.ISDELETE.eq(0)));
                if(usergeneral!= null){

                    params.addProperty("id",usergeneral.getId());
                    params.addProperty("wx_open_id",usergeneral.getWxOpenId());
                    params.addProperty("tel",usergeneral.getTel());

                    return success(1,params);
                }else{
                  /*GchUserGeneralRecord res = create.fetchOne(GCH_USER_GENERAL,GCH_USER_GENERAL.WX_OPEN_ID.eq(openid).and(GCH_USER_GENERAL.ISDELETE.eq(0)));
                    long seconds = System.currentTimeMillis()/1000;
                    res = create.newRecord(GCH_USER_GENERAL);
                    res.setId( MD5.MD5Encode(String.valueOf(System.currentTimeMillis())));
                    res.setWxOpenId(openid);
                    res.setIsdelete(0);
                    res.setRole(1);
                    res.setStatus((short) 1);
                    res.setLoginip(getIpAddr(req));
                    res.setLogintime(seconds);
                    res.store();

                    params.addProperty("id",res.getId());
                    params.addProperty("wx_open_id",res.getWxOpenId());*/
                   /*String date =" [{"id": "a2d27e7d97d08e6ebbfbb7eb4632c341","wx_open_id": "oItA9v52kZQ9sf-aiNcMsgir-89D"}]";*/
                    return success(2,"账户不存在！",null/*,params*/);
                }




        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }


    }

    //用户通过微信OPENID绑定手机号和用户名
    @POST
    @Path("/binding")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String binding(String query) {

        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {

            JsonObject json = new JsonParser().parse(query).getAsJsonObject().get("query").getAsJsonObject();

            GchUserGeneralRecord usergeneral =create.fetchOne(GCH_USER_GENERAL,GCH_USER_GENERAL.WX_OPEN_ID.eq(getJsonAsString(json, "open_id")).and(GCH_USER_GENERAL.ISDELETE.eq(0)));
            if(usergeneral!= null){

                if(usergeneral.getUserName() !=null && usergeneral.getUserName() !=""){
                    GchUserGeneralRecord find = create.fetchOne(GCH_USER_GENERAL,GCH_USER_GENERAL.USER_NAME.eq(getJsonAsString(json, "tel")).and(GCH_USER_GENERAL.WX_OPEN_ID.eq(getJsonAsString(json, "open_id"))).and(GCH_USER_GENERAL.ISDELETE.eq(0)));
                    if(find !=null){
                        return success(1,"绑定成功！");
                    }else{
                        return error(0,"该微信号已被别的手机绑定,如需解绑请联系管理员！");
                    }

                }else{
                    GchUserGeneralRecord find = create.fetchOne(GCH_USER_GENERAL,GCH_USER_GENERAL.USER_NAME.eq(getJsonAsString(json, "tel")).and(GCH_USER_GENERAL.ISDELETE.eq(0)));

                    if(find!=null){
                        return error(0,"该手机号已经存在,请换个手机号绑定！");
                    }

                    usergeneral.setUserName(getJsonAsString(json, "tel"));
                    usergeneral.setName(getJsonAsString(json, "name"));
                    usergeneral.setTel(getJsonAsString(json, "tel"));

                    usergeneral.store();

                    return success(1,"微信绑定用户名成功！");
                }


            }else{
                GchUserGeneralRecord usertel =create.fetchOne(GCH_USER_GENERAL,GCH_USER_GENERAL.USER_NAME.eq(getJsonAsString(json, "tel")).and(GCH_USER_GENERAL.ISDELETE.eq(0)));
                if(usertel!=null){
                    usertel.setWxOpenId(getJsonAsString(json, "open_id"));
                    usertel.store();
                    return success(2,"用户名绑定微信成功！");
                }else{
                    long seconds = System.currentTimeMillis()/1000;
                    usertel = create.newRecord(GCH_USER_GENERAL);

                    usertel.setId(genUUID());
                    usertel.setUserName(getJsonAsString(json, "tel"));
                    usertel.setTel(getJsonAsString(json, "tel"));
                    usertel.setPassword("e10adc3949ba59abbe56e057f20f883e");
                    usertel.setRole(1);
                    usertel.setLoginip(getIpAddr(req));
                    usertel.setLogintime(String.valueOf(seconds));
                    usertel.setStatus((short) 1);
                    //usertel.setName(name);
                    usertel.setWxOpenId(getJsonAsString(json, "open_id"));
                    usertel.setIsdelete(0);
                    usertel.store();
                    return success(3,"创建用户名成功！");
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
            return error(-1, e.getMessage());
        }

    }

    //用户通过微信OPENID绑定手机号和用户名
    @POST
    @Path("/getuserinfo/{open_id}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String getuserinfo(@PathParam("open_id") String openid) {

        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {



            OkHttpClient client = UtilBase.getUnsafeOkHttpClient();


            String url = "https://www.woodche.com/php/index.php/home/Api/get_user_info/openid/"+openid;
            //OkHttpClient client = new OkHttpClient();



            Request request = new Request.Builder().url(url).build();
            Response response = client.newCall(request).execute();


            if (response.isSuccessful()) {
                String s=response.body().string();
               /* JsonObject params = new JsonObject();
                JsonElement json=new JsonParser().parse(response.body().string());
                params.addProperty("nickname",json.getAsJsonObject().get("nickname").toString());
                params.addProperty("headimgurl",json.getAsJsonObject().get("headimgurl").toString());*/
                return successJson(s);
            } else {
                return error(0,"获取用户信息失败！");
                //throw new IOException("Unexpected code " + response);
            }



        } catch (Exception e) {

            e.printStackTrace();
            return error(-1, e.getMessage());
        }


    }

    /**
        * @Description:晒单里所有订车成功的车款
        * @Return:
        * @Author: 孙磊
        * @Date:2016/10/13 10:22
        * @Version 2.0
        */
    @POST
    @Path("/myordercar/{user_id}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String myordercar(@PathParam("user_id") String user_id) {

        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {

            String sql = "select id,carstyle from gch_pay where id_4s='"+user_id+"' and pay_obj=2 AND status != 0 AND status != 2 AND status != 10 and isdelete=0 group by car_id,exterior_color_id,interior_color_id ";
            List<Member4s_orderlist> res = create.fetch(sql).into(Member4s_orderlist.class);
            return success(res);

        }catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }
    }

}
