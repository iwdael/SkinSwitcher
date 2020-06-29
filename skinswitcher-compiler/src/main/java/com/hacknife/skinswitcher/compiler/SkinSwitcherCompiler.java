package com.hacknife.skinswitcher.compiler;

import com.google.auto.service.AutoService;
import com.hacknife.skinswitcher.annotation.DefaultFilter;
import com.hacknife.skinswitcher.annotation.Filter;
import com.hacknife.skinswitcher.annotation.Id;
import com.hacknife.skinswitcher.annotation.Method;
import com.hacknife.skinswitcher.annotation.Replace;
import com.hacknife.skinswitcher.annotation.Resource;
import com.hacknife.skinswitcher.annotation.Switcher;
import com.hacknife.skinswitcher.annotation.SwitcherChange;
import com.hacknife.skinswitcher.annotation.SwitcherView;

import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

/**
 * author  : Hacknife
 * e-mail  : hacknife@outlook.com
 * github  : http://github.com/hacknife
 * project : SkinSwitcher
 */
@AutoService(Processor.class)
public class SkinSwitcherCompiler extends AbstractProcessor {
    protected Messager messager;
    protected Map<String, SkinSwitcherAdapter> proxySkinSwitcher = new LinkedHashMap<>();
    protected Map<Integer, SkinSwitcherView> proxyView = new LinkedHashMap<>();
    protected Element elementId;
    protected Element elementReplace;
    protected Element elementResource;
    protected Element elementDefaultFilter;
    protected Element elementSwitcherChange;

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        HashSet<String> supportType = new LinkedHashSet<>();
        supportType.add(SwitcherChange.class.getCanonicalName());
        supportType.add(Switcher.class.getCanonicalName());
        supportType.add(Filter.class.getCanonicalName());
        supportType.add(Replace.class.getCanonicalName());
        supportType.add(Id.class.getCanonicalName());
        supportType.add(Resource.class.getCanonicalName());
        supportType.add(DefaultFilter.class.getCanonicalName());
        supportType.add(SwitcherView.class.getCanonicalName());
        return supportType;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        messager = processingEnv.getMessager();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        proxySkinSwitcher.clear();
        processSwitcherChange(roundEnv);
        processDefaultFilter(roundEnv);
        processResource(roundEnv);
        processReplace(roundEnv);
        processId(roundEnv);
        processFilter(roundEnv);
        processSwitcher(roundEnv);
        processProxy(roundEnv);
        processProxyMethod(roundEnv);
        process();
        return true;
    }

    private void processSwitcherChange(RoundEnvironment roundEnv) {
        Set<? extends Element> switcherChanges = roundEnv.getElementsAnnotatedWith(SwitcherChange.class);
        if (switcherChanges.size() == 0) return;
        if (switcherChanges.size() > 1)
            messager.printMessage(Diagnostic.Kind.ERROR, "Switcher change method more than one !");
        else elementSwitcherChange = (Element) switcherChanges.toArray()[0];
    }

    private void processProxyMethod(RoundEnvironment roundEnv) {
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(Method.class);
        for (Element element : elements) {
            Collection<SkinSwitcherView> views = proxyView.values();
            for (SkinSwitcherView view : views) {
                if (view.contain(element)) {
                    view.addMethodElement(element);
                    break;
                }
            }
        }
    }

    private void processProxy(RoundEnvironment roundEnv) {
        Set<? extends Element> proxies = roundEnv.getElementsAnnotatedWith(SwitcherView.class);
        for (Element element : proxies) {
            SkinSwitcherView view = proxyView.get(element.getAnnotation(SwitcherView.class).value());
            if (view == null) {
                view = new SkinSwitcherView();
                proxyView.put(element.getAnnotation(SwitcherView.class).value(), view);
                view.setPackage(element.getEnclosingElement().toString());
            }
            view.addClassElement(element);
        }
        proxyView.values().forEach(new Consumer<SkinSwitcherView>() {
            @Override
            public void accept(SkinSwitcherView view) {
                view.generateInfo(messager);
            }
        });
    }

    private void processDefaultFilter(RoundEnvironment roundEnv) {
        Set<? extends Element> defaultFilters = roundEnv.getElementsAnnotatedWith(DefaultFilter.class);
        if (defaultFilters.size() == 0) return;
        if (defaultFilters.size() > 1)
            messager.printMessage(Diagnostic.Kind.ERROR, "default filter method more than one !");
        else elementDefaultFilter = (Element) defaultFilters.toArray()[0];
    }

    private void processResource(RoundEnvironment roundEnv) {
        Set<? extends Element> resources = roundEnv.getElementsAnnotatedWith(Resource.class);
        if (resources.size() == 0) return;
        if (resources.size() > 1)
            messager.printMessage(Diagnostic.Kind.ERROR, "Source id convert value method more than one !");
        else elementResource = (Element) resources.toArray()[0];
    }

    private void processId(RoundEnvironment roundEnv) {
        Set<? extends Element> ids = roundEnv.getElementsAnnotatedWith(Id.class);
        if (ids.size() == 0) return;
        if (ids.size() > 1)
            messager.printMessage(Diagnostic.Kind.ERROR, "Source string convert source int method more than one !");
        else elementId = (Element) ids.toArray()[0];
    }

    private void processReplace(RoundEnvironment roundEnv) {
        Set<? extends Element> replaces = roundEnv.getElementsAnnotatedWith(Replace.class);
        if (replaces.size() == 0) return;
        if (replaces.size() > 1)
            messager.printMessage(Diagnostic.Kind.ERROR, "Replace method more than one !");
        else elementReplace = (Element) replaces.toArray()[0];

    }

    private void processSwitcher(RoundEnvironment roundEnv) {
        Set<? extends Element> switchers = roundEnv.getElementsAnnotatedWith(Switcher.class);
        for (Element element : switchers) {
            Switcher switcher = element.getAnnotation(Switcher.class);
            String key = switcher.value().length() == 0 ? element.getSimpleName().toString() : switcher.value();
            String fullClass = element.getEnclosingElement().toString();
            SkinSwitcherAdapter adapter;
            if (!proxySkinSwitcher.containsKey(fullClass)) {
                adapter = new SkinSwitcherAdapter();
                adapter.setElementDefaultFilter(elementDefaultFilter);
                adapter.setElementSwitcherChange(elementSwitcherChange);
                adapter.setElementResource(elementResource);
                adapter.setElementId(elementId);
                adapter.setElementReplace(elementReplace);
                adapter.setElement((TypeElement) element.getEnclosingElement());
                proxySkinSwitcher.put(fullClass, adapter);
            } else {
                adapter = proxySkinSwitcher.get(fullClass);
            }
            adapter.setFullClass(element.getEnclosingElement().toString());
            if (!adapter.invoke(key, element))
                messager.printMessage(Diagnostic.Kind.ERROR, String.format("More than two switcher: %s", key));
        }
    }

    private void processFilter(RoundEnvironment roundEnv) {
        Set<? extends Element> filters = roundEnv.getElementsAnnotatedWith(Filter.class);
        for (Element element : filters) {
            Filter filter = element.getAnnotation(Filter.class);
            String key = filter.value().length() == 0 ? element.getSimpleName().toString() : filter.value();
            String fullClass = element.getEnclosingElement().toString();
            SkinSwitcherAdapter adapter;
            if (!proxySkinSwitcher.containsKey(fullClass)) {
                adapter = new SkinSwitcherAdapter();
                adapter.setElementDefaultFilter(elementDefaultFilter);
                adapter.setElementSwitcherChange(elementSwitcherChange);
                adapter.setElementResource(elementResource);
                adapter.setElementId(elementId);
                adapter.setElementReplace(elementReplace);
                adapter.setElement((TypeElement) element.getEnclosingElement());
                proxySkinSwitcher.put(fullClass, adapter);
            } else {
                adapter = proxySkinSwitcher.get(fullClass);
            }
            adapter.setFullClass(element.getEnclosingElement().toString());
            if (!adapter.filter(key, element))
                messager.printMessage(Diagnostic.Kind.ERROR, String.format("More than two filter: %s", key));
        }
    }


    private void process() {
        List<SkinSwitcher> adapters = new ArrayList<>();
        adapters.addAll(proxySkinSwitcher.values());
        adapters.addAll(proxyView.values());
        for (SkinSwitcher switcher : adapters) {
            try {
                JavaFileObject jfo = processingEnv.getFiler().createSourceFile(
                        switcher.getSkinSwitcherClass(),
                        switcher.getElement()
                );
                Writer writer = jfo.openWriter();
                writer.write(switcher.createSkinSwitcher(messager));
                writer.flush();
                writer.close();
            } catch (Exception ignored) {
            }
        }
    }
}
