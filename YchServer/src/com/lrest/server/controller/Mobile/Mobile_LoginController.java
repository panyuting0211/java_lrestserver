package com.lrest.server.controller.Mobile;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lrest.server.controller.BaseController;
import com.lrest.server.jooqmodel.tables.records.GchUserGeneralRecord;
import com.lrest.server.jooqmodel.tables.records.V3UserDeviceRecord;
import com.lrest.server.models.Base_Idname;
import com.lrest.server.models.Mobile.Mobile_UserGeneral;
import com.lrest.server.services.Config;
import com.lrest.server.services.DB;
import com.lrest.server.services.session.SessionManager;
import com.lrest.server.utils.Constants;
import com.lrest.server.utils.DateUtils;
import com.lrest.server.utils.UtilBase;
import com.tencent.common.MD5;
import eu.bitwalker.useragentutils.UserAgent;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.sql.Timestamp;

import static com.lrest.server.jooqmodel.Tables.GCH_USER_GENERAL;
import static com.lrest.server.jooqmodel.Tables.V3_USER_DEVICE;
import static com.lrest.server.utils.UtilBase.genUUID;
import static com.lrest.server.utils.UtilBase.getJsonAsString;

/**
 * DESCRIPTION: 手机端登录
 *
 * @Author 韩武洽
 * @Date 2016-10
 * @Time 24 15:30
 **/
@Path("nl/mobile/login")
public class Mobile_LoginController extends BaseController {
    private final org.slf4j.Logger log = LoggerFactory.getLogger(getClass());


    /**
     * @param query  格式如下:
                  {"username":"18751852690","password":"gch88888888"}
    * @Description: 通过帐号名和密码或者token登录平台
    * @Return:
    * @Author: 韩武洽 @Wyshown
    * @Date: 2016/10/25 11:05
    * @Version 2.0
    */
    @POST
    @Path("auth/mobileLogin")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String mobileLogin(String query) {
        String ua = req.getHeader("User-Agent");
        System.out.println(ua);

        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {
            JsonObject json = new JsonParser().parse(query).getAsJsonObject();
            String passwd = getJsonAsString(json, "password");
            String userName = getJsonAsString(json, "username");
            String token = getJsonAsString(json, "token");
            String platform = getJsonAsString(json, "platform");

            StringBuffer sql = new StringBuffer();
            if (passwd != null) {
                String md5Passwd = MD5.MD5Encode(passwd);

                sql.append(" SELECT \n");
                sql.append(" general.id, \n");
                sql.append(" general.user_name AS userName, \n");
                sql.append(" general.role, \n");
                sql.append(" general.tel, \n");
                sql.append(" general.email, \n");
                sql.append(" general.head_url AS headUrl, \n");
                sql.append(" general.NAME, \n");
                sql.append(" general.nick, \n");
                sql.append(" general.sex, \n");
                sql.append(" general.birthday, \n");
                sql.append(" general.province_id AS provinceId, \n");
                sql.append(" general.city_id AS cityId, \n");
                sql.append(" general.district_id AS districtId, \n");
                sql.append(" general.address, \n");
                sql.append(" general.postcode, \n");
                sql.append(" general.remark \n");

                sql.append(" FROM gch_user_general AS general \n");
                sql.append(" WHERE general.user_name = '" + userName + "' \n");
                sql.append(" AND general.`password` = '" + md5Passwd + "' \n");
                sql.append(" AND general.isdelete = 0 \n");

                Mobile_UserGeneral general = null;
                Record r = create.fetchOne(sql.toString());

                if (r != null) {
                    general = r.into(Mobile_UserGeneral.class);
                    if (general == null || general.headUrl == "" || general.headUrl == null) {
                        general.headUrl = "";
                    } else {
                        general.headUrl = Config.OSS + general.headUrl.replace("type", "small");
                    }
                    general.session = getSession(userName, ua, platform);
                } else {
                    return error(-1, "4S 用户不存在或者密码错误。");
                }
                return success(general);
            } else if (token != null) {
                return error(-2, "暂未开发token登录!");

            } else {
                return error(-2, "require password or token");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }
    }


    /**
    * @Description:
    * @Return: 免登录的接口
    * @Author: 韩武洽 @Wyshown
    * @Date: 2016/10/25 11:18
    * @Version 2.0
    */
    @POST
    @Path("auth/mobileVoidLogin")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String mobileVoidLogin() {
        try {
            Mobile_UserGeneral general = Constants.getConstantsGeneral();
            return  success(general);
        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }
    }

    /**
     * * @param query  \
           传参方式如下: {"openId":"187518526923"}
        * @Return: 微信OPENID登录
        * @Author: 韩武洽 @Wyshown
        * @Date: 2016/10/25 14:24
        * @Version 2.0
        */
    @POST
    @Path("auth/mobileLoginByWeChat")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String mobileLoginByWeChat(String query) {
        String ua = req.getHeader("User-Agent");
        System.out.println(ua);

        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {
            JsonObject json = new JsonParser().parse(query).getAsJsonObject();
            String openId = getJsonAsString(json, "openId");
            String platform = getJsonAsString(json, "platform");

            StringBuffer sql = new StringBuffer();
            if (openId != null) {
                sql.append(" SELECT \n");
                sql.append(" general.id, \n");
                sql.append(" general.user_name AS userName, \n");
                sql.append(" general.role, \n");
                sql.append(" general.tel, \n");
                sql.append(" general.email, \n");
                sql.append(" general.head_url AS headUrl, \n");
                sql.append(" general.NAME, \n");
                sql.append(" general.nick, \n");
                sql.append(" general.sex, \n");
                sql.append(" general.birthday, \n");
                sql.append(" general.province_id AS provinceId, \n");
                sql.append(" general.city_id AS cityId, \n");
                sql.append(" general.district_id AS districtId, \n");
                sql.append(" general.address, \n");
                sql.append(" general.postcode, \n");
                sql.append(" general.remark \n");

                sql.append(" FROM gch_user_general AS general \n");
                sql.append(" WHERE general.wx_open_id = '" + openId + "'\n");
                sql.append(" AND general.isdelete = 0 \n");

                Mobile_UserGeneral general = null;
                Record r = create.fetchOne(sql.toString());

                if (r != null) {
                    general = r.into(Mobile_UserGeneral.class);
                    if (general == null || general.headUrl == "" || general.headUrl == null) {
                        general.headUrl = "";
                    } else {
                        general.headUrl = Config.OSS + general.headUrl.replace("type", "small");
                    }
                    general.session = getSession(general.id, ua, platform);
                } else {
                    // 如果当前openId的用户不存在系统中,则创建一个用户.
                    GchUserGeneralRecord userGeneral = create.newRecord(GCH_USER_GENERAL);
                    String uuId = genUUID();
                    userGeneral.setId(uuId);
                    userGeneral.setRole(Constants.GENERAL_ROLE_GENERALUSER);
                    userGeneral.setWxOpenId(openId);
                    userGeneral.setCreatetime(DateUtils.millisecondChangeTimestamp());
                    userGeneral.setIsdelete(0);
                    userGeneral.setUpdatetime(DateUtils.millisecondChangeTimestamp());
                    userGeneral.insert();

                    // 把新添加的用户信息传至前端
                    general = new Mobile_UserGeneral();
                    general.id = uuId;
                    general.session = getSession(uuId, ua, platform);
                }
                return success(general);
            } else  {
                return  error(-1,"该接口只能使用微信登录.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }
    }

    /**
     * @param _userid 用户id
     * @param _ua User-Agent
     * @param _platform 请求平台
     * @return
     */
    private Mobile_UserGeneral.Session getSession(String _userid, String _ua, String _platform) {
        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {
            UserAgent userAgent = UserAgent.parseUserAgentString(_ua);
            log.debug(String.format("get session asdf %s", _userid));

            Result<V3UserDeviceRecord> result =
                    create.fetch(V3_USER_DEVICE, V3_USER_DEVICE.USERID.eq(_userid));//.into(V3_USER_DEVICE);

            V3UserDeviceRecord record;
            String token = null;
            if (result.size() == 0) {

            }
            /*
            这个地方有问题 如果这个逻辑总是记录一个设备id
            不过目前4s店 1个账号多人使用 就用1个device 也可以。
             */
            else if (result.size() == 1) {
                token = result.get(0).getToken();
                result.get(0).setTokenLastLoginAt(new Timestamp(System.currentTimeMillis()));
                result.get(0).store();
            }
            else {
                for (V3UserDeviceRecord item : result) {
                    if (item.getDeviceDesc() == _ua) {
                        token = item.getToken();
                        item.setTokenLastLoginAt(DateUtils.millisecondChangeTimestamp());
                        item.store();
                        break;
                    }
                }
            }
            if (token == null) {
                // 如果token不存在 则添加Token
                token = UtilBase.genUUID();
                record = create.newRecord(V3_USER_DEVICE);
                record.setToken(token);
                record.setUserid(_userid);
                record.setDeviceDesc(_ua);
                record.setDeviceBrowser(userAgent.getBrowser().getName());
                record.setDeviceOs(userAgent.getOperatingSystem().getName());
                record.setTokenLastLoginAt(DateUtils.millisecondChangeTimestamp());
                record.setDevicePlatform(_platform);
                record.store();
            }
            log.debug(String.format("SessionManager.getInstance().createSID"));
            String sessionid = SessionManager.getInstance().createSID(token, _userid);
            Mobile_UserGeneral.Session se = new Mobile_UserGeneral.Session();
            se.sessionid = sessionid;
            se.token = token;
            return se;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
    * @param query   {"cellphone":"18751852690","signInNo":"8650141","password":"12345116"}
    * @Description: 注册接口
    * @Author: 韩武洽 @Wyshown
    * @Date: 2016/10/27 9:16
    * @Version 2.0
    */
    @POST
    @Path("auth/loginUp")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String loginUp(String query) {
        String ua = req.getHeader("User-Agent");
        System.out.println(ua);

        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {
            JsonObject json = new JsonParser().parse(query).getAsJsonObject();
            String signInNo = getJsonAsString(json, "signInNo");
            String password = getJsonAsString(json, "password");
            String cellphone = getJsonAsString(json, "cellphone");

            // 查询出手机号对应的用户是否存在
            Record rr = findGeneralUserByCell(create,cellphone);
            if (rr != null ) {
                return error(-1,"该手机号已存在,请直接登录!");
            } else {
                // 查询 手机对应的注册验证码存在
                Record r = new AuthCodeController().checkSignInNoIsValid(
                        create,cellphone,signInNo,AuthCodeController.AUTHCODE_TYPE_SENTSIGNINNO);
                if (r != null) {
                    // 添加个人用户
                    GchUserGeneralRecord userGeneral = create.newRecord(GCH_USER_GENERAL);
                    String uuId = genUUID();
                    userGeneral.setId(uuId);
                    userGeneral.setUserName(cellphone);
                    userGeneral.setPassword(MD5.MD5Encode(password));
                    userGeneral.setTel(cellphone);
                    userGeneral.setRole(Constants.GENERAL_ROLE_GENERALUSER);
                    userGeneral.setCreatetime(DateUtils.millisecondChangeTimestamp());
                    userGeneral.setIsdelete(0);
                    userGeneral.setUpdatetime(DateUtils.millisecondChangeTimestamp());
                    userGeneral.insert();
                    return success();
                } else {
                    return  error(-1,"验证码不正确");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }
    }

    /**
     * @Description:  {"cellphone":"18751852690","signInNo":"8650141","password":"12345116","userGeneralId":"20"}
     * @Return: 修改密码操作.
     * @Author: 韩武洽 @Wyshown
     * @Date: 2016/10/27 10:20
     * @Version 2.0
     */
    @POST
    @Path("auth/forgetPassword")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String forgetPassword(String query) {
        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {
            JsonObject json = new JsonParser().parse(query).getAsJsonObject();
            String signInNo = getJsonAsString(json, "signInNo");
            String password = getJsonAsString(json, "password");
            String cellphone = getJsonAsString(json, "cellphone");
//            String userGeneralId = getJsonAsString(json, "userGeneralId");

            // 查询出手机号对应的用户是否存在
            Record rr = findGeneralUserByCell(create,cellphone);
            if (rr != null ) {
                Base_Idname baseIdname= rr.into(Base_Idname.class);
                // 查询修改密码的注册码是否存在
                Record r = new AuthCodeController().checkSignInNoIsValid(
                        create,cellphone,signInNo,AuthCodeController.AUTHCODE_TYPE_CHANGEPASSWORD);
                if (r != null) {
                    // 修改个人用户
                    GchUserGeneralRecord userGeneral = create.newRecord(GCH_USER_GENERAL);
                    userGeneral.setId(baseIdname.id);
                    // 修改密码 通过MD5加密
                    userGeneral.setPassword(MD5.MD5Encode(password));
                    userGeneral.setUpdatetime(DateUtils.millisecondChangeTimestamp());
                    userGeneral.update();
                    return success();
                } else {
                    return  error(-1,"验证码不正确");
                }
            } else {
                return error(-1,"该手机号不存在!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }
    }


    /**
    * @param create  DSLContext
    * @param cellphone 手机号
    * @Description: 查询出手机号对应的个人用户是否存在
    * @Return:
    * @Author: 韩武洽 @Wyshown
    * @Date: 2016/10/27 10:06
    * @Version 2.0
    */
    public Record findGeneralUserByCell(DSLContext create,String cellphone) {
        StringBuffer userGeneralSql = new StringBuffer();
        userGeneralSql.append(" SELECT id,user_name \n");
        userGeneralSql.append(" FROM gch_user_general \n");
        userGeneralSql.append(" WHERE tel = '" + cellphone + "' AND isdelete = 0 \n");
        return create.fetchOne(userGeneralSql.toString());
    }
}
