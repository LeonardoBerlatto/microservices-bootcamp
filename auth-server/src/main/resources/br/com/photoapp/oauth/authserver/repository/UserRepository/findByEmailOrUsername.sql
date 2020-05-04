SELECT
    *
FROM
    "user"
WHERE
        email = :username or username = :username;