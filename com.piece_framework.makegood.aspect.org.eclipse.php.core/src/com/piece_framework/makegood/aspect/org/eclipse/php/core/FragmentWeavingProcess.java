/**
 * Copyright (c) 2010 KUBO Atsuhiro <kubo@iteman.jp>,
 * All rights reserved.
 *
 * This file is part of MakeGood.
 *
 * This program and the accompanying materials are made available under
 * the terms of the Eclipse Public License v1.0 which accompanies this
 * distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.piece_framework.makegood.aspect.org.eclipse.php.core;

import org.eclipse.ui.IStartup;

import com.piece_framework.makegood.aspect.org.eclipse.php.core.aspect.SystemIncludePathAspect;
import com.piece_framework.makegood.javassist.Aspect;
import com.piece_framework.makegood.javassist.WeavingProcess;

public class FragmentWeavingProcess extends WeavingProcess implements IStartup {
    private static final Aspect[] ASPECTS = {
        new SystemIncludePathAspect(),
    };
    private static final String[] DEPENDENCIES = {
        "org.eclipse.php.core", //$NON-NLS-1$
        "org.eclipse.core.resources" //$NON-NLS-1$
    };

    @Override
    public void earlyStartup() {
        if (MonitorTarget.endWeaving) return;
        process();
        MonitorTarget.endWeaving = true;
    }

    @Override
    protected String pluginId() {
        return Fragment.ID;
    }

    @Override
    protected Aspect[] aspects() {
        return ASPECTS;
    }

    @Override
    protected String[] dependencies() {
        return DEPENDENCIES;
    }
}
