package dto

import "time"

type LoginRequest struct {
	Username string `json:"username"`
}

type LeaderboardRequest struct {
	Username string `json:"username"`
	Score    *int   `json:"score"`
}

type LeaderboardResponse struct {
	Scores []Score `json:"scores"`
	Limit  int     `json:"limit"`
	Offset int     `json:"offset"`
	Total  int     `json:"total"`
}

type Score struct {
	ID         int       `json:"id"`
	Username   string    `json:"username"`
	Score      int       `json:"score"`
	InsertedAt time.Time `json:"inserted_at"`
}

type User struct {
	ID       int    `json:"id"`
	Username string `json:"username"`
}
