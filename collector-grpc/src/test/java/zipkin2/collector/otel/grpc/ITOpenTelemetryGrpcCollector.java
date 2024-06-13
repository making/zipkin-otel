/*
 * Copyright 2024 The OpenZipkin Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package zipkin2.collector.otel.grpc;

import java.io.IOException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import zipkin2.collector.CollectorComponent;
import zipkin2.collector.CollectorSampler;
import zipkin2.collector.InMemoryCollectorMetrics;
import zipkin2.storage.InMemoryStorage;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ITOpenTelemetryGrpcCollector {

  InMemoryStorage store;
  InMemoryCollectorMetrics metrics;
  CollectorComponent collector;

  @BeforeEach
  public void setup() {
    store = InMemoryStorage.newBuilder().build();
    metrics = new InMemoryCollectorMetrics();

    collector = OpenTelemetryGrpcCollector.newBuilder()
        .metrics(metrics)
        .sampler(CollectorSampler.ALWAYS_SAMPLE)
        .storage(store)
        .build()
        .start();
    metrics = metrics.forTransport("otel/grpc");
  }

  @AfterEach
  void teardown() throws IOException {
    store.close();
    collector.close();
  }

  // TODO: integration test
}
