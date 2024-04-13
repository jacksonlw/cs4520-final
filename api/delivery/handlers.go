package delivery

import (
	"encoding/json"
	"fmt"
	"net/http"
	"strconv"

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
		l, err := getLimitFromRequest(r)
		if err != nil {
			httpBadRequest(w, err.Error())
			return
		}

		o, err := getOffsetFromRequest(r)
		if err != nil {
			httpBadRequest(w, err.Error())
			return
		}

		scores, err := h.repo.GetScores(*l, *o)
		if err != nil {
			fmt.Println(err)
			http.Error(w, "error getting scores", http.StatusInternalServerError)
			return
		}

		total, err := h.repo.CountScores()
		if err != nil {
			http.Error(w, "error getting total scores", http.StatusInternalServerError)
			return
		}

		res := dto.LeaderboardResponse{
			Scores: scores,
			Limit:  *l,
			Offset: *o,
			Total:  total,
		}

		w.Header().Set("Content-Type", "application/json")
		w.WriteHeader(http.StatusOK)

		if err := json.NewEncoder(w).Encode(res); err != nil {
			http.Error(w, err.Error(), http.StatusInternalServerError)
		}
	} else if r.Method == "POST" {
		var req dto.LeaderboardRequest
		if err := json.NewDecoder(r.Body).Decode(&req); err != nil {
			http.Error(w, "invalid request body", http.StatusBadRequest)
			return
		}

		if err := validateLeaderboardRequest(req); err != nil {
			httpBadRequest(w, err.Error())
			return
		}

		err := h.repo.InsertScore(req.Username, *req.Score)
		if err != nil {
			http.Error(w, "error inserting score", http.StatusInternalServerError)
			return
		}

		w.WriteHeader(http.StatusCreated)
	} else {
		httpMethodNotAllowed(w)
	}

}

func getLimitFromRequest(r *http.Request) (*int, error) {
	q := r.URL.Query().Get("limit")

	if q == "" {
		l := domain.DEFAULT_LIMIT
		return &l, nil
	}

	l, err := strconv.Atoi(q)
	if err != nil {
		return nil, fmt.Errorf("invalid limit: must be an integer")
	}
	if l <= 0 {
		return nil, fmt.Errorf("limit must be greater than 0")
	}

	return &l, nil
}

func getOffsetFromRequest(r *http.Request) (*int, error) {
	q := r.URL.Query().Get("offset")

	if q == "" {
		o := domain.DEFAULT_OFFSET
		return &o, nil
	}

	o, err := strconv.Atoi(q)
	if err != nil {
		return nil, fmt.Errorf("invalid offset: must be an integer")
	}
	if o < 0 {
		return nil, fmt.Errorf("offset must be greater than or equal to 0")
	}

	return &o, nil
}
