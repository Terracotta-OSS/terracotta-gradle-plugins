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

import org.gradle.api.Project;
import org.gradle.api.capabilities.Capability;
import org.gradle.api.internal.artifacts.dsl.CapabilityNotationParser;

import javax.inject.Inject;

import static org.terracotta.build.Utils.mapOf;

public interface CustomCapabilitiesInternal extends CustomCapabilities {

  @Inject
  Project getProject();

  @Inject
  CapabilityNotationParser getCapabilityNotationParser();

  @Override
  default void capability(Object notation) {
    Capability c = getCapabilityNotationParser().parseNotation(notation);
    if (c.getVersion() == null) {
      c = getCapabilityNotationParser().parseNotation(mapOf(
          "group", c.getGroup(),
          "name", c.getName(),
          "version", getProject().getVersion()));
    }
    getCapabilities().add(c);
  }
}
