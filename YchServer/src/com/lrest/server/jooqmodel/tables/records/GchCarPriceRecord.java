/**
 * This class is generated by jOOQ
 */
package com.lrest.server.jooqmodel.tables.records;


import com.lrest.server.jooqmodel.tables.GchCarPrice;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Record1;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 报价表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.2"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class GchCarPriceRecord extends UpdatableRecordImpl<GchCarPriceRecord> {

    private static final long serialVersionUID = 564290129;

    /**
     * Setter for <code>gouchehui2.0.gch_car_price.id</code>. 主键
     */
    public void setId(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_car_price.id</code>. 主键
     */
    public String getId() {
        return (String) get(0);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_car_price.exterior_color_id</code>. 外观颜色ID
     */
    public void setExteriorColorId(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_car_price.exterior_color_id</code>. 外观颜色ID
     */
    public String getExteriorColorId() {
        return (String) get(1);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_car_price.interior_color_id</code>. 内饰颜色ID
     */
    public void setInteriorColorId(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_car_price.interior_color_id</code>. 内饰颜色ID
     */
    public String getInteriorColorId() {
        return (String) get(2);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_car_price.user_id</code>. 所属用户
     */
    public void setUserId(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_car_price.user_id</code>. 所属用户
     */
    public String getUserId() {
        return (String) get(3);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_car_price.car_id</code>.
     */
    public void setCarId(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_car_price.car_id</code>.
     */
    public String getCarId() {
        return (String) get(4);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_car_price.is_xunjia</code>. 是否需要询价(1需要询价,2不需要询价)
     */
    public void setIsXunjia(Short value) {
        set(5, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_car_price.is_xunjia</code>. 是否需要询价(1需要询价,2不需要询价)
     */
    public Short getIsXunjia() {
        return (Short) get(5);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_car_price.access_quantity</code>. 访问量
     */
    public void setAccessQuantity(Integer value) {
        set(6, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_car_price.access_quantity</code>. 访问量
     */
    public Integer getAccessQuantity() {
        return (Integer) get(6);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_car_price.transactions_count</code>. 成交量
     */
    public void setTransactionsCount(Integer value) {
        set(7, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_car_price.transactions_count</code>. 成交量
     */
    public Integer getTransactionsCount() {
        return (Integer) get(7);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_car_price.asking_price_count</code>. 询价量
     */
    public void setAskingPriceCount(Integer value) {
        set(8, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_car_price.asking_price_count</code>. 询价量
     */
    public Integer getAskingPriceCount() {
        return (Integer) get(8);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_car_price.price</code>. 指导价
     */
    public void setPrice(Integer value) {
        set(9, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_car_price.price</code>. 指导价
     */
    public Integer getPrice() {
        return (Integer) get(9);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_car_price.discount</code>. 优惠
     */
    public void setDiscount(Integer value) {
        set(10, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_car_price.discount</code>. 优惠
     */
    public Integer getDiscount() {
        return (Integer) get(10);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_car_price.stock</code>. 库存
     */
    public void setStock(String value) {
        set(11, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_car_price.stock</code>. 库存
     */
    public String getStock() {
        return (String) get(11);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_car_price.onway</code>. 在途
     */
    public void setOnway(String value) {
        set(12, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_car_price.onway</code>. 在途
     */
    public String getOnway() {
        return (String) get(12);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_car_price.createtime</code>. 创建时间
     */
    public void setCreatetime(Timestamp value) {
        set(13, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_car_price.createtime</code>. 创建时间
     */
    public Timestamp getCreatetime() {
        return (Timestamp) get(13);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_car_price.createuser</code>. 创建人
     */
    public void setCreateuser(String value) {
        set(14, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_car_price.createuser</code>. 创建人
     */
    public String getCreateuser() {
        return (String) get(14);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_car_price.createip</code>. 创建者的ip
     */
    public void setCreateip(String value) {
        set(15, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_car_price.createip</code>. 创建者的ip
     */
    public String getCreateip() {
        return (String) get(15);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_car_price.isdelete</code>. 是否删除
     */
    public void setIsdelete(Integer value) {
        set(16, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_car_price.isdelete</code>. 是否删除
     */
    public Integer getIsdelete() {
        return (Integer) get(16);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_car_price.updatetime</code>. 修改时间
     */
    public void setUpdatetime(Timestamp value) {
        set(17, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_car_price.updatetime</code>. 修改时间
     */
    public Timestamp getUpdatetime() {
        return (Timestamp) get(17);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_car_price.updateuser</code>. 修改人
     */
    public void setUpdateuser(String value) {
        set(18, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_car_price.updateuser</code>. 修改人
     */
    public String getUpdateuser() {
        return (String) get(18);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_car_price.updateip</code>. 修改者的ip
     */
    public void setUpdateip(String value) {
        set(19, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_car_price.updateip</code>. 修改者的ip
     */
    public String getUpdateip() {
        return (String) get(19);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_car_price.low_price</code>. 报价
     */
    public void setLowPrice(Integer value) {
        set(20, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_car_price.low_price</code>. 报价
     */
    public Integer getLowPrice() {
        return (Integer) get(20);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_car_price.show_index</code>. 显示索引
     */
    public void setShowIndex(Integer value) {
        set(21, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_car_price.show_index</code>. 显示索引
     */
    public Integer getShowIndex() {
        return (Integer) get(21);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_car_price.month_status</code>. 月份（底价次数标志）
     */
    public void setMonthStatus(Integer value) {
        set(22, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_car_price.month_status</code>. 月份（底价次数标志）
     */
    public Integer getMonthStatus() {
        return (Integer) get(22);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_car_price.quarter_status</code>. 季度（底价次数标志）
     */
    public void setQuarterStatus(Integer value) {
        set(23, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_car_price.quarter_status</code>. 季度（底价次数标志）
     */
    public Integer getQuarterStatus() {
        return (Integer) get(23);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_car_price.status_time</code>. 成为底价车的时间
     */
    public void setStatusTime(Timestamp value) {
        set(24, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_car_price.status_time</code>. 成为底价车的时间
     */
    public Timestamp getStatusTime() {
        return (Timestamp) get(24);
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
     * Create a detached GchCarPriceRecord
     */
    public GchCarPriceRecord() {
        super(GchCarPrice.GCH_CAR_PRICE);
    }

    /**
     * Create a detached, initialised GchCarPriceRecord
     */
    public GchCarPriceRecord(String id, String exteriorColorId, String interiorColorId, String userId, String carId, Short isXunjia, Integer accessQuantity, Integer transactionsCount, Integer askingPriceCount, Integer price, Integer discount, String stock, String onway, Timestamp createtime, String createuser, String createip, Integer isdelete, Timestamp updatetime, String updateuser, String updateip, Integer lowPrice, Integer showIndex, Integer monthStatus, Integer quarterStatus, Timestamp statusTime) {
        super(GchCarPrice.GCH_CAR_PRICE);

        set(0, id);
        set(1, exteriorColorId);
        set(2, interiorColorId);
        set(3, userId);
        set(4, carId);
        set(5, isXunjia);
        set(6, accessQuantity);
        set(7, transactionsCount);
        set(8, askingPriceCount);
        set(9, price);
        set(10, discount);
        set(11, stock);
        set(12, onway);
        set(13, createtime);
        set(14, createuser);
        set(15, createip);
        set(16, isdelete);
        set(17, updatetime);
        set(18, updateuser);
        set(19, updateip);
        set(20, lowPrice);
        set(21, showIndex);
        set(22, monthStatus);
        set(23, quarterStatus);
        set(24, statusTime);
    }
}
