install:
	mvn clean install -DskipTests

build:
	mvn clean package -DskipTests

check-config:
	docker compose --env-file .env -f compose.yaml config

up : build
	docker compose --env-file .env -f compose.yaml up -d

down: 
	docker compose down

stop: 
	docker compose down -v

ps: 
	docker compose ps

bash:
	docker compose exec gstock-local-server /bin/sh

logs:
	docker compose logs gstock-local-server -f

testall:
	mvn test

testone:
	mvn test -Dtest="$(name)"

check-config-prod:
	docker compose --env-file .env.prod -f compose.prod.yaml config

up-prod:
	docker compose --env-file .env.prod -f compose.prod.yaml up --build -d

