-- inserção projeto exemplo
-- INSERT 

INSERT INTO projeto.utilizador( id, created_date, last_modified_date, version, username, password, perfil) values(1, '2019-11-10 12:00:00', '2019-11-10 12:00:00', 1, 'tc1', '123', 'TECNICO');
INSERT INTO projeto.utilizador( id, created_date, last_modified_date, version, username, password, perfil) values(2, '2019-11-10 12:00:00', '2019-11-10 12:00:00', 1, 'gf1', '123', 'GESTOR_FINANCIAMENTO');
INSERT INTO projeto.utilizador( id, created_date, last_modified_date, version, username, password, perfil) values(3, '2019-11-10 12:00:00', '2019-11-10 12:00:00', 1, 'cm1', '123', 'COMISAO_FINANCIAMENTO');

INSERT INTO projeto.utilizador( id, created_date, last_modified_date, version, username, password, perfil) values(4, '2019-11-10 12:00:00', '2019-11-10 12:00:00', 1, 'tc2', '123', 'TECNICO');
INSERT INTO projeto.utilizador( id, created_date, last_modified_date, version, username, password, perfil) values(5, '2019-11-10 12:00:00', '2019-11-10 12:00:00', 1, 'gf2', '123', 'GESTOR_FINANCIAMENTO');
INSERT INTO projeto.utilizador( id, created_date, last_modified_date, version, username, password, perfil) values(6, '2019-11-10 12:00:00', '2019-11-10 12:00:00', 1, 'cm2', '123', 'COMISAO_FINANCIAMENTO');

-- INSERT PROJETOS
INSERT INTO projeto.projeto( id, created_date, last_modified_date, version, contato_email, contato_nome, contato_telefone, proj_descricao, proj_designacao, proj_estado, proj_mnt_solicitado, proj_nib, proj_numero, proj_tipo, promotor_designacao, promotor_nif, promotor_nacionalidade ) values(1, '2019-11-10 12:00:00', '2019-11-10 12:00:00', 1, 'alex@example.com', 'Alex', '36171111111111', 'FET-Open project ChipScope and the new tiny microscopes', 'ChipScope', 'EM_CANDIDATURA', 500000.0, 'NIB 1111', '2019-00001', 'INCENTIVO', 'UNIVERSIDADE DE BARCELONA', '111111111', 'Espanha');
INSERT INTO projeto.projeto( id, created_date, last_modified_date, version, contato_email, contato_nome, contato_telefone, proj_descricao, proj_designacao, proj_estado, proj_mnt_solicitado, proj_nib, proj_numero, proj_tipo, promotor_designacao, promotor_nif, promotor_nacionalidade ) values(2, '2019-12-10 12:00:00', '2019-12-10 12:00:00', 1, 'lui@example.com', 'Luis', '35191111111111', 'Future Internet Technologies - ISEL', 'FIT-ISEL', 'EM_CANDIDATURA', 1000000.0, 'NIB 2222', '2019-00002', 'INCENTIVO', 'ISEL', '111111111', 'Portugal');

-- INSERT HISTORICO
INSERT INTO projeto.historico(id, created_date, last_modified_date, version, proj_numero, fk_projeto, stm_estado_atual, stm_evento) values(1, '2019-12-10 12:00:00', '2019-12-10 12:00:00', 1, '2019-00001', 1, 'EM_CANDIDATURA', 'INIT');