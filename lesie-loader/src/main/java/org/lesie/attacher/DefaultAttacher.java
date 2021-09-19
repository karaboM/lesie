/**
 * Copyright 2015 CPUT
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.lesie.attacher;


import com.lesie.framework.annotations.ExitPoint;
import com.lesie.framework.annotations.Gate;
import com.lesie.framework.request.SharingRequest;
import javassist.*;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
import javassist.bytecode.ParameterAnnotationsAttribute;
import javassist.bytecode.annotation.Annotation;
import org.lesie.exception.WeaveException;
import org.lesie.framework.Processor;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

public class DefaultAttacher implements Processor<Map<String, List<String>>> {

    private final String MARK_CLASS = "markClass";
    private ClassPool classPool = null;

    public Map<String, List<String>> process(Map<String, List<String>> config) throws Exception {

        if (!config.containsKey(MARK_CLASS)) {
            throw new WeaveException("No Mark Classes found");
        }

        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        ClassPool classPool = ClassPool.getDefault();
        classPool.insertClassPath(new LoaderClassPath(cl));
        for (String strMarkClass : config.get(MARK_CLASS)) {
            CtClass markCtClass = classPool.get(strMarkClass);
            //weaveCode(markCtClass);

        }
        return config;
    }

    public boolean weaveCodeToClass(String name, ClassLoader cl) throws Exception {
        initClassPool(cl);
        CtClass lesieAnnotatedClass = classPool.get(name);
        return weaveCode(lesieAnnotatedClass);
    }

    private boolean weaveCode(CtClass markCtClass) throws ClassNotFoundException, WeaveException, CannotCompileException, NotFoundException, IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        boolean weaved = false;
        if (markCtClass.hasAnnotation(Gate.class)) {
            for (CtMethod ctMethod : markCtClass.getDeclaredMethods()) {
                if (ctMethod.hasAnnotation(ExitPoint.class)) {

                    int sharingReqIndex = -1;
                    MethodInfo methodInfo = ctMethod.getMethodInfo();
                    LocalVariableAttribute table = (LocalVariableAttribute) methodInfo.getCodeAttribute().getAttribute(LocalVariableAttribute.tag);


                    CtClass[] parameterTypes = ctMethod.getParameterTypes();
                    for (int i = 0; i != parameterTypes.length; i++) {
                        CtClass pType = parameterTypes[i];

                        String className = pType.getName();
                        if (className.equals(SharingRequest.class.getName())) {
                            sharingReqIndex = i + 1;
                        }
                    }

                    if (sharingReqIndex > 0) {

                        CtMethod genMethod = CtNewMethod.copy(ctMethod, ctMethod.getName(), markCtClass, null);
                        String newMethodName = ctMethod.getName() + "$Impl";
                        ctMethod.setName(newMethodName);

                        //build method body
                        StringBuffer body = new StringBuffer();
                        body.append("{");
                        body.append("com.lesie.framework.service.PrivacyEngineService privacyEngineService = new com.lesie.framework.service.PrivacyEngineService();");
                        body.append("com.lesie.framework.response.SharingResponse result = privacyEngineService.canShare($" + sharingReqIndex + ");");
                        body.append("if(result.getR().get(\"code\").equals(\"PROCEED\")){" +
                                newMethodName + "($$);"
                                + "}else{System.out.println(\"Yeah I am printing\");}");
                        body.append("}");
                        genMethod.setBody(body.toString());

                        markCtClass.addMethod(genMethod);
                        weaved = true;
                    }
                }
            }
            markCtClass.toClass();
            markCtClass.writeFile();
            markCtClass.detach();
        }
        return weaved;
    }

    private void generate(CtClass markCtClass, CtMethod ctMethod, String methodName) throws CannotCompileException {
        CtMethod mNew = CtNewMethod.copy(ctMethod, methodName, markCtClass, null);
        String nMethodName = methodName + "$Impl";
        ctMethod.setName(nMethodName);

        //build method body
        StringBuffer body = new StringBuffer();
        body.append("{System.out.println(\"Hello World\");}");

        mNew.setBody(body.toString());

        markCtClass.addMethod(mNew);
    }

    private void initClassPool(ClassLoader cl) {
        if (classPool == null) {
            classPool = ClassPool.getDefault();
            classPool.insertClassPath(new LoaderClassPath(cl));
        }
    }
}
