package com.hacknife.skinswitcher.compiler;

import com.google.auto.service.AutoService;
import com.hacknife.skinswitcher.annotation.Filter;
import com.hacknife.skinswitcher.annotation.Switcher;
import java.io.Writer;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

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
    protected Elements elementUtils;
    protected Map<String, SkinSwitcher> proxyMap = new LinkedHashMap<>();

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        HashSet<String> supportType = new LinkedHashSet<>();
        supportType.add(Switcher.class.getCanonicalName());
        supportType.add(Filter.class.getCanonicalName());
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
        elementUtils = processingEnv.getElementUtils();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        proxyMap.clear();
        processFilter(annotations, roundEnv);
        processSwitcher(annotations, roundEnv);
        process();
        return true;
    }

    private void processSwitcher(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> switchers = roundEnv.getElementsAnnotatedWith(Switcher.class);
        for (Element element : switchers) {
            Switcher switcher = element.getAnnotation(Switcher.class);
            String key = switcher.value().length() == 0 ? element.getSimpleName().toString() : switcher.value();
            String fullClass = element.getEnclosingElement().toString();
            SkinSwitcher adapter;
            if (!proxyMap.containsKey(fullClass)) {
                adapter = new SkinSwitcher();
                adapter.setElement((TypeElement) element.getEnclosingElement());
                proxyMap.put(fullClass, adapter);
            } else {
                adapter = proxyMap.get(fullClass);
            }
            adapter.setFullClass(element.getEnclosingElement().toString());
            if (!adapter.invoke(key, element))
                messager.printMessage(Diagnostic.Kind.ERROR, String.format("More than two switcher: %s", key));
        }
    }

    private void processFilter(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> filters = roundEnv.getElementsAnnotatedWith(Filter.class);
        for (Element element : filters) {
            Filter filter = element.getAnnotation(Filter.class);
            String key = filter.value().length() == 0 ? element.getSimpleName().toString() : filter.value();
            String fullClass = element.getEnclosingElement().toString();
            SkinSwitcher adapter;
            if (!proxyMap.containsKey(fullClass)) {
                adapter = new SkinSwitcher();
                adapter.setElement((TypeElement) element.getEnclosingElement());
                proxyMap.put(fullClass, adapter);
            } else {
                adapter = proxyMap.get(fullClass);
            }
            adapter.setFullClass(element.getEnclosingElement().toString());
            if (!adapter.filter(key, element))
                messager.printMessage(Diagnostic.Kind.ERROR, String.format("More than two filter: %s", key));

        }
    }


    private void process() {
        for (String key : proxyMap.keySet()) {
            try {
                SkinSwitcher switcher = proxyMap.get(key);
                JavaFileObject jfo = processingEnv.getFiler().createSourceFile(
                        switcher.getAdapterClass(),
                        switcher.getElement()
                );
                Writer writer = jfo.openWriter();
                writer.write(switcher.createAdapter(messager));
                writer.flush();
                writer.close();
            } catch (Exception ignored) {
            }
        }
    }
}
