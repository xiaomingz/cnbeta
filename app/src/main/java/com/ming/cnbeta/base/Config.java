package com.ming.cnbeta.base;

import java.util.regex.Pattern;

/**
 * Created by ming on 16/2/22.
 */
public class Config {

    public static final String ALLNEWS = "all";

    public static final Pattern SN_PATTERN = Pattern.compile("SN:\"(.{5})\"");

    /*时间格式*/
    public static final String TIMEFORMAT_DATE = "yyyy-MM-dd HH:mm:ss";

}
