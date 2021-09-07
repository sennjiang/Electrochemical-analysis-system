package com.bluedot;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

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

    public static void main(String[] args) {
        Map<String, String[]> parameterMap = new HashMap<>();

    }
}
