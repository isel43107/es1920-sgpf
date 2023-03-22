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
import io.github.kriolsolutions.sgpf.backend.bal.dto.DespachoFinIncentivoDto;
import io.github.kriolsolutions.sgpf.backend.bal.services.api.DespachoFinanciamentoIncentivoAcoes;
import io.github.kriolsolutions.sgpf.backend.dal.entidades.docs.Despacho;
import io.github.kriolsolutions.sgpf.backend.dal.entidades.docs.DespachoFinIncentivo;
import io.github.kriolsolutions.sgpf.backend.dal.entidades.docs.DocumentoCabecalho;
import io.github.kriolsolutions.sgpf.backend.dal.entidades.projeto.Historico;
import io.github.kriolsolutions.sgpf.backend.dal.entidades.projeto.Projeto;
import io.github.kriolsolutions.sgpf.backend.dal.entidades.projeto.Projeto.ProjetoEstado;
import io.github.kriolsolutions.sgpf.backend.dal.repo.DespachoFinIncentivoRepository;
import io.github.kriolsolutions.sgpf.backend.dal.repo.DocumentoRepository;
import io.github.kriolsolutions.sgpf.backend.dal.repo.HistoricoRepository;
import io.github.kriolsolutions.sgpf.backend.dal.repo.ProjetoRepository;
import io.github.kriolsolutions.sgpf.backend.dal.repo.SgpfRepositoryFacade;
import io.github.kriolsolutions.sgpf.backend.scxml.SGPFStateMachine;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;
import javax.inject.Inject;

/**
 *
 * @author pauloborges
 */
public class DespachoFinanciamentoIncentivoAcoesImpl extends AbstractDocumentoAndHistoryPersist<DespachoFinIncentivo> implements DespachoFinanciamentoIncentivoAcoes {

    @Inject
    public DespachoFinanciamentoIncentivoAcoesImpl(SgpfRepositoryFacade repositoryFace) {
        super(repositoryFace);
    }

    @Override
    public void transformarBonificacao(DespachoFinIncentivoDto despacho) {
        
        DespachoFinIncentivoDto desDto = (DespachoFinIncentivoDto)despacho;
        ProjetoRepository projetoRepository = getRepositoryFace().getProjetoRepository();

        Optional<Projeto> projetoOptional = projetoRepository.findOptionalBy(despacho.getProjetoId());

        projetoOptional.ifPresent(projeto -> {
            projeto.setProjTipo(Projeto.ProjetoTipo.BONIFICAO);
            projeto.setProjEstado(Projeto.ProjetoEstado.DESPACHO_FIN_BONIFICACAO);
            projetoRepository.saveAndFlush(projeto);
        });
    }

    @Override
    public void aprovar(AbstractDespachoFinDto despacho) {
        //TODO verficar se é instancia if(despacho instanceOf DespachoFinIncentivoDto) ou deixar rebentar ?
        DespachoFinIncentivoDto desDto = (DespachoFinIncentivoDto)despacho;
        ProjetoRepository projetoRepository = getRepositoryFace().getProjetoRepository();

        Optional<Projeto> projetoOptional = projetoRepository.findOptionalBy(despacho.getProjetoId());

        projetoOptional.ifPresent(projeto -> {
            ProjetoEstado estadoAnterior = projeto.getProjEstado();
            projeto.setProjEstado(Projeto.ProjetoEstado.EM_PAGAMENTO);
            projetoRepository.saveAndFlush(projeto);
            
            DespachoFinIncentivo desDetalhe = buildDespachoDetalhe(desDto);
            desDetalhe.setDecisao(Despacho.DespachoDecisao.APROVADO);
            DocumentoCabecalho.DocumentoTipo docTipo = DocumentoCabecalho.DocumentoTipo.DESPACHO_FIN_INCENTIVO;
            saveDocAndHistorico(projeto, estadoAnterior, SGPFStateMachine.EVENT_APROVADO, docTipo, desDetalhe);
        });
    }

    @Override
    public void rejeitar(AbstractDespachoFinDto despacho) {
        DespachoFinIncentivoDto desDto = (DespachoFinIncentivoDto)despacho;

        ProjetoRepository projetoRepository = getRepositoryFace().getProjetoRepository();

        Optional<Projeto> projetoOptional = projetoRepository.findOptionalBy(despacho.getProjetoId());

        projetoOptional.ifPresent(projeto -> {
            
            ProjetoEstado estadoAnterior = projeto.getProjEstado();
            
            projeto.setProjEstado(Projeto.ProjetoEstado.PROJETO_REJEITADO);
            projetoRepository.saveAndFlush(projeto);
            
            DespachoFinIncentivo desDetalhe = buildDespachoDetalhe(desDto);
            desDetalhe.setDecisao(Despacho.DespachoDecisao.REJEITADO);
            
            DocumentoCabecalho.DocumentoTipo docTipo = DocumentoCabecalho.DocumentoTipo.DESPACHO_FIN_INCENTIVO;
            saveDocAndHistorico(projeto, estadoAnterior, SGPFStateMachine.EVENT_REJEITADO, docTipo, desDetalhe);
        });
    }

    private DespachoFinIncentivo buildDespachoDetalhe(DespachoFinIncentivoDto desDto) {

        DespachoFinIncentivo desDetalhe = new DespachoFinIncentivo();
        desDetalhe.setCustoElegivel(desDto.getCustoElegivel());
        desDetalhe.setMontanteFinanciado(desDto.getMontanteFinanciado());
        desDetalhe.setPrazoExecucao(convertToDate(desDto.getPrazoExecucao()));

        return desDetalhe;
    }
    
    //TODO move to util class. common.utils
    private static Date convertToDate(LocalDate dateToConvert) {
        return java.sql.Date.valueOf(dateToConvert);
    }

    @Override
    protected void saveDocDetalhe(DocumentoCabecalho doc, DespachoFinIncentivo detalheDoc) {
        DespachoFinIncentivoRepository desDetalheRepo = getRepositoryFace().getDespachoFinIncentivoRepository();
        desDetalheRepo.save(detalheDoc);
    }

}
