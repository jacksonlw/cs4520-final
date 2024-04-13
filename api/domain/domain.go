package domain

import "brainflex.com/api/dto"

type Repository interface {
	GetScores(limit int, offset int) ([]dto.Score, error)
	InsertScore(username string, score int) error
	InsertUser(username string) error
}
