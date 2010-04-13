/**
 * Copyright (c) 2010 MATSUFUJI Hideharu <matsufuji2008@gmail.com>,
 *               2010 KUBO Atsuhiro <kubo@iteman.jp>,
 * All rights reserved.
 *
 * This file is part of MakeGood.
 *
 * This program and the accompanying materials are made available under
 * the terms of the Eclipse Public License v1.0 which accompanies this
 * distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.piece_framework.makegood.core.result;


public interface JUnitXMLReaderListener {
    public void startTestSuite(TestSuiteResult testSuite);

    public void endTestSuite();

    public void startTestCase(TestCaseResult testCase);

    public void endTestCase();

    public void startFailure(TestCaseResult failure);

    public void endFailure();
}
