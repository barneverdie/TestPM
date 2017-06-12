package com.example.administrator.musicplayer;

import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;

/**
 * Created by Administrator on 2017/2/4.
 */

public class Logger {

    public static boolean isSdCardExist() {

        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    public static String getSdCardPath() {
        boolean exist = isSdCardExist();
        String sdpath = "";
        if (exist) {
            sdpath = Environment.getExternalStorageDirectory()
                    .getAbsolutePath();
        } else {
            sdpath =null;
        }
        return sdpath;

    }

    public static String getDefaultFilePath() {
        String filepath = "";
        String sd=getSdCardPath();
        String dt=Environment.getExternalStorageDirectory().toString();
        File file = new File(Environment.getExternalStorageDirectory(),"logMusic.txt");
        if (file.exists()) {
            filepath = file.getAbsolutePath();
        } else {
            filepath =null;
        }
        return filepath;
    }

    public static boolean writeLog(String strTemp){
        try {
            File file = new File(Environment.getExternalStorageDirectory(),"log.txt");
            FileOutputStream fos = new FileOutputStream(file,true);


            SimpleDateFormat formatter    =   new    SimpleDateFormat    ("yyyy-MM-dd HH:mm:ss     ");
            Date curDate    =   new    Date(System.currentTimeMillis());
            curDate.getTime();
            String    str    =    formatter.format(curDate);
            fos.write((str+strTemp+"\r\n").getBytes());
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }


    /*4-1，使用FileInputStream读取文件

[java] view plain copy
在CODE上查看代码片派生到我的代码片

    try {
    le file = new File(Environment.getExternalStorageDirectory(),
    "test.txt");
        FileInputStream is = new FileInputStream(file);
        byte[] b = new byte[inputStream.available()];
        is.read(b);
        String result = new String(b);
        System.out.println("读取成功："+result);
    } catch (Exception e) {
        e.printStackTrace();
    }


4-2，使用BufferReader读取文件

[java] view plain copy
在CODE上查看代码片派生到我的代码片

    try {
        File file = new File(Environment.getExternalStorageDirectory(),
                DEFAULT_FILENAME);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String readline = "";
        StringBuffer sb = new StringBuffer();
        while ((readline = br.readLine()) != null) {
            System.out.println("readline:" + readline);
            sb.append(readline);
        }
        br.close();
        System.out.println("读取成功：" + sb.toString());
    } catch (Exception e) {
        e.printStackTrace();
    }

httpConnection读取流保存成String数据

[java] view plain copy
在CODE上查看代码片派生到我的代码片

    URL url = new URL(getForwardUrl("/queryUserByUNorIP"));
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    InputStream is = conn.getInputStream();
    BufferedReader br = new BufferedReader(new InputStreamReader(is));
    StringBuilder sb = new StringBuilder();
    String readline = null;
    while ((readline = br.readLine()) != null) {
        sb.append(readline);
    }
    System.out.println("result"+sb.toString());
    5-1，使用FileOutputStream写入文件

[java] view plain copy
在CODE上查看代码片派生到我的代码片

    try {
        File file = new File(Environment.getExternalStorageDirectory(),
                DEFAULT_FILENAME);
            FileOutputStream fos = new FileOutputStream(file);
            String info = "I am a chinanese!";
               fos.write(info.getBytes());
               fos.close();
        System.out.println("写入成功：");
    } catch (Exception e) {
        e.printStackTrace();
    }


5-2，使用BufferedWriter写入文件

[java] view plain copy
在CODE上查看代码片派生到我的代码片

    try {
        File file = new File(Environment.getExternalStorageDirectory(),
                DEFAULT_FILENAME);
        //第二个参数意义是说是否以append方式添加内容
        BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
        String info = " hey, yoo,bitch";
        bw.write(info);
        bw.flush();
        System.out.println("写入成功");
    } catch (Exception e) {
        e.printStackTrace();
    }



BufferedWriter

使用BufferedWriter，在构造BufferedWriter时，把第二个参数设为true
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file, true)));
         out.write(conent);

FileWriter

构造函数中的第二个参数true表示以追加形式写文件
         FileWriter writer = new FileWriter(fileName, true);
         writer.write(content);
         writer.close();

// 打开一个随机访问文件流，按读写方式
RandomAccessFile randomFile = new RandomAccessFile(fileName, "rw");
// 文件长度，字节数
long fileLength = randomFile.length();
// 将写文件指针移到文件尾。
randomFile.seek(fileLength);
randomFile.writeBytes(content);
randomFile.close();
*/
}
