package config

import (
	"os"

	"github.com/joho/godotenv"
)

type Config struct {
	DSN string
}

func New() Config {
	loadEnvOrPanic()

	return Config{
		DSN: getEnvOrPanic("DSN"),
	}
}

func loadEnvOrPanic() {
	if err := godotenv.Load(); err != nil {
		panic("Error loading .env file: " + err.Error())
	}
}

func getEnvOrPanic(key string) string {
	value := os.Getenv(key)
	if value == "" {
		panic(key + " is required")
	}
	return value
}
