package com.Gu.user.utils;

import com.arcsoft.face.*;
import com.arcsoft.face.enums.DetectMode;
import com.arcsoft.face.enums.DetectOrient;
import com.arcsoft.face.enums.ErrorInfo;
import com.arcsoft.face.enums.ExtractType;
import com.arcsoft.face.toolkit.ImageFactory;
import com.arcsoft.face.toolkit.ImageInfo;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component
public class ArcFaceCompare {

    public FaceEngine initFaceEngine(String appId, String sdkKey, String activeKey) {

        //人脸识别引擎库存放路径
        FaceEngine faceEngine = new FaceEngine("E:\\No_work_overtime_meeting\\cloud_demo\\cloud_user\\libs\\WIN64");
        //激活引擎
        int errorCode = faceEngine.activeOnline(appId, sdkKey, activeKey);
        System.out.println("引擎激活errorCode:" + errorCode);

        ActiveDeviceInfo activeDeviceInfo = new ActiveDeviceInfo();
        //采集设备信息（可离线）
        errorCode = faceEngine.getActiveDeviceInfo(activeDeviceInfo);
        System.out.println("采集设备信息errorCode:" + errorCode);
        System.out.println("设备信息:" + activeDeviceInfo.getDeviceInfo());

        ActiveFileInfo activeFileInfo = new ActiveFileInfo();
        errorCode = faceEngine.getActiveFileInfo(activeFileInfo);

        if (errorCode != ErrorInfo.MOK.getValue() && errorCode != ErrorInfo.MERR_ASF_ALREADY_ACTIVATED.getValue()) {
            System.out.println("引擎激活失败");
            return null;

        }

        //引擎配置
        EngineConfiguration engineConfiguration = new EngineConfiguration();
        engineConfiguration.setDetectMode(DetectMode.ASF_DETECT_MODE_IMAGE);
        engineConfiguration.setDetectFaceOrientPriority(DetectOrient.ASF_OP_ALL_OUT);
        engineConfiguration.setDetectFaceMaxNum(10);

        //功能配置
        FunctionConfiguration functionConfiguration = new FunctionConfiguration();
        functionConfiguration.setSupportAge(true);
        functionConfiguration.setSupportFaceDetect(true);
        functionConfiguration.setSupportFaceRecognition(true);
        functionConfiguration.setSupportGender(true);
        functionConfiguration.setSupportLiveness(true);
        functionConfiguration.setSupportIRLiveness(true);
        functionConfiguration.setSupportImageQuality(true);
        functionConfiguration.setSupportMaskDetect(true);
        functionConfiguration.setSupportUpdateFaceData(true);
        engineConfiguration.setFunctionConfiguration(functionConfiguration);

// 初始化引擎

        errorCode = faceEngine.init(engineConfiguration);
        System.out.println("初始化引擎errorCode:" + errorCode);
        VersionInfo version = faceEngine.getVersion();
        System.out.println(version);

        if (errorCode != ErrorInfo.MOK.getValue()) {

            System.out.println("初始化引擎失败");

            return null;
        }

        return faceEngine;
    }

    public Float FaceCompare(FaceEngine faceEngine, String picPath1, String picPath2) {
        //人脸检测

        ImageInfo imageInfo = ImageFactory.getRGBData(new File(picPath1));
        List<FaceInfo> faceInfoList = new ArrayList<>();
        int errorCode = faceEngine.detectFaces(imageInfo, faceInfoList);
        System.out.println("人脸检测errorCode:" + errorCode);
        System.out.println("检测到人脸数:" + faceInfoList.size());

        ImageQuality imageQuality = new ImageQuality();
        errorCode = faceEngine.imageQualityDetect(imageInfo, faceInfoList.get(0), 0, imageQuality);
        System.out.println("图像质量检测errorCode:" + errorCode);
        System.out.println("图像质量分数:" + imageQuality.getFaceQuality());

        //特征提取
        FaceFeature faceFeature = new FaceFeature();
        errorCode = faceEngine.extractFaceFeature(imageInfo, faceInfoList.get(0), ExtractType.REGISTER, 0, faceFeature);
        System.out.println("特征提取errorCode:" + errorCode);

        //人脸检测2
        ImageInfo imageInfo2 = ImageFactory.getRGBData(new File(picPath2));
        List<FaceInfo> faceInfoList2 = new ArrayList<>();
        errorCode = faceEngine.detectFaces(imageInfo2, faceInfoList2);
        System.out.println("人脸检测errorCode:" + errorCode);
        System.out.println("检测到人脸数:" + faceInfoList.size());

        //特征提取2
        FaceFeature faceFeature2 = new FaceFeature();
        errorCode = faceEngine.extractFaceFeature(imageInfo2, faceInfoList2.get(0), ExtractType.RECOGNIZE, 0, faceFeature2);
        System.out.println("特征提取errorCode:" + errorCode);

        //特征比对
        FaceFeature targetFaceFeature = new FaceFeature();
        targetFaceFeature.setFeatureData(faceFeature.getFeatureData());
        FaceFeature sourceFaceFeature = new FaceFeature();
        sourceFaceFeature.setFeatureData(faceFeature2.getFeatureData());
        FaceSimilar faceSimilar = new FaceSimilar();

        errorCode = faceEngine.compareFaceFeature(targetFaceFeature, sourceFaceFeature, faceSimilar);
        System.out.println("特征比对errorCode:" + errorCode);
        System.out.println("人脸相似度：" + faceSimilar.getScore());
        return faceSimilar.getScore();
    }
}
