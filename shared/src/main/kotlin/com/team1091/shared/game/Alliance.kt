package com.team1091.shared.game

import java.awt.Color

enum class Alliance(
        val color: Int
) {
    RED(Color(1f, 0f, 0f).rgb),
    BLUE(Color(0f, 0f, 1f).rgb)
}
