package org.wang.kuaijiback.util;

import java.util.Date;

public class TimeUtil {

    /**
     *
     * @param time  时间毫秒数
     * @param minute  保留时间 分钟
     * @return
     */
    public boolean expire(long time,int minute){
        Date date=new Date();
        long m=minute*60*1000;
        if(date.getTime()-time<m){
            return true;
        }
        return false;
    }
}
