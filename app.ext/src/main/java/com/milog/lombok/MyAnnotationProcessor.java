package com.milog.lombok;

import com.milog.lombok.app.MyApp;
import com.milog.lombok.javac.MiloProcessor;
import com.sun.tools.javac.main.JavaCompiler;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

/**
 * Created by miloway on 2018/7/12.
 * Main
 */
//@SupportedAnnotationTypes("*")
public class MyAnnotationProcessor extends AbstractProcessor {
    private MiloProcessor processor;
    private Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        MyApp.init();
        processor = new MiloProcessor();
        processor.init(processingEnv);
        messager = processingEnv.getMessager();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        messager.printMessage(Diagnostic.Kind.NOTE, "" + JavaCompiler.version());
        processor.process(annotations, roundEnv);

        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return MyApp.getAnnotations();
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }


}
