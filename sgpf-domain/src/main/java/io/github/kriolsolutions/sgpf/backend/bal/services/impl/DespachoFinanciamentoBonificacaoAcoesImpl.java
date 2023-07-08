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

import io.github.kriolsolutions.sgpf.backend.bal.dto.AbstractDespachoFinDto;
import io.github.kriolsolutions.sgpf.backend.bal.dto.DespachoFinBonificacaoDto;
import io.github.kriolsolutions.sgpf.backend.bal.services.api.DespachoFinanciamentoBonificacaoAcoes;
import io.github.kriolsolutions.sgpf.backend.dal.entidades.docs.Despacho;
import io.github.kriolsolutions.sgpf.backend.dal.entidades.docs.DespachoFinBonificacao;
import io.github.kriolsolutions.sgpf.backend.dal.entidades.docs.DocumentoCabecalho;
import io.github.kriolsolutions.sgpf.backend.dal.entidades.projeto.Projeto;
import io.github.kriolsolutions.sgpf.backend.dal.repo.DespachoFinBonificacaoRepository;
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
public class DespachoFinanciamentoBonificacaoAcoesImpl extends AbstractDocumentoAndHistoryPersist<DespachoFinBonificacao> implements DespachoFinanciamentoBonificacaoAcoes {


    @Inject
    public DespachoFinanciamentoBonificacaoAcoesImpl(SgpfRepositoryFacade repositoryFace) {
        super(repositoryFace);
    }


    @Override
    public void aprovar(AbstractDespachoFinDto despacho ) {
        DespachoFinBonificacaoDto desBoni = (DespachoFinBonificacaoDto)despacho;
        ProjetoRepository projetoRepository = getRepositoryFace().getProjetoRepository();
        Optional<Projeto> projetoOptional = projetoRepository.findByIdOptional(despacho.getProjetoId());
        projetoOptional.ifPresent( (Projeto projeto) -> {
            Projeto.ProjetoEstado estadoAnterior = projeto.getProjEstado();
            projeto.setProjEstado(Projeto.ProjetoEstado.EM_PAGAMENTO);
            projetoRepository.persist(projeto);;
            DespachoFinBonificacao desDetalhe = buildDespachoDetalhe(desBoni);
            desDetalhe.setDecisao(Despacho.DespachoDecisao.APROVADO);
            DocumentoCabecalho.DocumentoTipo docTipo = DocumentoCabecalho.DocumentoTipo.DESPACHO_FIN_BONIFICACAO;
            saveDocAndHistorico(projeto, estadoAnterior, SGPFStateMachine.EVENT_APROVADO, docTipo, desDetalhe);
        });
       
    }

    @Override
    public void rejeitar(AbstractDespachoFinDto despacho) {
        
        DespachoFinBonificacaoDto desDto = (DespachoFinBonificacaoDto)despacho;
        
        DespachoFinBonificacao desFinBonificacao = new DespachoFinBonificacao();
        desFinBonificacao.setMntMaxBonificacao(desDto.getMntMaxBonificacao());
        
        desFinBonificacao.setPeriodo(desDto.getPeriodo());
        
        ProjetoRepository projetoRepository = getRepositoryFace().getProjetoRepository();
        Optional<Projeto> projetoOptional = projetoRepository.findByIdOptional(despacho.getProjetoId());
        projetoOptional.ifPresent((Projeto projeto) -> {
            Projeto.ProjetoEstado estadoAnterior = projeto.getProjEstado();
            
            projeto.setProjEstado(Projeto.ProjetoEstado.PROJETO_REJEITADO);
            projetoRepository.persist(projeto);
            DespachoFinBonificacao desDetalhe = buildDespachoDetalhe(desDto);
            desDetalhe.setDecisao(Despacho.DespachoDecisao.REJEITADO);
            DocumentoCabecalho.DocumentoTipo docTipo = DocumentoCabecalho.DocumentoTipo.DESPACHO_FIN_BONIFICACAO;
            saveDocAndHistorico(projeto, estadoAnterior, SGPFStateMachine.EVENT_REJEITADO, docTipo, desDetalhe);
        });
    }
    
    private DespachoFinBonificacao buildDespachoDetalhe(DespachoFinBonificacaoDto desDto){
        
        DespachoFinBonificacao desDespachoFinBonificacao = new DespachoFinBonificacao();
        desDespachoFinBonificacao.setCustoElegivel(desDto.getCustoElegivel());
        desDespachoFinBonificacao.setMontanteFinanciado(desDto.getMontanteFinanciado());
        desDespachoFinBonificacao.setMntMaxBonificacao(desDto.getMntMaxBonificacao());
        desDespachoFinBonificacao.setTaxBonificacao(desDto.getTaxBonificacao());
        desDespachoFinBonificacao.setPeriodo(desDto.getPeriodo());
        
        return desDespachoFinBonificacao;
    }

    @Override
    protected void saveDocDetalhe(DocumentoCabecalho doc, DespachoFinBonificacao detalheDoc) {
        DespachoFinBonificacaoRepository detalheDocRepo = getRepositoryFace().getDespachoFinBonificacaoRepository();
        detalheDocRepo.persist(detalheDoc);
    }

}
