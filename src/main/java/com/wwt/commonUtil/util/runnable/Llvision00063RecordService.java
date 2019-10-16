package com.wwt.commonUtil.util.runnable;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by lifan on 2019/6/26.
 */
@Service
public class Llvision00063RecordService {
    private static final Logger log = LoggerFactory.getLogger("");
//    @Resource
//    private FaceRecordDao faceRecordDao;
//    @Resource
//    private CooperationService cooperationService;
//    @Resource
//    private RecordFunction recordFunction;
//    @Resource
//    private SystemConfService systemConfService;
    @Resource
    private Llvision00007ServiceTask llvision00007ServiceTask;
    @Resource
    private Llvision00030ServiceTask llvision00030ServiceTask;
//    @Resource
//    private PersonService personService;
//    @Resource
//    private Llvision00030Service llvision00030Service;
//    @Resource
//    private Llvision00007Service llvision00007YituService;
    @Value("${similarityNum}")
    private int similarityNum;



    /**
     * @param userId
     * @param request
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getPersonList(Integer userId, HttpServletRequest request, String sourcePicBase64,String pathName)
            throws Exception {
        //开启线程池
        ExecutorService pool = Executors.newFixedThreadPool(5);
        //并发任务
        CompletionService<JSONObject> cService = new ExecutorCompletionService<JSONObject>(pool);
        llvision00007ServiceTask.setImageBase64(sourcePicBase64);
        llvision00007ServiceTask.setNumResults(similarityNum);
        llvision00030ServiceTask.setFilePath(request.getSession().getServletContext().getRealPath("/") + pathName);
        cService.submit(llvision00007ServiceTask);
        cService.submit(llvision00030ServiceTask);
        Future<JSONObject> future = cService.take();
        JSONObject result = future.get();
        List<Map<String, Object>> personList = null;
        if(null!=result){
            String faceList = result.getJSONArray("data").toJSONString();
//            if ("llvision00007".equals(result.getString("flag"))) {
//                personList = getComPareFaceResultForYT(userId, request, faceList);
//            } else if ("llvision00030".equals(result.getString("flag"))) {
//                personList = getComPareFaceResultForST(userId, request, faceList);
//            }
        }
        return personList;
    }


}
