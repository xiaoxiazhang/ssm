package com.alibaba.alisonar.util;

import org.springframework.http.HttpStatus;

public class ResultDtoFactory {

	public static <T> ResultDto<T> toAck(T data) {
		ResultDto<T> dto = new ResultDto<T>();
		dto.setCode(HttpStatus.OK.value());
		dto.setMessage("SUCCESS");
		dto.setData(data);
		return dto;
	}

	public static <T> ResultDto<T> toNack(int code, String msg) {
		ResultDto<T> dto = new ResultDto<T>();
		dto.setCode(code);
		dto.setMessage(msg);
		return dto;
	}

}
