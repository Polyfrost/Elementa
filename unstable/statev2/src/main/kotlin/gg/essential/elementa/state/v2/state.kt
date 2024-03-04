package gg.essential.elementa.state.v2

import gg.essential.elementa.state.v2.ReferenceHolder
import gg.essential.elementa.state.v2.impl.Impl
import gg.essential.elementa.state.v2.impl.legacy.LegacyImpl

private val impl: Impl = LegacyImpl

interface Observer {
    /**
     * Get the current value of the State object and subscribe the observer to be re-evaluated when it changes.
     */
    operator fun <T> State<T>.invoke(): T = with(this@Observer) { get() }
}

object Untracked : Observer

fun <T> memo(func: Observer.() -> T): State<T> = impl.memo(func)
fun <T> State<T>.memo(): State<T> = memo inner@{ this@memo() }

fun effect(referenceHolder: ReferenceHolder, func: Observer.() -> Unit): () -> Unit = impl.effect(referenceHolder, func)

fun <T> State<T>.onChange(referenceHolder: ReferenceHolder, func: Observer.(value: T) -> Unit): () -> Unit {
    var first = true
    return effect(referenceHolder) {
        val value = this@onChange()
        if (first) {
            first = false
        } else {
            func(value)
        }
    }
}

/**
 * The base for all Elementa State objects.
 *
 * State objects are essentially just a wrapper around a value. However, the ability to be deeply
 * integrated into an Elementa component allows some nice functionality.
 *
 * The primary advantage of using state is that a single state object can be shared between multiple
 * components or constraints. This allows one value update to be seen by multiple components or
 * constraints. For example, if a component has many text children, and they all share the same
 * color state variable, then whenever the value of the state object is updated, all of the text
 * components will instantly change color.
 *
 * Another advantage arises when using Kotlin, as States can be delegated to. For more information,
 * see delegation.kt.
 */
fun interface State<out T> {
    /**
     * Get the current value of this State object and subscribe the observer to be re-evaluated when it changes.
     */
    fun Observer.get(): T

    /**
     * Get the current value of this State object.
     */
    fun getUntracked(): T = with(Untracked) { get() }

  /** Get the value of this State object */
  @Deprecated("Calls to this method are not tracked. If this is intentional, use `getUntracked` instead.")
  fun get(): T = getUntracked()

  /**
   * Register a listener which will be called whenever the value of this State object changes
   *
   * The listener registration is weak by default. This means that no strong reference to the
   * listener is kept in this State object and your listener may be garbage collected if no other
   * strong references to it exist. Once a listener is garbage collected, it will (obviously) no
   * longer receive updates.
   *
   * Keeping a strong reference to your own listener is easy to forget, so this method requires you
   * to explicitly pass in an object which will maintain a strong reference to your listener for
   * you. With that, your listener will stay active **at least** as long as the given [owner] is
   * alive (unless the returned callback in invoked).
   *
   * In general, the lifetime of your listener should match the lifetime of the passed [owner],
   * usually the thing (e.g. [UIComponent]) the listener is modifying. If the owner far outlives
   * your listener, you may be leaking memory because the owner will keep all those listeners and
   * anything they reference alive far beyond the point where they are needed. If your listener
   * outlives the owner, then it may become inactive sooner than you expected and whatever it is
   * updating might no longer update properly.
   *
   * If you wish to manually keep your listener alive, pass [ReferenceHolder.Weak] as the owner.
   *
   * @return A callback which, when invoked, removes this listener
   */
  @Deprecated("If this method is used to update dependent states, use `stateBy` instead.\n" +
          "Otherwise the State system cannot be guaranteed that downsteam states have a consistent view of upstream" +
          "values (i.e. so called \"glitches\" may occur) and all dependences will be forced to evaluate eagerly" +
          "instead of the usual lazy behavior (where states are only updated if there is a consumer).\n" +
          "\n" +
          "If this method is used to drive a final effect (e.g. updating some non-State UI property), and you also" +
          "care about the initial value of the state, consider using `effect` instead.\n" +
          "If you really only care about changes and not the inital value, use `onChange`.")
  fun onSetValue(owner: ReferenceHolder, listener: (T) -> Unit): () -> Unit = onChange(owner) { listener(it) }
}

/* ReferenceHolder is defined in Elementa as:
/**
 * Holds strong references to listeners to prevent them from being garbage collected.
 * @see State.onSetValue
 */
interface ReferenceHolder {
  fun holdOnto(listener: Any): () -> Unit

  object Weak : ReferenceHolder {
    override fun holdOnto(listener: Any): () -> Unit = {}
  }
}
 */

/** A [State] with a value that can be changed via [set] */
@JvmDefaultWithoutCompatibility
interface MutableState<T> : State<T> {
  /**
   * Update the value of this State object.
   *
   * After the value has been updated, all listeners of this State object are notified.
   *
   * The provided lambda must be a pure function which will return the new value for this State give
   * the current value.
   *
   * Note that while most basic State implementations will call the lambda and notify listeners
   * immediately, there is no general requirement for them to do so, and specialized State
   * implementations may delay either or both to e.g. batch multiple updates together.
   */
  fun set(mapper: (T) -> T)

  /**
   * Update the value of this State object.
   *
   * After the value has been updated, all listeners of this State object are notified.
   *
   * Note that while most basic State implementations will update and notify listeners immediately,
   * there is no general requirement for them to do so, and specialized State implementations may
   * delay either or both to e.g. batch multiple updates together.
   *
   * @see [set]
   */
  fun set(value: T) = set { value }
}

/** A [State] delegating to a configurable target [State] */
interface DelegatingState<T> : State<T> {
  fun rebind(newState: State<T>)
}

/** A [MutableState] delegating to a configurable target [MutableState] */
@JvmDefaultWithoutCompatibility
interface DelegatingMutableState<T> : MutableState<T> {
  fun rebind(newState: MutableState<T>)
}

/** Creates a new [State] with the given value. */
fun <T> stateOf(value: T): State<T> = ImmutableState(value)

/** Creates a new [MutableState] with the given initial value. */
fun <T> mutableStateOf(value: T): MutableState<T> = impl.mutableState(value)

/** Creates a new [DelegatingState] with the given target [State]. */
fun <T> stateDelegatingTo(state: State<T>): DelegatingState<T> = impl.stateDelegatingTo(state)

/** Creates a new [DelegatingMutableState] with the given target [MutableState]. */
fun <T> mutableStateDelegatingTo(state: MutableState<T>): DelegatingMutableState<T> =
    impl.mutableStateDelegatingTo(state)

/** Creates a [State] which derives its value in a user-defined way from one or more other states */
@Deprecated("See `State.onSetValue`. Use `stateBy` instead.")
fun <T> derivedState(
    initialValue: T,
    builder: (owner: ReferenceHolder, derivedState: MutableState<T>) -> Unit,
): State<T> = impl.derivedState(initialValue, builder)

/** A simple, immutable implementation of [State] */
private class ImmutableState<T>(private val value: T) : State<T> {
  override fun get(): T = value
  override fun onSetValue(owner: ReferenceHolder, listener: (T) -> Unit): () -> Unit = {}
  override fun Observer.get(): T = value
  override fun getUntracked(): T = value
}

/** A simple implementation of [ReferenceHolder] */
class ReferenceHolderImpl : ReferenceHolder {
  private val heldReferences = mutableListOf<Any>()

  override fun holdOnto(listener: Any): () -> Unit {
    heldReferences.add(listener)
    return { heldReferences.remove(listener) }
  }
}
