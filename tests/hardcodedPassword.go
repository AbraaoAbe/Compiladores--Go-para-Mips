package samples

import "fmt"

type HpType struct {
	name  string
	idade int
}

func (c HpType) HP() {
	password := `hardcoded`
	// var password = "hardcoded"
	fmt.Println("Hello, worldYou type the password=v", password)
	letters := []string{"a", "b", "c", "d"}
	// shadowing previous same decl
	// var letters []string
	//letters = make([]string, 4, 10)
	// letters[3] = "e"
	p := make([]string, 10)
	p = append(letters, "e", "f")
	fmt.Println(letters, len(letters), cap(letters))
	fmt.Println(p, len(p), cap(p))
}
