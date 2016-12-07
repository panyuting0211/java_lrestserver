package com.lrest.server.controller.Mobile;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lrest.server.controller.BaseController;
import com.lrest.server.jooqmodel.tables.GchAuthCode;
import com.lrest.server.jooqmodel.tables.records.GchAuthCodeRecord;
import com.lrest.server.jooqmodel.tables.records.GchUserGeneralRecord;
import com.lrest.server.jooqmodel.tables.records.V3UserDeviceRecord;
import com.lrest.server.services.DB;
import com.lrest.server.services.SystemManager;
import com.lrest.server.services.messages.JuheManager;
import com.lrest.server.utils.Constants;
import com.lrest.server.utils.DateUtils;
import com.lrest.server.utils.RandomUtils;
import com.lrest.server.utils.UtilBase;
import com.tencent.common.MD5;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;

import static com.lrest.server.jooqmodel.Tables.GCH_AUTH_CODE;
import static com.lrest.server.jooqmodel.Tables.GCH_USER_GENERAL;
import static com.lrest.server.jooqmodel.Tables.V3_USER_DEVICE;
import static com.lrest.server.utils.UtilBase.genUUID;
import static com.lrest.server.utils.UtilBase.getJsonAsInt;
import static com.lrest.server.utils.UtilBase.getJsonAsString;

/**
 * DESCRIPTION: 授权的验证码
 * @Author 韩武洽
 * @Date 2016-10
 * @Time 26 15:07
 **/
 @Path("/authCode")
public class AuthCodeController extends BaseController {

    /** 验证码类别: 注册验证码 */
    public static final Integer  AUTHCODE_TYPE_SENTSIGNINNO = 1;
    /** 验证码类别: 修改密码验证码 */
    public static final Integer  AUTHCODE_TYPE_CHANGEPASSWORD = 2;
    /** 验证码类别: 免费查询底价车 */
    public static final Integer  AUTHCODE_TYPE_FREESELECTCARPRICE = 3;


    /**


     * @param query  type的类别是:1:注册验证码, 2:修改密码验证码 3:免费查询底价车
                     {"cellphone":"18751852690","type":"1"}
     * @return    发送授权的验证码
     * @Author: 韩武洽 @Wyshown
     * @Date: 2016/10/27 10:00
     * @Version 2.0
     */
    @POST
    @Path("sentSignInNo")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String mobileLoginByWeChat1(String query) {
        String ua = req.getHeader("User-Agent");
        System.out.println(ua);

        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {
            JsonObject json = new JsonParser().parse(query).getAsJsonObject();
            String cellphone = getJsonAsString(json, "cellphone");
            Integer type = getJsonAsInt(json, "type");

            // 删除验证码
            StringBuilder deleteAuthCodeByCallAndType = new StringBuilder();
            deleteAuthCodeByCallAndType.append(" DELETE FROM gch_auth_code \n");
            deleteAuthCodeByCallAndType.append(" WHERE type ='" + type + "' \n");
            deleteAuthCodeByCallAndType.append(" AND cellphone = '" + cellphone + "'\n");
            create.execute(deleteAuthCodeByCallAndType.toString());

            // 产生6位的随机数作为验证码
            String signInNo = RandomUtils.randomInt(6);

            // 添加授权代码
            GchAuthCodeRecord authCode = create.newRecord(GCH_AUTH_CODE);
            authCode.setId(UtilBase.genUUID());
            authCode.setSignInNo(signInNo);
            authCode.setCellphone(cellphone);
            authCode.setType(type);
            authCode.setCreatetime(DateUtils.millisecondChangeTimestamp());
            // 超时时间为5分钟
            authCode.setExpirationTime(
                    DateUtils.millisecondChangeTimestamp(
                            System.currentTimeMillis() + Constants.LONG_FIVE_MINUTE_MILLIS));
            authCode.insert();

            JuheManager.sendSignInMassage(cellphone,signInNo);
            return success(1,"验证码已发送.");
        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }
    }

    /**
     * @param query type的类别是:1:注册验证码, 2:修改密码验证码 3:免费查询底价车
                   {"cellphone":"18751852690","signInNo":"8650141","type":"1"}
     * @Return: 校验发送的授权的验证码是否正确
     * @Author: 韩武洽 @Wyshown
     * @Date: 2016/10/26 17:14
     * @Version 2.0
     */
    @POST
    @Path("checkSignInNoIsTrue")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String checkSignInNoIsTrue(String query) {
        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {
            JsonObject json = new JsonParser().parse(query).getAsJsonObject();
            String cellphone = getJsonAsString(json, "cellphone");
            String signInNo = getJsonAsString(json, "signInNo");
            Integer type = getJsonAsInt(json, "type");

            // 查询 手机对应的注册验证码存在
            Record r = checkSignInNoIsValid(
                    create,cellphone,signInNo,type);
            if (r != null) {
                return success();
            } else {
                return  error(-1,"验证码不正确");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }
    }

    /**
    * @param create DSLContext
    * @param cellphone 手机号
    * @param signInNo 验证码
    * @param type 验证码类别
    * @Return: 返回查询出的验证码信息,如无,则返回Null
    * @Author: 韩武洽 @Wyshown
    * @Date: 2016/10/27 9:46
    * @Version 2.0
    */
    public Record checkSignInNoIsValid(DSLContext create,String cellphone,String signInNo,Integer type) {
        StringBuffer authCodeSql = new StringBuffer();
        authCodeSql.append(" SELECT id,sign_in_no AS name \n");
        authCodeSql.append(" FROM gch_auth_code \n");
        authCodeSql.append(" WHERE cellphone = '" + cellphone + "' \n");
        authCodeSql.append(" AND sign_in_no = '" + signInNo + "' \n");
        authCodeSql.append(" AND type = '" + type + "' \n");
        authCodeSql.append(" AND expiration_time >= '" + DateUtils.millisecondChangeTimestamp() + "' \n");
        return create.fetchOne(authCodeSql.toString());
    }
}
