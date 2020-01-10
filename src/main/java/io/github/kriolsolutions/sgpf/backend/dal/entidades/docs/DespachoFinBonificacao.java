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
import io.github.kriolsolutions.sgpf.backend.dal.entidades.docs.Despacho.DespachoDecisao;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Uma bonificação é um subsídio aos juros de um empréstimo, 
 * previamente contratado com um banco, para desenvolvimento de um projecto de I&D. 
 * A percentagem/taxa é relativa ao juro do empréstimo.
 * @author pauloborges
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Table(name = "doc_desp_fin_bonificacao", schema="documentos")
@Entity
public class DespachoFinBonificacao extends DespachoFin{
    
    @Column(name = "tax_bonificacao", nullable = false)
    private double taxBonificacao;
    
    @Column(name = "mnt_max_bonificacao", nullable = false)
    private double mntMaxBonificacao;
    
    /**
     * Período de carência de um empréstimo (Meses)
     * Duranta quantos meses
     * 
     * Este período consiste numa modalidade de reembolso que lhe permite pagar 
     * uma prestação menor do empréstimo, uma vez que, durante algum tempo, 
     * estará apenas a reembolsar os juros, 
     * não estando a amortizar capital em dívida.
     */
    @Column(name = "periodo", nullable = false)
    private long periodo;
}
