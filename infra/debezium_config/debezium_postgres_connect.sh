#!/bin/bash

HOST_DEBEZIUM=localhost:8083
HOST_CONTAINER_INTERNAL=host.docker.internal

# Postgres Configuration
DB_PGSQL_HTTP_PORT=5433
DB_PGSQL_HOST=${HOST_CONTAINER_INTERNAL}
DB_PGSQL_DATABASE=bank_service
DB_PGSQL_USERNAME=root
DB_PGSQL_PASSWORD=root123456

echo "Waiting to applying changes to Debezium Server"

sleep 22

curl -X POST ${HOST_DEBEZIUM}/connectors \
  -H 'Content-Type: application/json' \
  -H 'Accept: application/json' \
  --data '{
    "name": "cqrs-bank-psql-connector",
    "config": {
      "connector.class": "io.debezium.connector.postgresql.PostgresConnector",
      "plugin.name": "pgoutput",
      "database.hostname": "'${DB_PGSQL_HOST}'",
      "database.port": "'${DB_PGSQL_HTTP_PORT}'",
      "database.user": "'${DB_PGSQL_USERNAME}'",
      "database.password": "'${DB_PGSQL_PASSWORD}'",
      "database.dbname" : "'${DB_PGSQL_DATABASE}'",
      "topic.prefix": "cqrs-bank-psql",
      "database.allowPublicKeyRetrieval":"true",
      "database.server.name": "bank_postgres",
      "table.include.list": "public.users,public.accounts,public.transactions"
    }
  }'