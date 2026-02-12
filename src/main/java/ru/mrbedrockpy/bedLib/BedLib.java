package ru.mrbedrockpy.bedLib;

import ru.mrbedrockpy.bedLib.ui.MenuManager;

public final class BedLib extends BedPlugin<BedLib> {

    private MenuManager menuManager;

    @Override
    protected void registerManagers() {
        this.menuManager = new MenuManager(this);
    }

    @Override
    protected void saveManagers() {
        if (this.menuManager != null) this.menuManager.closeAll();
    }
}
