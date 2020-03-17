package fr.tangv.web.util;

public enum CodeHTTP {
	
	CODE_200_OK(200, "OK"),
	CODE_300_MULTIPLE_CHOICES(300, "Multiple Choices"),
	CODE_301_MOVED_PERMANENTLY(301, "Moved Permanently"),
	CODE_302_FOUND(302, "Found"),
	CODE_303_SEE_OTHER(303, "See Other"),
	CODE_400_BAD_REQUEST(400, "Bad Request"),
	CODE_401_UNAUTHORIZED(401, "Unauthorized"),
	CODE_402_PAYMENT_REQUIRED(402, "Payment Required"),
	CODE_403_FORBIDDEN(403, "Forbidden"),
	CODE_404_NOT_FOUND(404, "Not Found"),
	CODE_405_METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
	CODE_500_INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
	CODE_503_SERVICE_UNAVAILABLE(503, "Service Unavailable"),
	CODE_504_GATEWAY_TIME_OUT(504, "Gateway Time-out"),
	CODE_505_HTTP_VERSION_NOT_SUPPORTED(505, "HTTP Version not supported");
	
	private int id;
	private String message;
	
	private CodeHTTP(int id, String message) {
		this.id = id;
		this.message = message;
	}
	
	public int getId() {
		return id;
	}
	
	public String getMessage() {
		return message;
	}
	
	@Override
	public String toString() {
		return id+" "+message;
	}
	
}
