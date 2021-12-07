package com.iwdael.dblite;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.ArrayAccessExpr;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.github.javaparser.ast.expr.MarkerAnnotationExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.TypeParameter;
import com.iwdael.dblite.annotation.AutoInc;
import com.iwdael.dblite.annotation.Column;
import com.iwdael.dblite.annotation.NotNull;
import com.iwdael.dblite.annotation.Unique;
import com.iwdael.dblite.util.DbLiteHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;

import static com.github.javaparser.ast.expr.AssignExpr.Operator.ASSIGN;
import static com.github.javaparser.ast.expr.BinaryExpr.Operator.EQUALS;
import static com.github.javaparser.ast.expr.BinaryExpr.Operator.NOT_EQUALS;
import static com.github.javaparser.ast.expr.BinaryExpr.Operator.OR;

/**
 * author  : iwdael
 * e-mail  : iwdael@outlook.com
 * github  : http://github.com/iwdael
 * project : DbLite
 */
public class DbLite {

    private static final String LITE = "DbLite";
    private static final String FULL_LITE = "com.iwdael.dblite.DbLite";
    private static final String FULL_CURSOR = "android.database.Cursor";
    private static final String CURSOR = "Cursor";
    private static final String FULL_CONTENT_VALUES = "android.content.ContentValues";
    private static final String CONTENT_VALUES = "ContentValues";
    private static final String STRING_BUILDER = "StringBuilder";
    private static final String LIST_STRING = "List<String>";
    private static final String LIST_ARRAY = "ArrayList";
    private static final String FULL_LIST = "java.util.List";
    private static final String FULL_LIST_ARRAY = "java.util.ArrayList";

    private String fullClass;
    private String tableName;
    private List<Element> elements = new ArrayList<>();
    private List<Element> converts = new ArrayList<>();
    private int version;
    private Element element;
    private String _package;
    private String classLite;
    private String fullClassLite;
    private String clazz;
    private String clazzVar;
    private boolean targetIsKotlin = false;

    public void setFullClass(String fullClass) {
        this.fullClass = fullClass;
        this.clazz = fullClass.substring(fullClass.lastIndexOf(".") + 1);
        this.clazzVar = Character.toLowerCase(this.clazz.charAt(0)) + this.clazz.substring(1);
        this.fullClassLite = fullClass + "Lite";
        this.classLite = fullClassLite.substring(fullClassLite.lastIndexOf(".") + 1);
        this._package = fullClass.substring(0, fullClass.lastIndexOf("."));
    }

    public void setTableName(String tableName) {
        this.tableName = Character.toLowerCase(tableName.charAt(0)) + tableName.substring(1);
    }

    public void addElement(Element element) {
        this.elements.add(element);
    }

    public void addConvert(Element element) {
        this.converts.add(element);
    }

    public void setVersion(int value) {
        this.version = value;
    }

    public String getDbLiteClass() {
        return fullClassLite;
    }

    public void setElement(Element element) {
        this.element = element;
        List list = this.element.getAnnotationMirrors();
        for (Object o : list) {
            if (o.toString().contains("@kotlin.Metadata")) this.targetIsKotlin = true;
        }
    }

    public Element getElement() {
        return element;
    }

    public Map<String, List<Element>> fields() {
        Map<String, List<Element>> map = new HashMap<>();
        for (Element element : elements) {
            String field = element.toString();
            List<Element> fieldE = map.get(field);
            if (fieldE == null) {
                fieldE = new ArrayList<>();
                map.put(field, fieldE);
            }
            fieldE.add(element);
        }
        return map;
    }

    @Override
    public String toString() {
        return "{" +
                "\"fullClass\":\'" + fullClass + "\'" +
                ", \"tableName\":\'" + tableName + "\'" +
                ", \"elements\":" + elements +
                ", \"version\":" + version +
                ", \"filed\":" + fields() +
                '}';
    }


    public String createLite(Messager messager) {
        CompilationUnit unit = new CompilationUnit();
        unit.setPackageDeclaration(_package);
        unit.addImport(FULL_LITE);
        unit.addImport(fullClass);
        unit.addImport(FULL_CURSOR);
        unit.addImport(FULL_LIST);
        unit.addImport(FULL_LIST_ARRAY);
        unit.addImport(FULL_CONTENT_VALUES);
        unit.setBlockComment("Created by http://github.com/iwdael/DbLite");
        ClassOrInterfaceDeclaration clazzOrInterface = unit.addClass(classLite);
        clazzOrInterface.addExtendedType(new ClassOrInterfaceType().setName(LITE).setTypeArguments(new TypeParameter().setName(clazz)));
        clazzOrInterface.setBlockComment("*\n" +
                " * author  : iwdael\n" +
                " * e-mail  : iwdael@outlook.com\n" +
                " * github  : http://github.com/iwdael\n" +
                " * project : DbLite\n" +
                " ");


        createVariables(clazzOrInterface);

        clazzOrInterface.addConstructor(Modifier.PUBLIC)
                .setBody(createConstructor());


        clazzOrInterface.addMethod("createTable", Modifier.PROTECTED)
                .setType(String.class)
                .setAnnotations(NodeList.nodeList(new MarkerAnnotationExpr("Override")))
                .setBody(createTable());

        clazzOrInterface.addMethod("createObject", Modifier.PROTECTED)
                .setType(clazz)
                .setAnnotations(NodeList.nodeList(new MarkerAnnotationExpr("Override")))
                .setParameters(new NodeList(Arrays.asList(new Parameter(new TypeParameter(CURSOR), "cursor"))))
                .setBody(createObject());

        clazzOrInterface.addMethod("createContentValues", Modifier.PROTECTED)
                .setType(CONTENT_VALUES)
                .setAnnotations(NodeList.nodeList(new MarkerAnnotationExpr("Override")))
                .setParameters(new NodeList(Arrays.asList(new Parameter(new TypeParameter(clazz), clazzVar))))
                .setBody(createContentValues());

        clazzOrInterface.addMethod("createSelection", Modifier.PROTECTED)
                .setType(String.class)
                .setAnnotations(NodeList.nodeList(new MarkerAnnotationExpr("Override")))
                .setParameters(new NodeList(Arrays.asList(new Parameter(new TypeParameter(clazz), "where"))))
                .setBody(createSelection());

        clazzOrInterface.addMethod("createSelectionArgv", Modifier.PROTECTED)
                .setType(String[].class)
                .setAnnotations(NodeList.nodeList(new MarkerAnnotationExpr("Override")))
                .setParameters(new NodeList(Arrays.asList(new Parameter(new TypeParameter(clazz), "where"))))
                .setBody(createSelectionArgv());

        return unit.toString();
    }

    private void createVariables(ClassOrInterfaceDeclaration declaration) {
        declaration
                .addFieldWithInitializer(String.class, "TABLE_NAME", new StringLiteralExpr(tableName), Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL);
        declaration
                .addFieldWithInitializer(int.class, "VERSION", new IntegerLiteralExpr(version), Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL);

        List<List<Element>> lists = new ArrayList<>(fields().values());

        for (int i = 0; i < lists.size(); i++) {
            List<Element> list = lists.get(i);
            Column columnAnnotation = null;
            for (Element element : list) {
                if (element.getAnnotation(Column.class) != null)
                    columnAnnotation = element.getAnnotation(Column.class);
            }

            String name = columnAnnotation != null ? (columnAnnotation.name().length() == 0 ? list.get(0).toString() : columnAnnotation.name()) : list.get(0).toString();
            declaration
                    .addFieldWithInitializer(String.class, list.get(0).toString(), new StringLiteralExpr(name), Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL);

        }
    }

    private BlockStmt createSelectionArgv() {
        BlockStmt stmt = new BlockStmt();
        stmt.addStatement(new IfStmt()
                .setCondition(new BinaryExpr(new NameExpr("where"), new NameExpr("null"), EQUALS))
                .setThenStmt(new ReturnStmt().setExpression(new NameExpr("null")))
        );
        stmt.addStatement(new AssignExpr(new VariableDeclarationExpr(new TypeParameter(LIST_STRING), "list"), new ObjectCreationExpr(null, null, new ClassOrInterfaceType().setName(LIST_ARRAY), new NodeList<>(), new NodeList<>(), null), ASSIGN));

        List<List<Element>> listElement = new ArrayList<>(fields().values());
        if (listElement.size() == 0) return stmt;
        for (int i = 0; i < listElement.size(); i++) {
            List<Element> elements = listElement.get(i);
            String field = elements.get(0).toString();
            String col = null;
            for (Element element : elements) {
                if (element.getAnnotation(Column.class) != null) {
                    col = element.getAnnotation(Column.class).name();
                }
            }
            if (col == null || col.length() == 0) col = field;
            MethodCallExpr strMethod = new MethodCallExpr("String.valueOf", new MethodCallExpr(String.format("where.%s", DbLiteHelper.sql2Get(targetIsKotlin, field, elements.get(0).asType().toString()))));
            if (DbLiteHelper.isFieldOther(elements.get(0).asType().toString()))
                strMethod = new MethodCallExpr(String.format("where.%s", DbLiteHelper.sql2Get(targetIsKotlin, field, elements.get(0).asType().toString())));
            for (Element convert : converts) {
                if (!(Objects.equals(DbLiteHelper.argtypes(convert), elements.get(0).asType().toString()) && (Objects.equals(DbLiteHelper.restype(convert), String.class.getName()))))
                    continue;
                strMethod = new MethodCallExpr(String.format("%s.%s", convert.getEnclosingElement().toString(), convert.getSimpleName().toString()), new MethodCallExpr(String.format("where.%s", DbLiteHelper.sql2Get(targetIsKotlin, field, elements.get(0).asType().toString()))));
            }

            stmt.addStatement(new IfStmt()
                    .setCondition(new BinaryExpr(new MethodCallExpr(String.format("where.%s", DbLiteHelper.sql2Get(targetIsKotlin, field, elements.get(0).asType().toString()))), new NameExpr("null"), NOT_EQUALS))
                    .setThenStmt(new ExpressionStmt().setExpression(new MethodCallExpr("list.add", strMethod)))
            );
        }

        stmt.addStatement(new ReturnStmt().setExpression(new MethodCallExpr("list.toArray", new ArrayAccessExpr(new NameExpr("new " + String.class.getSimpleName()), new IntegerLiteralExpr(0)))));
        return stmt;
    }

    private BlockStmt createSelection() {
        BlockStmt stmt = new BlockStmt();
        stmt.addStatement(new IfStmt()
                .setCondition(new BinaryExpr(new NameExpr("where"), new NameExpr("null"), EQUALS))
                .setThenStmt(new ReturnStmt().setExpression(new NameExpr("null")))
        );
        stmt.addStatement(new AssignExpr(new VariableDeclarationExpr(new TypeParameter(STRING_BUILDER), "builder"), new ObjectCreationExpr(null, null, new ClassOrInterfaceType().setName(STRING_BUILDER), new NodeList<>(), new NodeList<>(), null), ASSIGN));
        stmt.addStatement(new MethodCallExpr("builder.append", new StringLiteralExpr("1 = 1 ")));

        List<List<Element>> listElement = new ArrayList<>(fields().values());
        if (listElement.size() == 0) return stmt;
        for (int i = 0; i < listElement.size(); i++) {
            List<Element> elements = listElement.get(i);
            String field = elements.get(0).toString();
            String col = null;
            for (Element element : elements) {
                if (element.getAnnotation(Column.class) != null) {
                    col = element.getAnnotation(Column.class).name();
                }
            }
            if (col == null || col.length() == 0) col = field;
            stmt.addStatement(new IfStmt()
                    .setCondition(new BinaryExpr(new MethodCallExpr(String.format("where.%s", DbLiteHelper.sql2Get(targetIsKotlin, field, elements.get(0).asType().toString()))), new NameExpr("null"), NOT_EQUALS))
                    .setThenStmt(new ExpressionStmt(new MethodCallExpr("builder.append", new StringLiteralExpr(String.format("and %s = ? ", col)))))
            );
        }

        stmt.addStatement(new ReturnStmt().setExpression(new MethodCallExpr("builder.toString")));
        return stmt;
    }

    private BlockStmt createContentValues() {
        BlockStmt stmt = new BlockStmt();
        stmt.addStatement(new IfStmt().setCondition(new BinaryExpr(new NameExpr(clazzVar), new NameExpr("null"), EQUALS)).setThenStmt(new ReturnStmt().setExpression(new NameExpr("null"))));
        stmt.addStatement(new AssignExpr(new VariableDeclarationExpr(new TypeParameter(CONTENT_VALUES), "values"), new ObjectCreationExpr(null, null, new ClassOrInterfaceType().setName(CONTENT_VALUES), new NodeList<>(), new NodeList<>(), null), ASSIGN));
        List<List<Element>> listElement = new ArrayList<>(fields().values());
        if (listElement.size() == 0) return stmt;
        for (int i = 0; i < listElement.size(); i++) {
            List<Element> elements = listElement.get(i);
            String field = elements.get(0).toString();
            String col = null;
            for (Element element : elements) {
                if (element.getAnnotation(Column.class) != null) {
                    col = element.getAnnotation(Column.class).name();
                }
            }
            if (col == null || col.length() == 0) col = field;
            MethodCallExpr valueMethod = new MethodCallExpr(String.format("%s.%s", clazzVar, DbLiteHelper.sql2Get(targetIsKotlin, field, elements.get(0).asType().toString())));
            for (Element convert : converts) {
                if (!(Objects.equals(DbLiteHelper.argtypes(convert), elements.get(0).asType().toString()) && (Objects.equals(DbLiteHelper.restype(convert), String.class.getName()))))
                    continue;
                valueMethod = new MethodCallExpr(String.format("%s.%s", convert.getEnclosingElement().toString(), convert.getSimpleName().toString()))
                        .setArguments(new NodeList(valueMethod));
            }
            stmt.addStatement(new IfStmt()
                    .setCondition(new BinaryExpr(new MethodCallExpr(String.format("%s.%s", clazzVar, DbLiteHelper.sql2Get(targetIsKotlin, field, elements.get(0).asType().toString()))), new NameExpr("null"), NOT_EQUALS))
                    .setThenStmt(new ExpressionStmt(new MethodCallExpr("values.put", new StringLiteralExpr(col), valueMethod)))
            );
        }
        stmt.addStatement(new ReturnStmt().setExpression(new NameExpr("values")));
        return stmt;
    }

    private BlockStmt createObject() {
        BlockStmt stmt = new BlockStmt();
        stmt.addStatement(new AssignExpr(new VariableDeclarationExpr(new TypeParameter(clazz), clazzVar),
                new ObjectCreationExpr(null, null, new ClassOrInterfaceType().setName(clazz), new NodeList<>(), new NodeList<>(), null), ASSIGN));
        List<List<Element>> listElement = new ArrayList<>(fields().values());

        for (int i = 0; i < listElement.size(); i++) {
            List<Element> elements = listElement.get(i);
            String field = elements.get(0).toString();
            String col = null;
            for (Element element : elements) {
                if (element.getAnnotation(Column.class) != null) {
                    col = element.getAnnotation(Column.class).name();
                }
            }
            if (col == null || col.length() == 0) col = field;
            Expression methodCallExpr = DbLiteHelper.isFieldBoolean(elements.get(0).asType().toString()) ?
                    new BinaryExpr(
                            new MethodCallExpr(
                                    new MethodCallExpr(
                                            String.format("cursor.get%s", DbLiteHelper.sql2Java(elements.get(0).asType().toString())))
                                            .setArguments(new NodeList(new MethodCallExpr("cursor.getColumnIndex").setArguments(new NodeList(new StringLiteralExpr(col))))), "equalsIgnoreCase", NodeList.nodeList(new StringLiteralExpr("1")))
                            ,
                            new MethodCallExpr(
                                    new MethodCallExpr(
                                            String.format("cursor.get%s", DbLiteHelper.sql2Java(elements.get(0).asType().toString())))
                                            .setArguments(new NodeList(new MethodCallExpr("cursor.getColumnIndex").setArguments(new NodeList(new StringLiteralExpr(col))))), "equalsIgnoreCase", NodeList.nodeList(new StringLiteralExpr("true")))
                            ,
                            OR) :
                    new MethodCallExpr(String.format("cursor.get%s", DbLiteHelper.sql2Java(elements.get(0).asType().toString())))
                            .setArguments(new NodeList(
                                            new MethodCallExpr("cursor.getColumnIndex")
                                                    .setArguments(new NodeList(new StringLiteralExpr(col)))

                                    )
                            );

            if (DbLiteHelper.isFieldOther(elements.get(0).asType().toString())) {

                for (Element convert : converts) {
                    if (!(Objects.equals(DbLiteHelper.restype(convert), elements.get(0).asType().toString()) && (Objects.equals(DbLiteHelper.argtypes(convert), String.class.getName()))))
                        continue;
                    methodCallExpr = new MethodCallExpr(String.format("%s.%s", convert.getEnclosingElement().toString(), convert.getSimpleName().toString()))
                            .setArguments(new NodeList(
                                            new MethodCallExpr(String.format("cursor.get%s", DbLiteHelper.sql2Java(elements.get(0).asType().toString())))
                                                    .setArguments(new NodeList(
                                                                    new MethodCallExpr("cursor.getColumnIndex")
                                                                            .setArguments(new NodeList(new StringLiteralExpr(col)))
                                                            )
                                                    )
                                    )
                            );
                }

            }
            stmt.addStatement(
                    new IfStmt()
                            .setCondition(new BinaryExpr(
                                    new MethodCallExpr(
                                            String.format("cursor.get%s", DbLiteHelper.sql2Java(elements.get(0).asType().toString())))
                                            .setArguments(new NodeList(new MethodCallExpr("cursor.getColumnIndex").setArguments(new NodeList(new StringLiteralExpr(col)))))
                                    , new NameExpr("null"), NOT_EQUALS))
                            .setThenStmt(
                                    new ExpressionStmt(new MethodCallExpr(String.format("%s.%s", clazzVar, DbLiteHelper.sql2Set(targetIsKotlin, field, elements.get(0).asType().toString())))
                                            .setArguments(new NodeList(methodCallExpr)
                                            ))
                            )

            );
        }
        stmt.addStatement(new ReturnStmt().setExpression(new NameExpr(clazzVar)));
        return stmt;
    }


    private BlockStmt createTable() {
        BlockStmt stmt = new BlockStmt();
        List<List<Element>> listElement = new ArrayList<>(fields().values());
        BinaryExpr binaryExpr = new BinaryExpr().setLeft(new StringLiteralExpr(String.format("CREATE TABLE IF NOT EXISTS %s(", tableName)));

        for (int i = 0; i < listElement.size(); i++) {
            List<Element> list = listElement.get(i);
            AutoInc autoIncAnnotation = null;
            Column columnAnnotation = null;
            NotNull notNullAnnotation = null;
            Unique uniqueAnnotation = null;
            for (Element element : list) {
                if (element.getAnnotation(AutoInc.class) != null)
                    autoIncAnnotation = element.getAnnotation(AutoInc.class);
                if (element.getAnnotation(Column.class) != null)
                    columnAnnotation = element.getAnnotation(Column.class);
                if (element.getAnnotation(NotNull.class) != null)
                    notNullAnnotation = element.getAnnotation(NotNull.class);
                if (element.getAnnotation(Unique.class) != null)
                    uniqueAnnotation = element.getAnnotation(Unique.class);
            }
            String name = columnAnnotation != null ? (columnAnnotation.name().length() == 0 ? list.get(0).toString() : columnAnnotation.name()) : list.get(0).toString();
            String type = columnAnnotation != null ? (columnAnnotation.type().length() == 0 ? DbLiteHelper.java2Sql(list.get(0).asType().toString()) : columnAnnotation.type()) : DbLiteHelper.java2Sql(list.get(0).asType().toString());

            String notNull = notNullAnnotation != null ? " NOT NULL" : " DEFAULT NULL";
            String unique = uniqueAnnotation != null ? " UNIQUE" : "";
            notNull = uniqueAnnotation != null ? " NOT NULL" : notNull;
            type = autoIncAnnotation != null ? "INTEGER" : type;
            unique = autoIncAnnotation != null ? " PRIMARY KEY" : unique;
            notNull = autoIncAnnotation != null ? " AUTOINCREMENT" : notNull;
            StringLiteralExpr right = new StringLiteralExpr(String.format("%s %s%s%s%s", name, type, unique, notNull, i < listElement.size() - 1 ? "," : ""));
            right.setBlockComment("*\n" +
                    "         * column: " + name + "\n" +
                    "         * class: {@link " + clazz + "}\n" +
                    "         * field: {@link " + clazz + "." + list.get(0).toString() + "}\n" +
                    "         * ");
            binaryExpr.setOperator(BinaryExpr.Operator.PLUS).setRight(right);
            binaryExpr = new BinaryExpr().setLeft(binaryExpr);
        }
        binaryExpr.setOperator(BinaryExpr.Operator.PLUS).setRight(new StringLiteralExpr(")"));
        stmt.addStatement(new ReturnStmt().setExpression(binaryExpr));
        return stmt;
    }

    private BlockStmt createConstructor() {
        return new BlockStmt()
                .addStatement(new AssignExpr(new NameExpr("tableName"), new StringLiteralExpr(String.valueOf(tableName)), ASSIGN))
                .addStatement(new AssignExpr(new NameExpr("version"), new NameExpr(String.valueOf(version)), ASSIGN));
    }
}
