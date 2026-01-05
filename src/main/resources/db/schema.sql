CREATE TABLE "users" (
  id SERIAL PRIMARY KEY,
  user_id VARCHAR(50) NOT NULL,
  password VARCHAR(255) NOT NULL,
  email VARCHAR(100) NOT NULL,
  role VARCHAR(50) NULL,
  note TEXT NULL,
  create_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  create_id VARCHAR(50) NOT NULL,
  update_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_id VARCHAR(50) NOT NULL,
  UNIQUE (user_id),
  UNIQUE (email)
);

CREATE OR REPLACE FUNCTION update_changetimestamp_column()
RETURNS TRIGGER AS $$
BEGIN
   NEW.update_at = now(); 
   RETURN NEW;
END;
$$ language 'plpgsql';

CREATE TRIGGER update_user_changetimestamp BEFORE UPDATE
ON "users" FOR EACH ROW EXECUTE PROCEDURE 
update_changetimestamp_column();