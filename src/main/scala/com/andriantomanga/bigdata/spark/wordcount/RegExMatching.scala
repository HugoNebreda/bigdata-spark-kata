package com.andriantomanga.bigdata.spark.wordcount

import org.apache.spark.{SparkContext, SparkConf}

/**
 * @author andriantomanga
 */
object RegExMatching {
  def main(args:Array[String]) = {
    
    val sc = new SparkContext(new SparkConf().setMaster("local").setAppName("Regular expression matching"))
    
    
    val data = Seq(("Pizza Malagasy", 1000), ("Cheese Burger", 200), ("Pizza Hot and Spicy",5000))
    
    // Creating new RDD from data and using regular expression
    
    val dataRdd = sc.parallelize(data).map{
      case (label, price) => 
        val regEx = "(P|p)izza.*".r
        val pizza = regEx.findFirstIn(label)
        val series = "Something"
        (label, price, pizza, series)
    }
    
    // print the new collections 
    dataRdd.collect().foreach(println)
    sc.stop()
    
    /*
     	(Pizza Malagasy,1000,Some(Pizza Malagasy),Something)
			(Cheese Burger,200,None,Something)
			(Pizza Hot and Spicy,5000,Some(Pizza Hot and Spicy),Something)
     */
  }
}