package com.wwt.commonUtil.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

//Http请求的工具类    
public class HttpUtils {    
   
  private static final int TIMEOUT_IN_MILLIONS = 5000;    
   
  public interface CallBack {  
      void onRequestComplete(String result);  
  }  

  /** 
   * 异步的Get请求 
   *  
   * @param urlStr 
   * @param callBack 
   */  
  public static void doGetAsyn(final String urlStr, final CallBack callBack) {  
      new Thread() {  
          public void run() {  
              try {  
                  String result = doGet(urlStr);  
                  if (callBack != null) {  
                      callBack.onRequestComplete(result);  
                  }  
              } catch (Exception e) {  
                  e.printStackTrace();  
              }  

          };  
      }.start();  
  }  

  /** 
   * 异步的Post请求 
   * @param urlStr 
   * @param params 
   * @param callBack 
   * @throws Exception 
   */  
  public static void doPostAsyn(final String urlStr, final String params,  
          final CallBack callBack) throws Exception {  
      new Thread() {  
          public void run() {  
              try {  
                  String result = doPost(urlStr, params);  
                  if (callBack != null) {  
                      callBack.onRequestComplete(result);  
                  }  
              } catch (Exception e) {  
                  e.printStackTrace();  
              }  

          };  
      }.start();  

  }  
   
  /**  
   * Get请求，获得返回数据  
   *   
   * @param urlStr  
   * @return  
   * @throws Exception  
   */   
  public static String doGet(String urlStr) {    
      URL url = null;    
      HttpURLConnection conn = null;    
      InputStream is = null;    
      ByteArrayOutputStream baos = null;    
      try {    
          url = new URL(urlStr);    
          conn = (HttpURLConnection) url.openConnection();    
          conn.setReadTimeout(TIMEOUT_IN_MILLIONS);    
          conn.setConnectTimeout(TIMEOUT_IN_MILLIONS);    
          conn.setRequestMethod("GET");    
          conn.setRequestProperty("accept", "*/*");    
          conn.setRequestProperty("connection", "Keep-Alive");    
          if (conn.getResponseCode() == 200) {    
              is = conn.getInputStream();    
              baos = new ByteArrayOutputStream();    
              int len = -1;    
              byte[] buf = new byte[128];    
   
              while ((len = is.read(buf)) != -1) {    
                  baos.write(buf, 0, len);    
              }    
              baos.flush();    
              return baos.toString();    
          } else {    
              throw new RuntimeException(" responseCode is not 200 ... ");    
          }    
   
      } catch (Exception e) {    
          e.printStackTrace();    
      } finally {    
          try {    
              if (is != null) {
            	  is.close();
              } 
          } catch (IOException e) { 

          }    
          try {
              if (baos != null) {
            	  baos.close();
              }
          } catch (IOException e) {   
        	  
          }    
          conn.disconnect();    
      }    

      return null ;    
   
  }    
   
  /**    
   * 向指定 URL 发送POST方法的请求    
   *     
   * @param url    
   *            发送请求的 URL    
   * @param param    
   *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。    
   * @return 所代表远程资源的响应结果    
   * @throws Exception    
   */   
  public static String doPost(String url, String param) {    
      PrintWriter out = null;    
      BufferedReader in = null;    
      String result = "";    
      try {    
          URL realUrl = new URL(url);    
          // 打开和URL之间的连接    
          HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();    
          // 设置通用的请求属性    
          conn.setRequestProperty("accept", "*/*");    
          conn.setRequestProperty("connection", "Keep-Alive");    
          conn.setRequestMethod("POST");    
          conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");    
          conn.setRequestProperty("charset", "utf-8");    
          conn.setUseCaches(false);    
          // 发送POST请求必须设置如下两行    
          conn.setDoOutput(true);    
          conn.setDoInput(true);    
          conn.setReadTimeout(TIMEOUT_IN_MILLIONS);    
          conn.setConnectTimeout(TIMEOUT_IN_MILLIONS);    
   
          if (param != null && !param.trim().equals("")) {    
              // 获取URLConnection对象对应的输出流    
              out = new PrintWriter(conn.getOutputStream());    
              // 发送请求参数    
              out.print(param);    
              // flush输出流的缓冲    
              out.flush();    
          }    
          // 定义BufferedReader输入流来读取URL的响应    
          in = new BufferedReader(new InputStreamReader(conn.getInputStream()));    
          String line;    
          while ((line = in.readLine()) != null) {    
              result += line;    
          }    
      } catch (Exception e) {    
          e.printStackTrace();    
      }    
      // 使用finally块来关闭输出流、输入流    
      finally {    
          try {    
              if (out != null) {    
                  out.close();    
              }    
              if (in != null) {    
                  in.close();    
              }    
          } catch (IOException ex) {    
              ex.printStackTrace();    
          }    
      }
      return result;    
	}

	public static void main(String[] args) throws Exception {
		// String longitude ="";
		// String latitude ="";
		// int userId = 90;
		// String pudong_url =
		// "http://tic.shang-group.com/face/api/uploadface.php";
		// String sourcePicBase64 =
		// FileBase64ConvertUitl.encodeBase64File("D:\\1516952602299.jpg");
		// String newSourcePicBase64 = sourcePicBase64.replace("=", "%3D");
		// newSourcePicBase64 = newSourcePicBase64.replace("/", "%2F");
		// newSourcePicBase64 = newSourcePicBase64.replace("+", "%2B");
		// String param = "img=" + newSourcePicBase64 + "&picid=" +
		// UUID.randomUUID().toString() + "&devno=" + userId
		// + "&lng=" + longitude + "&lat=" + latitude;
		// String doPost = HttpUtils.doPost(pudong_url, param);
		// System.out.println(doPost);
		String str = "{\"status\":1,\"msg\":\"成功\",\"data\":{\"input\":{\"picid\":\"b0cb54fb-1bec-480a-ba97-f97295b7e537\",\"devno\":\"90\",\"lng\":\"\",\"lat\":\"\",\"img_base64_size\":31992,\"img_size\":23377},\"server_image_id\":\"48\",\"server_path\":\"2018/02/06/20180206150707-00770cec5047d2af56ee4b349bc4fe2f.jpg\"}}";
		Map parse = JSONObject.parseObject(str, Map.class);
		int status = (int) parse.get("status");
		System.out.println(parse);
		System.out.println(status);
	}
}  
