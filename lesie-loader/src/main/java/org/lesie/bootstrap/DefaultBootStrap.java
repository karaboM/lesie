/**
 *      Copyright 2015 CPUT
 *
 *      Licensed under the Apache License, Version 2.0 (the "License");
 *      you may not use this file except in compliance with the License.
 *      You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *      Unless required by applicable law or agreed to in writing, software
 *      distributed under the License is distributed on an "AS IS" BASIS,
 *      WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *      See the License for the specific language governing permissions and
 *      limitations under the License.
 */

package org.lesie.bootstrap;

import com.google.inject.Inject;
import org.lesie.framework.Bootstrap;
import org.lesie.framework.ChainManager;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class DefaultBootStrap implements Bootstrap {

    private List<String> processors;
    @Inject
    private ChainManager chainManager;
    private Map<String,List<String>> config;

    @Override
    public void init(Map<String,List<String>> config) throws Exception {

        chainManager.setConfig(config);
        //TODO:Add processors manually here
        Collection<String> processors = config.get("processors");

        chainManager.addProcessor(processors);

        chainManager.processChain();
    }


    public List<String> getProcessors() {
        return processors;
    }

    public void setProcessors(List<String> processors) {
        this.processors = processors;
    }

    public ChainManager getChainManager() {
        return chainManager;
    }

    public void setChainManager(ChainManager chainManager) {
        this.chainManager = chainManager;
    }

    public void setConfig(Map<String,List<String>> config) {
        this.config = config;
    }
}
