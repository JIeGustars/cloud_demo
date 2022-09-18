package com.Gu.user.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
public class FileUtils {
    protected static Logger logger = LoggerFactory.getLogger(FileUtils.class);
    public static byte[] fileToBytes(String filePath) {
        byte[] buffer = null;
        File file = new File(filePath);
        FileInputStream fis = null;
        ByteArrayOutputStream bos = null;
        try {
            fis = new FileInputStream(file);
            bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            buffer = bos.toByteArray();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            logger.error("", ex);
        } catch (IOException ex) {
            ex.printStackTrace();
            logger.error("", ex);
        } finally {
            try {
                if (null != bos) {
                    bos.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                logger.error("", ex);
            } finally {
                try {
                    if (null != fis) {
                        fis.close();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                    logger.error("", ex);
                }
            }
        }
        return buffer;
    }
    public static void bytesToFile(byte[] buffer, final String filePath) {
        File file = new File(filePath);
        OutputStream output = null;
        BufferedOutputStream bufferedOutput = null;
        try {
            output = new FileOutputStream(file);
            bufferedOutput = new BufferedOutputStream(output);
            bufferedOutput.write(buffer);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            logger.error("", ex);
        } catch (IOException ex) {
            ex.printStackTrace();
            logger.error("", ex);
        } finally {
            if (null != bufferedOutput) {
                try {
                    bufferedOutput.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                    logger.error("", ex);
                }
            }
            if (null != output) {
                try {
                    output.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                    logger.error("", ex);
                }
            }
        }
    }
    public static void bytesToFile(byte[] buffer, File file) {
        OutputStream output = null;
        BufferedOutputStream bufferedOutput = null;
        try {
            output = new FileOutputStream(file);
            bufferedOutput = new BufferedOutputStream(output);
            bufferedOutput.write(buffer);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            logger.error("", ex);
        } catch (IOException ex) {
            ex.printStackTrace();
            logger.error("", ex);
        } finally {
            if (null != bufferedOutput) {
                try {
                    bufferedOutput.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                    logger.error("", ex);
                }
            }
            if (null != output) {
                try {
                    output.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                    logger.error("", ex);
                }
            }
        }
    }
    /** * byte数组转换成16进制字符串 * * @param src * @return */
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }
    /** * 根据文件流读取图片文件真实类型 */
    public static String getTypeByBytes(byte[] fileBytes) {
        byte[] b = new byte[4];
        System.arraycopy(fileBytes, 0, b, 0, b.length);
        String type = bytesToHexString(b).toUpperCase();
        if (type.contains("FFD8FF")) {
            return "jpg";
        } else if (type.contains("89504E47")) {
            return "png";
        } else if (type.contains("47494638")) {
            return "gif";
        } else if (type.contains("49492A00")) {
            return "tif";
        } else if (type.contains("424D")) {
            return "bmp";
        }
        return type;
    }
    public static String getTypeByFile(File file) {
        String fileName = file.getName();
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
    public static String getTypeByFilePath(String filePath) {
        return filePath.substring(filePath.lastIndexOf(".") + 1);
    }
    public static byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
        return output.toByteArray();
    }
}
