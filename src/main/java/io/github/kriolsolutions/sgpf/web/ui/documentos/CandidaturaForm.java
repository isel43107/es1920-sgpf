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
package io.github.kriolsolutions.sgpf.web.ui.documentos;

import io.github.kriolsolutions.sgpf.web.ui.projeto.AbstractProjetoForm;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import io.github.kriolsolutions.sgpf.backend.dal.entidades.Projeto;

/**
 *
 * @author pauloborges
 */
public class CandidaturaForm extends AbstractProjetoForm {

    public CandidaturaForm() {
        super();
        buildCandidaturaButtons();
    }

    private void buildCandidaturaButtons(){
        /* */
        // Button bar
        Button aceitarButton = new Button("Aceitar");
        aceitarButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        Button arquivarButton = new Button("Arquivar");
        arquivarButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        HorizontalLayout actions = new HorizontalLayout();
        actions.add(aceitarButton, arquivarButton);
        actions.getStyle().set("marginRight", "10px");
        this.add(actions);
    }
}
