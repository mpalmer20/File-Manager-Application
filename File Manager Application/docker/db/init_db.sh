#!/bin/bash
# Initialize the SQLite database and execute all SQL files in /db/sql_scripts

# Check if the SQLite database file exists, create if not
if [ ! -f /db/comp20081.db ]; then
    echo "[$(whoami)@$(pwd)]: Creating new SQLite database."
    sqlite3 /db/comp20081.db "VACUUM;"
fi

echo "[$(whoami)@$(pwd)]: Creating schema."

cd "/db/scripts"
ls -lrai
# Execute all .sql files in the specified directory
for sql_file in ./*.sql; 
do
  echo "[$(whoami)@(pwd)]: Executing $sql_file..."
  sqlite3 /db/comp20081.db < "$sql_file"
done

