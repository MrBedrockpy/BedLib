package ru.mrbedrockpy.bedlib;

import lombok.Getter;
import ru.mrbedrockpy.bedlib.ui.MenuManager;

@Getter
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
