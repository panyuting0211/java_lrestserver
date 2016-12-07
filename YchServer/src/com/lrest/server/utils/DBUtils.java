package com.lrest.server.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.jooq.Record;
import org.jooq.Result;

import java.util.Arrays;

/**
 * DESCRIPTION: aa
 *
 * @Author 韩武洽
 * @Date 2016-11
 * @Time 04 14:06
 **/
public class DBUtils {

    // 防止其他类调用
    private DBUtils(){

    }

    public static JsonArray dataChangeJsonArray(Result<Record> record) {
        try {
            // 元数据的数量
            int count = record.fieldsRow().size();
            String[] colNames = new String[count];
            for(int i = 0; i < count; i++){
                // 字段名称
                System.out.println(record.field(i).toString());
                colNames[i] = record.field(i).toString();
            }

            JsonObject jsonObject = new JsonObject();
            JsonArray jsonArray = new JsonArray();
            // 循环结果集列表条数
            for (int i = 0,ii = record.size(); i < ii; i++) {
                jsonObject = new JsonObject();
                // 循环无数据字段量
                for (int j = 0; j < count; j++) {
                    jsonObject.addProperty(
                            colNames[j].replaceAll("\"",""),
                            record.getValue(i,j) != null ? record.getValue(i,j).toString() : "");
                }
                System.out.println(jsonObject);
                jsonArray.add(jsonObject.toString());
            }
            System.out.println(jsonArray);
            return jsonArray;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
