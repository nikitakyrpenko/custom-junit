package annotations.test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class AnnotationRunner {

    private final Class<?> cls;
    private final Constructor<?> ctor;
    private final Object testableObject;

    public AnnotationRunner(Class<?> cls){
        try {
            this.cls = cls;
            this.ctor = cls.getConstructor();
            this.testableObject = ctor.newInstance();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e ) {
           throw new AssertionError("Tetable class should have only default constructor");
        }
    }

    private Method[] filterByAnnotationPresent(Class<? extends Annotation> annotation, Method[] methods){
        return Arrays.stream(methods)
                .filter(method -> method.isAnnotationPresent(annotation))
                .toArray(Method[]::new);
    }

    public void annotatedMethodRunner(){
        Method[] methods = filterByAnnotationPresent(TestRunner.class,cls.getMethods());
        Arrays.stream(methods).forEach(method -> {
            try {
                method.invoke(testableObject);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                if (e.getCause().getClass().equals(AssertionError.class)){
                    e.getCause().printStackTrace();
                }
            }
        });
    }




}
