#!/bin/bash -e

# check release version parameter
RELEASE_VERSION=$1
RELEASE_DIR=~/private_dev/tip_centrum/releases/${RELEASE_VERSION}

if [ -z "${RELEASE_VERSION}" ]
then
	echo "RELEASE_VERSION not set. Missing 1-st command line argument"
	exit 1
fi

# Maven Release
mvn clean install appassembler:assemble
mvn release:clean 
mvn release:prepare 

# Copy the release to releases directory
mkdir ${RELEASE_DIR}
cp -R target/appassembler/* ${RELEASE_DIR}
ln -fshv ${RELEASE_DIR} releases/latest
