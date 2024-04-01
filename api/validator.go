package main

import (
	"strings"
)

func isStringBlank(s string) bool {
	return strings.TrimSpace(s) == ""
}
