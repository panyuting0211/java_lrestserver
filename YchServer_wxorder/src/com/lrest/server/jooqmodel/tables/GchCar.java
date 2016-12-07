/**
 * This class is generated by jOOQ
 */
package com.lrest.server.jooqmodel.tables;


import com.lrest.server.jooqmodel.Gouchehui2_0;
import com.lrest.server.jooqmodel.Keys;
import com.lrest.server.jooqmodel.tables.records.GchCarRecord;

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
 * 车款表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.2"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class GchCar extends TableImpl<GchCarRecord> {

    private static final long serialVersionUID = 1510033981;

    /**
     * The reference instance of <code>gouchehui2.0.gch_car</code>
     */
    public static final GchCar GCH_CAR = new GchCar();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<GchCarRecord> getRecordType() {
        return GchCarRecord.class;
    }

    /**
     * The column <code>gouchehui2.0.gch_car.id</code>. 主键
     */
    public final TableField<GchCarRecord, String> ID = createField("id", org.jooq.impl.SQLDataType.VARCHAR.length(32).nullable(false), this, "主键");

    /**
     * The column <code>gouchehui2.0.gch_car.car_model_id</code>. 所属车型
     */
    public final TableField<GchCarRecord, String> CAR_MODEL_ID = createField("car_model_id", org.jooq.impl.SQLDataType.VARCHAR.length(32), this, "所属车型");

    /**
     * The column <code>gouchehui2.0.gch_car.access_quantity</code>. 访问量
     */
    public final TableField<GchCarRecord, Integer> ACCESS_QUANTITY = createField("access_quantity", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "访问量");

    /**
     * The column <code>gouchehui2.0.gch_car.car_name</code>. 名称
     */
    public final TableField<GchCarRecord, String> CAR_NAME = createField("car_name", org.jooq.impl.SQLDataType.VARCHAR.length(100), this, "名称");

    /**
     * The column <code>gouchehui2.0.gch_car.imgurl</code>. 图片
     */
    public final TableField<GchCarRecord, String> IMGURL = createField("imgurl", org.jooq.impl.SQLDataType.VARCHAR.length(240), this, "图片");

    /**
     * The column <code>gouchehui2.0.gch_car.description</code>. 描述
     */
    public final TableField<GchCarRecord, String> DESCRIPTION = createField("description", org.jooq.impl.SQLDataType.VARCHAR.length(255), this, "描述");

    /**
     * The column <code>gouchehui2.0.gch_car.createtime</code>. 创建时间
     */
    public final TableField<GchCarRecord, Timestamp> CREATETIME = createField("createtime", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "创建时间");

    /**
     * The column <code>gouchehui2.0.gch_car.createuser</code>. 创建人
     */
    public final TableField<GchCarRecord, String> CREATEUSER = createField("createuser", org.jooq.impl.SQLDataType.VARCHAR.length(32), this, "创建人");

    /**
     * The column <code>gouchehui2.0.gch_car.isdelete</code>. 是否删除
     */
    public final TableField<GchCarRecord, Integer> ISDELETE = createField("isdelete", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "是否删除");

    /**
     * The column <code>gouchehui2.0.gch_car.updatetime</code>. 修改时间
     */
    public final TableField<GchCarRecord, Timestamp> UPDATETIME = createField("updatetime", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0000-00-00 00:00:00", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "修改时间");

    /**
     * The column <code>gouchehui2.0.gch_car.updateuser</code>. 修改人
     */
    public final TableField<GchCarRecord, String> UPDATEUSER = createField("updateuser", org.jooq.impl.SQLDataType.VARCHAR.length(32), this, "修改人");

    /**
     * The column <code>gouchehui2.0.gch_car.alif</code>.
     */
    public final TableField<GchCarRecord, String> ALIF = createField("alif", org.jooq.impl.SQLDataType.VARCHAR.length(2), this, "");

    /**
     * The column <code>gouchehui2.0.gch_car.xj_count</code>. 询价总数
     */
    public final TableField<GchCarRecord, Integer> XJ_COUNT = createField("xj_count", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "询价总数");

    /**
     * The column <code>gouchehui2.0.gch_car.auth_price</code>. 官方指导价
     */
    public final TableField<GchCarRecord, Integer> AUTH_PRICE = createField("auth_price", org.jooq.impl.SQLDataType.INTEGER, this, "官方指导价");

    /**
     * The column <code>gouchehui2.0.gch_car.order</code>.
     */
    public final TableField<GchCarRecord, Integer> ORDER = createField("order", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>gouchehui2.0.gch_car.status</code>. 车款状态 1：在售；0：停售。
     */
    public final TableField<GchCarRecord, Byte> STATUS = createField("status", org.jooq.impl.SQLDataType.TINYINT.defaultValue(org.jooq.impl.DSL.inline("1", org.jooq.impl.SQLDataType.TINYINT)), this, "车款状态 1：在售；0：停售。");

    /**
     * The column <code>gouchehui2.0.gch_car.displacement</code>. 排量
     */
    public final TableField<GchCarRecord, String> DISPLACEMENT = createField("displacement", org.jooq.impl.SQLDataType.VARCHAR.length(20), this, "排量");

    /**
     * Create a <code>gouchehui2.0.gch_car</code> table reference
     */
    public GchCar() {
        this("gch_car", null);
    }

    /**
     * Create an aliased <code>gouchehui2.0.gch_car</code> table reference
     */
    public GchCar(String alias) {
        this(alias, GCH_CAR);
    }

    private GchCar(String alias, Table<GchCarRecord> aliased) {
        this(alias, aliased, null);
    }

    private GchCar(String alias, Table<GchCarRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "车款表");
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
    public UniqueKey<GchCarRecord> getPrimaryKey() {
        return Keys.KEY_GCH_CAR_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<GchCarRecord>> getKeys() {
        return Arrays.<UniqueKey<GchCarRecord>>asList(Keys.KEY_GCH_CAR_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GchCar as(String alias) {
        return new GchCar(alias, this);
    }

    /**
     * Rename this table
     */
    public GchCar rename(String name) {
        return new GchCar(name, null);
    }
}