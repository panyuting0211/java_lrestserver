/**
 * This class is generated by jOOQ
 */
package com.lrest.server.jooqmodel.tables.records;


import com.lrest.server.jooqmodel.tables.GchAuthCode;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record6;
import org.jooq.Row6;
import org.jooq.impl.TableRecordImpl;


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
public class GchAuthCodeRecord extends TableRecordImpl<GchAuthCodeRecord> implements Record6<String, Integer, String, String, Timestamp, Timestamp> {

    private static final long serialVersionUID = -765519475;

    /**
     * Setter for <code>gouchehui2.0.gch_auth_code.id</code>.
     */
    public void setId(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_auth_code.id</code>.
     */
    public String getId() {
        return (String) get(0);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_auth_code.type</code>. 1. 发送短信验证
     */
    public void setType(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_auth_code.type</code>. 1. 发送短信验证
     */
    public Integer getType() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_auth_code.cellphone</code>. 手机号
     */
    public void setCellphone(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_auth_code.cellphone</code>. 手机号
     */
    public String getCellphone() {
        return (String) get(2);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_auth_code.sign_in_no</code>. 注册码
     */
    public void setSignInNo(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_auth_code.sign_in_no</code>. 注册码
     */
    public String getSignInNo() {
        return (String) get(3);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_auth_code.createtime</code>. 创建时间
     */
    public void setCreatetime(Timestamp value) {
        set(4, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_auth_code.createtime</code>. 创建时间
     */
    public Timestamp getCreatetime() {
        return (Timestamp) get(4);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_auth_code.expiration_time</code>. 失效时间
     */
    public void setExpirationTime(Timestamp value) {
        set(5, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_auth_code.expiration_time</code>. 失效时间
     */
    public Timestamp getExpirationTime() {
        return (Timestamp) get(5);
    }

    // -------------------------------------------------------------------------
    // Record6 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<String, Integer, String, String, Timestamp, Timestamp> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<String, Integer, String, String, Timestamp, Timestamp> valuesRow() {
        return (Row6) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field1() {
        return GchAuthCode.GCH_AUTH_CODE.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return GchAuthCode.GCH_AUTH_CODE.TYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return GchAuthCode.GCH_AUTH_CODE.CELLPHONE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return GchAuthCode.GCH_AUTH_CODE.SIGN_IN_NO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field5() {
        return GchAuthCode.GCH_AUTH_CODE.CREATETIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field6() {
        return GchAuthCode.GCH_AUTH_CODE.EXPIRATION_TIME;
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
    public Integer value2() {
        return getType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getCellphone();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getSignInNo();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value5() {
        return getCreatetime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value6() {
        return getExpirationTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GchAuthCodeRecord value1(String value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GchAuthCodeRecord value2(Integer value) {
        setType(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GchAuthCodeRecord value3(String value) {
        setCellphone(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GchAuthCodeRecord value4(String value) {
        setSignInNo(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GchAuthCodeRecord value5(Timestamp value) {
        setCreatetime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GchAuthCodeRecord value6(Timestamp value) {
        setExpirationTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GchAuthCodeRecord values(String value1, Integer value2, String value3, String value4, Timestamp value5, Timestamp value6) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached GchAuthCodeRecord
     */
    public GchAuthCodeRecord() {
        super(GchAuthCode.GCH_AUTH_CODE);
    }

    /**
     * Create a detached, initialised GchAuthCodeRecord
     */
    public GchAuthCodeRecord(String id, Integer type, String cellphone, String signInNo, Timestamp createtime, Timestamp expirationTime) {
        super(GchAuthCode.GCH_AUTH_CODE);

        set(0, id);
        set(1, type);
        set(2, cellphone);
        set(3, signInNo);
        set(4, createtime);
        set(5, expirationTime);
    }
}