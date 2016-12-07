/**
 * This class is generated by jOOQ
 */
package com.lrest.server.jooqmodel.tables.records;


import com.lrest.server.jooqmodel.tables.GchCarLifeBbs;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Record1;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.2"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class GchCarLifeBbsRecord extends UpdatableRecordImpl<GchCarLifeBbsRecord> {

    private static final long serialVersionUID = -717847867;

    /**
     * Setter for <code>gouchehui2.0.gch_car_life_bbs.id</code>.
     */
    public void setId(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_car_life_bbs.id</code>.
     */
    public String getId() {
        return (String) get(0);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_car_life_bbs.type_id</code>. 帖子分类ID
     */
    public void setTypeId(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_car_life_bbs.type_id</code>. 帖子分类ID
     */
    public String getTypeId() {
        return (String) get(1);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_car_life_bbs.user_id</code>. 发帖人ID
     */
    public void setUserId(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_car_life_bbs.user_id</code>. 发帖人ID
     */
    public String getUserId() {
        return (String) get(2);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_car_life_bbs.title</code>. 标题
     */
    public void setTitle(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_car_life_bbs.title</code>. 标题
     */
    public String getTitle() {
        return (String) get(3);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_car_life_bbs.summary</code>. 摘要
     */
    public void setSummary(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_car_life_bbs.summary</code>. 摘要
     */
    public String getSummary() {
        return (String) get(4);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_car_life_bbs.contents</code>. 内容
     */
    public void setContents(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_car_life_bbs.contents</code>. 内容
     */
    public String getContents() {
        return (String) get(5);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_car_life_bbs.image</code>. 帖子图像
     */
    public void setImage(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_car_life_bbs.image</code>. 帖子图像
     */
    public String getImage() {
        return (String) get(6);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_car_life_bbs.club_id</code>. 俱乐部ID
     */
    public void setClubId(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_car_life_bbs.club_id</code>. 俱乐部ID
     */
    public String getClubId() {
        return (String) get(7);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_car_life_bbs.car_id</code>. 车款id
     */
    public void setCarId(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_car_life_bbs.car_id</code>. 车款id
     */
    public String getCarId() {
        return (String) get(8);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_car_life_bbs.final_price</code>. 落地价
     */
    public void setFinalPrice(Integer value) {
        set(9, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_car_life_bbs.final_price</code>. 落地价
     */
    public Integer getFinalPrice() {
        return (Integer) get(9);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_car_life_bbs.car_own</code>. 车主
     */
    public void setCarOwn(String value) {
        set(10, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_car_life_bbs.car_own</code>. 车主
     */
    public String getCarOwn() {
        return (String) get(10);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_car_life_bbs.click_amount</code>. 点击数
     */
    public void setClickAmount(Integer value) {
        set(11, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_car_life_bbs.click_amount</code>. 点击数
     */
    public Integer getClickAmount() {
        return (Integer) get(11);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_car_life_bbs.comment_amount</code>. 评论数
     */
    public void setCommentAmount(Integer value) {
        set(12, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_car_life_bbs.comment_amount</code>. 评论数
     */
    public Integer getCommentAmount() {
        return (Integer) get(12);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_car_life_bbs.thumbs_amount</code>. 点赞数
     */
    public void setThumbsAmount(Integer value) {
        set(13, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_car_life_bbs.thumbs_amount</code>. 点赞数
     */
    public Integer getThumbsAmount() {
        return (Integer) get(13);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_car_life_bbs.display</code>. 是否显示（1：不显示，2：显示）
     */
    public void setDisplay(Short value) {
        set(14, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_car_life_bbs.display</code>. 是否显示（1：不显示，2：显示）
     */
    public Short getDisplay() {
        return (Short) get(14);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_car_life_bbs.check</code>. 是否审核（1：未审核，2：审核通过，3：审核失败）
     */
    public void setCheck(Short value) {
        set(15, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_car_life_bbs.check</code>. 是否审核（1：未审核，2：审核通过，3：审核失败）
     */
    public Short getCheck() {
        return (Short) get(15);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_car_life_bbs.check_fail</code>. 审核失败的原因
     */
    public void setCheckFail(String value) {
        set(16, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_car_life_bbs.check_fail</code>. 审核失败的原因
     */
    public String getCheckFail() {
        return (String) get(16);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_car_life_bbs.place_hot</code>. 热门推荐放置（1：未放置，2：放置）
     */
    public void setPlaceHot(Short value) {
        set(17, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_car_life_bbs.place_hot</code>. 热门推荐放置（1：未放置，2：放置）
     */
    public Short getPlaceHot() {
        return (Short) get(17);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_car_life_bbs.place_index</code>. 帖子首页放置（1：未放置，2：放置）
     */
    public void setPlaceIndex(Short value) {
        set(18, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_car_life_bbs.place_index</code>. 帖子首页放置（1：未放置，2：放置）
     */
    public Short getPlaceIndex() {
        return (Short) get(18);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_car_life_bbs.createtime</code>. 创建时间
     */
    public void setCreatetime(Timestamp value) {
        set(19, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_car_life_bbs.createtime</code>. 创建时间
     */
    public Timestamp getCreatetime() {
        return (Timestamp) get(19);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_car_life_bbs.createuser</code>. 创建人
     */
    public void setCreateuser(String value) {
        set(20, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_car_life_bbs.createuser</code>. 创建人
     */
    public String getCreateuser() {
        return (String) get(20);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_car_life_bbs.isdelete</code>. 是否删除
     */
    public void setIsdelete(Integer value) {
        set(21, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_car_life_bbs.isdelete</code>. 是否删除
     */
    public Integer getIsdelete() {
        return (Integer) get(21);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_car_life_bbs.updatetime</code>. 修改时间
     */
    public void setUpdatetime(Timestamp value) {
        set(22, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_car_life_bbs.updatetime</code>. 修改时间
     */
    public Timestamp getUpdatetime() {
        return (Timestamp) get(22);
    }

    /**
     * Setter for <code>gouchehui2.0.gch_car_life_bbs.updateuser</code>. 修改人
     */
    public void setUpdateuser(String value) {
        set(23, value);
    }

    /**
     * Getter for <code>gouchehui2.0.gch_car_life_bbs.updateuser</code>. 修改人
     */
    public String getUpdateuser() {
        return (String) get(23);
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
     * Create a detached GchCarLifeBbsRecord
     */
    public GchCarLifeBbsRecord() {
        super(GchCarLifeBbs.GCH_CAR_LIFE_BBS);
    }

    /**
     * Create a detached, initialised GchCarLifeBbsRecord
     */
    public GchCarLifeBbsRecord(String id, String typeId, String userId, String title, String summary, String contents, String image, String clubId, String carId, Integer finalPrice, String carOwn, Integer clickAmount, Integer commentAmount, Integer thumbsAmount, Short display, Short check, String checkFail, Short placeHot, Short placeIndex, Timestamp createtime, String createuser, Integer isdelete, Timestamp updatetime, String updateuser) {
        super(GchCarLifeBbs.GCH_CAR_LIFE_BBS);

        set(0, id);
        set(1, typeId);
        set(2, userId);
        set(3, title);
        set(4, summary);
        set(5, contents);
        set(6, image);
        set(7, clubId);
        set(8, carId);
        set(9, finalPrice);
        set(10, carOwn);
        set(11, clickAmount);
        set(12, commentAmount);
        set(13, thumbsAmount);
        set(14, display);
        set(15, check);
        set(16, checkFail);
        set(17, placeHot);
        set(18, placeIndex);
        set(19, createtime);
        set(20, createuser);
        set(21, isdelete);
        set(22, updatetime);
        set(23, updateuser);
    }
}