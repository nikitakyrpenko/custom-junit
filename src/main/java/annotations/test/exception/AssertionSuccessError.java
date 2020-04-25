package annotations.test.exception;

public class AssertionSuccessError extends AssertionError{
    public AssertionSuccessError() {
        super();
    }

    public AssertionSuccessError(String message) {
        super(message);
    }
}
