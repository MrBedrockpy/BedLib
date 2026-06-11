package ru.mrbedrockpy.bedlib;

import ru.mrbedrockpy.bedlib.ui.MenuManager;

public final class BedLib extends BedPlugin<BedLib> {

    private MenuManager menuManager;

    @Override
    protected void initManagers() {
        this.menuManager = new MenuManager(this);
    }

    @Override
    protected void saveManagers() {
        if (this.menuManager != null) this.menuManager.closeAll();
    }
}
