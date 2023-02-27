# jdk-incubator-vector-optimization

Source code for the blog post [Accelerating vector operations on the JVM using the new jdk.incubator.vector module](https://alexklibisz.com/2023/02/25/accelerating-vector-operations-jvm-jdk-incubator-vector-project-panama.html)

To run the benchmarks:

1. Install Oracle JDK 19, e.g., `$ asdf install java oracle-jdk-19.0.2`
2. Install SBT, e.g., `$ brew install sbt`
3. Run the benchmarks via sbt-jmh: `$ sbt "Jmh/run"` 
