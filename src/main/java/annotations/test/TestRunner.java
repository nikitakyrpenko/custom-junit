package annotations.test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface TestRunner {
     Class<? extends Throwable> exception() default None.class;

     class None extends Throwable{
         private None(){}
     }
}
