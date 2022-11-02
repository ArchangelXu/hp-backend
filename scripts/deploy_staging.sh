#!/bin/bash
cd ..
if ! ./mvnw clean package; then
    echo "maven build error"
    exit
fi
mkdir target/extracted
java -Djarmode=layertools -jar target/*.jar extract --destination target/extracted
if ! docker build -f configs/Dockerfile --build-arg PROFILE=staging --build-arg EXTRACTED=target/extracted -t hp:staging-latest .; then
    echo "docker image build error"
    exit
fi

docker tag hp:staging-latest registry.cn-beijing.aliyuncs.com/newlogichaos/hp:staging-latest
if ! docker push registry.cn-beijing.aliyuncs.com/newlogichaos/hp:staging-latest; then
    echo "docker push error"
    exit
fi