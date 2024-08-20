dependencies {
	implementation(project(":diary-core"))

	//cache
	implementation("org.springframework.boot:spring-boot-starter-data-redis")
	implementation("org.springframework.boot:spring-boot-starter-cache")
	implementation("com.github.ben-manes.caffeine:caffeine:3.1.8")

	//security
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("io.jsonwebtoken:jjwt-api:0.12.5")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.5")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.5")

	//line kotlin jdsl
	implementation("com.linecorp.kotlin-jdsl:jpql-dsl:3.5.1")
	implementation("com.linecorp.kotlin-jdsl:jpql-render:3.5.1")
	implementation("com.linecorp.kotlin-jdsl:spring-data-jpa-support:3.5.1")


	//swagger
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.1.0")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
