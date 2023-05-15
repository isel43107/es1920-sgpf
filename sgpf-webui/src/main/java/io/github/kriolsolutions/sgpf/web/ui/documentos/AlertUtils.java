package io.github.kriolsolutions.sgpf.web.ui.documentos;


import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.notification.Notification;

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

/**
 *
 * @author pauloborges
 */
public class AlertUtils {

        static private Notification buildNotification(String cssClass, String message) {
            String classButtonClose = "  <span class=\"sgpf-alert-closebtn\" onclick=\"this.parentElement.style.display='none';\">&times;</span>";
            classButtonClose = ""; //PARA NAO APARECER O BUTTON close
            return new Notification(
                    new Html(" <div class=\"sgpf-alert " + cssClass + "\">"
                            + classButtonClose
                            + "  " + message
                            + "</div> ")
            );
        }

        static public Notification alert(String message) {
            return danger(message);
        }
        
        static public Notification danger(String message) {
            return buildNotification("danger", message);
        }

        static public Notification sucess(String message) {
            return buildNotification("sucess", message);
        }

        static public Notification info(String message) {
            return buildNotification("danger", message);
        }

        static public Notification warning(String message) {
            return buildNotification("warning", message);
        }
    }

