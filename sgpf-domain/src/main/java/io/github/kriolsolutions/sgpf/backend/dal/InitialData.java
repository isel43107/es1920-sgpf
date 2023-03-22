/*
 * Copyright 2019 kriolSolutions.
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
package io.github.kriolsolutions.sgpf.backend.dal;

import io.github.kriolsolutions.sgpf.backend.dal.repo.ProjetoRepository;
import io.github.kriolsolutions.sgpf.backend.dal.entidades.projeto.Projeto;
import io.github.kriolsolutions.sgpf.backend.dal.entidades.projeto.Projeto.ProjetoTipo;
import java.util.Arrays;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Destroyed;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

/**
 *
 * @author pauloborges
 */
@ApplicationScoped
public class InitialData {

    private static final Logger LOGGER = Logger.getLogger(InitialData.class.getName());

    //@Inject
    //private ProjetoRepository projetoRepository;
    
    private volatile Object context;

    @PostConstruct
    void init() {
        LOGGER.info("PostConstruct");
    }

    /*
    public void init(@Observes @Initialized(ApplicationScoped.class) Object context) {

        if (this.context != null) {
            throw new IllegalStateException("PreBoot context started twice");
        }
        
        print();
        
        this.context = context;

        LOGGER.info("app is deployed");
    }
    */

    public void tearDown(@Observes @Destroyed(ApplicationScoped.class) Object init) {
        // when app is undeployed
        LOGGER.info("app is undeployed");
    }
    
    private void print(){
    
        System.out.println(
                  "████████╗██╗  ██╗███████╗    ███╗   ███╗██╗██╗  ██╗\n"
                + "╚══██╔══╝██║  ██║██╔════╝    ████╗ ████║██║╚██╗██╔╝\n"
                + "   ██║   ███████║█████╗      ██╔████╔██║██║ ╚███╔╝ \n"
                + "   ██║   ██╔══██║██╔══╝      ██║╚██╔╝██║██║ ██╔██╗ \n"
                + "   ██║   ██║  ██║███████╗    ██║ ╚═╝ ██║██║██╔╝ ██╗\n"
                + "   ╚═╝   ╚═╝  ╚═╝╚══════╝    ╚═╝     ╚═╝╚═╝╚═╝  ╚═╝");
    }
    
    public void initData() {
        /*
        Arrays.asList(new Projeto(0L, "ISEL", "Portugal", "1111", "nib", "Mateus", "l@isel", "1234", 1000.00, ProjetoTipo.BONIFICAO),
                new Projeto(1L, "ISEL", "Portugal", "1111", "nib", "Ferreira", "ferreira@isel", "1235", 2000.00, ProjetoTipo.BONIFICAO),
                new Projeto(2L, "ISEL", "Portugal", "1111", "nib", "Mendes", "mendes@isel", "1236", 3000.00, ProjetoTipo.BONIFICAO))
                .stream()
                .forEach(event -> {
                    //this.projetoRepository.save(event);
                });
*/
    }
}
