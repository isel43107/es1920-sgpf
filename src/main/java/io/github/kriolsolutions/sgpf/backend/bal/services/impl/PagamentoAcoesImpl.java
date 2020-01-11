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
import io.github.kriolsolutions.sgpf.backend.bal.dto.ParecerTecnicoDto;
import io.github.kriolsolutions.sgpf.backend.bal.services.api.PagamentoAcoes;
import io.github.kriolsolutions.sgpf.backend.bal.services.api.ParecerTecnicoAcoes;
import io.github.kriolsolutions.sgpf.backend.dal.entidades.projeto.Projeto;
import io.github.kriolsolutions.sgpf.backend.dal.repo.ProjetoRepository;
import io.github.kriolsolutions.sgpf.backend.dal.repo.SgpfRepositoryFacade;
import java.util.Optional;
import javax.inject.Inject;

/**
 *
 * @author pauloborges
 */
public class PagamentoAcoesImpl implements PagamentoAcoes{
    
    @Inject 
    private SgpfRepositoryFacade repositoryFace;

    @Override
    public void efectuarPagamento(PagamentoDto pedido) {
        //TODO verificar se max e prazo foi atingido
        ProjetoRepository projetoRepository = repositoryFace.getProjetoRepository();
        Optional<Projeto> projetoOptional = projetoRepository.findOptionalBy(pedido.getProjetoId());
        projetoOptional.ifPresent( projeto -> {
            projetoRepository.saveAndFlush(projeto);
        });
        
        System.out.println("io.github.kriolsolutions.sgpf.backend.bal.services.impl.PagamentoAcoesImpl.efectuarPagamento()");
    }
}
