/**
 * This class is generated by jOOQ
 */
package com.lrest.server.jooqmodel.tables;


import com.lrest.server.jooqmodel.Gouchehui2_0;
import com.lrest.server.jooqmodel.Keys;
import com.lrest.server.jooqmodel.tables.records.GchCarLifeBbsRecord;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;


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
public class GchCarLifeBbs extends TableImpl<GchCarLifeBbsRecord> {

    private static final long serialVersionUID = 699831289;

    /**
     * The reference instance of <code>gouchehui2.0.gch_car_life_bbs</code>
     */
    public static final GchCarLifeBbs GCH_CAR_LIFE_BBS = new GchCarLifeBbs();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<GchCarLifeBbsRecord> getRecordType() {
        return GchCarLifeBbsRecord.class;
    }

    /**
     * The column <code>gouchehui2.0.gch_car_life_bbs.id</code>.
     */
    public final TableField<GchCarLifeBbsRecord, String> ID = createField("id", org.jooq.impl.SQLDataType.VARCHAR.length(32).nullable(false), this, "");

    /**
     * The column <code>gouchehui2.0.gch_car_life_bbs.type_id</code>. 帖子分类ID
     */
    public final TableField<GchCarLifeBbsRecord, String> TYPE_ID = createField("type_id", org.jooq.impl.SQLDataType.VARCHAR.length(32), this, "帖子分类ID");

    /**
     * The column <code>gouchehui2.0.gch_car_life_bbs.user_id</code>. 发帖人ID
     */
    public final TableField<GchCarLifeBbsRecord, String> USER_ID = createField("user_id", org.jooq.impl.SQLDataType.VARCHAR.length(32), this, "发帖人ID");

    /**
     * The column <code>gouchehui2.0.gch_car_life_bbs.title</code>. 标题
     */
    public final TableField<GchCarLifeBbsRecord, String> TITLE = createField("title", org.jooq.impl.SQLDataType.VARCHAR.length(100), this, "标题");

    /**
     * The column <code>gouchehui2.0.gch_car_life_bbs.summary</code>. 摘要
     */
    public final TableField<GchCarLifeBbsRecord, String> SUMMARY = createField("summary", org.jooq.impl.SQLDataType.CLOB, this, "摘要");

    /**
     * The column <code>gouchehui2.0.gch_car_life_bbs.contents</code>. 内容
     */
    public final TableField<GchCarLifeBbsRecord, String> CONTENTS = createField("contents", org.jooq.impl.SQLDataType.CLOB, this, "内容");

    /**
     * The column <code>gouchehui2.0.gch_car_life_bbs.image</code>. 帖子图像
     */
    public final TableField<GchCarLifeBbsRecord, String> IMAGE = createField("image", org.jooq.impl.SQLDataType.VARCHAR.length(255), this, "帖子图像");

    /**
     * The column <code>gouchehui2.0.gch_car_life_bbs.club_id</code>. 俱乐部ID
     */
    public final TableField<GchCarLifeBbsRecord, String> CLUB_ID = createField("club_id", org.jooq.impl.SQLDataType.VARCHAR.length(32), this, "俱乐部ID");

    /**
     * The column <code>gouchehui2.0.gch_car_life_bbs.car_id</code>. 车款id
     */
    public final TableField<GchCarLifeBbsRecord, String> CAR_ID = createField("car_id", org.jooq.impl.SQLDataType.VARCHAR.length(32), this, "车款id");

    /**
     * The column <code>gouchehui2.0.gch_car_life_bbs.final_price</code>. 落地价
     */
    public final TableField<GchCarLifeBbsRecord, Integer> FINAL_PRICE = createField("final_price", org.jooq.impl.SQLDataType.INTEGER, this, "落地价");

    /**
     * The column <code>gouchehui2.0.gch_car_life_bbs.car_own</code>. 车主
     */
    public final TableField<GchCarLifeBbsRecord, String> CAR_OWN = createField("car_own", org.jooq.impl.SQLDataType.VARCHAR.length(32), this, "车主");

    /**
     * The column <code>gouchehui2.0.gch_car_life_bbs.click_amount</code>. 点击数
     */
    public final TableField<GchCarLifeBbsRecord, Integer> CLICK_AMOUNT = createField("click_amount", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "点击数");

    /**
     * The column <code>gouchehui2.0.gch_car_life_bbs.comment_amount</code>. 评论数
     */
    public final TableField<GchCarLifeBbsRecord, Integer> COMMENT_AMOUNT = createField("comment_amount", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "评论数");

    /**
     * The column <code>gouchehui2.0.gch_car_life_bbs.thumbs_amount</code>. 点赞数
     */
    public final TableField<GchCarLifeBbsRecord, Integer> THUMBS_AMOUNT = createField("thumbs_amount", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "点赞数");

    /**
     * The column <code>gouchehui2.0.gch_car_life_bbs.display</code>. 是否显示（1：不显示，2：显示）
     */
    public final TableField<GchCarLifeBbsRecord, Short> DISPLAY = createField("display", org.jooq.impl.SQLDataType.SMALLINT.defaultValue(org.jooq.impl.DSL.inline("1", org.jooq.impl.SQLDataType.SMALLINT)), this, "是否显示（1：不显示，2：显示）");

    /**
     * The column <code>gouchehui2.0.gch_car_life_bbs.check</code>. 是否审核（1：未审核，2：审核通过，3：审核失败）
     */
    public final TableField<GchCarLifeBbsRecord, Short> CHECK = createField("check", org.jooq.impl.SQLDataType.SMALLINT.defaultValue(org.jooq.impl.DSL.inline("1", org.jooq.impl.SQLDataType.SMALLINT)), this, "是否审核（1：未审核，2：审核通过，3：审核失败）");

    /**
     * The column <code>gouchehui2.0.gch_car_life_bbs.check_fail</code>. 审核失败的原因
     */
    public final TableField<GchCarLifeBbsRecord, String> CHECK_FAIL = createField("check_fail", org.jooq.impl.SQLDataType.VARCHAR.length(255), this, "审核失败的原因");

    /**
     * The column <code>gouchehui2.0.gch_car_life_bbs.place_hot</code>. 热门推荐放置（1：未放置，2：放置）
     */
    public final TableField<GchCarLifeBbsRecord, Short> PLACE_HOT = createField("place_hot", org.jooq.impl.SQLDataType.SMALLINT.defaultValue(org.jooq.impl.DSL.inline("1", org.jooq.impl.SQLDataType.SMALLINT)), this, "热门推荐放置（1：未放置，2：放置）");

    /**
     * The column <code>gouchehui2.0.gch_car_life_bbs.place_index</code>. 帖子首页放置（1：未放置，2：放置）
     */
    public final TableField<GchCarLifeBbsRecord, Short> PLACE_INDEX = createField("place_index", org.jooq.impl.SQLDataType.SMALLINT.defaultValue(org.jooq.impl.DSL.inline("1", org.jooq.impl.SQLDataType.SMALLINT)), this, "帖子首页放置（1：未放置，2：放置）");

    /**
     * The column <code>gouchehui2.0.gch_car_life_bbs.createtime</code>. 创建时间
     */
    public final TableField<GchCarLifeBbsRecord, Timestamp> CREATETIME = createField("createtime", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "创建时间");

    /**
     * The column <code>gouchehui2.0.gch_car_life_bbs.createuser</code>. 创建人
     */
    public final TableField<GchCarLifeBbsRecord, String> CREATEUSER = createField("createuser", org.jooq.impl.SQLDataType.VARCHAR.length(32), this, "创建人");

    /**
     * The column <code>gouchehui2.0.gch_car_life_bbs.isdelete</code>. 是否删除
     */
    public final TableField<GchCarLifeBbsRecord, Integer> ISDELETE = createField("isdelete", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "是否删除");

    /**
     * The column <code>gouchehui2.0.gch_car_life_bbs.updatetime</code>. 修改时间
     */
    public final TableField<GchCarLifeBbsRecord, Timestamp> UPDATETIME = createField("updatetime", org.jooq.impl.SQLDataType.TIMESTAMP.defaultValue(org.jooq.impl.DSL.inline("0000-00-00 00:00:00", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "修改时间");

    /**
     * The column <code>gouchehui2.0.gch_car_life_bbs.updateuser</code>. 修改人
     */
    public final TableField<GchCarLifeBbsRecord, String> UPDATEUSER = createField("updateuser", org.jooq.impl.SQLDataType.VARCHAR.length(32), this, "修改人");

    /**
     * Create a <code>gouchehui2.0.gch_car_life_bbs</code> table reference
     */
    public GchCarLifeBbs() {
        this("gch_car_life_bbs", null);
    }

    /**
     * Create an aliased <code>gouchehui2.0.gch_car_life_bbs</code> table reference
     */
    public GchCarLifeBbs(String alias) {
        this(alias, GCH_CAR_LIFE_BBS);
    }

    private GchCarLifeBbs(String alias, Table<GchCarLifeBbsRecord> aliased) {
        this(alias, aliased, null);
    }

    private GchCarLifeBbs(String alias, Table<GchCarLifeBbsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "\r\n");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Gouchehui2_0.GOUCHEHUI2_0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<GchCarLifeBbsRecord> getPrimaryKey() {
        return Keys.KEY_GCH_CAR_LIFE_BBS_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<GchCarLifeBbsRecord>> getKeys() {
        return Arrays.<UniqueKey<GchCarLifeBbsRecord>>asList(Keys.KEY_GCH_CAR_LIFE_BBS_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GchCarLifeBbs as(String alias) {
        return new GchCarLifeBbs(alias, this);
    }

    /**
     * Rename this table
     */
    public GchCarLifeBbs rename(String name) {
        return new GchCarLifeBbs(name, null);
    }
}
