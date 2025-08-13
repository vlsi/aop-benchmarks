# AOP Benchmarks

The repository holds jmh-based benchmarks for AOP implementations.

## Benchmarks

* `@Cacheable` method: [CacheableBenchmark.java](src/main/java/io/github/vlsi/benchmarks/aop/cacheable/CacheableBenchmark.java)

  Here are the preliminary results with Java 21, Apple M1:

        Benchmark                         (implementation)  Mode  Cnt     Score     Error   Units
        CacheableBenchmark                          direct  avgt    5     0,707 ±   0,020   ns/op
        CacheableBenchmark:gc.alloc.rate            direct  avgt    5    ≈ 10⁻⁵              B/op
        CacheableBenchmark                    spring-cglib  avgt    5   190,043 ±   3,304   ns/op
        CacheableBenchmark:gc.alloc.rate      spring-cglib  avgt    5   608,001 ±   0,001    B/op
        CacheableBenchmark                 spring-jdkproxy  avgt    5   176,445 ±  10,604   ns/op
        CacheableBenchmark:gc.alloc.rate   spring-jdkproxy  avgt    5   592,001 ±   0,001    B/op
        CacheableBenchmark                          manual  avgt    5     7,408 ±   0,295   ns/op
        CacheableBenchmark:gc.alloc.rate            manual  avgt    5    ≈ 10⁻⁴              B/op

  Quick notes:
    * Spring CGLIB and JDK proxy implementations are significantly slower than a manual implementation
    * Spring CGLIB and JDK proxy implementations allocate ~600 bytes for every `@Cacheable` call