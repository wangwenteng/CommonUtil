package com.wwt.commonUtil.util.runnable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wwt.commonUtil.util.httpExt.OkHttpUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.Callable;
@Slf4j
public class Llvision00062TwoService implements Callable<JSONObject> {
    private int similarityNum;
    private String sourcePicBase64;
    private String landmarkJSON;
    private String queryPathLandMark = "/api/v1/identification/landmark";
    private String ipAddress = "http://10.33.48.53";

    public Llvision00062TwoService(int similarityNum, String sourcePicBase64, String landmarkJSON, String ipAddress) {
        this.similarityNum = similarityNum;
        this.sourcePicBase64 = sourcePicBase64;
        this.landmarkJSON = landmarkJSON;
        this.ipAddress = ipAddress;
    }
    public JSONObject getCommonParam() {
        JSONObject commonParam = new JSONObject();
        commonParam.put("version", "V1.0");
        commonParam.put("seqnum", 1);
        commonParam.put("from", "");
        commonParam.put("to", "");
        commonParam.put("type", "CWBS");
        commonParam.put("number", "1-1-1-1");
        return commonParam;
    }

    @Override
    public JSONObject call() throws Exception {
        String landmarkResult = compareFaceLandmark(sourcePicBase64, landmarkJSON);
        JSONObject result = new JSONObject();
        result.put("flag","landmark2");
        result.put("data",JSON.parseArray(landmarkResult));
        //添加返回结果标识
        return result;
    }

    public String compareFaceLandmark(String baseImage64, String landmarkJSON) {
        JSONObject json = JSON.parseObject(landmarkJSON);
        Map mapType = JSON.parseObject(json.toJSONString() ,Map.class);
        JSONObject data = new JSONObject();
        data.put("resultnumber", similarityNum);
        data.put("image", baseImage64);
        data.put("boundingbox", mapType);
        JSONObject commonParam = getCommonParam();
        commonParam.put("data", data);
        log.info(commonParam.toJSONString());
        String responseString = OkHttpUtils.jsonOkHttp(ipAddress + queryPathLandMark, commonParam);
        if (null != responseString) {
            JSONObject compareFace = (JSONObject) JSONObject.parse(responseString);
            if (isResponseOk(compareFace)) {
                JSONObject dataJSON = compareFace.getJSONObject("data");
                JSONArray list = dataJSON.getJSONArray("list");
                if (null != list && list.size() > 0) {
                    return list.toString();
                }
            }
        }
        return null;
    }
    public boolean isResponseOk(JSONObject responseJSON) {
        return responseJSON.getIntValue("code") == 0;
    }










}
