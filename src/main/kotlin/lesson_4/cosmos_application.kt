package lesson_4

fun main() {
    val earth = Planet("Earth", 5)
    val jupiter = Planet("Jupiter", 15)

    val sun = Star("Sun", 15)

    val solarSystem = System(
        name = "Solar System",
        planets = arrayListOf(earth, jupiter),
        star = sun
    )

    val milkyWayGalaxy = Galaxy(
        name = "Milky Way",
        systems = arrayListOf(solarSystem),
    )

    println("Gravity of Jupiter: ${jupiter.getGravity()}")
    println("Gravity of Sun: ${sun.getGravity()}")
    println("Gravity of Solar System: ${solarSystem.getGravity()}")
    println("Gravity of Milky Way Galaxy: ${milkyWayGalaxy.getGravity()}")
}

interface CosmicObject {
    val name: String
    val type: CosmicObjectType
    fun getGravity() : Int
}

data class Galaxy(
    override val name: String,
    val systems: ArrayList<System> = ArrayList(),
) : CosmicObject {

    override val type: CosmicObjectType = CosmicObjectType.GALAXY

    override fun getGravity(): Int {
        var gravity = 0
        systems.forEach { system ->
            gravity += system.getGravity()
        }
        return gravity
    }
}

data class System(
    override val name: String,
    val planets: ArrayList<Planet> = ArrayList(),
    var star: Star,
) : CosmicObject {

    override val type: CosmicObjectType = CosmicObjectType.SYSTEM

    override fun getGravity(): Int {
        var gravity = star.getGravity()
        planets.forEach { planet ->
            gravity += planet.getGravity()
        }
        return gravity
    }

}

data class Star(
    override val name: String,
    private var gravity: Int,
) : CosmicObject {

    override val type: CosmicObjectType = CosmicObjectType.STAR

    init {
        when {
            gravity < 11 -> gravity = 11
            gravity > 25 -> gravity = 25
        }
    }

    override fun getGravity(): Int {
        return gravity
    }
}

data class Planet(
    override val name: String,
    private var gravity: Int,
) : CosmicObject {

    override val type: CosmicObjectType = CosmicObjectType.PLANET

    init {
        when {
            gravity < 5 -> gravity = 5
            gravity > 10 -> gravity = 10
        }
    }

    override fun getGravity(): Int {
        return gravity
    }
}

enum class CosmicObjectType {
    PLANET,
    STAR,
    SYSTEM,
    GALAXY,
}