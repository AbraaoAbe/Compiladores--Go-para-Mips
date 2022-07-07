package main

import "fmt"


func ArrayDecls() {
	/*[32]byte
	[2*N] struct { x, y int32 }
	[1000]*float64
	[3][5]int
	[2][2][2]float64  // same as [2]([2]([2]float64))
	*/
	var a [2]string
	a[0] = "Hello"
	a[1] = "World"
	fmt.Println(a[0], a[1])

	var primes [6]int
	fmt.Println(primes)

	var twoD [2][4]int
	var i, j int = 0

	for i = 0; i < 2; i++ {
		for j = 0; j < 3; j++ {
			twoD[i][j] = i + j
		}
	}
	fmt.Println("2d: ", twoD)
}