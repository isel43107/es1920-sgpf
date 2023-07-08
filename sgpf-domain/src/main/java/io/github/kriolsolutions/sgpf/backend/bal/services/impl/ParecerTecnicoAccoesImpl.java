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

import io.github.kriolsolutions.sgpf.backend.bal.dto.ParecerTecnicoDto;
import io.github.kriolsolutions.sgpf.backend.bal.services.api.ParecerTecnicoAcoes;
import io.github.kriolsolutions.sgpf.backend.dal.entidades.docs.DocumentoCabecalho;
import io.github.kriolsolutions.sgpf.backend.dal.entidades.docs.ParecerTecnico;
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
public class ParecerTecnicoAccoesImpl extends AbstractDocumentoAndHistoryPersist<ParecerTecnico> implements ParecerTecnicoAcoes{
    
    @Inject
    public ParecerTecnicoAccoesImpl(SgpfRepositoryFacade repositoryFace) {
        super(repositoryFace);
    }

    @Override
    public void favoravel(ParecerTecnicoDto parecer) {
        ProjetoRepository projetoRepository = getRepositoryFace().getProjetoRepository();
        Optional<Projeto> projetoOptional = projetoRepository.findByIdOptional(parecer.getProjetoId());
        projetoOptional.ifPresent( projeto -> {

            Projeto.ProjetoEstado estadoAnterior = projeto.getProjEstado();
            
            if(projeto.getProjTipo() == Projeto.ProjetoTipo.BONIFICAO){
               projeto.setProjEstado(Projeto.ProjetoEstado.DESPACHO_FIN_BONIFICACAO); 
            }
            else{
                projeto.setProjEstado(Projeto.ProjetoEstado.DESPACHO_FIN_INCENTIVO);
            }
            projetoRepository.persist(projeto);
            
            ParecerTecnico pr = new ParecerTecnico();
            pr.setParecer(parecer.getTexto());
            pr.setDecisao(ParecerTecnico.ParecerTecnicoDecisao.FAVORAVEL);
            saveDocAndHistorico(projeto, estadoAnterior, SGPFStateMachine.EVENT_FAVORAVEL, DocumentoCabecalho.DocumentoTipo.PARECER_TECNICO, pr);
        });
    }

    @Override
    public void desfavoravel(ParecerTecnicoDto parecer) {
        ProjetoRepository projetoRepository = getRepositoryFace().getProjetoRepository();
        Optional<Projeto> projetoOptional = projetoRepository.findByIdOptional(parecer.getProjetoId());
        projetoOptional.ifPresent( projeto -> {

            Projeto.ProjetoEstado estadoAnterior = projeto.getProjEstado();
            
            projeto.setProjEstado(Projeto.ProjetoEstado.PROJETO_ARQUIVADO);
            projetoRepository.persist(projeto);
            
            ParecerTecnico pr = new ParecerTecnico();
            pr.setParecer(parecer.getTexto());
            pr.setDecisao(ParecerTecnico.ParecerTecnicoDecisao.DESFAVORAVEL);
            saveDocAndHistorico(projeto, estadoAnterior, SGPFStateMachine.EVENT_DESFAVORAVEL, DocumentoCabecalho.DocumentoTipo.PARECER_TECNICO, pr);
        });
    }

    @Override
    protected void saveDocDetalhe(DocumentoCabecalho doc, ParecerTecnico detalheDoc) {
        getRepositoryFace().getParecerTecnicoRepository().persist(detalheDoc);
    }
}
