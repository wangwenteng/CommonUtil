package com.wwt.commonutil.util.runnable;

import com.alibaba.fastjson.JSONObject;

import java.util.concurrent.*;

public class CompletionServiceTest {
	public static void main(String[] args) throws Exception {
		ExecutorService pool = Executors.newFixedThreadPool(5);
		CompletionService<JSONObject> cService = new ExecutorCompletionService<JSONObject>(pool);
		cService.submit(new Task());
		cService.submit(new Task1());
		long start = System.currentTimeMillis();
		Future<JSONObject> future = cService.take();
		System.out.println("method:" + future.get());
		long end = System.currentTimeMillis();
		System.out.println(end - start);
		pool.shutdown();
	}
}
