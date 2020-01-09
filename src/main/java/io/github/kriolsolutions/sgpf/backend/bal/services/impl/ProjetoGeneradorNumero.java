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
package io.github.kriolsolutions.sgpf.backend.bal.services.impl;

import java.time.LocalDate; 

/**
 *
 * @author pauloborges
 */
public class ProjetoGeneradorNumero {
    
    /**
     * Num sistema real este sera suportado por um tabela, normalmente conhecido com 
     * NumeroSerie {Nome, Prefixo, SequenciaInicial, SequenciaActual)
     *  Nome: Serie 2019, 
     *  Prefixo: 2019
     *  SequenciaInicial: 1 devera ser o valor Omissão se não definido 
     *  SequenciaActual: obter o numero actual se existir ? incrementar 1 : e guardar o novo
     *  Formato ${Prefixo}${SequenciaActual}
     * @param numero
     * @return 
     */
    public static String gerarNumeroProjeto(Long numero){
        long ano = LocalDate.now().getYear();
        String formatted = String.format("%d-%07d",ano, numero);
        return formatted;
    }
    
}
