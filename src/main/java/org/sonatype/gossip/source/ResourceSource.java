/*
 * Copyright (C) 2006-2007 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.sonatype.gossip.source;

import org.sonatype.gossip.ConfigurationException;
import org.sonatype.gossip.MissingPropertyException;
import org.sonatype.gossip.model.Model;

import java.net.URL;

/**
 * Resource-based configuration source.
 *
 * @author <a href="mailto:jason@planet57.com">Jason Dillon</a>
 *
 * @since 1.0
 */
public class ResourceSource
    extends SourceSupport
{
    private static final String CL_TYPE_TCL = "TCL";

    private static final String CL_TYPE_INTERNAL = "INTERNAL";

    private static final String CL_TYPE_SYSTEM = "SYSTEM";

    private String name;

    private String classLoaderType = CL_TYPE_TCL;

    private ClassLoader classLoader;

    public ResourceSource() {}

    public ResourceSource(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getClassLoaderType() {
        return classLoaderType;
    }

    public void setClassLoaderType(final String type) {
        assert type != null;
        
        this.classLoaderType = type;
    }
    
    public ClassLoader getClassLoader() {
        if (classLoader == null) {
            String type = classLoaderType.toUpperCase();

            if (type.equals(CL_TYPE_TCL)) {
                classLoader = Thread.currentThread().getContextClassLoader();
            }
            else if (type.equals(CL_TYPE_INTERNAL)) {
                classLoader = getClass().getClassLoader();
            }
            else if (type.equals(CL_TYPE_SYSTEM)) {
                classLoader = ClassLoader.getSystemClassLoader();
            }
            else {
                throw new ConfigurationException("Invalid classLoaderType: " + classLoaderType);
            }
        }

        return classLoader;
    }

    public void setClassLoader(final ClassLoader cl) {
        this.classLoader = cl;
    }

    public Model load() throws Exception {
        if (name == null) {
            throw new MissingPropertyException("name");
        }

        Model model = null;

        ClassLoader cl = getClassLoader();
        assert cl != null;
        
        log.trace("Loading resource for name: {}, CL: {}", name, cl);
        
        URL url = cl.getResource(name);

        if (url == null) {
            log.trace("Unable to load; missing resource: {}", name);
        }
        else {
            model = load(url);
        }

        return model;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "name='" + name + '\'' +
                ", classLoaderType='" + classLoaderType + '\'' +
                ", classLoader=" + classLoader +
                '}';
    }
}