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
package io.github.kriolsolutions.sgpf.backend.dal.entidades.projeto;

import io.github.kriolsolutions.sgpf.backend.dal.entidades.BaseEntity;
import io.github.kriolsolutions.sgpf.backend.dal.entidades.docs.DespachoFin;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "projeto_fin_bonificacao", schema="projeto")
@Entity
public class ProjetoFinBonificacao extends ProjetoFin {
    
    @NotNull
    @Column(name = "tax_bonificacao", nullable = false)
    private double taxBonificacao;
    
    @NotNull
    @Column(name = "mnt_max_bonificacao", nullable = false)
    private double mntMaxBonificacao;
    
    @Column(name = "periodo", nullable = false)
    private long periodo;
}
