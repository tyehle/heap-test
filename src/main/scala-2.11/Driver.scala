import scala.Iterator
import scala.collection.generic._
import scala.collection._
import scala.language.higherKinds

/**
 * @author Tobin Yehle
 */
object Driver {
  def main(args: Array[String]): Unit = {
    println("Hello")

    val t = RedHeap.empty[Int]
    val u = RedHeap()(Ordering.Int.reverse)
    val v = u.map(_*2)
    println(v)
    println("retainded ordering: " + (v.ord == u.ord))
    val w:RedHeap[Int] = List.empty[Int].map(_*2)(breakOut)
    println("Found int ordering: " + (w.ord == Ordering.Int))

//    val s = Seq(1,2,3).map(_*2)
//    val l = List(1,2,3).combinations(2)
//
    val ss = SortedSet(1, 2, 3)
    val backwards = SortedSet(1, 2, 3)(Ordering.Int.reverse)
    println(backwards.firstKey)
    println(backwards.map(identity).firstKey)

    val conv = SortedSet(1,2,3)(Ordering.Int.reverse)
    println(conv.map(_.toString)(bf = SortedSet.canBuildFrom(Ordering.String.reverse)).firstKey)

    println("Sorted Maps")

    val sm = SortedMap(1 -> "a", 5 -> "f")(Ordering.Int.reverse)
    println(sm.firstKey)
    println(sm.range(7, 3))
    println(sm.map(identity).firstKey)

    val x:RedHeap[Int] = t.merge(u)(RedHeap.redMerger)
  }
}

trait HeapLike[A, +This <: Heap[A] with HeapLike[A, This]] extends SeqLike[A, This] with Sorted[A, This] {
  protected[this] override def newBuilder: mutable.Builder[A, This] = ???
  def merge[O, To](other: O)(implicit m: CanMerge[This, O, To]): To = m.merge(repr, other)
}

trait Heap[A] extends Seq[A] with HeapLike[A, Heap[A]] with GenericOrderedTraversableTemplate[A, Heap] {
  def first: A
  implicit val ord: Ordering[A]
}
/** Default to RedHeap */
object Heap extends HeapFactory[Heap] {
  override def empty[A](implicit ord: Ordering[A]): Heap[A] = RedHeap.empty[A](ord)
  override def newBuilder[A](implicit ord: Ordering[A]): mutable.Builder[A, Heap[A]] = new HeapBuilder[A, Heap[A]](empty(ord))
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

  override def ordering: Ordering[E] = ord

  override def keySet: SortedSet[E] = ???

  override def firstKey: E = ???

  override def lastKey: E = ???

  override def rangeImpl(from: Option[E], until: Option[E]): RedHeap[E] = ???

  override def keysIteratorFrom(start: E): Iterator[E] = ???
}
object RedHeap extends HeapFactory[RedHeap] {
  override def empty[E](implicit ord: Ordering[E]): RedHeap[E] = new RedHeap[E](ord)

  override def newBuilder[A](implicit ord: Ordering[A]): mutable.Builder[A, RedHeap[A]] = new HeapBuilder[A, RedHeap[A]](empty(ord))

  implicit def CBFKeepOrder[A](implicit ord: Ordering[A]): CanBuildFrom[Sorted[A, _], A, RedHeap[A]] = new CanBuildFrom[Sorted[A, _], A, RedHeap[A]] {
    override def apply(from: Sorted[A, _]): mutable.Builder[A, RedHeap[A]] = newBuilder(from.ordering)
    override def apply(): mutable.Builder[A, RedHeap[A]] = newBuilder(ord)
  }

  implicit def redMerger[E]:CanMerge[RedHeap[E], RedHeap[E], RedHeap[E]] = new CanMerge[RedHeap[E], RedHeap[E], RedHeap[E]] {
    override def merge(left: RedHeap[E], right: RedHeap[E]): RedHeap[E] = {
      println("merging red")
      ???
    }
  }
}

class BlueHeap[E](val ord: Ordering[E]) extends Heap[E] with HeapLike[E, BlueHeap[E]] with GenericOrderedTraversableTemplate[E, BlueHeap] {
  override def first: E = ???
  override def length: Int = ???
  override def apply(idx: Int): E = ???
  override def iterator: Iterator[E] = ???
  override def orderedCompanion: GenericOrderedCompanion[BlueHeap] = BlueHeap

  override def ordering: Ordering[E] = ord

  override def keySet: SortedSet[E] = ???

  override def firstKey: E = ???

  override def lastKey: E = ???

  override def rangeImpl(from: Option[E], until: Option[E]): BlueHeap[E] = ???

  override def keysIteratorFrom(start: E): Iterator[E] = ???
}
object BlueHeap extends OrderedTraversableFactory[BlueHeap] {
  override def newBuilder[A](implicit ord: Ordering[A]): mutable.Builder[A, BlueHeap[A]] = ???
}
