package com.hacknife.skinswitcher.compiler.helper;

import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.CastExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.type.TypeParameter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;

import static com.github.javaparser.JavaParser.parseExpression;

/**
 * author  : Hacknife
 * e-mail  : hacknife@outlook.com
 * github  : http://github.com/hacknife
 * project : SkinSwitcher
 */
public class Helper {
    public static Expression[] parameter(Element element) {
        return parameter(element, false);
    }

    public static Expression[] parameter(Element element, boolean isOriginal) {
        try {
            Method parameterFile = element.getClass().getDeclaredMethod("getParameters");
            String[] parameters = parameterFile.invoke(element).toString().split(",");
            Expression[] parameterExpression = new Expression[parameters.length];
            for (int i = 0; i < parameters.length; i++) {
                if (parameters[i].equals("value")) {
                    if (isOriginal) {
                        parameterExpression[i] = parseExpression("originalValue");
                    } else {
                        parameterExpression[i] = parseExpression(parameters[i]);
                    }
                } else if (parameters[i].equals("resource")) {
                    parameterExpression[i] = new CastExpr(new TypeParameter(typeArguments(element.asType())[i]), parseExpression(parameters[i]));
                } else if (parameters[i].equals("view")) {
                    parameterExpression[i] = new CastExpr(new TypeParameter(typeArguments(element.asType())[i]), parseExpression(parameters[i]));
                } else {
                    parameterExpression[i] = parseExpression(parameters[i]);
                }
            }
            return parameterExpression;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Expression[]{};
    }

    public static Expression[] parameterMethodCast(Element element) {
        try {
            Method parameterFile = element.getClass().getDeclaredMethod("getParameters");
            String[] parameters = parameterFile.invoke(element).toString().split(",");
            Expression[] parameterExpression = new Expression[parameters.length];
            for (int i = 0; i < parameters.length; i++) {
                parameterExpression[i] = new CastExpr(new TypeParameter(typeArguments(element.asType())[i]), parseExpression(String.format("method.getArgs()[%d]", i)));
            }
            return parameterExpression;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Expression[]{};
    }

    public static <T extends Annotation> String annotationVal(T annotation) {
        if (annotation == null) return null;
        return annotation.toString().substring(annotation.toString().indexOf("=") + 1, annotation.toString().indexOf(")"));
    }


    public static String[] typeArguments(TypeMirror type) {
        try {
            Field field = type.getClass().getDeclaredField("argtypes");
            field.setAccessible(true);
            return field.get(type).toString().split(",");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String[0];
    }

    public static Parameter[] parameterMethod(Element element) {
        try {
            Method parameterFile = element.getClass().getDeclaredMethod("getParameters");
            String[] parameters = parameterFile.invoke(element).toString().split(",");
            Parameter[] parameterExpression = new Parameter[parameters.length];
            for (int i = 0; i < parameters.length; i++) {
                parameterExpression[i] = new Parameter(new TypeParameter(typeArguments(element.asType())[i]), parameters[i]);
            }
            return parameterExpression;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Parameter[]{};
    }

    public static Expression[] parameterALL(Element element) {
        try {
            Method parameterFile = element.getClass().getDeclaredMethod("getParameters");
            String[] parameters = parameterFile.invoke(element).toString().split(",");
            Expression[] parameterExpression = new Expression[parameters.length];
            for (int i = 0; i < parameters.length; i++)
                parameterExpression[i] = parseExpression(parameters[i]);
            return parameterExpression;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Expression[]{};
    }
    public static String[] parameterALLStr(Element element) {
        try {
            Method parameterFile = element.getClass().getDeclaredMethod("getParameters");
            String[] parameters = parameterFile.invoke(element).toString().split(",");

            return parameters;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String[]{};
    }
}
