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
package io.github.kriolsolutions.sgpf.backend.dal.entidades.projeto;

import io.github.kriolsolutions.sgpf.backend.dal.entidades.BaseEntity;
import io.github.kriolsolutions.sgpf.backend.dal.entidades.projeto.Projeto.ProjetoEstado;
import io.github.kriolsolutions.sgpf.backend.dal.entidades.docs.Documento;
import java.time.LocalDate;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
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
//@AllArgsConstructor
@Table(name = "historico", schema="projeto")
@Entity
public class Historico extends BaseEntity {

    private static final long serialVersionUID = 1L;
    
    @ManyToOne
    @JoinColumn(name = "fk_projeto", nullable = false)
    private Projeto projeto;
    
    //Para encontrar mostrar o projeto e nao usar eager loader
    @Column(name = "proj_numero", nullable = false)
    private String projNumero;

    @Column(name = "descricao", nullable = true)
    private String descricao;
    
    @Column(name = "stm_estado_anterior", nullable = true)
    private ProjetoEstado estadoAnterior;
    
    @Column(name = "stm_estado_atual", nullable = false)
    private ProjetoEstado estadoAtual;

    @Column(name = "stm_evento", nullable = false)
    private String evento; //TODO devera ser alterado para "enum SgpfEvent"
    
    /**
     * Chave estrangeira (Uidirecional) header do documento
     * TODO MAY BETTER ser provavelmente do tipo OneToOne(MUST: Documento Imutavel)
     */
    @ManyToOne
    @JoinColumn(name = "fk_documento", nullable = true)
    private Documento documento; 
}
