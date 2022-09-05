package com.Gu.user.utils;

import com.arcsoft.face.*;
import com.arcsoft.face.enums.*;
import com.arcsoft.face.toolkit.ImageFactory;
import com.arcsoft.face.toolkit.ImageInfo;
import com.arcsoft.face.toolkit.ImageInfoEx;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FaceEngineTest {


    public static void main(String[] args) {

        //激活码，从官网获取
        String appId = "3cz2nRe4SJDL11daJijZg25nWCPupUp1BCGe1egtcjzT";
        String sdkKey = "ByukQy2YY8dhnWxuq6uaNcBUdYoFqDcMrm3y6VyyTK8K";
        String activeKey = "86L1-11EG-T13G-FUWA";

        System.err.println("注意，如果返回的errorCode不为0，可查看com.arcsoft.face.enums.ErrorInfo类获取相应的错误信息");

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

//        faceEngine.activeOffline("d:\\ArcFacePro64.dat.offline");

        ActiveFileInfo activeFileInfo = new ActiveFileInfo();
        errorCode = faceEngine.getActiveFileInfo(activeFileInfo);
        System.out.println("获取激活文件errorCode:" + errorCode);
        System.out.println("激活文件信息:" + activeFileInfo.toString());

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

        //初始化引擎
        errorCode = faceEngine.init(engineConfiguration);
        System.out.println("初始化引擎errorCode:" + errorCode);
        VersionInfo version = faceEngine.getVersion();
        System.out.println(version);

        //人脸检测
        ImageInfo imageInfo = ImageFactory.getRGBData(new File("E:\\IMAGE\\胡歌1.jpg"));
        List<FaceInfo> faceInfoList = new ArrayList<FaceInfo>();
        errorCode = faceEngine.detectFaces(imageInfo, faceInfoList);
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
        ImageInfo imageInfo2 = ImageFactory.getRGBData(new File("E:\\IMAGE\\2.jpg"));
        List<FaceInfo> faceInfoList2 = new ArrayList<FaceInfo>();
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


        //人脸属性检测
        FunctionConfiguration configuration = new FunctionConfiguration();
        configuration.setSupportAge(true);
        configuration.setSupportGender(true);
        configuration.setSupportLiveness(true);
        configuration.setSupportMaskDetect(true);
        errorCode = faceEngine.process(imageInfo, faceInfoList, configuration);
        System.out.println("图像属性处理errorCode:" + errorCode);

        //性别检测
        List<GenderInfo> genderInfoList = new ArrayList<GenderInfo>();
        errorCode = faceEngine.getGender(genderInfoList);
        System.out.println("性别：" + genderInfoList.get(0).getGender());

        //年龄检测
        List<AgeInfo> ageInfoList = new ArrayList<AgeInfo>();
        errorCode = faceEngine.getAge(ageInfoList);
        System.out.println("年龄：" + ageInfoList.get(0).getAge());

        //活体检测
        List<LivenessInfo> livenessInfoList = new ArrayList<LivenessInfo>();
        errorCode = faceEngine.getLiveness(livenessInfoList);
        System.out.println("活体：" + livenessInfoList.get(0).getLiveness());

        //口罩检测
        List<MaskInfo> maskInfoList = new ArrayList<MaskInfo>();
        errorCode = faceEngine.getMask(maskInfoList);
        System.out.println("口罩：" + maskInfoList.get(0).getMask());


        //IR属性处理
        ImageInfo imageInfoGray = ImageFactory.getGrayData(new File("d:\\IR_480p.jpg"));
        List<FaceInfo> faceInfoListGray = new ArrayList<FaceInfo>();
        errorCode = faceEngine.detectFaces(imageInfoGray, faceInfoListGray);

        FunctionConfiguration configuration2 = new FunctionConfiguration();
        configuration2.setSupportIRLiveness(true);
        errorCode = faceEngine.processIr(imageInfoGray, faceInfoListGray, configuration2);
        //IR活体检测
        List<IrLivenessInfo> irLivenessInfo = new ArrayList<>();
        errorCode = faceEngine.getLivenessIr(irLivenessInfo);
        System.out.println("IR活体：" + irLivenessInfo.get(0).getLiveness());

        //获取激活文件信息
        ActiveFileInfo activeFileInfo2 = new ActiveFileInfo();
        errorCode = faceEngine.getActiveFileInfo(activeFileInfo2);

        //更新人脸数据
        errorCode = faceEngine.updateFaceData(imageInfo, faceInfoList);

        //高级人脸图像处理接口
        ImageInfoEx imageInfoEx = new ImageInfoEx();
        imageInfoEx.setHeight(imageInfo.getHeight());
        imageInfoEx.setWidth(imageInfo.getWidth());
        imageInfoEx.setImageFormat(imageInfo.getImageFormat());
        imageInfoEx.setImageDataPlanes(new byte[][]{imageInfo.getImageData()});
        imageInfoEx.setImageStrides(new int[]{imageInfo.getWidth() * 3});
        List<FaceInfo> faceInfoList1 = new ArrayList<>();
        errorCode = faceEngine.detectFaces(imageInfoEx, DetectModel.ASF_DETECT_MODEL_RGB, faceInfoList1);
        ImageQuality imageQuality1 = new ImageQuality();
        errorCode = faceEngine.imageQualityDetect(imageInfoEx, faceInfoList1.get(0), 0, imageQuality1);
        FunctionConfiguration fun = new FunctionConfiguration();
        fun.setSupportAge(true);
        errorCode = faceEngine.process(imageInfoEx, faceInfoList1, fun);
        List<AgeInfo> ageInfoList1 = new ArrayList<>();
        int age = faceEngine.getAge(ageInfoList1);
        FaceFeature feature = new FaceFeature();
        errorCode = faceEngine.extractFaceFeature(imageInfoEx, faceInfoList1.get(0), ExtractType.REGISTER, 0, feature);
        errorCode = faceEngine.updateFaceData(imageInfoEx, faceInfoList1);

        //设置活体测试
        errorCode = faceEngine.setLivenessParam(0.5f, 0.7f, 0.3f);
        System.out.println("设置活体活体阈值errorCode:" + errorCode);

        LivenessParam livenessParam=new LivenessParam();
        errorCode = faceEngine.getLivenessParam(livenessParam);

        //注册人脸信息1
        FaceFeatureInfo faceFeatureInfo = new FaceFeatureInfo();
        faceFeatureInfo.setSearchId(5);
        faceFeatureInfo.setFaceTag("FeatureData1");
        faceFeatureInfo.setFeatureData(faceFeature.getFeatureData());
        errorCode = faceEngine.registerFaceFeature(faceFeatureInfo);

        //注册人脸信息2
        FaceFeatureInfo faceFeatureInfo2 = new FaceFeatureInfo();
        faceFeatureInfo2.setSearchId(6);
        faceFeatureInfo2.setFaceTag("FeatureData2");
        faceFeatureInfo2.setFeatureData(faceFeature2.getFeatureData());
        errorCode = faceEngine.registerFaceFeature(faceFeatureInfo2);

        //获取注册人脸个数
        FaceSearchCount faceSearchCount = new FaceSearchCount();
        errorCode = faceEngine.getFaceCount(faceSearchCount);
        System.out.println("注册人脸个数:" + faceSearchCount.getCount());

        //搜索最相似人脸
        SearchResult searchResult = new SearchResult();
        errorCode = faceEngine.searchFaceFeature(faceFeature, CompareModel.LIFE_PHOTO, searchResult);
        System.out.println("最相似人脸Id:" + searchResult.getFaceFeatureInfo().getSearchId());

        //更新人脸信息
        FaceFeatureInfo faceFeatureInfo3 = new FaceFeatureInfo();
        faceFeatureInfo3.setSearchId(6);
        faceFeatureInfo3.setFaceTag("FeatureData2Update");
        faceFeatureInfo3.setFeatureData(faceFeature2.getFeatureData());
        errorCode = faceEngine.updateFaceFeature(faceFeatureInfo3);

        //移除人脸信息
        errorCode = faceEngine.removeFaceFeature(6);

        //引擎卸载
        errorCode = faceEngine.unInit();


    }
}
