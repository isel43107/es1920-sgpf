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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Documento Detalhe
 * Qualquer documento que necessita relação com o Cabeçalho devera extender
 * @author pauloborges
 */
@Data
@NoArgsConstructor
@MappedSuperclass
public abstract class DocumentoDetalhe extends BaseEntity{
    
    @ManyToOne
    @JoinColumn(name = "fk_documento")
    private DocumentoCabecalho documento;
}
