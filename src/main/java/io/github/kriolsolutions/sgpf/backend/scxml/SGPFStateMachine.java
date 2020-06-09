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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLStreamException;
import org.apache.commons.scxml2.ActionExecutionContext;
import org.apache.commons.scxml2.SCXMLExpressionException;
import org.apache.commons.scxml2.SCXMLListener;
import org.apache.commons.scxml2.Status;
import org.apache.commons.scxml2.TriggerEvent;
import org.apache.commons.scxml2.env.AbstractStateMachine;
import org.apache.commons.scxml2.env.SimpleErrorHandler;
import org.apache.commons.scxml2.env.jexl.JexlContext;
import org.apache.commons.scxml2.model.Action;
import org.apache.commons.scxml2.model.CustomAction;
import org.apache.commons.scxml2.model.EnterableState;
import org.apache.commons.scxml2.model.ModelException;
import org.apache.commons.scxml2.model.SCXML;
import org.apache.commons.scxml2.model.Transition;
import org.apache.commons.scxml2.model.TransitionTarget;
import org.xml.sax.ErrorHandler;

/**
 *
 * @author pauloborges
 */
public class SGPFStateMachine extends AbstractStateMachine {

    private final static Logger LOGGER = Logger.getLogger(SGPFStateMachine.class.getName());
    private static final URL SCXML_FILE = SGPFStateMachine.class.getResource("state_machines/sgpf_sm.scxml2");

    /**
     * The events for the SGPF
     */
    public static final String EVENT_CAND_REGISTAR = "CANDIDATURA_REGISTADO",
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

    public SGPFStateMachine() throws ModelException {
        super(SCXML_FILE);
    }

    public void run() throws XMLStreamException {

        //engine = new SCXMLExecutor(new JexlEvaluator(), new SimpleDispatcher(), new SimpleErrorReporter());
        //reate a list of custom actions
        List<CustomAction> customActions = new ArrayList<>();
        CustomAction ca = new CustomAction("http://kriolsolutions.github.io/sgpf/helloAction", "hello", HelloAction.class);
        customActions.add(ca);

        ErrorHandler errorHandler = new SimpleErrorHandler();
        try {
            //SCXML stateMachine = SCXMLReader.read(SCXML_FILE);
            SCXML stateMachine = getEngine().getStateMachine();

            Map<String, TransitionTarget> targets = stateMachine.getTargets();
            TransitionTarget transitionTarget = targets.get("paused");
            stateMachine.setInitial("INITIAL");

            getEngine().setStateMachine(stateMachine);
            getEngine().setRootContext(new JexlContext());
            getEngine().addListener(stateMachine, new EntryListener());

            getEngine().go();
        } catch (ModelException e) {
            LOGGER.log(Level.SEVERE, "Exception while execute engine", e);
        }
    }

    public void sendEvent(String event) {

        if (event != null && !event.equals("")) {
            TriggerEvent[] evts = {new TriggerEvent(event, TriggerEvent.SIGNAL_EVENT, null)};
            try {
                getEngine().triggerEvents(evts);

                Status currStatus = getEngine().getCurrentStatus();
                Set<EnterableState> states = currStatus.getStates();

                states.forEach((state) -> {
                    System.out.println("Current status id is : " + state.getId());
                    /**/
                    if (state.getId().equals("reset")) {
                        TransitionTarget parent = state.getParent();
                        System.out.println("parent is : " + parent.getId());
                    }
                });

            } catch (ModelException ex) {
                LOGGER.log(Level.SEVERE, "Exception while trigger event: " + event, ex);
            }
        }
    }

    /**
     * EntryListener a SCXMLListener
     */
    protected class EntryListener implements SCXMLListener {

        @Override
        public void onEntry(EnterableState es) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void onExit(EnterableState es) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void onTransition(TransitionTarget tt, TransitionTarget tt1, Transition trnstn, String string) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    protected class HelloAction extends Action {

        @Override
        public void execute(ActionExecutionContext aec) throws ModelException, SCXMLExpressionException {
            aec.getStateMachine().getName();
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

}
