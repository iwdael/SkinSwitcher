package com.hacknife.skinswitcher.compiler;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.BooleanLiteralExpr;
import com.github.javaparser.ast.expr.EnclosedExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.InstanceOfExpr;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.NullLiteralExpr;
import com.github.javaparser.ast.expr.UnaryExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.type.TypeParameter;
import com.hacknife.skinswitcher.annotation.Target;
import com.hacknife.skinswitcher.compiler.helper.Helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

import static com.github.javaparser.JavaParser.parseExpression;
import static com.github.javaparser.ast.expr.BinaryExpr.Operator.EQUALS;
import static com.github.javaparser.ast.expr.UnaryExpr.Operator.LOGICAL_COMPLEMENT;
import static com.hacknife.skinswitcher.compiler.helper.Helper.parameter;

/**
 * author  : Hacknife
 * e-mail  : hacknife@outlook.com
 * github  : http://github.com/hacknife
 * project : SkinSwitcher
 */
public class SkinSwitcherAdapter implements SkinSwitcher {

    private static final String TYPE = "com.hacknife.skinswitcher.entity.Type";
    private static final String VIEW = "android.view.View";
    private static final String FULL_SKIN_SWITCHER_ADAPTER = "com.hacknife.skinswitcher.SkinSwitcherAdapter";
    private static final String SKIN_SWITCHER_ADAPTER = "SkinSwitcherAdapter";
    private static final String FILTER = "filter";
    private static final String SWITCHER = "switcher";
    private static final Parameter PARAMETER_VIEW = new Parameter(new TypeParameter("View"), "view");
    private static final Parameter PARAMETER_NAME = new Parameter(new TypeParameter("String"), "name");
    private static final Parameter PARAMETER_ATTR = new Parameter(new TypeParameter("String"), "attr");
    private static final Parameter PARAMETER_VALUE = new Parameter(new TypeParameter("String"), "value");
    private static final Parameter PARAMETER_VALUE_ORIGINAL = new Parameter(new TypeParameter("String"), "originalValue");
    private static final Parameter PARAMETER_TYPE = new Parameter(new TypeParameter("Type"), "type");
    private static final NodeList<Parameter> PARAMETERS_FILTER = new NodeList(Arrays.asList(PARAMETER_VIEW, PARAMETER_NAME, PARAMETER_ATTR, PARAMETER_VALUE, PARAMETER_TYPE));
    private static final NodeList<Parameter> PARAMETERS_SWITCHER = new NodeList(Arrays.asList(PARAMETER_VIEW, PARAMETER_NAME, PARAMETER_ATTR, PARAMETER_VALUE, PARAMETER_TYPE));
    private static final NodeList<Parameter> PARAMETERS_SWITCHER_ORIGINAL = new NodeList(Arrays.asList(PARAMETER_VIEW, PARAMETER_NAME, PARAMETER_ATTR, PARAMETER_VALUE_ORIGINAL, PARAMETER_TYPE));

    private String fullClass;
    private String clazz;
    private String adapterClass;
    private Map<String, Element> invoke;
    private Map<String, Element> filter;
    private TypeElement element;
    private String _package;
    private Element elementId;
    private Element elementReplace;
    private Element elementResource;
    private Element elementDefaultFilter;

    SkinSwitcherAdapter() {
        invoke = new HashMap<>();
        filter = new HashMap<>();
    }

    public void setElementResource(Element elementResource) {
        this.elementResource = elementResource;
    }

    public void setElementDefaultFilter(Element elementDefaultFilter) {
        this.elementDefaultFilter = elementDefaultFilter;
    }

    public void setElementId(Element elementId) {
        this.elementId = elementId;
    }

    public void setElementReplace(Element elementReplace) {
        this.elementReplace = elementReplace;
    }

    boolean filter(String k, Element v) {
        if (this.filter.containsKey(k)) {
            return false;
        } else {
            this.filter.put(k, v);
            return true;
        }
    }

    void setFullClass(String fullClass) {
        this.fullClass = fullClass;
        this.adapterClass = fullClass + "Adapter";
        this._package = fullClass.substring(0, fullClass.lastIndexOf("."));
        this.clazz = adapterClass.substring(adapterClass.lastIndexOf(".") + 1);
    }

    public String getSkinSwitcherClass() {
        return adapterClass;
    }

    boolean invoke(String k, Element v) {
        if (this.invoke.containsKey(k)) {
            return false;
        } else {
            this.invoke.put(k, v);
            return true;
        }
    }

    public TypeElement getElement() {
        return element;
    }

    void setElement(TypeElement element) {
        this.element = element;
    }

    public String createSkinSwitcher(Messager messager) {
        CompilationUnit unit = new CompilationUnit();
        unit.setPackageDeclaration(_package);
        unit.addImport(TYPE);
        unit.addImport(FULL_SKIN_SWITCHER_ADAPTER);
        unit.addImport(VIEW);
        unit.setBlockComment("Created by http://github.com/hacknife/SkinSwitcher");
        ClassOrInterfaceDeclaration clazzOrInterface = unit.addClass(clazz);
        clazzOrInterface.addImplementedType(SKIN_SWITCHER_ADAPTER);
        checkFilterSwitcher(messager);
        clazzOrInterface.setBlockComment("*\n" +
                " * author  : Hacknife\n" +
                " * e-mail  : hacknife@outlook.com\n" +
                " * github  : http://github.com/hacknife\n" +
                " * project : SkinSwitcher\n" +
                "");
        clazzOrInterface
                .addMethod(FILTER)
                .setParameters(PARAMETERS_FILTER)
                .setPublic(true)
                .setType(boolean.class)
//                .addAnnotation(Override.class)
                .setBody(createFilter());
        clazzOrInterface
                .addMethod(SWITCHER)
                .setParameters(elementReplace == null ? PARAMETERS_SWITCHER : PARAMETERS_SWITCHER_ORIGINAL)
                .setPublic(true)
                .setType(boolean.class)
//                .addAnnotation(Override.class)
                .setBody(createSwitcher());
        return unit.toString();
    }

    private void checkFilterSwitcher(Messager messager) {
        List<String> keys = new ArrayList<>();
        for (Map.Entry<String, Element> entry : filter.entrySet()) {
            if (!invoke.containsKey(entry.getKey())) {
                keys.add(entry.getKey());
                messager.printMessage(Diagnostic.Kind.ERROR, String.format("No matching to corresponding switcher, keywords or methods :%s", entry.getKey()));
            }
        }
        for (String key : keys) filter.remove(key);
        keys.clear();
        for (Map.Entry<String, Element> entry : invoke.entrySet()) {
            if (!filter.containsKey(entry.getKey())) {
                keys.add(entry.getKey());
                messager.printMessage(Diagnostic.Kind.ERROR, String.format("No matching to corresponding filter, keywords or methods :%s", entry.getKey()));
            }
        }
        for (String key : keys) invoke.remove(key);
        keys.clear();
    }

    private BlockStmt createSwitcher() {
        BlockStmt blockStmt = new BlockStmt();

        if (elementDefaultFilter != null) {
            blockStmt.addStatement(new IfStmt().setCondition(new UnaryExpr(new MethodCallExpr(String.format("%s.%s", elementDefaultFilter.getEnclosingElement().toString(), elementDefaultFilter.getSimpleName().toString()), parameter(elementDefaultFilter, elementReplace != null)), LOGICAL_COMPLEMENT)).setThenStmt(new ReturnStmt(new BooleanLiteralExpr(false))));
        }
        if (Helper.annotationVal(element.getAnnotation(Target.class)) != null)
            blockStmt.addStatement(new IfStmt().setCondition(new UnaryExpr(new EnclosedExpr(new InstanceOfExpr().setExpression("view").setType(Helper.annotationVal(element.getAnnotation(Target.class)))), LOGICAL_COMPLEMENT)).setThenStmt(new ReturnStmt(new BooleanLiteralExpr(false))));


        if (elementReplace != null) {
            blockStmt.addStatement(new MethodCallExpr(String.format("String value = %s.%s", elementReplace.getEnclosingElement().toString(), elementReplace.getSimpleName().toString()), parameter(elementReplace)));
            blockStmt.addStatement(new IfStmt().setCondition(new MethodCallExpr("value.equalsIgnoreCase",parseExpression("originalValue"))).setThenStmt( new ReturnStmt().setExpression(new BooleanLiteralExpr(true)) ));
        }

        if (elementId != null) {
            blockStmt.addStatement(new MethodCallExpr(String.format("int id = %s.%s", elementId.getEnclosingElement().toString(), elementId.getSimpleName().toString()), parameter(elementId)))
                    .addStatement(new IfStmt().setCondition(new BinaryExpr(new IntegerLiteralExpr("id"), new IntegerLiteralExpr(0), EQUALS)).setThenStmt(new ReturnStmt(new BooleanLiteralExpr(true))));
        }

        if (elementResource != null) {
            blockStmt.addStatement(new MethodCallExpr(String.format("Object resource = %s.%s", elementResource.getEnclosingElement().toString(), elementResource.getSimpleName().toString()), parameter(elementResource)))
                    .addStatement(new IfStmt().setCondition(new BinaryExpr(new IntegerLiteralExpr("resource"), new NullLiteralExpr(), EQUALS)).setThenStmt(new ReturnStmt(new BooleanLiteralExpr(true))));

        }

        IfStmt ifStmt = null;
        for (Map.Entry<String, Element> entry : filter.entrySet()) {
            if (ifStmt == null) {
                ifStmt = new IfStmt().setCondition(new MethodCallExpr(String.format("%s.%s", fullClass, entry.getValue().getSimpleName().toString()), parameter(entry.getValue(), elementReplace != null)))
                        .setThenStmt(new BlockStmt().addStatement(new MethodCallExpr(String.format("%s.%s", fullClass, invoke.get(entry.getKey()).getSimpleName().toString()), parameter(invoke.get(entry.getKey())))).addStatement(new ReturnStmt(new BooleanLiteralExpr(true))));
                blockStmt.addStatement(ifStmt);
            } else {
                IfStmt stmt = new IfStmt().setCondition(new MethodCallExpr(String.format("%s.%s", fullClass, entry.getValue().getSimpleName().toString()), parameter(entry.getValue(), elementReplace != null)))
                        .setThenStmt(new BlockStmt().addStatement(new MethodCallExpr(String.format("%s.%s", fullClass, invoke.get(entry.getKey()).getSimpleName().toString()), parameter(invoke.get(entry.getKey())))).addStatement(new ReturnStmt(new BooleanLiteralExpr(true))));
                ifStmt.setElseStmt(stmt);
                ifStmt = stmt;
            }
        }
        ifStmt.setElseStmt(new ReturnStmt(new BooleanLiteralExpr(false)));
        return blockStmt;
    }

    private BlockStmt createFilter() {
        BlockStmt blockStmt = new BlockStmt();

        if (elementDefaultFilter != null) {
            blockStmt.addStatement(new IfStmt().setCondition(new UnaryExpr(new MethodCallExpr(String.format("%s.%s", elementDefaultFilter.getEnclosingElement().toString(), elementDefaultFilter.getSimpleName().toString()), parameter(elementDefaultFilter)), LOGICAL_COMPLEMENT)).setThenStmt(new ReturnStmt(new BooleanLiteralExpr(false))));
        }
        if (Helper.annotationVal(element.getAnnotation(Target.class)) != null) {
            blockStmt.addStatement(new IfStmt().setCondition(new UnaryExpr(new EnclosedExpr(new InstanceOfExpr().setExpression("view").setType(Helper.annotationVal(element.getAnnotation(Target.class)))), LOGICAL_COMPLEMENT)).setThenStmt(new ReturnStmt(new BooleanLiteralExpr(false))));
        }
        IfStmt ifStmt = null;
        for (Map.Entry<String, Element> entry : filter.entrySet()) {
            if (ifStmt == null) {
                ifStmt = new IfStmt().setCondition(new MethodCallExpr(String.format("%s.%s", fullClass, entry.getValue().getSimpleName().toString()), parameter(entry.getValue()))).setThenStmt(new ReturnStmt(new BooleanLiteralExpr(true)));
                blockStmt.addStatement(ifStmt);
            } else {
                IfStmt stmt = new IfStmt().setCondition(new MethodCallExpr(String.format("%s.%s", fullClass, entry.getValue().getSimpleName().toString()), parameter(entry.getValue()))).setThenStmt(new ReturnStmt(new BooleanLiteralExpr(true)));
                ifStmt.setElseStmt(stmt);
                ifStmt = stmt;
            }
        }
        ifStmt.setElseStmt(new ReturnStmt(new BooleanLiteralExpr(false)));
        return blockStmt;
    }

    @Override
    public String toString() {
        return "{" +
                "\"fullClass\":\'" + fullClass + "\'" +
                ", \"clazz\":\'" + clazz + "\'" +
                ", \"adapterClass\":\'" + adapterClass + "\'" +
                ", \"invoke\":" + invoke +
                ", \"filter\":" + filter +
                ", \"element\":" + element +
                ", \"_package\":\'" + _package + "\'" +
                '}';
    }

}
