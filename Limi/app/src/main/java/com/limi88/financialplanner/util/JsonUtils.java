package com.limi88.financialplanner.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.limi88.financialplanner.api.DataCenter;
import com.limi88.financialplanner.pojo.ad.Ad;
import com.limi88.financialplanner.pojo.clients.Clients;
import com.limi88.financialplanner.pojo.home.HomeData;
import com.limi88.financialplanner.pojo.products.ProductData;
import com.limi88.financialplanner.pojo.user.User;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;

/**
 * Created by hehao.
 * Created on 16/10/15.
 * Email: hao.he@sunanzq.com
 */
public class JsonUtils {
    private static Gson gson;

    private static int getUserId() {
        int id = -1;
        if (DataCenter.getCurrentUser() != null && DataCenter.getCurrentUser().getData() != null) {
            id = DataCenter.getCurrentUser().getData().getId();
        }
        return id;
    }

    public static boolean saveProductsToJson(ProductData products, String path, String cacheDir) {
        boolean flag = false;
        Gson gson = new GsonBuilder().create();
        String productsStr = gson.toJson(products);
        if (getUserId() == -1) {

            return saveDataToFile(productsStr, cacheDir + File.separator + "jsondata" + File.separator + path + ".txt");
        } else
            return saveDataToFile(productsStr, cacheDir + File.separator + "jsondata" + File.separator + getUserId() + path + ".txt");
    }

    public static boolean saveClientsToJson(Clients clients, String cacheDir) {
        boolean flag = false;
        gson = new Gson();
        String productsStr = gson.toJson(clients);
        if (getUserId() == -1) {

            return saveDataToFile(productsStr, cacheDir + File.separator + "jsondata" + File.separator + "clients.txt");
        } else
            return saveDataToFile(productsStr, cacheDir + File.separator + "jsondata" + File.separator + getUserId() + "clients.txt");

    }

    public static boolean saveHomeDataToJson(HomeData mHomeData, String cacheDir) {
        boolean flag = false;
        gson = new Gson();
        String homeStr = gson.toJson(mHomeData);
        if (getUserId() == -1) {
            return saveDataToFile(homeStr, cacheDir + File.separator + "jsondata" + File.separator + "home.txt");
        } else
            return saveDataToFile(homeStr, cacheDir + File.separator + "jsondata" + File.separator + getUserId() + "home.txt");
    }

    public static boolean saveUserToJson(User user, String cacheDir) {
        boolean flag = false;
        gson = new Gson();
        String homeStr = gson.toJson(user);
        if (getUserId() == -1) {
            return saveDataToFile(homeStr, cacheDir + File.separator + "jsondata" + File.separator + "user.txt");
        } else
            return saveDataToFile(homeStr, cacheDir + File.separator + "jsondata" + File.separator + getUserId() + "user.txt");

    }
    public static boolean saveAdsToJson(Ad mAd, String cacheDir) {
        boolean flag = false;
        gson = new Gson();
        String homeStr = gson.toJson(mAd);
            return saveDataToFile(homeStr, cacheDir + File.separator + "jsondata" + File.separator + "ads.txt");
    }

    private static boolean saveDataToFile(String productsStr, String dir) {
        boolean flag = false;
        File file = new File(dir);
        if (!file.getParentFile().exists()) {//判断父文件是否存在，如果不存在则创建
            file.getParentFile().mkdirs();
        } else {

        }
        PrintStream out = null; //打印流
        try {
            out = new PrintStream(new FileOutputStream(file)); //实例化打印流对象
            out.print(productsStr.toString()); //输出数据
            flag = true;
        } catch (FileNotFoundException e) {
// TODO Auto-generated catch block
            e.printStackTrace();

        } finally {
            if (out != null) { //如果打印流不为空，则关闭打印流
                out.close();
            }
        }
        return flag;
    }


    public static ProductData getProductsFromJson(String path, String cacheDir) {
        ProductData productData = new ProductData();

        Reader reader = null;
        BufferedReader br = null;
        try {
            if (getUserId() == -1) {
                reader = new FileReader(cacheDir + File.separator + "jsondata" + File.separator + path + ".txt");
            } else
                reader = new FileReader(cacheDir + File.separator + "jsondata" + File.separator + getUserId() + path + ".txt");
            br = new BufferedReader(reader);
            StringBuffer sb = new StringBuffer();
            String data = null;
            while ((data = br.readLine()) != null) {
                sb.append(data);
            }
            String productsStr = sb.toString();
            gson = new GsonBuilder().create();
            productData = gson.fromJson(productsStr, ProductData.class);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (br != null) {

                    br.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return productData;
    }

    public static Clients getClientsFromJson(String path) {
        Clients clients = new Clients();

        Reader reader = null;
        BufferedReader br = null;
        try {
            if (getUserId() == -1) {
                reader = new FileReader(path + File.separator + "jsondata" + File.separator  + "clients.txt");
            } else
                reader = new FileReader(path + File.separator + "jsondata" + File.separator + getUserId()  + "clients.txt");
            br = new BufferedReader(reader);
            StringBuffer sb = new StringBuffer();
            String data = null;
            while ((data = br.readLine()) != null) {
                sb.append(data);
            }
            String productsStr = sb.toString();
            gson = new GsonBuilder().create();
            clients = gson.fromJson(productsStr, Clients.class);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (br != null) {

                    br.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return clients;
    }

    public static HomeData getHomeFromJson(String path) {
        HomeData homeData = new HomeData();

        Reader reader = null;
        BufferedReader br = null;
        try {
            if (getUserId() == -1) {
                reader = new FileReader(path + File.separator + "jsondata" + File.separator  + "home.txt");
            } else
                reader = new FileReader(path + File.separator + "jsondata" + File.separator + getUserId()  + "home.txt");

            br = new BufferedReader(reader);
            StringBuffer sb = new StringBuffer();
            String data = null;
            while ((data = br.readLine()) != null) {
                sb.append(data);
            }
            String productsStr = sb.toString();
            gson = new GsonBuilder().create();
            homeData = gson.fromJson(productsStr, HomeData.class);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (br != null) {

                    br.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return homeData;
    }

    public static User getUserFromJson(String path) {
        User user = new User();

        Reader reader = null;
        BufferedReader br = null;
        try {
            if (getUserId() == -1) {
                reader = new FileReader(path + File.separator + "jsondata" + File.separator  + "user.txt");
            } else
                reader = new FileReader(path + File.separator + "jsondata" + File.separator + getUserId()  + "user.txt");
            br = new BufferedReader(reader);
            StringBuffer sb = new StringBuffer();
            String data = null;
            while ((data = br.readLine()) != null) {
                sb.append(data);
            }
            String productsStr = sb.toString();
            gson = new GsonBuilder().create();
            user = gson.fromJson(productsStr, User.class);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (br != null) {

                    br.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return user;
    }
    public static Ad getAdsFromJson(String path) {
        Ad mAd = new Ad();

        Reader reader = null;
        BufferedReader br = null;
        try {

                reader = new FileReader(path + File.separator + "jsondata" + File.separator  + "ads.txt");
                  br = new BufferedReader(reader);
            StringBuffer sb = new StringBuffer();
            String data = null;
            while ((data = br.readLine()) != null) {
                sb.append(data);
            }
            String productsStr = sb.toString();
            gson = new GsonBuilder().create();
            mAd = gson.fromJson(productsStr, Ad.class);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (br != null) {

                    br.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return mAd;
    }

}