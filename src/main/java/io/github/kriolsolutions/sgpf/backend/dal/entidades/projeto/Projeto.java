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
import io.github.kriolsolutions.sgpf.backend.dal.entidades.auth.Utilizador;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name = "projeto", schema="projeto")
@Entity
public class Projeto extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Column(name = "proj_designacao", nullable = false)
    private String projDesignacao;

    //@NotNull Gerado na camanda de negocio
    @Column(name = "proj_numero", nullable = false, unique = true)
    private String projNumero;

    @Column(name = "proj_nib")
    private String projNIB;

    @Column(name = "proj_mnt_solicitado", nullable = false)
    private double projMontanteSolicitado;

    //@Column(name = "proj_custo_elegivel", nullable = false)
    //private double projCustoElegivel;
    @Column(name = "proj_descricao")
    private String projDescricao;

    @Column(name = "proj_tipo", nullable = false)
    @Enumerated(EnumType.STRING)
    private ProjetoTipo projTipo;

    @Column(name = "proj_estado", nullable = false)
    @Enumerated(EnumType.STRING)
    private ProjetoEstado projEstado;

    @Column(name = "promotor_designacao")
    private String promotorDesignacao;

    @Column(name = "promotor_nif")
    private String promotorNIF;

    @Column(name = "promotor_nacionalidade")
    private String promotorNacionalidade;

    @Column(name = "contato_nome")
    private String contatoNome;

    @Column(name = "contato_email")
    private String contatoEmail;

    @Column(name = "contato_telefone")
    private String contatoTelefone;

    @ManyToOne
    @JoinColumn(name = "fk_utilizador_tecnico", updatable = true, nullable = true)
    private Utilizador utilizadorTecnico;

    @ManyToOne
    @JoinColumn(name = "fk_utilizador_gestorfin", updatable = true, nullable = true)
    private Utilizador utilizadorGestorFin;

    public static enum ProjetoTipo {
        BONIFICAO,
        INCENTIVO;
    }

    /**
     * Estados do projeto
     */
    public static enum ProjetoEstado {

        /**
         * EM CANDIDATURA
         */
        EM_CANDIDATURA,
        
        /**
         * Aguarda despacho de abertura
         */
        DESPACHO_ABERTURA,
        
        /**
         * Aguarda despacho de financiamento (Contido no Estado DESPACHO_FINANCIAMENTO)
         * 
         * Apenas para Financiamento do tipo Incentivo
         */
        DESPACHO_FIN_INCENTIVO,
        
        /**
         * Aguarda despacho de financiamento (Contido no Estado DESPACHO_FINANCIAMENTO)
         * 
         * Apenas para Financiamento do tipo Bonificação
         */
        DESPACHO_FIN_BONIFICACAO,
        
        /**
         * Aguarda despacho de reforço
         */
        DESPACHO_REFORCO,
        
        /**
         * Em pagamento (Contido no Estado EM_FINANCIAMENTO)
         */
        EM_PAGAMENTO,
        
        /**
         * Em pagamento (Contido no Estado EM_FINANCIAMENTO)
         */
        PROJETO_FECHADO,
        
        /**
         * Aguarda parecer técnico
         */
        PARECER_TECNICO,
        
        /**
         * Projeto arquivado
         */
        PROJETO_ARQUIVADO,
        
        /**
         * Projeto rejeitado
         */
        PROJETO_REJEITADO,
        
        /**
         * Projeto supenseo
         */
        PROJETO_SUSPENSO;
    }

}
