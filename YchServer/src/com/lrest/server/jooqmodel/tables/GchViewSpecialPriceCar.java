/**
 * This class is generated by jOOQ
 */
package com.lrest.server.jooqmodel.tables;


import com.lrest.server.jooqmodel.Gouchehui2_0;
import com.lrest.server.jooqmodel.tables.records.GchViewSpecialPriceCarRecord;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.TableImpl;


/**
 * VIEW
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.2"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class GchViewSpecialPriceCar extends TableImpl<GchViewSpecialPriceCarRecord> {

    private static final long serialVersionUID = 210857959;

    /**
     * The reference instance of <code>gouchehui2.0.gch_view_special_price_car</code>
     */
    public static final GchViewSpecialPriceCar GCH_VIEW_SPECIAL_PRICE_CAR = new GchViewSpecialPriceCar();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<GchViewSpecialPriceCarRecord> getRecordType() {
        return GchViewSpecialPriceCarRecord.class;
    }

    /**
     * The column <code>gouchehui2.0.gch_view_special_price_car.id</code>. 主键
     */
    public final TableField<GchViewSpecialPriceCarRecord, String> ID = createField("id", org.jooq.impl.SQLDataType.VARCHAR.length(32).nullable(false), this, "主键");

    /**
     * The column <code>gouchehui2.0.gch_view_special_price_car.user_id</code>. 关联用户ID
     */
    public final TableField<GchViewSpecialPriceCarRecord, String> USER_ID = createField("user_id", org.jooq.impl.SQLDataType.VARCHAR.length(32).nullable(false), this, "关联用户ID");

    /**
     * The column <code>gouchehui2.0.gch_view_special_price_car.interior_color_id</code>. 内饰颜色ID
     */
    public final TableField<GchViewSpecialPriceCarRecord, String> INTERIOR_COLOR_ID = createField("interior_color_id", org.jooq.impl.SQLDataType.VARCHAR.length(32), this, "内饰颜色ID");

    /**
     * The column <code>gouchehui2.0.gch_view_special_price_car.exterior_color_id</code>. 外观颜色ID
     */
    public final TableField<GchViewSpecialPriceCarRecord, String> EXTERIOR_COLOR_ID = createField("exterior_color_id", org.jooq.impl.SQLDataType.VARCHAR.length(32).nullable(false), this, "外观颜色ID");

    /**
     * The column <code>gouchehui2.0.gch_view_special_price_car.car_image</code>. 车款图片
     */
    public final TableField<GchViewSpecialPriceCarRecord, String> CAR_IMAGE = createField("car_image", org.jooq.impl.SQLDataType.VARCHAR.length(500).nullable(false), this, "车款图片");

    /**
     * The column <code>gouchehui2.0.gch_view_special_price_car.car_id</code>. 汽车ID
     */
    public final TableField<GchViewSpecialPriceCarRecord, String> CAR_ID = createField("car_id", org.jooq.impl.SQLDataType.VARCHAR.length(32).nullable(false), this, "汽车ID");

    /**
     * The column <code>gouchehui2.0.gch_view_special_price_car.price</code>. 价格
     */
    public final TableField<GchViewSpecialPriceCarRecord, Integer> PRICE = createField("price", org.jooq.impl.SQLDataType.INTEGER, this, "价格");

    /**
     * The column <code>gouchehui2.0.gch_view_special_price_car.special_price</code>. 特价
     */
    public final TableField<GchViewSpecialPriceCarRecord, Integer> SPECIAL_PRICE = createField("special_price", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "特价");

    /**
     * The column <code>gouchehui2.0.gch_view_special_price_car.start_date</code>. 开始时间
     */
    public final TableField<GchViewSpecialPriceCarRecord, Timestamp> START_DATE = createField("start_date", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "开始时间");

    /**
     * The column <code>gouchehui2.0.gch_view_special_price_car.end_date</code>. 结束时间
     */
    public final TableField<GchViewSpecialPriceCarRecord, Timestamp> END_DATE = createField("end_date", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "结束时间");

    /**
     * The column <code>gouchehui2.0.gch_view_special_price_car.number</code>. 活动数量
     */
    public final TableField<GchViewSpecialPriceCarRecord, Integer> NUMBER = createField("number", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "活动数量");

    /**
     * The column <code>gouchehui2.0.gch_view_special_price_car.description</code>. 活动说明
     */
    public final TableField<GchViewSpecialPriceCarRecord, String> DESCRIPTION = createField("description", org.jooq.impl.SQLDataType.CLOB, this, "活动说明");

    /**
     * The column <code>gouchehui2.0.gch_view_special_price_car.attention_count</code>. 关注度
     */
    public final TableField<GchViewSpecialPriceCarRecord, Integer> ATTENTION_COUNT = createField("attention_count", org.jooq.impl.SQLDataType.INTEGER, this, "关注度");

    /**
     * The column <code>gouchehui2.0.gch_view_special_price_car.isdelete</code>. 是否删除
     */
    public final TableField<GchViewSpecialPriceCarRecord, Integer> ISDELETE = createField("isdelete", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "是否删除");

    /**
     * The column <code>gouchehui2.0.gch_view_special_price_car.exterior_color_name</code>. 名称
     */
    public final TableField<GchViewSpecialPriceCarRecord, String> EXTERIOR_COLOR_NAME = createField("exterior_color_name", org.jooq.impl.SQLDataType.VARCHAR.length(100), this, "名称");

    /**
     * The column <code>gouchehui2.0.gch_view_special_price_car.car_name</code>. 名称
     */
    public final TableField<GchViewSpecialPriceCarRecord, String> CAR_NAME = createField("car_name", org.jooq.impl.SQLDataType.VARCHAR.length(100), this, "名称");

    /**
     * The column <code>gouchehui2.0.gch_view_special_price_car.brand_name</code>. 名称
     */
    public final TableField<GchViewSpecialPriceCarRecord, String> BRAND_NAME = createField("brand_name", org.jooq.impl.SQLDataType.VARCHAR.length(100), this, "名称");

    /**
     * The column <code>gouchehui2.0.gch_view_special_price_car.car_model_name</code>. 名称
     */
    public final TableField<GchViewSpecialPriceCarRecord, String> CAR_MODEL_NAME = createField("car_model_name", org.jooq.impl.SQLDataType.VARCHAR.length(100), this, "名称");

    /**
     * The column <code>gouchehui2.0.gch_view_special_price_car.brand_id</code>. 所属品牌
     */
    public final TableField<GchViewSpecialPriceCarRecord, String> BRAND_ID = createField("brand_id", org.jooq.impl.SQLDataType.VARCHAR.length(32), this, "所属品牌");

    /**
     * The column <code>gouchehui2.0.gch_view_special_price_car.car_model_id</code>. 所属车型
     */
    public final TableField<GchViewSpecialPriceCarRecord, String> CAR_MODEL_ID = createField("car_model_id", org.jooq.impl.SQLDataType.VARCHAR.length(32), this, "所属车型");

    /**
     * The column <code>gouchehui2.0.gch_view_special_price_car.interior_color_name</code>. 名称
     */
    public final TableField<GchViewSpecialPriceCarRecord, String> INTERIOR_COLOR_NAME = createField("interior_color_name", org.jooq.impl.SQLDataType.VARCHAR.length(100), this, "名称");

    /**
     * The column <code>gouchehui2.0.gch_view_special_price_car.car_status</code>. 车款状态 1：在售；0：停售。
     */
    public final TableField<GchViewSpecialPriceCarRecord, Byte> CAR_STATUS = createField("car_status", org.jooq.impl.SQLDataType.TINYINT.defaultValue(org.jooq.impl.DSL.inline("1", org.jooq.impl.SQLDataType.TINYINT)), this, "车款状态 1：在售；0：停售。");

    /**
     * The column <code>gouchehui2.0.gch_view_special_price_car.brand_alif</code>.
     */
    public final TableField<GchViewSpecialPriceCarRecord, String> BRAND_ALIF = createField("brand_alif", org.jooq.impl.SQLDataType.VARCHAR.length(2), this, "");

    /**
     * The column <code>gouchehui2.0.gch_view_special_price_car.auth_price</code>. 官方指导价
     */
    public final TableField<GchViewSpecialPriceCarRecord, Integer> AUTH_PRICE = createField("auth_price", org.jooq.impl.SQLDataType.INTEGER, this, "官方指导价");

    /**
     * The column <code>gouchehui2.0.gch_view_special_price_car.city_name</code>. 城市名称
     */
    public final TableField<GchViewSpecialPriceCarRecord, String> CITY_NAME = createField("city_name", org.jooq.impl.SQLDataType.VARCHAR.length(20), this, "城市名称");

    /**
     * The column <code>gouchehui2.0.gch_view_special_price_car.province_id</code>. 所属省份
     */
    public final TableField<GchViewSpecialPriceCarRecord, String> PROVINCE_ID = createField("province_id", org.jooq.impl.SQLDataType.VARCHAR.length(32), this, "所属省份");

    /**
     * The column <code>gouchehui2.0.gch_view_special_price_car.user_name</code>. 登录名
     */
    public final TableField<GchViewSpecialPriceCarRecord, String> USER_NAME = createField("user_name", org.jooq.impl.SQLDataType.VARCHAR.length(100), this, "登录名");

    /**
     * The column <code>gouchehui2.0.gch_view_special_price_car.name_4s</code>. 4S店名称
     */
    public final TableField<GchViewSpecialPriceCarRecord, String> NAME_4S = createField("name_4s", org.jooq.impl.SQLDataType.VARCHAR.length(50), this, "4S店名称");

    /**
     * The column <code>gouchehui2.0.gch_view_special_price_car.brand_4s</code>. 4S店主营品牌
     */
    public final TableField<GchViewSpecialPriceCarRecord, String> BRAND_4S = createField("brand_4s", org.jooq.impl.SQLDataType.VARCHAR.length(32), this, "4S店主营品牌");

    /**
     * The column <code>gouchehui2.0.gch_view_special_price_car.city_id</code>. 城市ID
     */
    public final TableField<GchViewSpecialPriceCarRecord, String> CITY_ID = createField("city_id", org.jooq.impl.SQLDataType.VARCHAR.length(32), this, "城市ID");

    /**
     * The column <code>gouchehui2.0.gch_view_special_price_car.province_name</code>. 省份名称
     */
    public final TableField<GchViewSpecialPriceCarRecord, String> PROVINCE_NAME = createField("province_name", org.jooq.impl.SQLDataType.VARCHAR.length(50), this, "省份名称");

    /**
     * The column <code>gouchehui2.0.gch_view_special_price_car.exterior_img</code>. 图片
     */
    public final TableField<GchViewSpecialPriceCarRecord, String> EXTERIOR_IMG = createField("exterior_img", org.jooq.impl.SQLDataType.VARCHAR.length(240), this, "图片");

    /**
     * The column <code>gouchehui2.0.gch_view_special_price_car.createtime</code>. 创建时间
     */
    public final TableField<GchViewSpecialPriceCarRecord, Timestamp> CREATETIME = createField("createtime", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0000-00-00 00:00:00", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "创建时间");

    /**
     * The column <code>gouchehui2.0.gch_view_special_price_car.createuser</code>. 创建人
     */
    public final TableField<GchViewSpecialPriceCarRecord, String> CREATEUSER = createField("createuser", org.jooq.impl.SQLDataType.VARCHAR.length(32), this, "创建人");

    /**
     * The column <code>gouchehui2.0.gch_view_special_price_car.updateuser</code>. 修改人
     */
    public final TableField<GchViewSpecialPriceCarRecord, String> UPDATEUSER = createField("updateuser", org.jooq.impl.SQLDataType.VARCHAR.length(32), this, "修改人");

    /**
     * The column <code>gouchehui2.0.gch_view_special_price_car.updatetime</code>. 修改时间
     */
    public final TableField<GchViewSpecialPriceCarRecord, Timestamp> UPDATETIME = createField("updatetime", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0000-00-00 00:00:00", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "修改时间");

    /**
     * The column <code>gouchehui2.0.gch_view_special_price_car.exterior_color_value</code>. 色值
     */
    public final TableField<GchViewSpecialPriceCarRecord, String> EXTERIOR_COLOR_VALUE = createField("exterior_color_value", org.jooq.impl.SQLDataType.VARCHAR.length(100), this, "色值");

    /**
     * The column <code>gouchehui2.0.gch_view_special_price_car.interior_color_value</code>. 色值
     */
    public final TableField<GchViewSpecialPriceCarRecord, String> INTERIOR_COLOR_VALUE = createField("interior_color_value", org.jooq.impl.SQLDataType.VARCHAR.length(100), this, "色值");

    /**
     * The column <code>gouchehui2.0.gch_view_special_price_car.status</code>. 特价车状态（1：审核中，2：未开始，3：在售，4：停售，5：过期，6：审核失败）
     */
    public final TableField<GchViewSpecialPriceCarRecord, Byte> STATUS = createField("status", org.jooq.impl.SQLDataType.TINYINT.defaultValue(org.jooq.impl.DSL.inline("1", org.jooq.impl.SQLDataType.TINYINT)), this, "特价车状态（1：审核中，2：未开始，3：在售，4：停售，5：过期，6：审核失败）");

    /**
     * The column <code>gouchehui2.0.gch_view_special_price_car.remark</code>. 备注(审核不通过）
     */
    public final TableField<GchViewSpecialPriceCarRecord, String> REMARK = createField("remark", org.jooq.impl.SQLDataType.VARCHAR.length(255), this, "备注(审核不通过）");

    /**
     * The column <code>gouchehui2.0.gch_view_special_price_car.displacement</code>. 排量
     */
    public final TableField<GchViewSpecialPriceCarRecord, String> DISPLACEMENT = createField("displacement", org.jooq.impl.SQLDataType.VARCHAR.length(20), this, "排量");

    /**
     * The column <code>gouchehui2.0.gch_view_special_price_car.discount</code>.
     */
    public final TableField<GchViewSpecialPriceCarRecord, Long> DISCOUNT = createField("discount", org.jooq.impl.SQLDataType.BIGINT, this, "");

    /**
     * The column <code>gouchehui2.0.gch_view_special_price_car.client</code>. 客户端(1:pc,2:手机web端)
     */
    public final TableField<GchViewSpecialPriceCarRecord, Integer> CLIENT = createField("client", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("1", org.jooq.impl.SQLDataType.INTEGER)), this, "客户端(1:pc,2:手机web端)");

    /**
     * Create a <code>gouchehui2.0.gch_view_special_price_car</code> table reference
     */
    public GchViewSpecialPriceCar() {
        this("gch_view_special_price_car", null);
    }

    /**
     * Create an aliased <code>gouchehui2.0.gch_view_special_price_car</code> table reference
     */
    public GchViewSpecialPriceCar(String alias) {
        this(alias, GCH_VIEW_SPECIAL_PRICE_CAR);
    }

    private GchViewSpecialPriceCar(String alias, Table<GchViewSpecialPriceCarRecord> aliased) {
        this(alias, aliased, null);
    }

    private GchViewSpecialPriceCar(String alias, Table<GchViewSpecialPriceCarRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "VIEW");
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
    public GchViewSpecialPriceCar as(String alias) {
        return new GchViewSpecialPriceCar(alias, this);
    }

    /**
     * Rename this table
     */
    public GchViewSpecialPriceCar rename(String name) {
        return new GchViewSpecialPriceCar(name, null);
    }
}
