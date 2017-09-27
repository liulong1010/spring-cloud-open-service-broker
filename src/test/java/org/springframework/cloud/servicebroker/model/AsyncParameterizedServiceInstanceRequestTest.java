package org.springframework.cloud.servicebroker.model;

import org.junit.Test;
import org.springframework.cloud.servicebroker.model.fixture.DataFixture;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.*;
import static org.springframework.cloud.servicebroker.model.CloudFoundryContext.CLOUD_FOUNDRY_PLATFORM;
import static org.springframework.cloud.servicebroker.model.KubernetesContext.KUBERNETES_PLATFORM;

public class AsyncParameterizedServiceInstanceRequestTest {
	@Test
	public void requestWithCloudFoundryContextIsRead() {
		AsyncParameterizedServiceInstanceRequest request =
				DataFixture.readTestDataFile("requestWithCloudFoundryContext.json",
						CreateServiceInstanceRequest.class);

		assertEquals(CLOUD_FOUNDRY_PLATFORM, request.getContext().getPlatform());
		assertThat(request.getContext(), instanceOf(CloudFoundryContext.class));

		CloudFoundryContext context = (CloudFoundryContext) request.getContext();
		assertEquals("test-organization-guid", context.getOrganizationGuid());
		assertEquals("test-space-guid", context.getSpaceGuid());
		assertEquals("data", context.getField("field1"));
		assertEquals(2, context.getField("field2"));
	}

	@Test
	public void requestWithKubernetesContextIsRead() {
		AsyncParameterizedServiceInstanceRequest request =
				DataFixture.readTestDataFile("requestWithKubernetesContext.json",
						CreateServiceInstanceRequest.class);

		assertEquals(KUBERNETES_PLATFORM, request.getContext().getPlatform());
		assertThat(request.getContext(), instanceOf(KubernetesContext.class));

		KubernetesContext context = (KubernetesContext) request.getContext();
		assertEquals("test-namespace", context.getNamespace());
		assertEquals("data", context.getField("field1"));
		assertEquals(2, context.getField("field2"));
	}

	@Test
	public void requestWithUnknownContextIsRead() {
		AsyncParameterizedServiceInstanceRequest request =
				DataFixture.readTestDataFile("requestWithCustomContext.json",
						CreateServiceInstanceRequest.class);

		assertEquals("test-platform", request.getContext().getPlatform());

		assertEquals("data", request.getContext().getField("field1"));
		assertEquals(2, request.getContext().getField("field2"));
	}
}