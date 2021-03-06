/**
 * This class is generated by jOOQ
 */
package com.lrest.server.jooqmodel.tables.records;


import com.lrest.server.jooqmodel.tables.GchSalesArea;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record11;
import org.jooq.Row11;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 销售区域表（底价车、特价车）
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.2"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class GchSalesAreaRecord extends UpdatableRecordImpl<GchSalesAreaRecord> implements Record11<String, String, Integer, String, String, Integer, Timestamp, String, Integer, Timestamp, String> {

    private static final long serialVersionUID = -1675192259;

    /**
     * Setter for <code>gouchehui2.0.gch_sales_area.id</code>. 主键
     */
    public void setId(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_sales_area.id</code>. 主键
     */
    public String getId() {
        return (String) get(0);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_sales_area.sales_area_name</code>. 销售区域名称
     */
    public void setSalesAreaName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_sales_area.sales_area_name</code>. 销售区域名称
     */
    public String getSalesAreaName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_sales_area.sales_area_level</code>. 销售区域权重 1、全国 2、省份 3、城市
     */
    public void setSalesAreaLevel(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_sales_area.sales_area_level</code>. 销售区域权重 1、全国 2、省份 3、城市
     */
    public Integer getSalesAreaLevel() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_sales_area.car_price_id</code>. 低价车id
     */
    public void setCarPriceId(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_sales_area.car_price_id</code>. 低价车id
     */
    public String getCarPriceId() {
        return (String) get(3);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_sales_area.car_special_id</code>. 特价车ID
     */
    public void setCarSpecialId(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_sales_area.car_special_id</code>. 特价车ID
     */
    public String getCarSpecialId() {
        return (String) get(4);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_sales_area.type</code>. 销售区域类别1 底价车，2特价车
     */
    public void setType(Integer value) {
        set(5, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_sales_area.type</code>. 销售区域类别1 底价车，2特价车
     */
    public Integer getType() {
        return (Integer) get(5);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_sales_area.createtime</code>. 创建时间
     */
    public void setCreatetime(Timestamp value) {
        set(6, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_sales_area.createtime</code>. 创建时间
     */
    public Timestamp getCreatetime() {
        return (Timestamp) get(6);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_sales_area.createuser</code>. 创建人
     */
    public void setCreateuser(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_sales_area.createuser</code>. 创建人
     */
    public String getCreateuser() {
        return (String) get(7);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_sales_area.isdelete</code>. 是否删除
     */
    public void setIsdelete(Integer value) {
        set(8, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_sales_area.isdelete</code>. 是否删除
     */
    public Integer getIsdelete() {
        return (Integer) get(8);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_sales_area.updatetime</code>. 修改时间
     */
    public void setUpdatetime(Timestamp value) {
        set(9, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_sales_area.updatetime</code>. 修改时间
     */
    public Timestamp getUpdatetime() {
        return (Timestamp) get(9);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_sales_area.updateuser</code>. 修改人
     */
    public void setUpdateuser(String value) {
        set(10, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_sales_area.updateuser</code>. 修改人
     */
    public String getUpdateuser() {
        return (String) get(10);
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
    // Record11 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row11<String, String, Integer, String, String, Integer, Timestamp, String, Integer, Timestamp, String> fieldsRow() {
        return (Row11) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row11<String, String, Integer, String, String, Integer, Timestamp, String, Integer, Timestamp, String> valuesRow() {
        return (Row11) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field1() {
        return GchSalesArea.GCH_SALES_AREA.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return GchSalesArea.GCH_SALES_AREA.SALES_AREA_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return GchSalesArea.GCH_SALES_AREA.SALES_AREA_LEVEL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return GchSalesArea.GCH_SALES_AREA.CAR_PRICE_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return GchSalesArea.GCH_SALES_AREA.CAR_SPECIAL_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field6() {
        return GchSalesArea.GCH_SALES_AREA.TYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field7() {
        return GchSalesArea.GCH_SALES_AREA.CREATETIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field8() {
        return GchSalesArea.GCH_SALES_AREA.CREATEUSER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field9() {
        return GchSalesArea.GCH_SALES_AREA.ISDELETE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field10() {
        return GchSalesArea.GCH_SALES_AREA.UPDATETIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field11() {
        return GchSalesArea.GCH_SALES_AREA.UPDATEUSER;
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
        return getSalesAreaName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value3() {
        return getSalesAreaLevel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getCarPriceId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getCarSpecialId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value6() {
        return getType();
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
    public GchSalesAreaRecord value1(String value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GchSalesAreaRecord value2(String value) {
        setSalesAreaName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GchSalesAreaRecord value3(Integer value) {
        setSalesAreaLevel(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GchSalesAreaRecord value4(String value) {
        setCarPriceId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GchSalesAreaRecord value5(String value) {
        setCarSpecialId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GchSalesAreaRecord value6(Integer value) {
        setType(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GchSalesAreaRecord value7(Timestamp value) {
        setCreatetime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GchSalesAreaRecord value8(String value) {
        setCreateuser(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GchSalesAreaRecord value9(Integer value) {
        setIsdelete(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GchSalesAreaRecord value10(Timestamp value) {
        setUpdatetime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GchSalesAreaRecord value11(String value) {
        setUpdateuser(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GchSalesAreaRecord values(String value1, String value2, Integer value3, String value4, String value5, Integer value6, Timestamp value7, String value8, Integer value9, Timestamp value10, String value11) {
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
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached GchSalesAreaRecord
     */
    public GchSalesAreaRecord() {
        super(GchSalesArea.GCH_SALES_AREA);
    }

    /**
     * Create a detached, initialised GchSalesAreaRecord
     */
    public GchSalesAreaRecord(String id, String salesAreaName, Integer salesAreaLevel, String carPriceId, String carSpecialId, Integer type, Timestamp createtime, String createuser, Integer isdelete, Timestamp updatetime, String updateuser) {
        super(GchSalesArea.GCH_SALES_AREA);

        set(0, id);
        set(1, salesAreaName);
        set(2, salesAreaLevel);
        set(3, carPriceId);
        set(4, carSpecialId);
        set(5, type);
        set(6, createtime);
        set(7, createuser);
        set(8, isdelete);
        set(9, updatetime);
        set(10, updateuser);
    }
}
