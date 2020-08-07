package cn.caishen.domain.utils;

import java.util.Comparator;
import java.util.LinkedHashMap;

import org.apache.commons.lang.exception.NestableRuntimeException;

public class DayoMap extends LinkedHashMap<String, Object> implements Comparator<DayoMap> {

	private static final long serialVersionUID = 1L;

	public int compare(DayoMap arg0, DayoMap arg1) {
		return new Integer(arg0.getName()).compareTo(new Integer(
				arg1.getName()));
	}

	private String getName() {
		return getString("name");
	}
	
	public String getString(String key) {
		Object obj = get(key);
		return obj == null ? "" : obj.toString();
	}
	
	public int getInt(String key) {
		Object o = get(key);
		if (o == null) {
			return 0;
		}else if("".equals(o)){
			return 0;
		}else{
			return o instanceof Number ? ((Number) o).intValue(): (int) getDouble(key);
		}
	}	
	
	public boolean getBoolean(String key){
		Object obj = get(key);
		return obj == null ? false : Boolean.valueOf(obj.toString());
	}	
	
	public double getDouble(String key){
		Object o = get(key);
		if (o != null&& !o.toString().equals("")) {
			try {
				return o instanceof Number ? ((Number) o).doubleValue(): Double.parseDouble((String) o);
			} catch (Exception e) {
				throw new NestableRuntimeException("DayoMap[" + key + "] is not a number. value = ["+o+"]");
			}
		}else{
			return 0.0;
		}
	}	
	
	public static DayoMap setResult(boolean result, String msg) {
		DayoMap resultMap = new DayoMap();
		resultMap.put("result", result);
		resultMap.put("msg", msg);
		return resultMap;
	}	
}
