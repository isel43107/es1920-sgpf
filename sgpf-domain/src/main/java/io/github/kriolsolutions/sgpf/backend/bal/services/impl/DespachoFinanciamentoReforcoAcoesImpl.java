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

import io.github.kriolsolutions.sgpf.backend.bal.dto.PedidoReforcoDto;
import io.github.kriolsolutions.sgpf.backend.bal.services.api.DespachoFinanciamentoReforcoAcoes;
import io.github.kriolsolutions.sgpf.backend.dal.entidades.docs.DespachoFinReforco;
import io.github.kriolsolutions.sgpf.backend.dal.entidades.docs.DocumentoCabecalho;
import io.github.kriolsolutions.sgpf.backend.dal.entidades.docs.DocumentoCabecalho.DocumentoTipo;
import io.github.kriolsolutions.sgpf.backend.dal.entidades.docs.PedidoReforco;
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
public class DespachoFinanciamentoReforcoAcoesImpl extends AbstractDocumentoAndHistoryPersist implements DespachoFinanciamentoReforcoAcoes {

    @Inject
    public DespachoFinanciamentoReforcoAcoesImpl(SgpfRepositoryFacade repositoryFace) {
        super(repositoryFace);
    }

    @Override
    public void aprovar(PedidoReforcoDto despacho) {

        ProjetoRepository projetoRepository = getRepositoryFace().getProjetoRepository();
        Optional<Projeto> projetoOptional = projetoRepository.findByIdOptional(despacho.getProjetoId());
        projetoOptional.ifPresent(projeto -> {

            Projeto.ProjetoEstado estadoAnterior = projeto.getProjEstado();
            
            projeto.setProjEstado(Projeto.ProjetoEstado.EM_PAGAMENTO);
            projetoRepository.persist(projeto);
            
            DespachoFinReforco pr = new DespachoFinReforco();
            pr.setMontanteAprovado(despacho.getMontanteReforco());
            pr.setDescricao(despacho.getDescricao());

            ///doc detalhe nao há
            saveDocAndHistorico(projeto, estadoAnterior, SGPFStateMachine.EVENT_APROVADO, DocumentoTipo.DESPACHO_FIN_REFORCO, pr);
        });
    }

    //@TODO Devera receber como parametro DespachoFinReforcoDto
    @Override
    public void rejeitar(PedidoReforcoDto despacho) {

        ProjetoRepository projetoRepository = getRepositoryFace().getProjetoRepository();
        Optional<Projeto> projetoOptional = projetoRepository.findByIdOptional(despacho.getProjetoId());
        projetoOptional.ifPresent(projeto -> {

            Projeto.ProjetoEstado estadoAnterior = projeto.getProjEstado();

            projeto.setProjEstado(Projeto.ProjetoEstado.EM_PAGAMENTO);
            projetoRepository.persist(projeto);
            
            DespachoFinReforco pr = new DespachoFinReforco();
            pr.setMontanteAprovado(despacho.getMontanteReforco());
            pr.setDescricao(despacho.getDescricao());

            ///doc detalhe nao há
            saveDocAndHistorico(projeto, estadoAnterior, SGPFStateMachine.EVENT_REJEITADO, DocumentoTipo.DESPACHO_FIN_REFORCO, pr);
        });
    }

    //@TODO Devera receber como parametro DespachoFinReforcoDto
    @Override
    public void solicitar(PedidoReforcoDto despacho) {

        ProjetoRepository projetoRepository = getRepositoryFace().getProjetoRepository();
        Optional<Projeto> projetoOptional = projetoRepository.findByIdOptional(despacho.getProjetoId());
        projetoOptional.ifPresent(projeto -> {

            Projeto.ProjetoEstado estadoAnterior = projeto.getProjEstado();

            projeto.setProjEstado(Projeto.ProjetoEstado.DESPACHO_REFORCO);
            projetoRepository.persist(projeto);

            PedidoReforco pr = new PedidoReforco();
            pr.setMontanteSolicitado(despacho.getMontanteReforco());
            pr.setDescricao(despacho.getDescricao());

            ///doc detalhe nao há
            saveDocAndHistorico(projeto, estadoAnterior, SGPFStateMachine.EVENT_REFORCO, DocumentoTipo.PEDIDO_REFORCO, pr);

        });
    }

    @Override
    protected void saveDocDetalhe(DocumentoCabecalho doc, Object detalheDoc) {

        if (detalheDoc != null && detalheDoc instanceof PedidoReforco) {

            PedidoReforco data = (PedidoReforco) detalheDoc;
            data.setDocumento(doc);
            getRepositoryFace().getPedidoReforcoRepository().persist(data);

        } else if (detalheDoc != null && detalheDoc instanceof DespachoFinReforco) {

            DespachoFinReforco data = (DespachoFinReforco) detalheDoc;
            data.setDocumento(doc);
            getRepositoryFace().getDespachoFinReforcoRepository().persist(data);
        }
    }

}
