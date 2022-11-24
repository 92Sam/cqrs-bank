include .env

# App Actions
run_app:
	mvn spring-boot:run

# App Actions
run_app_debug:
	mvn -Dmaven.surefire.debug spring-boot:run

clean_app:
	mvn clean spring-boot:repackage

compile_app:
	mvn package

# Containers Actions
run_container_services_app: \
	containers_build containers_run execute_debezium_config

run_container_services: \
	containers_run execute_debezium_config

containers_run:
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
	containers_run \
	execute_debezium_config

containers_build:
	docker-compose \
	--env-file ${ENV_FILE_PATH} \
	-f ${PWD}/docker-compose-broker.yml \
	-f ${PWD}/docker-compose.yml build \

# Others Actions
show:
	echo "Test ->" ${HOST} \

removing_images_test:
	docker images | grep "none" | awk '{print $3}' | xargs docker rmi

execute_debezium_config:
	/bin/bash ${PWD}/infra/debezium_config/debezium_postgres_connect.sh