package com.juneutf.mtg.config.vender;

public class CheckTimePlan {
    public boolean check(String timeStart,String timeEnd){
        try {
            this.checkNull(timeStart,timeEnd);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return true;
    }
    public boolean checkNull(String timeStart,String timeEnd){
        return true;
    }
}
