package com.lrest.server.models;

import java.util.List;
import java.util.Map;

/**
 * Created by angrycans on 16/8/1.
 */
public  class Base_ProvinceCity {





    public static class ProvinceCity{
        //public String cityid;
        public String province;
        public String city;

        public   ProvinceCity(String _provincename,String _cityname){
            province=_provincename;
            city=_cityname;
        }

    }

   public List<String> provinces;
   public Map<String, List<ProvinceCity>> provincecity;


}
