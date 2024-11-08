build:
	mvn clean package

startfirst:
	docker compose up --build -d

start:
	docker compose up -d

stop: 
	docker compose down -v

ps: 
	docker compose ps