package com.kh.spring.common;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

public class StringArrayTypeHandler implements TypeHandler<String[]> {

	@Override
	public void setParameter(PreparedStatement ps, int i, String[] parameter, JdbcType jdbcType) throws SQLException {
		//각각의 값들을 하나의 스트링 으로 만들기 위한 로직
		if(parameter!=null) {
			ps.setString(i, String.join(",", parameter));
		}
		else {
			ps.setString(i, "");
		}

	}

	@Override
	public String[] getResult(ResultSet rs, String columnName) throws SQLException {
		//ex) java,c,javaScript로 되어 있는 스트링 값을 columnValueStr에 넣는다
		String columnValueStr = rs.getString(columnName);
		
		return columnValueStr.split(",");
	}

	@Override
	public String[] getResult(ResultSet rs, int columnIndex) throws SQLException {
		String columnValueStr = rs.getString(columnIndex);
		
		return columnValueStr.split(",");
	}

	@Override
	public String[] getResult(CallableStatement cs, int columnIndex) throws SQLException {
		//DB에 function 또는 프로시저를 구동시킬때 사용
		String columnValueStr = cs.getString(columnIndex);
		
		return columnValueStr.split(",");
	}

}
