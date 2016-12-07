package com.lrest.server.controller.Car;

/**
 * Created by acans on 16/6/21.
 */

import com.google.gson.*;
import com.lrest.server.controller.BaseController;
import com.lrest.server.jooqmodel.Tables;

import com.lrest.server.jooqmodel.tables.*;
import com.lrest.server.jooqmodel.tables.GchPay;
import com.lrest.server.jooqmodel.tables.records.GchPayRecord;
import com.lrest.server.jooqmodel.tables.records.GchPayAreaLowPriceRecord;


import com.lrest.server.jooqmodel.tables.records.GchWxpayPackageRecord;
import com.lrest.server.models.*;
import com.lrest.server.services.Config;
import com.lrest.server.services.DB;


import com.lrest.server.services.weixin.WXAPIConfig;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.tools.StringUtils;
import org.jooq.tools.json.JSONObject;
import org.jooq.types.UByte;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;


import static com.lrest.server.jooqmodel.Tables.*;
import static com.lrest.server.utils.UtilBase.*;
import static com.tencent.common.RandomStringGenerator.getRandomStringByLength;
import static com.tencent.common.Signature.getSign;


import static com.tencent.common.XMLParser.getMapFromXML;
import static java.util.stream.Collectors.*;

@Path("/nl/order")
public class OrderController extends BaseController {
    private final org.slf4j.Logger log = LoggerFactory.getLogger(getClass());

    @POST
    @Path("weixin/notify")
    @Produces(MediaType.TEXT_XML + ";charset=utf-8")
    @Consumes(MediaType.TEXT_XML)
    public String notify(String query) {
        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {
            log.info("接收的原始数据" + query);
            if (query.length() != 0) {
                Map<String, Object> info = getMapFromXML(query);
                log.info("转map之后：" + info);

                String return_code = (String) info.get("return_code");
                String result_code = (String) info.get("result_code");
                String out_trade_no = (String) info.get("out_trade_no");
                GchPay pay = new GchPay();
                GchPayRecord record = create.newRecord(GCH_PAY);

                Record2<String, Integer> result = create.select(pay.ID, pay.STATUS)
                        .from(pay)
                        .where(pay.OUT_TRADE_NO.eq(out_trade_no).and(pay.ISDELETE.eq(0)))
                        .limit(1)
                        .fetchOne();

                if (result != null) {
                    GchWxpayPackageRecord recordpay = create.newRecord(GCH_WXPAY_PACKAGE);
                    recordpay.setPayId((String) result.getValue(0));
                    recordpay.setOutTradeNo(out_trade_no);
                    recordpay.setTransactionId((String) info.get("transaction_id"));
                    recordpay.setTotalFee(Integer.parseInt((String) info.get("total_fee")));
                    recordpay.setTradeType((String) info.get("trade_type"));
                    recordpay.setBankType((String) info.get("bank_type"));
                    recordpay.setTimeEnd((String) info.get("time_end"));
                    recordpay.setPackage(String.valueOf(info));
                    int in = recordpay.insert();
                    log.debug(String.valueOf(in));

                    if (return_code.equals("SUCCESS") && result_code.equals("SUCCESS")) {
                        weixin_return parms = new weixin_return();
                        if (result.getValue(1).equals(9)) {
                            long seconds = System.currentTimeMillis() / 1000;
                            record.setId((String) result.getValue(0));
                            record.setStatus(1);
                            record.setUpdatetime(seconds);
                            int upd = record.update();
                            log.info(String.valueOf(upd));
                            if (upd == 1) {
                                parms.return_code = "SUCCESS";
                                parms.return_msg = "OK";
                            } else {
                                parms.return_code = "FAIL";
                                parms.return_msg = "系统更新支付状态失败";
                            }
                        } else {
                            parms.return_code = "SUCCESS";
                            parms.return_msg = "OK";
                        }

                        XStream xStreamForRequestPostData = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
                        xStreamForRequestPostData.alias("xml", weixin_return.class);
                        String postDataXML = xStreamForRequestPostData.toXML(parms);
                        try {
                            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("text/xml;charset=UTF-8"), postDataXML);

                            String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
                            OkHttpClient client = new OkHttpClient();

                            Request request = new Request.Builder()
                                    .url(url)
                                    .post(body)
                                    .build();
                            Response response = client.newCall(request).execute();
                            if (response.isSuccessful()) {
                                return success("已经成功将信息反馈给微信！");
                            } else {
                                return error(-1, response.toString());
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            return null;
                        }

                    } else {
                        String return_msg = (String) info.get("return_msg");
                        String err_code_des = (String) info.get("err_code_des");
                        log.info(return_msg);
                        log.info(err_code_des);
                        if (!StringUtils.isEmpty(return_msg)) {
                            record.setId((String) result.getValue(0));
                            record.setStatus(10);
                            record.setPayErrCodeDes(return_msg);
                            record.update();
                        } else if (!StringUtils.isEmpty(err_code_des)) {
                            record.setId((String) result.getValue(0));
                            record.setStatus(10);
                            record.setPayErrCodeDes(err_code_des);
                            record.update();
                        } else {
                            record.setId((String) result.getValue(0));
                            record.setStatus(10);
                            record.setPayErrCodeDes("返回数据为空，应该不会发生");
                            record.update();
                        }
                        return success(info);
                    }
                } else {
                    return error(-1, "非系统内订单");
                }

            } else {
                return error(-1, "data doesn't exist");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }


    }

}
