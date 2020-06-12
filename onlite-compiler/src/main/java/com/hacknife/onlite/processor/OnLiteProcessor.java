package com.hacknife.onlite.processor;

import com.hacknife.onlite.OnLite;
import com.hacknife.onlite.annotation.AutoInc;
import com.hacknife.onlite.annotation.Column;
import com.hacknife.onlite.annotation.Convert;
import com.hacknife.onlite.annotation.NotNull;
import com.hacknife.onlite.annotation.Table;
import com.google.auto.service.AutoService;
import com.hacknife.onlite.annotation.Unique;
import com.hacknife.onlite.annotation.Version;

import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;
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
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

/**
 * author  : Hacknife
 * e-mail  : hacknife@outlook.com
 * github  : http://github.com/hacknife
 * project : OnLite
 */
@AutoService(Processor.class)
public class OnLiteProcessor extends AbstractProcessor {
    protected Messager messager;
    protected Map<String, OnLite> liteMap = new HashMap<>();


    @Override
    public Set<String> getSupportedAnnotationTypes() {
        HashSet<String> supportType = new LinkedHashSet<>();
        supportType.add(Table.class.getCanonicalName());
        supportType.add(Version.class.getCanonicalName());
        supportType.add(AutoInc.class.getCanonicalName());
        supportType.add(Column.class.getCanonicalName());
        supportType.add(NotNull.class.getCanonicalName());
        supportType.add(Unique.class.getCanonicalName());
        supportType.add(Convert.class.getCanonicalName());
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
        messager.printMessage(Diagnostic.Kind.NOTE, "process...");
        liteMap.clear();
        processTable(roundEnv);
        processVersion(roundEnv);
        processAutoInc(roundEnv);
        processColumn(roundEnv);
        processNotNull(roundEnv);
        processUnique(roundEnv);
        processConvert(roundEnv);
        process();
        return true;
    }

    private void processConvert(RoundEnvironment roundEnv) {
        Set<? extends Element> converts = roundEnv.getElementsAnnotatedWith(Convert.class);
        for (Element element : converts) {
            for (OnLite value : liteMap.values()) {
                value.addConvert(element);
            }
        }
    }

    private void processUnique(RoundEnvironment roundEnv) {
        Set<? extends Element> uniques = roundEnv.getElementsAnnotatedWith(Unique.class);
        for (Element element : uniques) {
            String fullClass = element.getEnclosingElement().toString();
            OnLite lite = liteMap.get(fullClass);
            if (lite == null) return;
            lite.addElement(element);
        }
    }

    private void processNotNull(RoundEnvironment roundEnv) {
        Set<? extends Element> notNulls = roundEnv.getElementsAnnotatedWith(NotNull.class);
        for (Element element : notNulls) {
            String fullClass = element.getEnclosingElement().toString();
            OnLite lite = liteMap.get(fullClass);
            if (lite == null) return;
            lite.addElement(element);
        }
    }

    private void processColumn(RoundEnvironment roundEnv) {
        Set<? extends Element> columns = roundEnv.getElementsAnnotatedWith(Column.class);
        for (Element element : columns) {
            String fullClass = element.getEnclosingElement().toString();
            OnLite lite = liteMap.get(fullClass);
            if (lite == null) return;
            lite.addElement(element);
        }
    }


    private void processAutoInc(RoundEnvironment roundEnv) {
        Set<? extends Element> autoIncs = roundEnv.getElementsAnnotatedWith(AutoInc.class);
        for (Element element : autoIncs) {
            String fullClass = element.getEnclosingElement().toString();
            OnLite lite = liteMap.get(fullClass);
            if (lite == null) return;
            lite.addElement(element);
        }
    }

    private void processVersion(RoundEnvironment roundEnv) {
        Set<? extends Element> versions = roundEnv.getElementsAnnotatedWith(Version.class);
        for (Element element : versions) {
            String fullClass = element.asType().toString();
            Version version = element.getAnnotation(Version.class);
            OnLite lite = liteMap.get(fullClass);
            if (lite == null) return;
            lite.setVersion(version.value());
        }
    }

    private void processTable(RoundEnvironment roundEnv) {
        Set<? extends Element> tables = roundEnv.getElementsAnnotatedWith(Table.class);
        for (Element element : tables) {
            String fullClass = element.asType().toString();
            Table table = element.getAnnotation(Table.class);
            OnLite onLite = new OnLite();
            liteMap.put(fullClass, onLite);
            onLite.setElement(element);
            onLite.setFullClass(fullClass);
            onLite.setTableName(table.value().equals("") ? fullClass.substring(fullClass.lastIndexOf(".") + 1) : table.value());
        }
    }

    private void process() {

        for (String key : liteMap.keySet()) {
            try {
                OnLite lite = liteMap.get(key);
                JavaFileObject jfo = processingEnv.getFiler().createSourceFile(
                        lite.getOnLiteClass(),
                        lite.getElement()
                );
                Writer writer = jfo.openWriter();
                writer.write(lite.createLite(messager));
                writer.flush();
                writer.close();
            } catch (Exception ignored) {
            }
        }
    }
}
