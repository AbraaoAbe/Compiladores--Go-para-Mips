package main

import "fmt"


func main() {
	var a, b, c, d, e int
	var f, g float64

	a = 10
	b = 20
	c = a + b
	d = (c * a + b)/2
	e = d * d + 1
	f = a * e
	g = d + a

	fmt.Println(a, b, c, d, e)

}