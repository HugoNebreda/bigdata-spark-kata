package com.andriantomanga.bigdata.spark.wordcount

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._

/**
 * @author andriantomanga
 */
object Launcher {
  def main(args:Array[String]) = {
    
    // set the configuration 
    val conf = new SparkConf().setMaster("local").setAppName("WordCount")
    val sc = new SparkContext(conf)
    
    // set the input data file 
    val data = sc.textFile("data.txt")
    
    // split in words, map and reduce 
    val result = data.flatMap (_.split(" ")).map( word =>(word, 1)).reduceByKey(_ + _)
    
    // result.toDebugString
     
    // print the pairs in the console 
    result.collect.foreach (println)
  
    // save results in an output text file 
    result.saveAsTextFile("result.txt")
    
    
  }
}