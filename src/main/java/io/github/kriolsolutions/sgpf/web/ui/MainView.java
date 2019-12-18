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
package io.github.kriolsolutions.sgpf.web.ui;

import io.github.kriolsolutions.sgpf.web.ui.projeto.ProjetoManager;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import io.github.kriolsolutions.sgpf.backend.dal.ProjetoRepository;
import javax.inject.Inject;

/**
 * Main view
 */
@Route("")
@PWA(name = "Sistema de Gestão de Projeto de Financiamento", shortName = "SGPF")
@Theme(value = Lumo.class, variant = Lumo.DARK)
public class MainView extends VerticalLayout {

    private final ProjetoRepository projetoRepository;

    @Inject
    public MainView(ProjetoRepository projetoRepository) {
        if (projetoRepository == null) {
            throw new IllegalArgumentException("Parameter projetoRepository must not be null");
        }
        
        this.projetoRepository = projetoRepository;

        initComponent();

    }

    private void initComponent() {
        add(new ProjetoManager(projetoRepository));
        //add(new ExampleUI(projetoRepository));
    }

}
