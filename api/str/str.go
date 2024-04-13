package str

import "strings"

func IsStringBlank(s string) bool {
	return strings.TrimSpace(s) == ""
}
