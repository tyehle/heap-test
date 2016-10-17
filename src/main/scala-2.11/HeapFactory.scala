import scala.collection.generic.{CanBuildFrom, GenericOrderedTraversableTemplate, OrderedTraversableFactory}
import scala.collection.mutable
import scala.language.higherKinds

/**
 * @author Tobin Yehle
 */
abstract class HeapFactory[CC[X] <: Heap[X] with GenericOrderedTraversableTemplate[X, CC]] extends OrderedTraversableFactory[CC] {
  implicit def CBFNoOrder[A](implicit ord: Ordering[A]): CanBuildFrom[Coll, A, Heap[A]] = new CanBuildFrom[Coll, A, Heap[A]] {
    override def apply(from: Coll): mutable.Builder[A, Heap[A]] = newBuilder
    override def apply(): mutable.Builder[A, Heap[A]] = newBuilder
  }
}
