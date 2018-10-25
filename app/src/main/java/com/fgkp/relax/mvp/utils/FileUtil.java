package com.fgkp.relax.mvp.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;

public class FileUtil {
    private static String tag="test";
    /**
     * 写入文件
     * 断点续传
     * @param in
     * @param file
     */
    public static void writeFile(InputStream in, File file) throws IOException {
        RandomAccessFile savedFile = null;
        long ltest = 0;
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        if (file != null && file.exists()){
            ltest = file.length();
        }
        if (in != null){
            savedFile = new RandomAccessFile(file , "rw");
            savedFile.seek(ltest);
            byte[] buffer = new byte[1024 * 128];
            int len = -1;
            while ((len = in.read(buffer)) != -1) {
                savedFile.write(buffer, 0, len);
            }
            in.close();
        }else {
            try {
                throw new Exception("下载失败" , new Throwable("检查网网咯"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 读取文件长度
     */
    public static long readFile(File file){
        if (file != null && file.exists()){
            return file.length();
        }else {
            return 0;
        }
    }

    /**
     * 照片路径
     */
    public static File mkFile(String path,String name) {
        //String filePath= Constants.SOFT_FILE_PATH+ Constants.HEAD_IMA_PATH;//文件目录
        File file=new File(path);
        if(!file.exists()) {
            file.mkdirs();
            MLog.e(tag, "--------------------目录不存在,创建");
        }
        file=new File(path+"/"+name);
        return file;
    }

    //读取路径  写入路径
    public static   void copyFile(String isPath,String osPath)  {
        //读取a
        // File f=new File("D:/Test/a.txt");
        InputStream is= null;
        OutputStream os=null;
        try {
            is = new FileInputStream(isPath);
            os=new FileOutputStream(osPath);

            byte[] by=new byte[1024];
            int length=0;
            while((length=is.read(by))!=-1){
                os.write(by, 0, length);
            }
            MLog.e(tag, "--------------------复制完成");
            is.close();
            os.flush();
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
