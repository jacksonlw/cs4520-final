package config

import (
	"log"
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
		log.Fatalf("Error loading .env file: %v", err)
		panic(err)
	}
}

func getEnvOrPanic(key string) string {
	value := os.Getenv(key)
	if value == "" {
		panic(key + " is required")
	}
	return value
}
