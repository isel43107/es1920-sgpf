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
package io.github.kriolsolutions.sgpf.backend.dal.entidades.docs;

import io.github.kriolsolutions.sgpf.backend.dal.entidades.BaseEntity;
import io.github.kriolsolutions.sgpf.backend.dal.entidades.projeto.Historico;
import io.github.kriolsolutions.sgpf.backend.dal.entidades.projeto.Projeto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *
 * @author pauloborges
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Table(name = "documento", schema="documentos")
@Entity
public class DocumentoCabecalho extends BaseEntity{
    
    @ManyToOne
    @JoinColumn(name = "fk_projeto")
    private Projeto projeto;
    
    @ManyToOne
    @JoinColumn(name = "fk_historico", nullable = true)
    private Historico historico; 
    
    @Column(name = "doc_tipo")
    @Enumerated(EnumType.STRING)
    private DocumentoTipo docTipo;

    public static enum DocumentoTipo {
        
        CANDIDATURA,
        PARECER_TECNICO,
        DESPACHO_ABERTURA,
        DESPACHO_FIN_BONIFICACAO,
        DESPACHO_FIN_INCENTIVO,
        DESPACHO_FIN_REFORCO,
        PEDIDO_REFORCO,
        PAGAMENTO,
    }
}
