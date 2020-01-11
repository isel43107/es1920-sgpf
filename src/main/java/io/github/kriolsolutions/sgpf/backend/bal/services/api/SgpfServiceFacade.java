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
package io.github.kriolsolutions.sgpf.backend.bal.services.api;

import javax.inject.Inject;

/**
 *
 * @author pauloborges
 */
public class SgpfServiceFacade {

    @Inject
    private AceitacaoCandidaturaAcoes aceitacaoCandidaturaAcoes;

    @Inject
    private DespachoAberturaAcoes despachoAberturaAcoes;

    @Inject
    private DespachoFinanciamentoBonificacaoAcoes despachoBonificacaoAcoes;

    @Inject
    private DespachoFinanciamentoIncentivoAcoes despachoFinanciamentoIncentivoAcoes;

    @Inject
    private DespachoFinanciamentoReforcoAcoes despachoFinanciamentoReforcoAcoes;

    @Inject
    private ProjetoActivacaoAcoes projetoAcoes;

    @Inject
    private ParecerTecnicoAcoes parecerTecnicoAcoes;

    @Inject
    private PagamentoAcoes pagamentoAcoes;

    public PagamentoAcoes getPagamentoAcoes() {
        return pagamentoAcoes;
    }

    public AceitacaoCandidaturaAcoes getAceitacaoCandidaturaAcoes() {
        return aceitacaoCandidaturaAcoes;
    }

    public DespachoAberturaAcoes getDespachoAberturaAcoes() {
        return despachoAberturaAcoes;
    }

    public DespachoFinanciamentoBonificacaoAcoes getDespachoBonificacaoAcoes() {
        return despachoBonificacaoAcoes;
    }

    public DespachoFinanciamentoIncentivoAcoes getDespachoFinanciamentoIncentivoAcoes() {
        return despachoFinanciamentoIncentivoAcoes;
    }

    public DespachoFinanciamentoReforcoAcoes getDespachoFinanciamentoReforcoAcoes() {
        return despachoFinanciamentoReforcoAcoes;
    }

    public ProjetoActivacaoAcoes getProjetoAcoes() {
        return projetoAcoes;
    }

    public ParecerTecnicoAcoes getParecerTecnicoAcoes() {
        return parecerTecnicoAcoes;
    }

}
