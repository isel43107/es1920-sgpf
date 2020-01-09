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
package io.github.kriolsolutions.sgpf.web.ui.projeto;

import io.github.kriolsolutions.sgpf.web.ui.documentos.AbstractProjetoForm;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

/**
 *
 * @author pauloborges
 */
public class ProjetoEditorForm extends AbstractProjetoForm {

    public ProjetoEditorForm() {
        super();
    }

    private void buildActionsButtons() {
        // Button bar
        Button aceitarButton = new Button("Guardar");
        aceitarButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        HorizontalLayout actions = new HorizontalLayout();
        actions.add(aceitarButton);
        actions.getStyle().set("marginRight", "10px");
        this.add(actions);
    }
}
