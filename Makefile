#.DEFAULT_GOAL := build-run
#
#setup:
#	gradle wrapper --gradle-version 8.4
#
#clean:
#	./gradlew clean
#
#build:
#	./gradlew clean build
#
#install:
#	./gradlew clean installDist
#
#run-dist:
#	./build/install/app/bin/app
#
#run:
#	./gradlew run
#
#start:
#	./gradlew bootRun --args='--spring.profiles.active=dev'
#
#start-prod:
#	./gradlew bootRun --args='--spring.profiles.active=prod'
#
#test:
#	./gradlew test
#
#report:
#	./gradlew jacocoTestReport
#
#lint:
#	./gradlew checkstyleMain checkstyleTest
#
#build-run: build run
#
#.PHONY: build

.DEFAULT_GOAL := build-run

backend:
	./gradlew bootRun --args='--spring.profiles.active=dev'

clean:
	./gradlew clean

build:
	./gradlew clean build

install:
	./gradlew clean install

run-dist:
	./build/install/java-package/bin/java-package

run:
	./gradlew run

test:
	./gradlew test

report:
	./gradlew jacocoTestReport

lint:
	./gradlew checkstyleMain checkstyleTest

update-deps:
	./gradlew useLatestVersions


build-run: build run

.PHONY: build