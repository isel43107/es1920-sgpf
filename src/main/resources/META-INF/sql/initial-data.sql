-- Copyright 2019 KriolSolutions(KS).
-- 
-- Licensed under the Apache License, Version 2.0 (the "License");
-- you may not use this file except in compliance with the License.
-- You may obtain a copy of the License at
-- 
--      http://www.apache.org/licenses/LICENSE-2.0
-- 
-- Unless required by applicable law or agreed to in writing, software
-- distributed under the License is distributed on an "AS IS" BASIS,
-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
-- See the License for the specific language governing permissions and
-- limitations under the License.
--

-- inserção projeto exemplo
-- OS CAMPO TIMESTAMP DEVEM CONTER DATETIME CORRECT, SENAO EXECEPTION
-- --- INVALIDO '2019-11-10 12:00:00'
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
INSERT INTO projeto.projeto( id, created_date, last_modified_date, version, contato_email, contato_nome, contato_telefone, proj_descricao, proj_designacao, proj_estado, proj_mnt_solicitado, proj_nib, proj_numero, proj_tipo, promotor_designacao, promotor_nif, promotor_nacionalidade ) values(3, '2019-11-10 12:00:00', '2019-11-10 12:00:00', 1, 'alex@example.com', 'Alex', '36171111111111', 'Projeto 03', 'Projeto-03', 'DESPACHO_ABERTURA', 500000.0, 'NIB 1111', '2019-00003', 'INCENTIVO', 'ISEL', '111111111', 'Portugal');
INSERT INTO projeto.projeto( id, created_date, last_modified_date, version, contato_email, contato_nome, contato_telefone, proj_descricao, proj_designacao, proj_estado, proj_mnt_solicitado, proj_nib, proj_numero, proj_tipo, promotor_designacao, promotor_nif, promotor_nacionalidade ) values(4, '2019-12-10 12:00:00', '2019-12-10 12:00:00', 1, 'luis@example.com', 'Luis', '35191111111111', 'Projeto 04', 'Projeto 04', 'DESPACHO_FIN_INCENTIVO', 100000.0, 'NIB 2222', '2019-00004', 'INCENTIVO', 'ISEL', '111111111', 'Portugal');
INSERT INTO projeto.projeto( id, created_date, last_modified_date, version, contato_email, contato_nome, contato_telefone, proj_descricao, proj_designacao, proj_estado, proj_mnt_solicitado, proj_nib, proj_numero, proj_tipo, promotor_designacao, promotor_nif, promotor_nacionalidade ) values(5, '2019-11-10 12:00:00', '2019-11-10 12:00:00', 1, 'alex@example.com', 'Alex', '36171111111111', 'projeto 05', 'Projeto-05', 'EM_PAGAMENTO', 500000.0, 'NIB 1111', '2019-00005', 'INCENTIVO', 'ISEL', '111111111', 'Portugal');
INSERT INTO projeto.projeto( id, created_date, last_modified_date, version, contato_email, contato_nome, contato_telefone, proj_descricao, proj_designacao, proj_estado, proj_mnt_solicitado, proj_nib, proj_numero, proj_tipo, promotor_designacao, promotor_nif, promotor_nacionalidade ) values(6, '2019-12-10 12:00:00', '2019-12-10 12:00:00', 1, 'luis@example.com', 'Luis', '35191111111111', 'Projeto 06', 'Projeto 06', 'PROJETO_REJEITADO', 100000.0, 'NIB 2222', '2019-00006', 'INCENTIVO', 'ISEL', '111111111', 'Portugal');

-- INSERT HISTORICO
-- (BUG) INSERT INTO projeto.historico( id, created_date, last_modified_date, version, stm_estado_atual, stm_evento, proj_numero, fk_projeto) values(1, '2019-11-10 12:00:00', '2019-11-10 12:00:00', 1, 'EM_CANDIDATURA', 'INIT', '2019-00001', 1);