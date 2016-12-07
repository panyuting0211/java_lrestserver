/**
 * This class is generated by jOOQ
 */
package com.lrest.server.jooqmodel.tables.records;


import com.lrest.server.jooqmodel.tables.GchProvince;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record9;
import org.jooq.Row9;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 4S店所在省份
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.2"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class GchProvinceRecord extends UpdatableRecordImpl<GchProvinceRecord> implements Record9<String, String, String, Timestamp, String, Integer, Timestamp, String, Byte> {

    private static final long serialVersionUID = -152775511;

    /**
     * Setter for <code>gouchehui2.0.gch_province.id</code>. 主键
     */
    public void setId(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_province.id</code>. 主键
     */
    public String getId() {
        return (String) get(0);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_province.area_id</code>. 区域ID
     */
    public void setAreaId(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_province.area_id</code>. 区域ID
     */
    public String getAreaId() {
        return (String) get(1);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_province.province_name</code>. 省份名称
     */
    public void setProvinceName(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_province.province_name</code>. 省份名称
     */
    public String getProvinceName() {
        return (String) get(2);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_province.createtime</code>. 创建时间
     */
    public void setCreatetime(Timestamp value) {
        set(3, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_province.createtime</code>. 创建时间
     */
    public Timestamp getCreatetime() {
        return (Timestamp) get(3);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_province.createuser</code>. 创建人
     */
    public void setCreateuser(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_province.createuser</code>. 创建人
     */
    public String getCreateuser() {
        return (String) get(4);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_province.isdelete</code>. 是否删除
     */
    public void setIsdelete(Integer value) {
        set(5, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_province.isdelete</code>. 是否删除
     */
    public Integer getIsdelete() {
        return (Integer) get(5);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_province.updatetime</code>. 修改时间
     */
    public void setUpdatetime(Timestamp value) {
        set(6, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_province.updatetime</code>. 修改时间
     */
    public Timestamp getUpdatetime() {
        return (Timestamp) get(6);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_province.updateuser</code>. 修改人
     */
    public void setUpdateuser(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_province.updateuser</code>. 修改人
     */
    public String getUpdateuser() {
        return (String) get(7);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_province.status</code>. 是否显示
     */
    public void setStatus(Byte value) {
        set(8, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_province.status</code>. 是否显示
     */
    public Byte getStatus() {
        return (Byte) get(8);
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
    // Record9 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row9<String, String, String, Timestamp, String, Integer, Timestamp, String, Byte> fieldsRow() {
        return (Row9) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row9<String, String, String, Timestamp, String, Integer, Timestamp, String, Byte> valuesRow() {
        return (Row9) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field1() {
        return GchProvince.GCH_PROVINCE.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return GchProvince.GCH_PROVINCE.AREA_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return GchProvince.GCH_PROVINCE.PROVINCE_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field4() {
        return GchProvince.GCH_PROVINCE.CREATETIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return GchProvince.GCH_PROVINCE.CREATEUSER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field6() {
        return GchProvince.GCH_PROVINCE.ISDELETE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field7() {
        return GchProvince.GCH_PROVINCE.UPDATETIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field8() {
        return GchProvince.GCH_PROVINCE.UPDATEUSER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field9() {
        return GchProvince.GCH_PROVINCE.STATUS;
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
        return getAreaId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getProvinceName();
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
    public Byte value9() {
        return getStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GchProvinceRecord value1(String value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GchProvinceRecord value2(String value) {
        setAreaId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GchProvinceRecord value3(String value) {
        setProvinceName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GchProvinceRecord value4(Timestamp value) {
        setCreatetime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GchProvinceRecord value5(String value) {
        setCreateuser(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GchProvinceRecord value6(Integer value) {
        setIsdelete(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GchProvinceRecord value7(Timestamp value) {
        setUpdatetime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GchProvinceRecord value8(String value) {
        setUpdateuser(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GchProvinceRecord value9(Byte value) {
        setStatus(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GchProvinceRecord values(String value1, String value2, String value3, Timestamp value4, String value5, Integer value6, Timestamp value7, String value8, Byte value9) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached GchProvinceRecord
     */
    public GchProvinceRecord() {
        super(GchProvince.GCH_PROVINCE);
    }

    /**
     * Create a detached, initialised GchProvinceRecord
     */
    public GchProvinceRecord(String id, String areaId, String provinceName, Timestamp createtime, String createuser, Integer isdelete, Timestamp updatetime, String updateuser, Byte status) {
        super(GchProvince.GCH_PROVINCE);

        set(0, id);
        set(1, areaId);
        set(2, provinceName);
        set(3, createtime);
        set(4, createuser);
        set(5, isdelete);
        set(6, updatetime);
        set(7, updateuser);
        set(8, status);
    }
}