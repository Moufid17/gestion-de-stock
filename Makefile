install:
	mvn clean install

build:
	mvn clean package

startfirst:
	docker compose up --build -d

start:
	docker compose up -d

down: 
	docker compose down

stop: 
	docker compose down -v

ps: 
	docker compose ps

bash:
	docker compose exec gstock-jdk-server /bin/sh

logs:
	docker compose logs gstock-jdk-server -f