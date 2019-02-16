import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.team1091.shared.autonomous.commands.DriveToTarget
import com.team1091.shared.control.ImageInfo
import com.team1091.shared.control.RobotComponents
import com.team1091.shared.system.DriveSystem
import com.team1091.shared.system.ITargetingSystem
import org.junit.Test

class DriveToTargetTest {
    @Test
    fun `Test the drive and turn alignment`() {
        mapOf(
                ImageInfo(seen = true, center = 0.0, distance = 100.0) to Pair(1.0, 0.0)
        ).forEach { imageInfo, driveInput ->

            val (forward, turn) = driveInput

            // Given
            val driveSystemMock = mock<DriveSystem>()
            val targetSystemMock = TestITargetingSystem(imageInfo)

            val rc = mock<RobotComponents> {
                on { driveSystem } doReturn driveSystemMock
                on { targetingSystem } doReturn targetSystemMock
            }

            val driveToTarget = DriveToTarget(rc)

            // when
            driveToTarget.execute(0.1)

            // then
            verify(driveSystemMock).arcadeDrive(forward, turn)


        }

    }
}

open class TestITargetingSystem(val imageInfo: ImageInfo) : ITargetingSystem {
    open override fun getCenter(): ImageInfo = imageInfo
    open override fun start() = Unit
}