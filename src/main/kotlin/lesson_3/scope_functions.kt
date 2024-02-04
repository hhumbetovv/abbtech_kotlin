package lesson_3

fun main(){
    val rectangle: Rectangle = Rectangle(3.0, 4.0)
    val triangle: Triangle = Triangle(Triple(6.0, 8.0, 10.0))
    val circle: Circle = Circle(5.0)

    // Reference - This, Return - Context Object (Rectangle)
    rectangle.apply {
        setSize(8)
        setWidth(6.0)
    }

    // Reference - This, Return - Lambda Result
    val triangleArea = triangle.run {
        setSides(Triple(3.0, 4.0, 5.0))
        println("Triangle perimeter: ${getPerimeter()}")
        calculateArea()
    }

    // Reference - This, Return - Lambda Result
    // logic is the same with run, just syntax is different
    // but run is better for nullable variables
    println("Inner Square Side" + with(circle) {
        setRadius(3)
        getInnerSquareSide()
    })

    // Reference - It, Return - Context Object (Triangle)
    triangle.also { t ->
        println("Triangle Type: ${t.getType()}")
        println("Triangle Sides: ${t.getSides()}")
    }

    // Reference - It, Return - Lambda Result
    val sumOfAreas =  Pair(rectangle, circle).let { (r, c) ->
        r.calculateArea() + c.calculateArea() + triangleArea
    }

    println(sumOfAreas)
}