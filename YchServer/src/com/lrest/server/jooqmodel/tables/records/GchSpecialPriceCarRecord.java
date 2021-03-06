/**
 * This class is generated by jOOQ
 */
package com.lrest.server.jooqmodel.tables.records;


import com.lrest.server.jooqmodel.tables.GchSpecialPriceCar;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record21;
import org.jooq.Row21;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 特价车
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.2"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class GchSpecialPriceCarRecord extends UpdatableRecordImpl<GchSpecialPriceCarRecord> implements Record21<String, String, String, String, String, Integer, String, Integer, Integer, Timestamp, Timestamp, Integer, Byte, String, String, Integer, Timestamp, String, Integer, String, Timestamp> {

    private static final long serialVersionUID = 907275115;

    /**
     * Setter for <code>gouchehui2.0.gch_special_price_car.id</code>. 主键
     */
    public void setId(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_special_price_car.id</code>. 主键
     */
    public String getId() {
        return (String) get(0);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_special_price_car.user_id</code>. 关联用户ID
     */
    public void setUserId(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_special_price_car.user_id</code>. 关联用户ID
     */
    public String getUserId() {
        return (String) get(1);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_special_price_car.interior_color_id</code>. 内饰颜色ID
     */
    public void setInteriorColorId(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_special_price_car.interior_color_id</code>. 内饰颜色ID
     */
    public String getInteriorColorId() {
        return (String) get(2);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_special_price_car.exterior_color_id</code>. 外观颜色ID
     */
    public void setExteriorColorId(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_special_price_car.exterior_color_id</code>. 外观颜色ID
     */
    public String getExteriorColorId() {
        return (String) get(3);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_special_price_car.car_image</code>. 车款图片
     */
    public void setCarImage(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_special_price_car.car_image</code>. 车款图片
     */
    public String getCarImage() {
        return (String) get(4);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_special_price_car.client</code>. 客户端(1:pc,2:手机web端)
     */
    public void setClient(Integer value) {
        set(5, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_special_price_car.client</code>. 客户端(1:pc,2:手机web端)
     */
    public Integer getClient() {
        return (Integer) get(5);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_special_price_car.car_id</code>. 汽车ID
     */
    public void setCarId(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_special_price_car.car_id</code>. 汽车ID
     */
    public String getCarId() {
        return (String) get(6);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_special_price_car.price</code>. 价格
     */
    public void setPrice(Integer value) {
        set(7, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_special_price_car.price</code>. 价格
     */
    public Integer getPrice() {
        return (Integer) get(7);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_special_price_car.special_price</code>. 特价
     */
    public void setSpecialPrice(Integer value) {
        set(8, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_special_price_car.special_price</code>. 特价
     */
    public Integer getSpecialPrice() {
        return (Integer) get(8);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_special_price_car.start_date</code>. 开始时间
     */
    public void setStartDate(Timestamp value) {
        set(9, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_special_price_car.start_date</code>. 开始时间
     */
    public Timestamp getStartDate() {
        return (Timestamp) get(9);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_special_price_car.end_date</code>. 结束时间
     */
    public void setEndDate(Timestamp value) {
        set(10, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_special_price_car.end_date</code>. 结束时间
     */
    public Timestamp getEndDate() {
        return (Timestamp) get(10);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_special_price_car.number</code>. 活动数量
     */
    public void setNumber(Integer value) {
        set(11, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_special_price_car.number</code>. 活动数量
     */
    public Integer getNumber() {
        return (Integer) get(11);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_special_price_car.status</code>. 特价车状态（1：审核中，2：未开始，3：在售，4：停售，5：过期，6：审核失败）
     */
    public void setStatus(Byte value) {
        set(12, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_special_price_car.status</code>. 特价车状态（1：审核中，2：未开始，3：在售，4：停售，5：过期，6：审核失败）
     */
    public Byte getStatus() {
        return (Byte) get(12);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_special_price_car.remark</code>. 备注(审核不通过）
     */
    public void setRemark(String value) {
        set(13, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_special_price_car.remark</code>. 备注(审核不通过）
     */
    public String getRemark() {
        return (String) get(13);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_special_price_car.description</code>. 活动说明
     */
    public void setDescription(String value) {
        set(14, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_special_price_car.description</code>. 活动说明
     */
    public String getDescription() {
        return (String) get(14);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_special_price_car.attention_count</code>. 关注度
     */
    public void setAttentionCount(Integer value) {
        set(15, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_special_price_car.attention_count</code>. 关注度
     */
    public Integer getAttentionCount() {
        return (Integer) get(15);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_special_price_car.createtime</code>. 创建时间
     */
    public void setCreatetime(Timestamp value) {
        set(16, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_special_price_car.createtime</code>. 创建时间
     */
    public Timestamp getCreatetime() {
        return (Timestamp) get(16);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_special_price_car.createuser</code>. 创建人
     */
    public void setCreateuser(String value) {
        set(17, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_special_price_car.createuser</code>. 创建人
     */
    public String getCreateuser() {
        return (String) get(17);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_special_price_car.isdelete</code>. 是否删除
     */
    public void setIsdelete(Integer value) {
        set(18, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_special_price_car.isdelete</code>. 是否删除
     */
    public Integer getIsdelete() {
        return (Integer) get(18);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_special_price_car.updateuser</code>. 修改人
     */
    public void setUpdateuser(String value) {
        set(19, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_special_price_car.updateuser</code>. 修改人
     */
    public String getUpdateuser() {
        return (String) get(19);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_special_price_car.updatetime</code>. 修改时间
     */
    public void setUpdatetime(Timestamp value) {
        set(20, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_special_price_car.updatetime</code>. 修改时间
     */
    public Timestamp getUpdatetime() {
        return (Timestamp) get(20);
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
    // Record21 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row21<String, String, String, String, String, Integer, String, Integer, Integer, Timestamp, Timestamp, Integer, Byte, String, String, Integer, Timestamp, String, Integer, String, Timestamp> fieldsRow() {
        return (Row21) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row21<String, String, String, String, String, Integer, String, Integer, Integer, Timestamp, Timestamp, Integer, Byte, String, String, Integer, Timestamp, String, Integer, String, Timestamp> valuesRow() {
        return (Row21) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field1() {
        return GchSpecialPriceCar.GCH_SPECIAL_PRICE_CAR.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return GchSpecialPriceCar.GCH_SPECIAL_PRICE_CAR.USER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return GchSpecialPriceCar.GCH_SPECIAL_PRICE_CAR.INTERIOR_COLOR_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return GchSpecialPriceCar.GCH_SPECIAL_PRICE_CAR.EXTERIOR_COLOR_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return GchSpecialPriceCar.GCH_SPECIAL_PRICE_CAR.CAR_IMAGE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field6() {
        return GchSpecialPriceCar.GCH_SPECIAL_PRICE_CAR.CLIENT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field7() {
        return GchSpecialPriceCar.GCH_SPECIAL_PRICE_CAR.CAR_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field8() {
        return GchSpecialPriceCar.GCH_SPECIAL_PRICE_CAR.PRICE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field9() {
        return GchSpecialPriceCar.GCH_SPECIAL_PRICE_CAR.SPECIAL_PRICE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field10() {
        return GchSpecialPriceCar.GCH_SPECIAL_PRICE_CAR.START_DATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field11() {
        return GchSpecialPriceCar.GCH_SPECIAL_PRICE_CAR.END_DATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field12() {
        return GchSpecialPriceCar.GCH_SPECIAL_PRICE_CAR.NUMBER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field13() {
        return GchSpecialPriceCar.GCH_SPECIAL_PRICE_CAR.STATUS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field14() {
        return GchSpecialPriceCar.GCH_SPECIAL_PRICE_CAR.REMARK;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field15() {
        return GchSpecialPriceCar.GCH_SPECIAL_PRICE_CAR.DESCRIPTION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field16() {
        return GchSpecialPriceCar.GCH_SPECIAL_PRICE_CAR.ATTENTION_COUNT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field17() {
        return GchSpecialPriceCar.GCH_SPECIAL_PRICE_CAR.CREATETIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field18() {
        return GchSpecialPriceCar.GCH_SPECIAL_PRICE_CAR.CREATEUSER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field19() {
        return GchSpecialPriceCar.GCH_SPECIAL_PRICE_CAR.ISDELETE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field20() {
        return GchSpecialPriceCar.GCH_SPECIAL_PRICE_CAR.UPDATEUSER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field21() {
        return GchSpecialPriceCar.GCH_SPECIAL_PRICE_CAR.UPDATETIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getInteriorColorId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getExteriorColorId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getCarImage();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value6() {
        return getClient();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value7() {
        return getCarId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value8() {
        return getPrice();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value9() {
        return getSpecialPrice();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value10() {
        return getStartDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value11() {
        return getEndDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value12() {
        return getNumber();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value13() {
        return getStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value14() {
        return getRemark();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value15() {
        return getDescription();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value16() {
        return getAttentionCount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value17() {
        return getCreatetime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value18() {
        return getCreateuser();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value19() {
        return getIsdelete();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value20() {
        return getUpdateuser();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value21() {
        return getUpdatetime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GchSpecialPriceCarRecord value1(String value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GchSpecialPriceCarRecord value2(String value) {
        setUserId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GchSpecialPriceCarRecord value3(String value) {
        setInteriorColorId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GchSpecialPriceCarRecord value4(String value) {
        setExteriorColorId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GchSpecialPriceCarRecord value5(String value) {
        setCarImage(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GchSpecialPriceCarRecord value6(Integer value) {
        setClient(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GchSpecialPriceCarRecord value7(String value) {
        setCarId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GchSpecialPriceCarRecord value8(Integer value) {
        setPrice(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GchSpecialPriceCarRecord value9(Integer value) {
        setSpecialPrice(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GchSpecialPriceCarRecord value10(Timestamp value) {
        setStartDate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GchSpecialPriceCarRecord value11(Timestamp value) {
        setEndDate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GchSpecialPriceCarRecord value12(Integer value) {
        setNumber(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GchSpecialPriceCarRecord value13(Byte value) {
        setStatus(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GchSpecialPriceCarRecord value14(String value) {
        setRemark(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GchSpecialPriceCarRecord value15(String value) {
        setDescription(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GchSpecialPriceCarRecord value16(Integer value) {
        setAttentionCount(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GchSpecialPriceCarRecord value17(Timestamp value) {
        setCreatetime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GchSpecialPriceCarRecord value18(String value) {
        setCreateuser(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GchSpecialPriceCarRecord value19(Integer value) {
        setIsdelete(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GchSpecialPriceCarRecord value20(String value) {
        setUpdateuser(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GchSpecialPriceCarRecord value21(Timestamp value) {
        setUpdatetime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GchSpecialPriceCarRecord values(String value1, String value2, String value3, String value4, String value5, Integer value6, String value7, Integer value8, Integer value9, Timestamp value10, Timestamp value11, Integer value12, Byte value13, String value14, String value15, Integer value16, Timestamp value17, String value18, Integer value19, String value20, Timestamp value21) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        value10(value10);
        value11(value11);
        value12(value12);
        value13(value13);
        value14(value14);
        value15(value15);
        value16(value16);
        value17(value17);
        value18(value18);
        value19(value19);
        value20(value20);
        value21(value21);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached GchSpecialPriceCarRecord
     */
    public GchSpecialPriceCarRecord() {
        super(GchSpecialPriceCar.GCH_SPECIAL_PRICE_CAR);
    }

    /**
     * Create a detached, initialised GchSpecialPriceCarRecord
     */
    public GchSpecialPriceCarRecord(String id, String userId, String interiorColorId, String exteriorColorId, String carImage, Integer client, String carId, Integer price, Integer specialPrice, Timestamp startDate, Timestamp endDate, Integer number, Byte status, String remark, String description, Integer attentionCount, Timestamp createtime, String createuser, Integer isdelete, String updateuser, Timestamp updatetime) {
        super(GchSpecialPriceCar.GCH_SPECIAL_PRICE_CAR);

        set(0, id);
        set(1, userId);
        set(2, interiorColorId);
        set(3, exteriorColorId);
        set(4, carImage);
        set(5, client);
        set(6, carId);
        set(7, price);
        set(8, specialPrice);
        set(9, startDate);
        set(10, endDate);
        set(11, number);
        set(12, status);
        set(13, remark);
        set(14, description);
        set(15, attentionCount);
        set(16, createtime);
        set(17, createuser);
        set(18, isdelete);
        set(19, updateuser);
        set(20, updatetime);
    }
}
