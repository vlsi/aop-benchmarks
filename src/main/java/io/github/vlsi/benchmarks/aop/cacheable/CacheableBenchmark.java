package io.github.vlsi.benchmarks.aop.cacheable;

import org.openjdk.jmh.annotations.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class CacheableBenchmark {
    int value;

    @Param({"direct", "spring-cglib", "spring-jdkproxy", "manual"})
    String implementation;

    Calculator calculator;

    @Setup
    public void setup() {
        if ("direct".equals(implementation)) {
            calculator = new PlainCalculator();
        } else if ("spring-cglib".equals(implementation)) {
            calculator = new AnnotationConfigApplicationContext(CacheableCglibConfig.class).getBean(Calculator.class);
        } else if ("spring-jdkproxy".equals(implementation)) {
            calculator = new AnnotationConfigApplicationContext(CacheableJdkProxyConfig.class).getBean(Calculator.class);
        } else if ("manual".equals(implementation)) {
            calculator = new AnnotationConfigApplicationContext(ManualCaching.class).getBean(Calculator.class);
        }
    }

    @Benchmark
    public int identity() {
        return calculator.identity(value);
    }
}


