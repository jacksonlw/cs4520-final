package repo

import (
	"context"

	"brainflex.com/api/domain"
	"brainflex.com/api/dto"
	"github.com/jackc/pgx/v5"
)

type repo struct {
	db *pgx.Conn
}

func New(db *pgx.Conn) domain.Repository {
	return repo{db: db}
}

func (r repo) GetScores(limit int, offset int) ([]dto.Score, error) {
	rows, err := r.db.Query(context.Background(), "SELECT username, score FROM scores ORDER BY score DESC LIMIT $1 OFFSET $2", limit, offset)
	if err != nil {
		return nil, err
	}
	defer rows.Close()

	var scores []dto.Score
	for rows.Next() {
		var s dto.Score
		err := rows.Scan(&s.Username, &s.Score)
		if err != nil {
			return nil, err
		}

		scores = append(scores, s)
	}

	return scores, nil
}

func (r repo) InsertScore(username string, score int) error {
	_, err := r.db.Exec(context.Background(), "INSERT INTO scores (username, score) VALUES ($1, $2)", username, score)
	return err
}

func (r repo) InsertUser(username string) error {
	_, err := r.db.Exec(context.Background(), "INSERT INTO users (username) VALUES ($1)", username)
	return err
}
