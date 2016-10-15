import scala.collection.generic._
import scala.collection._
import scala.language.higherKinds

/**
 * @author Tobin Yehle
 */
object Driver {
  def main(args: Array[String]): Unit = {
    println("Hello")

    val t = new RedHeap(Ordering.Int)
    val u = RedHeap.empty[Int]
    val v = u.map(_*2)
    println(v)

//    val s = Seq(1,2,3).map(_*2)
//    val l = List(1,2,3).combinations(2)
//
    val ss = SortedSet(1, 2, 3)
    val backwards = SortedSet(1, 2, 3)(Ordering.Int.reverse)
    println(backwards.firstKey)
    println(backwards.map(identity).firstKey)

    println("Sorted Maps")

    val sm = SortedMap(1 -> "a", 5 -> "f")(Ordering.Int.reverse)
    println(sm.firstKey)
    println(sm.range(7, 3))
    println(sm.map(identity).firstKey)

    val x:RedHeap[Int] = t.merge(u)(RedHeap.redMerger)
  }
}

trait HeapLike[A, +This <: Heap[A] with HeapLike[A, This]] extends SeqLike[A, This] {
  protected[this] override def newBuilder: mutable.Builder[A, This] = ???
  def merge[O, To](other: O)(implicit m: CanMerge[This, O, To]): To = m.merge(repr, other)
}

trait Heap[A] extends Seq[A] with HeapLike[A, Heap[A]] with GenericOrderedTraversableTemplate[A, Heap] {
  def first: A
}
object Heap extends OrderedTraversableFactory[Heap] {
  override def empty[A](implicit ord: Ordering[A]): Heap[A] = ???
  override def newBuilder[A](implicit ord: Ordering[A]): mutable.Builder[A, Heap[A]] = ???
}

trait CanMerge[-L, -R, +To] {
  def merge(left: L, right: R): To
}

class RedHeap[E](val ord: Ordering[E]) extends Heap[E] with HeapLike[E, RedHeap[E]] with GenericOrderedTraversableTemplate[E, RedHeap] {
  override def first: E = ???
  override def length: Int = ???
  override def apply(idx: Int): E = ???
  override def iterator: Iterator[E] = Iterator.empty
  override def orderedCompanion: GenericOrderedCompanion[RedHeap] = RedHeap
}
object RedHeap extends OrderedTraversableFactory[RedHeap] {
  override def empty[E](implicit ord: Ordering[E]): RedHeap[E] = new RedHeap[E](ord)

  implicit def canBuildFrom[A](implicit ord: Ordering[A]):CanBuildFrom[Coll, A, RedHeap[A]] = new CanBuildFrom[Coll, A, RedHeap[A]] {
    override def apply(from: Coll): mutable.Builder[A, RedHeap[A]] = ???
    override def apply(): mutable.Builder[A, RedHeap[A]] = ???
  }

  implicit def redMerger[E]:CanMerge[RedHeap[E], RedHeap[E], RedHeap[E]] = new CanMerge[RedHeap[E], RedHeap[E], RedHeap[E]] {
    override def merge(left: RedHeap[E], right: RedHeap[E]): RedHeap[E] = {
      println("merging red")
      ???
    }
  }

  override def newBuilder[A](implicit ord: Ordering[A]): mutable.Builder[A, RedHeap[A]] = ???
}

class BlueHeap[E](val ord: Ordering[E]) extends Heap[E] with HeapLike[E, BlueHeap[E]] with GenericOrderedTraversableTemplate[E, BlueHeap] {
  override def first: E = ???
  override def length: Int = ???
  override def apply(idx: Int): E = ???
  override def iterator: Iterator[E] = ???
  override def orderedCompanion: GenericOrderedCompanion[BlueHeap] = BlueHeap
}
object BlueHeap extends OrderedTraversableFactory[BlueHeap] {
  override def newBuilder[A](implicit ord: Ordering[A]): mutable.Builder[A, BlueHeap[A]] = ???
}
