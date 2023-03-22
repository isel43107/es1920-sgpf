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
import io.github.kriolsolutions.sgpf.backend.dal.entidades.projeto.Projeto;
import io.github.kriolsolutions.sgpf.backend.dal.entidades.docs.Candidatura;
import io.github.kriolsolutions.sgpf.backend.dal.entidades.docs.DocumentoCabecalho;
import io.github.kriolsolutions.sgpf.backend.dal.repo.CandidaturaRepository;
import io.github.kriolsolutions.sgpf.backend.dal.repo.ProjetoRepository;
import io.github.kriolsolutions.sgpf.backend.dal.repo.SgpfRepositoryFacade;
import io.github.kriolsolutions.sgpf.backend.scxml.SGPFStateMachine;
import java.util.Date;
import javax.inject.Inject;

/**
 *
 * @author pauloborges
 */
public class AceitacaoCandidaturaAcoesImpl extends AbstractDocumentoAndHistoryPersist implements AceitacaoCandidaturaAcoes {

    @Inject
    public AceitacaoCandidaturaAcoesImpl(SgpfRepositoryFacade repositoryFace) {
        super(repositoryFace);
    }

    @Override
    public void aceitar(Projeto projeto) {

        ProjetoRepository projetoRepository = getRepositoryFace().getProjetoRepository();

        Projeto.ProjetoEstado estadoAnterior = Projeto.ProjetoEstado.EM_CANDIDATURA;

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

        //Documento Detalhe
        Candidatura candidatura = new Candidatura();
        candidatura.setDecisao(Candidatura.CandidaturaDecisao.ENQUADRADO);
        saveDocAndHistorico(projeto, estadoAnterior, SGPFStateMachine.EVENT_CAND_REGISTAR, null, candidatura);

    }

    @Override
    public void abir(Projeto projeto) {

        ProjetoRepository projetoRepository = getRepositoryFace().getProjetoRepository();

        Projeto.ProjetoEstado estadoAnterior = projeto.getProjEstado();

        projeto.setProjEstado(Projeto.ProjetoEstado.DESPACHO_ABERTURA);
        projetoRepository.saveAndFlush(projeto);

        //doc detalhe nao há
        saveDocAndHistorico(projeto, estadoAnterior, SGPFStateMachine.EVENT_ENQUADRADO, null, null);

    }

    @Override
    public void arquivar(Projeto projeto) {

        ProjetoRepository projetoRepository = getRepositoryFace().getProjetoRepository();

        Projeto.ProjetoEstado estadoAnterior = projeto.getProjEstado();

        projeto.setProjEstado(Projeto.ProjetoEstado.PROJETO_ARQUIVADO);
        projetoRepository.saveAndFlush(projeto);

        ///doc detalhe nao há
        saveDocAndHistorico(projeto, estadoAnterior, SGPFStateMachine.EVENT_DESENQUADRADO, null, null);
    }

    @Override
    protected void saveDocDetalhe(DocumentoCabecalho doc, Object detalheDoc) {

        
        if (detalheDoc != null && detalheDoc instanceof Candidatura) {
            CandidaturaRepository candidaturaRepository = getRepositoryFace().getCandidaturaRepository();

            Candidatura candidatura = (Candidatura) detalheDoc;
            //Guarda Detable do Candidatura
            candidatura.setDocumento(doc);
            candidaturaRepository.saveAndFlush(candidatura);
        }
    }

}
