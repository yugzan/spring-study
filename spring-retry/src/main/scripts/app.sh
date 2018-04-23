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

cd $SCRIPT_DIR
APP_HOME="`pwd -P`"

JAR=@name@-@version@.jar
LOGGING_CONFIG="--logging.config=${APP_HOME}/config/logback.xml --logging.file=${APP_HOME}/log/@name@.log"
ARG=""
ENV_ARG=""

while getopts ":ehds" option; do
	case "$option" in
		s)
			ARG="-server"
			;;
		d)
			# ignore default
			ARG="-Xms2048m -Xmx8192m"
			;;
		e)
			ARG="$ARG -ea"	
			;;
		h)
	    	usage
			exit 0
			;;
		:)
			echo "Option -$OPTARG requires an argument." >&2
			usage
			exit 1
			;;
		\?)
			echo "Invalid option: -$OPTARG" >&2
			usage
			exit 1
			;;		
	esac
done

cmd="java -jar $ARG $JAR $ENV_ARG $LOGGING_CONFIG"
echo $cmd
eval $cmd
