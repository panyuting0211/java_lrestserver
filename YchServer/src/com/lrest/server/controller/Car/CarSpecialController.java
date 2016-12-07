package com.lrest.server.controller.Car;

/**
 * Created by acans on 16/6/21.
 */

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lrest.server.controller.BaseController;

import com.lrest.server.jooqmodel.tables.records.GchUserActivityRecord;
import com.lrest.server.models.*;
import com.lrest.server.services.DB;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.*;
import java.util.stream.Collectors;

import static com.lrest.server.jooqmodel.Tables.GCH_CAR_ACTIVITIES;
import static com.lrest.server.jooqmodel.Tables.GCH_USER_ACTIVITY;
import static com.lrest.server.utils.UtilBase.*;
import static java.util.stream.Collectors.*;
import static org.jooq.impl.DSL.count;
import static org.jooq.impl.DSL.sum;

@Path("/nl/car")
public class CarSpecialController extends BaseController {
    private final org.slf4j.Logger log = LoggerFactory.getLogger(getClass());

    @GET
    public String get() {
        log.debug("/nl/car");
        return "/nl/car";
    }

    @POST
    @Path("carlist/{car_id}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String carlist(@PathParam("car_id") String car_id) {

        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {


            Car_details m_car = new Car_details();
            //获得车款信息
            String sql = "select distinct id,car_id,car_name,brand_id,brand_name,car_model_id,car_model_name,car_model_imageurl from gch_view_car_price  where car_id='" + car_id + "' and isdelete=0 group by car_id";
            List<Car_details.car_details> rets = create.fetch(sql).into(Car_details.car_details.class);
            m_car.car_details = rets;

            //获得车款报价
            sql = "select distinct id,car_id,exterior_color_id,interior_color_id,auth_price,low_price,price,discount,is_xunjia from gch_view_car_price  where car_id='" + car_id + "' and isdelete=0";
            Result<Record> car_prices_res = create.fetch(sql);
            m_car.car_prices = car_prices_res.
                    stream().
                    collect(
                            Collectors.toMap
                                    (k -> k.getValue("car_id", String.class) + "," + k.getValue("exterior_color_id", String.class) + "," + k.getValue("interior_color_id", String.class),
                                            v -> new Car_details.car_price(
                                                    v.getValue("car_id", String.class),
                                                    v.getValue("exterior_color_id", String.class),
                                                    v.getValue("interior_color_id", String.class),
                                                    v.getValue("auth_price", String.class),
                                                    v.getValue("low_price", String.class),
                                                    v.getValue("price", String.class),
                                                    v.getValue("discount", String.class),
                                                    v.getValue("is_xunjia", String.class),
                                                    v.getValue("id", String.class)
                                            ),
                                            (v1, v2) -> {

                                                //log.info(v1.toString()+"  "+v2.toString());
                                                if (Integer.parseInt(v1.low_price) > Integer.parseInt(v2.low_price)) {
                                                    return v2;
                                                } else {
                                                    return v1;
                                                }


                                            }
                                    )
                    );

            sql = "select distinct a.type,a.car_id,a.imgurl from gch_car_color_image a,gch_view_car_price  b where a.car_id=b.car_id and a.isdelete=0 and b.car_id='" + car_id + "'";
            Result<Record> result_img = create.fetch(sql);

            Map<String, List<Car_details.img>> map_ret_img = result_img.
                    stream().
                    collect(
                            groupingBy(
                                    r -> r.getValue("type", String.class) + "," + r.getValue("car_id", String.class),
                                    LinkedHashMap::new,
                                    mapping(
                                            r -> new Car_details.img(
                                                    r.getValue("imgurl", String.class)
                                            ),
                                            toList()
                                    )
                            )
                    );

            m_car.car_imgs = map_ret_img;

            //获得内饰
            sql = "select distinct interior_color_id,interior_color_name,interior_color_value,car_id,car_name from gch_view_car_price where car_id='" + car_id + "' and isdelete=0";
            Result<Record> result = create.fetch(sql);

            Map<String, List<Car_details.car_in_color>> map_ret = result.
                    stream().
                    collect(
                            groupingBy(
                                    r -> r.getValue("car_id", String.class),
                                    LinkedHashMap::new,
                                    mapping(
                                            r -> new Car_details.car_in_color(
                                                    r.getValue("interior_color_id", String.class),
                                                    r.getValue("interior_color_name", String.class),
                                                    r.getValue("interior_color_value", String.class)
                                            ),
                                            toList()
                                    )
                            )
                    );

            m_car.car_in_color = map_ret;
            //获得外饰
            sql = "select distinct exterior_color_id,exterior_color_name,exterior_color_value,car_id,car_name from gch_view_car_price where car_id='" + car_id + "' and isdelete=0";
            result = create.fetch(sql);

            m_car.car_ex_color = result.
                    stream().
                    collect(
                            groupingBy(
                                    r -> r.getValue("car_id", String.class),
                                    LinkedHashMap::new,
                                    mapping(
                                            r -> new Car_details.car_ex_color(
                                                    r.getValue("exterior_color_id", String.class),
                                                    r.getValue("exterior_color_name", String.class),
                                                    r.getValue("exterior_color_value", String.class)
                                            ),
                                            toList()
                                    )
                            )
                    );

            return success(m_car);

        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }


    }

    @POST
    @Path("specialcar/{model_id}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String specialcar(@PathParam("model_id") String model) {

        log.debug("model=" + model);
        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {


            Car_specialcar m_specialcar = new Car_specialcar();

            String sql = "select distinct car_id,car_name,brand_id,brand_name,car_model_id,car_model_name,car_model_imageurl from gch_view_car_price  where car_model_id='" + model + "' and isdelete=0 and is_xunjia=2 and car_status=1";
            List<Car_specialcar.car_type> rets = create.fetch(sql).into(Car_specialcar.car_type.class);

            m_specialcar.car_types = rets;


            sql = "select distinct interior_color_id,interior_color_name,interior_color_value,car_id,car_name from gch_view_car_price where car_model_id='" + model + "' and isdelete=0 and is_xunjia=2 and car_status=1";
            Result<Record> result = create.fetch(sql);

            Map<String, List<Car_specialcar.car_type_in_color>> map_ret = result.
                    stream().
                    collect(
                            groupingBy(
                                    r -> r.getValue("car_id", String.class),
                                    LinkedHashMap::new,
                                    mapping(
                                            r -> new Car_specialcar.car_type_in_color(
                                                    r.getValue("interior_color_id", String.class),
                                                    r.getValue("interior_color_name", String.class),
                                                    r.getValue("interior_color_value", String.class)
                                            ),
                                            toList()
                                    )
                            )
                    );

            m_specialcar.car_type_in_colors = map_ret;


            sql = "select distinct exterior_color_id,exterior_color_name,exterior_color_value,car_id,car_name from gch_view_car_price where car_model_id='" + model + "' and isdelete=0 and is_xunjia=2 and car_status=1";
            result = create.fetch(sql);

            m_specialcar.car_type_ex_colors = result.
                    stream().
                    collect(
                            groupingBy(
                                    r -> r.getValue("car_id", String.class),
                                    LinkedHashMap::new,
                                    mapping(
                                            r -> new Car_specialcar.car_type_ex_color(
                                                    r.getValue("exterior_color_id", String.class),
                                                    r.getValue("exterior_color_name", String.class),
                                                    r.getValue("exterior_color_value", String.class)
                                            ),
                                            toList()
                                    )
                            )
                    );


            sql = "select car_id,car_name,interior_color_id,exterior_color_id,auth_price,low_price,discount from gch_view_car_price where car_model_id='" + model + "' and isdelete=0 and is_xunjia=2 and car_status=1";
            result = create.fetch(sql);

            m_specialcar.car_prices = result.
                    stream().
                    collect(
                            Collectors.toMap
                                    (k -> k.getValue("car_id", String.class) + "," + k.getValue("exterior_color_id", String.class) + "," + k.getValue("interior_color_id", String.class),
                                            v -> new Car_specialcar.car_price(
                                                    v.getValue("car_id", String.class),
                                                    v.getValue("exterior_color_id", String.class),
                                                    v.getValue("interior_color_id", String.class),
                                                    v.getValue("auth_price", String.class),
                                                    v.getValue("low_price", String.class),
                                                    v.getValue("discount", String.class)
                                            ),
                                            (v1, v2) -> {

                                                //log.info(v1.toString()+"  "+v2.toString());
                                                if (Integer.parseInt(v1.low_price) > Integer.parseInt(v2.low_price)) {
                                                    return v2;
                                                } else {
                                                    return v1;
                                                }


                                            }
                                    )
                    );


            return success(m_specialcar);

        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }


    }

    @POST
    @Path("brand/{action}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String brandlist(@PathParam("action") String action) {

        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {
            String sql = "";
            if (action.equals("list")) {
                sql = "SELECT id,brand_name name FROM gch_brand WHERE isdelete=0 order by alif";
            } else if (action.equals("quotes")) {
                sql = "SELECT brand_id as id,brand_name as name FROM gch_view_car_price WHERE isdelete=0  group by brand_id order by brand_alif";
            }
            List<Base_Idname> rets = create.fetch(sql).into(Base_Idname.class);
            return success(rets);
        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }


    }

    @POST
    @Path("brand/list/letter")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String brandlistletter() {

        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {


            //Base_Idname bandlist=new Base_Idname();

            String sql = "SELECT brand_id id,brand_name name,logo,brand_alif alif FROM gch_view_car_price WHERE isdelete=0  AND brand_alif IS NOT NULL group by brand_id order by brand_alif";


            Result<Record> result = create.fetch(sql);

            Map<String, List<String[]>> rets = result.
                    stream().
                    collect(
                            groupingBy(
                                    r -> r.getValue("alif", String.class).toUpperCase(),
                                    LinkedHashMap::new,
                                    mapping(
                                            r -> {
                                                String[] s = new String[3];
                                                s[0] = r.getValue("id", String.class);
                                                s[1] = r.getValue("name", String.class);
                                                s[2] = r.getValue("logo", String.class);
                                                return s;
                                            },
                                            toList()
                                    )
                            )
                    );


            return success(rets);

        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }
    }

    @POST
    @Path("carmodel/{action}/{brandid}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String carmodel_list(@PathParam("action") String action, @PathParam("brandid") String brandid) {

        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {
            String sql = "";

            if (action.equals("list")) {
//select distinct car_model_id,car_model_name,car_model_imageurl from gch_view_car_price where brand_id=61 and isdelete=0 order by car_model_name
                sql = "SELECT id,car_model_name name,imgurl FROM gch_car_model WHERE isdelete=0 AND brand_id=\"" + brandid + "\"";

            } else if (action.equals("listhasprice")) {
                sql = "select  id ,name ,imgurl"
                        + ",(select max(auth_price) from gch_view_car_price b where a.id=b.car_model_id and b.isdelete=0) maxprice"
                        + ",(select min(auth_price) from gch_view_car_price b where a.id=b.car_model_id and b.isdelete=0) minprice"
                        + " from (select distinct car_model_id id,car_model_name name,car_model_imageurl imgurl from gch_view_car_price where brand_id='" + brandid + "' and isdelete=0 order by car_model_name) a";

            } else {
                return error(-2, "action error");
            }

            List<Car_model> rets = create.fetch(sql).into(Car_model.class);
            return success(rets);

        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }


    }

    @POST
    @Path("cartype/{action}/{modelid}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String cartype_list(@PathParam("action") String action, @PathParam("modelid") String modelid) {

        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {
            String sql = "";

            if (action.equals("list")) {
                sql = "SELECT id,car_name name,car_model_imageurl as img  FROM gch_view_car WHERE isdelete=0 AND car_model_id=\"" + modelid + "\" order by car_name desc";
            } else if (action.equals("listhasprice")) {
                sql = "SELECT distinct(car_id) id,car_name name,car_model_imageurl as img FROM gch_view_car_price WHERE isdelete=0 AND car_model_id='" + modelid + "' order by car_name desc";
            } else {
                return error(-2, "action error");
            }

            List<Car_type> rets = create.fetch(sql).into(Car_type.class);

            return success(rets);

        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }


    }

    @POST
    @Path("/user_activity/add")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String gch_user_activity_add(String query) {

        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {


            log.info(String.format("query = {} {}"), query, UUID.randomUUID());

            JsonObject json = new JsonParser().parse(query).getAsJsonObject().get("query").getAsJsonObject();


            String sql = "select count(*) from gch_user_activity where activity_number='"
                    + getJsonAsString(json, "activity_number") + "'"
                    + " and user_name='" + getJsonAsString(json, "user_name") + "'"
                    + " and tel='" + getJsonAsString(json, "tel") + "'"
                    + " and car_id='" + getJsonAsString(json, "car_id") + "'"
                    + " and isdelete=0";
            int count = create.fetchOne(sql).into(int.class);

            if (count > 0) {

                return error(-1, "data  exists");
            }

            GchUserActivityRecord record = create.newRecord(GCH_USER_ACTIVITY);

            record.setId(genUUID());
            record.setUserName(getJsonAsString(json, "user_name"));
            record.setSource(getJsonAsInt(json, "source"));
            record.setFlag(getJsonAsInt(json, "flag"));
            record.setTel(getJsonAsString(json, "tel"));
            record.setActivityPrice(getJsonAsString(json, "activity_price"));
            record.setActivityNumber(getJsonAsString(json, "activity_number"));
            record.setActivityName(getJsonAsString(json, "activity_name"));
            record.setBrandId(getJsonAsString(json, "brand_id"));
            record.setBrandName(getJsonAsString(json, "brand_name"));
            record.setCarId(getJsonAsString(json, "car_id"));
            record.setCarName(getJsonAsString(json, "car_name"));
            record.setCarModelId(getJsonAsString(json, "car_model_id"));
            record.setCarModelName(getJsonAsString(json, "car_model_name"));
            record.setExteriorColorId(getJsonAsString(json, "exterior_color_id"));
            record.setExteriorColorName(getJsonAsString(json, "exterior_color_name"));
            record.setInteriorColorId(getJsonAsString(json, "interior_color_id"));
            record.setInteriorColorName(getJsonAsString(json, "interior_color_name"));
            record.setCreateuser(getJsonAsString(json, "createuser"));
            record.setRemarks(getJsonAsString(json, "remarks"));
            record.setRewardId(getJsonAsString(json, "reward_id"));
            record.setRewardType(getJsonAsString(json, "reward_type"));


            // create.attach(record);

            record.store();


            return success(record.getId());

        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }


    }

    @POST
    @Path("/user_activity/isfisrt")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String gch_user_activity_isfisrt(String query) {

        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {


            log.info(String.format("query = {} {}"), query, UUID.randomUUID());

            JsonObject json = new JsonParser().parse(query).getAsJsonObject().get("query").getAsJsonObject();


            String sql = "select count(*) from gch_user_activity where activity_number='"
                    + getJsonAsString(json, "activity_number") + "'"
                    + " and flag='" + getJsonAsString(json, "flag") + "'"
                    + " and tel='" + getJsonAsString(json, "tel") + "'"
                    + " and isdelete=0";
            int count = create.fetchOne(sql).into(int.class);

            if (count > 0) {

                return error(0, "已经参加过！");
            } else {
                return success(1, "还未参加！");
            }


        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }


    }

    @POST
    @Path("user_activity/count/{activitynumber}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String user_activity_count(@PathParam("activitynumber") String activiynumber) {

        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {

            String sql = "select count(*) from gch_user_activity where activity_number='" + activiynumber + "' and isdelete=0  order by createtime desc limit 15";
            int count = create.fetchOne(sql).into(int.class);

            sql = "select CONCAT(car_model_name,'',car_name) id,CONCAT(left(tel,5),'****',right(tel,2)) name from gch_user_activity where activity_number='" + activiynumber + "' and isdelete=0  order by createtime desc limit 15";
            List<Base_Idname> rets = create.fetch(sql).into(Base_Idname.class);


            Base_Count base_count = new Base_Count();
            base_count.count = count;
            base_count.rows = rets;


            return success(base_count);

        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }


    }

    @POST
    @Path("carDetails/{model_id}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String carDetails(@PathParam("model_id") String model) {

        log.debug("model=" + model);
        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {


            Car_details m_car = new Car_details();
            //获得车款信息
            String sql = "select distinct id,car_id,car_name,brand_id,brand_name,car_model_id,car_model_name,car_model_imageurl from gch_view_car_price  where car_model_id='" + model + "' and isdelete=0 group by car_id";
            List<Car_details.car_details> rets = create.fetch(sql).into(Car_details.car_details.class);
            m_car.car_details = rets;

            //获得车款报价
            sql = "select distinct id,car_id,exterior_color_id,interior_color_id,auth_price,low_price,price,discount,is_xunjia from gch_view_car_price  where car_model_id='" + model + "' and isdelete=0";
            Result<Record> car_prices_res = create.fetch(sql);
            m_car.car_prices = car_prices_res.
                    stream().
                    collect(
                            Collectors.toMap
                                    (k -> k.getValue("car_id", String.class) + "," + k.getValue("exterior_color_id", String.class) + "," + k.getValue("interior_color_id", String.class),
                                            v -> new Car_details.car_price(
                                                    v.getValue("car_id", String.class),
                                                    v.getValue("exterior_color_id", String.class),
                                                    v.getValue("interior_color_id", String.class),
                                                    v.getValue("auth_price", String.class),
                                                    v.getValue("low_price", String.class),
                                                    v.getValue("price", String.class),
                                                    v.getValue("discount", String.class),
                                                    v.getValue("is_xunjia", String.class),
                                                    v.getValue("id", String.class)
                                            ),
                                            (v1, v2) -> {

                                                //log.info(v1.toString()+"  "+v2.toString());
                                                if (Integer.parseInt(v1.low_price) > Integer.parseInt(v2.low_price)) {
                                                    return v2;
                                                } else {
                                                    return v1;
                                                }


                                            }
                                    )
                    );

            sql = "select distinct a.type,a.car_id,a.imgurl from gch_car_color_image a,gch_view_car_price  b where a.car_id=b.car_id and a.isdelete=0 and b.car_model_id='" + model + "'";
            Result<Record> result_img = create.fetch(sql);

            Map<String, List<Car_details.img>> map_ret_img = result_img.
                    stream().
                    collect(
                            groupingBy(
                                    r -> r.getValue("type", String.class) + "," + r.getValue("car_id", String.class),
                                    LinkedHashMap::new,
                                    mapping(
                                            r -> new Car_details.img(
                                                    r.getValue("imgurl", String.class)
                                            ),
                                            toList()
                                    )
                            )
                    );

            m_car.car_imgs = map_ret_img;

            //获得内饰
            sql = "select distinct interior_color_id,interior_color_name,interior_color_value,car_id,car_name from gch_view_car_price where car_model_id='" + model + "' and isdelete=0";
            Result<Record> result = create.fetch(sql);

            Map<String, List<Car_details.car_in_color>> map_ret = result.
                    stream().
                    collect(
                            groupingBy(
                                    r -> r.getValue("car_id", String.class),
                                    LinkedHashMap::new,
                                    mapping(
                                            r -> new Car_details.car_in_color(
                                                    r.getValue("interior_color_id", String.class),
                                                    r.getValue("interior_color_name", String.class),
                                                    r.getValue("interior_color_value", String.class)
                                            ),
                                            toList()
                                    )
                            )
                    );

            m_car.car_in_color = map_ret;
            //获得外饰
            sql = "select distinct exterior_color_id,exterior_color_name,exterior_color_value,car_id,car_name from gch_view_car_price where car_model_id='" + model + "' and isdelete=0";
            result = create.fetch(sql);

            m_car.car_ex_color = result.
                    stream().
                    collect(
                            groupingBy(
                                    r -> r.getValue("car_id", String.class),
                                    LinkedHashMap::new,
                                    mapping(
                                            r -> new Car_details.car_ex_color(
                                                    r.getValue("exterior_color_id", String.class),
                                                    r.getValue("exterior_color_name", String.class),
                                                    r.getValue("exterior_color_value", String.class)
                                            ),
                                            toList()
                                    )
                            )
                    );

            return success(m_car);

        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }


    }
    /**
        * @Description:map：PC端，list的手机站
        * @Return:
        * @Author: 孙磊
        * @Date:2016/10/26 9:44
        * @Version 2.0
        */
    @POST
    @Path("activitylist/{action}/{activity_id}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String activitylist(@PathParam("action") String action, @PathParam("activity_id") int activity_id) {
        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {

            if (action.equals("map")) {
                Car_activity list = new Car_activity();

                String sql = "select distinct a.group_id,b.brand_id, b.brand_name,b.car_model_id,b.car_model_name,b.car_id,b.car_name,b.exterior_color_id,b.interior_color_id,b.discount,b.auth_price price,b.low_price,a.buyer_count buyer_count,a.type type,b.exterior_color_name,b.interior_color_name,a.sort sort,a.status status,a.id id from gch_car_activities a,gch_view_car_price b where a.isdelete=0 and a.car_price_id = b.id and a.activity_id='" + activity_id + "' order by a.sort asc";

                Result<Record> result = create.fetch(sql);
                Map<String, List<Car_activity.activitylist>> activitylist = result.
                        stream().
                        collect(
                                groupingBy(
                                        r -> r.getValue("group_id", String.class),
                                        LinkedHashMap::new,
                                        mapping(
                                                (r) -> {
                                                    Car_activity.activitylist m = new Car_activity.activitylist();
                                                    m.bid = r.getValue("brand_id", String.class);
                                                    m.brand_name = r.getValue("brand_name", String.class);
                                                    m.car_model_id = r.getValue("car_model_id", String.class);
                                                    m.car_model_name = r.getValue("car_model_name", String.class);
                                                    m.car_name = r.getValue("car_name", String.class);
                                                    m.discount = r.getValue("discount", String.class);
                                                    m.price = r.getValue("price", String.class);
                                                    m.low_price = r.getValue("low_price", String.class);
                                                    m.car_id = r.getValue("car_id", String.class);
                                                    m.exterior_color_id = r.getValue("exterior_color_id", String.class);
                                                    m.interior_color_id = r.getValue("interior_color_id", String.class);
                                                    m.buyer_count = r.getValue("buyer_count", int.class);
                                                    m.type = r.getValue("type", int.class);
                                                    m.exterior_color_name = r.getValue("exterior_color_name", String.class);
                                                    m.interior_color_name = r.getValue("interior_color_name", String.class);
                                                    m.sort = r.getValue("sort", int.class);
                                                    m.status = r.getValue("status", int.class);
                                                    m.activity_car_id = r.getValue("id", int.class);
                                                    return m;
                                                },
//                                                r -> new Car_activity.activitylist(
//                                                        r.getValue("brand_name",String.class),
//                                                        r.getValue("car_model_id",String.class),
//                                                        r.getValue("car_model_name",String.class),
//                                                        r.getValue("car_name",String.class),
//                                                        r.getValue("discount",String.class),
//                                                        r.getValue("price",String.class),
//                                                        r.getValue("low_price",String.class),
//                                                        r.getValue("car_id",String.class),
//                                                        r.getValue("exterior_color_id",String.class),
//                                                        r.getValue("interior_color_id",String.class),
//                                                        r.getValue("brand_id",String.class)
//                                                ),
                                                toList()
                                        )
                                )
                        );
                list.activitylist = activitylist;

                sql = "select id,activity_name,activity_number,DATE_FORMAT(from_unixtime(starttime),\"%Y-%m-%d\" ) starttime,DATE_FORMAT(from_unixtime(endtime),\"%Y-%m-%d\" ) endtime  from gch_activities where id=" + activity_id;
                list.activityinfo = create.fetchOne(sql).into(Car_activity.Activityinfo.class);
                return success(list);
            } else if (action.equals("list")) {


                Car_activity car_activity = new Car_activity();
                String sql = "select distinct b.brand_name brand_name,b.car_model_id car_model_id,b.car_model_name car_model_name"
                        + ",b.car_name car_name,b.discount discount,b.auth_price price,b.low_price low_price ,b.car_id car_id,b.exterior_color_id exterior_color_id"
                        + ",b.interior_color_id interior_color_id,b.brand_id bid,a.buyer_count buyer_count,a.type type,b.exterior_color_name,b.interior_color_name,a.sort sort"
                        + ",a.status status,a.id activity_car_id  from gch_car_activities a,gch_view_car_price b where a.isdelete=0 and a.car_price_id = b.id and a.activity_id='" + activity_id + "'  order by a.sort,a.id";
                car_activity.list = create.fetch(sql).into(Car_activity.activitylist.class);

                sql = "select id,activity_name,activity_number,DATE_FORMAT(from_unixtime(starttime),\"%Y-%m-%d\" ) starttime,DATE_FORMAT(from_unixtime(endtime),\"%Y-%m-%d\" ) endtime  from gch_activities where id=" + activity_id;
                car_activity.activityinfo = create.fetchOne(sql).into(Car_activity.Activityinfo.class);


                return success(car_activity);
            } else {
                return error(-2, "action error");
            }


        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }
    }

    /**
        * @Description:获取活动车款的参加人数
        * @Return:
        * @Author: 潘玉婷 @panyuting
        * @Date: 2016/9/21 15:09
        * @Version 2.0
        */
    @POST
    @Path("activitycount/{activity_number}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String activity_count(@PathParam("activity_number") String activity_number) {
        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {
            List<activity_count> result_car = create.select(GCH_CAR_ACTIVITIES.CAR_ID, sum(GCH_CAR_ACTIVITIES.BUYER_COUNT))
                    .from(GCH_CAR_ACTIVITIES)
                    .where(GCH_CAR_ACTIVITIES.ISDELETE.eq(0).and(GCH_CAR_ACTIVITIES.ACTIVITY_NUMBER.eq(activity_number)))
                    .groupBy(GCH_CAR_ACTIVITIES.CAR_ID)
                    .orderBy(GCH_CAR_ACTIVITIES.SORT)
                    .fetch()
                    .into(activity_count.class);
            Result<Record2<String, Integer>> result_user = create.select(GCH_USER_ACTIVITY.CAR_ID, count())
                    .from(GCH_USER_ACTIVITY)
                    .where(GCH_USER_ACTIVITY.ISDELETE.eq(0).and(GCH_USER_ACTIVITY.ACTIVITY_NUMBER.eq(activity_number)))
                    .groupBy(GCH_USER_ACTIVITY.CAR_ID)
                    .fetch();

            for (activity_count list : result_car) {
                for (Record re : result_user) {
                    if (list.car_id.equals(re.get(0))) {
                        BigDecimal temp = new BigDecimal((Integer) re.get(1));
                        list.sum = list.sum.add(temp);
                    }
                }

            }

            return success(result_car);

        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }


    }


}
