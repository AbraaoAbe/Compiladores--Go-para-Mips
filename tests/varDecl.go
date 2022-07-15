package samples

import "fmt"

func multiRet() int {
	return 1
}

func VarDecls() {
	var a int        // +
	var b, c float64 // + strange extra levels
	var d int
	d = 1        // + doesn't show zero value
	var e, f float32
	e = 1 
	f = 2  // +
 // + need to precise general text span
	// var _, k = entries["1"] // map lookup;
	var l int
	//multiRet() // + TODO: Figure out with duplication
	//fmt.Println(a, b, c, d, e, f, l, m)
}
