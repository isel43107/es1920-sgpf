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
import io.github.kriolsolutions.sgpf.backend.dal.entidades.projeto.Historico;
import io.github.kriolsolutions.sgpf.backend.dal.entidades.projeto.Projeto;
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
public class DespachoAberturaAcoesImpl implements DespachoAberturaAcoes {

    @Inject
    private SgpfRepositoryFacade repositoryFace;

    @Override
    public void aprovar(DespachoAberturaDto despacho) {

        
        ProjetoRepository projetoRepository = repositoryFace.getProjetoRepository();
        HistoricoRepository historicoRepo = repositoryFace.getHistoricoRepository();
        
        Optional<Projeto> projetoOptional = projetoRepository.findOptionalBy(despacho.getProjetoId());
        projetoOptional.ifPresent( projeto -> {
            Projeto.ProjetoEstado estadoAnterior = projeto.getProjEstado();

            projeto.setProjEstado(Projeto.ProjetoEstado.PARECER_TECNICO);
            projetoRepository.saveAndFlush(projeto);

            // Guardar no historico o evento(Evolução maquina estado)
            Historico his = new Historico();
            //his.setDocumento(doc);
            his.setProjeto(projeto);
            his.setProjNumero(projeto.getProjNumero());
            his.setEstadoAnterior(estadoAnterior);
            his.setEstadoAtual(projeto.getProjEstado());
            his.setEvento(SGPFStateMachine.EVENT_APROVADO);
            historicoRepo.saveAndFlush(his);
        });
        
        
    }

}
