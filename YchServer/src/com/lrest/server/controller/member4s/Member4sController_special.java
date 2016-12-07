package com.lrest.server.controller.member4s;

/**
 * Created by acans on 16/6/21.
 */

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lrest.server.controller.BaseController;
import com.lrest.server.jooqmodel.tables.records.GchSpecialPriceCarRecord;
import com.lrest.server.models.BaseCount;
import com.lrest.server.models.Car_special_price_car;
import com.lrest.server.models.Member4s_speciallist;
import com.lrest.server.services.DB;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static com.lrest.server.jooqmodel.Tables.*;
import static com.lrest.server.utils.UtilBase.getJsonAsInt;
import static com.lrest.server.utils.UtilBase.getJsonAsString;

@Path("/4s/special")
public class Member4sController_special extends BaseController {
    private final org.slf4j.Logger log = LoggerFactory.getLogger(getClass());

//    @Inject
//    private SessionManager sessionManager;


    /**
     * 操作类型: 修改
     * 修改说明: 返回值添加了一个Count(所有特价车的总数)
     * @param query
     * @author 韩武洽
     * @date 2013-09-12 11:18
     * @return
     */
    @POST
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String speciallist(String query) {
        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {

            JsonObject json = new JsonParser().parse(query).getAsJsonObject().get("query").getAsJsonObject();
            JsonArray status = json.get("status").getAsJsonArray();
            List inparams = new ArrayList();
            for (JsonElement x : status) {
                inparams.add(x);
            }
            List<Member4s_speciallist> result_special = create.fetch(
                    resultSpecialSql(
                            getJsonAsInt(json, "pagenum")
                            ,getJsonAsInt(json, "page")
                            ,getJsonAsString(json, "user_id")
                            ,inparams
                            ,getJsonAsString(json, "id")
                     )
            ).into(Member4s_speciallist.class);

            // 查询出所有本店新增活动的车款的内饰 外饰的总数
            int count = create.fetch(
                    resultSpecialSql(0,0
                            ,getJsonAsString(json, "user_id")
                            ,inparams
                            ,getJsonAsString(json, "id")
                    )
            ).size();

            for (Member4s_speciallist list : result_special) {
                Result<Record1<String>> result_area = create.select(GCH_SALES_AREA.SALES_AREA_NAME)
                        .from(GCH_SALES_AREA)
                        .where(GCH_SALES_AREA.ISDELETE.eq(0).and(GCH_SALES_AREA.CAR_SPECIAL_ID.eq(list.id)))
                        .fetch();
                List params = new ArrayList();
                for (Record re : result_area) {
                    params.add(re.get(GCH_SALES_AREA.SALES_AREA_NAME));
                }
                list.sales_area = String.valueOf(params);
            }

            //
            BaseCount<Member4s_speciallist> resultData =  new BaseCount<>();
            resultData.count = count;
            resultData.rows = result_special;

            return success(resultData);

        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }
    }

    @POST
    @Path("delete/{car_special_id}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String specialdelete(@PathParam("car_special_id") String car_special_id) {
        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {
            long seconds = System.currentTimeMillis();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            GchSpecialPriceCarRecord record = create.newRecord(GCH_SPECIAL_PRICE_CAR);
            record.setId(car_special_id);
            record.setIsdelete(1);
            record.setUpdatetime(Timestamp.valueOf(format.format(seconds)));
            int upd = record.update();

            return success(upd);

        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }
    }




    public String resultSpecialSql(Integer pagenum,Integer page,String user_id,List inparams,String id){

        StringBuilder resultSpecialSql = new StringBuilder();
        resultSpecialSql.append(" SELECT \n");
        resultSpecialSql.append(" `gouchehui2.0`.`gch_view_special_price_car`.`id`, \n");
        resultSpecialSql.append(" `gouchehui2.0`.`gch_view_special_price_car`.`brand_name`, \n");
        resultSpecialSql.append(" `gouchehui2.0`.`gch_view_special_price_car`.`car_model_name`, \n");
        resultSpecialSql.append(" `gouchehui2.0`.`gch_view_special_price_car`.`car_name`, \n");
        resultSpecialSql.append(" `gouchehui2.0`.`gch_view_special_price_car`.`exterior_color_id`, \n");
        resultSpecialSql.append(" `gouchehui2.0`.`gch_view_special_price_car`.`exterior_color_name`, \n");
        resultSpecialSql.append(" `gouchehui2.0`.`gch_view_special_price_car`.`interior_color_id`, \n");
        resultSpecialSql.append(" `gouchehui2.0`.`gch_view_special_price_car`.`interior_color_name`, \n");
        resultSpecialSql.append(" `gouchehui2.0`.`gch_view_special_price_car`.`auth_price`, \n");
        resultSpecialSql.append(" `gouchehui2.0`.`gch_view_special_price_car`.`special_price`, \n");
        resultSpecialSql.append(" `gouchehui2.0`.`gch_view_special_price_car`.`start_date`, \n");
        resultSpecialSql.append(" `gouchehui2.0`.`gch_view_special_price_car`.`end_date`, \n");
        resultSpecialSql.append(" `gouchehui2.0`.`gch_view_special_price_car`.`number`, \n");
        resultSpecialSql.append(" `gouchehui2.0`.`gch_view_special_price_car`.`status`, \n");
        resultSpecialSql.append(" `gouchehui2.0`.`gch_view_special_price_car`.`car_image`, \n");
        resultSpecialSql.append(" `gouchehui2.0`.`gch_view_special_price_car`.`car_id`, \n");
        resultSpecialSql.append(" `gouchehui2.0`.`gch_view_special_price_car`.`description` \n\n");

        resultSpecialSql.append(" FROM  `gouchehui2.0`.`gch_view_special_price_car`\n");
        resultSpecialSql.append(" WHERE ( 1 = 1\n");
        resultSpecialSql.append(" AND `gouchehui2.0`.`gch_view_special_price_car`.`isdelete` = 0 \n");

        if (!"null".equals(id) && id != null) {
            resultSpecialSql.append(" AND `gouchehui2.0`.`gch_view_special_price_car`.`id` = '" + id + "' \n");
        } else {
            resultSpecialSql.append(" AND `gouchehui2.0`.`gch_view_special_price_car`.`user_id` = '" + user_id + "' \n");
            resultSpecialSql.append(" AND `gouchehui2.0`.`gch_view_special_price_car`.`status` IN (" + inparams.toString().substring(1,inparams.toString().length()-1) + ") \n");
        }
        resultSpecialSql.append(" ) \n");
        resultSpecialSql.append(" ORDER BY \n");
        resultSpecialSql.append(" `gouchehui2.0`.`gch_view_special_price_car`.`createtime` DESC \n");

        if (!"null".equals(id) && id != null) {

        } else {
            if (0 != pagenum && 0 != page) {
                resultSpecialSql.append("LIMIT " + (page - 1) * pagenum + "," + pagenum);
            }
        }
        return  resultSpecialSql.toString();
    }
}
