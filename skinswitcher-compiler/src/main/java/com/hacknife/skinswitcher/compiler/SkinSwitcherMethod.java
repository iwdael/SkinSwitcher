package com.hacknife.skinswitcher.compiler;

import com.hacknife.skinswitcher.annotation.Method;
import com.hacknife.skinswitcher.annotation.State;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.lang.model.element.Element;

public class SkinSwitcherMethod {
    private int methodId;
    private List<Element> stickyElements;
    private List<Element> elements;

    public SkinSwitcherMethod() {
        this.methodId = 0;
        this.elements = new ArrayList<>();
        this.stickyElements = new ArrayList<>();
    }

    public SkinSwitcherMethod setMethodId(int methodId) {
        this.methodId = methodId;
        return this;
    }

    public int getMethodId() {
        return methodId;
    }

    public SkinSwitcherMethod addElement(Element element) {
        if (element.getAnnotation(Method.class).value()== State.sticky){
            stickyElements.add(element);
        }else if (element.getAnnotation(Method.class).value()== State.once){
            elements.add(element);
        }
        return this;
    }

    public List<Element> getElements() {
        return elements;
    }

    public List<Element> getStickyElements() {
        return stickyElements;
    }

    public List<Element> getAllElement() {
        List<Element> list = new ArrayList<>();
        list.addAll(elements);
        list.addAll(stickyElements);
        return list;
    }

    @Override
    public String toString() {
        return "{" +
                "\"methodId\":" + methodId +
                ", \"stickyElements\":" + stickyElements +
                ", \"elements\":" + elements +
                '}';
    }
}
