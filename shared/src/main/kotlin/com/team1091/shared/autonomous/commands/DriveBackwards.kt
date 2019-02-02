package com.team1091.shared.autonomous.commands

import com.team1091.shared.control.RobotComponents
import com.team1091.shared.math.Length

class DriveBackwards(
        components: RobotComponents,
        distance: Length
) : DriveForwards(components, Length(-distance.distance))