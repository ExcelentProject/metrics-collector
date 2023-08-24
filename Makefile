include Makefile.docker

PROJECT_NAME ?= metrics-collector

build: copy_files docker_build docker_tag docker_push clear

copy_files:
	mkdir -p docker-image/tmp/
	cp collector/src/main/resources/log4j2.properties docker-image/tmp/
	cp collector/target/collector-0.1.0-SNAPSHOT.jar docker-image/tmp/

clear:
	rm -rf docker-image/tmp/