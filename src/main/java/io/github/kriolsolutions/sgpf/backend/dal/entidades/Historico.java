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
package io.github.kriolsolutions.sgpf.backend.dal.entidades;

import io.github.kriolsolutions.sgpf.backend.dal.entidades.Projeto.ProjetoEstado;
import io.github.kriolsolutions.sgpf.backend.dal.entidades.docs.Documento;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "historico")
@Entity
public class Historico extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private LocalDate dataOcorrencia;
    private String descricao;
    private ProjetoEstado estado;
    
    /**
     * Relação unidirecional, ou seja, chave estrangeira do documento
     * Um 
     * TODO devera ser provavelmente do tipo OneToOne
     */
    @ManyToOne
    @JoinColumn(name = "fk_documento")
    private Documento documento;
    
    
}
