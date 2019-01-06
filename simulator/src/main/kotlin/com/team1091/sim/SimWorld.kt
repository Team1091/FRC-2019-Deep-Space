package com.team1091.sim

import com.team1091.shared.math.radians
import com.team1091.sim.components.SimAccelerometer
import com.team1091.sim.components.SimDrive
import com.team1091.sim.components.SimEncoder
import com.team1091.sim.components.SimGyroscope
import com.team1091.sim.phys.GamePiece
import com.team1091.sim.phys.Obstacle
import com.team1091.sim.phys.SimRobot
import org.jbox2d.collision.shapes.PolygonShape
import org.jbox2d.common.Vec2
import org.jbox2d.dynamics.BodyDef
import org.jbox2d.dynamics.BodyType
import org.jbox2d.dynamics.FixtureDef
import org.jbox2d.dynamics.World


class SimWorld(
        val fieldXSize: Double = 650.0,
        val fieldYSize: Double = 320.0,
        val robots: Array<SimRobot>,
        val gamePieces: Array<GamePiece>, // gamePiece
        val obstacles: Array<Obstacle> // static walls

) {

    // What is all this physics?

    // world -> magic box that holds physics bodies

    // body -> things that have physics and move around the screen, empty. Velocity, pos, etc
    // shape -> has geometry, gets attached to body.  Collisions Rectangle/polygon/circle
    // fixture -> Attaches shapes to body.
    // joint - Connects 2 bodies.

    // create physics stuff
    // in a world with no gravity
    private val world: World = World(Vec2())

    init {

        // build walls
        obstacles.forEach {
            val wall = BodyDef()
            wall.type = BodyType.STATIC
            val body = world.createBody(wall)

            body.position.x = it.x
            body.position.y = it.y

            val shape = PolygonShape()
            shape.setAsBox(it.xSize / 2f, it.ySize / 2f)

            body.createFixture(shape, 1f)
            it.body = body
        }

        gamePieces.forEach {
            val bodyDef = BodyDef()
            bodyDef.position.x = it.x
            bodyDef.position.y = it.y
            bodyDef.angle = it.r
            bodyDef.type = BodyType.DYNAMIC

            bodyDef.linearDamping = 0.8f
            bodyDef.angularDamping = 0.8f
            bodyDef.bullet = true // fast moving

            val body = world.createBody(bodyDef)

            val shape = PolygonShape()
            shape.setAsBox(it.xSize / 2f, it.ySize / 2f)

            val fixtureDef = FixtureDef()
            fixtureDef.shape = shape
            fixtureDef.friction = 0.3f
            fixtureDef.restitution = 0.5f
            fixtureDef.density = 1.0f // mass is calculated from this * area

            body.createFixture(fixtureDef)

            it.body = body
        }

        robots.forEach {

            val bodyDef = BodyDef()
            bodyDef.position.x = it.startingPos.pos.x.toFloat()
            bodyDef.position.y = it.startingPos.pos.y.toFloat()
            bodyDef.angle = it.startingPos.rotation.toRadians().toFloat()
            bodyDef.type = BodyType.DYNAMIC

            bodyDef.linearDamping = 0.8f
            bodyDef.angularDamping = 0.8f
            bodyDef.bullet = true // fast moving

            val body = world.createBody(bodyDef)

            val shape = PolygonShape()
            shape.setAsBox(it.xSize / 2f, it.ySize / 2f)

            val fixtureDef = FixtureDef()
            fixtureDef.shape = shape
            fixtureDef.friction = 0.3f
            fixtureDef.restitution = 0.5f
            fixtureDef.density = 1.0f // mass is calculated from this * area

            body.createFixture(fixtureDef)

            it.body = body
        }

    }


    // Next step is probably to use an actual physics engine
    // http://www.gamefromscratch.com/post/2014/09/25/LibGDX-LibGDX-Tutorial-13-Physics-with-Box2D-Part-3-Collisions.aspx
    // http://www.iforce2d.net/b2dtut/top-down-car
    // http://thisiswhatiknowabout.blogspot.com/2011/12/jbox2d-tutorial.html

    var currentGameState: Period = Period.PREMATCH
    var elapsedSec: Double = 0.0

    // Runs the sim
    fun stepSimulation(dt: Double = 0.1) {
        elapsedSec += dt

        when (currentGameState) {
            Period.PREMATCH -> {
                robots.forEach { it.teamRobot.robotInit(it.startingPos) }
                currentGameState = Period.AUTONOMOUS
                robots.forEach { it.teamRobot.autonomousInit() }
            }
            Period.AUTONOMOUS -> {
                if (elapsedSec >= Period.AUTONOMOUS.seconds) {
                    currentGameState = Period.TELEOP
                    robots.forEach { it.teamRobot.teleopInit() }
                }
            }
            Period.TELEOP -> {
                if (elapsedSec >= Period.TELEOP.seconds) {
                    currentGameState = Period.DISABLED
                    robots.forEach { it.teamRobot.disabledInit() }
                }
            }
            Period.DISABLED -> return
        }

        for (robot in robots) {
            when (currentGameState) {
                Period.PREMATCH -> Unit
                Period.AUTONOMOUS -> robot.teamRobot.autonomousPeriodic()
                Period.TELEOP -> robot.teamRobot.teleopPeriodic()
                Period.DISABLED -> robot.teamRobot.disabledPeriodic()
            }
        }

        val invertedDt = 1f / dt.toFloat()

        // TODO: add each robot's drive's acceleration
        for (robot in robots) {
            val drive = (robot.rc.drive as SimDrive)
            drive.applyForce(robot.body)

            val accelerometer = robot.rc.accelerometer as SimAccelerometer

            val vel = robot.body.linearVelocity
            val acceleration = vel.sub(robot.lastVelocity).mul(invertedDt)
            robot.lastVelocity.set(vel)

            accelerometer.set(acceleration.x, acceleration.y, -1f)

            val gyro = (robot.rc.gyroscope as SimGyroscope)
            val rv = robot.body.angularVelocity
            gyro.add((rv * dt).radians)

        }

        world.step(dt.toFloat(), 5, 3)

        for (robot in robots) {
            with(robot) {

                val lEncode = (rc.leftEncoder as SimEncoder)
                val rEncode = (rc.rightEncoder as SimEncoder)

                val v = body.linearVelocity.length()
                val rv = body.angularVelocity

                lEncode.rotation += (v + rv * lEncode.rotDist) * dt
                rEncode.rotation += (v + rv * rEncode.rotDist) * dt


            }
        }

    }
    //println("r:${robots.first().rEncode.get()} l:${robots.first().lEncode.get()}")

}