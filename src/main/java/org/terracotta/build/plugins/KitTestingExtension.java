/*
 * Copyright IBM Corp. 2026
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

import org.gradle.api.DomainObjectSet;
import org.gradle.api.plugins.jvm.JvmTestSuite;

/**
 * Project extension of the kit based testing plugins (angela, galvan).
 */
public abstract class KitTestingExtension {

  private final DomainObjectSet<JvmTestSuite> targetSuites;

  public KitTestingExtension(DomainObjectSet<JvmTestSuite> targetSuites) {
    this.targetSuites = targetSuites;
  }

  public DomainObjectSet<JvmTestSuite> getSuites() {
    return targetSuites;
  }

  public void on(JvmTestSuite testSuite) {
    targetSuites.add(testSuite);
  }
}
