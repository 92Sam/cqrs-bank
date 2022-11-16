include .env

run_service: \
	run_containers \
	run_app

run_app:
	mvn spring-boot:run

clean_app:
	mvn clean spring-boot:repackage

compile_app:
	mvn package

run_containers:
	docker-compose \
	--env-file ${ENV_FILE_PATH} \
	-f ${PWD}/docker-compose-broker.yml \
	-f ${PWD}/docker-compose.yml up -d

containers_down:
	docker-compose \
	--env-file ${ENV_FILE_PATH} \
	-f ${PWD}/docker-compose-broker.yml \
	-f ${PWD}/docker-compose.yml down

containers_restart: \
	containers_down \
	run_containers \

show:
	echo ${ENV_FILE_PATH}

removing-images-test:
	docker images | grep "none" | awk '{print $3}' | xargs docker rmi