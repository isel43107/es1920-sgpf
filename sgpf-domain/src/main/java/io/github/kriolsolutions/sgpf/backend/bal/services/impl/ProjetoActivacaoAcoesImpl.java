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

import io.github.kriolsolutions.sgpf.backend.bal.services.api.ProjetoActivacaoAcoes;
import io.github.kriolsolutions.sgpf.backend.dal.entidades.projeto.Projeto;
import io.github.kriolsolutions.sgpf.backend.dal.repo.ProjetoRepository;
import io.github.kriolsolutions.sgpf.backend.dal.repo.SgpfRepositoryFacade;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;

/**
 *
 * @author pauloborges
 */
@Dependent
public class ProjetoActivacaoAcoesImpl implements ProjetoActivacaoAcoes{

    @Inject 
    private SgpfRepositoryFacade repositoryFace;
    
    @Override
    public void suspender(Projeto projeto) {
        projeto.setProjEstado(Projeto.ProjetoEstado.PROJETO_SUSPENSO);
        saveProjeto(projeto);
    }

    @Override
    public void reenquadrar(Projeto projeto) {
        projeto.setProjEstado(Projeto.ProjetoEstado.EM_CANDIDATURA);
        saveProjeto(projeto);
    }
    
    private void saveProjeto(Projeto projeto){
        
        ProjetoRepository projetoRepository = repositoryFace.getProjetoRepository();
        projetoRepository.persist(projeto);
    }

    @Override
    public void reativar(Projeto projeto) {
        //@TODO Reativar Falta retornar o projeto ao estado anterior
        projeto.setProjEstado(Projeto.ProjetoEstado.PROJETO_FECHADO);
        saveProjeto(projeto);
    }
    
}
