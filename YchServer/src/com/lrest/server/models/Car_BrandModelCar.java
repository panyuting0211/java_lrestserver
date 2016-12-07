package com.lrest.server.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DESCRIPTION: 汽车品牌 车型和车款
 * @author 韩武洽
 * @create 2016-09-09 11:51
 **/
public class Car_BrandModelCar {

    /*// 汽车品牌
    public String brandId;
    public String brandName;

    // 汽车车型
    public String carModelId;
    public String carModelName;

    // 汽车车款
    public String carId;
    public String carName;*/


    // 静态内部类 品牌
    public static class Brand {
        public String brandId;
        public String brandName;

        public Brand(String _brandId,String _brandName){
            this.brandId = _brandId;
            this.brandName = _brandName;
        }
    }

    // 静态内部类 车型
    public static class CarModel {
//        // 汽车品牌
//        public String brandId;
//        public String brandName;

        // 汽车车型
        public String carModelId;
        public String carModelName;

        public CarModel(String _carModelId,String _carModelName){
            this.carModelId = _carModelId;
            this.carModelName = _carModelName;
        }
//        public CarModel(String _brandId,String _brandName,String _carModelId,String _carModelName){
//            this.brandId = _brandId;
//            this.brandName = _brandName;
//            this.carModelId = _carModelId;
//            this.carModelName = _carModelName;
//        }
    }

    // 静态内部类 车款
    public static class Car {
//        // 汽车品牌
//        public String brandId;
//        public String brandName;
//
//        // 汽车车型
//        public String carModelId;
//        public String carModelName;

        // 汽车车款
        public String carId;
        public String carName;

        public Car(String _carId,String _carName){
            this.carId = _carId;
            this.carName = _carName;
        }
//        public Car(String _brandId,String _brandName,
//                   String _carModelId,String _carModelName,String _carId,String _carName){
//            this.brandId = _brandId;
//            this.brandName = _brandName;
//            this.carModelId = _carModelId;
//            this.carModelName = _carModelName;
//            this.carId = _carId;
//            this.carName = _carName;
//        }
    }

    // 品牌 的Map集合类
    public List<Brand> brand = new ArrayList<>();
    // 车型 的Map集合类
    public Map<String,List<CarModel>> carModel = new HashMap<>();
    // 车款 的Map集合类
    public Map<String,List<Car>> car = new HashMap<>();

}
