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
    public static String getRecordTypeByType(int type){
        String recordType="";
        if (type==1){
            recordType="spxs";
        }else if (type==2){
            recordType="spxs";
        }
        else if (type == 6 || type == 7) {//药品
            recordType="yp";
        }
        else if (type == 8 || type == 9 || type == 10) {//医疗器械
            recordType="ylqx";
        }else if (type == 19) {//医疗器械
            recordType="hzp";
        }
        return  recordType;
    }
}
