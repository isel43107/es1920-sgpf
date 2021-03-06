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

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import io.github.kriolsolutions.sgpf.backend.bal.dto.DespachoAberturaDto;
import io.github.kriolsolutions.sgpf.backend.bal.services.api.DespachoAberturaAcoes;
import io.github.kriolsolutions.sgpf.backend.dal.entidades.projeto.Projeto;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pauloborges
 */
public abstract class AbstractDespachoForm extends FormLayout {

    Button fecharButton = new Button("Fechar");

    public Button getFecharButton() {
        return fecharButton;
    }

    private HorizontalLayout actions = new HorizontalLayout();

    protected HorizontalLayout getActions() {
        return actions;
    }

    public AbstractDespachoForm() {
        fecharButton();
    }

    protected abstract void setupFields();

    private void fecharButton() {
        fecharButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        actions.add(fecharButton);
        actions.getStyle().set("marginRight", "10px");
        this.add(actions);
    }

    protected void handleException(ValidationException exception) {
        AlertUtils.danger("ERROR! Não foi possivel executar a ação.").open();

    }

}
