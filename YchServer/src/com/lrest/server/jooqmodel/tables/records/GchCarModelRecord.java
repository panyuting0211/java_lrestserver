/**
 * This class is generated by jOOQ
 */
package com.lrest.server.jooqmodel.tables.records;


import com.lrest.server.jooqmodel.tables.GchCarModel;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record15;
import org.jooq.Row15;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 车型表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.2"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class GchCarModelRecord extends UpdatableRecordImpl<GchCarModelRecord> implements Record15<String, String, String, Integer, String, String, Timestamp, String, Integer, Timestamp, String, String, Byte, Integer, Integer> {

    private static final long serialVersionUID = 544225168;

    /**
     * Setter for <code>gouchehui2.0.gch_car_model.id</code>. 主键
     */
    public void setId(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_car_model.id</code>. 主键
     */
    public String getId() {
        return (String) get(0);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_car_model.brand_id</code>. 所属品牌
     */
    public void setBrandId(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_car_model.brand_id</code>. 所属品牌
     */
    public String getBrandId() {
        return (String) get(1);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_car_model.car_type_id</code>. 所属分类
     */
    public void setCarTypeId(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_car_model.car_type_id</code>. 所属分类
     */
    public String getCarTypeId() {
        return (String) get(2);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_car_model.access_quantity</code>. 访问量
     */
    public void setAccessQuantity(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_car_model.access_quantity</code>. 访问量
     */
    public Integer getAccessQuantity() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_car_model.car_model_name</code>. 名称
     */
    public void setCarModelName(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_car_model.car_model_name</code>. 名称
     */
    public String getCarModelName() {
        return (String) get(4);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_car_model.imgurl</code>. 图片
     */
    public void setImgurl(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_car_model.imgurl</code>. 图片
     */
    public String getImgurl() {
        return (String) get(5);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_car_model.createtime</code>. 创建时间
     */
    public void setCreatetime(Timestamp value) {
        set(6, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_car_model.createtime</code>. 创建时间
     */
    public Timestamp getCreatetime() {
        return (Timestamp) get(6);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_car_model.createuser</code>. 创建人
     */
    public void setCreateuser(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_car_model.createuser</code>. 创建人
     */
    public String getCreateuser() {
        return (String) get(7);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_car_model.isdelete</code>. 是否删除
     */
    public void setIsdelete(Integer value) {
        set(8, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_car_model.isdelete</code>. 是否删除
     */
    public Integer getIsdelete() {
        return (Integer) get(8);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_car_model.updatetime</code>. 修改时间
     */
    public void setUpdatetime(Timestamp value) {
        set(9, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_car_model.updatetime</code>. 修改时间
     */
    public Timestamp getUpdatetime() {
        return (Timestamp) get(9);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_car_model.updateuser</code>. 修改人
     */
    public void setUpdateuser(String value) {
        set(10, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_car_model.updateuser</code>. 修改人
     */
    public String getUpdateuser() {
        return (String) get(10);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_car_model.alif</code>.
     */
    public void setAlif(String value) {
        set(11, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_car_model.alif</code>.
     */
    public String getAlif() {
        return (String) get(11);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_car_model.status</code>. 车型状态，1：在售；0：停售
     */
    public void setStatus(Byte value) {
        set(12, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_car_model.status</code>. 车型状态，1：在售；0：停售
     */
    public Byte getStatus() {
        return (Byte) get(12);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_car_model.xj_count</code>. 询价次数
     */
    public void setXjCount(Integer value) {
        set(13, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_car_model.xj_count</code>. 询价次数
     */
    public Integer getXjCount() {
        return (Integer) get(13);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_car_model.attention_count</code>. 关注度
     */
    public void setAttentionCount(Integer value) {
        set(14, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_car_model.attention_count</code>. 关注度
     */
    public Integer getAttentionCount() {
        return (Integer) get(14);
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
    // Record15 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row15<String, String, String, Integer, String, String, Timestamp, String, Integer, Timestamp, String, String, Byte, Integer, Integer> fieldsRow() {
        return (Row15) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row15<String, String, String, Integer, String, String, Timestamp, String, Integer, Timestamp, String, String, Byte, Integer, Integer> valuesRow() {
        return (Row15) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field1() {
        return GchCarModel.GCH_CAR_MODEL.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return GchCarModel.GCH_CAR_MODEL.BRAND_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return GchCarModel.GCH_CAR_MODEL.CAR_TYPE_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field4() {
        return GchCarModel.GCH_CAR_MODEL.ACCESS_QUANTITY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return GchCarModel.GCH_CAR_MODEL.CAR_MODEL_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return GchCarModel.GCH_CAR_MODEL.IMGURL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field7() {
        return GchCarModel.GCH_CAR_MODEL.CREATETIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field8() {
        return GchCarModel.GCH_CAR_MODEL.CREATEUSER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field9() {
        return GchCarModel.GCH_CAR_MODEL.ISDELETE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field10() {
        return GchCarModel.GCH_CAR_MODEL.UPDATETIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field11() {
        return GchCarModel.GCH_CAR_MODEL.UPDATEUSER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field12() {
        return GchCarModel.GCH_CAR_MODEL.ALIF;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field13() {
        return GchCarModel.GCH_CAR_MODEL.STATUS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field14() {
        return GchCarModel.GCH_CAR_MODEL.XJ_COUNT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field15() {
        return GchCarModel.GCH_CAR_MODEL.ATTENTION_COUNT;
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
        return getBrandId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getCarTypeId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value4() {
        return getAccessQuantity();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getCarModelName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getImgurl();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value7() {
        return getCreatetime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value8() {
        return getCreateuser();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value9() {
        return getIsdelete();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value10() {
        return getUpdatetime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value11() {
        return getUpdateuser();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value12() {
        return getAlif();
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
    public Integer value14() {
        return getXjCount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value15() {
        return getAttentionCount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GchCarModelRecord value1(String value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GchCarModelRecord value2(String value) {
        setBrandId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GchCarModelRecord value3(String value) {
        setCarTypeId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GchCarModelRecord value4(Integer value) {
        setAccessQuantity(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GchCarModelRecord value5(String value) {
        setCarModelName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GchCarModelRecord value6(String value) {
        setImgurl(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GchCarModelRecord value7(Timestamp value) {
        setCreatetime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GchCarModelRecord value8(String value) {
        setCreateuser(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GchCarModelRecord value9(Integer value) {
        setIsdelete(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GchCarModelRecord value10(Timestamp value) {
        setUpdatetime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GchCarModelRecord value11(String value) {
        setUpdateuser(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GchCarModelRecord value12(String value) {
        setAlif(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GchCarModelRecord value13(Byte value) {
        setStatus(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GchCarModelRecord value14(Integer value) {
        setXjCount(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GchCarModelRecord value15(Integer value) {
        setAttentionCount(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GchCarModelRecord values(String value1, String value2, String value3, Integer value4, String value5, String value6, Timestamp value7, String value8, Integer value9, Timestamp value10, String value11, String value12, Byte value13, Integer value14, Integer value15) {
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
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached GchCarModelRecord
     */
    public GchCarModelRecord() {
        super(GchCarModel.GCH_CAR_MODEL);
    }

    /**
     * Create a detached, initialised GchCarModelRecord
     */
    public GchCarModelRecord(String id, String brandId, String carTypeId, Integer accessQuantity, String carModelName, String imgurl, Timestamp createtime, String createuser, Integer isdelete, Timestamp updatetime, String updateuser, String alif, Byte status, Integer xjCount, Integer attentionCount) {
        super(GchCarModel.GCH_CAR_MODEL);

        set(0, id);
        set(1, brandId);
        set(2, carTypeId);
        set(3, accessQuantity);
        set(4, carModelName);
        set(5, imgurl);
        set(6, createtime);
        set(7, createuser);
        set(8, isdelete);
        set(9, updatetime);
        set(10, updateuser);
        set(11, alif);
        set(12, status);
        set(13, xjCount);
        set(14, attentionCount);
    }
}
