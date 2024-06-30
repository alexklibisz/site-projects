object Main {
  def main(args: Array[String]): Unit = {
    println((new SortingFindTopK).findTopK(Array(5, 2, 3, -1, 4), 3).mkString(","))
    println((new HeapFindTopK).findTopK(Array(5, 2, 3, -1, 4), 3).mkString(","))
    println((new HistogramFindTopK).findTopK(Array(5, 2, 3, -1, 4), 3).mkString(","))
  }
}
