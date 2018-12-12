package org.sysu.processexecutionservice.util;

import java.util.ArrayList;

public class CommonUtil {
    //获取全部的列表元素,以split符号隔开
    public static String ArrayList2String(ArrayList<String> arrayList, String split) {
        StringBuilder sb = new StringBuilder();
        for(String s : arrayList) {
            sb.append(s).append(split);
        }
        return sb.toString();
    }
}
