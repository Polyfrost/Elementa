package org.polyfrost.elementa.state.v2.combinators

import org.polyfrost.elementa.state.v2.State

operator fun <A, B> State<Pair<A, B>>.component1(): State<A> = this.map { it.first }
operator fun <A, B> State<Pair<A, B>>.component2(): State<B> = this.map { it.second }
