package test.ylf.com.viewtest.test.ylf.com.utils;

import java.io.DataInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2015/10/21.
 */
public class HttpUtils {

    public static String sendConnPost(String path){
        try {
            URL url=new URL(path);
            HttpURLConnection conn= (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(1000 * 3);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            InputStream is=conn.getInputStream();
            DataInputStream dis=new DataInputStream(is);
            byte bs[]=new byte[dis.available()];
            String result=new String(bs,"UTF-8");
            return  result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }
}
