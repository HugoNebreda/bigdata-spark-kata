package com.andriantomanga.bigdata.spark.sql

import org.apache.spark.{ SparkContext, SparkConf }
import org.apache.spark.sql._
import java.util.StringTokenizer

/**
 * @author andriantomanga
 */
object SQLContextTest {
  def main(args: Array[String]) = {
   
    // set spark conf 
    val sc = new SparkContext(new SparkConf().setMaster("local").setAppName("Spark Context"))

    val sqlCtx = new SQLContext(sc)

    // Reading from json file 
    val dfs = sqlCtx.read.json("json/data.json");

    // show data in the dataframe 
    dfs.show()

    // the data structure 
    dfs.printSchema()
    dfs.select("name").show()
    dfs.select("age").filter(dfs("age") > 0).show()
    dfs.filter(dfs("age") > 0).groupBy("age").count().show()

    // register as a temp table 
    dfs.registerTempTable("users")

    // find distinct 
    val distinctSubscriptionDates = dfs.sqlContext.sql("select distinct subs_date from users order by subs_date desc")
    distinctSubscriptionDates.collect().foreach(println)

    // Reading from csv file
    val subscribers = sqlCtx.read.format("com.databricks.spark.csv").option("header", "true").option("inferSchema", "true").load("csv/data.csv")
    subscribers.registerTempTable("subscribers")
    val distinctNames = sqlCtx.sql("select distinct name from subscribers")
    distinctNames.collect().foreach(println)

    // average age per year
    val averageAgePerYear = sqlCtx.sql("select year, round(avg(age), 0) as average from subscribers group by year")
    averageAgePerYear.printSchema()
    averageAgePerYear.collect().foreach(r => printDF(r.toString()))
    
    /*
     	Annee : 2015, Age moyen : 45
			Annee : 2016, Age moyen : 45
			Annee : 2017, Age moyen : 45
     */
  }

  /**
   * print each row 
   */
  def printDF(row: String) = {
    
    val st = new StringTokenizer(row, ",[] ")
    val sb = StringBuilder.newBuilder

    sb.append("Annee : ").append(st.nextToken()).append(", Age moyen : ").append(st.nextToken().toDouble.toInt)
    println(sb.toString())
  }

}