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

import groovy.lang.Closure;
import org.gradle.api.NamedDomainObjectProvider;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.Dependency;
import org.gradle.api.artifacts.dsl.DependencyHandler;
import org.gradle.api.attributes.Category;
import org.gradle.api.file.RegularFile;
import org.gradle.api.provider.Provider;

import java.util.Map;

import static java.util.stream.Collectors.toMap;

public class DockerEcosystemPlugin implements Plugin<Project> {

  public static final String DOCKER_IMAGE_ID = "docker-image";

  @Override
  public void apply(Project project) {
    NamedDomainObjectProvider<Configuration> dockerBucket = project.getConfigurations().register("docker", config -> {
      config.setDescription("Docker image dependencies.");
      config.setCanBeConsumed(false);
      config.setCanBeResolved(false);
      config.setVisible(false);
    });

    NamedDomainObjectProvider<Configuration> dockerImageIds = project.getConfigurations().register("dockerImageIds", config -> {
      config.setDescription("Incoming docker image-id files.");
      config.setCanBeConsumed(false);
      config.setCanBeResolved(true);
      config.setVisible(false);
      config.extendsFrom(dockerBucket.get());
      config.attributes(attrs -> attrs.attribute(Category.CATEGORY_ATTRIBUTE, project.getObjects().named(Category.class, DOCKER_IMAGE_ID)));
    });

    project.getExtensions().create("docker", DockerExtension.class, dockerImageIds);

    DependencyHandler dependencies = project.getDependencies();
    dependencies.getExtensions().add("external", new Closure<Dependency>(dependencies, dependencies) {

      Dependency doCall(String name, String imageId) {
        Provider<RegularFile> imageIdFile = project.getLayout().getBuildDirectory().file(name + ".iid");
        DockerBuild.writeImageId(imageIdFile.get(), imageId);
        return ((DependencyHandler) getOwner()).create(project.files(imageIdFile));
      }
    });
  }

  public static class DockerExtension {

    private final Images images;

    public DockerExtension(Provider<Configuration> imageIdConfiguration) {
      this.images = new Images(imageIdConfiguration);
    }

    public Images getImages() {
      return images;
    }
  }

  public static class Images {

    private final Provider<Map<String, String>> images;

    public Images(Provider<Configuration> imageIdConfiguration) {
      this.images = imageIdConfiguration.flatMap(c -> c.getElements().map(files -> files.stream().collect(
              toMap(a -> a.getAsFile().getName().replaceAll("\\.iid$", ""), DockerBuild::readImageId))));
    }

    public Provider<String> getByName(String name) {
      return images.map(m -> m.get(name));
    }

    public Provider<String> getAt(String name) {
      return getByName(name);
    }

    public Provider<Map<String, String>> all() {
      return images;
    }
  }
}
