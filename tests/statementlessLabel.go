package pkg

func fn() {
	for {
		if true {
			fmt.Println()
		}
		break // want `the surrounding loop is unconditionally terminated`
	}
	for {
		if true {
			break
		} else {
			break
		}
	}
	for {
		if false {
			continue
		}
		break
	}
	for {
		if true {
			continue
		}
		break
	}
}
