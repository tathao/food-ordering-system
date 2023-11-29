#/bin/zsh

VERSION=1.0-SNAPSHOT

folder="keycloak-containers"

image_name="keycloak"
image_tag="${VERSION}"

if  ! docker images --format "{{.Repository}}:{{.Tag}}" | grep -q "^$image_name:$image_tag$" ; then
	#statements
	if [[ ! -d "$folder"  ]]; then
		#statements
		git clone git@github.com:keycloak/keycloak-containers.git
	fi
	cd keycloak-containers/server
	git checkout 19.0.3
	docker build -t "com.twitter.kafka.service/keycloak:${VERSION}" .

fi
cd $HOME/Projects/docker
docker-compose -f network.yml -f keycloak.yml up -d
echo "Done"!