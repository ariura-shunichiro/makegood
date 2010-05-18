/**
 * Copyright (c) 2010 MATSUFUJI Hideharu <matsufuji2008@gmail.com>
 * All rights reserved.
 *
 * This file is part of MakeGood.
 *
 * This program and the accompanying materials are made available under
 * the terms of the Eclipse Public License v1.0 which accompanies this
 * distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.piece_framework.makegood.core.preference;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;

import com.piece_framework.makegood.core.MakeGoodCorePlugin;

public class MakeGoodPreferenceInitializer extends AbstractPreferenceInitializer {
    public static final String RUN_ALL_TESTS_WHEN_FILE_IS_SAVED = "RUN_ALL_TESTS_WHEN_FILE_IS_SAVED"; //$NON-NLS-1$

    @Override
    public void initializeDefaultPreferences() {
        MakeGoodCorePlugin.getDefault()
                          .getPreferenceStore()
                          .setDefault(RUN_ALL_TESTS_WHEN_FILE_IS_SAVED, true);
    }
}
