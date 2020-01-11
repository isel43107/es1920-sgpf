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
import io.github.kriolsolutions.sgpf.backend.dal.entidades.docs.DespachoFinBonificacao;
import io.github.kriolsolutions.sgpf.backend.dal.entidades.docs.DocumentoCabecalho;
import io.github.kriolsolutions.sgpf.backend.dal.entidades.projeto.Historico;
import io.github.kriolsolutions.sgpf.backend.dal.entidades.projeto.Projeto;
import io.github.kriolsolutions.sgpf.backend.dal.repo.DespachoFinBonificacaoRepository;
import io.github.kriolsolutions.sgpf.backend.dal.repo.DocumentoRepository;
import io.github.kriolsolutions.sgpf.backend.dal.repo.HistoricoRepository;
import io.github.kriolsolutions.sgpf.backend.dal.repo.ProjetoRepository;
import io.github.kriolsolutions.sgpf.backend.dal.repo.SgpfRepositoryFacade;
import io.github.kriolsolutions.sgpf.backend.scxml.SGPFStateMachine;
import java.util.Optional;
import javax.inject.Inject;

/**
 *
 * @author pauloborges
 */
public class DespachoFinanciamentoBonificacaoAcoesImpl implements DespachoFinanciamentoBonificacaoAcoes {

    @Inject
    private SgpfRepositoryFacade repositoryFace;


    @Override
    public void aprovar(AbstractDespachoFinDto despacho ) {
        
        ProjetoRepository projetoRepository = repositoryFace.getProjetoRepository();
        Optional<Projeto> projetoOptional = projetoRepository.findOptionalBy(despacho.getProjetoId());
        projetoOptional.ifPresent( projeto -> {
            projeto.setProjEstado(Projeto.ProjetoEstado.EM_PAGAMENTO);
            projetoRepository.saveAndFlush(projeto);
            
            saveHistorico(projeto, SGPFStateMachine.EVENT_APROVADO);
        });
       
    }

    @Override
    public void rejeitar(AbstractDespachoFinDto despacho) {
        
        DespachoFinBonificacaoDto desBoni = (DespachoFinBonificacaoDto)despacho;
        
        DespachoFinBonificacao desFinBonificacao = new DespachoFinBonificacao();
        desFinBonificacao.setMntMaxBonificacao(desBoni.getMntMaxBonificacao());
        
        desFinBonificacao.setPeriodo(desBoni.getPeriodo());
        
        ProjetoRepository projetoRepository = repositoryFace.getProjetoRepository();
        Optional<Projeto> projetoOptional = projetoRepository.findOptionalBy(despacho.getProjetoId());
        projetoOptional.ifPresent( projeto -> {
            projeto.setProjEstado(Projeto.ProjetoEstado.PROJETO_REJEITADO);
            projetoRepository.saveAndFlush(projeto);
            
            saveHistorico(projeto, SGPFStateMachine.EVENT_REJEITADO);
        });
    }
    
    //TODO Necessario definir extensao - DUPLICADO (COPY PASTE)
    private void saveHistorico(Projeto projeto, String sgpfStateMachine){

        DocumentoRepository documentoRepository = repositoryFace.getDocumentoRepository();
        HistoricoRepository historicoRepo = repositoryFace.getHistoricoRepository();
        DespachoFinBonificacaoRepository boniRepo = repositoryFace.getDespachoFinBonificacaoRepository();
        
        //Guarda Cabeçalho do documento
        DocumentoCabecalho doc = new DocumentoCabecalho();
        doc.setProjeto(projeto);
        doc.setDocTipo(DocumentoCabecalho.DocumentoTipo.DESPACHO_FIN_BONIFICACAO);

        doc = documentoRepository.saveAndFlush(doc);
        
        /* */
        
        // Guardar no historico o evento(Evolução maquina estado)
        Historico his = new Historico();
        his.setDocumento(doc);
        his.setProjeto(projeto);
        his.setProjNumero(projeto.getProjNumero());
        his.setEstadoAtual(projeto.getProjEstado());
        his.setEvento(sgpfStateMachine);
        historicoRepo.saveAndFlush(his);

    }
}
