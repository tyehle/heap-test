import scala.collection.mutable

/**
 * @author Tobin Yehle
 */
class HeapBuilder[A, Coll <: Heap[A] with HeapLike[A, Coll]](empty: Coll) extends mutable.Builder[A, Coll] {
  protected var elems: Coll = empty
  def +=(x: A): this.type = ???
  def clear() { elems = empty }
  def result: Coll = elems
}
