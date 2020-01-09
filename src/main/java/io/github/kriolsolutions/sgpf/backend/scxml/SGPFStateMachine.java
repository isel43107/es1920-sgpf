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
package io.github.kriolsolutions.sgpf.backend.scxml;

import java.net.URL;
import org.apache.commons.scxml2.env.AbstractStateMachine;
import org.apache.commons.scxml2.model.ModelException;

/**
 *
 * @author pauloborges
 */
public class SGPFStateMachine extends AbstractStateMachine {

    /**
     * The events for the SGPF
     */
    public static final String 
            
            EVENT_CAND_REGISTAR = "CANDIDATURA_REGISTADO",
            EVENT_ENQUADRADO = "ENQUADRADO",
            EVENT_DESENQUADRADO = "DESENQUADRADO", 
            EVENT_APROVADO = "APROVADO",
            EVENT_REJEITADO = "REJEITADO",
            EVENT_REENQUADRADO = "REENQUADRADO",
            EVENT_FAVORAVEL = "FAVORAVEL", 
            EVENT_DESFAVORAVEL = "DESFAVORAVEL",
            EVENT_PAGAMENTO = "PAGAMENTO",
            EVENT_FIM_PAGAMENTO = "FIM_PAGAMENTO",
            EVENT_REFORCO = "REFORCO";

    public SGPFStateMachine(URL scxmlDocument) throws ModelException {
        super(scxmlDocument);
    }

    public SGPFStateMachine() throws ModelException {
        super(SGPFStateMachine.class.getClassLoader().
                getResource("state_machines/sgpf_sm.scxml2"));
    }

}
