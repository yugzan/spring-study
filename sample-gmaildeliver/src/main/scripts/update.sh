#!/bin/sh

NAME='update.sh'
srcFolder='tequila'

if [ -d "$srcFolder" ]; then
	rm -rf $srcFolder
fi

git clone ssh://git@140.92.71.209/opt/git/cellar/tequila.git

cd $srcFolder && git checkout develop
./gradlew clean build -x test
rm build/libs/*.jar.original

case "$1" in
	jar)
		# only replace jar
		mv build/libs/*.jar $pwd 
		;;
	*|all)
		# replace everything
		mv app.sh application.yml *.jar update.sh $pwd
		;;
esac