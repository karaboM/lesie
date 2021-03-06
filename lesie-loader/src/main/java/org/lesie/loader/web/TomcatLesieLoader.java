/**
 *      Copyright 2014 CPUT
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

package org.lesie.loader.web;

import org.lesie.loader.LesieLoader;
import org.apache.catalina.loader.WebappClassLoader;
import org.lesie.loader.connection.Connector;
import org.lesie.loader.connection.impl.ConnectorImpl;
import org.lesie.loader.impl.DefaultLesieLoaderStrategy;
import org.lesie.loader.util.LesieLogger;

import java.util.logging.Logger;


public class TomcatLesieLoader extends WebappClassLoader implements LesieLoader {

    private boolean frameworkStarted = false;
    private DefaultLesieLoaderStrategy loaderStrategy;
    private Connector connector;

    private Logger log = Logger.getLogger(TomcatLesieLoader.class.getName());

    public TomcatLesieLoader() {
        super();
        lInit();

    }

    public TomcatLesieLoader(ClassLoader parent) {
        super(parent);
        lInit();
    }

    /* name: init
     * description: this does the necessary initialisation for the Tomcat lesie loader
     *
     */
    private void lInit() {
        connector = new ConnectorImpl(9999, "localhost", log);
        connector.connect();
        loaderStrategy = new DefaultLesieLoaderStrategy();
    }

    @Override
    public Class loadClass(String name) throws ClassNotFoundException {

        if (frameworkStarted) {
            log.info("lesie framework has been started");

            Class modifiedClass = loaderStrategy.getModifiedClass(name);
            if (modifiedClass != null) {
                return modifiedClass;
            }

        } else {
            log.info("lesie framework has not been started as of yet");
        }

        return super.loadClass(name);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public Class findClass(String name) throws ClassNotFoundException {
        return super.findClass(name);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    protected Class findLoadedClass0(String name) {
        return super.findLoadedClass0(name);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public boolean isFrameworkStarted() {
        return frameworkStarted;
    }

    public void setFrameworkStarted(Boolean frameWorkStarted) {
        this.frameworkStarted = frameWorkStarted;
    }

    @Override
    public void addGateClass(String classFullName, Class gateClass) {
        loaderStrategy.addGateClass(classFullName, gateClass);

    }

    @Override
    public void addMarkClass(String classFullName, Class markClass) {
        loaderStrategy.addGateClass(classFullName, markClass);
    }
}
