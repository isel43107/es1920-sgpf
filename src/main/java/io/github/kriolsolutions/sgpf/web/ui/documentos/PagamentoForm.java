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
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import io.github.kriolsolutions.sgpf.backend.bal.dto.PagamentoDto;
import io.github.kriolsolutions.sgpf.backend.bal.services.api.PagamentoAcoes;
import io.github.kriolsolutions.sgpf.backend.dal.entidades.projeto.Projeto;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pauloborges
 */
public class PagamentoForm extends AbstractDespachoForm {

    private final BeanValidationBinder<PagamentoDto> binder = new BeanValidationBinder<>(PagamentoDto.class);

    private NumberField montantePagamento = new NumberField();
    Button efectuarButton = new Button("Efectuar Pagamento");
    private final PagamentoAcoes pagamentoAccoes;
    private final PagamentoDto despacho = new PagamentoDto();

    public PagamentoForm(PagamentoAcoes despachoAccoes, Projeto projeto) {

        this.pagamentoAccoes = despachoAccoes;
        this.despacho.setProjetoId(projeto.getId());
        setupFields();
    }

    @Override
    protected void setupFields() {
        montantePagamento.setLabel("Montante a ser pago");
        this.add(montantePagamento);
        binder.forMemberField(montantePagamento);

        binder.bindInstanceFields(this);

        efectuarButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        efectuarButton.addClickListener((event) -> {
            try {
                binder.writeBean(despacho);
                pagamentoAccoes.efectuarPagamento(despacho);
                AlertUtils.sucess("Submetido com Sucesso").open();
            } catch (ValidationException ex) {
                Logger.getLogger(PagamentoForm.class.getName()).log(Level.SEVERE, null, ex);
                handleException(ex);
            }
        });
        getActions().add(efectuarButton);
    }
}
