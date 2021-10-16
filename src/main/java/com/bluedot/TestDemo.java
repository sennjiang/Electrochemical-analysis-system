package com.bluedot;


import com.bluedot.electrochemistry.dao.base.BaseMapper;
import com.bluedot.electrochemistry.factory.MapperFactory;
import com.bluedot.electrochemistry.pojo.domain.File;
import com.bluedot.framework.simplemybatis.session.defaults.DefaultSqlSessionFactory;
import com.bluedot.framework.simplespring.util.JsonUtil;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class TestDemo{
    private static Comparator<Map> comparator = new Comparator<Map>() {
        @Override
        public int compare(Map o1, Map o2) {
            return (Integer)o1.get("priority") - (Integer)o2.get("priority");
        }

        @Override
        public boolean equals(Object obj) {
            return (this == obj);
        }
    };

    public static void main(String[] args){
        String time = "Oct 10, 2021 7:23:26 PM";
//        Timestamp timestamp = Timestamp.valueOf(time);
//        System.out.println(timestamp.toString());
//        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//        File file = new File();
//        file.setProduceTime(timestamp);
//        file.setModifiedTime(timestamp);
//        List<File> list = new ArrayList<>();
//        list.add(file);
//        Map<String,Object> map = new HashMap<>();
//        map.put("data",list);
//        System.out.println(JsonUtil.toJson(map));

    }
}
