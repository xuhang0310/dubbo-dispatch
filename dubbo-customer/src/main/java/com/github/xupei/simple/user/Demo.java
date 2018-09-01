package com.github.xupei.simple.user;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Demo implements  Runnable {

    private final CountDownLatch countDownLatch ;

    private int i;
    
    private static List list=new ArrayList();

    public Demo(CountDownLatch countDownLatch,int i) {
        super();
        this.i=i;
        System.out.println("第"+i+"次请求");
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String urlStr="http://localhost:8090/dubbo-customer/user/loginHome.do";
        URL url= null;
        try {
            url = new URL(urlStr);
            HttpURLConnection httpConn=(HttpURLConnection)url.openConnection();
            httpConn.connect();
            HashMap map=new HashMap();
            if (httpConn.getResponseCode() == 200) {
                java.io.InputStream is = (java.io.InputStream) httpConn.getContent();
                java.io.ByteArrayOutputStream baos =
                        new java.io.ByteArrayOutputStream();

                int buffer = 1024;
                byte[] b = new byte[buffer];
                int n = 0;
                while ((n = is.read(b, 0, buffer)) > 0) {
                    baos.write(b, 0, n);
                }
                String s = new String(baos.toByteArray(), "UTF-8");
                System.out.println(this.getI()+"---------------"+s);
                /*map.put(this.getI()+"",s);
                System.out.println("success");*/
                list.add(s.toString());
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        for(int i=1000;i>0;i--){
            new Thread(new Demo(countDownLatch,i)).start();
        }
        countDownLatch.countDown();

        System.out.println(list.size());

    }


    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }
}
