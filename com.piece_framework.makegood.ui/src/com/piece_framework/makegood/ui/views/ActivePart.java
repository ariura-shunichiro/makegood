/**
 * Copyright (c) 2009-2010 MATSUFUJI Hideharu <matsufuji2008@gmail.com>,
 *               2010 KUBO Atsuhiro <kubo@iteman.jp>,
 * All rights reserved.
 *
 * This file is part of MakeGood.
 *
 * This program and the accompanying materials are made available under
 * the terms of the Eclipse Public License v1.0 which accompanies this
 * distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.piece_framework.makegood.ui.views;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.internal.ui.editor.EditorUtility;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.php.internal.core.project.PHPNature;
import org.eclipse.php.internal.debug.ui.PHPDebugPerspectiveFactory;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.console.IConsoleConstants;

import com.piece_framework.makegood.core.MakeGoodProperty;
import com.piece_framework.makegood.ui.Activator;

public class ActivePart {
    private static ActivePart soleInstance;
    private Object lastTarget;

    private ActivePart() {}

    public static ActivePart getInstance() {
        if (soleInstance == null) {
            soleInstance = new ActivePart();
        }

        return soleInstance;
    }

    public void setPart(IWorkbenchPart part) {
        String id = part.getSite().getId();
        if (ResultView.VIEW_ID.equals(id)) return;
        if (PHPDebugPerspectiveFactory.ID_PHPDebugOutput.equals(id)) return;
        if (PHPDebugPerspectiveFactory.ID_PHPBrowserOutput.equals(id)) return;
        if (IConsoleConstants.ID_CONSOLE_VIEW.equals(id)) return;

        if (part instanceof IEditorPart) {
            if (shouldUpdate(part)) {
                lastTarget = part;
            }
        } else {
            ISelectionProvider provider = part.getSite().getSelectionProvider();
            if (provider != null) {
                provider.addSelectionChangedListener(new ISelectionChangedListener() {
                    @Override
                    public void selectionChanged(SelectionChangedEvent event) {
                        if (shouldUpdate(event.getSelection())) {
                            lastTarget = event.getSelection();
                        }
                    }
                });
            }
        }
    }

    public void setPart() {
        IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
        if (window == null) return;
        IWorkbenchPage page = window.getActivePage();
        if (page == null) return;
        setPart(page.getActivePart());
    }

    public static boolean isAllTestsRunnable(Object target) {
        if (target == null) return false;

        IResource resource = getResource(target);
        if (resource == null) return false;
        if (!resource.getProject().exists()) return false;
        if (new MakeGoodProperty(resource).getTestFolders().size() == 0) return false;

        return true;
    }

    public boolean isAllTestsRunnable() {
        return isAllTestsRunnable(lastTarget);
    }

    public Object getLastTarget() {
        return lastTarget;
    }

    public static IResource getResource(Object target) {
        if (target instanceof IStructuredSelection) {
            IStructuredSelection selection = (IStructuredSelection) target;
            if (selection.getFirstElement() instanceof IModelElement) {
                return ((IModelElement) selection.getFirstElement()).getResource();
            } else if (selection.getFirstElement() instanceof IResource) {
                return (IResource) selection.getFirstElement();
            }
        } else if (target instanceof IEditorPart) {
            ISourceModule source =
                EditorUtility.getEditorInputModelElement((IEditorPart) target, false);
            if (source != null) {
                return source.getResource();
            }

            IEditorPart editor = (IEditorPart) target;
            if (editor.getEditorInput() instanceof IFileEditorInput) {
                return ((IFileEditorInput) editor.getEditorInput()).getFile();
            }
        }

        return null;
    }

    public IProject getProject() {
        return getProject(lastTarget);
    }

    /**
     * @since 1.5.0
     */
    private IProject getProject(Object target) {
        if (target == null) return null;
        IResource resource = getResource(target);
        if (resource == null) return null;
        return resource.getProject();
    }

    /**
     * @since 1.5.0
     */
    private boolean shouldUpdate(Object target) {
        IProject project = getProject(target);
        if (project == null) return false;
        try {
            return project.hasNature(PHPNature.ID);
        } catch (CoreException e) {
            Activator.getDefault().getLog().log(new Status(IStatus.WARNING, Activator.PLUGIN_ID, e.getMessage(), e));
            return false;
        }
    }
}
