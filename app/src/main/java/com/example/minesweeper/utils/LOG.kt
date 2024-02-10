package com.example.minesweeper.utils

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember

class Ref(var value: Int)

const val TAG = "LOGCOMPOSTION"

@Composable
inline fun LogComposition(tag: String, msg: String) {
    val ref = remember {Ref(0)}
    SideEffect {
        ref.value++
    }
    Log.i(tag, "com: ${ref.value} for $msg")
}
