package com.hacknife.skinswitcher.compiler;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;

public interface SkinSwitcher {
    String createSkinSwitcher(Messager messager);

    String getSkinSwitcherClass();

    Element getElement();
}
