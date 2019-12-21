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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

    @Column(name = "proj_designacao")
    private String projDesignacao;
    
    @Column(name = "proj_numero")
    private String projNumero;
    
    @Column(name = "proj_nib")
    private String projNIB;
    
    @Column(name = "proj_mnt_solicitado")
    private double projMontanteSolicitado;
    
    @Column(name = "proj_descricao")
    private String projDescricao;

    @Column(name = "proj_tipo")
    @Enumerated(EnumType.STRING)
    private ProjetoTipo projTipo;   
    
    @Column(name = "proj_estado")
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
    
    public static enum ProjetoTipo {
        BONIFICAO,
        INCENTIVO;
    }
    
    
    public static enum ProjetoEstado {
        
        EM_CANDIDATURA,
        DESPACHO_ABERTURA,
        DESPACHO_FINANCIAMENTO,
        DESPACHO_REFORCO,
        EM_PAGAMENTO,
        PROJETO_FECHADO,
        PARECER_TECNICO,
        PROJETO_ARQUIVADO,
        PROJETO_REJEITADO,
        PROJETO_SUSPENSO;
    }
    
}
