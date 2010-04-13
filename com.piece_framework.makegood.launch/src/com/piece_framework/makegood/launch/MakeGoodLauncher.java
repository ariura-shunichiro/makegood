/**
 * Copyright (c) 2009 MATSUFUJI Hideharu <matsufuji2008@gmail.com>,
 * All rights reserved.
 *
 * This file is part of MakeGood.
 *
 * This program and the accompanying materials are made available under
 * the terms of the Eclipse Public License v1.0 which accompanies this
 * distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.piece_framework.makegood.launch;

import com.piece_framework.makegood.core.TestingFramework;

public class MakeGoodLauncher {
    private TestingFramework testingFramework;
    private String script;

    public MakeGoodLauncher(TestingFramework testingFramework,
                     String script
                     ) {
        this.testingFramework = testingFramework;
        this.script = script;
    }

    public TestingFramework getTestingFramework() {
        return testingFramework;
    }

    public String getScript() {
        return script;
    }
}
