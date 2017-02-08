package com.andriantomanga.bigdata.spark.sql

import org.apache.spark.{ SparkContext, SparkConf }
import org.apache.spark.sql._

/**
 * @author andriantomanga
 */
object TextFileToDataFrame {

  /**
   * 	@see http://docs.scala-lang.org/tutorials/tour/case-classes.html
   */
  case class Subscriber(name: String, age: Int, year: Int)

  def main(args: Array[String]) = {

    val spark = new SparkContext(new SparkConf().setMaster("local").setAppName("Example of manipuling data from file to Dataframe "))

    val sqlContext = new SQLContext(spark)
    import sqlContext.implicits._

    val subscribersDF = spark.textFile("text/subscribers.txt").map(_.split(",")).map(row => Subscriber(row(0), row(1).toInt, row(2).toInt)).toDF()

    // Register the DataFrame as a temporary view
    subscribersDF.createOrReplaceTempView("subscribers")

    // SQL statements can be run from here 
    val youngAdult = sqlContext.sql("select name, age from subscribers s where s.age between 30 and 35 order by age")

    youngAdult.collect().foreach(println)

  }
}