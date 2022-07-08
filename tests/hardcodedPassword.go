package samples

import "fmt"

func HP() {
	var password string
	password = `hardcoded`
	// var password = "hardcoded"
	fmt.Println("Hello, worldYou type the password=v", password)
	var letters [4]string
	letters[0] = "a"
	letters[1] = "b"
	letters[2] = "c"
	letters[3] = "d"
	var i int
	for i = 0; i < 4; i++ {
		fmt.Println("letters[i] = ", letters[i])
	}
	// shadowing previous same decl
	// var letters []string
	//letters = make([]string, 4, 10)
	// letters[3] = "e
}