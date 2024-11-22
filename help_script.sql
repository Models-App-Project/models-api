INSERT INTO roles (id, name) VALUES (uuid_generate_v4(), 'ADMINISTRADOR');
INSERT INTO roles (id, name) VALUES (uuid_generate_v4(), 'USUARIO');


-- Insere um novo usuário na tabela 'users'
INSERT INTO users (id, username, password) VALUES (uuid_generate_v4(), 'new_admin', 'password123');

-- Obtém o ID do usuário recém-criado
-- Obtém o ID da regra 'ADMINISTRADOR'
DO $$
DECLARE
	user_id uuid;
	role_id uuid;
BEGIN
	user_id := (SELECT id FROM users WHERE username = 'new_admin');
	role_id := (SELECT id FROM roles WHERE name = 'ADMINISTRADOR');
	-- Associa o novo usuário à regra 'ADMINISTRADOR'
	INSERT INTO user_roles (user_id, role_id) VALUES (user_id, role_id);
END $$;


SELECT * FROM user_roles;
SELECT * FROM users;
SELECT * FROM file_storage;
SELECT * FROM model;

-- Desabilitar restrições de chave estrangeira temporariamente
SET FOREIGN_KEY_CHECKS = 0;

-- Gerar e executar a query para dropar todas as tabelas
SELECT CONCAT('DROP TABLE IF EXISTS ', table_name, ';')
FROM information_schema.tables
WHERE table_schema = 'postgres';

-- Habilitar restrições de chave estrangeira novamente
SET FOREIGN_KEY_CHECKS = 1;



