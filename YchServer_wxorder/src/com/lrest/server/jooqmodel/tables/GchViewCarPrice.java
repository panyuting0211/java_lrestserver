/**
 * This class is generated by jOOQ
 */
package com.lrest.server.jooqmodel.tables;


import com.lrest.server.jooqmodel.Gouchehui2_0;
import com.lrest.server.jooqmodel.tables.records.GchViewCarPriceRecord;

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
public class GchViewCarPrice extends TableImpl<GchViewCarPriceRecord> {

    private static final long serialVersionUID = -162350878;

    /**
     * The reference instance of <code>gouchehui2.0.gch_view_car_price</code>
     */
    public static final GchViewCarPrice GCH_VIEW_CAR_PRICE = new GchViewCarPrice();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<GchViewCarPriceRecord> getRecordType() {
        return GchViewCarPriceRecord.class;
    }

    /**
     * The column <code>gouchehui2.0.gch_view_car_price.id</code>. 主键
     */
    public final TableField<GchViewCarPriceRecord, String> ID = createField("id", org.jooq.impl.SQLDataType.VARCHAR.length(32).nullable(false), this, "主键");

    /**
     * The column <code>gouchehui2.0.gch_view_car_price.exterior_color_id</code>. 外观颜色ID
     */
    public final TableField<GchViewCarPriceRecord, String> EXTERIOR_COLOR_ID = createField("exterior_color_id", org.jooq.impl.SQLDataType.VARCHAR.length(32), this, "外观颜色ID");

    /**
     * The column <code>gouchehui2.0.gch_view_car_price.interior_color_id</code>. 内饰颜色ID
     */
    public final TableField<GchViewCarPriceRecord, String> INTERIOR_COLOR_ID = createField("interior_color_id", org.jooq.impl.SQLDataType.VARCHAR.length(32).nullable(false), this, "内饰颜色ID");

    /**
     * The column <code>gouchehui2.0.gch_view_car_price.user_id</code>. 所属用户
     */
    public final TableField<GchViewCarPriceRecord, String> USER_ID = createField("user_id", org.jooq.impl.SQLDataType.VARCHAR.length(32), this, "所属用户");

    /**
     * The column <code>gouchehui2.0.gch_view_car_price.access_quantity</code>. 访问量
     */
    public final TableField<GchViewCarPriceRecord, Integer> ACCESS_QUANTITY = createField("access_quantity", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "访问量");

    /**
     * The column <code>gouchehui2.0.gch_view_car_price.transactions_count</code>. 成交量
     */
    public final TableField<GchViewCarPriceRecord, Integer> TRANSACTIONS_COUNT = createField("transactions_count", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "成交量");

    /**
     * The column <code>gouchehui2.0.gch_view_car_price.asking_price_count</code>. 询价量
     */
    public final TableField<GchViewCarPriceRecord, Integer> ASKING_PRICE_COUNT = createField("asking_price_count", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "询价量");

    /**
     * The column <code>gouchehui2.0.gch_view_car_price.price</code>. 指导价
     */
    public final TableField<GchViewCarPriceRecord, String> PRICE = createField("price", org.jooq.impl.SQLDataType.VARCHAR.length(10), this, "指导价");

    /**
     * The column <code>gouchehui2.0.gch_view_car_price.discount</code>. 优惠
     */
    public final TableField<GchViewCarPriceRecord, Integer> DISCOUNT = createField("discount", org.jooq.impl.SQLDataType.INTEGER, this, "优惠");

    /**
     * The column <code>gouchehui2.0.gch_view_car_price.stock</code>. 库存
     */
    public final TableField<GchViewCarPriceRecord, String> STOCK = createField("stock", org.jooq.impl.SQLDataType.VARCHAR.length(10), this, "库存");

    /**
     * The column <code>gouchehui2.0.gch_view_car_price.onway</code>. 在途
     */
    public final TableField<GchViewCarPriceRecord, String> ONWAY = createField("onway", org.jooq.impl.SQLDataType.VARCHAR.length(10), this, "在途");

    /**
     * The column <code>gouchehui2.0.gch_view_car_price.createtime</code>. 创建时间
     */
    public final TableField<GchViewCarPriceRecord, Timestamp> CREATETIME = createField("createtime", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0000-00-00 00:00:00", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "创建时间");

    /**
     * The column <code>gouchehui2.0.gch_view_car_price.createuser</code>. 创建人
     */
    public final TableField<GchViewCarPriceRecord, String> CREATEUSER = createField("createuser", org.jooq.impl.SQLDataType.VARCHAR.length(32), this, "创建人");

    /**
     * The column <code>gouchehui2.0.gch_view_car_price.isdelete</code>. 是否删除
     */
    public final TableField<GchViewCarPriceRecord, Integer> ISDELETE = createField("isdelete", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "是否删除");

    /**
     * The column <code>gouchehui2.0.gch_view_car_price.updatetime</code>. 修改时间
     */
    public final TableField<GchViewCarPriceRecord, Timestamp> UPDATETIME = createField("updatetime", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0000-00-00 00:00:00", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "修改时间");

    /**
     * The column <code>gouchehui2.0.gch_view_car_price.updateuser</code>. 修改人
     */
    public final TableField<GchViewCarPriceRecord, String> UPDATEUSER = createField("updateuser", org.jooq.impl.SQLDataType.VARCHAR.length(32), this, "修改人");

    /**
     * The column <code>gouchehui2.0.gch_view_car_price.low_price</code>. 报价
     */
    public final TableField<GchViewCarPriceRecord, Integer> LOW_PRICE = createField("low_price", org.jooq.impl.SQLDataType.INTEGER, this, "报价");

    /**
     * The column <code>gouchehui2.0.gch_view_car_price.interior_color_name</code>. 名称
     */
    public final TableField<GchViewCarPriceRecord, String> INTERIOR_COLOR_NAME = createField("interior_color_name", org.jooq.impl.SQLDataType.VARCHAR.length(100), this, "名称");

    /**
     * The column <code>gouchehui2.0.gch_view_car_price.interior_color_value</code>. 色值
     */
    public final TableField<GchViewCarPriceRecord, String> INTERIOR_COLOR_VALUE = createField("interior_color_value", org.jooq.impl.SQLDataType.VARCHAR.length(100), this, "色值");

    /**
     * The column <code>gouchehui2.0.gch_view_car_price.interior_color_imageurl</code>. 图片
     */
    public final TableField<GchViewCarPriceRecord, String> INTERIOR_COLOR_IMAGEURL = createField("interior_color_imageurl", org.jooq.impl.SQLDataType.VARCHAR.length(240), this, "图片");

    /**
     * The column <code>gouchehui2.0.gch_view_car_price.exterior_color_name</code>. 名称
     */
    public final TableField<GchViewCarPriceRecord, String> EXTERIOR_COLOR_NAME = createField("exterior_color_name", org.jooq.impl.SQLDataType.VARCHAR.length(100), this, "名称");

    /**
     * The column <code>gouchehui2.0.gch_view_car_price.exterior_color_value</code>. 色值
     */
    public final TableField<GchViewCarPriceRecord, String> EXTERIOR_COLOR_VALUE = createField("exterior_color_value", org.jooq.impl.SQLDataType.VARCHAR.length(100), this, "色值");

    /**
     * The column <code>gouchehui2.0.gch_view_car_price.exterior_color_imgurl</code>. 图片
     */
    public final TableField<GchViewCarPriceRecord, String> EXTERIOR_COLOR_IMGURL = createField("exterior_color_imgurl", org.jooq.impl.SQLDataType.VARCHAR.length(240), this, "图片");

    /**
     * The column <code>gouchehui2.0.gch_view_car_price.car_id</code>.
     */
    public final TableField<GchViewCarPriceRecord, String> CAR_ID = createField("car_id", org.jooq.impl.SQLDataType.VARCHAR.length(32).nullable(false), this, "");

    /**
     * The column <code>gouchehui2.0.gch_view_car_price.car_name</code>. 名称
     */
    public final TableField<GchViewCarPriceRecord, String> CAR_NAME = createField("car_name", org.jooq.impl.SQLDataType.VARCHAR.length(100), this, "名称");

    /**
     * The column <code>gouchehui2.0.gch_view_car_price.car_model_id</code>. 所属车型
     */
    public final TableField<GchViewCarPriceRecord, String> CAR_MODEL_ID = createField("car_model_id", org.jooq.impl.SQLDataType.VARCHAR.length(32), this, "所属车型");

    /**
     * The column <code>gouchehui2.0.gch_view_car_price.car_model_name</code>. 名称
     */
    public final TableField<GchViewCarPriceRecord, String> CAR_MODEL_NAME = createField("car_model_name", org.jooq.impl.SQLDataType.VARCHAR.length(100), this, "名称");

    /**
     * The column <code>gouchehui2.0.gch_view_car_price.car_type_id</code>. 所属分类
     */
    public final TableField<GchViewCarPriceRecord, String> CAR_TYPE_ID = createField("car_type_id", org.jooq.impl.SQLDataType.VARCHAR.length(32), this, "所属分类");

    /**
     * The column <code>gouchehui2.0.gch_view_car_price.car_type_name</code>. 名称
     */
    public final TableField<GchViewCarPriceRecord, String> CAR_TYPE_NAME = createField("car_type_name", org.jooq.impl.SQLDataType.VARCHAR.length(100), this, "名称");

    /**
     * The column <code>gouchehui2.0.gch_view_car_price.car_model_imageurl</code>. 图片
     */
    public final TableField<GchViewCarPriceRecord, String> CAR_MODEL_IMAGEURL = createField("car_model_imageurl", org.jooq.impl.SQLDataType.VARCHAR.length(240), this, "图片");

    /**
     * The column <code>gouchehui2.0.gch_view_car_price.brand_id</code>. 所属品牌
     */
    public final TableField<GchViewCarPriceRecord, String> BRAND_ID = createField("brand_id", org.jooq.impl.SQLDataType.VARCHAR.length(32), this, "所属品牌");

    /**
     * The column <code>gouchehui2.0.gch_view_car_price.brand_name</code>. 名称
     */
    public final TableField<GchViewCarPriceRecord, String> BRAND_NAME = createField("brand_name", org.jooq.impl.SQLDataType.VARCHAR.length(100), this, "名称");

    /**
     * The column <code>gouchehui2.0.gch_view_car_price.alif</code>.
     */
    public final TableField<GchViewCarPriceRecord, String> ALIF = createField("alif", org.jooq.impl.SQLDataType.VARCHAR.length(2), this, "");

    /**
     * The column <code>gouchehui2.0.gch_view_car_price.city_name</code>. 城市名称
     */
    public final TableField<GchViewCarPriceRecord, String> CITY_NAME = createField("city_name", org.jooq.impl.SQLDataType.VARCHAR.length(20), this, "城市名称");

    /**
     * The column <code>gouchehui2.0.gch_view_car_price.province_id</code>. 所属省份
     */
    public final TableField<GchViewCarPriceRecord, String> PROVINCE_ID = createField("province_id", org.jooq.impl.SQLDataType.VARCHAR.length(32), this, "所属省份");

    /**
     * The column <code>gouchehui2.0.gch_view_car_price.province_name</code>. 省份名称
     */
    public final TableField<GchViewCarPriceRecord, String> PROVINCE_NAME = createField("province_name", org.jooq.impl.SQLDataType.VARCHAR.length(50), this, "省份名称");

    /**
     * The column <code>gouchehui2.0.gch_view_car_price.car_xj_count</code>. 询价总数
     */
    public final TableField<GchViewCarPriceRecord, Integer> CAR_XJ_COUNT = createField("car_xj_count", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "询价总数");

    /**
     * The column <code>gouchehui2.0.gch_view_car_price.is_xunjia</code>. 是否需要询价(1需要询价,2不需要询价)
     */
    public final TableField<GchViewCarPriceRecord, Integer> IS_XUNJIA = createField("is_xunjia", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("1", org.jooq.impl.SQLDataType.INTEGER)), this, "是否需要询价(1需要询价,2不需要询价)");

    /**
     * The column <code>gouchehui2.0.gch_view_car_price.brand_alif</code>.
     */
    public final TableField<GchViewCarPriceRecord, String> BRAND_ALIF = createField("brand_alif", org.jooq.impl.SQLDataType.VARCHAR.length(2), this, "");

    /**
     * The column <code>gouchehui2.0.gch_view_car_price.model_alif</code>.
     */
    public final TableField<GchViewCarPriceRecord, String> MODEL_ALIF = createField("model_alif", org.jooq.impl.SQLDataType.VARCHAR.length(2), this, "");

    /**
     * The column <code>gouchehui2.0.gch_view_car_price.car_model_xj_count</code>. 询价次数
     */
    public final TableField<GchViewCarPriceRecord, Integer> CAR_MODEL_XJ_COUNT = createField("car_model_xj_count", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "询价次数");

    /**
     * The column <code>gouchehui2.0.gch_view_car_price.show_index</code>. 显示索引
     */
    public final TableField<GchViewCarPriceRecord, Integer> SHOW_INDEX = createField("show_index", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "显示索引");

    /**
     * The column <code>gouchehui2.0.gch_view_car_price.brand_access_quantity</code>. 访问量
     */
    public final TableField<GchViewCarPriceRecord, Integer> BRAND_ACCESS_QUANTITY = createField("brand_access_quantity", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "访问量");

    /**
     * The column <code>gouchehui2.0.gch_view_car_price.description</code>. 描述
     */
    public final TableField<GchViewCarPriceRecord, String> DESCRIPTION = createField("description", org.jooq.impl.SQLDataType.VARCHAR.length(255), this, "描述");

    /**
     * The column <code>gouchehui2.0.gch_view_car_price.car_status</code>. 车款状态 1：在售；0：停售。
     */
    public final TableField<GchViewCarPriceRecord, Byte> CAR_STATUS = createField("car_status", org.jooq.impl.SQLDataType.TINYINT.defaultValue(org.jooq.impl.DSL.inline("1", org.jooq.impl.SQLDataType.TINYINT)), this, "车款状态 1：在售；0：停售。");

    /**
     * The column <code>gouchehui2.0.gch_view_car_price.name_4s</code>. 4S店名称
     */
    public final TableField<GchViewCarPriceRecord, String> NAME_4S = createField("name_4s", org.jooq.impl.SQLDataType.VARCHAR.length(50), this, "4S店名称");

    /**
     * The column <code>gouchehui2.0.gch_view_car_price.brand_4s</code>. 4S店主营品牌
     */
    public final TableField<GchViewCarPriceRecord, String> BRAND_4S = createField("brand_4s", org.jooq.impl.SQLDataType.VARCHAR.length(32), this, "4S店主营品牌");

    /**
     * The column <code>gouchehui2.0.gch_view_car_price.addr</code>. 地址
     */
    public final TableField<GchViewCarPriceRecord, String> ADDR = createField("addr", org.jooq.impl.SQLDataType.VARCHAR.length(50), this, "地址");

    /**
     * The column <code>gouchehui2.0.gch_view_car_price.city_id</code>. 城市ID
     */
    public final TableField<GchViewCarPriceRecord, String> CITY_ID = createField("city_id", org.jooq.impl.SQLDataType.VARCHAR.length(32), this, "城市ID");

    /**
     * The column <code>gouchehui2.0.gch_view_car_price.auth_price</code>. 官方指导价
     */
    public final TableField<GchViewCarPriceRecord, Integer> AUTH_PRICE = createField("auth_price", org.jooq.impl.SQLDataType.INTEGER, this, "官方指导价");

    /**
     * The column <code>gouchehui2.0.gch_view_car_price.exterior_img</code>. 图片
     */
    public final TableField<GchViewCarPriceRecord, String> EXTERIOR_IMG = createField("exterior_img", org.jooq.impl.SQLDataType.VARCHAR.length(240), this, "图片");

    /**
     * The column <code>gouchehui2.0.gch_view_car_price.displacement</code>. 排量
     */
    public final TableField<GchViewCarPriceRecord, String> DISPLACEMENT = createField("displacement", org.jooq.impl.SQLDataType.VARCHAR.length(20), this, "排量");

    /**
     * The column <code>gouchehui2.0.gch_view_car_price.logo</code>. logo
     */
    public final TableField<GchViewCarPriceRecord, String> LOGO = createField("logo", org.jooq.impl.SQLDataType.VARCHAR.length(240), this, "logo");

    /**
     * The column <code>gouchehui2.0.gch_view_car_price.model_access_quantity</code>. 访问量
     */
    public final TableField<GchViewCarPriceRecord, Integer> MODEL_ACCESS_QUANTITY = createField("model_access_quantity", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "访问量");

    /**
     * Create a <code>gouchehui2.0.gch_view_car_price</code> table reference
     */
    public GchViewCarPrice() {
        this("gch_view_car_price", null);
    }

    /**
     * Create an aliased <code>gouchehui2.0.gch_view_car_price</code> table reference
     */
    public GchViewCarPrice(String alias) {
        this(alias, GCH_VIEW_CAR_PRICE);
    }

    private GchViewCarPrice(String alias, Table<GchViewCarPriceRecord> aliased) {
        this(alias, aliased, null);
    }

    private GchViewCarPrice(String alias, Table<GchViewCarPriceRecord> aliased, Field<?>[] parameters) {
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
    public GchViewCarPrice as(String alias) {
        return new GchViewCarPrice(alias, this);
    }

    /**
     * Rename this table
     */
    public GchViewCarPrice rename(String name) {
        return new GchViewCarPrice(name, null);
    }
}
