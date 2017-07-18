// redis-server --protected-mode no
// sbt package && spark-submit --jars /tmp/data/jars/spark-redis-0.3.2-jar-with-dependencies.jar,/tmp/data/jars/jedis-2.7.0.jar target/scala-2.11/spark-redis-test_2.11-1.0.jar

import org.apache.spark.sql.SparkSession
import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import com.redislabs.provider.redis._

object App {
  def main(args: Array[String]) {

	val _sc = new SparkContext(new SparkConf()
		  .setMaster("local")
	  .setAppName("myApp")

	  // initial redis host - can be any node in cluster mode. below is local ip but from docker
	  .set("redis.host", "10.2.2.182")

	  // initial redis port
	  .set("redis.port", "6379")
	)

	_sc.setLogLevel("WARN")

	_sc.toRedisKV(_sc.parallelize(Array( ("foo", "bar") )))
	_sc.toRedisKV(_sc.parallelize(Array( ("bar", "foo") )))
	_sc.toRedisKV(_sc.parallelize(Array( ("a", "12345") )))
	_sc.toRedisKV(_sc.parallelize(Array( ("b", "678910") )))
	_sc.toRedisKV(_sc.parallelize(Array( ("lorem", "ipsum sit dolor amet") )))

	_sc.toRedisHASH(_sc.parallelize(Array( ("12345", "ridwanbejo"), ("12341", "wildan"), ("12344", "tajhul") )), "students")

	val stringRDD = _sc.fromRedisKV(Array("foo", "bar", "a", "b", "lorem"))
	println("Result length:")
	println(stringRDD.count())

	for (x <- stringRDD.collect())
	{
		// println("Ebizu for the win!")
		// println(x.getClass)
		println(x)
		println(x._1 + " --> " + x._2)
	}
  }
}