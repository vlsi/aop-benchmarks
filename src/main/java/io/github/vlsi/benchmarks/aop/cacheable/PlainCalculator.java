package io.github.vlsi.benchmarks.aop.cacheable;

import org.springframework.cache.annotation.Cacheable;

public class PlainCalculator implements Calculator {
    @Cacheable("identity")
    @Override
    public int identity(int x) {
        return x;
    }
}
