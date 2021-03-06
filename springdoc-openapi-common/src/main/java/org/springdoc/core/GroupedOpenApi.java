/*
 *
 *  *
 *  *  * Copyright 2019-2020 the original author or authors.
 *  *  *
 *  *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  *  * you may not use this file except in compliance with the License.
 *  *  * You may obtain a copy of the License at
 *  *  *
 *  *  *      https://www.apache.org/licenses/LICENSE-2.0
 *  *  *
 *  *  * Unless required by applicable law or agreed to in writing, software
 *  *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *  * See the License for the specific language governing permissions and
 *  *  * limitations under the License.
 *  *
 *
 */

package org.springdoc.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springdoc.core.customizers.OperationCustomizer;

import org.springframework.util.CollectionUtils;

import static org.springdoc.core.Constants.GROUP_NAME_NOT_NULL;

public class GroupedOpenApi {

	private final String group;

	private final List<OpenApiCustomiser> openApiCustomisers;

	private final List<OperationCustomizer> operationCustomizers;

	private final List<String> pathsToMatch;

	private final List<String> packagesToScan;

	private final List<String> packagesToExclude;

	private final List<String> pathsToExclude;

	private GroupedOpenApi(Builder builder) {
		this.group = Objects.requireNonNull(builder.group, GROUP_NAME_NOT_NULL);
		this.pathsToMatch = builder.pathsToMatch;
		this.packagesToScan = builder.packagesToScan;
		this.packagesToExclude = builder.packagesToExclude;
		this.pathsToExclude = builder.pathsToExclude;
		this.openApiCustomisers = Objects.requireNonNull(builder.openApiCustomisers);
		this.operationCustomizers = Objects.requireNonNull(builder.operationCustomizers);
		if (CollectionUtils.isEmpty(this.pathsToMatch)
				&& CollectionUtils.isEmpty(this.packagesToScan)
				&& CollectionUtils.isEmpty(this.pathsToExclude)
				&& CollectionUtils.isEmpty(this.packagesToExclude)
				&& CollectionUtils.isEmpty(openApiCustomisers)
				&& CollectionUtils.isEmpty(operationCustomizers))
			throw new IllegalStateException("Packages to scan or paths to filter or openApiCustomisers/operationCustomizers can not be all null for the group:" + this.group);
	}

	public static Builder builder() {
		return new Builder();
	}

	public String getGroup() {
		return group;
	}

	public List<String> getPathsToMatch() {
		return pathsToMatch;
	}

	public List<String> getPackagesToScan() {
		return packagesToScan;
	}

	public List<String> getPackagesToExclude() {
		return packagesToExclude;
	}

	public List<String> getPathsToExclude() {
		return pathsToExclude;
	}

	public List<OpenApiCustomiser> getOpenApiCustomisers() {
		return openApiCustomisers;
	}

	public List<OperationCustomizer> getOperationCustomizers() {
		return operationCustomizers;
	}

	public static class Builder {
		private final List<OpenApiCustomiser> openApiCustomisers = new ArrayList<>();

		private final List<OperationCustomizer> operationCustomizers = new ArrayList<>();

		private String group;

		private List<String> pathsToMatch;

		private List<String> packagesToScan;

		private List<String> packagesToExclude;

		private List<String> pathsToExclude;

		private Builder() {
			// use static factory method in parent class
		}

		/**
		 * @deprecated Since v1.4.0, GroupedOpenApi.setGroup is marked as deprecated.
		 * Use {@link #group(String) } instead.
		 */
		@Deprecated
		public Builder setGroup(String group) {
			return this.group(group);
		}

		public Builder group(String group) {
			this.group = group;
			return this;
		}

		public Builder pathsToMatch(String... pathsToMatch) {
			this.pathsToMatch = Arrays.asList(pathsToMatch);
			return this;
		}

		public Builder packagesToScan(String... packagesToScan) {
			this.packagesToScan = Arrays.asList(packagesToScan);
			return this;
		}

		public Builder pathsToExclude(String... pathsToExclude) {
			this.pathsToExclude = Arrays.asList(pathsToExclude);
			return this;
		}

		public Builder packagesToExclude(String... packagesToExclude) {
			this.packagesToExclude = Arrays.asList(packagesToExclude);
			return this;
		}

		public Builder addOpenApiCustomiser(OpenApiCustomiser openApiCustomiser) {
			this.openApiCustomisers.add(openApiCustomiser);
			return this;
		}

		public Builder addOperationCustomizer(OperationCustomizer operationCustomizer) {
			this.operationCustomizers.add(operationCustomizer);
			return this;
		}

		public GroupedOpenApi build() {
			return new GroupedOpenApi(this);
		}
	}
}
