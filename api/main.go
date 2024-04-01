package main

import (
	"log"
	"net/http"
)

func main() {
	mux := http.NewServeMux()

	mux.HandleFunc("/login", LoginHandler)
	mux.HandleFunc("/leaderboard", LeaderboardHandler)

	println("Server is running on port 8080")
	err := http.ListenAndServe(":8080", mux)
	log.Fatal(err)
}
