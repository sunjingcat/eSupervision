package com.zz.supervision.business;

public class BusinessUtils {
//    tinspectType, tinspectSheetType
//
//    食品责改：1,3
//    食品处罚：1,4
//    食品回达：1,5
//
//    餐饮责改：2,3
//    餐饮处罚：2,4
//    餐饮回达：2,5
//
//    医疗器械责改：6,3
//    医疗器械处罚：6,4
//    医疗器械回达：6,5
//
//    药品责改：7,3
//    药品处罚：7,4
//    药品回达：7,5
//
//    化妆品责改：9,3
//    化妆品处罚：9,4
//    化妆品回达：9,5
    public static int getStatusTinspectTypeByInsType(int type){
        int tinspectType=0;
        if (type == 1||type==3) {
            tinspectType = 1;
        } else if (type == 2||type == 4) {
            tinspectType = 2;
        }  else if (type == 6 || type == 7) {
            tinspectType = 7;
        } else if (type == 8 || type == 9 || type == 10) {
            tinspectType = 6;
        }else if (type == 19 ) {
            tinspectType = 9;
        }else if (type == 20 ) {
            tinspectType = 9;
        }
        return tinspectType;
    }
}
