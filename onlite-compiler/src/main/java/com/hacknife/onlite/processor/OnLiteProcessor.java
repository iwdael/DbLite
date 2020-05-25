package com.hacknife.onlite.processor;

import com.hacknife.onlite.LiteFile;
import com.hacknife.onlite.annotation.Table;
import com.hacknife.onlite.util.Logger;
import com.google.auto.service.AutoService;
import com.hacknife.onlite.util.StringUtil;

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
 * e-mail  : 4884280@qq.com
 * github  : http://github.com/hacknife
 * project : OnLite
 */
@AutoService(Processor.class)
public class OnLiteProcessor extends AbstractProcessor {
    protected Messager messager;
    protected Elements elementUtils;
    protected Map<String, LiteFile> mProxyMap = new LinkedHashMap<>();
    protected String buidPath;
    protected boolean inited = false;

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        HashSet<String> supportType = new LinkedHashSet<>();
        supportType.add(Table.class.getCanonicalName());
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
        messager.printMessage(Diagnostic.Kind.NOTE, "process...");
        mProxyMap.clear();
        processLite(annotations, roundEnv);
        process();
        return true;
    }

    private void processLite(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> tables = roundEnv.getElementsAnnotatedWith(Table.class);
        for (Element element : tables) {
            String fullClassName = element.asType().toString();
            Table table = element.getAnnotation(Table.class);

            if (mProxyMap.get(fullClassName) == null) {
                mProxyMap.put(fullClassName, new LiteFile(elementUtils, (TypeElement) element, table.value()));
            }
        }
    }

    private void process() {
        for (String key : mProxyMap.keySet()) {
            LiteFile liteFile = mProxyMap.get(key);
            try {
                JavaFileObject jfo = processingEnv.getFiler().createSourceFile(
                        liteFile.getProxyClassFullName(),
                        liteFile.getTypeElement()
                );
                if (!inited) {
                    //找到当前module
                    buidPath = StringUtil.findBuildDir(jfo.toUri().getPath());
//                    Logger.v("find build directory: " + buidPath);
                    inited = true;
                }
//                Logger.v(jfo.toUri().getPath());
                Writer writer = jfo.openWriter();
                writer.write(liteFile.generateJavaCode(buidPath));
                writer.flush();
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
