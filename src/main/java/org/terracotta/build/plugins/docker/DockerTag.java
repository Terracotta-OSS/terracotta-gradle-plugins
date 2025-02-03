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

package org.terracotta.build.plugins.docker;

import org.gradle.api.provider.ListProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.publish.plugins.PublishingPlugin;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;

/**
 * Docker '{@code docker tag}' task.
 */
public abstract class DockerTag extends DockerTask {

  public DockerTag() {
    setGroup(PublishingPlugin.PUBLISH_TASK_GROUP);
  }

  @TaskAction
  public void tag() {
    getTags().get().forEach(tag -> docker(spec -> spec.args("tag", getImageId().get(), tag)));
  }

  /**
   * Tags to create.
   *
   * @return tags to create
   */
  @Input
  public abstract ListProperty<String> getTags();

  /**
   * Image id (hash) to tag.
   *
   * @return image id
   */
  @Input
  public abstract Property<String> getImageId();
}
