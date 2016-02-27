package com.petercipov.traces.junit;

import com.petercipov.traces.api.FinishableTrace;
import com.petercipov.traces.api.Level;
import com.petercipov.traces.api.NoopTraceFactory;
import com.petercipov.traces.api.TraceFactory;
import java.net.URL;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 *
 * @author petercipov
 */
public class ReflexTraceImplFactory implements TraceImplFactory {

	private final String loaderPath;
	private final String className;
	private final Level expectedLevel;
	private final TraceFactory traceFactory;

	public ReflexTraceImplFactory(String className, Level expectedLevel) {
		this.loaderPath = className.replace('.', '/')+".class";
		this.className = className;
		this.expectedLevel = expectedLevel;
		this.traceFactory = load();
	}

	@Override
	public FinishableTrace create() {
		return this.traceFactory.create(expectedLevel);
	}
	
	private TraceFactory load() {
		Set<URL> loaders = findPossibleLoaders();
		
		if (loaders.isEmpty()) {
			return new NoopTraceFactory();
		}
		
		assertSingle(loaders);
		
		try {
			return (TraceFactory)this.getClass().getClassLoader().loadClass(className).newInstance();
		} catch(Exception ex) {
			throw new IllegalStateException("Error while initializing TraceFactory", ex);
		}
	}
	
	private Set<URL> findPossibleLoaders() {
		
        Set<URL> loadersPaths = new LinkedHashSet();
        try {
            ClassLoader loader = this.getClass().getClassLoader();
            Enumeration<URL> paths;
            if (loader == null) {
                paths = ClassLoader.getSystemResources(loaderPath);
            } else {
                paths = loader.getResources(loaderPath);
            }
			
			if (paths != null) {
				while (paths.hasMoreElements()) {
					URL path = paths.nextElement();
					loadersPaths.add(path);
				}
			}
        } catch (Exception ex) {
			throw new IllegalStateException("Exception while loading class", ex);
        }
        return loadersPaths;
    }
	
	private void assertSingle(Set<URL> loaders) {
		int size = loaders.size();
		if (size != 1) {
			throw new IllegalArgumentException("Multiple connector classes were found on the classpath. It is ambiguous !");
		}
	}
	
}
