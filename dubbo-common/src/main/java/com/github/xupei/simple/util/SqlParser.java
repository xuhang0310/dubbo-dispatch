package com.github.xupei.simple.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SqlParser {
	
private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * ����sql
	 * @param sql
	 * @param ctxMap
	 * @return
	 * @throws Exception
	 */
	public static String parse(String sql, Map<String, String> ctxMap) throws Exception {
		//1��У��
		if(sql == null || "".equals(sql)) {
			return sql;
		}
		
		//2��Ԥ����
		sql = preprocess(sql, ctxMap);
		
		//3���滻����
		if(sql.indexOf("~") == -1) {
			return sql;
		}
		sql = replace(sql, ctxMap, "");
		
		//4������sql
		return sql;
	}
	
	
	/**
	 * Ԥ����
	 * @param index
	 * @param sql
	 * @param paramsMap
	 * @throws Exception
	 */
	private static String preprocess(String sql, Map<String, String> ctxMap) throws Exception {
		//��ñ�������ʼ���λ��
		int start = sql.indexOf("{");
		if(start == -1) {
			return sql;
		}
		int end = sql.indexOf("}");
		//��ñ���
		String variable = sql.substring(start + 1, end);
		//��ñ�����ֵ
		String value = null;
		if(variable.startsWith("s_")) {//ϵͳ����
			SysVar sysVar = (SysVar)SpringContextUtil.getBean(variable);
			value = sysVar.getValue(null);
		}else {//�û�����
			value = ctxMap.get(variable);
		}
		
		//��ñ���������
		int start2 = sql.indexOf("[");
		int end2 = sql.indexOf("]");
		
		if(value == null) {
			if(start2 != -1 && start2 < start) {
				sql = sql.substring(0, start2) + sql.substring(end2 + 1);
				int start3 = sql.indexOf("$");
				int end3 = sql.indexOf("#");
				if(start3 != -1 && end3 != -1) {
					sql = sql.substring(0, start3) + sql.substring(end3 + 1);
				}
			}else {
				throw new Exception("In parameter map(" + ctxMap.toString() + "),there is not a value for variable '" + variable + "'");
			}
		}else {
			sql = sql.replaceFirst("\\{", "~");
			sql = sql.replaceFirst("\\}", "@");
			if(start2 != -1 && start2 < start) {
				sql = sql.replaceFirst("\\[", "");
				sql = sql.replaceFirst("\\]", "");
				sql = sql.replaceFirst("\\$", "");
				sql = sql.replaceFirst("\\#", "");
			}
		}
		return preprocess(sql, ctxMap);
	}
	
	/**
	 * �滻����
	 * @return
	 * @throws Exception
	 */
	private static String replace(String sql, Map<String, String> ctxMap, String sql2) throws Exception {
		//��ñ�������ʼ���λ��
		int start = sql.indexOf("~");
		if(start == -1) {
			return sql2 + sql;
		}
		int end = sql.indexOf("@");
		//��ñ���
		String variable = sql.substring(start + 1, end);
		//��ñ�����ֵ
		String value = null;
		if(variable.startsWith("s_")) {//ϵͳ����
			SysVar sysVar = (SysVar)SpringContextUtil.getBean(variable);
			value = sysVar.getValue(null);
		}else {//�û�����
			value = ctxMap.get(variable);
		}
		
		sql2 += sql.substring(0, start);
		sql2 += value;
		
		sql = sql.substring(end + 1);
		
		return replace(sql, ctxMap, sql2);
	}
	
	public static String addPagingInfo(String sql) {
		return "select * from (select t.*,rownum rn2 from (" + sql + ") t) t where rn2 between {start} and {start} + {limit} - 1";
	}
	
	public static Map<String, String> escape4select(Map<String, String> paramsMap) {
		Map<String, String> ctxMap = new HashMap();
		Iterator<String> it = paramsMap.keySet().iterator();
		while(it.hasNext()) {
			String key = it.next();
			String value = paramsMap.get(key);
			if(value == null || "".equals(value)) {
				continue;
			}
			value = value.trim();
			//escape
			value = value.replaceAll("%", "\\\\%");
			value = value.replaceAll("'", "''");
			ctxMap.put(key, value);
		}
		return ctxMap;
	}
	
	
	public static Map<String, String> escape4selectObject(Map<String, Object> paramsMap) {
		Map<String, String> ctxMap = new HashMap();
		Iterator<String> it = paramsMap.keySet().iterator();
		while(it.hasNext()) {
			String key = it.next();
			String value = paramsMap.get(key)+"";
			if(value == null || "".equals(value)) {
				continue;
			}
			value = value.trim();
			//escape
			value = value.replaceAll("%", "\\\\%");
			value = value.replaceAll("'", "''");
			ctxMap.put(key, value);
		}
		return ctxMap;
	}
	
	public static Map<String, String> escape4insert(Map<String, String> paramsMap) {
		Map<String, String> ctxMap = new HashMap();
		Iterator<String> it = paramsMap.keySet().iterator();
		while(it.hasNext()) {
			String key = it.next();
			String value = paramsMap.get(key);
			if(value == null) {
				continue;
			}
			value = value.trim();
			//escape
			value = value.replaceAll("'", "''");
			ctxMap.put(key, value);
		}
		return ctxMap;
	}
	public static Map<String, String> escape4update(Map<String, String> paramsMap, String[] whereParams, String[] setParams) {
		Map<String, String> ctxMap = new HashMap();
		if(whereParams != null) {
			for(int i = 0; i < whereParams.length; i ++) {
				String key = whereParams[i];
				String value = paramsMap.get(key);
				if(value == null) {
					continue;
				}
				value = value.trim();
				//escape
				value = value.replaceAll("%", "\\\\%");
				value = value.replaceAll("'", "''");
				ctxMap.put(key, value);
			}
		}
		if(setParams != null) {
			for(int i = 0; i < setParams.length; i ++) {
				String key = setParams[i];
				String value = paramsMap.get(key);
				if(value == null) {
					continue;
				}
				value = value.trim();
				value = value.replaceAll("'", "''");
				ctxMap.put(key, value);
			}
		}
		return ctxMap;
	}

}
