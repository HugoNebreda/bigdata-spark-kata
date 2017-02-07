package com.andriantomanga.bigdata.spark.wordcount

import org.apache.spark.{ SparkContext, SparkConf }

/**
 * @author andriantomanga
 */
object DistinctNamesPerFirstLetter {
  def main(args: Array[String]) = {

    val sc = new SparkContext(new SparkConf().setMaster("local").setAppName("Distinct names per first letter"))

    // sc.textFile("names.txt").map(x =>  (x.charAt(0), x)).groupByKey().collect().foreach(println)

    sc.textFile("names.txt").map(x => (x.charAt(0), x))
      .groupByKey().mapValues {
        x => x.toSet.size
      }.collect().foreach(println)
/*
 
input :

nabil
haja
zaid
narindra
herihaja
hasina
malik
david
justinia
djamila
tadjdine
ali
mokhtar
lucman
ridha

output :

(d,2)
(z,1)
(a,1)
(t,1)
(h,3)
(n,2)
(j,1)
(r,1)
(l,1)
(m,2)

 */
  }
}