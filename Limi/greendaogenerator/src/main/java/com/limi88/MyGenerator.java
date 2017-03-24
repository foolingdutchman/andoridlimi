package com.sunanzq;
import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;
public class MyGenerator {
    public static void main(String[] args) throws Exception {

        Schema schema = new Schema(1, "com.sunanzq.greendao.bean");
        schema.setDefaultJavaPackageDao("com.sunanzq.greendao.dao");
        initUserBean(schema); // 初始化Bean了
        new DaoGenerator().generateAll(schema, "/Users/hehao/Desktop/android/Limi/app/src/main/java-gen");
    }
    private static void initUserBean(Schema schema) {
        Entity userBean = schema.addEntity("AccountBean");// 表名


        userBean.addStringProperty("userMobile").primaryKey();// 主键，索引
        userBean.addStringProperty("userToken");
        userBean.addStringProperty("userAvatar");
        userBean.addStringProperty("name");
        userBean.addBooleanProperty("authenticated");

        Entity searchBean = schema.addEntity("Search");// 表名
        searchBean.addIdProperty();
        searchBean.addStringProperty("searchTitle");



    }

}
