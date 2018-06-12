package cn.true123;

public class DLException extends RuntimeException {

    public DLException() {
        super();
    }

    public DLException(String message) {
        super(message);
    }

    public DLException(String message, Throwable cause) {
        super(message, cause);
    }

    public DLException(Throwable cause) {
        super(cause);
    }

    protected DLException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
