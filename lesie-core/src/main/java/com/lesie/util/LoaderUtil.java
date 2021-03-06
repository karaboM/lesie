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

package com.lesie.util;

import org.lesie.loader.LesieLoader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;


public class LoaderUtil {

    /*
     * Name:convertTolesieLoader
     * Description: Thi method returns true if the specified Classloader is of type lesieLoader
     */
    public static boolean islesieClassLoader(ClassLoader cl) throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        Class lesieLoaderClazz = cl.getParent().loadClass(LesieLoader.class.getCanonicalName());
        Class<?>[] clInterfaces = cl.getClass().getInterfaces();


        for(int i=0; i != clInterfaces.length;i++){
            Class clInterface =clInterfaces[i];
            if(clInterface.isAssignableFrom(lesieLoaderClazz)){
                return true;
            }

        }

        return false;
    }

    public static void invokeMethodOnLoader(ClassLoader cl,String methodName,Object... params) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?>[] paramClasses = new Class<?>[params.length];
        for(int i=0;i != params.length ;i++){

            paramClasses[i] = params[i].getClass();
        }
        Method method = cl.getClass().getMethod(methodName,paramClasses);
        method.invoke(cl,params);
    }
}
