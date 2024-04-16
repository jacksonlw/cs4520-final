package delivery

import (
	"fmt"

	"brainflex.com/api/dto"
	"brainflex.com/api/str"
)

func validateLeaderboardRequest(req dto.LeaderboardRequest) error {
	if str.IsStringBlank(req.Username) {
		return fmt.Errorf("username is required")
	}

	if req.Score == nil {
		return fmt.Errorf("score is required")
	}

	return nil
}
