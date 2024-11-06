build:
	mvn clean package

start: build
	docker compose up --build -d

stop: 
	docker compose down -v