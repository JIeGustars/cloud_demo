package com.Gu.user.utils;


import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class imgutile {
    /**
     * Base64转换为图片服务
     * targetPath  输出视频文件路径,不需要文件名
     * return picPath 文件所在绝对路径
     * */
    public static File base64ToImg(String base64,String targetPath){
        if (base64 == null || "".equals(base64)){
            return null;
        }
        File file = null;
        FileOutputStream fops;
        int start = base64.indexOf("/");
        int end = base64.indexOf(";");
        String ext = "." + base64.substring(start + 1, end);
        base64 = base64.substring(base64.indexOf(",") + 1);
        byte[] buff = DatatypeConverter.parseBase64Binary(base64);
        try {
            file = File.createTempFile("face",ext,new File(targetPath));
            fops = new FileOutputStream(file);
            fops.write(buff);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("--------------------------------"+"图片转换完成"+"--------------------------------");
        assert file != null;
        return file;
    }



}
