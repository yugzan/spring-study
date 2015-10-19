#!/bin/sh
usage() {
	local message="Usage: $(basename "$0") [-e]\n
		-e enable assert"
	echo $message
}

TARGET_FILE=$0

cd `dirname $TARGET_FILE`
TARGET_FILE=`basename $TARGET_FILE`

# Iterate down a (possible) chain of symlinks
while [ -L "$TARGET_FILE" ]
do
    TARGET_FILE=`readlink $TARGET_FILE`
    cd `dirname $TARGET_FILE`
    TARGET_FILE=`basename $TARGET_FILE`
done

# Compute the canonicalized name by finding the physical path
# for the directory we're in and appending the target file.
SCRIPT_DIR=`pwd -P`
APP_HOME=${SCRIPT_DIR}/

cd $APP_HOME
APP_HOME=`pwd -P`
echo "using APP_HOME="${APP_HOME}

JAR_NAME="xlsdownload-0.0.1-SNAPSHOT.jar"
ARG=""
ENV_ARG=""

cmd="java -jar $ARG $JAR_NAME $ENV_ARG $LOGGING_CONFIG"
echo $cmd
eval $cmd
