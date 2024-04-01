package main

type LoginRequest struct {
	Username string `json:"username"`
}

type LeaderboardRequest struct {
	Username string `json:"username"`
	Score    *int   `json:"score"`
}
