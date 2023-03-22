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
package io.github.kriolsolutions.sgpf.backend.dal.repo;

import javax.inject.Inject;

/**
 *
 * @author pauloborges
 */
public class SgpfRepositoryFacade {
 
    @Inject
    private ProjetoRepository projetoRepository;
    
    @Inject
    private DocumentoRepository documentoRepository;
    
    @Inject
    private CandidaturaRepository candidaturaRepository;
    
    @Inject
    private HistoricoRepository historicoRepository;
    
    @Inject
    private DespachoFinBonificacaoRepository despachoFinBonificacaoRepository;
    
    @Inject
    private DespachoFinIncentivoRepository despachoFinIncentivoRepository;
    
    @Inject
    private DespachoFinReforcoRepository despachoFinReforcoRepository;
    
    @Inject
    private DespachoAberturaRepository despachoAberturaRepository;
    
    @Inject
    private PedidoReforcoRepository pedidoReforcoRepository;
    
    @Inject
    private ParecerTecnicoRepository parecerTecnicoRepository;
    
    @Inject
    private PagamentoRepository pagamentoRepository;

    public ProjetoRepository getProjetoRepository() {
        return projetoRepository;
    }

    public DocumentoRepository getDocumentoRepository() {
        return documentoRepository;
    }

    public CandidaturaRepository getCandidaturaRepository() {
        return candidaturaRepository;
    }

    public DespachoFinBonificacaoRepository getDespachoFinBonificacaoRepository() {
        return despachoFinBonificacaoRepository;
    }

    public DespachoFinIncentivoRepository getDespachoFinIncentivoRepository() {
        return despachoFinIncentivoRepository;
    }

    public DespachoFinReforcoRepository getDespachoFinReforcoRepository() {
        return despachoFinReforcoRepository;
    }

    public HistoricoRepository getHistoricoRepository() {
        return historicoRepository;
    }

    public DespachoAberturaRepository getDespachoAberturaRepository() {
        return despachoAberturaRepository;
    }

    public PedidoReforcoRepository getPedidoReforcoRepository() {
        return pedidoReforcoRepository;
    }

    public ParecerTecnicoRepository getParecerTecnicoRepository() {
        return parecerTecnicoRepository;
    }

    public PagamentoRepository getPagamentoRepository() {
        return pagamentoRepository;
    }
    
    
}
