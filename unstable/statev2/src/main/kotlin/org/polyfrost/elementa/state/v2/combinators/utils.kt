package org.polyfrost.elementa.state.v2.combinators

import org.polyfrost.elementa.state.v2.MutableState

fun MutableState<Int>.reorder(vararg mapping: Int) =
    bimap({ mapping[it] }, { mapping.indexOf(it) })
