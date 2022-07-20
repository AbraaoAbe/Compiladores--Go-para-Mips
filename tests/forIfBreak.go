package main

func ForeachStmts() {
	// Here we use `range` to sum the numbers in a slice.
	// Arrays work like this too.
	var nums [3]int
	nums[0] = 2
	nums[1] = 3
	nums[2] = 4

	var i int

	// `range` on arrays and slices provides both the
	// index and value for each entry. Above we didn't
	// need the index, so we ignored it with the
	// blank identifier `_`. Sometimes we actually want
	// the indexes though.
	for i = 0; i < 3; i = i + 1 {
		if nums[i] == 3 {
			fmt.Println("index:", i)
			break
		}
	}

}