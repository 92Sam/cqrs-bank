#!/usr/bin/env bash
set -e
echo "Waiting for postgres to start..."
sleep 1
echo "Enabling extensions..."
psql --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
  ALTER SYSTEM SET wal_level = logical;
  create extension if not exists "pg_trgm";
  create extension if not exists "uuid-ossp";
  select * FROM pg_extension;
EOSQL
echo "Extensions enabled."
echo "Waiting for postgres to stop..."
#stop postgres gracefully
pg_ctl stop -m fast
