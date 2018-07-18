/*
 * Copyright (C) 2013 The Project Lombok Authors.
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.milog.lombok.javac.core;


import com.sun.tools.javac.main.JavaCompiler;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.Options;

import javax.lang.model.SourceVersion;

public class LombokOptionsFactory {
	enum LombokOptionCompilerVersion {
		JDK7_AND_LOWER {
			@Override LombokOptions createAndRegisterOptions(Context context) {
				return Javac6BasedLombokOptions.replaceWithDelombokOptions(context);
			}
		},
		
		JDK8 {
			@Override LombokOptions createAndRegisterOptions(Context context) {
				return Javac8BasedLombokOptions.replaceWithDelombokOptions(context);
			}
		},
		
		JDK9 {
			@Override LombokOptions createAndRegisterOptions(Context context) {
				return Javac9BasedLombokOptions.replaceWithDelombokOptions(context);
			}
		};
		
		abstract LombokOptions createAndRegisterOptions(Context context);
	}
	
	public static LombokOptions getDelombokOptions(Context context) {
		Options rawOptions = Options.instance(context);
		if (rawOptions instanceof LombokOptions) {
            return (LombokOptions) rawOptions;
        }
		
		LombokOptions options;
		int javaVersion = getJavaCompilerVersion();
		if (javaVersion < 8) {
			options = LombokOptionCompilerVersion.JDK7_AND_LOWER.createAndRegisterOptions(context);
		} else if (javaVersion == 8) {
			options = LombokOptionCompilerVersion.JDK8.createAndRegisterOptions(context);
		} else {
			options = LombokOptionCompilerVersion.JDK9.createAndRegisterOptions(context);
		}
		return options;
	}

    private static int getJavaCompilerVersion() {
        String version = JavaCompiler.version();
        String [] arr = version.split("\\.");
        return Integer.parseInt(arr[1]);
    }
}
