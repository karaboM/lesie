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

package com.lesie.framework.service;


import com.lesie.framework.request.SharingRequest;
import com.lesie.framework.response.SharingResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.HashMap;

@Ignore
public class PrivacyEngineServiceTest {

    private PrivacyEngineService privacyEngineService;

    @Before
    public void init(){
        privacyEngineService = new PrivacyEngineService();
    }

    @Test
    public void testCanShare(){

        SharingRequest request = new SharingRequest("e123", "ck1", "w123", "ct2",
                new HashMap<String, String>());
        SharingResponse result = null;
        try {
            result = privacyEngineService.canShare(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertEquals("Not a success", "PROCEED", result.getR().get("status"));
    }
}
