/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package be.fluid_it.camel.components;

import java.util.Map;

import org.apache.camel.CamelContext;
import org.apache.camel.Consumer;
import org.apache.camel.Endpoint;
import org.apache.camel.Processor;
import org.apache.camel.impl.DefaultComponent;
import org.apache.camel.spi.RestConfiguration;
import org.apache.camel.spi.RestConsumerFactory;
import org.glassfish.jersey.process.Inflector;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.model.Resource;
import org.glassfish.jersey.server.model.ResourceMethod;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MediaType;

/**
 * Represents the component that manages {@link Jersey2Endpoint}.
 */
public class Jersey2Component extends DefaultComponent implements RestConsumerFactory {

  protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters) throws Exception {
    Endpoint endpoint = new Jersey2Endpoint(uri, this);
    setProperties(endpoint, parameters);
    return endpoint;
  }

  @Override
  public Consumer createConsumer(CamelContext camelContext, Processor processor, String verb, String basePath, String uriTemplate, String consumes, String produces, RestConfiguration configuration, Map<String, Object> parameters) throws Exception {
    ResourceConfig resourceConfig = (ResourceConfig)camelContext.getRegistry().lookupByName(ResourceConfig.class.getName());
    final Resource.Builder resourceBuilder = Resource.builder();
    resourceBuilder.path(basePath);
    final ResourceMethod.Builder methodBuilder = resourceBuilder.addMethod(verb.toUpperCase());
    // TODO
    methodBuilder.produces(MediaType.TEXT_PLAIN_TYPE)
            .handledBy(new Inflector<ContainerRequestContext, String>() {

              @Override
              public String apply(ContainerRequestContext containerRequestContext) {
                return "Hello World!";
              }
            });
    Resource resource = resourceBuilder.build();
    resourceConfig.registerResources(resource);
    return null;
  }
}
