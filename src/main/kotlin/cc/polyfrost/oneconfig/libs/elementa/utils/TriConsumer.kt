package cc.polyfrost.oneconfig.libs.elementa.utils

@FunctionalInterface
interface TriConsumer <T, U, V> {
    fun accept(t: T, u: U, v: V)
}