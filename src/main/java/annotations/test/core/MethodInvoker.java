package annotations.test.core;

import annotations.test.exception.AssertionFailureError;
import annotations.test.exception.AssertionSuccessError;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodInvoker {
    private static final String SUCCESS_MESSAGE = "%s METHOD INVOCATION SUCCESSED %s";
    private static final String FAILURE_MESSAGE = "%s METHOD INVOCATION FAILED %s";

    private final Object testableObject;

    public MethodInvoker(Object testableObject){
        this.testableObject = testableObject;
    }

    public String processMethod(Method method){
        Class<? extends Throwable> expectedException = extractException(method);
        return invokeMethod(method, expectedException);
    }

    private Class<? extends Throwable> extractException(Method method){
        return method.getAnnotation(TestRunner.class).exception();
    }

    public String invokeMethod(Method method, Class<? extends Throwable> expectedException){
        String result = null;
        try {
            method.invoke(testableObject);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            Class<? extends Throwable> actual = e.getCause().getClass();
            if (actual == AssertionSuccessError.class){
                result = generateReportMessage(method.getName(), e.getCause().getMessage(), true);
            }else if(actual == AssertionFailureError.class){
                result = generateReportMessage(method.getName(), e.getCause().getMessage(), false);
            }else if(actual == expectedException){
                String message = String.format("- EXPECTED : %s; ACTUAL - %s", expectedException, actual);
                result = generateReportMessage(method.getName(), message, true);
            }else {
                String message = String.format("- EXPECTED : %s; ACTUAL - %s", expectedException, actual);
                result = generateReportMessage(method.getName(), message, false);
            }
        }
        return result;
    }


    private String generateReportMessage(String methodName, String message, boolean executionResult){
        if (executionResult){
            return String.format(SUCCESS_MESSAGE, methodName, message);
        }
       return String.format(FAILURE_MESSAGE, methodName, message);
    }


}
