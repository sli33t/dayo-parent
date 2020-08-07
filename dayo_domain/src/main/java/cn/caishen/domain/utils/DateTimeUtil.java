package cn.caishen.domain.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
/**
 * 时间工具类
 * @author ZS
 *
 */
public class DateTimeUtil{
		public static String dateAdd(String date, int addDay) {
			if ("".equals(date)) {
				return "";
			}

			SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
			Date d;
			try {
				d = f.parse(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "";
			}	
			Calendar calendar = new GregorianCalendar(); 
			calendar.setTime(d); 
			calendar.add(Calendar.DATE, addDay); //把日期往后增加一天,整数  往后推,负数往前移动 
			d=calendar.getTime(); //这个时间就是日期往后推一天的结果 		
			return f.format(d);				
		}	
	
		public static Boolean time(String timestamp){
			//获取系统时间
			Date xdate=new Date();
			////将请求时间从String转换为Data
				//设置日期格式
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				try {
					Date qdate = df.parse(timestamp);
					long diff = xdate.getTime() - qdate.getTime(); 
					//超过10分钟
					if(diff>600000||diff<-600000){
						return false;
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
				return true;
		}
		//日期转秒
		 public Long hTOs(String str){
			  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			  Long millionSeconds =null;
			try {
				millionSeconds = (sdf.parse(str).getTime()/1000);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return millionSeconds;
	    }
	//http编码转换  xml串
	public String httpCod(String str){
					String s;
					try {
						s = URLDecoder.decode(str,"gbk");
						return s;
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return null;
	 }
}