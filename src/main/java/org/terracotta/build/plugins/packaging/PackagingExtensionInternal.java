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

package org.terracotta.build.plugins.packaging;

import org.gradle.api.Action;
import org.gradle.api.NamedDomainObjectContainer;
import org.gradle.api.tasks.Nested;

public interface PackagingExtensionInternal extends PackagingExtension {

  @Override
  default void withSourcesJar() {
    getDefaultPackage().withSourcesJar();
  }

  @Override
  default void withJavadocJar() {
    getDefaultPackage().withJavadocJar();
  }

  @Override
  default void withJavadocJar(Action<Javadoc> action) {
    getDefaultPackage().withJavadocJar(action);
  }

  @Override
  default NamedDomainObjectContainer<? extends OptionalFeature> getOptionalFeatures() {
    return getDefaultPackage().getOptionalFeatures();
  }

  @Nested
  DefaultPackageInternal getDefaultPackage();

  @Override
  NamedDomainObjectContainer<VariantPackageInternal> getVariants();
}
