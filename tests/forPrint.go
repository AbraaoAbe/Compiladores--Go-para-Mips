package main

func main() {

	var i int

	// `range` on arrays and slices provides both the
	// index and value for each entry. Above we didn't
	// need the index, so we ignored it with the
	// blank identifier `_`. Sometimes we actually want
	// the indexes though.
	for i = 0; i < 3; i = i + 1 {
		fmt.Println("index: ");
		fmt.Println(i);
	}

}