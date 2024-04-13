package delivery

import (
	"encoding/json"
	"fmt"
	"net/http"

	"brainflex.com/api/config"
	"brainflex.com/api/domain"
	"brainflex.com/api/dto"
	"brainflex.com/api/str"
)

type handlers struct {
	cfg  config.Config
	repo domain.Repository
}

func NewHandlers(cfg config.Config, repo domain.Repository) handlers {
	return handlers{
		cfg,
		repo,
	}
}

func (h handlers) MapHandlers(mux *http.ServeMux) {
	mux.HandleFunc("/login", h.loginHandler)
	mux.HandleFunc("/leaderboard", h.leaderboardHandler)
}

func (h handlers) loginHandler(w http.ResponseWriter, r *http.Request) {
	if r.Method == "POST" {
		var req dto.LoginRequest
		err := json.NewDecoder(r.Body).Decode(&req)
		if err != nil {
			http.Error(w, "invalid request body", http.StatusBadRequest)
			return
		}

		if str.IsStringBlank(req.Username) {
			http.Error(w, "username is required", http.StatusBadRequest)
			return
		}

		err = h.repo.InsertUser(req.Username)
		if err != nil {
			fmt.Println(err)
			http.Error(w, "error inserting user", http.StatusInternalServerError)
			return
		}

		w.WriteHeader(http.StatusOK)
		return
	}

	httpMethodNotAllowed(w)
}

func (h handlers) leaderboardHandler(w http.ResponseWriter, r *http.Request) {
	if r.Method == "GET" {
		w.WriteHeader(http.StatusOK)
		return
	}

	if r.Method == "POST" {
		var req dto.LeaderboardRequest

		err := json.NewDecoder(r.Body).Decode(&req)
		if err != nil {
			http.Error(w, "invalid request body", http.StatusBadRequest)
			return
		}

		if err = validateLeaderboardRequest(req); err != nil {
			httpBadRequest(w, err.Error())
			return
		}

		w.WriteHeader(http.StatusOK)
		return
	}

	httpMethodNotAllowed(w)
}
