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
package io.github.kriolsolutions.sgpf.web.ui.projeto;

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import io.github.kriolsolutions.sgpf.backend.dal.entidades.Projeto;

/**
 *
 * @author pauloborges
 */
public abstract class AbstractProjetoForm extends FormLayout {

    private final Binder<Projeto> binder = new Binder<>(Projeto.class);
    
    // FIELD TRANSLATION - SHOULD GO TO resource files, but now put here 
    // NOT WORK YET: getTranslation("AbstractProjetoForm.projDesignacao.label")
    private final static String TX_PROJ_DESIGNACAO = "";
    private final static String TX_PROJ_MNT_SOLICITADO = "";
    private final static String TX_PROJ_NIB = "";
    private final static String TX_PROJ_DESCRICAO = "";
    
    private final static String TX_PROMOTOR_DESIGNACAO = "";
    private final static String TX_PROMOTOR_NIF = "";
    private final static String TX_PROMOTOR_NACIONALIDADE = "";
    
    private final static String TX_CONTATO_NOME = "";
    private final static String TX_CONTATO_TEL = "";
    private final static String TX_CONTATO_EMAIL = "";
    

    //Campos relativo a informação projeto
    TextField projDesignacao = new TextField();
    NumberField projMontanteSolicitado = new NumberField();
    TextField projNIB = new TextField();
    TextField projDescricao = new TextField();

    //Campos - PROMOTOR
    TextField promotorDesignacao = new TextField();
    TextField promotorNIF = new TextField();
    TextField promotorNacionalidade = new TextField();

    //Campos - CONTACTO
    TextField contatoNome = new TextField();
    TextField contatoTelefone = new TextField();
    TextField contatoEmail = new TextField();

    public AbstractProjetoForm() {
        init();
    }

    private void init() {

        RadioButtonGroup<Projeto.ProjetoTipo> projTipo = new RadioButtonGroup<>();
        //projTipo.setRenderer(new TextRenderer<>(Projeto.ProjetoTipo::));
        projTipo.setItems(Projeto.ProjetoTipo.values());

        // Setting the desired responsive steps for the columns in the layout
        this.setResponsiveSteps(
                new FormLayout.ResponsiveStep("25em", 1),
                new FormLayout.ResponsiveStep("32em", 2),
                new FormLayout.ResponsiveStep("40em", 3));
        //
        projDesignacao.setLabel("Designação do projeto");
        projDesignacao.setPlaceholder("Designação do projeto");
        projMontanteSolicitado.setLabel("Montante solicitado");
        projMontanteSolicitado.setPlaceholder("Montante solicitado");
        projNIB.setLabel("NIB");
        projNIB.setPlaceholder("NIB");
        projDescricao.setLabel("Descrição do projeto");
        projDescricao.setPlaceholder("Descrição do projeto");

        //Campos - 
        promotorDesignacao.setLabel("Designação do promotor");
        promotorDesignacao.setPlaceholder("Designação do promotor");
        promotorNIF.setLabel("NIF do promotor");
        promotorNIF.setPlaceholder("NIF do promotor");
        promotorNacionalidade.setLabel("Nacionalidade do promotor");
        promotorNacionalidade.setPlaceholder("Nacionalidade do promotor");

        //Campos - CONTACTO
        contatoNome.setLabel("Nome do contato");
        contatoNome.setPlaceholder("Nome do contato");
        contatoTelefone.setLabel("Telefone do contato");
        contatoTelefone.setPlaceholder("Telefone do contato");
        contatoEmail.setLabel("E-mail do contato");
        contatoEmail.setPlaceholder("E-mail do contato");

        this.add(projDesignacao, projMontanteSolicitado, projNIB, projDescricao);
        this.setColspan(projDescricao, 3);

        this.add(promotorDesignacao, promotorNIF, promotorNacionalidade);
        this.add(contatoNome, contatoTelefone, contatoEmail);

        binder.bindInstanceFields(this);
    }

    public Binder<Projeto> getBinder() {
        return binder;
    }
}