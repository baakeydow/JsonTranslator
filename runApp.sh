sudo docker build -t json-translator . &&
sudo docker run \
  -p 8080:8000 \
  -it --rm --name runningJava json-translator \
  /bin/bash -c "mvn spring-boot:run"