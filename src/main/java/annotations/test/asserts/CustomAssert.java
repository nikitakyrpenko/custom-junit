package annotations.test.asserts;

import annotations.test.exception.AssertionFailureError;
import annotations.test.exception.AssertionSuccessError;

public class CustomAssert {

    private CustomAssert(){}

    public static<T> void assertObject(T expected, T actual){
        boolean result = assertObjectByEquals(expected, actual);
        if (result){
            throw new AssertionSuccessError(String.format("- EXPECTED : %s; ACTUAL - %s", expected, actual));
        }else{
            throw new AssertionFailureError(String.format("- EXPECTED : %s; ACTUAL - %s", expected, actual));
        }
    }

    private static<T> boolean assertObjectByEquals(T expected, T actual){
        return expected.equals(actual);
    }
}


