package com.andriantomanga.bigdata.spark.accumulator

import org.apache.spark.{ SparkContext, SparkConf }
import java.util.StringTokenizer

/**
 * @author andriantomanga
 */
object AccumulatorUse {
  def main(args:Array[String]) = {
    
    // set spark conf 
    val sc = new SparkContext(new SparkConf().setMaster("local").setAppName("Accumulator use"))
    
    // define the accumulator 
    val acc = sc.accumulator(0, "Total of my purchases")
    
    // set the files that will be used 
    sc.textFile("purchases/purchases1.txt,purchases/purchases2.txt,purchases/purchases3.txt")
    
    // remove header of files 
    .mapPartitionsWithIndex{(x, y) => y.drop(1)} // for removing header only from first file {(x,y) => if(x == 0) iter.drop(1) else iter }
    
    // accumulate the prices 
    .foreach(x => acc += getprice(x))
    
    // print the result 
    println(acc.name + " : " + acc.value + " €" )
  }
  
  /**
   * from each line, return the second word as Int
   */
  def getprice(line:String):Int = {
    
    val st = new StringTokenizer(line, " \t") // remove space and tab chars ...
    
    if(st.countTokens() > 1) {
      
      st.nextToken() // Skip the first element ... 
      st.nextToken().toInt
    } else 0
    
  }
}