package dto

type LoginRequest struct {
	Username string `json:"username"`
}

type LeaderboardRequest struct {
	Username string `json:"username"`
	Score    *int   `json:"score"`
}

type Score struct {
	ID          int    `json:"id"`
	Score       int    `json:"score"`
	Inserted_At string `json:"inserted_at"`
	Username    string `json:"username"`
}
