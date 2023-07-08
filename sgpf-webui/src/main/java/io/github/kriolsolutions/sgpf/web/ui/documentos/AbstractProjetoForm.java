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

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.EmailValidator;
import io.github.kriolsolutions.sgpf.backend.dal.entidades.projeto.Projeto;

/**
 *
 * @author pauloborges
 */
public abstract class AbstractProjetoForm extends FormLayout {

    private final BeanValidationBinder<Projeto> binder = new BeanValidationBinder<>(Projeto.class);

    //Campos relativo a informação projeto
    private TextField projDesignacao = new TextField();
    private NumberField projMontanteSolicitado = new NumberField();
    private TextField projNIB = new TextField();
    private TextArea projDescricao = new TextArea();
    //private ComboBox<ProjetoTipo> projTipos = new ComboBox<>();
    private RadioButtonGroup<Projeto.ProjetoTipo> projTipo = new RadioButtonGroup<>();

    //Campos - PROMOTOR
    private TextField promotorDesignacao = new TextField();
    private TextField promotorNIF = new TextField();
    private TextField promotorNacionalidade = new TextField();

    //Campos - CONTACTO
    private TextField contatoNome = new TextField();
    private TextField contatoTelefone = new TextField();
    private TextField contatoEmail = new TextField();

    public AbstractProjetoForm() {
        init();
    }

    private void init() {

        //projTipo.setRenderer(new TextRenderer<>(Projeto.ProjetoTipo::));
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
        projTipo.setLabel("Tipo");
        projTipo.setItems(Projeto.ProjetoTipo.values());

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

        this.add(projDesignacao, projMontanteSolicitado, projNIB, projTipo, projDescricao);
        this.setColspan(projDescricao, 3);

        this.add(promotorDesignacao, promotorNIF, promotorNacionalidade);
        this.add(contatoNome, contatoTelefone, contatoEmail);

        //Customizar menssagens de erro
        //
        binder.forMemberField(projDesignacao).asRequired("Prenchimento obrigatorio");
        binder.forMemberField(projMontanteSolicitado).asRequired("Prenchimento obrigatorio");
        binder.forMemberField(projTipo).asRequired("Prenchimento obrigatorio");
        binder.forMemberField(promotorDesignacao).asRequired("Prenchimento obrigatorio");
        binder.forMemberField(contatoNome).asRequired("Prenchimento obrigatorio");
        binder.forMemberField(contatoEmail)
                .withValidator(new EmailValidator("Formato e-mail invalido"))
                .asRequired("Prenchimento obrigatorio")
                .bind(Projeto::getContatoEmail, Projeto::setContatoEmail);

/*
        binder.forField(projMontanteSolicitado)
                .withValidator(new MontandValidator("Montando deve conter duas casas decimais"))
                .bind(Projeto::getProjMontanteSolicitado, Projeto::setrojMontanteSolicitado);
*/
        
        //Binding automatico baseado, visto que os nomes dos campos são identicos aos do bean
        binder.bindInstanceFields(this);
    }

    public Binder<Projeto> getBinder() {
        return binder;
    }
}
