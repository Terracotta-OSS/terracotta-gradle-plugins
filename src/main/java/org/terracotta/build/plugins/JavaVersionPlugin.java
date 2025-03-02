/*
 * Copyright Terracotta, Inc.
 * Copyright IBM Corp. 2024, 2025
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

package org.terracotta.build.plugins;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.provider.Property;
import org.gradle.jvm.toolchain.JavaLanguageVersion;

public class JavaVersionPlugin implements Plugin<Project> {

  public static final JavaLanguageVersion DEFAULT_LANGUAGE_VERSION = JavaLanguageVersion.of(8);

  @Override
  public void apply(Project project) {
    JavaVersions javaVersions = project.getExtensions().create("java-versions", JavaVersions.class);

    javaVersions.getCompileVersion().convention(project.provider(() -> project.findProperty("compileVM"))
            .map(o -> JavaLanguageVersion.of(o.toString()))
            .orElse(DEFAULT_LANGUAGE_VERSION));

    javaVersions.getTestVersion().convention(project.provider(() -> project.findProperty("testVM"))
            .map(o -> JavaLanguageVersion.of(o.toString()))
            .orElse(javaVersions.getCompileVersion()));
  }

  public interface JavaVersions {

    Property<JavaLanguageVersion> getCompileVersion();

    Property<JavaLanguageVersion> getTestVersion();
  }
}
