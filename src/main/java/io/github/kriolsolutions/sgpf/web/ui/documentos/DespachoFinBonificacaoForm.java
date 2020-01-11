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
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import io.github.kriolsolutions.sgpf.backend.bal.dto.DespachoFinBonificacaoDto;
import io.github.kriolsolutions.sgpf.backend.bal.services.api.DespachoFinanciamentoBonificacaoAcoes;
import io.github.kriolsolutions.sgpf.backend.dal.entidades.projeto.Projeto;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pauloborges
 * Montante máximo de bonificação
 * Taxa de bonificação
 * Período
 */
public class DespachoFinBonificacaoForm extends AbstractDespachoForm {
    
    private final BeanValidationBinder<DespachoFinBonificacaoDto> binder = new BeanValidationBinder<>(DespachoFinBonificacaoDto.class);

    private NumberField montanteFinanciado = new NumberField();
    private NumberField custoElegivel = new NumberField();
    private NumberField taxBonificacao = new NumberField();
    private NumberField mntMaxBonificacao = new NumberField();
    private IntegerField periodo = new IntegerField();
    
    private Button aceitarButton = new Button("Aprovar");
    private Button arquivarButton = new Button("Arquivar");
    
    private final Projeto projeto;
    private final DespachoFinanciamentoBonificacaoAcoes bonificacaoAccoes;
    
    private final DespachoFinBonificacaoDto despacho = new DespachoFinBonificacaoDto();
    
    public DespachoFinBonificacaoForm(DespachoFinanciamentoBonificacaoAcoes accoes, Projeto projeto ){
        this.projeto = projeto;
        this.bonificacaoAccoes = accoes;
        this.despacho.setProjetoId(projeto.getId());
        setupFields();
    }


    @Override
    protected void setupFields() {
        montanteFinanciado.setLabel("Montante de Financiamento");
        custoElegivel.setLabel("Custo Elegivel");
        periodo.setLabel("Periodo");
        mntMaxBonificacao.setLabel("Montante Maximo Bonificacao");
        taxBonificacao.setLabel("Taxa de Bonificação");
        
        binder.forMemberField(montanteFinanciado);        
        binder.forMemberField(custoElegivel);
        binder.forMemberField(periodo);
        binder.forMemberField(mntMaxBonificacao);
        binder.forMemberField(taxBonificacao);
        
        this.add(montanteFinanciado,custoElegivel, periodo,mntMaxBonificacao,taxBonificacao);
        binder.bindInstanceFields(this);
        
        aceitarButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        aceitarButton.addClickListener((event) -> {
            try {
                binder.writeBean(despacho);
                this.bonificacaoAccoes.aprovar(despacho);
            } catch (ValidationException ex) {
                Logger.getLogger(DespachoFinBonificacaoForm.class.getName()).log(Level.SEVERE, null, ex);
                handleException(ex);
            }
            
        });
        
        arquivarButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        arquivarButton.addClickListener((event) -> {
            try {
                binder.writeBean(despacho);
                this.bonificacaoAccoes.rejeitar(despacho);
            } catch (ValidationException ex) {
                Logger.getLogger(DespachoFinBonificacaoForm.class.getName()).log(Level.SEVERE, null, ex);
                handleException(ex);
            }
        });
        
        this.getActions().add(aceitarButton, arquivarButton);
    }
    
}
