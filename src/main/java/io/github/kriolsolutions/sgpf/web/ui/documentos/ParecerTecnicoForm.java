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
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import io.github.kriolsolutions.sgpf.backend.bal.dto.ParecerTecnicoDto;
import io.github.kriolsolutions.sgpf.backend.bal.services.api.ParecerTecnicoAcoes;
import io.github.kriolsolutions.sgpf.backend.dal.entidades.projeto.Projeto;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pauloborges
 */
public class ParecerTecnicoForm extends AbstractDespachoForm {
    private final BeanValidationBinder<ParecerTecnicoDto> binder = new BeanValidationBinder<>(ParecerTecnicoDto.class);

    Button favoravelButton = new Button("Favoravel");
    Button desfavoravelButton = new Button("Desfavoravel");
    private TextField texto = new TextField();
    private Projeto projecto;
    private ParecerTecnicoAcoes parecerAccoes;
    private ParecerTecnicoDto parecer = new ParecerTecnicoDto();

    public ParecerTecnicoForm ( ParecerTecnicoAcoes parecerAccoes  , Projeto projecto ) {
        this.projecto = projecto ; 
        this.parecerAccoes = parecerAccoes; 
        this.parecer.setProjetoId(projecto.getId());
        setupFields();
    }
    
    public ParecerTecnicoForm( ){
        setupFields();
    }
    @Override
    protected void setupFields() {
        
        texto.setLabel("Texto");
        
        binder.forMemberField(texto);
        binder.bindInstanceFields(this);
        this.add(texto);
        
        favoravelButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        favoravelButton.addClickListener((event) -> {
            try {
                binder.writeBean(parecer);
                this.parecerAccoes.favoravel(parecer);
            } catch (ValidationException ex) {
                Logger.getLogger(ParecerTecnicoForm.class.getName()).log(Level.SEVERE, null, ex);
                handleException(ex);
            }
        });
        desfavoravelButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        desfavoravelButton.addClickListener((event) -> {
            try {
                binder.writeBean(parecer);
                this.parecerAccoes.desfavoravel(parecer);
            } catch (ValidationException ex) {
                Logger.getLogger(ParecerTecnicoForm.class.getName()).log(Level.SEVERE, null, ex);
                handleException(ex);
            }
        });
        
        getActions().add(favoravelButton, desfavoravelButton);
    }
}
