package annotations.test.core;

import annotations.test.collector.ReportCollector;
import annotations.test.exception.AssertionSetupException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Stream;

public class AnnotationRunner {

    private  Class<?> cls;
    private  MethodInvoker methodInvoker;
    private  ReportCollector collector;

    public AnnotationRunner(Class<?> cls){
       constructTestableObject(cls);
        this.collector = System.out::println;
    }

    public AnnotationRunner(Class<?> cls, ReportCollector reportCollector){
        constructTestableObject(cls);
        this.collector = reportCollector;
    }

    public void run(){
        filterByAnnotationPresent(cls.getMethods())
                .map(methodInvoker::processMethod)
                .forEach(collector::collectResult);
    }

    private Stream<Method> filterByAnnotationPresent(Method[] methods){
        return Arrays.stream(methods)
                .filter(method -> method.isAnnotationPresent(TestRunner.class));

    }


    private void constructTestableObject(Class<?> cls){
        try {
            this.cls = cls;
            Constructor<?> ctor = cls.getConstructor();
            Object testableObject = ctor.newInstance();
            this.methodInvoker = new MethodInvoker(testableObject);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e ) {
            throw new AssertionSetupException("Testable class should have only default constructor");
        }
    }
}
