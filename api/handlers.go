package main

import (
	"encoding/json"
	"net/http"
)

func LoginHandler(w http.ResponseWriter, r *http.Request) {
	if r.Method == "POST" {
		var req LoginRequest
		err := json.NewDecoder(r.Body).Decode(&req)
		if err != nil {
			http.Error(w, "invalid request body", http.StatusBadRequest)
			return
		}

		if isStringBlank(req.Username) {
			http.Error(w, "username is required", http.StatusBadRequest)
			return
		}

		w.WriteHeader(http.StatusOK)
		return
	}

	httpMethodNotAllowed(w)
}

func LeaderboardHandler(w http.ResponseWriter, r *http.Request) {
	if r.Method == "GET" {
		w.WriteHeader(http.StatusOK)
		return
	}

	if r.Method == "POST" {
		var req LeaderboardRequest
		err := json.NewDecoder(r.Body).Decode(&req)
		if err != nil {
			http.Error(w, "invalid request body", http.StatusBadRequest)
			return
		}

		if isStringBlank(req.Username) {
			http.Error(w, "username is required", http.StatusBadRequest)
			return
		}

		if req.Score == nil {
			http.Error(w, "score is required", http.StatusBadRequest)
			return
		}

		w.WriteHeader(http.StatusOK)
		return
	}

	httpMethodNotAllowed(w)
}
