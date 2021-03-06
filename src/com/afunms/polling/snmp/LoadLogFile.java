package com.afunms.polling.snmp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.afunms.initialize.ResourceCenter;



/*
 * @author nielin@dhcc.com.cn
 *
 */



/**
 * @author Administrator
 *
 * 此类用于解析 大小额日志文件 该类为重庆三峡银行定制  
 */
public class LoadLogFile {
	
	public Hashtable getTuxedoInfo(String ip) throws IOException{
		
		//String name = "E:\\"+ip+".tuxedo.log";
		String filename = ResourceCenter.getInstance().getSysPath() + "/linuxserver/cics_logfile.txt";
		//String filename = "D:/Tomcat5.0/webapps/afunms/linuxserver/cics_logfile.txt";
		//System.out.println(filename+"=========");
		BufferedReader in= new BufferedReader(new FileReader(filename));
		String cc=in.readLine(); 
	    StringBuffer buffer=new StringBuffer(); 

	    while(cc!=null) 
	    { 
	    	 buffer.append(cc).append("\r\n");        	 
	    	 cc=in.readLine(); 
	    } 
	    in.close();
	    
		Hashtable<String, List> returnHashtable = new Hashtable<String,List>();
	    String[] re1 = buffer.toString().trim().split("\\n");
	    String result = buffer.toString().trim().replaceAll(" " , "") ;
	    String[] re2 = result.split("\\n");
//	    System.out.println(result);
	    String timeStr=re1[0];
	    int psrBegin = 0;
	    int pscBegin = 0;
	    int pqBegin = 0;
	    int pcltBegin = 0;
	    
	    
	    
	    List valueList = new ArrayList();
	    List falseList = new ArrayList();
	    List resultList = null;
	    
	    List contentList = null;
	    
	    String time = null;
	    String resultType = null;
	    String content = null;
	    
	    
	    
	    for(int i =0;i<re1.length;i++)	{
//	    	System.out.println(re1[i]);
	    	
	    	String value = re1[i].trim();
	    	
	    	if(value == null || value.length()==0){
	    		continue;
	    	}
	    	
	    	if(value.indexOf("错误码")!=-1){
	    		falseList.add(praseFalseByte(value));
	    	}
	    	
	    	
	    	if(value.charAt(0)=='[' && value.charAt(value.length()-1)==']'){
	    		System.out.println(value);
	    		time = value.substring(1, value.length()-1);
	    	}
	    	
	    	if("-----------接收报文内容-----------".equals(value) || "-----------发送报文内容-----------".equals(value)){
	    		String resultType_temp = value;
	    		
	    		resultType = resultType_temp.replaceAll("-", "");
	    	}
	    	
	    	if(value.charAt(0) == '{'){
//	    		content = value;
	    		contentList = praseContent(value);
	    	}
	    	
	    	if("-----------END-----------".equals(value)){
	    		resultList = new ArrayList();
	    		resultList.add(contentList);
	    		resultList.add(resultType);
	    		resultList.add(time);
	    		valueList.add(resultList);
	    	}
	    	
	    }

//	    System.out.println("(" + (char)32 + "|" + (char)9 + ")+");
//
//		List<String[]> tuxSr = new ArrayList<String[]>();
//		List<String[]> tuxSc = new ArrayList<String[]>();
//		List<String[]> tuxQ = new ArrayList<String[]>();
//		List<String[]> tuxClt = new ArrayList<String[]>();
//                
//		for(int i = psrBegin+2;i<pscBegin;i++){
//			String value = re1[i];
//			String s1="\\( ";
//			String s2=" \\)";
//			value=value.replaceFirst(s1,"");
//			value=value.replaceFirst(s2, "");
//			String[] re=value.split("(" + (char)32 + "|" + (char)9 + ")+");
//			tuxSr.add(re);
//		}
//		for(int i = pscBegin+2 ; i<pqBegin;i++){
//			String[] re=re1[i].split("(" + (char)32 + "|" + (char)9 + ")+");
//			tuxSc.add(re);		
//		}
//		for(int i = pqBegin+2 ; i<pcltBegin;i++){
//			String[] re=re1[i].split("(" + (char)32 + "|" + (char)9 + ")+");
//			tuxQ.add(re);		
//		}
//		for(int i = pcltBegin+2 ; i<re1.length-1;i++){
//			String[] re=re1[i].split("(" + (char)32 + "|" + (char)9 + ")+");
//			tuxClt.add(re);		
//		}
//		List<Hashtable> srList = new ArrayList<Hashtable>();
//		List<Hashtable> scList = new ArrayList<Hashtable>();
//		List<Hashtable> pqList = new ArrayList<Hashtable>();
//		List<Hashtable> cltList = new ArrayList<Hashtable>();
//		if(tuxSr!=null&& tuxSr.size()>0){
//			for(int i =0;i<tuxSr.size();i++){
//				String[] s1= tuxSr.get(i);
//				if(s1.length>=7){
//					Hashtable<String,String> ts = new Hashtable<String,String>();
//                                        ts.put("currentTime", timeStr);
//					for(int j=0;j<s1.length;j++){
//						if(j==0)
//							ts.put("ProgName", s1[j].trim());
//						if(j==1)
//							ts.put("QueueName", s1[j].trim());
//						if(j==2)
//							ts.put("GrpName", s1[j].trim());
//						if(j==3)
//							ts.put("Id", s1[j].trim());
//						if(j==4)
//							ts.put("RqDone", s1[j].trim());
//						if(j==5)
//							ts.put("LoadDone", s1[j].trim());
//						if(j==6)
//							ts.put("CurrentService", s1[j].trim());						
//					}
//					srList.add(ts);
//				}
//			}
//		}
//		if(tuxSc!=null&& tuxSc.size()>0){
//			for(int i =0;i<tuxSc.size();i++){
//				String[] s1= tuxSc.get(i);
//				if(s1.length>=8){
//					Hashtable<String,String> ts = new Hashtable<String,String>();
//                                        ts.put("currentTime", timeStr);
//					for(int j=0;j<s1.length;j++){
//						if(j==0)
//							ts.put("ServiceName", s1[j].trim());
//						if(j==1)
//							ts.put("RoutineName", s1[j].trim());
//						if(j==2)
//							ts.put("ProgName", s1[j].trim());
//						if(j==3)
//							ts.put("GrpName", s1[j].trim());
//						if(j==4)
//							ts.put("Id", s1[j].trim());
//						if(j==5)
//							ts.put("Machine", s1[j].trim());
//						if(j==6)
//							ts.put("Done", s1[j].trim());
//						if(j==7)
//							ts.put("Avail", s1[j].trim());
//						
//					}
//					scList.add(ts);
//				}
//			}
//		}
//		if(tuxQ!=null&& tuxQ.size()>0){
//			for(int i =0;i<tuxQ.size();i++){
//				String[] s1= tuxQ.get(i);
//				if(s1.length>=7){
//					Hashtable<String,String> ts = new Hashtable<String,String>();
//                                        ts.put("currentTime", timeStr);
//					for(int j=0;j<s1.length;j++){
//						if(j==0)
//							ts.put("ProgName", s1[j].trim());
//						if(j==1)
//							ts.put("QueueName", s1[j].trim());
//						if(j==2)
//							ts.put("Serve", s1[j].trim());
//						if(j==3)
//							ts.put("WkQueued", s1[j].trim());
//						if(j==4)
//							ts.put("Queued", s1[j].trim());
//						if(j==5)
//							ts.put("AveLen", s1[j].trim());
//						if(j==6)
//							ts.put("Machine", s1[j].trim());
//						
//					}
//					pqList.add(ts);
//				}
//			}
//		}
//		if(tuxClt!=null&& tuxClt.size()>0){
//			for(int i =0;i<tuxClt.size();i++){
//				String[] s1= tuxClt.get(i);
//				if(s1.length>=6){
//					Hashtable<String,String> ts = new Hashtable<String,String>();
//                                        ts.put("currentTime", timeStr);
//					for(int j=0;j<s1.length;j++){
//						if(j==0)
//							ts.put("LMID", s1[j].trim());
//						if(j==1)
//							ts.put("UserName", s1[j].trim());
//						if(j==2)
//							ts.put("ClientName", s1[j].trim());
//						if(j==3)
//							ts.put("Time", s1[j].trim());
//						if(j==4)
//							ts.put("Status", s1[j].trim());
//						if(j==5)
//							ts.put("Bca", s1[j].trim());
//						
//					}
//					cltList.add(ts);
//				}
//			}
//		}
//		ta.put("Server", srList);
//		ta.put("Service", scList);
//		ta.put("Queue", pqList);
//		ta.put("Client", cltList);
//		
	    returnHashtable.put("valueList", valueList);
	    returnHashtable.put("falseList", falseList);
		return returnHashtable;
	}
	
	private List praseFalseByte(String content){
		List list = new ArrayList();
		
		String value = content;
		
		String time = null;
		
		int timeStart = value.indexOf('[');
		
		int timeEnd = value.indexOf(']');
		if(timeStart != -1 && timeEnd !=-1 && timeStart <= timeEnd &&  timeEnd < value.length()){
    		System.out.println(value);
    		time = value.substring(timeStart+1, timeEnd);
    		list.add(time);
    		
    		list.add(value.substring(timeEnd+1));
    	}
		return list;
	}
	
	
	
	private List praseContent(String content){
		List list = new ArrayList();
		int start = content.indexOf('{');
		int end = content.indexOf('}');
		if(start!=-1&&end!=-1){
			list.add(content.substring(start+1, end));
			list.add(content.substring(end));
		}
		return list;
	}
	
	public static void main(String[] args){
		try {
			new LoadLogFile().getTuxedoInfo("");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}






