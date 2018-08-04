package com.zhongda.museum.config;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

public class StringArrayTypeHandler extends BaseTypeHandler<String[]> {

	@Override
	public String[] getNullableResult(ResultSet arg0, String arg1)
			throws SQLException {
		return stringToStringArr(arg0, arg1);
	}

	@Override
	public String[] getNullableResult(ResultSet arg0, int arg1)
			throws SQLException {
		return stringToStringArr(arg0, arg1);
	}

	@Override
	public String[] getNullableResult(CallableStatement arg0, int arg1)
			throws SQLException {
		return stringToStringArr(arg0, arg1);
	}

	@Override
	public void setNonNullParameter(PreparedStatement arg0, int arg1,
			String[] arg2, JdbcType arg3) throws SQLException {
		arg0.setString(arg1, Arrays.toString(arg2));
	}
	
	/**
	 * 将数据库取出来的字符串转换成javabean的字符数组
	 */
	private String[] stringToStringArr(Object arg0, Object arg1)
			throws SQLException {
		String[] array = null;
		if(arg0 instanceof ResultSet){
			ResultSet resultSet = (ResultSet) arg0;
			if(arg1 instanceof String){
				String columnName = (String) arg1;
				array = resultSet.getString(columnName).split(", ");
			}else{
				int columnIndex = (int) arg1;
				array = resultSet.getString(columnIndex).split(", ");
			}
		}else{
			CallableStatement callableStatement = (CallableStatement) arg0;
			int columnIndex = (int) arg1;
			array = callableStatement.getString(columnIndex).split(", ");
		}
		array[0] = array[0].substring(1);
		array[array.length - 1] = array[array.length - 1].replace("]", "");
		return array;
	}
}
