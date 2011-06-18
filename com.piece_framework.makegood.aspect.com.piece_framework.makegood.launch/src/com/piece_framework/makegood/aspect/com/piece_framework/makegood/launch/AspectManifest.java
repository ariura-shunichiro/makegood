/**
 * Copyright (c) 2010-2011 KUBO Atsuhiro <kubo@iteman.jp>,
 * All rights reserved.
 *
 * This file is part of MakeGood.
 *
 * This program and the accompanying materials are made available under
 * the terms of the Eclipse Public License v1.0 which accompanies this
 * distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.piece_framework.makegood.aspect.com.piece_framework.makegood.launch;

import com.piece_framework.makegood.aspect.Aspect;
import com.piece_framework.makegood.aspect.PDTVersion;
import com.piece_framework.makegood.aspect.com.piece_framework.makegood.launch.aspect.PHPexeItemRepositoryAspect;

/**
 * @since 1.2.0
 */
public class AspectManifest implements com.piece_framework.makegood.aspect.AspectManifest {
    private static final Aspect[] ASPECTS = {
        new PHPexeItemRepositoryAspect()
    };

    @Override
    public String pluginId() {
        return Fragment.PLUGIN_ID;
    }

    @Override
    public Aspect[] aspects() {
        return ASPECTS;
    }

    @Override
    public String[] dependencies() {
        return PDTVersion.getInstance().compareTo("2.2.0") >= 0 ? //$NON-NLS-1$
                   new String[] {
                       "com.piece_framework.makegood.launch", //$NON-NLS-1$
                       "org.eclipse.core.resources", //$NON-NLS-1$
                       "org.eclipse.php.debug.core" //$NON-NLS-1$
                   } :
                   new String[] {
                       "com.piece_framework.makegood.launch", //$NON-NLS-1$
                       "org.eclipse.core.resources", //$NON-NLS-1$
                       "org.eclipse.php.debug.core", //$NON-NLS-1$
                       "org.eclipse.equinox.preferences" //$NON-NLS-1$
                   };
    }
}