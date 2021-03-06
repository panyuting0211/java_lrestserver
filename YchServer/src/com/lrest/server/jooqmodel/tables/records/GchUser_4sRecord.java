/**
 * This class is generated by jOOQ
 */
package com.lrest.server.jooqmodel.tables.records;


import com.lrest.server.jooqmodel.tables.GchUser_4s;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Record1;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 用户表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.2"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class GchUser_4sRecord extends UpdatableRecordImpl<GchUser_4sRecord> {

    private static final long serialVersionUID = 622846721;

    /**
     * Setter for <code>gouchehui2.0.gch_user_4s.id</code>. 主键
     */
    public void setId(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_user_4s.id</code>. 主键
     */
    public String getId() {
        return (String) get(0);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_user_4s.user_name</code>. 登录名
     */
    public void setUserName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_user_4s.user_name</code>. 登录名
     */
    public String getUserName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_user_4s.password</code>. 登陆密码(MD5)
     */
    public void setPassword(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_user_4s.password</code>. 登陆密码(MD5)
     */
    public String getPassword() {
        return (String) get(2);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_user_4s.role</code>. 身份1、普通用户 2、4S店会员(关联到4S店自定管理4S店主键) 3、经销商
     */
    public void setRole(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_user_4s.role</code>. 身份1、普通用户 2、4S店会员(关联到4S店自定管理4S店主键) 3、经销商
     */
    public Integer getRole() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_user_4s.tel</code>. 电话
     */
    public void setTel(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_user_4s.tel</code>. 电话
     */
    public String getTel() {
        return (String) get(4);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_user_4s.email</code>. 邮箱
     */
    public void setEmail(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_user_4s.email</code>. 邮箱
     */
    public String getEmail() {
        return (String) get(5);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_user_4s.status</code>. 用户状态
     */
    public void setStatus(Short value) {
        set(6, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_user_4s.status</code>. 用户状态
     */
    public Short getStatus() {
        return (Short) get(6);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_user_4s.head_url</code>. 头像
     */
    public void setHeadUrl(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_user_4s.head_url</code>. 头像
     */
    public String getHeadUrl() {
        return (String) get(7);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_user_4s.nick</code>. 昵称
     */
    public void setNick(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_user_4s.nick</code>. 昵称
     */
    public String getNick() {
        return (String) get(8);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_user_4s.name_4s</code>. 4S店名称
     */
    public void setName_4s(String value) {
        set(9, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_user_4s.name_4s</code>. 4S店名称
     */
    public String getName_4s() {
        return (String) get(9);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_user_4s.brand_4s</code>. 4S店主营品牌
     */
    public void setBrand_4s(String value) {
        set(10, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_user_4s.brand_4s</code>. 4S店主营品牌
     */
    public String getBrand_4s() {
        return (String) get(10);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_user_4s.contacts</code>. 联系人
     */
    public void setContacts(String value) {
        set(11, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_user_4s.contacts</code>. 联系人
     */
    public String getContacts() {
        return (String) get(11);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_user_4s.addr</code>. 地址
     */
    public void setAddr(String value) {
        set(12, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_user_4s.addr</code>. 地址
     */
    public String getAddr() {
        return (String) get(12);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_user_4s.city_id</code>. 城市ID
     */
    public void setCityId(String value) {
        set(13, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_user_4s.city_id</code>. 城市ID
     */
    public String getCityId() {
        return (String) get(13);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_user_4s.my_num</code>. 我的邀请码
     */
    public void setMyNum(String value) {
        set(14, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_user_4s.my_num</code>. 我的邀请码
     */
    public String getMyNum() {
        return (String) get(14);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_user_4s.binding_num</code>. 绑定邀请码
     */
    public void setBindingNum(String value) {
        set(15, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_user_4s.binding_num</code>. 绑定邀请码
     */
    public String getBindingNum() {
        return (String) get(15);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_user_4s.total_jifen</code>. 用户积分
     */
    public void setTotalJifen(Integer value) {
        set(16, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_user_4s.total_jifen</code>. 用户积分
     */
    public Integer getTotalJifen() {
        return (Integer) get(16);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_user_4s.jifen_sign</code>.
     */
    public void setJifenSign(String value) {
        set(17, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_user_4s.jifen_sign</code>.
     */
    public String getJifenSign() {
        return (String) get(17);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_user_4s.exchange_usedtimes</code>. 当月积分已使用次数
     */
    public void setExchangeUsedtimes(Integer value) {
        set(18, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_user_4s.exchange_usedtimes</code>. 当月积分已使用次数
     */
    public Integer getExchangeUsedtimes() {
        return (Integer) get(18);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_user_4s.exchange_alltimes</code>. 当月积分总兑换次数
     */
    public void setExchangeAlltimes(Integer value) {
        set(19, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_user_4s.exchange_alltimes</code>. 当月积分总兑换次数
     */
    public Integer getExchangeAlltimes() {
        return (Integer) get(19);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_user_4s.wx_open_id</code>. 微信开放用户ID
     */
    public void setWxOpenId(String value) {
        set(20, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_user_4s.wx_open_id</code>. 微信开放用户ID
     */
    public String getWxOpenId() {
        return (String) get(20);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_user_4s.qq_open_id</code>. QQ互联用户open_id
     */
    public void setQqOpenId(String value) {
        set(21, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_user_4s.qq_open_id</code>. QQ互联用户open_id
     */
    public String getQqOpenId() {
        return (String) get(21);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_user_4s.power</code>. 1：报价权限，2：4s店名称地址权限，4：库存权限；权限值可叠加。
     */
    public void setPower(Integer value) {
        set(22, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_user_4s.power</code>. 1：报价权限，2：4s店名称地址权限，4：库存权限；权限值可叠加。
     */
    public Integer getPower() {
        return (Integer) get(22);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_user_4s.month_car_num</code>. 每个月被标为底价的车款数
     */
    public void setMonthCarNum(Integer value) {
        set(23, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_user_4s.month_car_num</code>. 每个月被标为底价的车款数
     */
    public Integer getMonthCarNum() {
        return (Integer) get(23);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_user_4s.total_car_num</code>.
     */
    public void setTotalCarNum(Integer value) {
        set(24, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_user_4s.total_car_num</code>.
     */
    public Integer getTotalCarNum() {
        return (Integer) get(24);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_user_4s.quarter_car_num</code>. 每个季度被标为底价的车款数
     */
    public void setQuarterCarNum(Integer value) {
        set(25, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_user_4s.quarter_car_num</code>. 每个季度被标为底价的车款数
     */
    public Integer getQuarterCarNum() {
        return (Integer) get(25);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_user_4s.logintime</code>.
     */
    public void setLogintime(String value) {
        set(26, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_user_4s.logintime</code>.
     */
    public String getLogintime() {
        return (String) get(26);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_user_4s.loginip</code>.
     */
    public void setLoginip(String value) {
        set(27, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_user_4s.loginip</code>.
     */
    public String getLoginip() {
        return (String) get(27);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_user_4s.createtime</code>. 创建时间
     */
    public void setCreatetime(Timestamp value) {
        set(28, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_user_4s.createtime</code>. 创建时间
     */
    public Timestamp getCreatetime() {
        return (Timestamp) get(28);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_user_4s.createuser</code>.
     */
    public void setCreateuser(String value) {
        set(29, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_user_4s.createuser</code>.
     */
    public String getCreateuser() {
        return (String) get(29);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_user_4s.isdelete</code>. 是否删除
     */
    public void setIsdelete(Integer value) {
        set(30, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_user_4s.isdelete</code>. 是否删除
     */
    public Integer getIsdelete() {
        return (Integer) get(30);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_user_4s.updatetime</code>. 修改时间
     */
    public void setUpdatetime(Timestamp value) {
        set(31, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_user_4s.updatetime</code>. 修改时间
     */
    public Timestamp getUpdatetime() {
        return (Timestamp) get(31);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_user_4s.updateuser</code>.
     */
    public void setUpdateuser(String value) {
        set(32, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_user_4s.updateuser</code>.
     */
    public String getUpdateuser() {
        return (String) get(32);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<String> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached GchUser_4sRecord
     */
    public GchUser_4sRecord() {
        super(GchUser_4s.GCH_USER_4S);
    }

    /**
     * Create a detached, initialised GchUser_4sRecord
     */
    public GchUser_4sRecord(String id, String userName, String password, Integer role, String tel, String email, Short status, String headUrl, String nick, String name_4s, String brand_4s, String contacts, String addr, String cityId, String myNum, String bindingNum, Integer totalJifen, String jifenSign, Integer exchangeUsedtimes, Integer exchangeAlltimes, String wxOpenId, String qqOpenId, Integer power, Integer monthCarNum, Integer totalCarNum, Integer quarterCarNum, String logintime, String loginip, Timestamp createtime, String createuser, Integer isdelete, Timestamp updatetime, String updateuser) {
        super(GchUser_4s.GCH_USER_4S);

        set(0, id);
        set(1, userName);
        set(2, password);
        set(3, role);
        set(4, tel);
        set(5, email);
        set(6, status);
        set(7, headUrl);
        set(8, nick);
        set(9, name_4s);
        set(10, brand_4s);
        set(11, contacts);
        set(12, addr);
        set(13, cityId);
        set(14, myNum);
        set(15, bindingNum);
        set(16, totalJifen);
        set(17, jifenSign);
        set(18, exchangeUsedtimes);
        set(19, exchangeAlltimes);
        set(20, wxOpenId);
        set(21, qqOpenId);
        set(22, power);
        set(23, monthCarNum);
        set(24, totalCarNum);
        set(25, quarterCarNum);
        set(26, logintime);
        set(27, loginip);
        set(28, createtime);
        set(29, createuser);
        set(30, isdelete);
        set(31, updatetime);
        set(32, updateuser);
    }
}
