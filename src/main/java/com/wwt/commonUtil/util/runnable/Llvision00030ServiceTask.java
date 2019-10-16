package com.wwt.commonUtil.util.runnable;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.Callable;

/**
 * 商汤对接的线程任务
 */
@Service
public class Llvision00030ServiceTask implements Callable<JSONObject> {

    private Logger log = LoggerFactory.getLogger("");
//    @Resource
//    private Llvision00030Service llvision00030Service;

    private String filePath;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public JSONObject call(){
        long start = System.currentTimeMillis();
        log.info("start Llvision00030ServiceTask >......");
        //TODO 调用接口/方法
        //String landmarkResult =llvision00030Service.compareFace(filePath);
        JSONObject result = new JSONObject();
        result.put("flag","llvision00030");
//        if(StringUtils.isNotEmpty(landmarkResult)){
//            result.put("data",JSON.parseArray(landmarkResult));
//        }else{
//            result.put("data",new JSONArray());
//        }
        long end = System.currentTimeMillis();
        log.info("Llvision00030ServiceTask----(end - start){}",(end - start));
        //添加返回结果标识
        return result;
    }

}
