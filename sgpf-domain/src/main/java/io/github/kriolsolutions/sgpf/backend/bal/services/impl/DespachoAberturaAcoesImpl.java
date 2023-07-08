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

import io.github.kriolsolutions.sgpf.backend.bal.dto.DespachoAberturaDto;
import io.github.kriolsolutions.sgpf.backend.bal.services.api.DespachoAberturaAcoes;
import io.github.kriolsolutions.sgpf.backend.dal.entidades.docs.Despacho;
import io.github.kriolsolutions.sgpf.backend.dal.entidades.docs.DespachoAbertura;
import io.github.kriolsolutions.sgpf.backend.dal.entidades.docs.DocumentoCabecalho;
import io.github.kriolsolutions.sgpf.backend.dal.entidades.projeto.Projeto;
import io.github.kriolsolutions.sgpf.backend.dal.repo.ProjetoRepository;
import io.github.kriolsolutions.sgpf.backend.dal.repo.SgpfRepositoryFacade;
import io.github.kriolsolutions.sgpf.backend.scxml.SGPFStateMachine;
import jakarta.enterprise.context.Dependent;
import java.util.Optional;
import jakarta.inject.Inject;

/**
 *
 * @author pauloborges
 */
@Dependent
public class DespachoAberturaAcoesImpl extends AbstractDocumentoAndHistoryPersist<DespachoAbertura> implements DespachoAberturaAcoes {

    @Inject
    public DespachoAberturaAcoesImpl(SgpfRepositoryFacade repositoryFace) {
        super(repositoryFace);
    }

    @Override
    public void aprovar(DespachoAberturaDto despacho) {

        ProjetoRepository projetoRepository = getRepositoryFace().getProjetoRepository();
        
        Optional<Projeto> projetoOptional = projetoRepository.findByIdOptional(despacho.getProjetoId());
        projetoOptional.ifPresent( projeto -> {
            Projeto.ProjetoEstado estadoAnterior = projeto.getProjEstado();

            projeto.setProjEstado(Projeto.ProjetoEstado.PARECER_TECNICO);
            projetoRepository.persist(projeto);
            
            DespachoAbertura docDetalhe = buildDespachoDetalhe(despacho);
            docDetalhe.setDecisao(Despacho.DespachoDecisao.APROVADO);
            
            saveDocAndHistorico(projeto, estadoAnterior, SGPFStateMachine.EVENT_APROVADO, DocumentoCabecalho.DocumentoTipo.DESPACHO_ABERTURA, docDetalhe);
        });
    }
    
    private DespachoAbertura buildDespachoDetalhe(DespachoAberturaDto desDto){
        
        desDto.getGestorFinanciamentoId();
        DespachoAbertura despacho = new DespachoAbertura();
        despacho.setGestorFinanciamento(desDto.getGestorFinanciamentoId().longValue());
        
        return despacho;
    }

    @Override
    protected void saveDocDetalhe(DocumentoCabecalho doc, DespachoAbertura detalheDoc) {
        getRepositoryFace().getDespachoAberturaRepository().persist(detalheDoc);
    }
}
