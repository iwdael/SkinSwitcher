package com.hacknife.skinswitcher.compiler;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.BooleanLiteralExpr;
import com.github.javaparser.ast.expr.ClassExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.InstanceOfExpr;
import com.github.javaparser.ast.expr.MarkerAnnotationExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.BreakStmt;
import com.github.javaparser.ast.stmt.CatchClause;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.stmt.SwitchEntryStmt;
import com.github.javaparser.ast.stmt.SwitchStmt;
import com.github.javaparser.ast.stmt.TryStmt;
import com.github.javaparser.ast.type.TypeParameter;
import com.hacknife.skinswitcher.annotation.Method;
import com.hacknife.skinswitcher.annotation.State;
import com.hacknife.skinswitcher.annotation.SwitcherView;
import com.hacknife.skinswitcher.compiler.helper.Helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.tools.Diagnostic;

import static com.github.javaparser.JavaParser.parseExpression;
import static com.github.javaparser.ast.Modifier.PRIVATE;
import static com.github.javaparser.ast.Modifier.PROTECTED;
import static com.github.javaparser.ast.Modifier.PUBLIC;

public class SkinSwitcherView implements SkinSwitcher {
    public static final String SkinSwitcherViewAttribute = "com.hacknife.skinswitcher.SkinSwitcherViewAttribute";
    public static final String SkinSwitcherMethod = "com.hacknife.skinswitcher.SkinSwitcherMethod";
    public static final String SkinSwitcherView = "com.hacknife.skinswitcher.SkinSwitcherView";
    public static final String Map = "java.util.Map";
    public static final String HashMap = "java.util.HashMap";
    public static final String View = "android.view.View";
    public static final String Context = "android.content.Context";
    public static final String AttributeSet = "android.util.AttributeSet";
    public static final String FrameLayout = "android.widget.FrameLayout";
    private static final Parameter PARAMETER_Context = new Parameter(new TypeParameter("Context"), "context");
    private static final Parameter PARAMETER_AttributeSet = new Parameter(new TypeParameter("AttributeSet"), "attrs");
    private static final Parameter PARAMETER_defStyleAttr = new Parameter(new TypeParameter("int"), "defStyleAttr");
    private static final Parameter PARAMETER_defStyleRes = new Parameter(new TypeParameter("int"), "defStyleRes");


    private static final NodeList<Parameter> PARAMETERS_2 = new NodeList(Arrays.asList(PARAMETER_Context, PARAMETER_AttributeSet));
    private static final Expression[] PARAMETERS_2_args = new Expression[]{parseExpression("context"), parseExpression("attrs"), parseExpression("0")};

    private static final NodeList<Parameter> PARAMETERS_3 = new NodeList(Arrays.asList(PARAMETER_Context, PARAMETER_AttributeSet, PARAMETER_defStyleAttr));
    private static final Expression[] PARAMETERS_3_args = new Expression[]{parseExpression("context"), parseExpression("attrs"), parseExpression("defStyleAttr"), parseExpression("0")};

    private static final NodeList<Parameter> PARAMETERS_4 = new NodeList(Arrays.asList(PARAMETER_Context, PARAMETER_AttributeSet, PARAMETER_defStyleAttr, PARAMETER_defStyleRes));
    private static final Expression[] PARAMETERS_4_args = new Expression[]{parseExpression("context"), parseExpression("attrs"), parseExpression("defStyleAttr"), parseExpression("defStyleRes")};


    List<Element> classElements = new ArrayList<>();
    List<SkinSwitcherMethod> methods = new ArrayList<>();
    Element defaultView = null;
    private String adapterClass;
    private String clazz;
    private String _package;

    public void addClassElement(Element proxy) {
        classElements.add(proxy);
    }

    public void addMethodElement(Element proxy) {
        boolean isAdd = false;
        for (com.hacknife.skinswitcher.compiler.SkinSwitcherMethod method : methods) {
            Element element = method.getAllElement().get(0);
            if (element.asType().toString().equals(proxy.asType().toString()) &&
                    element.toString().equals(proxy.toString())) {
                method.addElement(proxy);
                isAdd = true;
                break;
            }
        }
        if (!isAdd) {
            methods.add(new SkinSwitcherMethod()
                    .setMethodId(methods.size() + 1)
                    .addElement(proxy));
        }
    }

    void setPackage(String _package) {
        this._package = _package;
    }

    public boolean contain(Element e) {
        for (Element element : classElements) {
            if (element.toString().equals(e.getEnclosingElement().toString()))
                return true;
        }
        return false;
    }

    public void generateInfo(Messager messager) {
        String proxy = null;
        for (Element element : classElements) {
            SwitcherView proxyAnnotation = element.getAnnotation(SwitcherView.class);
            if (proxy == null)
                proxy = proxyAnnotation.proxy();
            if (!proxy.equals(proxyAnnotation.proxy()))
                messager.printMessage(Diagnostic.Kind.ERROR, String.format("The difference of proxy between %s and %s", proxy, proxyAnnotation.proxy()));
            if (proxyAnnotation.defaultView())
                defaultView = element;
        }
        if (defaultView == null)
            defaultView = classElements.get(0);
        adapterClass = String.format("%s.%sSkinSwitcherView", _package, proxy);
        clazz = String.format("%sSkinSwitcherView", proxy);
    }

    @Override
    public String createSkinSwitcher(Messager messager) {
        CompilationUnit unit = new CompilationUnit();
        unit.setPackageDeclaration(_package);
        unit.addImport(Context);
        unit.addImport(Map);
        unit.addImport(HashMap);
        unit.addImport(View);
        unit.addImport(SkinSwitcherView);
        unit.addImport(SkinSwitcherMethod);
        unit.addImport(SkinSwitcherViewAttribute);
        unit.addImport(AttributeSet);
        unit.addImport(FrameLayout);
        unit.setBlockComment("Created by http://github.com/hacknife/SkinSwitcher");
        ClassOrInterfaceDeclaration clazzOrInterface = unit.addClass(clazz).addExtendedType("SkinSwitcherView");
        clazzOrInterface.setBlockComment("*\n" +
                " * author  : Hacknife\n" +
                " * e-mail  : hacknife@outlook.com\n" +
                " * github  : http://github.com/hacknife\n" +
                " * project : SkinSwitcher\n" +
                "");
        clazzOrInterface.addField("AttributeSet", "attrs", PRIVATE);
        clazzOrInterface.addField("Context", "context", PRIVATE);
        clazzOrInterface.addField("Map<Integer,SkinSwitcherMethod>", "methods", PRIVATE);


        clazzOrInterface.addConstructor(PUBLIC)
                .setParameters(PARAMETERS_2)
                .setBody(new BlockStmt().addStatement(new MethodCallExpr("this", PARAMETERS_2_args)));

        clazzOrInterface.addConstructor(PUBLIC)
                .setParameters(PARAMETERS_3)
                .setBody(new BlockStmt().addStatement(new MethodCallExpr("this", PARAMETERS_3_args)));

        clazzOrInterface.addConstructor(PUBLIC)
                .setParameters(PARAMETERS_4)
                .setBody(new BlockStmt()
                        .addStatement(new MethodCallExpr("super", PARAMETERS_4_args))
                        .addStatement(new MethodCallExpr("init", parseExpression("context"), parseExpression("attrs")))
                );

        clazzOrInterface
                .addMethod("init", PRIVATE)
                .setParameters(new NodeList(PARAMETER_Context, PARAMETER_AttributeSet))
                .setBody(
                        new BlockStmt()
                                .addStatement(
                                        new MethodCallExpr(String.format("view = new %s", defaultView.toString()), parseExpression("context"), parseExpression("attrs"))
                                )
                                .addStatement(
                                        new MethodCallExpr(String.format("LayoutParams layoutParams = new LayoutParams"), parseExpression("LayoutParams.MATCH_PARENT"), parseExpression("LayoutParams.MATCH_PARENT"))
                                )
                                .addStatement(
                                        new MethodCallExpr(String.format("addView"), parseExpression("view"), parseExpression("layoutParams"))
                                )
                                .addStatement(new NameExpr(String.format("this.attrs = attrs")))
                                .addStatement(new NameExpr(String.format("this.context = context")))
                                .addStatement(new AssignExpr(new NameExpr("methods"), new ObjectCreationExpr().setType("HashMap"), AssignExpr.Operator.ASSIGN))
                );

        clazzOrInterface.addMethod("setDefaultView", PROTECTED)
                .setParameters(new NodeList(new Parameter(new TypeParameter("Class<?>"), "defaultView")))
                .setBody(
                        new BlockStmt()
                                .addStatement(new NameExpr(String.format("View view = null")))
                                .addStatement(
                                        new TryStmt()
                                                .setTryBlock(
                                                        new BlockStmt()
                                                                .addStatement(new MethodCallExpr(String.format("view = (View) defaultView.getConstructor(Context.class, AttributeSet.class).newInstance"), parseExpression("this.context"), parseExpression("this.attrs")))

                                                ).setCatchClauses(new NodeList<>(new CatchClause(new Parameter(new TypeParameter("Exception"), "e"), new BlockStmt().addStatement(new MethodCallExpr("e.printStackTrace")))))
                                )
                                .addStatement(new IfStmt().setCondition(parseExpression("view == null")).setThenStmt(new ReturnStmt()))
                                .addStatement(new IfStmt()
                                        .setCondition(createCondition())
                                        .setThenStmt(new BlockStmt()
                                                .addStatement(new IfStmt()
                                                        .setCondition(parseExpression("view instanceof SkinSwitcherViewAttribute && this.view instanceof SkinSwitcherViewAttribute"))
                                                        .setThenStmt(new BlockStmt()
                                                                .addStatement(new MethodCallExpr("((SkinSwitcherViewAttribute) view).restoreAttribute", new MethodCallExpr("((SkinSwitcherViewAttribute) this.view).saveAttribute")))
                                                        )
                                                )
                                                .addStatement(parseExpression("this.view = view"))
                                        )
                                        .setElseStmt(new ReturnStmt())
                                )
                                .addStatement(new MethodCallExpr("removeAllViews"))
                                .addStatement(
                                        new MethodCallExpr(String.format("LayoutParams layoutParams = new LayoutParams"), parseExpression("LayoutParams.MATCH_PARENT"), parseExpression("LayoutParams.MATCH_PARENT"))
                                )
                                .addStatement(
                                        new MethodCallExpr(String.format("addView"), parseExpression("this.view"), parseExpression("layoutParams"))
                                )
                                .addStatement(new MethodCallExpr("restoreView"))
                );

        clazzOrInterface.addMethod("restore", PROTECTED)
                .setAnnotations(new NodeList<AnnotationExpr>(new MarkerAnnotationExpr("Override")))
                .setParameters(new NodeList(new Parameter(new TypeParameter("SkinSwitcherMethod"), "method")))
                .setBody(
                        new BlockStmt()
                                .addStatement(
                                        new SwitchStmt()
                                                .setSelector(new MethodCallExpr("method.getMethodId"))
                                                .setEntries(new NodeList(createRestore()))
                                )
                );

        for (int i = 0; i < methods.size(); i++) {
            com.hacknife.skinswitcher.compiler.SkinSwitcherMethod method = methods.get(i);
            clazzOrInterface.addMethod(method.getAllElement().get(0).getSimpleName().toString(), PUBLIC)
                    .setParameters(new NodeList(Helper.parameterMethod(method.getAllElement().get(0))))
                    .setBody(createMethod(method.getAllElement(), method.getMethodId()));
        }
        return unit.toString();
    }

    private Expression createCondition() {
        BinaryExpr retExpr = null;

        if (classElements.size() == 1)
            return new InstanceOfExpr(new NameExpr("view"), new TypeParameter(classElements.get(0).toString()));
        BinaryExpr binaryExpr = null;
        for (int i = 0; i < classElements.size(); i++) {

            if (retExpr == null) {
                retExpr = binaryExpr = new BinaryExpr().setLeft(new InstanceOfExpr(new NameExpr("view"), new TypeParameter(classElements.get(i).toString())));
            } else {
                binaryExpr.setOperator(BinaryExpr.Operator.OR);
                BinaryExpr expr = new BinaryExpr().setLeft(new InstanceOfExpr(new NameExpr("view"), new TypeParameter(classElements.get(i).toString())));
                binaryExpr.setRight(expr);
                binaryExpr = expr;
            }
        }
        binaryExpr.setOperator(BinaryExpr.Operator.OR).setRight(new BooleanLiteralExpr(false));
        return retExpr;
    }

    private BlockStmt createMethod(List<Element> elements, int methodId) {
        BlockStmt stmt = new BlockStmt();
        IfStmt ifStmt = null;
        for (Element element : elements) {
            if (ifStmt == null) {
                ifStmt = new IfStmt()
                        .setCondition(new InstanceOfExpr(new NameExpr("view"), new TypeParameter(element.getEnclosingElement().toString())))
                        .setThenStmt(new BlockStmt()
                                .addStatement(new MethodCallExpr(String.format("((%s) view).%s", element.getEnclosingElement().toString(), element.getSimpleName().toString()), Helper.parameterALL(element)))
                                .addStatement(
                                        new MethodCallExpr(element.getAnnotation(Method.class).value() == State.sticky ? "addStickyMethod" : "addMethod")
                                                .setArguments(new NodeList<Expression>(
                                                        new MethodCallExpr(
                                                                String.format("new SkinSwitcherMethod().setMethodId(%d).setArgs", element.getAnnotation(Method.class).value() == State.sticky ? methodId * 2 - 1 : methodId * 2),
                                                                Helper.parameterALL(element)
                                                        )
                                                ))

                                )
                        );
                stmt.addStatement(ifStmt);
            } else {
                IfStmt elseStmt = new IfStmt()
                        .setCondition(new InstanceOfExpr(new NameExpr("view"), new TypeParameter(element.getEnclosingElement().toString())))
                        .setThenStmt(new BlockStmt()
                                .addStatement(new MethodCallExpr(String.format("((%s) view).%s", element.getEnclosingElement().toString(), element.getSimpleName().toString()), Helper.parameterALL(element)))
                                .addStatement(
                                        new MethodCallExpr(element.getAnnotation(Method.class).value() == State.sticky ? "addStickyMethod" : "addMethod")
                                                .setArguments(new NodeList<Expression>(
                                                        new MethodCallExpr(
                                                                String.format("new SkinSwitcherMethod().setMethodId(%d).setArgs", element.getAnnotation(Method.class).value() == State.sticky ? methodId * 2 - 1 : methodId * 2),
                                                                Helper.parameterALL(element)
                                                        )
                                                ))

                                )
                        );
                ifStmt.setElseStmt(elseStmt);
                ifStmt = elseStmt;
            }
        }
        return stmt;
    }

    private SwitchEntryStmt[] createRestore() {
        SwitchEntryStmt[] stmts = new SwitchEntryStmt[methods.size() * 2];
        for (int i = 0; i < methods.size() * 2; i = i + 2) {

            com.hacknife.skinswitcher.compiler.SkinSwitcherMethod method = methods.get(i / 2);

            stmts[i] = new SwitchEntryStmt();
            stmts[i + 1] = new SwitchEntryStmt();


            stmts[i].setLabel(new NameExpr(String.valueOf(method.getMethodId() * 2 - 1)));
            IfStmt ifStmtSticky = null;
            for (Element stickyElement : method.getStickyElements()) {
                if (ifStmtSticky == null) {
                    ifStmtSticky = new IfStmt().setCondition(new InstanceOfExpr(new NameExpr("view"), new TypeParameter(stickyElement.getEnclosingElement().toString())))
                            .setThenStmt(
                                    new BlockStmt()
                                            .addStatement(
                                                    new MethodCallExpr(String.format("((%s) view).%s", stickyElement.getEnclosingElement().toString(), stickyElement.getSimpleName().toString()))
                                                            .setArguments(new NodeList(Helper.parameterMethodCast(stickyElement)))
                                            )
                            );
                    stmts[i].addStatement(ifStmtSticky);
                } else {
                    IfStmt stmt = new IfStmt().setCondition(new InstanceOfExpr(new NameExpr("view"), new TypeParameter(stickyElement.getEnclosingElement().toString())))
                            .setThenStmt(
                                    new BlockStmt()
                                            .addStatement(
                                                    new MethodCallExpr(String.format("((%s) view).%s", stickyElement.getEnclosingElement().toString(), stickyElement.getSimpleName().toString()))
                                                            .setArguments(new NodeList(Helper.parameterMethodCast(stickyElement)))
                                            )
                            );
                    ifStmtSticky.setElseStmt(stmt);
                    ifStmtSticky = stmt;
                }
            }

            stmts[i + 1].setLabel(new NameExpr(String.valueOf(method.getMethodId() * 2)));
            IfStmt ifStmtOnce = null;
            for (Element stickyElement : method.getElements()) {
                if (ifStmtOnce == null) {
                    ifStmtOnce = new IfStmt().setCondition(new InstanceOfExpr(new NameExpr("view"), new TypeParameter(stickyElement.getEnclosingElement().toString())))
                            .setThenStmt(
                                    new BlockStmt()
                                            .addStatement(
                                                    new MethodCallExpr(String.format("((%s) view).%s", stickyElement.getEnclosingElement().toString(), stickyElement.getSimpleName().toString()))
                                                            .setArguments(new NodeList(Helper.parameterMethodCast(stickyElement)))
                                            )
                            );
                    stmts[i + 1].addStatement(ifStmtOnce);
                } else {
                    IfStmt stmt = new IfStmt().setCondition(new InstanceOfExpr(new NameExpr("view"), new TypeParameter(stickyElement.getEnclosingElement().toString())))
                            .setThenStmt(
                                    new BlockStmt()
                                            .addStatement(
                                                    new MethodCallExpr(String.format("((%s) view).%s", stickyElement.getEnclosingElement().toString(), stickyElement.getSimpleName().toString()))
                                                            .setArguments(new NodeList(Helper.parameterMethodCast(stickyElement)))
                                            )
                            );
                    ifStmtOnce.setElseStmt(stmt);
                    ifStmtOnce = stmt;
                }
            }

            stmts[i + 1].addStatement(new BreakStmt().setLabel(new SimpleName(" ")));
        }
        return stmts;
    }


    public String getSkinSwitcherClass() {
        return adapterClass;
    }

    @Override
    public Element getElement() {
        return classElements.get(0);
    }


}
