package lesson_3

import kotlin.math.sqrt

fun main() {
    // Initialization
    // Rectangle and Square
    val rectangleOne: Rectangle = Rectangle(3.0, 4.0)
    val rectangleTwo: Rectangle = Rectangle(4.0)

    // Triangles
    val equilateral: Triangle = Triangle(8.0)
    val isosceles: Triangle = Triangle(Pair(3.0, 4.0))
    val dichotomous: Triangle = Triangle(Triple(3.0, 4.0, 5.0))

    // Circle
    val circle: Circle = Circle(10)

    // Method Overriding
    println("Calculate Area: ${rectangleOne.calculateArea()}")
    println("Triple Type: ${isosceles.getType()}")

    // Object Specific Methods
    println("Square Diagonal: ${rectangleTwo.getDiagonal()}")
    println("Triangle Perimeter: ${dichotomous.getPerimeter()}")
    println("Circle's inner Square side: ${circle.getInnerSquareSide()}")

    // Method Overload
    rectangleOne.setSize(5)
    rectangleTwo.setSize(5.0)

    // Extension Methods
    println("Merged Rectangle: ${rectangleOne.mergeHorizontally(rectangleTwo)}")
    println("Sum Triple Areas: ${equilateral.sumArea(isosceles)}")
    println("Get Ring Area: ${circle.ringArea(Circle(3.0))}")

    // Infix Methods
    println("are Rectangles Equal: ${rectangleOne isEqual rectangleTwo}") // With Diagonal
    println("are the areas of Triangles Equal: ${isosceles hasEqualArea dichotomous}")
    println("are Circles Equal: ${circle isEqual Circle(5.0)}") // with radius
}

// Shape
open class Shape {
    open fun calculateArea(): Double {
        return 0.0
    }

    open fun getType(): String {
        return "Shape"
    }
}

// Rectangle
data class Rectangle(private var width: Double, private var height: Double) : Shape() {
    // Constructor for Square
    constructor(size: Double) : this(size, size)

    private lateinit var type: String;

    init {
        setType()
    }

    // Method Overrides
    override fun getType(): String {
        return type
    }

    override fun calculateArea(): Double {
        return width * height
    }

    // Getters
    fun getHeight(): Double {
        return height
    }

    fun getWidth(): Double {
        return width
    }

    // Object Specific Method
    fun getDiagonal(): Double {
        return sqrt(width * width + height * height)
    }

    // Setters
    fun setHeight(height: Double) {
        if (height <= 0) println("Height must be positive")
        else {
            this.height = height
            setType()
        }
    }

    fun setWidth(width: Double) {
        if (width <= 0) println("Width must be positive")
        else {
            this.width = width
            setType()
        }
    }

    fun setSize(size: Double) {
        if (size <= 0) println("Size must be positive")
        else {
            width = size
            height = size
            setType()
        }
    }

    // Method overload
    fun setSize(size: Int) {
        if (size <= 0) println("Size must be positive")
        else {
            width = size.toDouble()
            height = size.toDouble()
        }
    }

    // Private Setters
    private fun setType() {
        type = if (width == height) "Square"
        else "Rectangle"
    }
}

// Extension and Infix Methods
fun Rectangle.mergeHorizontally(other: Rectangle): Rectangle {
    val width = this.getWidth() + other.getWidth()
    val height = max(this.getHeight(), other.getHeight())
    return Rectangle(width, height)
}

fun Rectangle.mergeVertically(other: Rectangle): Rectangle {
    val width = max(this.getWidth(), other.getHeight())
    val height = this.getHeight() + other.getHeight()
    return Rectangle(width, height)
}

infix fun Rectangle.isEqual(other: Rectangle): Boolean {
    return this.getDiagonal() == other.getDiagonal()
}

// Triangle
data class Triangle(private var sides: Triple<Double, Double, Double>) : Shape() {

    // Constructor for Equilateral Triangle
    constructor(size: Double) : this(Triple(size, size, size))

    // Constructor for Isosceles Triangle
    constructor(sides: Pair<Double, Double>) : this(
        Triple(
            sides.first,
            if (sides.first * 2 > sides.second) sides.first
            else sides.second,
            sides.second,
        )
    )


    private lateinit var type: String;

    init {
        setType()
        val (a, b, c) = sides
        if (a + b <= c || a + c <= b || b + c <= a) {
            println("it is not possible to create triangle with this sides $sides")
            println("Sides changed to default - 1.0")
            sides = Triple(1.0, 1.0, 1.0)
        }
    }

    // Method Overrides
    override fun getType(): String {
        return this.type
    }

    override fun calculateArea(): Double {
        val (a, b, c) = sides
        val s = (a + b + c) / 2
        return sqrt(s * (s - a) * (s - b) * (s - c))
    }

    // Getters
    fun getSides(): Triple<Double, Double, Double> {
        return sides
    }

    // Object Specific Method
    fun getPerimeter(): Double {
        return sides.first + sides.second + sides.third
    }

    // Setters
    fun setSides(sides: Triple<Double, Double, Double>) {
        val (a, b, c) = sides
        if (a + b <= c || a + c <= b || b + c <= a) println("it is not possible to set this sides")
        else this.sides = sides
    }

    // Method overload
    // This method Throws error, the solution is custom type definitions with Triple,
    // but In the first 3 lessons we didn't learn type aliases yet
//    fun setSides(sides: Triple<Int, Int, Int>) {
//        val (a, b, c) = sides
//        if (a + b <= c || a + c <= b || b + c <= a) println("it is not possible to set this sides")
//        else this.sides = Triple(a.toDouble(), b.toDouble(), c.toDouble())
//    }

    // Private Setters
    private fun setType() {
        val equality: Triple<Boolean, Boolean, Boolean> = Triple(
            sides.first == sides.second,
            sides.second == sides.third,
            sides.first == sides.third,
        )
        equality.apply {
            type = when {
                !first && !second && !third -> "Dichotomous"
                first && second -> "Equilateral"
                else -> "Isosceles"
            }
        }
    }
}

// Extension and Infix Methods
fun Triangle.sumArea(other: Triangle): Double {
    return this.calculateArea() + other.calculateArea()
}

infix fun Triangle.hasEqualArea(other: Triangle): Boolean {
    return this.calculateArea() == other.calculateArea()
}

// Circle
data class Circle(private var radius: Double) : Shape() {

    constructor(diameter: Number) : this(diameter.toDouble() / 2)

    private var type: String = "Circle";

    // Method Overrides
    override fun getType(): String {
        return this.type
    }

    override fun calculateArea(): Double {
        return radius * radius * Math.PI
    }

    // Getters
    fun getRadius(): Double {
        return radius
    }

    // Object Specific Method
    fun getInnerSquareSide(): Double {
        return sqrt(radius * radius * 2)
    }

    // Setters
    fun setRadius(radius: Double) {
        if (radius <= 0) println("Radius must be positive")
        else this.radius = radius
    }

    // Method overload
    fun setRadius(radius: Int) {
        if (radius <= 0) println("Diameter must be positive")
        else this.radius = radius.toDouble()
    }
}

// Extension and Infix Methods
fun Circle.ringArea(other: Circle): Double {
    if (this.getRadius() > other.getRadius()) {
        return this.calculateArea() - other.calculateArea()
    }
    return other.calculateArea() - this.calculateArea()
}

infix fun Circle.isEqual(other: Circle): Boolean {
    return this.getRadius() == other.getRadius()
}

// Global Functions
fun max(first: Double, second: Double): Double {
    if (first > second) return first
    return second
}

fun max(first: Int, second: Int): Int {
    if (first > second) return first
    return second
}