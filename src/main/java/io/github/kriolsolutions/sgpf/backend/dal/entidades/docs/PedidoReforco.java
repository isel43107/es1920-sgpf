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
package io.github.kriolsolutions.sgpf.backend.dal.entidades.docs;

import io.github.kriolsolutions.sgpf.backend.dal.entidades.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "documento_pedido_reforco", schema="documentos")
@Entity
public class PedidoReforco extends BaseEntity{
    
    
    @Column(name = "mnt_solicitado", nullable = true)
    private double montanteSolicitado;
    
    @Column(name = "descricao", nullable = true)
    private String descricao;
      
    @ManyToOne
    @JoinColumn(name = "fk_documento")
    private DocumentoCabecalho documento;
}
