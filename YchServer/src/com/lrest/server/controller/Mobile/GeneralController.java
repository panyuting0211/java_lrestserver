package com.lrest.server.controller.Mobile;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lrest.server.controller.BaseController;
import com.lrest.server.jooqmodel.tables.GchCoin;
import com.lrest.server.jooqmodel.tables.GchPay;
import com.lrest.server.jooqmodel.tables.records.GchCoinRecord;
import com.lrest.server.jooqmodel.tables.records.GchPayRecord;
import com.lrest.server.jooqmodel.tables.records.GchSpecialPriceCarRecord;
import com.lrest.server.jooqmodel.tables.records.GchUserGeneralRecord;
import com.lrest.server.models.Mobile.Mobile_Coin;
import com.lrest.server.models.User_general;
import com.lrest.server.services.Config;
import com.lrest.server.services.DB;
import com.lrest.server.utils.DateUtils;
import com.tencent.common.MD5;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import sun.misc.GC;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.lrest.server.jooqmodel.Tables.*;
import static com.lrest.server.utils.UtilBase.genUUID;
import static com.lrest.server.utils.UtilBase.getJsonAsInt;
import static com.lrest.server.utils.UtilBase.getJsonAsString;

/**
    * @Description:手机端普通用户个人中心控制器
    * @Return:
    * @Author: 孙磊
    * @Date:2016/10/25 14:58
    * @Version 2.0
    */
@Path("/general")
public class GeneralController extends BaseController {

    /**
     * @Description:获得普通用户的积分记录
     * @Return:
     * @Author: 孙磊
     * @Date:2016/10/25 13:32
     * @Version 2.0
     */
    @POST
    @Path("/myscore/{user_id}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String myScore(@PathParam("user_id") String userId) {

        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {

            String sql = "select score,FROM_UNIXTIME(UNIX_TIMESTAMP(`createtime`),'%Y-%m-%d') as createtime,info from gch_score where user_id='"+userId+"' and role=1 order by createtime desc";
            List<User_general.myscore> res = create.fetch(sql).into(
                    User_general.myscore.class);
            if(res.isEmpty()){
                return error(1,"暂无数据");
            }else{
                return success(res);
            }


        }catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }
    }
    /**
     * @Description:获得普通用户的优惠券
     * @Return:
     * @Author: 孙磊
     * @Date:2016/10/25 13:50
     * @Version 2.0
     */

    @POST
    @Path("/myticket/{user_id}/{status}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String myTicket(@PathParam("user_id") String user_id,@PathParam("status") String status) {

        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {
            //user_id:用户ID，status:状态分为（1未使用，2已使用，3已过期，4已占用）
            String sql = "select from_ticket,FROM_UNIXTIME(UNIX_TIMESTAMP(`createtime`),'%Y-%m-%d') as createtime,money,ticket_number,FROM_UNIXTIME(`end_time`,'%Y-%m-%d') as end_time from gch_ticket_user where user_id='"+user_id+"' and status='"+status+"' order by createtime desc";
            List<User_general.mysticket> res = create.fetch(sql).into(User_general.mysticket.class);
            if(res.isEmpty()){
                return error(1,"暂无数据");
            }else{
                return success(res);
            }

        }catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }
    }
    /**
     * @Description:个人中心-我的活动订单
     * @Return:
     * @Author: 孙磊
     * @Date:2016/10/25 16:37
     * @Version 2.0
     */
    @POST
    @Path("/myactivityorder")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String myActivityOrder(String query) {

        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {
            JsonObject json = new JsonParser().parse(query).getAsJsonObject();
            String user_id = getJsonAsString(json, "user_id");//用户ID
            String sql = "select gp.id,gp.out_trade_no,gp.createtime,gp.carstyle,gp.status,gp.money,ga.activity_name,FROM_UNIXTIME(ga.endtime,'%Y-%m-%d') as endtime,gp.exterior_img,gcm.imgurl,gp.brand_id,gp.brand_name,gp.car_model_id,gp.car_model_name from gch_pay as gp,gch_activities as ga,gch_car_model as gcm where gp.from_activityid = ga.id and gp.user_id='"+user_id+"'and gp.car_model_id=gcm.id  and gp.pay_obj=4 and gp.isdelete=0 order by gp.createtime desc";
            List<User_general.myactivityorder> res = create.fetch(sql).into(User_general.myactivityorder.class);
            if(res.isEmpty()){
                return error(1,"暂无数据");
            }else{
                return success(res);
            }

        }catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }
    }
    /**
        * @Description:根据活动订单ID获得订单详情
        * @Return:
        * @Author: 孙磊
        * @Date:2016/10/26 10:14
        * @Version 2.0
        */
    @POST
    @Path("/getorderdetail")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String getOrderDetail(String query){
        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {
            JsonObject json = new JsonParser().parse(query).getAsJsonObject();
            String id = getJsonAsString(json, "id");//订单ID
            String sql = "select * from gch_pay where id='"+id+"'";
            //GchPayRecord res = create.fetchOne(GCH_PAY,GCH_PAY.ID.eq(id));
            List<User_general.orderdetail> res = create.fetch(sql).into(User_general.orderdetail.class);
            if(res.isEmpty()){
                return error(1,"暂无数据");
            }else{
                return success(res);
            }

        }catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }
    }

    @POST
    @Path("/updateGeneralInfo")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String changeGeneralInfo(String query){
        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {
            JsonObject json = new JsonParser().parse(query).getAsJsonObject();

            String id = getJsonAsString(json, "id");
            String headUrl = getJsonAsString(json, "headUrl");
            String name = getJsonAsString(json, "name");
            String nick = getJsonAsString(json, "nick");
            String signInNo = getJsonAsString(json, "signInNo");
            String tel = getJsonAsString(json, "tel");
            Integer sex = getJsonAsInt(json, "sex");
            String cellphone = getJsonAsString(json, "cellphone");


            GchUserGeneralRecord res = create.newRecord(GCH_USER_GENERAL);
            // 主键
            res.setId(id);
            // 头像
            if (headUrl != null) {
                res.setHeadUrl(headUrl.substring(Config.OSS.length()));
            }
            // 名字
            if (name != null) {
                res.setName(name);
            }
            if (nick != null) {
                res.setNick(nick);
            }
            // 性别
            if (sex != null) {
                res.setSex(sex);
            }
            // 手机号
            if (tel != null && signInNo != null) {
                Record r = new AuthCodeController().checkSignInNoIsValid(
                        create,cellphone,signInNo
                        ,AuthCodeController.AUTHCODE_TYPE_FREESELECTCARPRICE);
                if (r == null) {
                    return  error(-1,"验证码不正确");
                } else {
                    res.setTel(tel);
                }
            }
            // 修改时间
            res.setUpdatetime(DateUtils.millisecondChangeTimestamp());
            res.update();
            return  success();
        }catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }
    }
    /**
        * @Description:获取车币首页接口
        * @param：user_id：用户ID
        * @Return:(1:已经存在，0：不存在)isfirstlogin：是否首次登陆，iscomplete：是否完善资料，isheadimg：是否上传头像，issign：今天是否签到，coin：车币
        * @Author: 孙磊
        * @Date:2016/11/30 9:52
        * @Version 2.0
        */
    @POST
    @Path("/coinhome/{user_id}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String coinHome(@PathParam("user_id") String userId) {

        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {

            Mobile_Coin home = new Mobile_Coin();
            //是否首次登陆
            GchCoinRecord firstlogin = create.fetchOne(GCH_COIN,GCH_COIN.USER_ID.eq(userId).and(GCH_COIN.TYPES.eq("first_login")));
            //是否完善用户资料
            GchCoinRecord complete = create.fetchOne(GCH_COIN,GCH_COIN.USER_ID.eq(userId).and(GCH_COIN.TYPES.eq("complete")));
            //是否上传头像
            GchCoinRecord headimg = create.fetchOne(GCH_COIN,GCH_COIN.USER_ID.eq(userId).and(GCH_COIN.TYPES.eq("headimg")));
            //获得车币
            GchUserGeneralRecord coin = create.fetchOne(GCH_USER_GENERAL,GCH_USER_GENERAL.ID.eq(userId));
            //今天是否签到
            Date date =new Date();
            SimpleDateFormat format =  new SimpleDateFormat( "yyyy-MM-dd" );
            String today = format.format(date);
            String  sql= "select * from gch_coin as gc where left(gc.createtime,'10')='"+today+"'and gc.types='everyday' and gc.user_id='"+userId+"'";
            Result<Record> sign = create.fetch(sql);
            if( firstlogin!=null) {
                home.isfirstlogin = 1;
            }else {
                home.isfirstlogin = 0;
            }
            if( complete!=null) {
                home.iscomplete = 1;
            }else {
                home.iscomplete = 0;
            }
            if( headimg!=null) {
                home.isheadimg = 1;
            }else {
                home.isheadimg = 0;
            }
            if(sign.isEmpty()){
                home.issign = 0;
            }else{
                home.issign = 1;
            }
            if(coin!=null){
                home.coin = coin.getTotalCoin();
            }else{
                home.coin =0;
            }
            return success(home);
        }catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }
    }
    /**
        * @Description:我的车币记录
        * @param：
        * @Return:
        * @Author: 孙磊
        * @Date:2016/11/25 9:27
        * @Version 2.0
        */
    @POST
    @Path("/mycoin/{user_id}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String myCoin(@PathParam("user_id") String userId) {

        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {
            String sql = "select id,coin,info,FROM_UNIXTIME(UNIX_TIMESTAMP(`createtime`),'%Y-%m-%d') as createtime from gch_coin where user_id='"+userId+"' and role=1 order by createtime desc";
            List<Mobile_Coin.myCoin> res = create.fetch(sql).into(Mobile_Coin.myCoin.class);
            if(res.isEmpty()){
                return error(1,"暂无数据");
            }else{
                return success(0,res);
            }
        }catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }
    }
    /**
        * @Description:每日签到
        * @param：user_id：用户ID
        * @Return:{code:返回的状态码，data：返回的数据说明}
        * @Author: 孙磊
        * @Date:2016/11/25 11:12
        * @Version 2.0
        */
    @POST
    @Path("/coineveryday/{user_id}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String coinEveryday(@PathParam("user_id") String userId) {

        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {

            //今天是否签到
            Date date =new Date();
            SimpleDateFormat format =  new SimpleDateFormat( "yyyy-MM-dd" );
            String today = format.format(date);
            String  sql= "select * from gch_coin as gc where left(gc.createtime,'10')='"+today+"'and gc.types='everyday' and gc.user_id='"+userId+"'";
            Result<Record> sign = create.fetch(sql);
            //判断今天是否已经打卡
            if(sign.isNotEmpty()){
                return success(1,"您今天已经打过卡了!");
            }else{
                //插入车币记录表
                String id = MD5.MD5Encode(String.valueOf(System.currentTimeMillis()));
                sql = "INSERT INTO `gch_coin` (id,user_id,coin,role,types,info) VALUES ('"+id+"','"+userId+"',5,1,'everyday','打卡增加5车币')";
                int res =  create.execute(sql);
                if(res ==1){
                    //用户加车币
                    sql = "UPDATE gch_user_general set total_coin=total_coin+5 where id='"+userId+"'";
                    int ress =  create.execute(sql);
                    if(ress == 1){
                        return success(0,"打卡成功");
                    }else{
                        return success(2,"打卡成功,车币增加失败");
                    }
                }else{
                    return success(3,"打卡失败");
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }
    }
}
