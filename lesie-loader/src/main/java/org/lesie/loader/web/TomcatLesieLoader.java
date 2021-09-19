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

package org.lesie.loader.web;

import org.apache.catalina.loader.WebappClassLoader;
import org.lesie.attacher.DefaultAttacher;

import java.util.logging.Level;
import java.util.logging.Logger;


public class TomcatLesieLoader extends WebappClassLoader {

    private DefaultAttacher da;

    private Logger log = Logger.getLogger(TomcatLesieLoader.class.getName());

    public TomcatLesieLoader() {
        super();
        da = new DefaultAttacher();
    }

    public TomcatLesieLoader(ClassLoader parent) {
        super(parent);
        da = new DefaultAttacher();
    }

    @Override
    public Class loadClass(String name) throws ClassNotFoundException {
        try {
            boolean weaved = da.weaveCodeToClass(name,this);
           if(weaved) {
               resourceEntries.remove(name.replaceAll("\\.", "/") + ".class");
           }
        } catch (Exception e) {
            log.log(Level.ALL,e.getMessage());
        }
        return super.loadClass(name);
    }

    @Override
    public Class findClass(String name) throws ClassNotFoundException {
        return super.findClass(name);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    protected Class findLoadedClass0(String name) {
        return super.findLoadedClass0(name);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
