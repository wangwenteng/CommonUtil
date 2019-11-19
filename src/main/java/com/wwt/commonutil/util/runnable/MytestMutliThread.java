package com.wwt.commonutil.util.runnable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wwt.commonutil.util.httpExt.OkHttpUtils;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MultipartBody;
import okhttp3.MultipartBody.Builder;
import okhttp3.RequestBody;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
@Slf4j
public class MytestMutliThread implements Runnable {
	Map<String, Object> postData = new HashMap<String, Object>();
	String totalUrl = "http://10.33.48.53:8019/web/v1/user/getUserList";
	String name = "wangwt";
	String pwd = "123456";

	@Override
	public void run() {
		log.info("start");
		Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM)
				.addFormDataPart("userName", name)
				.addFormDataPart("token", "cab0ae2997e04ac4bc42124ffbac7971")
				.addFormDataPart("userId", "13")
				.addFormDataPart("password", pwd);
		RequestBody requestBody = builder.build();
		String responseStr = OkHttpUtils.fromDataOkHttp(totalUrl, requestBody);
		log.info("responseStr:" + responseStr);
	}

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ExecutorService service = Executors.newFixedThreadPool(10);// 10是线程数
		// for (int i = 0; i < 10; i++) {
		// service.execute(new MytestMutliThread());// 并发50个用户
		// System.out.println(service.isTerminated());
		// }

		ExecutorService executor = Executors.newCachedThreadPool();
		Task task = new Task();
		Task1 task1 = new Task1();

		Set<Callable<JSONObject>> list = new HashSet<>();
		list.add(task);
		list.add(task1);
		JSONObject invokeAny = executor.invokeAny(list);
		System.out.println("invokeAny" + invokeAny.toJSONString());
		executor.shutdown();

		ExecutorService executorService = Executors.newSingleThreadExecutor();
		Set<Callable<String>> callables = new HashSet<Callable<String>>();
		callables.add(new Callable<String>() {
			public String call() throws Exception {
				return "Task 1";
			}
		});
		callables.add(new Callable<String>() {
			public String call() throws Exception {
				return "Task 2";
			}
		});
		callables.add(new Callable<String>() {
			public String call() throws Exception {
				return "Task 3";
			}
		});
		String result = executorService.invokeAny(callables);
		System.out.println("result = " + result);
		executorService.shutdown();

	}
}

@Slf4j
class Task implements Callable<JSONObject> {
	String name = "wangwt";
	String pwd = "123456";
	String totalUrl = "http://10.33.48.53:8019/web/v1/user/getUserList";

	@Override
	public JSONObject call() throws Exception {
		long start = System.currentTimeMillis();
		log.info("start");
		Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM)
				.addFormDataPart("userName", name).addFormDataPart("token", "cab0ae2997e04ac4bc42124ffbac7971")
				.addFormDataPart("userId", "13").addFormDataPart("password", pwd);
		RequestBody requestBody = builder.build();
		String responseStr = OkHttpUtils.fromDataOkHttp(totalUrl, requestBody);
		JSONObject result = JSON.parseObject(responseStr);
		result.put("aaa", "111");
		log.info("responseStr:" + responseStr);
		long end = System.currentTimeMillis();
		System.out.println("task>>>>>" + (end - start));
		return result;

	}

}

@Slf4j
class Task1 implements Callable<JSONObject> {
	String name = "wangwt";
	String pwd = "123456";
	String totalUrl = "http://10.33.48.53:8019/web/v1/user/getUserList";

	@Override
	public JSONObject call() throws Exception {
		long start = System.currentTimeMillis();
		log.info("start1");
		Thread.sleep(4000);
		log.info("start1");
		Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("userName", name)
				.addFormDataPart("token", "cab0ae2997e04ac4bc42124ffbac7971").addFormDataPart("userId", "13")
				.addFormDataPart("password", pwd);
		RequestBody requestBody = builder.build();
		String responseStr = OkHttpUtils.fromDataOkHttp(totalUrl, requestBody);
		JSONObject result = JSON.parseObject(responseStr);
		result.put("bbb", "222");
		log.info("responseStr1:" + responseStr);
		long end = System.currentTimeMillis();
		System.out.println("task1>>>>>" + (end - start));
		Thread.sleep(4000);
		end = System.currentTimeMillis();
		System.out.println("task1>>>>>" + (end - start));
		return result;
	}

}