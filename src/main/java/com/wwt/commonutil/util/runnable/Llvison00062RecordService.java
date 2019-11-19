package com.wwt.commonutil.util.runnable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by lifan on 2019/6/26.
 */
@Service
public class Llvison00062RecordService {
    private static final Logger log = LoggerFactory.getLogger("");

    @Value("${similarityNum}")
    private int similarityNum;
    @Value("${llvision00062.ipAddress}")
    private String llvision00062OneIpAddress;
    @Value("${llvision00062.ipAddress}")
    private String llvision00062TwoIpAddress;
    public String faceRecord(Integer userId, String token, String results, String sourcePicBase64,String bgImageId,String longitude, String latitude,
                             HttpServletRequest request, String cardNo, String cardCode, String landmarkJSON) throws Exception {
        long startTime = System.currentTimeMillis();
        log.info("人脸比对开始:" + startTime);
        Map<String, Object> data = new HashMap<>();
        JSONArray resultList = JSON.parseArray(results);
        int thisId = 0;
        // 识别照片处理
        // 标准识别
        if (thisId == 0) {
            // 无识别结果时
            ExecutorService pool = Executors.newFixedThreadPool(5);
            CompletionService<JSONObject> cService = new ExecutorCompletionService<JSONObject>(pool);
            cService.submit(new Llvision00062OneService(similarityNum,sourcePicBase64,landmarkJSON, llvision00062OneIpAddress));
            cService.submit(new Llvision00062TwoService(similarityNum,sourcePicBase64,landmarkJSON, llvision00062TwoIpAddress));
            Future<JSONObject> future = cService.take();
            JSONObject result = future.get();
            System.out.println("method:" + result);
            //判断返回的结果的标识
            List<Map<String, Object>> personList = null;
            String flag = result.getString("flag");
            if(flag.equals("landmark1")){
                String faceList = result.getJSONArray("data").toJSONString();
                personList = getCompareFaceResultYitu(userId, request, faceList);
            }else{
                String faceList = result.getJSONArray("data").toJSONString();
                personList = getCompareFaceResultShangTang(userId, request, faceList);

            }
            resultList = JSON.parseArray(JSONObject.toJSONString(personList));

        }
        data.put("token", token);
        data.put("userId", userId);
        data.put("create_user", userId);
        log.info("resultList：" + resultList);
        log.info("thisId：" + thisId);
        return null;

    }

    /**
     * @param userId
     * @param request
     * @param faceList
     * @return
     * @throws Exception
     */
    private List<Map<String, Object>> getCompareFaceResultYitu(Integer userId, HttpServletRequest request, String faceList)
            throws Exception {
        List<Map<String, Object>> personList = new ArrayList<>();
        long jsonStart = System.currentTimeMillis();
        JSONArray comparisonList = JSON.parseArray(faceList);
        if (comparisonList != null ) {
            // 人脸比对结果
            Map<String, Object> dataMap;
            JSONObject ob;
            int similarity;
            for (int i = 0; i < comparisonList.size(); i++){
                dataMap = new HashMap<>();
                ob = comparisonList.getJSONObject(i);
                Double parseDouble = Double.parseDouble(ob.getString("value"));
                similarity = (int) (parseDouble * 100D);
                String faceId = ob.getString("name");
                dataMap.put("personId", faceId);
                dataMap.put("similarity", (double) similarity);
                personList.add(dataMap);
            }
        } else {
            personList = null;
        }
        long jsonEnd = System.currentTimeMillis();
        System.out.println("json解析时长：" + (jsonEnd - jsonStart));
        return personList;
    }
    /**
     * @param userId
     * @param request
     * @param faceList
     * @return
     * @throws Exception
     */
    private List<Map<String, Object>> getCompareFaceResultShangTang(Integer userId, HttpServletRequest request, String faceList)
            throws Exception {
        List<Map<String, Object>> personList = new ArrayList<>();
        long jsonStart = System.currentTimeMillis();
        JSONArray comparisonList = JSON.parseArray(faceList);
        if (comparisonList != null ) {
            // 人脸比对结果
            Map<String, Object> dataMap;
            JSONObject ob;
            int similarity;
            for (int i = 0; i < comparisonList.size(); i++){
                dataMap = new HashMap<>();
                ob = comparisonList.getJSONObject(i);
                Double parseDouble = Double.parseDouble(ob.getString("value"));
                similarity = (int) (parseDouble * 100D);
                String faceId = ob.getString("name");
                dataMap.put("personId", faceId);
                dataMap.put("similarity", (double) similarity);
                personList.add(dataMap);
            }
        } else {
            personList = null;
        }
        long jsonEnd = System.currentTimeMillis();
        System.out.println("json解析时长：" + (jsonEnd - jsonStart));
        return personList;
    }
}
