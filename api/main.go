package main

import (
	"context"
	"fmt"
	"log"
	"net/http"

	"brainflex.com/api/config"
	"brainflex.com/api/delivery"
	"brainflex.com/api/repo"
	"github.com/jackc/pgx/v5"
)

func main() {
	mux := http.NewServeMux()

	cfg := config.New()

	db, err := pgx.Connect(context.Background(), cfg.DSN)
	if err != nil {
		log.Fatalf("Error opening database: %v", err)
	}
	defer db.Close(context.Background())

	repo := repo.New(db)
	handlers := delivery.NewHandlers(cfg, repo)
	handlers.MapHandlers(mux)

	fmt.Println("Server is running on port 80")
	err = http.ListenAndServe(":80", mux)
	log.Fatal(err)
}
