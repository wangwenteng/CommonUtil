package com.wwt.commonUtil.util.runnable;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.Callable;

@Service
public class Llvision00007ServiceTask implements Callable<JSONObject> {
    private Logger log = LoggerFactory.getLogger("");
//    @Resource
//    private Llvision00007Service llvision00007Service;
    private String imageBase64;
    private int numResults;
    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    public int getNumResults() {
        return numResults;
    }

    public void setNumResults(int numResults) {
        this.numResults = numResults;
    }

    @Override
    public JSONObject call() throws Exception {
        long start = System.currentTimeMillis();
        log.info("start llvision00007ServiceTask >......");
        //String compareFaceResult = llvision00007Service.compareFace(getImageBase64(),getNumResults());
        JSONObject result = new JSONObject();
        result.put("flag", "llvision00007");
//        if(StringUtils.isNotEmpty(compareFaceResult)){
//            result.put("data",JSON.parseArray(compareFaceResult));
//        }else{
//            result.put("data",new JSONArray());
//        }
        long end = System.currentTimeMillis();
        //添加返回结果标识
        log.info("Llvision00007ServiceTask----(end - start){}",(end - start));
        return result;
    }


}
