SELECT
	EXISTS (
	SELECT
		1
	FROM
		"user"
	WHERE
		email = :email)