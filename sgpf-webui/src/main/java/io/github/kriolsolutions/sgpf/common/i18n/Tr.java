/*
 * Copyright 2023 kriolSolutions.
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
package io.github.kriolsolutions.sgpf.common.i18n;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinService;
import java.io.Serializable;

/**
 *
 * @author poolb
 */
public class Tr implements Serializable {
    private final String key;
    private final Object[] params;

    public Tr(String key, Object... params) {
        this.key = key;
        this.params = params;
    }
    public Tr(String key) { this(key, (Object[]) null); }

    public String get() { return tr(key, params); }

    public static String tr(String key, Object... params) {
        return VaadinService.getCurrent().getInstantiator().getI18NProvider().getTranslation(key, UI.getCurrent().getLocale(), params);
    }
}