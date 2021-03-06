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
package be.fluid_it.camel.components.jersey2;

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultEndpoint;

/**
 * Represents a Jersey2 endpoint.
 */
public class Jersey2Endpoint extends DefaultEndpoint {

  public Jersey2Endpoint() {
  }

  public Jersey2Endpoint(String uri, Jersey2Component component) {
    super(uri, component);
  }

  public Jersey2Endpoint(String endpointUri) {
    super(endpointUri);
  }

  public Producer createProducer() throws Exception {
    return new Jersey2Producer(this);
  }

  public Consumer createConsumer(Processor processor) throws Exception {
    return new Jersey2Consumer(this, processor);
  }

  public boolean isSingleton() {
    return true;
  }
}
