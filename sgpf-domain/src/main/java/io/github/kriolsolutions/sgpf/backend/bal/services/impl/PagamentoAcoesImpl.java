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

import io.github.kriolsolutions.sgpf.backend.bal.dto.PagamentoDto;
import io.github.kriolsolutions.sgpf.backend.bal.services.api.PagamentoAcoes;
import io.github.kriolsolutions.sgpf.backend.dal.entidades.docs.DocumentoCabecalho;
import io.github.kriolsolutions.sgpf.backend.dal.entidades.docs.Pagamento;
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
public class PagamentoAcoesImpl extends AbstractDocumentoAndHistoryPersist<Pagamento> implements PagamentoAcoes {

    @Inject
    public PagamentoAcoesImpl(SgpfRepositoryFacade repositoryFace) {
        super(repositoryFace);
    }

    @Override
    public void efectuarPagamento(PagamentoDto pagDto) {

        ProjetoRepository projetoRepository = getRepositoryFace().getProjetoRepository();
        Optional<Projeto> projetoOptional = projetoRepository.findByIdOptional(pagDto.getProjetoId());
        projetoOptional.ifPresent(projeto -> {

            Projeto.ProjetoEstado estadoAnterior = projeto.getProjEstado();

            //TODO verificar se MontanteFinanciado  e/ou Prazo nao foi atingido mantem estado pagamento
            //projetoRepository.persist(projeto);
            if (estadoAnterior == Projeto.ProjetoEstado.EM_PAGAMENTO) {

                Pagamento pr = new Pagamento();
                pr.setMntPago(pagDto.getMontantePagamento());
                //pr.setDescricao(pagDto.getDescricao());
                saveDocAndHistorico(projeto, estadoAnterior, SGPFStateMachine.EVENT_PAGAMENTO, DocumentoCabecalho.DocumentoTipo.PAGAMENTO, pr);
            }
            //ELSE Muda estado para fechado
        });

    }

    @Override
    protected void saveDocDetalhe(DocumentoCabecalho doc, Pagamento detalheDoc) {
        getRepositoryFace().getPagamentoRepository().persist(detalheDoc);
    }

}
