package com.alibaba.alisonar.factory;

import org.springframework.http.HttpStatus;

import com.alibaba.alisonar.dto.ResultDTO;

public class ResultDTOFactory {

	public static <T> ResultDTO<T> toAck(T data) {
		ResultDTO<T> dto = new ResultDTO<T>();
		dto.setCode(HttpStatus.OK.value());
		dto.setMessage("SUCCESS");
		dto.setData(data);
		return dto;
	}

	public static <T> ResultDTO<T> toNack(int code, String msg) {
		ResultDTO<T> dto = new ResultDTO<T>();
		dto.setCode(code);
		dto.setMessage(msg);
		return dto;
	}

}
