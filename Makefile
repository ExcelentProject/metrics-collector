include Makefile.docker

VERSION ?= `cat collector.version`
PROJECT_NAME ?= metrics-collector

build: copy_files docker_build docker_tag docker_push clear

copy_files:
	mkdir -p docker-image/tmp/
	cp collector/src/main/resources/log4j2.properties docker-image/tmp/
	cp collector/target/collector-${VERSION}.jar docker-image/tmp/

clear:
	rm -rf docker-image/tmp/