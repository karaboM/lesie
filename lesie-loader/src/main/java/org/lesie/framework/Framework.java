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
package org.lesie.framework;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.lesie.framework.annotations.logging.InjectLogger;
import org.lesie.bootstrap.DefaultBootStrap;
import org.lesie.modules.DefaultBootupModule;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * File:Framework.java
 * Description: This class is the bootstrapping class that lo
 */
public class Framework {



    private Bootstrap  bootstrap;

    private static Framework frameworkInstance;

    private static boolean started = false;

    @InjectLogger
    private Logger log;

    private Framework(){

    }

    public static Framework getInstance(){

        synchronized (Framework.class){
            if(frameworkInstance == null){
                frameworkInstance = new Framework();
            }
            return frameworkInstance;
        }
    }

    public static boolean isStarted(){
        return started;
    }


    public void start(Map<String, List<String>> annotedPackages) throws Exception{

        Injector injector = Guice.createInjector(new DefaultBootupModule());
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        bootstrap = injector.getInstance(DefaultBootStrap.class);

     //   if(LoaderUtil.islesieClassLoader(cl)){

            started = true;
            Map<String,List<String>> config = configureLesie();
            config.putAll(annotedPackages);
            bootstrap.init(config);
          //  LoaderUtil.invokeMethodOnLoader(cl,"setFrameworkStarted",true);

   //     }else{
     //       log.info("V-001:ClassLoader is not of type lesieLoader");
       // }


    }

    private Map<String, List<String>> configureLesie() {
        HashMap<String, List<String>> config = new HashMap<String, List<String>>();;

        List<String> processorList = new ArrayList<String>();
        processorList.add("finder.FinderProcessor");
        processorList.add("attacher.DefaultAttacher");

        List<String> domainList = new ArrayList<String>();
        config.put("processors",processorList);

        return config;
    }

    @Inject
    public void setBootstrap(Bootstrap bootstrap){
        this.bootstrap = bootstrap;
    }

}
