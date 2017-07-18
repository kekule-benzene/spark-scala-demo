name := "Spark Redis Test"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies += "org.apache.spark" %% "spark-sql" % "2.2.0"

unmanagedJars in Compile += file("/tmp/data/jars/jedis-2.7.0.jar")
unmanagedJars in Compile += file("/tmp/data/jars/spark-redis-0.3.2-jar-with-dependencies.jar")