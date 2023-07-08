/*
 * Copyright 2020 kriolSolutions.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.kriolsolutions.sgpf.backend.bal.services.impl;

import io.github.kriolsolutions.sgpf.backend.dal.entidades.docs.DocumentoCabecalho;
import io.github.kriolsolutions.sgpf.backend.dal.entidades.projeto.Historico;
import io.github.kriolsolutions.sgpf.backend.dal.entidades.projeto.Projeto;
import io.github.kriolsolutions.sgpf.backend.dal.repo.DocumentoRepository;
import io.github.kriolsolutions.sgpf.backend.dal.repo.HistoricoRepository;
import io.github.kriolsolutions.sgpf.backend.dal.repo.SgpfRepositoryFacade;

/**
 *
 * @author pauloborges
 * @param <T>
 */
abstract class AbstractDocumentoAndHistoryPersist<T> {

    private final SgpfRepositoryFacade repositoryFace;

    
    public AbstractDocumentoAndHistoryPersist(SgpfRepositoryFacade repositoryFace) {
        this.repositoryFace = repositoryFace;
    }

    protected abstract void saveDocDetalhe(DocumentoCabecalho doc, T detalheDoc);

    /**
     * 
     * @param projeto projeto
     * @param estadoAnterior Estado anterior a transição
     * @param evento Evento que origina a transição de estado
     * @param docTipo Caso null (Guarda apenas historico sem referencia para o documento)
     * @param detalheDoc 
     */
    protected void saveDocAndHistorico(Projeto projeto, Projeto.ProjetoEstado estadoAnterior, String evento, DocumentoCabecalho.DocumentoTipo docTipo, T detalheDoc) {

        DocumentoRepository documentoRepository = repositoryFace.getDocumentoRepository();
        HistoricoRepository historicoRepo = repositoryFace.getHistoricoRepository();

        // Guardar no historico o evento (evento de transiçao da maquina estado)
        Historico his = new Historico();
        his.setProjeto(projeto);
        his.setProjNumero(projeto.getProjNumero());
        his.setEstadoAnterior(estadoAnterior);
        his.setEstadoAtual(projeto.getProjEstado());
        his.setEvento(evento);
        historicoRepo.save(his);
        
        //Guarda Cabeçalho do documento
        //se o tipo for null não guarda documento, mas historico
        DocumentoCabecalho doc = new DocumentoCabecalho();
        doc.setHistorico(his);
        
        if (docTipo != null) {
            
            doc.setProjeto(projeto);
            doc.setDocTipo(docTipo);
            doc = documentoRepository.save(doc);

            /*Detalhes do */
            //desDetalhe.setDocumento(doc);
            //boniRepo.save(desDetalhe);
            saveDocDetalhe(doc, detalheDoc);
        }

    }

    protected SgpfRepositoryFacade getRepositoryFace() {
        return repositoryFace;
    }
}
