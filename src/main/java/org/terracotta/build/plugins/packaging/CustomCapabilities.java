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

import org.gradle.api.DomainObjectSet;
import org.gradle.api.artifacts.ModuleDependencyCapabilitiesHandler;
import org.gradle.api.capabilities.Capability;
import org.gradle.api.plugins.JavaPluginExtension;

public interface CustomCapabilities {

  /**
   * The capabilities of this package/variant.
   *
   * @return the capability set
   */
  DomainObjectSet<Capability> getCapabilities();

  /**
   * Declares a capability for this package/variant.
   *
   * @param notation capability notation
   * @see ModuleDependencyCapabilitiesHandler
   */
  void capability(Object notation);
}
