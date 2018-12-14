--Stored Procedures

--Registrar LogDocumment (operacao, id_pessoa, id_medida, id_local)
--Sem Retorno
CREATE OR REPLACE FUNCTION sensor_logger.fn_log (
  p_operacao VARCHAR(40),
  p_id_pessoa INTEGER,
  p_id_medida INTEGER,
  p_id_local INTEGER
)
  RETURNS VOID AS
  $$
  DECLARE
    horario_atual TIMESTAMP;
  BEGIN
    SELECT CURRENT_TIMESTAMP INTO horario_atual;
    INSERT INTO sensor_logger.tb_log
      (horario, operacao, id_pessoa, id_medida, id_local)
    VALUES
      (horario_atual, p_operacao, p_id_pessoa, p_id_medida, p_id_local);
  END
  $$
  LANGUAGE plpgsql;

--Verificar Usuário
--Retorna: '-1 se não encontrado e id se encontrado'
CREATE OR REPLACE FUNCTION sensor_logger.fn_consulta_usuario (
  p_id_usuario INTEGER
)
  RETURNS INTEGER AS
  $$
  DECLARE
    dados INTEGER;
  BEGIN
    SELECT id_pessoa INTO dados FROM sensor_logger.tb_usuarios WHERE id_pessoa = $1;
    IF NOT FOUND THEN
      RETURN -1;
    ELSE
      RETURN dados;
    END IF;
  END
  $$
  LANGUAGE plpgsql;

--Inserir Medições (temp_ar, umid_ar, umid_solo, luminosidade, id_pessoa, id_local)
--Retorna: 'Sucesso!'
CREATE OR REPLACE FUNCTION sensor_logger.fn_inserir_medicoes (
  p_temp_ar NUMERIC(4,2),
  p_umid_ar NUMERIC(4,2),
  p_umid_solo NUMERIC(4,2),
  p_luminosidade NUMERIC(4,2),
  p_id_pessoa INTEGER,
  p_id_local INTEGER
)
  RETURNS TEXT AS
  $$
  DECLARE
    out_id_medida INTEGER;
  BEGIN
    INSERT INTO sensor_logger.tb_medidas
      (temp_ar, umid_ar, umid_solo, luminosidade, id_pessoa, id_local)
    VALUES
      (p_temp_ar, p_umid_ar, p_umid_solo, p_luminosidade, p_id_pessoa, p_id_local)
    RETURNING
      id_medida INTO out_id_medida;

    PERFORM sensor_logger.fn_log (
      'INSERT MEDIDA', p_id_pessoa, out_id_medida, p_id_local
    );

    RETURN 'Sucesso!';
  END
  $$
  LANGUAGE plpgsql;

--Deletar Medições (id_medida, id_pessoa, id_local)
--Retorna: 'Sucesso!'
CREATE OR REPLACE FUNCTION sensor_logger.fn_deletar_medicoes (
  p_id_medida INTEGER,
  p_id_pessoa INTEGER,
  p_id_local INTEGER
)
  RETURNS TEXT AS
  $$
  BEGIN
    DELETE FROM sensor_logger.tb_medidas WHERE p_id_medida = id_medida;

    PERFORM sensor_logger.fn_log (
      'DELETE MEDIDA', p_id_pessoa, p_id_medida, p_id_local
    );
  RETURN 'Sucesso';
  END
  $$
  LANGUAGE plpgsql;

--Cadastrar ou Atualizar Usuário (operacao('I' ou 'U'), nome, idade, curso, id_usuario(caso atualizar))
--Retorna: '-1 se erro, 0 se operação nao encontrada e id_pessoa se sucesso'
CREATE OR REPLACE FUNCTION sensor_logger.fn_usuario (
  p_operacao CHAR,
  p_nome VARCHAR(40),
  p_idade INTEGER,
  p_curso VARCHAR(40),
  p_id_usuario INTEGER
)
  RETURNS INTEGER AS
  $$
  DECLARE
    out_id_usuario INTEGER;
  BEGIN
    IF p_operacao = 'I' THEN
      INSERT INTO sensor_logger.tb_usuarios (nome, idade, curso)
        VALUES (p_nome, p_idade, p_curso)
        RETURNING id_pessoa INTO out_id_usuario;

      PERFORM sensor_logger.fn_log (
        'INSERT USUARIO', out_id_usuario, NULL, NULL
      );
      RETURN out_id_usuario;
    ELSIF p_operacao = 'U' THEN
      UPDATE sensor_logger.tb_usuarios
        SET
          nome = p_nome,
          idade = p_idade,
          curso = p_curso
        WHERE
          id_pessoa = p_id_usuario;

        PERFORM sensor_logger.fn_log (
          'UPDATE USUARIO', p_id_usuario, NULL, NULL
        );
        RETURN p_id_usuario;
    ELSE
      RETURN 0;
    END IF;
    EXCEPTION WHEN OTHERS THEN RETURN -1;
  END
  $$
  LANGUAGE plpgsql;

--Cadastrar ou Atualizar Local (operacao, id_pessoa, nome, cidade, estado, clima, id_local(caso atualizar))
--Retorna: '-1 se erro, 0 se operação nao encontrada e id_local caso sucesso'
CREATE OR REPLACE FUNCTION sensor_logger.fn_local (
  p_operacao CHAR,
  p_id_pessoa INTEGER,
  p_nome VARCHAR(40),
  p_cidade VARCHAR(40),
  p_estado VARCHAR(2),
  p_clima VARCHAR(40),
  p_id_local INTEGER
)
  RETURNS INTEGER AS
  $$
  DECLARE
    out_id_local INTEGER;
  BEGIN
    IF p_operacao = 'I' THEN
      INSERT INTO sensor_logger.tb_locais (nome, cidade, estado, clima)
        VALUES (p_nome, p_cidade, p_estado, p_clima)
        RETURNING id_local INTO out_id_local;

      PERFORM sensor_logger.fn_log (
        'INSERT LOCAL', p_id_pessoa, NULL, out_id_local
      );
      RETURN out_id_local;

    ELSIF p_operacao = 'U' THEN
      UPDATE sensor_logger.tb_locais
        SET
          nome = p_nome,
          cidade = p_cidade,
          estado = p_estado,
          clima = p_clima
        WHERE
          id_local = p_id_local;

        PERFORM sensor_logger.fn_log (
          'UPDATE LOCAL', p_id_pessoa, NULL, p_id_local
        );
        RETURN p_id_local;
    ELSE
      RETURN 0;
    END IF;
    EXCEPTION WHEN OTHERS THEN RETURN -1;
  END
  $$
  LANGUAGE plpgsql;