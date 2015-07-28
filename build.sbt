import AssemblyKeys._

////////////////////////////////////////////////////////////////////////
// Based on https://github.com/rssvihla/spark_commons
////////////////////////////////////////////////////////////////////////
val Spark = "1.2.1"
val SparkCassandra = "1.2.1"

val spark_core = "org.apache.spark" % "spark-core_2.10" % Spark % "provided"
val spark_sql = "org.apache.spark" % "spark-sql_2.10" % Spark % "provided"
//val spark_streaming = "org.apache.spark" % "spark-streaming_2.10" % Spark % "provided"
//val spark_streaming_kafka = "org.apache.spark" % "spark-streaming-kafka_2.10" % Spark % "provided"
//val spark_streaming_zeromq = "org.apache.spark" % "spark-streaming-zeromq_2.10" % Spark % "provided"

val spark_connector = ("com.datastax.spark" %% "spark-cassandra-connector" % SparkCassandra withSources() withJavadoc()).
  exclude("com.esotericsoftware.minlog", "minlog").
  exclude("commons-beanutils","commons-beanutils")

val spark_connector_java = ("com.datastax.spark" %% "spark-cassandra-connector-java" % SparkCassandra withSources() withJavadoc()).
  exclude("org.apache.spark","spark-core")
  
val sparkDependencies = Seq(spark_core,
  spark_sql,
//  spark_streaming,
//  spark_streaming_kafka,
//  spark_streaming_zeromq,
  spark_connector,
  spark_connector_java)
  
val testDependencies = Seq(
  "org.scalatest" % "scalatest_2.10" % "2.2.4" % "test",
  "org.mockito" % "mockito-all" % "1.10.19" % "test",
  "com.github.javafaker" % "javafaker" % "0.5")



//lazy val commons = (project in file("commons")).
//  settings(commonSettings: _*)


//lazy val spark_bulk_operations_example = (project in file("examples/spark_bulk_operations"))
//  .dependsOn(commons % "compile->compile;test->test")
//   .settings(commonSettings: _*)


////////////////////////////////////////////////////////////////////////

name := "spark-example"
organization := "com.examples"
scalaVersion := "2.10.5"
version := "1.0.0"

resolvers += "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/"

parallelExecution in Test := false

fork in Test := true

libraryDependencies ++= Seq(
	"com.typesafe.play" %% "play-json" % "2.2.1",
	"org.apache.commons" % "commons-math3" % "3.4.1",
	"net.jpountz.lz4" % "lz4" % "1.2.0"
)
libraryDependencies ++= sparkDependencies
libraryDependencies ++= testDependencies



//We do this so that Spark Dependencies will not be bundled with our fat jar but will still be included on the classpath
//When we do a sbt/run
run in Compile <<= Defaults.runTask(fullClasspath in Compile, mainClass in (Compile, run), runner in (Compile, run))

assemblySettings

jarName in assembly := name.value + "-assembly.jar"