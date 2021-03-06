/**
 * This class is generated by jOOQ
 */
package com.lrest.server.jooqmodel.tables.records;


import com.lrest.server.jooqmodel.tables.GchUserAttentionCarModel;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record8;
import org.jooq.Row8;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 用户关注车型表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.2"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class GchUserAttentionCarModelRecord extends UpdatableRecordImpl<GchUserAttentionCarModelRecord> implements Record8<String, String, String, Timestamp, String, Integer, Timestamp, String> {

    private static final long serialVersionUID = 617796181;

    /**
     * Setter for <code>gouchehui2.0.gch_user_attention_car_model.id</code>. 主键
     */
    public void setId(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_user_attention_car_model.id</code>. 主键
     */
    public String getId() {
        return (String) get(0);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_user_attention_car_model.user_id</code>. 关注用户
     */
    public void setUserId(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_user_attention_car_model.user_id</code>. 关注用户
     */
    public String getUserId() {
        return (String) get(1);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_user_attention_car_model.car_model_id</code>. 所属车型
     */
    public void setCarModelId(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_user_attention_car_model.car_model_id</code>. 所属车型
     */
    public String getCarModelId() {
        return (String) get(2);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_user_attention_car_model.createtime</code>. 创建时间(关注时间)
     */
    public void setCreatetime(Timestamp value) {
        set(3, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_user_attention_car_model.createtime</code>. 创建时间(关注时间)
     */
    public Timestamp getCreatetime() {
        return (Timestamp) get(3);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_user_attention_car_model.createuser</code>. 创建人
     */
    public void setCreateuser(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_user_attention_car_model.createuser</code>. 创建人
     */
    public String getCreateuser() {
        return (String) get(4);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_user_attention_car_model.isdelete</code>. 是否删除
     */
    public void setIsdelete(Integer value) {
        set(5, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_user_attention_car_model.isdelete</code>. 是否删除
     */
    public Integer getIsdelete() {
        return (Integer) get(5);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_user_attention_car_model.updatetime</code>. 修改时间
     */
    public void setUpdatetime(Timestamp value) {
        set(6, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_user_attention_car_model.updatetime</code>. 修改时间
     */
    public Timestamp getUpdatetime() {
        return (Timestamp) get(6);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_user_attention_car_model.updateuser</code>. 修改人
     */
    public void setUpdateuser(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_user_attention_car_model.updateuser</code>. 修改人
     */
    public String getUpdateuser() {
        return (String) get(7);
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
    // Record8 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row8<String, String, String, Timestamp, String, Integer, Timestamp, String> fieldsRow() {
        return (Row8) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row8<String, String, String, Timestamp, String, Integer, Timestamp, String> valuesRow() {
        return (Row8) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field1() {
        return GchUserAttentionCarModel.GCH_USER_ATTENTION_CAR_MODEL.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return GchUserAttentionCarModel.GCH_USER_ATTENTION_CAR_MODEL.USER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return GchUserAttentionCarModel.GCH_USER_ATTENTION_CAR_MODEL.CAR_MODEL_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field4() {
        return GchUserAttentionCarModel.GCH_USER_ATTENTION_CAR_MODEL.CREATETIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return GchUserAttentionCarModel.GCH_USER_ATTENTION_CAR_MODEL.CREATEUSER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field6() {
        return GchUserAttentionCarModel.GCH_USER_ATTENTION_CAR_MODEL.ISDELETE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field7() {
        return GchUserAttentionCarModel.GCH_USER_ATTENTION_CAR_MODEL.UPDATETIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field8() {
        return GchUserAttentionCarModel.GCH_USER_ATTENTION_CAR_MODEL.UPDATEUSER;
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
        return getCarModelId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value4() {
        return getCreatetime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getCreateuser();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value6() {
        return getIsdelete();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value7() {
        return getUpdatetime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value8() {
        return getUpdateuser();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GchUserAttentionCarModelRecord value1(String value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GchUserAttentionCarModelRecord value2(String value) {
        setUserId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GchUserAttentionCarModelRecord value3(String value) {
        setCarModelId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GchUserAttentionCarModelRecord value4(Timestamp value) {
        setCreatetime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GchUserAttentionCarModelRecord value5(String value) {
        setCreateuser(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GchUserAttentionCarModelRecord value6(Integer value) {
        setIsdelete(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GchUserAttentionCarModelRecord value7(Timestamp value) {
        setUpdatetime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GchUserAttentionCarModelRecord value8(String value) {
        setUpdateuser(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GchUserAttentionCarModelRecord values(String value1, String value2, String value3, Timestamp value4, String value5, Integer value6, Timestamp value7, String value8) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached GchUserAttentionCarModelRecord
     */
    public GchUserAttentionCarModelRecord() {
        super(GchUserAttentionCarModel.GCH_USER_ATTENTION_CAR_MODEL);
    }

    /**
     * Create a detached, initialised GchUserAttentionCarModelRecord
     */
    public GchUserAttentionCarModelRecord(String id, String userId, String carModelId, Timestamp createtime, String createuser, Integer isdelete, Timestamp updatetime, String updateuser) {
        super(GchUserAttentionCarModel.GCH_USER_ATTENTION_CAR_MODEL);

        set(0, id);
        set(1, userId);
        set(2, carModelId);
        set(3, createtime);
        set(4, createuser);
        set(5, isdelete);
        set(6, updatetime);
        set(7, updateuser);
    }
}
