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
	rows, err := r.db.Query(context.Background(), "SELECT s.id, u.username, s.score, s.inserted_at FROM scores s JOIN users u ON s.user_id = u.id ORDER BY score DESC LIMIT $1 OFFSET $2", limit, offset)
	if err != nil {
		return nil, err
	}
	defer rows.Close()

	scores := []dto.Score{}
	for rows.Next() {
		var s dto.Score
		err := rows.Scan(&s.ID, &s.Username, &s.Score, &s.InsertedAt)
		if err != nil {
			return nil, err
		}

		scores = append(scores, s)
	}

	return scores, nil
}

func (r repo) InsertScore(username string, score int) error {
	tag, err := r.db.Exec(context.Background(), "INSERT INTO scores (user_id, score) SELECT id, $2 FROM users WHERE username=$1", username, score)

	if tag.RowsAffected() == 0 {
		return pgx.ErrNoRows
	}

	return err
}

func (r repo) InsertUser(username string) error {
	_, err := r.db.Exec(context.Background(), "INSERT INTO users (username) VALUES ($1)", username)
	return err
}

func (r repo) GetUser(username string) (bool, error) {
	var exists bool
	err := r.db.QueryRow(context.Background(), "SELECT EXISTS(SELECT 1 FROM users WHERE username = $1)", username).Scan(&exists)
	return exists, err
}

func (r repo) CountScores() (int, error) {
	var count int
	err := r.db.QueryRow(context.Background(), "SELECT COUNT(*) FROM scores").Scan(&count)
	return count, err
}
