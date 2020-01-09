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

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import io.github.kriolsolutions.sgpf.backend.bal.services.api.ParecerTecnicoAcoes;
import io.github.kriolsolutions.sgpf.backend.dal.entidades.projeto.Projeto;

/**
 *
 * @author pauloborges
 */
public class ParecerTecnicoForm extends FormLayout {
    private final BeanValidationBinder<Projeto> binder = new BeanValidationBinder<>(Projeto.class);

    Button favoravelButton = new Button("Favoravel");
    Button desfavoravelButton = new Button("Desfavoravel");
    private NumberField projMontanteSolicitado = new NumberField();
    private Projeto projecto;
    private ParecerTecnicoAcoes parecerAccoes;

    public ParecerTecnicoForm ( ParecerTecnicoAcoes parecerAccoes  , Projeto projecto ) {
        this.projecto = projecto ; 
        this.parecerAccoes = parecerAccoes; 
    }

    public Button getFavoravelButton() {
        return favoravelButton;
    }

    public Button getDesfavoravelButton() {
        return desfavoravelButton;
    }
    
    public ParecerTecnicoForm( ){
    
        init();
        buildActionsButtons();
    }

    private void init() {
        
        this.setResponsiveSteps(
                new FormLayout.ResponsiveStep("25em", 1),
                new FormLayout.ResponsiveStep("32em", 2),
                new FormLayout.ResponsiveStep("40em", 3));
        
        projMontanteSolicitado.setLabel("Montante Solicitado");
        binder.bindInstanceFields(this);
        binder.readBean(this.projecto);
        
        
    }
    
    
    public Binder<Projeto> getBinder() {
        return binder;
    }
    
    private void buildActionsButtons(){
        /* */
        // Button bar
        favoravelButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        favoravelButton.addClickListener((event) -> {
            Projeto proj = getBinder().getBean();
            this.parecerAccoes.favoravel(proj);
        });
        desfavoravelButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        desfavoravelButton.addClickListener((event) -> {
            Projeto proj = getBinder().getBean();
            this.parecerAccoes.desfavoravel(proj);
        });
        
        HorizontalLayout actions = new HorizontalLayout();
        actions.add(favoravelButton, desfavoravelButton);
        actions.getStyle().set("marginRight", "10px");
        //this.add(actions);
    }
}
