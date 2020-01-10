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
import io.github.kriolsolutions.sgpf.backend.dal.entidades.projeto.Projeto;
import io.github.kriolsolutions.sgpf.backend.dal.repo.ProjetoRepository;
import io.github.kriolsolutions.sgpf.backend.dal.repo.SgpfRepositoryFacade;
import java.util.Optional;
import javax.inject.Inject;

/**
 *
 * @author pauloborges
 */
public class DespachoFinanciamentoIncentivoAcoesImpl implements DespachoFinanciamentoIncentivoAcoes {

    @Inject
    private SgpfRepositoryFacade repositoryFace;

    @Override
    public void transformarBonificacao(DespachoFinIncentivoDto despacho) {
        ProjetoRepository projetoRepository = repositoryFace.getProjetoRepository();
        
        Optional<Projeto> projetoOptional = projetoRepository.findOptionalBy(despacho.getProjetoId());
        
        projetoOptional.ifPresent(projeto -> {
            projeto.setProjEstado(Projeto.ProjetoEstado.DESPACHO_FIN_BONIFICACAO);
            projetoRepository.saveAndFlush(projeto);
        });
    }

    @Override
    public void aprovar(AbstractDespachoFinDto despacho) {
        
        ProjetoRepository projetoRepository = repositoryFace.getProjetoRepository();
        
        Optional<Projeto> projetoOptional = projetoRepository.findOptionalBy(despacho.getProjetoId());
        
        projetoOptional.ifPresent(projeto -> {
            projeto.setProjEstado(Projeto.ProjetoEstado.EM_PAGAMENTO);
            projetoRepository.saveAndFlush(projeto);
        });
    }

    @Override
    public void rejeitar(AbstractDespachoFinDto despacho) {
        
        ProjetoRepository projetoRepository = repositoryFace.getProjetoRepository();
        
        Optional<Projeto> projetoOptional = projetoRepository.findOptionalBy(despacho.getProjetoId());
        
        projetoOptional.ifPresent(projeto -> {
            projeto.setProjEstado(Projeto.ProjetoEstado.PROJETO_REJEITADO);
            projetoRepository.saveAndFlush(projeto);
        });
    }

}
