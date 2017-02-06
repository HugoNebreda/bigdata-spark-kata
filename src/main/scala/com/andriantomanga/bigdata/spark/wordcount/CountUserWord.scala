package com.andriantomanga.bigdata.spark.wordcount

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._

/**
 * @author andriantomanga
 */
object CountUserWord {
  def main(args:Array[String]) = {
    
    println("Enter your word : ")
    val word = Console.readLine
    
    val conf = new SparkConf().setMaster("local").setAppName("CountUserWorld")
    
    val sc = new SparkContext(conf)
    val data = sc.textFile("data.txt")
    
    val result = data.flatMap (_.split(" ")).filter(x => x.equals(word))
    
    println("Word " + word + " occours " + result.count() + " times in data.txt")
    
  }
}