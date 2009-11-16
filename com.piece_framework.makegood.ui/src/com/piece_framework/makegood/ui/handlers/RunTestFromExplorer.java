package com.piece_framework.makegood.ui.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IScriptFolder;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

import com.piece_framework.makegood.core.PHPResource;
import com.piece_framework.makegood.javassist.monitor.WeavingMonitor;
import com.piece_framework.makegood.ui.launch.MakeGoodLaunchShortcut;

public class RunTestFromExplorer extends AbstractHandler {
    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        ISelection selection = HandlerUtil.getActiveMenuSelection(event);
        if (selection == null) {
            selection = getSelectionFromActivePage();
        }
        MakeGoodLaunchShortcut shortcut = new MakeGoodLaunchShortcut();
        shortcut.launch(selection, "run"); //$NON-NLS-1$
        return shortcut;
    }

    @Override
    public boolean isEnabled() {
        if (!WeavingMonitor.endAll()) {
            return false;
        }
        ISelection selection = getSelectionFromActivePage();
        if (!(selection instanceof IStructuredSelection)) {
            return super.isEnabled();
        }

        Object element = ((IStructuredSelection) selection).getFirstElement();
        IResource resource = null;
        if (element instanceof IResource) {
            resource = (IResource) element;
        } else if (element instanceof IModelElement) {
            resource = ((IModelElement) element).getResource();
        }

        if (resource == null) {
            return false;
        }
        if (resource instanceof IFolder) {
            return true;
        }
        return PHPResource.isTrue(resource);
    }

    private ISelection getSelectionFromActivePage() {
        IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
        return page.getSelection();
    }
}