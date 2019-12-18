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
package io.github.kriolsolutions.sgpf.backend;

import com.vaadin.cdi.annotation.VaadinSessionScoped;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author pauloborges
 */
@ApplicationScoped
public class EntityManagerProducer {

    
    @PersistenceUnit(unitName = "sgpf-pu")
    private EntityManagerFactory emf;
    //private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("sgpf-pu");

    
    @Default
    @VaadinSessionScoped
    @Produces
    public EntityManager entityManager() {
        return emf.createEntityManager();
    }

    /**
     * Close properly entityManager
     *
     * @param manager
     */
    public void close(@Disposes @Default EntityManager manager) {
        if (manager.isOpen()) {
            manager.close();
        }
    }
}
