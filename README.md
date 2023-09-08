# money-mapper

## Overview

This is a Spring Batch application designed to process .qfx files downloaded from a financial institution and apply user defined categorization to each transaction.

### Local postgres

docker run --name money-mapper-postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_USER=postgres -p 5432:5432 -d postgres

docker cp src/main/sql/schema-postgresql.sql money-mapper-postgres:/mnt/schema-postgres.sql
docker cp src/main/sql/schema-drop-postgres.sql money-mapper-postgres:/mnt/schema-drop-postgres.sql

docker exec -u postgres money-mapper-postgres psql postgres -f /mnt/schema-drop-postgres.sql
docker exec -u postgres money-mapper-postgres psql postgres -f /mnt/schema-postgres.sql
