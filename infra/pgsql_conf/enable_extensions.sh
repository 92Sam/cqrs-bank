#!/bin/sh
set -e

# stop postgresql server after done setting up user and running scripts
docker_temp_server_stop() {
  psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
  # must quote extension names or else symbolic error will be thrown.
    create extension if not exists "pg_trgm";
    create extension if not exists "uuid-ossp";
    select * FROM pg_extension;
  EOSQL
}

docker_temp_server_stop