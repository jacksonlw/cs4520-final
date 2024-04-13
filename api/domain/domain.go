package domain

import "brainflex.com/api/dto"

const DEFAULT_LIMIT int = 10
const DEFAULT_OFFSET int = 0

type Repository interface {
	GetScores(limit int, offset int) ([]dto.Score, error)
	InsertScore(username string, score int) error
	InsertUser(username string) error
	CountScores() (int, error)
}
