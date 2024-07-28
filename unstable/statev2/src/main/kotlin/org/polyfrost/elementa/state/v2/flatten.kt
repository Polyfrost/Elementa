package org.polyfrost.elementa.state.v2

fun <T> State<State<T>>.flatten() = stateBy { this@flatten()() }
