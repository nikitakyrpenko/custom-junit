package annotations.test.exception;

public class AssertionFailureError extends AssertionError{

    public AssertionFailureError() {
        super();
    }

    public AssertionFailureError(String message) {
        super(message);
    }
}
