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

import com.vaadin.cdi.annotation.UIScoped;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.binder.ValidationException;
import io.github.kriolsolutions.sgpf.backend.bal.services.api.AceitacaoCandidaturaAcoes;
import io.github.kriolsolutions.sgpf.backend.dal.entidades.projeto.Projeto;

/**
 *
 * @author pauloborges
 */
public class CandidaturaForm extends AbstractProjetoForm {

    private final AceitacaoCandidaturaAcoes aceitacaoCandidaturaAcoes;

    private final Projeto projeto = new Projeto();
    
    private final Button aceitarButton = new Button("Aceitar");
    private final Button arquivarButton = new Button("Canelar");

    public CandidaturaForm(AceitacaoCandidaturaAcoes aceitacaoCandidaturaAcoes) {
        super();
        this.aceitacaoCandidaturaAcoes = aceitacaoCandidaturaAcoes;
        buildActionsButtons();
    }

    private void buildActionsButtons() {

        //this.getBinder().setBean(projeto);
        /* */
        // Button bar
        
        aceitarButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        aceitarButton.addClickListener((event) -> {

            try {
                this.getBinder().writeBean(projeto);

                if (getBinder().validate().isOk()) {
                    aceitacaoCandidaturaAcoes.aceitar(projeto);
                    Notification.show("Candidatura foi aceite com sucesso");
                }
            } catch (ValidationException e) {
                notifyValidationException(e);
                
            }
        });

        
        arquivarButton.addThemeVariants(ButtonVariant.LUMO_ERROR);

        HorizontalLayout actions = new HorizontalLayout();
        actions.add(aceitarButton, arquivarButton);
        actions.getStyle().set("marginRight", "10px");
        this.add(actions);
    }

    private void notifyValidationException(ValidationException e) {
        Notification.show("Erro ao aceitar candidatura");
    }

    public Button getAceitarButton() {
        return aceitarButton;
    }

    public Button getArquivarButton() {
        return arquivarButton;
    }
    
}
