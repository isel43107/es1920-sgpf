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

import io.github.kriolsolutions.sgpf.backend.dal.entidades.docs.*;
import io.github.kriolsolutions.sgpf.backend.dal.entidades.BaseEntity;
import io.github.kriolsolutions.sgpf.backend.dal.entidades.docs.Despacho.DespachoDecisao;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author pauloborges
 */
@Data
@NoArgsConstructor
@MappedSuperclass
public abstract class ProjetoFin extends BaseEntity{
    
    @NotNull
    @Column(name = "custo_elegivel", nullable = false)
    private double custoElegivel;
    
    @NotNull
    @Column(name = "mnt_financiado", nullable = false)
    private double montanteFinanciado;

    @OneToOne
    @JoinColumn(name = "fk_projeto")
    private Projeto projeto;
}
