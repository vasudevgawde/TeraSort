import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf
import java.io.{File, BufferedWriter, FileWriter}

import org.apache.spark.SparkContext

import scala.collection.immutable.ListMap

object SparoSort {

  def main(args: Array[String]) {
    val logFile = "spark-1.6.1-bin-hadoop2.6/vasudevlogs/log"
    val conf = new SparkConf().setAppName("SparkSort").set("spark.executor.memory", "2g")
    val sc = new SparkContext(conf)
    val file = new File("final")
    val br = new BufferedWriter(new FileWriter(file))
    val mydata = sc.textFile(logFile)
    val dts = mydata.flatMap(line => line.split("\n"))

    val arrayCl = dts.flatMap(line => Map(line.substring(0,10) -> line.substring(10,line.length)))
    val arrMap = arrayCl.map(line => line._1 -> line._2)
    val sortedData = arrMap.sortBy(_._1)
    val collectedData = sortedData.collect()
    collectedData.foreach(line => br.write(line._1 + line._2 + "\r\n"))

  }
}
