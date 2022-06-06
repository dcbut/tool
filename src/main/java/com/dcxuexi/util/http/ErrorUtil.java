package com.dcxuexi.util.http;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;



/**
 * <p>Title: 将异常转成字符串 输出，方便打印</p>
 * <p>Description: 完成一些对异常转成字符串的工作</p>
 *
 * @version 1.0.0
 */
public class ErrorUtil {
	  /**
		 * 将异常转成字符串 输出，方便打印
		 * @date
		 * @param e
		 * @return
		 */
		public static String getErrorInfoFromException(Exception e) {  
	        try {  
	            StringWriter sw = new StringWriter();  
	            PrintWriter pw = new PrintWriter(sw);  
	            e.printStackTrace(pw);  
	            return  "：：：Exception---->>>>>>>>>>"+sw.toString() + "\r\n";  
	        } catch (Exception e2) {  
	            return "bad getErrorInfoFromException";  
	        }  
	    } 
		
		public static String mapToStringSort(Map<String,String> map){
			String retunVR = "";
			if(map!=null&& map.size()>0){
				StringBuffer sb=new StringBuffer();
				Object[] key =  map.keySet().toArray();   
		        Arrays.sort(key);  
		        for (int   i   =   0;   i   <   key.length;   i++)   {   
		            sb.append(key[i]).append("=").append(map.get(key[i])).append("&");
		        }
		        retunVR = sb.substring(0, sb.length()-1);	       
			}
			return retunVR;
		}
		
		/**
		 * 
		 * @author: husy
		 * @param map
		 * @param connector 连接符 =
		 * @param separator 分割符 &
		 * @return
		 */
		public static String mapToStringSort(Map<String,String> map,String connector,String separator){
			String retunVR = "";
			if(map!=null&& map.size()>0){
				StringBuffer sb=new StringBuffer();
				Object[] key =  map.keySet().toArray();   
		        Arrays.sort(key);  
		        for (int   i   =   0;   i   <   key.length;   i++)   {   
		            sb.append(key[i]).append(connector).append(map.get(key[i])).append(separator);
		        }
		        if(!"".equals(separator)){
		        	retunVR = sb.substring(0, sb.length()-1);	       
		        }else{
		        	retunVR=sb.toString();
		        }
			}
			return retunVR;
		}
		
		/**
		 * 获取4位随机编号 
		 * @return
		 */
		public static String getRandom4(){
			Random random = new  Random();
			return (random.nextInt(9000)+1000)+"";
		}
}
