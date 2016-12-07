package com.lrest.server.controller.member4s;

/**
 * Created by acans on 16/6/21.
 */

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lrest.server.controller.BaseController;
import com.lrest.server.jooqmodel.tables.records.GchReceiptAddressRecord;
import com.lrest.server.jooqmodel.tables.records.GchUserGeneralRecord;
import com.lrest.server.jooqmodel.tables.records.GchUser_4sRecord;
import com.lrest.server.models.*;
import com.lrest.server.services.Config;
import com.lrest.server.services.DB;
import com.tencent.common.MD5;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.lrest.server.jooqmodel.Tables.GCH_RECEIPT_ADDRESS;
import static com.lrest.server.jooqmodel.Tables.GCH_USER_4S;
import static com.lrest.server.jooqmodel.Tables.GCH_USER_GENERAL;
import static com.lrest.server.utils.UtilBase.getJsonAsString;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

@Path("/4s/accountmanagement")
public class Member4sController_accountmanagement extends BaseController{
    private   final org.slf4j.Logger log = LoggerFactory.getLogger(getClass());
    public  static final Integer MAX_ADRESS_NUM = 8;

//    @Inject
//    private SessionManager sessionManager;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String get() {



        return success("4s");

    }
    /**
        * @Description:获得4S店会员的基本信息
        * @Return:
        * @Author: 孙磊
        * @Date:2016/9/19 13:20
        * @Version 2.0
        */
    @POST
    @Path("/information")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String information(String query) {
        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {
            JsonObject json = new JsonParser().parse(query).getAsJsonObject().get("query").getAsJsonObject();
            String uid = getJsonAsString(json,"uid");

            Userinfo_4s userinfo4s = new Userinfo_4s();

            String sql = "select a.id id,a.user_name user_name,a.head_url head_url,a.tel tel "
                    +",a.email email,a.nick nick,a.name_4s name_4s,a.brand_4s brand_4s,a.total_jifen total_jifen,a.contacts contacts,a.addr addr,b.brand_name brand_name"
                    +" from gch_user_4s a,gch_brand b where a.id ='"+uid+"' and a.brand_4s=b.id";



            Userinfo_4s userinfo=null;
            Record r=create.fetchOne(sql);
            userinfo=r.into(Userinfo_4s.class);
//            userinfo.head_url = Config.OSS+userinfo.head_url.replace("type","small");


            if (userinfo == null || userinfo.head_url == "" || userinfo.head_url == null) {
                userinfo.head_url = Config.OSS;
            } else {
                userinfo.head_url = Config.OSS + userinfo.head_url.replace("type", "small");
            }

            userinfo4s = userinfo;

             sql = " select a.id brand_id,b.brand_name brand_name from gch_user_4s a,gch_brand b where a.id ='"+uid+"' and a.brand_4s=b.id"+
                     " UNION  select b.id brand_id,b.brand_name from gch_user_4s_brand a,gch_brand b where a.user_4s_id='"+uid+"'and a.isdelete=0 and a.brand_id=b.id" ;
                List<Userinfo_4s.brandList> rets = create.fetch(sql).into(Userinfo_4s.brandList.class);

            userinfo4s.brandlist = rets;

            return success(userinfo);


        } catch (Exception e) {

            return error(-1, e.getMessage());
        }

    }
    /**
        * @Description: 更新4S店会员基本信息
        * @Return:
        * @Author: 孙磊
        * @Date:2016/9/19 13:21
        * @Version 2.0
        */
    @POST
    @Path("/updateinformation")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateinformation(String query) {
        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {
            JsonObject json = new JsonParser().parse(query).getAsJsonObject().get("query").getAsJsonObject();
            String uid = getJsonAsString(json,"uid");
            String contacts = getJsonAsString(json,"contacts");
            String tel = getJsonAsString(json,"tel");
            String email = getJsonAsString(json,"email");

            GchUser_4sRecord userinfo =create.fetchOne(GCH_USER_4S,GCH_USER_4S.ID.eq(uid));

            if(userinfo !=null){
                if(tel.isEmpty() || contacts.isEmpty()){
                    return  error(-1,"手机号和联系人不能为空！");
                }
                userinfo.setTel(tel);
                userinfo.setContacts(contacts);
                userinfo.setEmail(email);

                userinfo.store();

                return success(1,"修改用户信息成功！");
            }

            return error(0,"系统出错请稍后重试！");

        } catch (Exception e) {

            return error(-1, e.getMessage());
        }

    }
    /**
        * @Description:4S店会员的收货地址列表
        * @Return:
        * @Author: 孙磊
        * @Date:2016/9/19 13:25
        * @Version 2.0
        */
    @POST
    @Path("/addrlist")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String addrlist(String query) {
        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {
            JsonObject json = new JsonParser().parse(query).getAsJsonObject().get("query").getAsJsonObject();
            String uid = getJsonAsString(json,"uid");
            String sql = "select id,user_id,receiver,telphone,receipt_province,receipt_city,receipt_quarter,receipt_address,status,province_id,city_id,quarter_id,createtime from gch_receipt_address where user_id='"+uid+"' and isdelete=0 order by status desc,createtime desc "
                   ;

            List<M4S_ACT_SHIPADDR.ShippingAddress> list=create.fetch(sql).into(M4S_ACT_SHIPADDR.ShippingAddress.class);
            return success(list);

        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }
    }
    /**
        * @Description:设置4S店会员的收货地址为默认收货地址
        * @Return:
        * @Author: 孙磊
        * @Date:2016/9/19 13:25
        * @Version 2.0
        */
    @POST
    @Path("/setdefault")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String setdefault(String query) {
        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {
            JsonObject json = new JsonParser().parse(query).getAsJsonObject().get("query").getAsJsonObject();
            String uid = getJsonAsString(json,"uid");
            String id = getJsonAsString(json,"id");

            String sql = "select * from gch_receipt_address where id='"+id+"'";
            create.fetchOne(sql).into(M4S_ACT_SHIPADDR.ShippingAddress.class);

            GchReceiptAddressRecord find =create.fetchOne(GCH_RECEIPT_ADDRESS,GCH_RECEIPT_ADDRESS.ID.eq(id));
            if(find.getStatus() == 1){
                return success(0,"该地址已经是默认的收获地址！");
            }else{

                sql = "update gch_receipt_address set status=0 where isdelete=0 and user_id='"+uid+"'";


                create.execute(sql);

                sql = "update gch_receipt_address set status=1 where  id='"+id+"'";

                create.execute(sql);

                return success(1,"设置成功！");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }
    }
    /**
        * @Description:添加收货地址
        * @Return:
        * @Author: 孙磊
        * @Date:2016/9/19 13:26
        * @Version 2.0
        */
    @POST
    @Path("/addaddress")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String addaddress(String query) {
        try (Connection conn = DB.getConnection();
            DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {
            JsonObject json = new JsonParser().parse(query).getAsJsonObject().get("query").getAsJsonObject();
            String user_id = getJsonAsString(json,"user_id");

            int addressCount = create.fetch("SELECT * FROM gch_receipt_address where isdelete=0 AND user_id = '" + user_id + "'").size();
            if (addressCount >= MAX_ADRESS_NUM) {
                return  error(-1,"最大地址数量超出" + MAX_ADRESS_NUM + "条,添加失败!" );
            } else {
                String id = MD5.MD5Encode(String.valueOf(System.currentTimeMillis()));
                String receiver = getJsonAsString(json,"receiver");
                String telphone = getJsonAsString(json,"telphone");
                String province_id = getJsonAsString(json,"province_id");
                String receipt_province = getJsonAsString(json,"receipt_province");
                String city_id = getJsonAsString(json,"city_id");
                String receipt_city = getJsonAsString(json,"receipt_city");
                String quarter_id = getJsonAsString(json,"quarter_id");
                String receipt_quarter = getJsonAsString(json,"receipt_quarter");
                String receipt_address = getJsonAsString(json,"receipt_address");
                int status = Integer.parseInt(getJsonAsString(json,"status"));
                if(status == 1) {
                    String sql = "update gch_receipt_address set status=0 where isdelete=0 and user_id='" + user_id + "'";

                    create.execute(sql);
                } else if (status == 0) {
                    String sql = "update gch_receipt_address set status=1 where isdelete=0 and user_id='" + user_id + "'";

                    create.execute(sql);
                }

                GchReceiptAddressRecord res = create.newRecord(GCH_RECEIPT_ADDRESS);

                res.setId(id);
                res.setUserId(user_id);
                res.setRole((byte) 2);
                res.setReceiver(receiver);
                res.setTelphone(telphone);
                res.setProvinceId(province_id);
                res.setReceiptProvince(receipt_province);
                res.setCityId(city_id);
                res.setReceiptCity(receipt_city);
                res.setQuarterId(quarter_id);
                res.setReceiptQuarter(receipt_quarter);
                res.setReceiptAddress(receipt_address);
                res.setStatus((byte) status);
                res.setIsdelete(0);

                if(res.store() == 1){
                    return success(0,id);
                }else{
                    return  error(-1,"添加失败");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }
    }
    /**
        * @Description:更新收货地址
        * @Return:
        * @Author: 孙磊
        * @Date:2016/9/19 13:27
        * @Version 2.0
        */
    @POST
    @Path("/updateaddress")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateaddress(String query) {
        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {
            JsonObject json = new JsonParser().parse(query).getAsJsonObject().get("query").getAsJsonObject();
            String id = getJsonAsString(json,"id");
            String receiver = getJsonAsString(json,"receiver");
            String telphone = getJsonAsString(json,"telphone");
            String province_id = getJsonAsString(json,"province_id");
            String receipt_province = getJsonAsString(json,"receipt_province");
            String city_id = getJsonAsString(json,"city_id");
            String receipt_city = getJsonAsString(json,"receipt_city");
            String quarter_id = getJsonAsString(json,"quarter_id");
            String receipt_quarter = getJsonAsString(json,"receipt_quarter");
            String receipt_address = getJsonAsString(json,"receipt_address");
            int status = Integer.parseInt(getJsonAsString(json,"status"));
            GchReceiptAddressRecord  res = create.fetchOne(GCH_RECEIPT_ADDRESS,GCH_RECEIPT_ADDRESS.ID.eq(id));
            if( status == 1 && res.getStatus() ==0){

                String  sql = "update gch_receipt_address set status=0 where isdelete=0 and user_id='"+res.getUserId()+"'";

                create.execute(sql);

                res.setStatus((byte) 1);
            } else if (status == 0 && res.getStatus() ==1){
//                String  sql = "update gch_receipt_address set status=1 where isdelete=0 and user_id='"+res.getUserId()+"'";

//                create.execute(sql);

                res.setStatus((byte) 0);

            }

            res.setReceiver(receiver);
            res.setTelphone(telphone);
            res.setProvinceId(province_id);
            res.setReceiptProvince(receipt_province);
            res.setCityId(city_id);
            res.setReceiptCity(receipt_city);
            res.setQuarterId(quarter_id);
            res.setReceiptQuarter(receipt_quarter);
            res.setReceiptAddress(receipt_address);


            if(res.update() == 1){
                return success(0,"修改成功");
            }else{
                return  error(-1,"修改失败");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }
    }
    /**
        * @Description:删除收货地址
        * @Return:
        * @Author: 孙磊
        * @Date:2016/9/19 13:27
        * @Version 2.0
        */
    @POST
    @Path("/deladdress")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String deladdress(String query) {
        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {
            JsonObject json = new JsonParser().parse(query).getAsJsonObject().get("query").getAsJsonObject();
            String id = getJsonAsString(json,"id");
            GchReceiptAddressRecord  res = create.fetchOne(GCH_RECEIPT_ADDRESS,GCH_RECEIPT_ADDRESS.ID.eq(id).and(GCH_RECEIPT_ADDRESS.ISDELETE.eq(0)));
            if(res != null){
                res.setIsdelete(1);
                if(res.store() == 1){
                    return success(0,"删除成功");
                }else{
                    return  error(-1,"删除失败");
                }
            }else{
                return  error(-2,"删除的地址已经被删除");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }
    }
    /**
        * @Description:重新设置密码
        * @Return:
        * @Author: 孙磊
        * @Date:2016/9/19 13:27
        * @Version 2.0
        */
    @POST
    @Path("/resetpassword")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String resetpassword(String query) {
        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {
            JsonObject json = new JsonParser().parse(query).getAsJsonObject().get("query").getAsJsonObject();
            String id = getJsonAsString(json,"id");
            String password = getJsonAsString(json,"password");
            String newpassword = getJsonAsString(json,"newpassword");
            GchUser_4sRecord  res = create.fetchOne(GCH_USER_4S,GCH_USER_4S.ID.eq(id).and(GCH_USER_4S.ISDELETE.eq(0)));
            if(res != null){
                if(res.getPassword().equals(MD5.MD5Encode(password)) ){
                    res.setPassword( MD5.MD5Encode(newpassword));
                    if(res.store() == 1){
                        return success(0,"修改成功");
                    }else{
                        return  error(-1,"修改失败");
                    }
                }else{
                    return  error(-3,"原密码输入不正确");
                }

            }else{
                return  error(-2,"账号不存在");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }
    }
}
