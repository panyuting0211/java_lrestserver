/**
 * This class is generated by jOOQ
 */
package com.lrest.server.jooqmodel.tables.records;


import com.lrest.server.jooqmodel.tables.GchUserActivity;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Record1;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.2"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class GchUserActivityRecord extends UpdatableRecordImpl<GchUserActivityRecord> {

    private static final long serialVersionUID = -1188501771;

    /**
     * Setter for <code>gouchehui2.0.gch_user_activity.id</code>. 主键id
     */
    public void setId(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_user_activity.id</code>. 主键id
     */
    public String getId() {
        return (String) get(0);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_user_activity.source</code>. 来源：1：web端，2：微信端
     */
    public void setSource(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_user_activity.source</code>. 来源：1：web端，2：微信端
     */
    public Integer getSource() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_user_activity.user_name</code>. 用户名
     */
    public void setUserName(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_user_activity.user_name</code>. 用户名
     */
    public String getUserName() {
        return (String) get(2);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_user_activity.tel</code>. 手机号
     */
    public void setTel(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_user_activity.tel</code>. 手机号
     */
    public String getTel() {
        return (String) get(3);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_user_activity.activity_price</code>. 活动价
     */
    public void setActivityPrice(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_user_activity.activity_price</code>. 活动价
     */
    public String getActivityPrice() {
        return (String) get(4);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_user_activity.brand_id</code>. 品牌id
     */
    public void setBrandId(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_user_activity.brand_id</code>. 品牌id
     */
    public String getBrandId() {
        return (String) get(5);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_user_activity.brand_name</code>. 品牌名称
     */
    public void setBrandName(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_user_activity.brand_name</code>. 品牌名称
     */
    public String getBrandName() {
        return (String) get(6);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_user_activity.car_model_id</code>. 车型id
     */
    public void setCarModelId(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_user_activity.car_model_id</code>. 车型id
     */
    public String getCarModelId() {
        return (String) get(7);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_user_activity.car_model_name</code>. 车型名称
     */
    public void setCarModelName(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_user_activity.car_model_name</code>. 车型名称
     */
    public String getCarModelName() {
        return (String) get(8);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_user_activity.car_id</code>. 车款id
     */
    public void setCarId(String value) {
        set(9, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_user_activity.car_id</code>. 车款id
     */
    public String getCarId() {
        return (String) get(9);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_user_activity.car_name</code>. 车款名称
     */
    public void setCarName(String value) {
        set(10, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_user_activity.car_name</code>. 车款名称
     */
    public String getCarName() {
        return (String) get(10);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_user_activity.exterior_color_id</code>. 外观颜色id
     */
    public void setExteriorColorId(String value) {
        set(11, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_user_activity.exterior_color_id</code>. 外观颜色id
     */
    public String getExteriorColorId() {
        return (String) get(11);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_user_activity.exterior_color_name</code>. 外观颜色名称
     */
    public void setExteriorColorName(String value) {
        set(12, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_user_activity.exterior_color_name</code>. 外观颜色名称
     */
    public String getExteriorColorName() {
        return (String) get(12);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_user_activity.interior_color_id</code>. 内饰颜色id
     */
    public void setInteriorColorId(String value) {
        set(13, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_user_activity.interior_color_id</code>. 内饰颜色id
     */
    public String getInteriorColorId() {
        return (String) get(13);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_user_activity.interior_color_name</code>. 内饰颜色名称
     */
    public void setInteriorColorName(String value) {
        set(14, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_user_activity.interior_color_name</code>. 内饰颜色名称
     */
    public String getInteriorColorName() {
        return (String) get(14);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_user_activity.activity_number</code>. 活动编号
     */
    public void setActivityNumber(String value) {
        set(15, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_user_activity.activity_number</code>. 活动编号
     */
    public String getActivityNumber() {
        return (String) get(15);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_user_activity.activity_name</code>. 活动名称
     */
    public void setActivityName(String value) {
        set(16, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_user_activity.activity_name</code>. 活动名称
     */
    public String getActivityName() {
        return (String) get(16);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_user_activity.flag</code>. 活动类型：1：立即报名，2：特价车，3：立即购买，4：马上抢
     */
    public void setFlag(Integer value) {
        set(17, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_user_activity.flag</code>. 活动类型：1：立即报名，2：特价车，3：立即购买，4：马上抢
     */
    public Integer getFlag() {
        return (Integer) get(17);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_user_activity.createtime</code>. 创建时间
     */
    public void setCreatetime(Timestamp value) {
        set(18, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_user_activity.createtime</code>. 创建时间
     */
    public Timestamp getCreatetime() {
        return (Timestamp) get(18);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_user_activity.createuser</code>. 创建人
     */
    public void setCreateuser(String value) {
        set(19, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_user_activity.createuser</code>. 创建人
     */
    public String getCreateuser() {
        return (String) get(19);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_user_activity.isdelete</code>. 是否删除
     */
    public void setIsdelete(Integer value) {
        set(20, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_user_activity.isdelete</code>. 是否删除
     */
    public Integer getIsdelete() {
        return (Integer) get(20);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_user_activity.updatetime</code>. 修改时间
     */
    public void setUpdatetime(Timestamp value) {
        set(21, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_user_activity.updatetime</code>. 修改时间
     */
    public Timestamp getUpdatetime() {
        return (Timestamp) get(21);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_user_activity.updateuser</code>. 修改人
     */
    public void setUpdateuser(String value) {
        set(22, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_user_activity.updateuser</code>. 修改人
     */
    public String getUpdateuser() {
        return (String) get(22);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_user_activity.reward_type</code>. 奖励的标识(积分,优惠券)
     */
    public void setRewardType(String value) {
        set(23, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_user_activity.reward_type</code>. 奖励的标识(积分,优惠券)
     */
    public String getRewardType() {
        return (String) get(23);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_user_activity.reward_id</code>. 奖励的唯一标识
     */
    public void setRewardId(String value) {
        set(24, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_user_activity.reward_id</code>. 奖励的唯一标识
     */
    public String getRewardId() {
        return (String) get(24);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_user_activity.remarks</code>. 备注
     */
    public void setRemarks(String value) {
        set(25, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_user_activity.remarks</code>. 备注
     */
    public String getRemarks() {
        return (String) get(25);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_user_activity.status_track</code>. 询价订单状态（1：未跟踪，2：已跟踪）
     */
    public void setStatusTrack(Integer value) {
        set(26, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_user_activity.status_track</code>. 询价订单状态（1：未跟踪，2：已跟踪）
     */
    public Integer getStatusTrack() {
        return (Integer) get(26);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_user_activity.cus_name</code>. 客户姓名
     */
    public void setCusName(String value) {
        set(27, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_user_activity.cus_name</code>. 客户姓名
     */
    public String getCusName() {
        return (String) get(27);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_user_activity.cus_remark</code>. 客服备注
     */
    public void setCusRemark(String value) {
        set(28, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_user_activity.cus_remark</code>. 客服备注
     */
    public String getCusRemark() {
        return (String) get(28);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_user_activity.cus_character</code>. 客服-角色判读（1：用户，2：内部）
     */
    public void setCusCharacter(String value) {
        set(29, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_user_activity.cus_character</code>. 客服-角色判读（1：用户，2：内部）
     */
    public String getCusCharacter() {
        return (String) get(29);
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
     * Create a detached GchUserActivityRecord
     */
    public GchUserActivityRecord() {
        super(GchUserActivity.GCH_USER_ACTIVITY);
    }

    /**
     * Create a detached, initialised GchUserActivityRecord
     */
    public GchUserActivityRecord(String id, Integer source, String userName, String tel, String activityPrice, String brandId, String brandName, String carModelId, String carModelName, String carId, String carName, String exteriorColorId, String exteriorColorName, String interiorColorId, String interiorColorName, String activityNumber, String activityName, Integer flag, Timestamp createtime, String createuser, Integer isdelete, Timestamp updatetime, String updateuser, String rewardType, String rewardId, String remarks, Integer statusTrack, String cusName, String cusRemark, String cusCharacter) {
        super(GchUserActivity.GCH_USER_ACTIVITY);

        set(0, id);
        set(1, source);
        set(2, userName);
        set(3, tel);
        set(4, activityPrice);
        set(5, brandId);
        set(6, brandName);
        set(7, carModelId);
        set(8, carModelName);
        set(9, carId);
        set(10, carName);
        set(11, exteriorColorId);
        set(12, exteriorColorName);
        set(13, interiorColorId);
        set(14, interiorColorName);
        set(15, activityNumber);
        set(16, activityName);
        set(17, flag);
        set(18, createtime);
        set(19, createuser);
        set(20, isdelete);
        set(21, updatetime);
        set(22, updateuser);
        set(23, rewardType);
        set(24, rewardId);
        set(25, remarks);
        set(26, statusTrack);
        set(27, cusName);
        set(28, cusRemark);
        set(29, cusCharacter);
    }
}