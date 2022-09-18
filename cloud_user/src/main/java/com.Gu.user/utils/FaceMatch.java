package com.Gu.user.utils;

import com.baidu.aip.face.AipFace;
import com.baidu.aip.face.MatchRequest;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 人脸对比
 */
public class FaceMatch {


    private static final String APP_ID = "26143910";
    private static final String API_KEY = "KUosFBpbyMcW1oWr0P3RfayU";
    private static final String SECRET_KEY = "BYILm5NGSVlftaWfcSI9gy66FAhTYk8y";
    public static Double Match(String picPath1, String picPath2) {
        // 初始化一个AipFace
        AipFace client = new AipFace(APP_ID, API_KEY, SECRET_KEY);
        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
        // client.setHttpProxy("proxy_host", proxy_port); // 设置http代理
        // client.setSocketProxy(“proxy_host”, proxy_port); // 设置socket代理

        /* START 获取读取 图片Base64编码 **/
        // 方法一：
        //String image1 = "找一张有人脸的图片，在线图片生成Base64编码 然后复制到这里";
        //String image2 = "找一张有人脸的图片，在线图片生成Base64编码 然后复制到这里";
        // 方法二：
        // 读本地图片
        byte[] bytes = FileUtils.fileToBytes(picPath1);
        byte[] bytes2 = FileUtils.fileToBytes(picPath2);
        // 将字节转base64
        String image1 = Base64.encodeBase64String(bytes);
        String image2 = Base64.encodeBase64String(bytes2);
        /* END 获取读取 图片Base64编码 */
        // 人脸对比
        // image1/image2也可以为url或facetoken, 相应的imageType参数需要与之对应。
        MatchRequest req1 = new MatchRequest(image1, "BASE64");
        MatchRequest req2 = new MatchRequest(image2, "BASE64");
        ArrayList<MatchRequest> requests = new ArrayList<MatchRequest>();
        requests.add(req1);
        requests.add(req2);
        JSONObject res = client.match(requests);
        String result = res.toString(2).substring(29,34);
        return Double.parseDouble(result);
    }
}
