package main

import "fmt"


func main() {

	var i, j int

	for i = 1; i < 3; i = i + 1 {
		for j = 1; j < 4; j = j + 1 {
			fmt.Println(i + j)
		}
	}
}