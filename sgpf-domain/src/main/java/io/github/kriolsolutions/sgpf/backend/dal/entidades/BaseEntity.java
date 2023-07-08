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

import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Version;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Base atributos de auditoria
 * Audit Fields
 * @author pauloborges
 */
@Data
@NoArgsConstructor
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    /**
     * Aqui apenas para facilitar a generacao de ID nas subclasses
     * Tem limitições que neste projeto nao tem importancia
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "created_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(name = "last_modified_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;
    
    //private String createdBy;
    //private String lastModifiedBy ;
    
    /**
     * Este campo é gerido automatica pelo framework JPA(Hibernate/EclipeLink)
     * Red more: Pessimist Lock vs Optimistic Lock
     */
    @Column(name = "version", nullable = false)
    @Version
    private Integer version;

    @PrePersist
    public void prePersist() {
        createdDate = new Date();
        lastModifiedDate = new Date();
    }
    
    @PreUpdate
    public void preUpdate(){
        lastModifiedDate = new Date();
    }

    @PreRemove
    public void preRemove() {

    }
}
