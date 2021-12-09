package com.yuanbaogo.network.parser;

public class ErrorResponse {
	private String message;
	private String status;
	private long timestamp;

	public void setMessage(String message) {
		this.message = message;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage(){
		return message;
	}

	public String getStatus(){
		return status;
	}

	public long getTimestamp(){
		return timestamp;
	}

	@Override
	public String toString() {
		return "ErrorResponse{" +
				"message='" + message + '\'' +
				", status='" + status + '\'' +
				", timestamp=" + timestamp +
				'}';
	}
}
