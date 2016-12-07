package com.lrest.server.controller.Mobile;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lrest.server.controller.BaseController;
import com.lrest.server.jooqmodel.tables.records.GchCarLifeBbsRecord;
import com.lrest.server.jooqmodel.tables.records.GchCarLifeThumbsRecord;
import com.lrest.server.jooqmodel.tables.records.GchUserGeneralRecord;
import com.lrest.server.models.BaseCount;
import com.lrest.server.models.Mobile.Mobile_CarLifeBBS;
import com.lrest.server.services.Config;
import com.lrest.server.services.DB;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.util.List;
import java.util.stream.Collectors;
import static com.lrest.server.jooqmodel.Tables.*;

import static com.lrest.server.utils.UtilBase.getJsonAsInt;
import static com.lrest.server.utils.UtilBase.getJsonAsString;

/**
 * DESCRIPTION: Mobile端 车生活
 *
 * @Author 韩武洽
 * @Date 2016-10
 * @Time 27 15:14
 **/

@Path("mobile/carLifeBBS")
public class Mobile_CarLifeBBSController extends BaseController{

    /**
     * @param query typeId可传可不传,不传的话为空  {"query":{"pagenum":10,"page":1,"pid":"1","typeId":""}}
     * @Description:
     * @Return: 车生活
     * @Author: 韩武洽 @Wyshown
     * @Date: 2016/10/27 17:13
     * @Version 2.0
     */
    @POST
    @Path("findCarLifeBBSList")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String findCarLifeBBSList(String query) {
        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {
            JsonObject json = new JsonParser().parse(query).getAsJsonObject().get("query").getAsJsonObject();
            Integer pagenum = getJsonAsInt(json, "pagenum");
            Integer page = getJsonAsInt(json, "page");
            String pid = getJsonAsString(json, "pid");
            String typeId = getJsonAsString(json, "typeId");

            // 查询出车型的SQL语句
            StringBuilder carLifeSql = new StringBuilder();
            carLifeSql.append(" SELECT \n");
            carLifeSql.append(" bbs.id AS bbsId, \n");
            carLifeSql.append(" bbs.title, \n");
            carLifeSql.append(" bbs.pid, \n");
            carLifeSql.append(" bbs.type_id AS typeId, \n");
            carLifeSql.append(" bbs.type_name AS typeName, \n");
            carLifeSql.append(" CASE \n");
            carLifeSql.append(" WHEN bbs.image IS NULL THEN NULL \n");
            carLifeSql.append(" ELSE CONCAT('" + Config.OSS + "',REPLACE(bbs.image,\"type\",\"small\")) \n");
            carLifeSql.append(" END AS image, \n");
            carLifeSql.append(" bbs.summary, \n");
            carLifeSql.append(" bbs.car_id AS carId, \n");
            carLifeSql.append(" bbs.car_name AS carName, \n");
            carLifeSql.append(" bbs.click_amount AS clickAmount, \n");
            carLifeSql.append(" bbs.comment_amount AS commentAmount, \n");
            carLifeSql.append(" bbs.thumbs_amount AS thumbsAmount \n");

            carLifeSql.append(" FROM gch_view_car_bbs AS bbs \n");
            carLifeSql.append(" WHERE bbs.pid = '" + pid + "' \n");

            if (typeId != null && !typeId.equals("0") && !typeId.equals("")) {
                carLifeSql.append(" AND bbs.type_id = '" + typeId + "' \n");
            }
            carLifeSql.append(" ORDER BY bbs.createtime desc \n");

            List<Mobile_CarLifeBBS> rets = create.fetch(carLifeSql.toString()).into(Mobile_CarLifeBBS.class);
            BaseCount<Mobile_CarLifeBBS> resultData =  new BaseCount<>();
            resultData.count = (int)rets.stream().count();
            resultData.rows = rets.stream().skip((page - 1) * pagenum)
                    .limit(pagenum).collect(Collectors.toList());
            return success(resultData);
        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }
    }

    /**
     * @Description:个人中心--车主秀列表
     * @Params:userId;
     * @Return:
     * @Author: 潘玉婷 @panyuting
     * @Date: 2016/11/9 13:41
     * @Version 2.0
     */
    @POST
    @Path("userCarLifeBBSList")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String userCarLifeBBSList(String query){
        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {
            JsonObject json = new JsonParser().parse(query).getAsJsonObject().get("query").getAsJsonObject();

            String Sql="SELECT\n" +
                    "\tgclb.id AS bbsId,\n" +
                    "\tgclb.type_id,\n" +
                    "\tgcl.type_name,\n" +
                    "\tgclb.title,\n" +
                    "\tgclb.summary,\n" +
                    "\t CONCAT('" + Config.OSS + "',REPLACE(gclb.image,\"type\",\"small\")) AS image,\n" +
                    "\tgclb.click_amount,\n" +
                    "\tgclb.comment_amount,\n" +
                    "\tgclb.thumbs_amount,\n" +
                    "\tgclb.`check`\n" +
                    "FROM\n" +
                    "\tgch_car_life_bbs AS gclb\n" +
                    "LEFT JOIN gch_car_life AS gcl ON gcl.id = gclb.type_id\n" +
                    "WHERE\n" +
                    "\tgclb.user_id = '"+getJsonAsString(json,"userId")+"'\n" +
                    "AND gclb.isdelete = 0";
            List<Mobile_CarLifeBBS> result=create.fetch(Sql).into(Mobile_CarLifeBBS.class);

            return success(result);
        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }

    }
    /**
     * @Description:个人中心--删除车主秀帖子
     * @Params:bbsId;
     * @Return:
     * @Author: 潘玉婷 @panyuting
     * @Date: 2016/11/10 9:48
     * @Version 2.0
     */
    @POST
    @Path("userCarLifeBBSDel")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String userCarLifeBBSDel(String query){
        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {
            JsonObject json = new JsonParser().parse(query).getAsJsonObject().get("query").getAsJsonObject();
         /*   String Sql="delete from gch_car_life_bbs where id='"+getJsonAsString(json,"bbsId")+"'";
            List<Integer> result = create.fetch(Sql).into(int.class);*/
            int result = create.delete(GCH_CAR_LIFE_BBS)
                    .where(GCH_CAR_LIFE_BBS.ID.eq(getJsonAsString(json, "bbsId")))
                    .execute();
            return success(result);
        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }

    }
    /**
     * @Description:车生活详情页
     * @param：query id 该帖子的ID{"query":{"id":"231564safdsfdasdfasdf","user_id":"asfdafdasd254as5d4f56a4"}}
     * @Return:
     * @Author: 孙磊
     * @Date:2016/11/9 9:46
     * @Version 2.0
     */
    @POST
    @Path("carLifeBBSDetail")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String carLifeBBSDetail(String query) {
        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {
            JsonObject json = new JsonParser().parse(query).getAsJsonObject().get("query").getAsJsonObject();;
            String id = getJsonAsString(json, "id");
            String user_id = getJsonAsString(json, "user_id");

            Mobile_CarLifeBBS ret = new Mobile_CarLifeBBS();
            //该文章的点击数自增1
            String sql = "update `gch_car_life_bbs` set `click_amount` = `click_amount`+1 WHERE `id` ='"+id+"'";
            create.execute(sql);
            // 查询出详情的SQL
            sql = "SELECT\n" +
                    "\tbbs.id AS bbsid,\n" +
                    "\tbbs.title,\n" +
                    "\tbbs.contents,\n" +
                    "\tbbs.type_name AS typeName,\n" +
                    "\tgcl.type_name as ftypeName,\n" +
                    "\t CONCAT('" + Config.OSS + "',REPLACE(bbs.image,\"type\",\"small\")) AS image,\n" +
                    " bbs.createtime AS createtime,\n" +
                    " bbs.click_amount AS clickAmount,\n" +
                    " bbs.comment_amount AS commentAmount,\n" +
                    " bbs.thumbs_amount AS thumbsAmount\n" +
                    "FROM\n" +
                    "\tgch_view_car_bbs AS bbs,gch_car_life as gcl\n" +
                    "WHERE\n" +
                    "\tbbs.id = '"+id+"' and\n" +
                    "\tbbs.pid = gcl.id";
            Mobile_CarLifeBBS.BBSDetail details =create.fetchOne(sql).into(Mobile_CarLifeBBS.BBSDetail.class);
            GchCarLifeThumbsRecord find = create.fetchOne(GCH_CAR_LIFE_THUMBS,GCH_CAR_LIFE_THUMBS.BBS_ID.eq(id).and(GCH_CAR_LIFE_THUMBS.USER_ID.eq(user_id)));

            if(find!=null){
                details.isthumbs = 0;
            }else{
                details.isthumbs = 1;
            }

            sql = "SELECT\n" +
                    "\ta.id,\n" +
                    "\ta.`comment`,\n" +
                    "\tleft(a.createtime,16) as createtime,\n" +
                    "\tb.user_name,\n" +
                    "\t CONCAT('" + Config.OSS + "',REPLACE(b.head_url,\"type\",\"small\")) as head_url\n" +
                    "FROM\n" +
                    "\tgch_car_life_comment AS a,\n" +
                    "\tgch_user_general AS b\n" +
                    "WHERE\n" +
                    "\ta.bbs_id = '"+id+"' and a.user_id=b.id order by createtime desc";
            List<Mobile_CarLifeBBS.BBSComments> comments =create.fetch(sql).into(Mobile_CarLifeBBS.BBSComments.class);



            ret.Detail = details;
            ret.Comments = comments;
            return success(ret);
        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }
    }
    /**
        * @Description:车生活帖子评论接口
        * @param：{"query":{"id(帖子ID)":"231564safdsfdasdfasdf","user_id(评论用户ID)":"asdfasdfasdfasdf","comment(评论内容)":"asdfasfdafsd"}}
        * @Return:
        * @Author: 孙磊
        * @Date:2016/11/14 10:14
        * @Version 2.0
        */
    @POST
    @Path("carLifeBBSComments")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String carLifeBBSComments(String query) {
        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {
            JsonObject json = new JsonParser().parse(query).getAsJsonObject().get("query").getAsJsonObject();;
            String id = getJsonAsString(json, "id");
            String user_id = getJsonAsString(json, "user_id");
            String comment = getJsonAsString(json, "comment");

            GchCarLifeBbsRecord find = create.fetchOne(GCH_CAR_LIFE_BBS,GCH_CAR_LIFE_BBS.ID.eq(id));
            GchUserGeneralRecord find_user = create.fetchOne(GCH_USER_GENERAL,GCH_USER_GENERAL.ID.eq(user_id));
            if(find!=null && find_user!=null){
                //评论加入评论表
                String sql = "INSERT INTO `gch_car_life_comment` (`user_id`, `comment`, `bbs_id`) VALUES ('"+user_id+"', '"+comment+"', '"+id+"')";
                int code =  create.execute(sql);
                if( code !=0 ){
                    //调用数据库的afterComments存储函数
                    sql = "call afterComments('"+find.getUserId()+"')";
                    create.execute(sql);
                    return success(0,"评论发布成功！");
                }else{

                    return success(1,"评论发布失败！");
                }
            }else{
                return success(2,"帖子或者用户不存在！");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }
    }

    /**
     * @Description:车生活帖子点赞接口
     * @param：{"query":{"id(帖子ID)":"231564safdsfdasdfasdf","user_id(点赞用户ID)":"asdfasdfasdfasdf"}}
     * @Return:
     * @Author: 孙磊
     * @Date:2016/11/14 10:14
     * @Version 2.0
     */
    @POST
    @Path("carLifeBBSThumbs")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String carLifeBBSThumbs(String query) {
        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {
            JsonObject json = new JsonParser().parse(query).getAsJsonObject().get("query").getAsJsonObject();
            String bbs_id = getJsonAsString(json, "bbs_id");
            String user_id = getJsonAsString(json, "user_id");

            GchCarLifeThumbsRecord find = create.fetchOne(GCH_CAR_LIFE_THUMBS,GCH_CAR_LIFE_THUMBS.BBS_ID.eq(bbs_id).and(GCH_CAR_LIFE_THUMBS.USER_ID.eq(user_id)));
            GchCarLifeBbsRecord bbs = create.fetchOne(GCH_CAR_LIFE_BBS,GCH_CAR_LIFE_BBS.ID.eq(bbs_id));
            if(find!=null && bbs!=null){
                //删除点赞
                    String sql = "DELETE FROM `gch_car_life_thumbs` WHERE id='"+find.getId()+"'";
                    int res = create.execute(sql);
                if(res == 1){
                    return success(res,"取消点赞成功！");
                }else{
                    return error(res, "取消点赞失败");
                }
            }else{
                String sql = "INSERT INTO `gch_car_life_thumbs` (`user_id`, `bbs_id`) VALUES ('"+user_id+"', '"+bbs_id+"')";
                int code = create.execute(sql);
                if( code != 0 ){
                    sql = "call afterThumbs('"+bbs.getUserId()+"')";
                    create.execute(sql);
                    return success(code,"点赞成功！");
                }else{
                    return error(code, "点赞失败");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }
    }
}
