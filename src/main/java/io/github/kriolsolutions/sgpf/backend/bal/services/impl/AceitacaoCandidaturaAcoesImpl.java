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

import io.github.kriolsolutions.sgpf.backend.bal.services.api.AceitacaoCandidaturaAcoes;
import io.github.kriolsolutions.sgpf.backend.dal.entidades.projeto.Historico;
import io.github.kriolsolutions.sgpf.backend.dal.entidades.projeto.Projeto;
import io.github.kriolsolutions.sgpf.backend.dal.entidades.docs.Candidatura;
import io.github.kriolsolutions.sgpf.backend.dal.entidades.docs.Documento;
import io.github.kriolsolutions.sgpf.backend.dal.entidades.docs.Documento.DocumentoTipo;
import io.github.kriolsolutions.sgpf.backend.dal.repo.CandidaturaRepository;
import io.github.kriolsolutions.sgpf.backend.dal.repo.DocumentoRepository;
import io.github.kriolsolutions.sgpf.backend.dal.repo.HistoricoRepository;
import io.github.kriolsolutions.sgpf.backend.dal.repo.ProjetoRepository;
import io.github.kriolsolutions.sgpf.backend.dal.repo.SgpfRepositoryFacade;
import io.github.kriolsolutions.sgpf.backend.scxml.SGPFStateMachine;
import java.util.Date;
import javax.inject.Inject;

/**
 *
 * @author pauloborges
 */
public class AceitacaoCandidaturaAcoesImpl implements AceitacaoCandidaturaAcoes{
    
    @Inject 
    private SgpfRepositoryFacade repositoryFace;

    @Override
    public void aceitar(Projeto projeto) {
        
        ProjetoRepository projetoRepository = repositoryFace.getProjetoRepository();
        DocumentoRepository documentoRepository = repositoryFace.getDocumentoRepository();
        CandidaturaRepository candidaturaRepository = repositoryFace.getCandidaturaRepository();
        HistoricoRepository historicoRepo = repositoryFace.getHistoricoRepository();
        
        //PROJETO -  Guarda o projeto
        projeto.setProjEstado(Projeto.ProjetoEstado.EM_CANDIDATURA);
        
        //PROJETO -  Num projeto temporario
        String numProjeto = "TMP_" + ProjetoGeneradorNumero.gerarNumeroProjeto(new Date().getTime());
        projeto.setProjNumero(numProjeto);
        projeto = projetoRepository.save(projeto);
        
        //PROJETO -  Num projeto Final
        numProjeto = ProjetoGeneradorNumero.gerarNumeroProjeto(projeto.getId());
        projeto.setProjNumero(numProjeto);
        
        projeto = projetoRepository.saveAndFlush(projeto);
        
        //Guarda Cabeçalho do documento
        Documento doc = new Documento();
        doc.setProjeto(projeto);
        doc.setDocTipo(DocumentoTipo.CANDIDATURA);

        doc = documentoRepository.saveAndFlush(doc);
        
        //Guarda Detable do Candidatura
        Candidatura candidatura = new Candidatura();
        candidatura.setDecisao(Candidatura.CandidaturaDecisao.ENQUADRADO);
        candidatura.setDocumento(doc);
        
        candidaturaRepository.saveAndFlush(candidatura);
        
        // Guardar no historico o evento(Evolução maquina estado)
        Historico his = new Historico();
        his.setDocumento(doc);
        his.setProjeto(projeto);
        his.setProjNumero(projeto.getProjNumero());
        his.setEstadoAtual(projeto.getProjEstado());
        his.setEvento(SGPFStateMachine.EVENT_CAND_REGISTAR);
        historicoRepo.saveAndFlush(his);

    }
    
    @Override
    public void abir(Projeto projeto) {
        
        ProjetoRepository projetoRepository = repositoryFace.getProjetoRepository();
        HistoricoRepository historicoRepo = repositoryFace.getHistoricoRepository();
        
        Projeto.ProjetoEstado estadoAnterior = projeto.getProjEstado();
        
        projeto.setProjEstado(Projeto.ProjetoEstado.DESPACHO_ABERTURA);
        projetoRepository.saveAndFlush(projeto);
        
        // Guardar no historico o evento(Evolução maquina estado)
        Historico his = new Historico();
        //his.setDocumento(doc);
        his.setProjeto(projeto);
        his.setProjNumero(projeto.getProjNumero());
        his.setEstadoAnterior(estadoAnterior);
        his.setEstadoAtual(projeto.getProjEstado());
        his.setEvento(SGPFStateMachine.EVENT_ENQUADRADO);
        historicoRepo.saveAndFlush(his);
    }

    @Override
    public void arquivar(Projeto projeto) {
        
        ProjetoRepository projetoRepository = repositoryFace.getProjetoRepository();
        HistoricoRepository historicoRepo = repositoryFace.getHistoricoRepository();
        
        Projeto.ProjetoEstado estadoAnterior = projeto.getProjEstado();
        
        projeto.setProjEstado(Projeto.ProjetoEstado.PROJETO_ARQUIVADO);
        projetoRepository.saveAndFlush(projeto);
        
        // Guardar no historico o evento(Evolução maquina estado)
        Historico his = new Historico();
        //his.setDocumento(doc);
        his.setProjeto(projeto);
        his.setProjNumero(projeto.getProjNumero());
        his.setEstadoAnterior(estadoAnterior);
        his.setEstadoAtual(projeto.getProjEstado());
        his.setEvento(SGPFStateMachine.EVENT_DESENQUADRADO);
        historicoRepo.saveAndFlush(his);
    }
    
}
