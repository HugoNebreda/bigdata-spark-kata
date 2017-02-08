package com.andriantomanga.bigdata.spark.datagenerators

import java.io._

object Generator {

  def main(args: Array[String]) = {

    println("start : generation of csv file")
    val names = List("marie", "joseph", "ali", "john")

    val pw = new PrintWriter(new File("csv/data.csv"))
    for (i <- 1 to 10000) {

      val name = names(getIdx(0, names.length - 1))
      val age = getIdx(20, 70)
      val year = getIdx(2015, 2017)

      pw.write("" + name + "," + age.toString() + "," + year.toString() + "\n")
    }
    pw.close()
    println("end : generation of csv file")
  }

  def getIdx(start: Int, end: Int): Int = {
    
    val rnd = new scala.util.Random
    start + rnd.nextInt((end - start) + 1)
  }
}