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
import io.github.kriolsolutions.sgpf.backend.bal.services.api.AceitacaoCandidaturaAcoes;
import io.github.kriolsolutions.sgpf.backend.bal.services.api.ParecerTecnicoAcoes;
import io.github.kriolsolutions.sgpf.backend.dal.entidades.projeto.Historico;
import io.github.kriolsolutions.sgpf.backend.dal.entidades.projeto.Projeto;
import io.github.kriolsolutions.sgpf.backend.dal.entidades.docs.Candidatura;
import io.github.kriolsolutions.sgpf.backend.dal.entidades.docs.DocumentoCabecalho;
import io.github.kriolsolutions.sgpf.backend.dal.entidades.docs.DocumentoCabecalho.DocumentoTipo;
import io.github.kriolsolutions.sgpf.backend.dal.repo.CandidaturaRepository;
import io.github.kriolsolutions.sgpf.backend.dal.repo.DocumentoRepository;
import io.github.kriolsolutions.sgpf.backend.dal.repo.HistoricoRepository;
import io.github.kriolsolutions.sgpf.backend.dal.repo.ProjetoRepository;
import io.github.kriolsolutions.sgpf.backend.dal.repo.SgpfRepositoryFacade;
import io.github.kriolsolutions.sgpf.backend.scxml.SGPFStateMachine;
import java.util.Date;
import javax.inject.Inject;

/**
 *
 * @author pauloborges
 */
public class ParecerTecnicoAccoesImpl implements ParecerTecnicoAcoes{
    
    @Inject 
    private SgpfRepositoryFacade repositoryFace;

    @Override
    public void favoravel(ParecerTecnicoDto parecer) {
        System.out.println("io.github.kriolsolutions.sgpf.backend.bal.services.impl.ParecerTecnicoAccoesImpl.favoravel()");
    }

    @Override
    public void desfavoravel(ParecerTecnicoDto parecer) {
        System.out.println("io.github.kriolsolutions.sgpf.backend.bal.services.impl.ParecerTecnicoAccoesImpl.desfavoravel()");
    }
}
